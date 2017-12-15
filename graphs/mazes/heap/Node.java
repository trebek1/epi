class Node implements Comparable<Node> {
	int value;

	Node(int val){
		this.value = val;
	}

	@Override
	public int compareTo(Node node){
		return this.value - node.value;
	}
}
