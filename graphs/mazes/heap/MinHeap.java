
import java.util.ArrayList;
import java.lang.Math;

class MinHeap {

	Node[] heap;
	int heapSize;
	int length;

	MinHeap(int[] arr){
		int len = arr.length;
		heap = new Node[len + 1];
		
		// length of the heap
		heapSize = len;

		// position of last element inserted
		length = 0;
		makeHeap(arr);
		printHeap();
		buildMinHeap();
	}

	//Heap Functions!!

	void minHeapify(int i){
		int leftIndex = 2*i;
		int rightIndex = 2*i + 1;
		int parentIndex = i;
		int smaller = parentIndex;

		// compare parent and left index
		if(range(leftIndex) && heap[parentIndex].compareTo(heap[leftIndex]) > 0){
			smaller = leftIndex;
		}
		// compare the smaller of the two with the right index
		if(range(rightIndex) && heap[smaller].compareTo(heap[rightIndex]) > 0){
			smaller = rightIndex;
		}
		// if either was smaller, swap with parent and run minHeapify on new swapped child
		if(smaller != parentIndex){
			swap(parentIndex, smaller);
			minHeapify(smaller);
		}
	}

	void buildMinHeap(){
		System.out.println("Making ordered...");
		for(int i = (int)(Math.floor(heapSize/2)); i > 0; i--){
			minHeapify(i);
		}
		System.out.println("Heap after Heapifying!");
		printHeap();
	}

	// Heap Utility Functions!!!!

	// Assumes that L and R children are min Heaps but parent might not be in right spot 
	// i is an index

	boolean range(int i){
		return i >= 1 && i <= length;
	}

	void swap(int i, int j){
		Node left = heap[i];
		heap[i] = heap[j];
		heap[j] = left;
	}

	void makeHeap(int[] arr){
		for(int i = 0; i < arr.length; i++){
			Node node = new Node(arr[i]);
			heap[++length] = node;
		}
		System.out.println("Heap has been created");
	}

	void printHeap(){
		for(int i = 0; i < length; i++){
			System.out.println(heap[i + 1].value);
		}
	}

	int parentIndex(int i){
		int parentIndex = (int)(Math.floor(i/2));

		if(parentIndex <= 0){
			return 0;
		}

		return parentIndex;
	}

	private void resize(int capacity){
		Node[] newHeap = new Node[capacity];
		for(int i = 1; i < heap.length; i++){
			newHeap[i] = heap[i];
		}
		heap = newHeap;
	}

	// Min Priority Queue Functions!

	Node minimum(){
		System.out.println(heap[1].value);
		return heap[1];
	}

	Node extractMin(){
		swap(1, length);
		Node min = heap[length];
		// shrink heap
		heapSize--;
		length--;
		minHeapify(1);
		System.out.println("This is the new Heap!");
		printHeap();
		return min;
	}

	void insert(int num){
		Node newNode = new Node(Integer.MAX_VALUE);
		length++;
		if(length == heapSize -1){
			// double size of heap and copy over values 
			resize(heap.length * 2);
		}

		heap[length] = newNode;

		decreaseKey(length, num);


	}

	void decreaseKey(int index, int newValue){
		if(newValue > heap[index].value){
			System.out.println("Invalid Key Decrease Request");
			return;
		}

		heap[index].value = newValue;

		while(index > 1 && heap[parentIndex(index)].value > heap[index].value){
			swap(index, parentIndex(index));
			index = parentIndex(index);
		}

		System.out.println("Heap after decreasing key!!! ");
		printHeap();
	}

	public static void main(String[] args){
		MinHeap m = new MinHeap(new int[] {4, 1, 3, 2, 16, 9, 10, 14, 8, 7});
		m.extractMin();
		m.decreaseKey(m.length, 5);
		m.insert(1);
		// initial order 
		// 4 1 3 2 16 9 10 14 8 7
	}
}
