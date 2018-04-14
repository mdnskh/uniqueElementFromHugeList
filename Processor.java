import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;


/**
 * Processor class.
 * It  process the sublist in separate thread and updates the unique data in map
 * @author mdnskh
 *
 * @param <T>
 */
public class  Processor<T> implements Callable<String> {
	private Map<T,Object> updatedData;
	private List<T> dataToProcess;
	
	public Processor(Map<T,Object> updatedData, List<T> dataToProcess) {
		this.updatedData = updatedData;
		this.dataToProcess = dataToProcess;
	}
	public String call() {
		System.out.println(Thread.currentThread().getName() + ": Started call");
		System.out.println(Thread.currentThread().getName() + ": temp list : " + dataToProcess);
		if( this.dataToProcess != null && !this.dataToProcess.isEmpty()) {
			for(T current: dataToProcess) {
				if( updatedData.containsKey(current)) continue;
				else updatedData.put(current,new Object());
			}
		}
		System.out.println(Thread.currentThread().getName() + ":  Finished call");
		return "done";
	}
}
