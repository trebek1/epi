import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

class Heap {


  public static List<Integer> mergeSortedFiles(List<List<Integer>> sortedArrays){
  	  int initialCapacity = 16;

      PriorityQueue<ArrayEntry> pq = new PriorityQueue<>(16, new Comparator<ArrayEntry>(){
        @Override
        public int compare(ArrayEntry o1, ArrayEntry o2){
          return Integer.compare(o1.value, o2.value);
        }
      });


      List<Integer> heads = new ArrayList<>(sortedArrays.size());

      // add smallest entry from each list 
      for(int i = 0; i < sortedArrays.size(); i++){
      	if(sortedArrays.get(i).size() > 0){
      		pq.add(new ArrayEntry(sortedArrays.get(i).get(0), i));
      		heads.add(1);
      	} else {
      		heads.add(0);
      	}
      }

      List<Integer> result = new ArrayList<>();

      ArrayEntry headEntry;

      // poll removes and returns the head of the queue 
      while((headEntry = pq.poll()) != null){
      	// remove smallest 
      	result.add(headEntry.value);
      	// find the array that the smallest came from 
      	List<Integer> listHeadCameFrom = sortedArrays.get(headEntry.arrayId);
      	// get head value from heads array (init to 1 for all arrays with data)
      	int arrayIndexOfLastHead = heads.get(headEntry.arrayId);

      	// as long as you havent used all the elements from the array this will be true
      	if(arrayIndexOfLastHead < listHeadCameFrom.size()){
      		// add the next entry of samllestArray into heap

      		// since indices start at zero, getting the current one and adding will work here and forward 
      		// as we add one to it in the next step
      		pq.add(new ArrayEntry(listHeadCameFrom.get(arrayIndexOfLastHead), headEntry.arrayId));
      		heads.set(headEntry.arrayId, heads.get(headEntry.arrayId) + 1); 

      	}
      }

      for(Integer a : result){
      	System.out.println(a);
      }	
      return result;
  }

  private static enum SubarrayType { INCREASING, DECREASING };
  public static List<Integer> sortIncreasingDecreasingArray(List<Integer> ints){
  	List<List<Integer>> sortedSubs = new ArrayList<>();
  	SubarrayType subarrayType = SubarrayType.INCREASING;
  	int index = 0;

  	List<Integer> result = new ArrayList<>();

  	for(int i = 1; i <= ints.size(); i++){
  		if(i == ints.size() || 
  			(ints.get(i - 1) <= ints.get(i) && subarrayType == SubarrayType.DECREASING) || 
  			(ints.get(i - 1) >= ints.get(i) && subarrayType == SubarrayType.INCREASING)){
  			List<Integer> subarray = ints.subList(index, i);
  			if(subarrayType == SubarrayType.DECREASING){
  				Collections.reverse(subarray);
  			}
  			sortedSubs.add(subarray);
  			index = i;
  			subarrayType = (subarrayType == SubarrayType.INCREASING ) ? SubarrayType.DECREASING : SubarrayType.INCREASING;
  		}
  	}

  	return Heap.mergeSortedFiles(sortedSubs);
  }

  public static List<Integer> sortAlmostSorted(List<Integer> ints, int k){
  	List<Integer> result = new ArrayList<>();
  	PriorityQueue<Integer> pq = new PriorityQueue<>();

  	int index = 0;
  	for(int i = 0; i < k; i++){
  		pq.add(ints.get(i));
  		index++;
  	}

  	for(int i = index; i < ints.size(); i++){
  		pq.add(ints.get(i));
  		result.add(pq.poll());
  	}

  	while(!pq.isEmpty()){
  		result.add(pq.poll());
  	}

  	for(Integer a : result){
      System.out.println(a);
    }	

  	return result;
  }



  public static void main(String[] args){

    // -------------------------------------
      // 11.1 Merge Sorted Files
  	  // idea is that you can save space using a heap 
  	  // Max size of heap is O(k) which is size of k lists, this is better than o(n) putting all lists on a pq
  	  // always have min value since have smallest so far on heap at all times 

      // List<Integer> l1 = new ArrayList<>();
      // l1.add(3);
      // l1.add(5);
      // l1.add(7);
      // List<Integer> l2 = new ArrayList<>();
      // l2.add(0);
      // l2.add(6);
      // List<Integer> l3 = new ArrayList<>();
      // l3.add(0);
      // l3.add(6);
      // l3.add(28);

      // List<List<Integer>> sortedArrays = new ArrayList<>();
      // sortedArrays.add(l1);
      // sortedArrays.add(l2);
      // sortedArrays.add(l3);

      // Heap.mergeSortedFiles(sortedArrays);

      // java review! 
      // supress uncheked cast exception
      // @SuppressWarnings("unchecked")
      // ArrayList<Integer> l4 = (ArrayList)l3;

      // 11.2 Sort an Increasing Decreasing Array
      // k increasing decreasing array increases up to a number then decreases down then increases up k times 
      // design an algorithm to sort a k increasing decreasing array (up down up down is a 4 increasing decreasing array)

      // idea is to reverse the decreasing sections of the subarray 
      // then use the sortedFiles idea using minPriorityQueue in the previous question 

      // to reverse an array use Collections.reverse(array)
      // System.out.println("----------");
      // List<Integer> ints = new ArrayList<>();
      // ints.add(57);
      // ints.add(131);
      // ints.add(493);
      // ints.add(294);
      // ints.add(221);
      // ints.add(339);
      // ints.add(418);
      // ints.add(452);
      // ints.add(442);
      // ints.add(190);

      // Heap.sortIncreasingDecreasingArray(ints);

      // 11.2 Sort an Almost Sorted Array -- number are at most k positions from their final position 
      // Idea --> place k items on min heap (first number can be k numbers away from first position)
      // Smallest must be the min on the heap
      // then add the next number to the heap (and remove the smallest) and continue until list is empty and then just 
      // pop off rest of numbers off the heap 
      
      // example with k = 2 

  	  // O(nlog(k)) time complexity 
  	  // O(k) memory 

      System.out.println("----------");
      List<Integer> ints = new ArrayList<>();
      ints.add(3);
      ints.add(-1);
      ints.add(2);
      ints.add(6);
      ints.add(4);
      ints.add(5);
      ints.add(8);

      Heap.sortAlmostSorted(ints, 2);



    // -------------------------------------


  }
}