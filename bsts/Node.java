class Node<T>{
	T data; 
	Node<T> left, right;
	Node(){
	}
	Node(T data){
		this.data = data;
	}
	// remember you have to declare types here if you want to use a strongly typed component 
	Node(T data, Node<T> left, Node<T> right){
		this.data = data;
		this.left = left;
		this.right = right;
	}
}