import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

class BinaryTree {

  Node root;
  Queue<Node> q = new LinkedList<>();
  static Integer currentIndex = 0;

  BinaryTree(Node node){
    root = node;
  }

  BinaryTree(int value){
    Node node = new Node(value);
    root = node;
  }

  BinaryTree(){
    root = null;
  }

  TreeInfo lCAHelperFunction(Node root, Node node1, Node node2){
    if(root == null){
      return new TreeInfo(0, null);
    }

    TreeInfo left = lCAHelperFunction(root.left, node1, node2);
    if(left.numberOfNodes == 2){
      return left;
    }
    TreeInfo right = lCAHelperFunction(root.right, node1, node2); 
    if(right.numberOfNodes == 2){
      return right;
    }

    int numberOfNodes = left.numberOfNodes + right.numberOfNodes + (root == node1 ? 1 : 0) + (root == node2 ? 1 : 0);
    return new TreeInfo(numberOfNodes, numberOfNodes == 2 ? root : null);
  }

  int getDepth(Node n1){
    int level = 0; 

    while(n1.p != null){
      n1 = n1.p;
      level++;
    }
    return level; 
  }

  int sumPath(int sum, Node node){
    if(node == null){
      return 0;
    }

    // leaf
    if(node.left == null && node.right == null){
      return sum;
    }

    int newSum = 2 * sum + node.value;

    return sumPath(newSum, node.left) + sumPath(newSum, node.right);
  }

  String sumRootToLeafPaths(){
    return Integer.toBinaryString(sumPath(0, this.root));
  }

  Node lowestCommonAncestorWithParentPointer(int n1, int n2){
      Node node1 = treeSearch(n1);
      Node node2 = treeSearch(n2);
      if(node1 == null || node2 == null){
        System.out.println("At least one of the nodes is not in the tree");
        return null;
      }

      int depth1 = getDepth(node1);
      int depth2 = getDepth(node2); 

      if(depth1 > depth2){
        while(depth1 > depth2){
          node1 = node1.p;
          depth1--;
        }
      } else if(depth2 > depth1){
        while(depth2 > depth1){
          node2 = node2.p;
          depth2--;
        }
      }

      while(node1 != node2){
        node1 = node1.p;
        node2 = node2.p;
      }

      return node1;
  }

  Node lowestCommonAncestor(int n1, int n2){
      Node node1 = treeSearch(n1);
      Node node2 = treeSearch(n2);
      if(node1 == null || node2 == null){
        System.out.println("At least one of the nodes is not in the tree");
        return null;
      }
      return lCAHelperFunction(this.root, node1, node2).ancestor;
  }

  boolean isNodeSymmetric(Node left, Node right){
    
    // if both are null then they are symmetric, base case 
    if(left == null && right == null){
      return true;
      // if both are not...
    } else if(left != null && right != null){
      return left.value == right.value && isNodeSymmetric(left.right, right.left) && isNodeSymmetric(left.left, right.right);
    }

    // if one is null but one is not 
    return false; 
  }

  boolean isSymmetric(){
    return isNodeSymmetric(this.root.left, this.root.right);
  }

  int isNodeBalanced(Node node){
    if(node == null){
      return 0;
    }

    int leftH = isNodeBalanced(node.left);
    if(leftH == -1){
      return -1;
    }
    int rightH = isNodeBalanced(node.right);
    if(rightH == -1){
      return -1;
    }
    int diff = leftH - rightH;
    if(Math.abs(diff) > 1){
      return -1;
    }

    return 1 + Math.max(leftH, rightH);
  }

  boolean isBalanced(){
    
    // do a postorder traversal just returning the number for each node.
    // if the number is -1 then the tree is not balanced somewhere
    // O(N) time complexity. O(H) space complexity since call stack wont be more than height of tree

    int res = isNodeBalanced(this.root);

    if(res > 0){
      return true;
    }
    return false;
  }

  void add(int value){
    if(root == null){
      Node node = new Node(value);
      root = node;
      return;
    }
    Node next = new Node(value);
    Node current = this.root;
    while(true){
      if(value > current.value){
        if(current.right != null){
          current = current.right;
        } else {
          next.p = current;
          current.right = next;
          return;
        }
      } else if(value < current.value){
        if(current.left != null){
          current = current.left;
        } else {
          next.p = current;
          current.left = next;
          return;
        }
      } else {
        System.out.println("Error: Nodes need to be distinct for BST");
        return;
      }
    }
  }

  void add(Node node){
    if(root == null){
      root = node;
      return;
    }

    Node current = this.root;
    while(true){
      if(node.value > current.value){
        if(current.right != null){
          current = current.right;
        } else {
          node.p = current;
          current.right = node;
          return;
        }
      } else if(node.value < current.value){
        if(current.left != null){
          current = current.left;
        } else {
          node.p = current;
          current.left = node;
          return;
        }
      } else {
        System.out.println("Error: Nodes need to be distinct for BST");
        return;
      }
    }
  }

  void inorderTraversalIter(){
    // set root to current and create a stack 
    Node current = this.root;
    Stack<Node> s = new Stack<>();

    // start the loop 
    while(true){

      // test current 
      // if not null then add to the stack 
      // set current to left 
      
      while(current != null){
        s.add(current);
        current = current.left;
      }

      if(s.isEmpty()){
        return;
      }

      current = s.pop(); 
      System.out.println(current.value);
      current = current.right;
    }
  }

  Node addRecursive(Node node, Node current){
      if(root == null){
        root = node;
        return null;
      }

      if(node.value > current.value){
        if(current.right == null){
          current.right = node;
          return node;
        } else {
          return addRecursive(node, current.right);
        }
      } else if(node.value < current.value){
          if(current.left == null){
          current.left = node;
          return node;
        } else {
          return addRecursive(node, current.left);
        }
      } else {
        System.out.println("node already in tree!");
        return null;
      }
  }

  // Iterative Level Order Traversal of the Tree 
  void printTree(){
    if(this.root == null){
      System.out.println("Tree is empty!");
      return;
    }

    q.add(this.root);

    while(!q.isEmpty()){
      Node node = q.poll();
      System.out.print(node.value);
      if(node.levelNext != null){
        System.out.print(" Node next ---> " + node.levelNext.value);
      }
      System.out.println();

      if(node.left != null){
        q.add(node.left);
      }
      if(node.right != null){
        q.add(node.right);
      }
    }
  }

  void inorderTraversalRecur(Node root){
    if(root == null){
      return;
    }
    inorderTraversalRecur(root.left);
    System.out.println(root.value);
    inorderTraversalRecur(root.right);
  }

  Node min(){
    Node current = this.root;
    if(current == null){
      System.out.println("Error tree is empty");
      return null;
    }
    while(current.left != null){
      current = current.left;
    }
    // System.out.println(current.value);
    return current;
  }

  Node min(Node node){
    Node current = node;
    if(current == null){
      System.out.println("Error tree is empty");
      return null;
    }
    while(current.left != null){
      current = current.left;
    }
    // System.out.println(current.value);
    return current;
  }

  Node max(){
    Node current = this.root;
    if(current == null){
      System.out.println("Error tree is empty");
      return null;
    }
    while(current.right != null){
      current = current.right;
    }
    System.out.println(current.value);
    return current;
  }

  Node max(Node node){
    Node current = node;
    if(current == null){
      System.out.println("Error tree is empty");
      return null;
    }
    while(current.right != null){
      current = current.right;
    }
    System.out.println(current.value);
    return current;
  }

  Node treeSearch(int val){
    Node current = root;
    if(current == null){
      System.out.println("Tree is empty");
      return null;
    }
    while(true){
      if(current == null){
        System.out.println(val + " is not in the tree");
        return null;
      }
      if(current.value > val){
        current = current.left;
      } else if(current.value < val){
        current = current.right;
      } else {
        // System.out.println("found " + val);
        return current;
      }
    }  
  }

  Node successor(int val){
    Node node = this.treeSearch(val);
    Node successor = null;

    if(node == null){
      System.out.println("Node is not in the tree to have a successor");
      return successor;
    }
    if(node.right != null){
      successor = this.min(node.right);
      System.out.println("Successor is " + successor.value);
      return successor;
    }
    while(node.p.left != node){
      node = node.p;
      if(node.value == this.root.value){
        System.out.println("There is no successor to this node");
        return null;
      }
    }
    successor = node.p;
    System.out.println("Successor is " + successor.value);
    return successor;
  }

  Node predecessor(int val){
    Node node = this.treeSearch(val);
    Node successor = null;

    if(node == null){
      System.out.println("Node is not in the tree to have a predecessor");
      return successor;
    }
    if(node.left != null){
      successor = node.left;
      System.out.println("Predecessor is " + successor.value);
      return successor;
    }
    while(node.p.right != node){
      node = node.p;
      if(node.value == this.root.value){
        System.out.println("There is no predecessor to this node");
        return null;
      }
    }
    successor = node.p;
    System.out.println("Predecessor is " + successor.value);
    return successor;
  }


  void deleteNode(int val){
    Node node = this.treeSearch(val);
    boolean left = node.left != null; 
    boolean right = node.right != null;

    // case for having no children and using a pointer to the parent 
    if(!left && !right){
      if(node.p.left == node){
        node.p.left = null;
      } else if(node.p.right == node){
        node.p.right = null;
      }
    } else if((left || right) && !(left && right)){
      // this case if for having one child 
      if(left){
        node.p.left = node.left;
      } else {
        node.p.right = node.right;
      }
    } else {
      // this case if for having two children
      Node min = min(node.right);
      node.value = min.value;
      if(min.p.left == min){
        min.p.left = null;
      } else if(min.p.right == min){
        min.p.right = null;
      }
    }

  // case 1: No children: Modify parent to point to null
  // case 2: One child: "Splice out node so that parent points to child"
  // case 3: Two Children: 

  // find a minimum value in the right subtree;
  // replace value of the node to be removed with found minimum. Now, right subtree contains a duplicate!
  // apply remove to the right subtree to remove a duplicate.
  // Notice, that the node with minimum value has no left child and, therefore, it's removal may result in first or second cases only.
  
  }

  boolean sumHelper(int num, Node current, int sum){
    if(current == null){
      return false;
    }

    // at a leaf
    if(current.left == null && current.right == null){
      return (sum + current.value) == num;
    }

    return sumHelper(num, current.left, sum + current.value) || sumHelper(num, current.right, sum + current.value);

  }


  boolean hasSum(int num){
    return sumHelper(num, this.root, 0);
  }


  // root has all nodes in subtree + 1 including itself 
  // left has left child number of nodes + 1 including self 

  // if in left half of nodes go left 
  // look left until in right half or find exact node where it is in


  int nthInorderNode(int k){
    Node current = this.root;
    if(current == null){
      return 0;
    }

    int left;

    while(current != null){
      left = (current.left != null) ? current.left.nodes : 0;

      if(left + 1 == k){
        return current.value;
      } else if (left + 1 > k) {
        current = current.left;
      } else {
        k -= (left + 1);
        current = current.right;
      }
    }
    return 0;
  }

  int succ(int num){
    Node node = treeSearch(num);
    if(node == null){
      return -1;
    }

    // if there is a right child --> smallest of the right children
    // if no right child then look up tree to find what the node is a left child of

    if(node.right != null){
      return min(node.right).value;
    } else {
      while(node != node.p.left){
        node = node.p;
      }
      return node.p.value;
    }
  }

  void efficientInorder(){
    Node current = this.root;
    Node prev = null;
    Node next = null;

    if(current == null){
      return;
    }

    while(current != null){
      // came down tree
      if(current.p == prev){
        if(current.left != null){
          next = current.left;
        } else {
          System.out.println(current.value);
          next = (current.right != null) ? current.right : current.p;
        }
       // came up from left
      } else if(current.left == prev){
        System.out.println(current.value);
        next = (current.right != null) ? current.right : current.p;

        // came up from right
      } else {  
        next = current.p;
      }

      prev = current;
      current = next;
    }
    return;
  }

  Node treeFromTraversalData(List<Integer> preorder, List<Integer> inorder){

    Map<Integer, Integer> indexToInorderNode = new HashMap<>();

    for(int i = 0; i < inorder.size(); i++){
      indexToInorderNode.put(inorder.get(i), i);
    }

    // map will look like: 
    // 1: 0
    // 6: 1 
    // 11: 2
    // 15: 3
    // 25: 4
    // ...

    return binaryTreeFromPreorderInorderHelper(preorder, 0, preorder.size(), 0, inorder.size(), indexToInorderNode);

  }

  private static Node binaryTreeFromPreorderInorderHelper(List<Integer> preorder, int preorderStart, int preorderEnd, int inorderStart, int inorderEnd, Map<Integer, Integer> indexToInorderNode){
    // preorder, preorder start, end, inorder start, end, mapToInorder

    // inorder 
    // 1 
    // 6
    // 11
    // 15
    // 25

    //preorder
    // 25
    // 11
    // 6
    // 1 

    // have to have lists left to iterate over

    if(preorderEnd <= preorderStart || inorderEnd <= inorderStart){
      return null;
    }
    // get the preorder node value, look up in table and return index
    int rootInorderIdx = indexToInorderNode.get(preorder.get(preorderStart));
    // preorder.get(0) ==> 25 --> (25 --> 4)

    int leftSubtreeSize = rootInorderIdx - inorderStart;
    // left subtree size will be the index looked up - start of the subtree, zero for left most tree, + 1 for rt
    // 4 - 0 = 4

    //       25
    //    11.    40
    //  6.  15. 35. 55
    // 1.      33

    int nextPreorderPosition = preorderStart + 1;

    // next preorder position will always be current + 1 for left and current + 1 + leftSubtreeSize for rt 
    // preorder end is nexPreorderPosition + left SubtreeSize for left subtree and preorder end for rt (current node for left, last node for right)
    // inorder index goes from zero(inorderStart) on left tree to looked up value + 1 in rt
    // inorder end is looked up value in left or end in rt 

    return new Node(preorder.get(preorderStart),
      binaryTreeFromPreorderInorderHelper(preorder, nextPreorderPosition, nextPreorderPosition + leftSubtreeSize, inorderStart, rootInorderIdx, indexToInorderNode),
      binaryTreeFromPreorderInorderHelper(preorder, nextPreorderPosition + leftSubtreeSize, preorderEnd, rootInorderIdx + 1, inorderEnd, indexToInorderNode));

    // node.value --> preorder(0)
    // node.left --> preorder, 1, 1 + 4, 0, 4
    // node.left --> preorder, 1, 5, 0, 4

    // left subtree 
    // preorder start 1
    // preorder end   5
    // inorder start  0
    // inorder end    4 

    // node.right --> preorder, 1 + 4, 9, 4 + 1, 9
    // node.right --> preorder, 5, 9, 5, 9

    // right subtree 
    // preorder start 5
    // preorder end   9
    // inorder start  5
    // inorder end    9  

    // index    0  1  2  3  4  5  6  7  8 
    // inorder  1  6  11 15 25 33 35 40 55 
    // preorder 25 11 6  1. 15 40 35 33 55

  }

  Node treeFromPreorderMarkers(List<Integer> preorder){
    currentIndex = 0;
    return traverse(preorder);
  }

  private static Node traverse(List<Integer> preorder){

    Integer value = preorder.get(currentIndex);
    currentIndex++;
    if(value == -1){
      return null;
    }

    Node leftNode = traverse(preorder);
    Node rightNode = traverse(preorder);
    return new Node(value, leftNode, rightNode);
  }

  private void getLeaves(Node current, List<Node> leaves){
      
      if(current == null){
        return;
      }
    
      if(current.left == null && current.right == null){
        leaves.add(current);
      }  
      getLeaves(current.left, leaves);
      getLeaves(current.right, leaves);
  }

  List<Node> listOfBinaryTreeLeaves(){
    List<Node> leaves = new LinkedList<Node>();
    getLeaves(this.root, leaves);
    return leaves;

  }

  boolean isLeaf(Node node){
    return (node.left == null) && (node.right == null);
  }

  List<Node> leftBoundary(Node node, boolean side){
    List<Node> nodes = new LinkedList<Node>();
    if(node != null){
      if(side == true || isLeaf(node)){
      nodes.add(node);
      }
      nodes.addAll(leftBoundary(node.left, side));
      nodes.addAll(leftBoundary(node.right, side && node.left == null));  
    }
    
    return nodes;
  }

  List<Node> rightBoundary(Node node, boolean side){
    List<Node> nodes = new LinkedList<Node>();
    if(node != null){
      if(side == true || isLeaf(node)){
        nodes.add(node);
      }
      nodes.addAll(rightBoundary(node.left, side && node.right == null));
      nodes.addAll(rightBoundary(node.right, side));
    }
    return nodes;
  }


  List<Node> boundaryOfTree(){
    List<Node> boundary = new LinkedList<Node>();
    if(this.root != null){
      boundary.add(this.root);
      boundary.addAll(leftBoundary(this.root.left, true));
      boundary.addAll(rightBoundary(this.root.right, true));
    }
    return boundary;
  }

  // add level next to a perfect binary tree
  public void addLevelNext(){
    Node current = this.root;
    addLevelNextHelper(current);
  }

  public void addLevelNextHelper(Node node){
    if(node == null){
      return;
    }
    // at root
    if(node.p == null){
      addLevelNextHelper(node.left);
      addLevelNextHelper(node.right);
      return;
    }

    if(isLeftChild(node)){
      node.levelNext = node.p.right;
    } else {
      if(node.p.levelNext != null){
        node.levelNext = node.p.levelNext.left;
      }
    }
    addLevelNextHelper(node.left);
    addLevelNextHelper(node.right);
  }

  public void addLevelNextNoParentPointer(){
    Node start = this.root;
    while(start != null && start.left != null){
      populateNextLevel(start);
      start = start.left;
    }

  }

  private static void populateNextLevel(Node start){
    Node iter = start;
    while(iter != null){
      iter.left.levelNext = iter.right;
      if(iter.levelNext != null){
        iter.right.levelNext = iter.levelNext.left;
      }
      iter = iter.levelNext;
    }
  }

  boolean isLeftChild(Node node){
    return node.p.left == node;
  }



  public static void main(String[] args){
    // Create Binary Tree 
    // BinaryTree tree = new BinaryTree();
    // tree.add(25);
    // tree.add(11);
    // tree.add(40);
    // tree.add(6);
    // tree.add(15);
    // tree.add(35);
    // tree.add(55);
    // tree.add(1);
    // tree.add(33);
    // tree.add(30);

    // //       25
    // //    11.    40
    // //  6.  15. 35. 55
    // // 1.      33
    


    // ----------------------------------------

    // 10.1 Is the tree balanced?
      // System.out.println(tree.isBalanced());
    // 10.2 Test if a binary tree is symmetric
      // BinaryTree tree = new BinaryTree();
      // tree.add(77);
      // tree.root.left = new Node(88);
      // tree.root.right = new Node(88);
      // tree.root.left.right = new Node(15);
      // tree.root.right.left = new Node(15);
      // tree.root.left.left = new Node(99);
      // tree.root.right.right = new Node(99);
      // tree.root.left.left.left = new Node(1);
      // tree.root.right.right.right = new Node(1);

      //       77
      //    88.    88
      //  99.  15. 15. 99
      // 1.              1.
      // System.out.println(tree.isSymmetric());
    
    // 10.3 Find LCA without a pointer to parent 
      // BinaryTree tree = new BinaryTree();  
      // tree.add(25);
      // tree.add(11);
      // tree.add(40);
      // tree.add(6);
      // tree.add(15);
      // tree.add(35);
      // tree.add(55);
      // tree.add(1);
      // tree.add(33);
      // tree.add(30);

      //       25
      //    11.    40
      //  6.  15. 35. 55
      // 1.      33

      // System.out.println(tree.lowestCommonAncestor(1, 15).value);

    // 10.4 Find LCA with Pointer to Parent 
      // BinaryTree tree = new BinaryTree();  
      // tree.add(25);
      // tree.add(11);
      // tree.add(40);
      // tree.add(6);
      // tree.add(15);
      // tree.add(35);
      // tree.add(55);
      // tree.add(1);
      // tree.add(33);
      // tree.add(30);

      //       25
      //    11.    40
      //  6.  15. 35. 55
      // 1.      33

      // System.out.println(tree.lowestCommonAncestorWithParentPointer(1,15).value);

    // 10.5 Find Sum of Root to leaf paths in binary tree

      // BinaryTree tree = new BinaryTree();
      // tree.add(1);
      // tree.root.left = new Node(0);
      // tree.root.right = new Node(0);
      // tree.root.left.left = new Node(0);
      // tree.root.left.right = new Node(1);

      // tree.root.left.right.right = new Node(1);
      // tree.root.left.right.right.left = new Node(0);
      
      // tree.root.right.left = new Node(0);
      // tree.root.right.left.right = new Node(0);
      
      // tree.root.right.left.right.left = new Node(1);
      // tree.root.right.left.right.left.right = new Node(1);
      // tree.root.right.left.right.right = new Node(0);

      // tree.root.right.right = new Node(0);

      // tree.root.right.right.right = new Node(0);

      // tree.root.left.left.left = new Node(0);
      // tree.root.left.left.right = new Node(1);

      //        1
      //    0        1 
      //   0  1     0  0 
      //  0 1   1     0  0
      //      0      1 0
      //               1
      // System.out.println(tree.sumRootToLeafPaths());

    // 10.6 Find If a specific sum exists in the tree

      // BinaryTree tree = new BinaryTree();  
      // tree.add(25);
      // tree.add(11);
      // tree.add(40);
      // tree.add(6);
      // tree.add(15);
      // tree.add(35);
      // tree.add(55);
      // tree.add(1);
      // tree.add(33);
      // tree.add(30);

      //       25
      //    11.    40
      //  6.  15. 35. 55
      // 1.      33

      // Should be false then true 
      // System.out.println(tree.hasSum(42));
      // System.out.println(tree.hasSum(43));


    // 10.7 Kth Node in an inorder traversal given more information (number of nodes in subtree)
    // Node: Includes current node in its own subtree -- this is a must. Think from root node prospective 
    // This is a divide and conqueror algorithm 

      // BinaryTree tree = new BinaryTree();  
      // tree.add(new Node(25, 9));
      // tree.add(new Node(11, 4));
      // tree.add(new Node(40, 4));
      // tree.add(new Node(6, 2));
      // tree.add(new Node(15, 1));
      // tree.add(new Node(1, 1));
      // tree.add(new Node(35, 2));
      // tree.add(new Node(55, 1));
      // tree.add(new Node(33, 1));
      
      // System.out.println(tree.nthInorderNode(9));
      // System.out.println(tree.nthInorderNode(5));
      // System.out.println(tree.nthInorderNode(4)); // 15 
      // System.out.println(tree.nthInorderNode(3)); // 11
      // System.out.println(tree.nthInorderNode(2));
      // System.out.println(tree.nthInorderNode(1));
      //       25
      //    11.    40
      //  6.  15. 35. 55
      // 1.      33

    // 10.8 Successor

      // BinaryTree tree = new BinaryTree();  
      // tree.add(25);
      // tree.add(11);
      // tree.add(40);
      // tree.add(6);
      // tree.add(15);
      // tree.add(35);
      // tree.add(55);
      // tree.add(1);
      // tree.add(33);
      // tree.add(30);

      //       25
      //    11.    40
      //  6.  15. 35. 55
      // 1.      33

      // System.out.println(tree.succ(25)); // 33
      // System.out.println(tree.succ(15)); // 25
      // System.out.println(tree.succ(6)); // 11
      // System.out.println(tree.succ(11)); // 15
      // System.out.println(tree.succ(35)); // 40

      // 10.9 Efficient Inorder

      // BinaryTree tree = new BinaryTree();  
      // tree.add(25);
      // tree.add(11);
      // tree.add(40);
      // tree.add(6);
      // tree.add(15);
      // tree.add(35);
      // tree.add(55);
      // tree.add(1);
      // tree.add(33);

      // tree.efficientInorder();


      // 10.10 Construct Tree from Traversal Data

      // 1, 6, 11, 15, 25, 33, 35, 40, 55

      // BinaryTree tree = new BinaryTree();

      // List<Integer> inorder = new ArrayList<>();
      // List<Integer> preorder = new ArrayList<>();
      // inorder.add(1);
      // inorder.add(6);
      // inorder.add(11);
      // inorder.add(15);
      // inorder.add(25);
      // inorder.add(33);
      // inorder.add(35);
      // inorder.add(40);
      // inorder.add(55);

      // //       25
      // //    11.    40
      // //  6.  15. 35. 55
      // // 1.      33

      // preorder.add(25);
      // preorder.add(11);
      // preorder.add(6);
      // preorder.add(1);
      // preorder.add(15);
      // preorder.add(40);
      // preorder.add(35);
      // preorder.add(33);
      // preorder.add(55);

      // tree.root = tree.treeFromTraversalData(preorder, inorder);
      // tree.printTree();

      // index    0  1  2  3  4  5  6  7  8 
      // inorder  1  6  11 15 25 33 35 40 55 
      // preorder 25 11 6  1. 15 40 35 33 55 

      // 10.11 Reconstruct a binary tree from a preorder traversal with markers 

      // 1, 6, 11, 15, 25, 33, 35, 40, 55

      // BinaryTree tree = new BinaryTree();
      // List<Integer> preorder = new ArrayList<>();

      // //       25
      // //    11.    40
      // //  6.  15. 35. 55
      // // 1.      33

      // preorder.add(25);
      // preorder.add(11);
      // preorder.add(6);
      // preorder.add(1);
      // preorder.add(-1);
      // preorder.add(-1);
      // preorder.add(-1);
      // preorder.add(15);
      // preorder.add(-1);
      // preorder.add(-1);
      // preorder.add(40);
      // preorder.add(35);
      // preorder.add(33);
      // preorder.add(-1);
      // preorder.add(-1);
      // preorder.add(-1);
      // preorder.add(55);
      // preorder.add(-1);
      // preorder.add(-1);

      // tree.root = tree.treeFromPreorderMarkers(preorder);
      // tree.printTree();


    // 10.12 Get List of leaves of binary tree

      // 1, 6, 11, 15, 25, 33, 35, 40, 55

      // BinaryTree tree = new BinaryTree();
      // tree.add(25);
      // tree.add(11);
      // tree.add(40);
      // tree.add(6);
      // tree.add(15);
      // tree.add(35);
      // tree.add(55);
      // tree.add(1);
      // tree.add(33);
      // tree.add(30);

      // //       25
      // //    11.    40
      // //  6.  15. 35. 55
      // // 1.      33
      
      // List<Node> list = tree.listOfBinaryTreeLeaves();

      // for(Node node : list){
      //   System.out.println(node.value);
      // }

    // 10.13 Get Perimeter of binary tree 

      // 1, 6, 11, 15, 25, 33, 35, 40, 55

      // BinaryTree tree = new BinaryTree();
      // tree.add(25);
      // tree.add(11);
      // tree.add(40);
      // tree.add(6);
      // tree.add(15);
      // tree.add(35);
      // tree.add(55);
      // tree.add(1);
      // tree.add(33);
      

      // // //       25
      // // //    11.    40
      // // //  6.  15. 35. 55
      // // // 1.      33
      
      // List<Node> list = tree.boundaryOfTree();

      // for(Node node : list){
      //   System.out.println(node.value);
      // }

        // 10.14 Add correct level next field given perfect binary tree

      BinaryTree tree = new BinaryTree();
      tree.add(25);
      tree.add(11);
      tree.add(40);
      tree.add(6);
      tree.add(15);
      tree.add(35);
      tree.add(55);
      tree.add(1);
      tree.add(7);
      tree.add(14);
      tree.add(16);
      tree.add(33);
      tree.add(37);
      tree.add(50);
      tree.add(60);
      

    //        25
    //    11       40
    //  6   15    35    55
    // 1 7 14 16 33 37 50 60
        tree.addLevelNextNoParentPointer();
       // tree.addLevelNext();
       tree.printTree();

      // for(Node node : list){
      //   System.out.println(node.value);
      // }


    // ----------------------------------------




    // tree.add(40);
    // tree.add(35);
    // tree.add(36);
    // tree.add(37);
    // tree.add(38);
    // tree.add(39);

    // tree.successor(39);

    // tree.add(54);
    // tree.printTree();
    // tree.deleteNode(40);
    // System.out.println("After Delete");
    // tree.printTree();    

    // expect to get 25 11 40 6 15 35 55 1 33 
    // tree.printTree();

    // iterative dfs inorder traversal Left, Head, Right
    // expect 1, 6, 11, 15, 25, 33, 35, 40, 55
    // tree.inorderTraversalIter();
    // tree.inorderTraversalRecur(tree.root);
    // System.out.println("Max");
    // tree.max();
    // System.out.println("Min");
    // tree.min();

    // tree.treeSearch(6);
    // tree.treeSearch(35);
    // tree.treeSearch(77);

    // Find Successor Nodes 
    //33
    // tree.successor(25);
    // // 35
    // tree.successor(33);
    
    // // 6
    // tree.successor(1);

    // // 55
    // tree.successor(40);

    // // last node
    // tree.successor(55);

    // // error node 
    // tree.successor(1337);

    // Find Predecessor Nodes 
    // 25
    // tree.predecessor(33);
    
    // // node
    // tree.predecessor(1);

    // // 35
    // tree.predecessor(40);

    // // last node
    // tree.predecessor(55);

    // // error node 
    // tree.predecessor(1337);


  }

}
