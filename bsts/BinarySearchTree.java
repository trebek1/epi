import java.util.Queue;
import java.util.LinkedList;

class BinarySearchTree<T>{
	T root;
	BinarySearchTree(){
		this.root = null;
	}
	BinarySearchTree(T root){
		this.root = root;
	}
	@SuppressWarnings("unchecked")
	void createTempTree(){

		BinarySearchTree<Node<Integer>> tree = new BinarySearchTree<>();

		//          19
		//    7             43
		//   3  11      23      47 
		// 2  5    17       37     53 
		//       13       29  41 
		//                  31

		Node<Integer> node1 = new Node<>(19);
		Node<Integer> node2 = new Node<>(7);
		Node<Integer> node3 = new Node<>(43);
		Node<Integer> node4 = new Node<>(3);
		Node<Integer> node5 = new Node<>(11);
		Node<Integer> node6 = new Node<>(23);
		Node<Integer> node7 = new Node<>(47);
		Node<Integer> node8 = new Node<>(2);
		Node<Integer> node9 = new Node<>(5);
		Node<Integer> node10 = new Node<>(17);
		Node<Integer> node11 = new Node<>(37);
		Node<Integer> node12 = new Node<>(53);
		Node<Integer> node13 = new Node<>(13);
		Node<Integer> node14 = new Node<>(29);
		Node<Integer> node15 = new Node<>(41);
		Node<Integer> node16 = new Node<>(31);

		tree.add(node1);
		tree.add(node2);
		tree.add(node3);
		tree.add(node4);
		tree.add(node5);
		tree.add(node6);
		tree.add(node7);
		tree.add(node8);
		tree.add(node9);
		tree.add(node10);
		tree.add(node11);
		tree.add(node12);
		tree.add(node13);
		tree.add(node14);
		tree.add(node15);
		tree.add(node16);

		this.root = (T)(tree.root);

		System.out.println("tree created");

	}
	@SuppressWarnings("unchecked")
	void add(Node<Integer> node){
		Node<Integer> current = (Node<Integer>)this.root;
		if(current == null){
			this.root = (T)node;
			return;
		}
		while(current != null){
			if(node.data < current.data){
				if(current.left != null){
					current = current.left;
					continue;	
				} else {
					current.left = node;
					return;
				}
			} else if(node.data > current.data){
				if(current.right != null){
					current = current.right;
					continue;	
				} else {
					current.right = node;
					return;
				}
			}
		}
	}
	@SuppressWarnings("unchecked")
	void printTree(){
		Queue<Node<Integer>> q = new LinkedList<>();
		Node<Integer> current = (Node<Integer>)this.root;
		q.add(current);
		while(!q.isEmpty()){
			current = q.poll();
			System.out.println(current.data);
			if(current.left != null){
				q.add(current.left);
			}
			if(current.right != null){
				q.add(current.right);
			}
		}
	}

	public static void main(String[] args){	
	  // 15.1 Is the Binary Tree a BST ? 
	  BinarySearchTree<Node<Integer>> tree = new BinarySearchTree<>();
	  tree.createTempTree();
	  tree.printTree();
	  

	}
}