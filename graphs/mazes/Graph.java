import java.util.HashMap;
import java.util.ArrayList;

class Graph {
	HashMap<String, GraphNode> vertices;
	final Integer V;
	int E = 0;

	Graph(int size){
		vertices = new HashMap<>();
		V = size;
	}

	void addVertex(String sym){
		// if symbol is not in table 
		vertices.put(sym, new GraphNode(sym));
		System.out.println("vertex " + sym + " has been added ");
	} 

	void addEdge(String from, String to){
		GraphNode node = vertices.get(from);
		GraphNode end = vertices.get(to); 
		if(node != null && end != null){
			if(!node.contains(end)){
				node.neighbors.add(end);
			}
			if(!end.contains(node)){
				end.neighbors.add(node);
			}
		} else {
			System.out.println("Cannot add edge. Nodes do not all exist");
		}
	}

	void printGraph(){
		for(String key : vertices.keySet()){
			GraphNode node = vertices.get(key);
			System.out.print("Key " + key + " connections: ");
			for(int i = 0; i < node.neighbors.size(); i++){
				ArrayList<GraphNode> n = node.neighbors;
				System.out.print(n.get(i).value);
			}
			System.out.print('\n');
		}
	}

	public static void main(String[] args){
		Graph g = new Graph(5);

		g.addVertex("A"); 
		g.addVertex("B"); 
		g.addVertex("C"); 
		g.addVertex("D"); 
		g.addVertex("E"); 

		g.addEdge("A", "B");
		g.addEdge("A", "C");
		g.addEdge("C", "E"); 
		g.addEdge("E", "D");
		g.addEdge("C", "D");

		g.printGraph(); 
	}
}
