import java.lang.Comparable;

class Node implements Comparable<Node> {

  int value;
  Node right, left, p;

  // CLRS wants a poninter to the parent node 
  Node(int val){
  value = val; 
    left = null;
    right = null;
    p = null;
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
