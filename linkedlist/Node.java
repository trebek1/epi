import java.lang.Comparable;


class Node implements Comparable<Node> {

	int value;
	Node next;

	Node(int val){
		this.value = val;
	}

	@Override 
	public int compareTo(Node that){
		// the object being compared to is less than the current Node 
		if(this.value > that.value){
			return -1;
		}
		if(this.value < that.value){
			return 1;
		}

		return 0;
	}

} 