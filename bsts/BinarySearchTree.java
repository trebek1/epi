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

		// have to cast into whatever the defined type is 
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

		// Have to cast out of generic type 
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
	@SuppressWarnings("unchecked")
	Node<Integer> getTreeNode(int data){
		Node<Integer> current = (Node<Integer>)this.root;
		while(current != null){
			if(current.data == data){
				return current;
			} else if(data < current.data){
				current = current.left;
			} else {
				current = current.right;
			}
		}
		return null;
	}

	public static boolean isTreeBST(BinarySearchTree<Node<Integer>> tree){
		Node<Integer> root = tree.root;
		if(root == null){
			return true;
		}

		Node<Integer> current = root;
		Integer max = Integer.MAX_VALUE;
		Integer min = Integer.MIN_VALUE;

		return bstCheckHelper(root, max, min);
	}

	public static boolean bstCheckHelper(Node<Integer> node, Integer max, Integer min){

		if(node == null){
			return true;
		} else if(node.data > max || node.data < min){
			return false;
		}

		return bstCheckHelper(node.left, node.data, min) && bstCheckHelper(node.right, max, node.data);
	}

	@SuppressWarnings("unchecked")
	void findFirstInstance(int number){
		Node<Integer> current = (Node<Integer>) this.root;

		Node<Integer> found = null;
		while(current != null){
			if(current.data == number){
				found = current;
				if((current.left != null && current.left.data != number) || current.left == null){
					break;
				}
				System.out.println("found it but going left!");
				current = current.left;
			} else if(current.data < number){
				current = current.right;
			} else if(current.data > number){
				current = current.left;
			}
		}

		 System.out.println("found it");
		 System.out.println(found.data);
	}

	public static void main(String[] args){	
	  // 15.1 Is the Binary Tree a BST ? 

	  // BinarySearchTree<Node<Integer>> tree = new BinarySearchTree<>();
	  // tree.createTempTree();
	  // tree.printTree();

	  // // expect true
	  // System.out.println(BinarySearchTree.isTreeBST(tree));

	  // Node<Integer> test = tree.getTreeNode(17);
	  // test.right = new Node<Integer>(25);

	  // // expect false
	  // System.out.println(BinarySearchTree.isTreeBST(tree));

	  // Notes: Instead of checking each node you can do an inorder traversal as well. This 
	  // would show a violation if ever come across a node that is smaller than the previous since 
	  // inorder traversal finds nodes from smallest to largest 
	  // if space is not a constraint and want to find node faster you can do a BFS storing nodes so that 
	  // all the nodes dont have to be visited 






	  //15.2 Find first occurrence of a key (inorder traversal order)
	  // Note: This assumes that duplicates are Okay!. Left Child is <= Node and Right Child is >= Node
	  // Do regular traversal until you find one
	  // After finding one look all the way left to see if there are any more instances else youve found it 

	  BinarySearchTree<Node<Integer>> tree = new BinarySearchTree<>();
	  tree.createTempTree();

	  Node<Integer> test = tree.getTreeNode(11);
	  test.left = new Node<Integer>(11);

	  tree.findFirstInstance(11);


	}
}




