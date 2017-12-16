class Edge implements Comparable<Edge>{

  double weight;
  private int v, w;

  Edge(int v, int w, double weight){
    this.v = v; 
    this.w = w; 
    this.weight = weight;
  }

  int either(){
    return v;
  }; 
  int other(int vertex){
    if(vertex == v){
      return w;
    }
    return v;
  };
  public int compareTo(Edge that){
    if(weight < that.weight){
      return -1;
    }
    if(weight > that.weight){
      return 1;
    }
    return 0;
  };
  double weight(){
    return weight;
  };
  public String toString(){
    String str = v + " --> " + w;
    System.out.println(str);
    return str;
  };
}
