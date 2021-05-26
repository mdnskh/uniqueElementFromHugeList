# uniqueElementFromHugeList
Find out unique elements from a huge size list.

The idea here is to use multithreading to filter the list.
Input : huge size list having unique and duplicate elements lets say hugeList.
Ouput : List of unique elements. Lets say finalData.

Configuration
maxThreadPool : Maximum size of thread pool.
maxElements : maximum number of elements that a sublist can contain.

This can be achived in following steps.

1. Create a thread pool of maxThreadPool size. Depending on the machine's cores you can choose any number. In the uploaded code it is 20.
2. Create sublists of maxElements from the huge list. The maxElements should be choosen carefully too. For my test project, I have taken 100 elements as maxElements.
3. Now span all the threads, each containing the sublist and reference to common data structure which will have final unique data.
4. Keep on checking the pool for any ideal thread. If any any such thread exist, create the subList of maxElements from remaining elements of huge list and assign this to ideal thread.
5. Continue repeating step 4 until the hugeList exhausted.

At the end of step 5, all unique element will be present in finalData.
