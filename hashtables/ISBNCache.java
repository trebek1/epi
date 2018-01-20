import java.util.LinkedHashMap;

class ISBNCache {

	LinkedHashMap<Integer, Integer> map;

	// LRU Implementation --> least recently used 
	ISBNCache(int capacity){
		// capacity, load factor --> when capacity should be doubled 
		map = new LinkedHashMap<>(capacity, 1.0, true){
			@Override 
			protected boolean removeEldestEntry(Map.Entry<Integer, Integer> e){
				return this.size() > capacity;
			}
		}
	}

	// isbn --> price 
	void lookup(Integert isbn){
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

	static void main(String[] args){

	}
}