import java.util.HashMap;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Set; 
import java.util.HashSet;

class Production {

	// This is a BFS without actually using the graph. 
	// Return length of shortest production sequence 

	Queue<GraphNode> q = new LinkedList<>();
	Set<String> dictionary;
	String start;
	String end;

	Production(Set<String> dict, String s, String e){
		dictionary = dict;
		start = s; 
		end = e;
	}

	int sProduceT(){

		if(dictionary.contains(start)){
			GraphNode n = new GraphNode(start);
			n.distance = 0; 
			q.add(n);

			dictionary.remove(start);
			GraphNode current;
			while((current = q.poll()) != null){
				String val = current.value;
				System.out.println("This is val " + val);
				if(val.equals(end)){
					return current.distance;
				}

				for(int i = 0; i < val.length(); i++){
					String sStart = (i == 0) ? "" : val.substring(0, i);
					String sEnd = (i + 1) < val.length() ? val.substring(i + 1) : "";
					for(int k = 0; k < 26; k++){
						String pStr = sStart + (char)('a' + k) + sEnd;
						if(dictionary.remove(pStr)){
							GraphNode newNode = new GraphNode(pStr);
							newNode.distance = current.distance + 1;
							q.add(newNode);
						}
					}
				}		
			}

		} else {

			return -1;	
		}
		return -1;
	}

	public static void main(String[] args){

		Set<String> set = new HashSet<>();

		set.add("bat");
		set.add("cot");
		set.add("dog");
		set.add("dag");
		set.add("dot");
		set.add("cat");

		String start = "cat";
		String end = "dog";


		Production g = new Production(set, start, end);
		System.out.println("does s produce t " + g.sProduceT());

	}
}

