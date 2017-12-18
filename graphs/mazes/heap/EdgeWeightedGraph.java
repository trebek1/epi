import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
  
class EdgeWeightedGraph {
  int V, E;
  ArrayList<Edge>[] adj;


  // constructor for EdgeWeightedGraph
  // @SuppressWarnings("unchecked")
  // EdgeWeightedGraph(int v){
  //   this.V = v;
  //   this.E = 0;

  //   // java handles types after runtime at the compiler level
  //   // so when this is created you dont include the type until the cast
  //   adj = (ArrayList<Edge>[]) new ArrayList[V]; 

  //   for(int i = 0; i < v; i++){
  //     adj[i] = new ArrayList<Edge>();
  //   }
  // }

  @SuppressWarnings({"unchecked", "rawtypes"})
  EdgeWeightedGraph(String f){
  	try{
  		File file = new File(f);
  		Scanner sc = new Scanner(file);
  		this.V = sc.nextInt();
	    this.E = sc.nextInt();

	    // java handles types after runtime at the compiler level
	    // so when this is created you dont include the type until the cast
	    adj = (ArrayList<Edge>[]) new ArrayList[V]; 

	    for(int i = 0; i < V; i++){
	      adj[i] = new ArrayList<Edge>();
	    }

	    // Constructor to add edges from a file
	    while(sc.hasNext()){
	    	int v = sc.nextInt();
	    	int w = sc.nextInt();
	    	double weight = sc.nextDouble();
	    	Edge e = new Edge(v, w, weight);
	    	addEdge(e);
	  	}

	  	sc.close();
  	} catch(Exception e){
  		System.out.println("error " + e);
  	}
  }

  // Add an edge to the graph
  void addEdge(Edge e){
    adj[e.either()].add(e);
    adj[e.other(e.either())].add(e);
  }

  // edges incident to v
  Iterable<Edge> adj(int v){
    return adj[v];
  }
  // // all edges in graph
  ArrayList<Edge> edges(){
    
    // Idea here is that only larger vertex will be returned so duplicates aren't returned
    ArrayList<Edge> listFinal = new ArrayList<>();
    for(int i = 0; i < V; i++){
      for(Edge e : adj[i]){
        if(e.other(i) > i){
          listFinal.add(e);
        }
      }
    }

    return listFinal;

  }
  
  // vertices 
  int V(){
    return V;
  }

  // edges 
  int E(){
    return E;
  }
}




