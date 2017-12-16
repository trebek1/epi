import java.util.ArrayList;

class Prims {
  EdgeWeightedGraph G;

  MinHeap pq;

  Prims(EdgeWeightedGraph G){
    this.G = G;
    System.out.println("Running prims with a graph");
    System.out.println("Vertices " + G.V());
    System.out.println("Edges " + G.E());

    System.out.println("Initial Rendering");
    ArrayList<Edge> edges = G.edges();
    System.out.println("edges size " + edges.size());
    Edge[] arr = edges.toArray(new Edge[G.E()]);
    System.out.println("This i arr size " + arr.length);
    pq = new MinHeap(arr);

    // for(Edge e : edges){
    //   e.toString();
    // }

    System.out.println("....MST?....");



  }

  // all edges that create MST
  // Iterable<Edge> edges(){
    
  // }

  // // total weight of the mst
  // double weight(){

  // }

  public static void main(String[] args){
    EdgeWeightedGraph g = new EdgeWeightedGraph(args[0]);
    Prims prims = new Prims(g);
  }
}