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

  public static List<Star> findClosestKStars(int k, List<Star> stars){

  	List<Star> result;

  	PriorityQueue<Star> pq = new PriorityQueue<>(k, Collections.reverseOrder());

  	for(int i = 0; i < k; i++){
  		pq.add(stars.get(i));
  	}

  	Star earth = new Star(0, 0, 0);
  	double maxDistance;

  	maxDistance = pq.peek().distance();

  	for(int i = k; i < stars.size(); i++){
  		Star current = stars.get(i);
  		if(current.distance() < maxDistance){

  			pq.poll();
  			pq.add(current);

  			maxDistance = pq.peek().distance();
  		}
  		// if distance of current < max remove head and add new star to PQ;
  	}

  	result = new ArrayList<>(pq);
  	Collections.sort(result);

  	for(Star a : result){
      	System.out.println(a.x + " " + a.y + " " + a.z);
      }	

  	return result;
  }

  public static void findRunningMedian(List<Integer> numbers){
  	int initialCapacity = 16;

  	// larger numbers 
  	PriorityQueue<Integer> minPQ = new PriorityQueue<>();

  	// smaller numbers 
  	PriorityQueue<Integer> maxPQ = new PriorityQueue<>(initialCapacity, Collections.reverseOrder());

  	if(minPQ.isEmpty()){
  		minPQ.add(numbers.get(0));
  	}

  	for(int i = 1; i < numbers.size(); i++){
  		int current = numbers.get(i);

  		if(current < minPQ.peek()){
  			maxPQ.add(current);
  		} else {
  			minPQ.add(current);
  		}

  		if(minPQ.size() > maxPQ.size() + 1){
  			maxPQ.add(minPQ.poll());
  		} else if(maxPQ.size() > minPQ.size()){
  			minPQ.add(maxPQ.poll());
  		}

  		if(maxPQ.size() == minPQ.size()){
  			System.out.println(0.5 * (minPQ.peek() + maxPQ.peek()));
  		} else {
  			System.out.println(minPQ.peek());
  		}
  	}
  }

  public static List<Integer> computeKLargestElementsInMaxHeap(int k, List<Integer> numbers){

  	List<Integer> result = new ArrayList<>();

  	PriorityQueue<Integer> maxPQ = new PriorityQueue<>(16, Collections.reverseOrder());

  	maxPQ.add(numbers.get(0));

  	for(int i = 0; i < k; i++){
  		Integer current = maxPQ.poll();
  		result.add(current);

  		int left = 2 * i + 1;
  		int right = 2 * i + 2;

  		if(left < numbers.size()){
  			maxPQ.add(numbers.get(left));
  		}
  		if(right < numbers.size()){
  			maxPQ.add(numbers.get(right));
  		}
  	}

  	for(Integer i : result){
  		System.out.println(i);
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

      // 11.3 Sort an Almost Sorted Array -- number are at most k positions from their final position 
      // Idea --> place k items on min heap (first number can be k numbers away from first position)
      // Smallest must be the min on the heap
      // then add the next number to the heap (and remove the smallest) and continue until list is empty and then just 
      // pop off rest of numbers off the heap 
      
      // example with k = 2 

  	  // O(nlog(k)) time complexity 
  	  // O(k) memory 

      // System.out.println("----------");
      // List<Integer> ints = new ArrayList<>();
      // ints.add(3);
      // ints.add(-1);
      // ints.add(2);
      // ints.add(6);
      // ints.add(4);
      // ints.add(5);
      // ints.add(8);

      // Heap.sortAlmostSorted(ints, 2);


      // 11.4 Compute k closest stars 

  	  // coordinate system with earth at (0, 0, 0)
  	  // 10^12 stars, coordinates in a file
      // how would you compute the k stars closest to Earth? 

  	  // Use a max heap --> store 12 closest then update by kicking off maximum if one is smaller than it 
  	  // after going through all stars you have 12 smallest in O(nlog(k)) time and O(k) space

      // java review 
  	  // compareTo function in java returns 1, -1, 0. If 1 is returned first number > second so swap is required! 
  	  // jedi swap. So, sort assumes low to high sorting off the bat. 

  	  // To reverse order of Priority Queue (Normally minPQ) use new PriorityQueue(initialCapacity, Collections.reverseOrder());

      // System.out.println("----------");
      // List<Star> stars  = new ArrayList<>();
      // stars.add(new Star(1.0, 2.0, 3.0));
      // stars.add(new Star(77.0, 22.0, 44.0));
      // stars.add(new Star(4.0, 5.0, 6.0));
      // stars.add(new Star(40.0,52.0,22.0));
      // stars.add(new Star(4.0, 4.0, 4.0));
      // stars.add(new Star(88.0, 23.0, 24.0));
      // stars.add(new Star(11.0, 17.0, 1337.0));
      
      // int k = 4;


      // Heap.findClosestKStars(k, stars);

      // 11.5 Compute the running median 
  	  // idea is to keep two heaps
  	  // use max heap for the smaller numbers and min heap for the larger numbers 
      // keep the heaps balanced. As the numbers come in add to heap. 
  	  // median is the avg of 2 numbers if sizes are equal heaps and the larger heap top number if they are not.
  	  // efficiency is O(log(n)) for inserting and deleting elements 
  	  // space is O(n)


  	  // List<Integer> numbers = new ArrayList<>();

  	  // numbers.add(10);
  	  // numbers.add(45);
  	  // numbers.add(72);
  	  // numbers.add(12);
  	  // numbers.add(3);
  	  // numbers.add(55);
  	  // numbers.add(54);
  	  // numbers.add(33);
  	  // numbers.add(99);
  	  // numbers.add(1000);
  	  // numbers.add(1337);

  	  // Heap.findRunningMedian(numbers); // expect 54

  	  // 11.6 Compute k largest elements in a max heap

  	  // Idea: use a second heap to store candidates using heap rule and spit out the largest ones.
  	  // reason this works is because we are only looking at largest nodes in candidate max heap so we always 
  	  // only traverse the larger ones 
  	  // solution is Time: O(klog(k))
  	  // space(O(2k) = O(k))

  	  // List<Integer> numbers = new ArrayList<>();

  	  // numbers.add(16);
  	  // numbers.add(14);
  	  // numbers.add(10);
  	  // numbers.add(8);
  	  // numbers.add(7);
  	  // numbers.add(9);
  	  // numbers.add(3);
  	  // numbers.add(2);
  	  // numbers.add(4);
  	  // numbers.add(1);

  	  // // array representation of below heap
  	  // //
  	  // //       16
  	  // //    14     10
  	  // //   8   7   9   3 
     //  //  2 4 1
 
  	  // Heap.computeKLargestElementsInMaxHeap(4, numbers);


  	// 11.7 Implement a Stack using a Heap

  	  HeapStack heap = new HeapStack();

  	  heap.push(16);
  	  heap.push(14);
  	  heap.push(10);
  	  heap.push(8);
  	  heap.push(7);
  	  heap.push(9);
  	  heap.push(3);
  	  heap.push(2);
  	  heap.push(4);
  	  heap.push(1);

  	  heap.peek();
  	  heap.pop();
  	  heap.pop();
  	  heap.pop();
  	  heap.pop();
  	  heap.pop();
  	  heap.peek();

  	  // // array representation of below heap
  	  // //
  	  // //       16
  	  // //    14     10
  	  // //   8   7   9   3 
     //  //  2 4 1
 



    // -------------------------------------


  }
}