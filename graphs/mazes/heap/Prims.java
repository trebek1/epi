import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

class Prims {
  EdgeWeightedGraph G;

  MinHeap pq;
  Queue<Edge> mst;
  private boolean[] marked;
  double weight;

  Prims(EdgeWeightedGraph G){
    this.G = G;
    this.weight = 0.0;

    ArrayList<Edge> edges = G.edges();
    
    // This can be used if you want to add all the edges to the PQ first 
    // Edge[] arr = edges.toArray(new Edge[G.E()]);
    
    mst = new LinkedList<>();
    pq = new MinHeap();

    // 0 through V - 1 
    marked = new boolean[G.V];

    // pq.heapSort();

    System.out.println("....MST?....");
    findMST();
  }

  void findMST(){

    // while our MST doesnt have all the vertices on it 
    // and there are still elements on the priority queue 

    while(mst.size() < G.V - 1){
      
      visit(G, 0);
      Edge e = pq.extractMin();
      int either = e.either();
      int other = e.other(e.either());
      
      if(marked[either] && marked[other]){
        continue;
      }
      
      mst.add(e);
      weight += e.weight; 

      if (!marked[either]) visit(G, either);
      if (!marked[other]) visit(G, other);

    }

    System.out.println("MST Found");
    printMST();
  }

  void printMST(){
    Edge e = mst.poll();

    while(e != null){
      System.out.println(e.getV() + " ---> " + e.getW() + " weight --> " + e.weight);
      e = mst.poll();
    }
  }

  void visit(EdgeWeightedGraph G, int v){
    marked[v] = true;
    for(Edge e: G.adj(v)){
      if(!marked[e.other(v)]){
        pq.insert(e);
      }
    }
  }

  // all edges that create MST
  Iterable<Edge> edges(){
    return mst; 
  }

  // total weight of the mst
  double weight(){
    return weight;
  }

  public static void main(String[] args){
    EdgeWeightedGraph g = new EdgeWeightedGraph(args[0]);
    Prims prims = new Prims(g);
  }
}