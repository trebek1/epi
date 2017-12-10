import java.util.ArrayList;

class GraphNode {
	public static enum Color { WHITE, GREY, BLACK };
	String value;
	ArrayList<GraphNode> neighbors;
	Color color;
	GraphNode cameFrom;

	GraphNode(String value){
		neighbors = new ArrayList<>();
		this.value = value;
		this.color = Color.WHITE; 
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