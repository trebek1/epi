import java.util.HashMap;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

class WiredConnections {
	HashMap<String, GraphNode> vertices;
	Stack<GraphNode> stack = new Stack<>();
	Queue<GraphNode> q = new LinkedList<>();

	final Integer V;

	WiredConnections(int size){
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

	boolean canBeMinimallyConnected(){
		// pick starting point 
		GraphNode start = vertices.get("A");
		start.color = GraphNode.Color.BLACK;
		start.count = 0;

		q.add(start);

		int count = 0;

		while(!q.isEmpty()){

			// get head of queue 
			GraphNode current = q.poll();

			if(current.count != -1){
				count = current.count + 1;
			} else {
				count = 0;
			}

			// set color to working node 
			int size = current.neighbors.size();
			if(size > 0){
				for(int i = 0; i < size; i++){
					GraphNode g = current.neighbors.get(i);
					g.count = count;

					GraphNode.Color c;
					// pick color 
					if(count % 2 == 0){
						c = GraphNode.Color.BLACK;
					} else {
						c = GraphNode.Color.GREY;
					}

					if(g.color != GraphNode.Color.WHITE && (g.color == current.color)){
						return false;
					}

					g.color = c; 
					if(g.visited != true){
						q.add(g);
						g.visited = true;
					}
				}
			}
		}

		return true;

		// begin BFS marking nodes until all are marked or find an exception 
	}

	public static void main(String[] args){
		WiredConnections g = new WiredConnections(5);
		
		g.addVertex("A");
		g.addVertex("B");
		g.addVertex("C");
		g.addVertex("D");
		g.addVertex("E");
		g.addVertex("F");
		g.addVertex("G");
		g.addVertex("H");

		g.addEdge("A", "B");
		g.addEdge("A", "C");
		g.addEdge("A", "D");
		
		g.addEdge("D", "E");
		g.addEdge("D", "F");
		g.addEdge("D", "G");

		g.addEdge("G", "H");

		// This edge should break the graph
		// g.addEdge("E", "G");

		g.printGraph();

		System.out.println("Can be minimally connected " + g.canBeMinimallyConnected());
		
		// GraphNode firstNode = g.vertices.get(g.vertices.keySet().toArray()[0]);
		// g.cloneGraph(firstNode);

	}
}
