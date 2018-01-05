import java.util.PriorityQueue;
import java.util.Comparator;
// Stack made from a heap API 

class HeapStack {

  	public PriorityQueue<HeapStackItem> pq = new PriorityQueue<HeapStackItem>(16, Compare.COMPARE_HEAPSTACKITEMS);
  	public Integer idx = 0;

	void push(Integer value){
		pq.add(new HeapStackItem(idx++, value));
	}

	void pop(){
		System.out.println("removed " + pq.poll().value); 
	}

	void peek(){
		System.out.println("Look " + pq.peek().value);
	}
}