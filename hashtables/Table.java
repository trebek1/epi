import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;

class Table {

	static List<List<String>> findAnagrams(List<String> dictionary){
		Map<String, List<String>> map = new HashMap<>();

		for(String s : dictionary){
			char[] arr = s.toCharArray();
			Arrays.sort(arr);
			String sortedStr = new String(arr);

			if(!map.containsKey(sortedStr)){
				map.put(sortedStr, new ArrayList<String>());
			}
			map.get(sortedStr).add(s);
		}

		List<List<String>> anagrams = new ArrayList<>();
		for(Map.Entry<String, List<String>> p : map.entrySet()){
			if(p.getValue().size() >= 2){
				anagrams.add(p.getValue());
			}
		}
		return anagrams;
	}

  public static void main(String[] args){
  	// 13.1 Partition into anagrams

  	List<String> dictionary = new ArrayList<>();

  	dictionary.add("debitcard");
  	dictionary.add("badcredit");
  	dictionary.add("elvis");
  	dictionary.add("silent");
  	dictionary.add("lives");
  	dictionary.add("freedom");
  	dictionary.add("listen");
  	dictionary.add("levis");
  	dictionary.add("money");

  	List<List<String>> anagrams = findAnagrams(dictionary);
  	for(List<String> list : anagrams){

  		for(String s : list){
  			System.out.print(s + " ");
  		}
  		System.out.println("");
  	}
  }
}