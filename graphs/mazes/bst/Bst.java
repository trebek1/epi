import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

class Bst {
  Node head;

  Bst(int val){
    Node node = new Node(val);
    head = node;
  }

  void add(int val){
    Node node = new Node(val);
    Node current = head;

    if(current == null){
      head = current;
    }

    while(current != null){
      if(val > current.value){
        if(current.right == null){
        	node.parent = current;
          current.right = node;
          return;
        } else {
          current = current.right;
        }
      } else {
        if(current.left == null){
        	node.parent = current;
          current.left = node;
          return;
        } else {
          current = current.left;
        }
      }
    }
  }

  void dfsRec(Node node){
    if(node == null){
      return;
    }
    dfsRec(node.left);
    System.out.println(node.value);
    dfsRec(node.right);
  }

  void dfsIter(){
    Stack<Node> s = new Stack<>();
    Node current = head;
    while(true){
      while(current != null){
      s.push(current);
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

  void bfsIter(){
    Queue<Node> q = new LinkedList<>();

    Node current = head;

    q.add(current);

    while(!q.isEmpty()){
      current = q.poll();

      System.out.println(current.value);

      if(current.left != null){
        q.add(current.left);
      }

      if(current.right != null){
        q.add(current.right);
      }
    }
  }

  void bfsRecur(){
    Queue<Node> q = new LinkedList<>();
    Node current = head;

    q.add(current);

    recursiveBFS(q);

  }

  void recursiveBFS(Queue<Node> q){
    Node n = q.poll();

    System.out.println(n.value);
    if(n.left != null){
      q.add(n.left);
    }

    if(n.right != null){
      q.add(n.right);
    }

    if(q.isEmpty()){
      return;
    }

    recursiveBFS(q);
  }

  boolean contains(int value){
    Node current = head;
    if(current == null){
      return false;
    }

    while(true){
      if(value == current.value){
        return true;
      }
      if(value > current.value){
        if(current.right != null){
          current = current.right;
        } else {
          return false;
        }
      }
      if(value < current.value){
        if(current.left != null){
          current = current.left;
        } else {
          return false;
        }
      }
    }
  }

  // void morrisTraversal(){
  // 	Node current = head;

  // 	boolean doneLeft = false;

  // 	while(current != null){
  // 		if(doneLeft == false){
  // 			while(current.left != null){
  // 				current = current.left;
  // 			}
  // 		}
  // 		System.out.println(current.value);
  // 		doneLeft = true;

  // 		if(current.right != null){
  // 			current = current.right; 
  // 			doneLeft = false;
  // 		} else if(current.parent != null){
  // 			// case down deep right side of a tree coming up many nodes 
  // 			while(current.parent != null && current == current.parent.right){
  // 				current = current.parent;
  // 			}
  // 			// case where on rt side of tree coming all the way up to head many rt nodes 
  // 			if(current.parent == null){
  // 				return;
  // 			}
  // 			// case going from a left child that was reached coming up from right many nodes 
  // 			current = current.parent;
  // 		} else {
  // 			// if rt is null and parent is null, must be at the top!
  // 			return;
  // 		}
  // 	}
  // }

  void morrisTraversal(){
  	Node current = head;
  	boolean doneLeft = false;

  	while(current != null){

  		// got all the way left 
  		if(doneLeft != true){
  			while(current.left != null){
  				current = current.left;
  			}
  		}
  		// print value 
  		System.out.println(current.value);
  		doneLeft = true;

  		// if right go right
  		if(current.right != null){
  			current = current.right;
  			doneLeft = false;
  		} else if (current.parent != null){
  			// go up and to the left if out of rt branch 
  			while(current.parent != null && current == current.parent.right){
  				current = current.parent;
  			}
  			// if at the very top 
  			if(current.parent == null){
  				return;
  			}
  			// left side of tree coming up from a long rt branch
  			current = current.parent;

  		} else {
  			// must be at the top if nothing right or up
  			return;
  		}

  	}

  }

  public static void main(String[] args){
    Bst tree = new Bst(75);
    tree.add(73);
    tree.add(92);
    tree.add(23);
    tree.add(74);
    tree.add(15);
    tree.add(25);
    tree.add(80); 
    tree.add(101);
    tree.add(26);
    tree.add(27);
    tree.add(28);

    // tree.dfsRec(tree.head); 
    // tree.dfsIter();
    // tree.bfsIter();
    // tree.bfsRecur();

    // System.out.println("Contains " + "77 " + tree.contains(77));
    // System.out.println("Contains " + "74 " + tree.contains(74));

    tree.morrisTraversal();
  }



}
// dfs
// 15
// 23
// 25
// 73
// 74
// 75
// 80
// 92
// 101

// bfs 
// 75
// 73
// 92
// 23
// 74 
// 80
// 101
// 15
// 25