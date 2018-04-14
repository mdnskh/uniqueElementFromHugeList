import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * Main runner application
 * @author mdnskh
 *
 */
public class MainApplication {
	public static void main(String []args) throws InterruptedException, ExecutionException {
		List<String> heavyList = new ArrayList<String>();
		Random random = new Random();
		for(int i = 0; i<3000; i++) {
			heavyList.add("Test"+ random.nextInt(1000));
		}
		
		System.out.println(heavyList);
		Map<String,Object> finalData = new ConcurrentHashMap<String,Object>();
		
		// Create a pool of 20 threads to process the information in 20 batches
		ExecutorService executorService = Executors.newFixedThreadPool(20);
		List<Processor<String>> threadList = new ArrayList<Processor<String>>(20);
		int lastLimit = 0;
		int maxElements = 100;
		
		List<Future<String>> futures = new ArrayList<Future<String>>(20);
		// Initialize all 20 thread the let them process 10 (maxLimit) items at a time.
		for(int i=0; i<20; i++) {
			Processor<String> dataProcessor = new Processor<>(finalData, heavyList.subList(lastLimit, lastLimit+maxElements));
			lastLimit += maxElements;
			threadList.add(dataProcessor);
			futures.add(executorService.submit(dataProcessor));
		}
		// Now check if any thread has finished its work and is ideal in pool then assign the next 10 (maxLimit) elements
		// from list to process. Continue this until the whole list is exhausted.
		outer:
		while( heavyList.size() > lastLimit) {
			for(int i=0; i<futures.size(); i++) {
				Future<String> future = futures.get(i);
				if( future.isDone()) {
					Processor<String> dataProcessor = new Processor<>(finalData, heavyList.subList(lastLimit, lastLimit+maxElements < heavyList.size() ?lastLimit+maxElements : heavyList.size()));
					future = executorService.submit(dataProcessor);
					futures.set(i,future);
					lastLimit += maxElements;
				}
				if( lastLimit >= heavyList.size()) {
					break outer;
				}
			}
		}
	
		// Request the pool to shutdown
		for(Future<String> future: futures) {
			future.get();
		}
		executorService.shutdown();
		
		// Print the final non-duplicate list
		System.out.println("final list : "+ finalData.keySet());
		//System.out.println(finalData.size());
	}
}
