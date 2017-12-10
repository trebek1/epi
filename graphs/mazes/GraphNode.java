import java.util.ArrayList;

class GraphNode {
	String value;
	ArrayList<GraphNode> neighbors;
	GraphNode(String value){
		neighbors = new ArrayList<>();
		this.value = value;
	}

	@Override
	public boolean equals(Object o){
		if(this == o){
			return true;
		}
		if(!(o instanceof GraphNode)){
			return false;
		}

		GraphNode node = (GraphNode) o;

		return this.value == node.value; 
	}

	@Override
	public int hashCode(){
		return value.hashCode();
	}

	boolean contains(GraphNode g){
		for(int i = 0; i < g.neighbors.size(); i++){
			GraphNode n = g.neighbors.get(i);
			if(n.equals(g)){
				return true;
			}
		}
		return false;
	}
}