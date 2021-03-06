import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet; 
import java.util.NavigableSet;
import java.util.SortedSet;

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
	@SuppressWarnings("unchecked")
	void findNextElement(int number){
		Node<Integer> current = (Node<Integer>) this.root;
		Node<Integer> candidate = null;
		while(current != null){
			if(current.data != number){
				if(current.data > number){
					if(candidate == null || candidate.data > current.data){
						candidate = current;
					}
					current = current.left;
				} else if(current.data < number){
					current = current.right;
				}
			} else {
				System.out.println("Next: " + candidate.data);
				return;
			}
		}
	}
	@SuppressWarnings("unchecked")
	ArrayList<Node<Integer>> kLargestElements(int k){
		Node<Integer> current = (Node<Integer>) this.root;
		ArrayList<Node<Integer>> found = new ArrayList<>();
	    kLargestHelper(current, found, k);
	    return found;
	}

	void kLargestHelper(Node<Integer> current, ArrayList<Node<Integer>> found, int k){
		if(current == null){
			return;
		}
		if(found.size() < k){
			 kLargestHelper(current.right, found, k);
			if(found.size() < k){
				found.add(current);
				kLargestHelper(current.left, found, k);
			}
		}
	}

	@SuppressWarnings("unchecked")
	void findLCA(int a, int b){
		Node<Integer> current = (Node<Integer>) this.root;
		LCAHelper(current, a, b);
	}

	void LCAHelper(Node<Integer> current, int a, int b){
		if(current == null){
			System.out.println("No LCA in tree");
			return;
		}
		if(current.data == a || current.data == b){
			System.out.println("LCA is " + current.data);
			return;
		}
		int currentData = current.data;
		if(a < currentData && b < currentData){
			LCAHelper(current.left, a, b);
		} else if(a > currentData && b > currentData){
			LCAHelper(current.right, a, b);
		} else {
			System.out.println("Found LCA " + current.data);
		}
	}
	@SuppressWarnings("unchecked")
	List<Node<Integer>> inorder(){
		List<Node<Integer>> list = new LinkedList<>();
		Node<Integer> current = (Node<Integer>) this.root;
		inorderHelper(current, list);
		return list;
	}

	void inorderHelper(Node<Integer> current, List<Node<Integer>> list){
		if(current == null){
			return;
		}
		inorderHelper(current.left, list);
		list.add(current);
		inorderHelper(current.right, list);
	}
	@SuppressWarnings("unchecked")
	List<Node<Integer>> preorder(){
		List<Node<Integer>> list = new LinkedList<>();
		Node<Integer> current = (Node<Integer>) this.root;
		preorderHelper(current, list);
		return list;
	}

	void preorderHelper(Node<Integer> current, List<Node<Integer>> list){
		if(current == null){
			return;
		}
		list.add(current);
		preorderHelper(current.left, list);
		preorderHelper(current.right, list);
	}

	@SuppressWarnings("unchecked")
	List<Node<Integer>> postorder(){
		List<Node<Integer>> list = new LinkedList<>();
		Node<Integer> current = (Node<Integer>) this.root;
		postorderHelper(current, list);
		return list;
	}

	void postorderHelper(Node<Integer> current, List<Node<Integer>> list){
		if(current == null){
			return;
		}
		postorderHelper(current.left, list);
		postorderHelper(current.right, list);
		list.add(current);
	}

	static BinarySearchTree<Node<Integer>> constructTreeFromPre(List<Node<Integer>> preorder){

		Node<Integer> root = constructPreHelper(preorder, 0, preorder.size());
		BinarySearchTree<Node<Integer>> t = new BinarySearchTree<>(root);
		return t;
	}

	private static Integer rootIndex;

	static BinarySearchTree<Node<Integer>> constructTreeFromPreV2(List<Node<Integer>> preorder){
		rootIndex = 0;
		Node<Integer> root = constructPreHelperV2(preorder, Integer.MIN_VALUE, Integer.MAX_VALUE);
		BinarySearchTree<Node<Integer>> t = new BinarySearchTree<>(root);
		return t;
	}

	// left skewed --> O(n^2) right --> (O(n)) balanced --> (O(nlog(n)))
	static Node<Integer> constructPreHelper(List<Node<Integer>> preorder, int start, int end){
		if(start >= end){
			return null;
		}

		int transition = start + 1;
		// find a number bigger than the first number in preorder traversal (head)
		while(transition < end && Integer.compare(preorder.get(transition).data, preorder.get(start).data) < 0){
			transition++;
		}
		// make tree from sublists left and right
		return new Node<Integer>(   preorder.get(start).data,
									constructPreHelper(preorder, start + 1, transition), // does not include transition point
									constructPreHelper(preorder, transition, end) // uses trans point to start 
								);
	}

	// create a global index
	// if its outside the bounds return 
	// make left and right nodes recursively going through preorder nodes in order 

	static Node<Integer> constructPreHelperV2(List<Node<Integer>> preorder, int lowerBound, int upperBound){
		if(rootIndex == preorder.size()){
			return null;
		}

		Integer root = preorder.get(rootIndex).data;

		if(root < lowerBound || root > upperBound){
			System.out.println("here! " + " root " + root + " upper " + upperBound + " lower " + lowerBound);
			return null;
		}

		rootIndex++;
		// System.out.println("Going left " + lowerBound + " " + root);
		Node<Integer> left = constructPreHelperV2(preorder, lowerBound, root);
		// System.out.println("going right ? " + root + " " + upperBound);
		Node<Integer> right = constructPreHelperV2(preorder, root, upperBound);
		// System.out.println("This is root " + root);
		return new Node<Integer>(root, left, right);

	}

	// idea is that since arrays are sorted if you always replace the minimum element with the next 
	// element from its array to the end. This will be n lg(k) efficiency 
	public static int findMinDistanceSortedArrays(List<List<Integer>> sortedArrays){
		// list of heads initialized to zero for each array
		List<Integer> heads = new ArrayList<>(sortedArrays.size());

		// add a zero for each array in the arraylist 
		for(List<Integer> arr : sortedArrays){
			heads.add(0);
		}
 
		// create a set so that we can get min/max easily
		NavigableSet<ArrayData> currentHeads = new TreeSet<>();

		// add first element from each array to the set 
		for(int i = 0; i < sortedArrays.size(); i++){
			// this is the first value in each increasing array
			int value = sortedArrays.get(i).get(0);
			currentHeads.add(new ArrayData(i, value));
		}

		int result = Integer.MAX_VALUE;

		while(true){
			// get the value for this array 
			// can get biggest and smallest element come across easily here 
			result = Math.min(result, currentHeads.last().val - currentHeads.first().val);

			// index of smallest element in the array 
			int idxNextMin = currentHeads.first().idx;
			System.out.println("This is smalles idx " + idxNextMin);
			
			// increment the smallest index 
			heads.set(idxNextMin, heads.get(idxNextMin) + 1);
			for(Integer i : heads){
				System.out.println(i);
			}
			System.out.println("------");
			
			// return if some array has no remaining elements 
			if(heads.get(idxNextMin) >= sortedArrays.get(idxNextMin).size()){
				return result;
			}
			// remove the smallest 
			currentHeads.pollFirst();
			// try the next element by adding new index 
			currentHeads.add(new ArrayData(idxNextMin, sortedArrays.get(idxNextMin).get(heads.get(idxNextMin))));
		}

	}
	//O(klogk) time and O(2k) = O(k) space req 
	List<ABSqrt2> generateKSmallestNums1(int numSmallest){
		List<ABSqrt2> answer = new ArrayList<>();
		SortedSet<ABSqrt2> candidates = new TreeSet<>();
		candidates.add(new ABSqrt2(0,0));

		while(answer.size() < numSmallest){
			ABSqrt2 next = candidates.first();
			answer.add(next);
			candidates.add(new ABSqrt2(next.a + 1, next.b));
			candidates.add(new ABSqrt2(next.a, next.b + 1));
			candidates.remove(next);
		}

		for(ABSqrt2 ans : answer){
			System.out.println(ans.val);
		}

		return answer;
	}

	//O(n) time and O(n) space 
	List<ABSqrt2> generateKSmallestNums2(int numSmallest){
		List<ABSqrt2> answer = new ArrayList<>();
		answer.add(new ABSqrt2(0,0));
		int i = 0, j = 0;
		for(int n = 1; n < numSmallest; n++){

			// look at last entry where value was incremented and increment one more 
			ABSqrt2 resultPlus1 = new ABSqrt2(answer.get(i).a + 1, answer.get(i).b);
			// look at last value where factor was incremented and add one more to that 
			ABSqrt2 resultPlusFactor = new ABSqrt2(answer.get(j).a, answer.get(j).b + 1);
			// This guarantees that all the factors will be considered in order 
			
			// increment only if that is what creates the smallest 
			if(resultPlus1.val < resultPlusFactor.val){
				i++;
				answer.add(resultPlus1);
			} else if(resultPlusFactor.val < resultPlus1.val){
				j++;
				answer.add(resultPlusFactor);
			} else {
				// This is a case where both are equal
				i++;
				j++;
				answer.add(resultPlusFactor);
			}
		}
		for(ABSqrt2 ans : answer){
			System.out.println(ans.val);
		}
		return answer;
	}

	static Node<Integer> createFromListHelper(List<Integer> list){
		int size = list.size();
		int middle = size / 2;

		if(middle == 0){
			return null;
		}

		return new Node<Integer>(list.get(middle), createFromListHelper(list.subList(0, middle)), createFromListHelper(list.subList(middle, size)));
	}

	static BinarySearchTree<Node<Integer>> createBalancedBSTFromList(List<Integer> list){
		BinarySearchTree<Node<Integer>> bst = new BinarySearchTree<>();
		Node<Integer> root = createFromListHelper(list);
		bst.root = root;
		bst.printTree();
		return bst;
	}

	@SuppressWarnings("unchecked")
	void delete(int value){
		deleteHelper((Node<Integer>) this.root, value, null);
	}

	// assumes that element is in tree to delete 
	@SuppressWarnings("unchecked")
	void deleteHelper(Node<Integer> current, int value, Node<Integer> predecessor){
		// at root 
		if(current.data == value){
			// one subtree or 2 
			if(current.left == null){
				Node<Integer> rtTree = current.right;
				this.root = (T)rtTree;
				return;
			} else {
				Node<Integer> rtTree = current.right;
				Node<Integer> leftTree = current.left;
				Node<Integer> min = findMin(current.right);
				min.left = leftTree;
				this.root = (T)rtTree;
				return;
			}
		}
		Node<Integer> something;
		Node<Integer> rightMin;
		if(value < current.data){
			if(current.left.data == value){
				// no children 
				if(current.left.left == null && current.left.right == null){
					current.left = null;
					return;
				} else if(current.left.left != null && current.left.right != null){
					rightMin = findMin(current.left.right);
					rightMin.left = current.left.left;
					current.left = current.left.right;
					return;
				} else {
					something = (current.left.left == null) ? current.left.right : current.left.left;
					current.left = something;
					return;
				}
			} else {
				deleteHelper(current.left, value, current);
				return;
			}
		} else if(value > current.data){
			if(current.right.data == value){
				if(current.right.left == null && current.right.right == null){
					current.right = null;
					return;
				} else if(current.right.left != null && current.right.right != null){
					rightMin = findMin(current.right.right);
					rightMin.left = current.right.left;
					current.right = current.right.right;
					return;
				} else {
					something = (current.right.left == null) ? current.right.right : current.right.left;
					current.right = something; 
					return;
				}
			} else {
				deleteHelper(current.right, value, predecessor);
				return;
			}
		} else {
			// do nothing shouldnt get here 
		}
	}
	// if there is a rt subtree then it is min of rt subtree. 
	// if no rt subtree then it is a node that is last left child predecessor 
	// find case 2 and look and see if case 1 applies. If so, use case 2 instead of case 1
	// @SuppressWarnings("unchecked")
	// Node<Integer> findSuccessor(int val){
	// 	Node<Integer> current = (Node<Integer>) this.root;
	// 	return findSuccessorHelper(current, val, null);
	// }

	// Node<Integer> findSuccessorHelper(Node<Integer> current, int val, Node<Integer> candidate){
	// 	// must be at root
	// 	if(current.data == val){
	// 		return findMin(current.right);
	// 	}

	// 	if(val < current.data){
	// 		if(current.left == null){
	// 			return null;
	// 		}
	// 		if(current.left.data != val){
	// 			return findSuccessorHelper(current.left, val, current.left);
	// 		} else {
	// 			if(current.left.right == null){
	// 				return candidate;
	// 			} else {
	// 				return findMin(current.left.right);
	// 			}
	// 		}

	// 	} else {
	// 		if(current.right == null){
	// 			return null;
	// 		}
	// 		if(current.right.data != val){
	// 			return findSuccessorHelper(current.right, val, candidate);
	// 		} else {
	// 			if(current.right.right == null){
	// 				return candidate;
	// 			} else {
	// 				return findMin(current.right.right);
	// 			}
					
	// 		}
	// 	}	
	// 	return null;
	// }

	Node<Integer> findMin(Node<Integer> current){
		if(current.left != null){
			return findMin(current.left);
		} else {
			return current;
		}
	}

	boolean totalOrder(List<Node<Integer>> nodes, Node<Integer> middle){

		Node<Integer> test1 = nodes.get(0);
		Node<Integer> test2 = nodes.get(1);

		Node<Integer> test1c = nodes.get(0);
		Node<Integer> test2c = nodes.get(1);

		// if either node is the search node then they arent proper 
		if(test1.data == middle.data || test2.data == middle.data){
			return false;
		}
		int depth = 0;
		Node<Integer> winner;
		while(true){
			if(test1 == null && test2 == null){
				return false;
			}
			if(test1 != null){
				if(test1.data > middle.data){
				test1 = test1.left;
				} else if(test1.data < middle.data){
					test1 = test1.right;
				}	
			}
			if(test2 != null){
				if(test2.data > middle.data){
				test2 = test2.left;
				} else if(test2.data < middle.data){
					test2 = test2.right;
				}	
			}
			
			depth++;
			if(test2 != null && test2.data == middle.data){
				winner = test2c;
				break;
		    } else if(test1 != null && test1.data == middle.data){
		    	winner = test1c;
				break;
		    }
		}

		Node<Integer> target = (winner.data == test1c.data) ? test2c : test1c;
		Node<Integer> current = middle;

		for(int i = 0; i < depth; i++){
			if(current.data < target.data){
				current = current.right;
			} else if(current.data > target.data){
				current = current.left;
			} else {
				return true;
			}
		}

		if(current == null || current.data != target.data){
			return false;
		}

		return true;
	}

	static void findRangeHelper(Node<Integer> node, Interval i, List<Integer> solution){

		if(node.data <= i.y && node.data >= i.x){
			solution.add(node.data);
			if(node.left != null){
				findRangeHelper(node.left, i, solution);
			}
			if(node.right != null){
				findRangeHelper(node.right, i, solution);
			}
		} else if(node.data >= i.x){
			if(node.left != null){
				findRangeHelper(node.left, i, solution);
			}
		} else {
			if(node.right != null){
				findRangeHelper(node.right, i, solution);
			}
		}
	}

	static List<Integer> findRange(BinarySearchTree<Node<Integer>> tree, Interval i){
		List<Integer> result = new ArrayList<>();
		Node<Integer> start = tree.root;
		findRangeHelper(start, i, result);

		return result;
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

	  // 15.2 Find first occurrence of a key (inorder traversal order)
		  // Note: This assumes that duplicates are Okay!. Left Child is <= Node and Right Child is >= Node
		  // Do regular traversal until you find one
		  // After finding one look all the way left to see if there are any more instances else youve found it 

		  // BinarySearchTree<Node<Integer>> tree = new BinarySearchTree<>();
		  // tree.createTempTree();

		  // Node<Integer> test = tree.getTreeNode(11);
		  // test.left = new Node<Integer>(11);

		  // tree.findFirstInstance(11);
	  // 15.3 Find the first key larger than a given value in a BST 

		  // Brute fore is to do inorder traversal and then return the next node in the traversal 

		  // idea is to find the value and look right. 
		  // if right is null then look up until you find a node that is a left child
		  // if no node exists then there is none greater 

		  // or if you dont have pointer to parent you can traverse tree and keep track of possible candidate 
		  // up to that point. Then once finding node you have the answer -- greedy solution 
		  // will implement this since nodes in BST dont have pointer to parent 

		  // This solution assumes that the node exists in the tree
		  
		  // BinarySearchTree<Node<Integer>> tree = new BinarySearchTree<>();
		  // tree.createTempTree();
		  // tree.findNextElement(31); // should be 37;
	  // 15.4 Find the K largest elements in a Binary Search Tree
		  // Brute force: Do Inorder Traversal and push onto stack then pop of K elements 
		  // do a reverse inorder traversal and print out first K elements 

		  // BinarySearchTree<Node<Integer>> tree = new BinarySearchTree<>();
		  // tree.createTempTree();
		  // ArrayList<Node<Integer>> largest = tree.kLargestElements(3);

		  // for(Node<Integer> node : largest){
		  // 	System.out.println(node.data);
		  // }

		// 15.5 Compute the LCA in a BST
		  // LCA is least common ancestor
		  // traverse until you find a node where both nodes arent in the same direction

		  // BinarySearchTree<Node<Integer>> tree = new BinarySearchTree<>();
		  // tree.createTempTree();
		  // tree.findLCA(29, 53); // should be 43
		  // tree.findLCA(13, 31); // should be 19
		  // This implementation assumes that both keys are in the tree. 
		  // This will break if you try keys that are not in the tree.
		  // tree.findLCA(1000, 31); // should be 19

		// 15.6 Reconstruct a tree from traversal data 
		  // keys MUST be distinct to reconstruct tree from traversal data (inorder || preorder || postorder)
		  // Need inorder && preorder || inorder && postorder 

			// BinarySearchTree<Node<Integer>> tree = new BinarySearchTree<>();
		    // ree.createTempTree();
			//tree.printTree();
		  	// List<Node<Integer>> postorder = tree.postorder();
			// subproblem get inorder and postorder traversals
			
			// List<Node<Integer>> preorder = tree.preorder();
		    // List<Node<Integer>> inorder = tree.inorder();
		    // List<Node<Integer>> postorder = tree.postorder();

		  	// for(Node<Integer> node : preorder){
		  	// 	System.out.println(node.data);
		  	// }

		  	// BinarySearchTree<Node<Integer>> tree2 = BinarySearchTree.constructTreeFromPreV2(preorder);
		  	// tree2.printTree();
		  	// List<Node<Integer>> listcheck = tree2.postorder();

		  	// for(Node<Integer> node : listcheck){
		  	// 	System.out.println(node.data);
		  	// }

		// 15.7 Find Closest Entries in 3 sorted arrays

			// List<Integer> arr1 = new ArrayList<>();
			// arr1.add(5);
			// arr1.add(10);
			// arr1.add(15);
			// List<Integer> arr2 = new ArrayList<>();
			// arr2.add(3);
			// arr2.add(6);
			// arr2.add(9);
			// arr2.add(12);
			// arr2.add(15);
			// List<Integer> arr3 = new ArrayList<>();
			// arr3.add(8);
			// arr3.add(16);
			// arr3.add(24);
		  
		 // BinarySearchTree<Node<Integer>> tree = new BinarySearchTree<>();
		 //    tree.createTempTree();
		 //    List<List<Integer>> lists = new ArrayList<>();
		 //    lists.add(arr1);
		 //    lists.add(arr2);
		 //    lists.add(arr3);
		 // System.out.println(BinarySearchTree.findMinDistanceSortedArrays(lists));

		// 15.8 Enumerate numbers of the form a + b * Math.sqrt(2)
		  // find the k smallest numbers in the form above 
		  // BinarySearchTree<Node<Integer>> tree = new BinarySearchTree<>();
		  //    tree.createTempTree();
		  //    tree.generateKSmallestNums2(5);
		  // System.out.println(BinarySearchTree.findMinDistanceSortedArrays(lists));

		// 15.9 The Most Visited Pages Problem 
		  // read a file a billion lines long and find the k most visited pages 
		  // each line has a page number on it (id field) identifying the page visited 
		  // idea: Hight balanced BSTs are good for incremental updates
		  // something like a hashtable to store most visited takes a lot of extra time to 
		  // update the current most visited pages 
		  // use a hash table to keep track of what is in the BST
		  // when updating delete from BST current, update then add back 
		  // for k most visited find kth node then do k calls to predecessor 
		  // O(k + logm) --> much better than iterating through entire collection of pages 
		  // logm to update the tree with a page --> worse than brute force but price we pay 
		  // example 

		  // t --> (2,t)
		  // a --> (1.a)

		  // adding another a page we look up a in HT and get (1,a). update to (2,a). 
		  // delete (1, a) from BST and input (2, a)


		// 15.10 Build a min hight BST from a sorted array (Keep the tree balanced)
		  // do it recursively making sure that the middle element spits the L and R evenly 

		  // List<Integer> list = new ArrayList<>();
		  // list.add(2);
		  // list.add(3);
		  // list.add(5);
		  // list.add(7);
		  // list.add(11);
		  // list.add(13);
		  // list.add(17);
		  // list.add(19);
		  // list.add(23);

		  // BinarySearchTree.createBalancedBSTFromList(list);
		// 15.11 Delete a node from a BST

		// BinarySearchTree<Node<Integer>> tree = new BinarySearchTree<>();
		// tree.createTempTree();
		//          19
		//    7             43
		//   3  11      23      47 
		// 2  5    17       37     53 
		//       13       29  41 
		//                  31
		// no children case
		// tree.delete(13);
		// tree.printTree();
		// System.out.println("------");
		// // one child case     
		// tree.delete(47);
		// tree.printTree();
		// System.out.println("------");
		// // two children case 
	 	// tree.delete(11);
	 	// tree.printTree();
	 	// System.out.println("------");
	    // not in tree case 
	    // will break code!
	    // tree.delete(82);
	    // System.out.println("------");
	   
	    // 15.12 Test if 3 BST Nodes are Totally Ordered (One is proper ancestor one is proper descendant)

	    // BinarySearchTree<Node<Integer>> tree = new BinarySearchTree<>();
	    // tree.createTempTree();
	    // Node<Integer> node1 = tree.getTreeNode(43);
	    // Node<Integer> node2 = tree.getTreeNode(31);
	    // Node<Integer> node1 = tree.getTreeNode(37);
	    // Node<Integer> node2 = tree.getTreeNode(47);
	    // List<Node<Integer>> nodes = new ArrayList<>();
	    // nodes.add(node1);
	    // nodes.add(node2);

	    // Node<Integer> middle = tree.getTreeNode(37);
	    // Node<Integer> middle = tree.getTreeNode(23);

	    // System.out.println(tree.totalOrder(nodes, middle));

	    // 15.13 Find Range of Numbers in Interval in BST
	    // prune tree while traversing to only visit nodes that could be in range 
	    // O(m) for middle nodes in range, O(h) for nodes on edge looking for endpoints -> O(m + h)

	 	//  BinarySearchTree<Node<Integer>> tree = new BinarySearchTree<>();
	 	//  tree.createTempTree();
		// Interval i = new Interval(7, 37);

		// List<Integer> numbersInRange = findRange(tree, i);

		// for(int k : numbersInRange){
		// 	System.out.println(k);
		// }

		// 15.14 Add Credits 
		// Large number of clients connect to a server. Client id by a string. Each client has a credit. 
		// design a data structure that: Insert, Remove, Lookup, Add-Credit-To-All, Max

		// 


	}
}





