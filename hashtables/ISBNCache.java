import java.util.LinkedHashMap;
import java.util.Map;

class ISBNCache {

	LinkedHashMap<Integer, Integer> map;

	// LRU Implementation --> least recently used 
	ISBNCache(int capacity){
		// capacity, load factor --> when capacity should be doubled 
		// access order --> true === LRU Cache (Access Order instead of insertion order)
		this.map = new LinkedHashMap<Integer, Integer>(capacity, 1.0f, true){
			@Override 
			protected boolean removeEldestEntry(Map.Entry<Integer, Integer> e){
				return this.size() > capacity;
			}

			// Creating an instance of something that implements Serializable needs a serialVersionUID
			// LinkedHashMap implements Serializable 
			private static final long serialVersionUID = 3456346465746L;
			// or you could suppress Serializable warnings @SuppressWarnings("serial")
		};
	}

	// isbn --> price 
	void lookup(Integer isbn){
		if(map.containsKey(isbn) == false){
			System.out.println("not found");
			return;
		}
		System.out.println(map.get(isbn));
	}

	void insert(Integer isbn, Integer value){
		map.put(isbn, value);
	}

	void remove(Integer isbn){
		map.remove(isbn);
	}

	public static void main(String[] args){

	}
}