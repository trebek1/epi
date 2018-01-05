import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

class Heap {


  public static List<Integer> mergeSortedFiles(List<List<ArrayEntry>> sortedArrays){
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
      		pq.add(sortedArrays.get(i).get(0));
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
      	List<ArrayEntry> listHeadCameFrom = sortedArrays.get(headEntry.arrayId);
      	// get head value from heads array (init to 1 for all arrays with data)
      	int arrayIndexOfLastHead = heads.get(headEntry.arrayId);

      	// as long as you havent used all the elements from the array this will be true
      	if(arrayIndexOfLastHead < listHeadCameFrom.size()){
      		// add the next entry of samllestArray into heap

      		// since indices start at zero, getting the current one and adding will work here and forward 
      		// as we add one to it in the next step
      		pq.add(listHeadCameFrom.get(arrayIndexOfLastHead));
      		heads.set(headEntry.arrayId, heads.get(headEntry.arrayId) + 1); 

      	}
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

      List<ArrayEntry> l1 = new ArrayList<>();
      l1.add(new ArrayEntry(3,0));
      l1.add(new ArrayEntry(5,0));
      l1.add(new ArrayEntry(7,0));
      List<ArrayEntry> l2 = new ArrayList<>();
      l2.add(new ArrayEntry(0,1));
      l2.add(new ArrayEntry(6,1));
      List<ArrayEntry> l3 = new ArrayList<>();
      l3.add(new ArrayEntry(0,2));
      l3.add(new ArrayEntry(6,2));
      l3.add(new ArrayEntry(28,2));

      List<List<ArrayEntry>> sortedArrays = new ArrayList<>();
      sortedArrays.add(l1);
      sortedArrays.add(l2);
      sortedArrays.add(l3);

      Heap.mergeSortedFiles(sortedArrays);

      // java review! 
      // supress uncheked cast exception
      // @SuppressWarnings("unchecked")
      // ArrayList<Integer> l4 = (ArrayList)l3;




    // -------------------------------------


  }
}