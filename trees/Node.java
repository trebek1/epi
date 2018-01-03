import java.lang.Comparable;

class Node implements Comparable<Node> {

  int value;
  Node right, left, p, levelNext;
  int nodes;


  // CLRS wants pointer to parent node 
  // nodes is number of nodes in subtree for specific problem  
  Node(int val, int nodes){
    value = val; 
    left = null;
    right = null;
    p = null;
    levelNext = null;
    this.nodes = nodes;
  }

  Node(int val){
    value = val; 
    left = null;
    right = null;
    p = null;
    levelNext = null;
  }

  Node(int val, Node left, Node right){
    value = val; 
    this.left = left;
    this.right = right;
    p = null;
    levelNext = null;
  }

  @Override 
  public int compareTo(Node that){
    if(this.value > that.value){
      return -1;
    }
    if(that.value > this.value){
      return 1;
    }
    return 0;
  }
}
