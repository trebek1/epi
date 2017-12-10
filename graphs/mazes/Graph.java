import java.util.HashMap;
import java.util.ArrayList;
import java.util.Stack;

class Graph {
	HashMap<String, GraphNode> vertices;
	Stack<GraphNode> stack = new Stack<>();
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

	boolean checkMinimalConnectedness(){

		GraphNode firstNode = vertices.get(vertices.keySet().toArray()[0]);

		stack.push(firstNode);
		firstNode.color = GraphNode.Color.GREY;


		while(!stack.isEmpty()){
			
			GraphNode currentNode = stack.peek();
			ArrayList<GraphNode> neighbors = currentNode.neighbors;

			for(int i = 0; i < neighbors.size(); i++){
				GraphNode n = neighbors.get(i);
				if(n.color == GraphNode.Color.WHITE){
					n.cameFrom = currentNode;
					stack.push(n);
					n.color = GraphNode.Color.GREY;
				} else if(currentNode.cameFrom != null && (!(currentNode.cameFrom).equals(n)) && n.color == GraphNode.Color.GREY){
					return false;
				} else if(i == neighbors.size() - 1){
					currentNode.color = GraphNode.Color.BLACK;
					stack.pop();
				} 
			}
		}
		return true;
	}

	Graph cloneGraph(GraphNode start){
		System.out.println("This is node start  " + start.value);
		Graph clone = new Graph(V); 

		clone.vertices.put(start.value, start);

		clone.stack.push(start);
		start.color = GraphNode.Color.GREY;

		while(!clone.stack.isEmpty()){
			GraphNode currentNode = clone.stack.peek();
			ArrayList<GraphNode> neighbors = currentNode.neighbors;

			for(int i = 0; i < neighbors.size(); i++){
				GraphNode n = neighbors.get(i);
				if(n.color == GraphNode.Color.WHITE){
					clone.stack.push(n);
					clone.vertices.put(n.value, n);
					n.color = GraphNode.Color.GREY;
					break;
				}else if(i == neighbors.size() - 1){
					currentNode.color = GraphNode.Color.BLACK;
					clone.stack.pop();
				}
			}
		}

		System.out.println("This is the clone ");
		printGraph();
		return clone;
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

		System.out.println("Is minimally connected " + g.checkMinimalConnectedness());

		System.out.println("clone the graph ");
		GraphNode firstNode = g.vertices.get(g.vertices.keySet().toArray()[0]);
		g.cloneGraph(firstNode);

	}
}
