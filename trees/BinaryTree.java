import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;

class BinaryTree {

  Node root;
  Queue<Node> q = new LinkedList<>();

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

  boolean isNodeSymmetric(Node left, Node right){
    // if both are null then they are symmetric 
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
      System.out.println(node.value);
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
    System.out.println(current.value);
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
    System.out.println(current.value);
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
        System.out.println("found " + val);
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
      BinaryTree tree = new BinaryTree();
      tree.add(77);
      tree.root.left = new Node(88);
      tree.root.right = new Node(88);
      tree.root.left.right = new Node(15);
      tree.root.right.left = new Node(15);
      tree.root.left.left = new Node(99);
      tree.root.right.right = new Node(99);
      tree.root.left.left.left = new Node(1);
      tree.root.right.right.right = new Node(1);

      //       77
      //    88.    88
      //  99.  15. 15. 99
      // 1.              1.
      System.out.println(tree.isSymmetric());

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
