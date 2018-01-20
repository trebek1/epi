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

  static boolean isPalindrome(String str){

    Map<Character, Integer> table = new HashMap<>();

    for(int i = 0; i < str.length(); i++){
      char c = str.charAt(i);
      if(table.containsKey(c)){
        table.put(c, table.get(c) + 1);
      } else {
        table.put(c, 1);
      }
    }

    if(str.length() % 2 == 0){
      // even
      for(Character c : table.keySet()){
        int val = table.get(c);
        if(val % 2 != 0){
          return false;
        }
      }
      return true;

    } else {
      int odds = 0; 
      for(Character c : table.keySet()){
        int val = table.get(c);
        if(val % 2 != 0){
          odds++;
          if(odds != 1){
            return false;
          } 
        }
      }
    }

    return true;

  }

  static boolean constructLetter(String letter, String magazine){

    Map<Character, Integer> index = new HashMap<>();

    // Adding Letters to the Map
    for(int i = 0; i < letter.length(); i++){
      char c = letter.charAt(i);
      if(index.containsKey(c)){
        index.put(c, index.get(c) + 1);
      } else {
        index.put(c, 1);
      }
    }

    // remove letters from index until empty 
    // If empty return true

    for(int i = 0; i < magazine.length(); i++){
      
      char c = magazine.charAt(i);
      if(index.containsKey(c)){
        int val = index.get(c);
        if(val == 1){
      
          // delete key from Map
          index.remove(c);
        } else {
          index.put(c, val - 1);
        }
      }
      if(index.isEmpty()){
        return true;
      }
    }
    



    return false;
  }

  public static void main(String[] args){
  	// 13.1 Partition into anagrams

    	// List<String> dictionary = new ArrayList<>();

    	// dictionary.add("debitcard");
    	// dictionary.add("badcredit");
    	// dictionary.add("elvis");
    	// dictionary.add("silent");
    	// dictionary.add("lives");
    	// dictionary.add("freedom");
    	// dictionary.add("listen");
    	// dictionary.add("levis");
    	// dictionary.add("money");

    	// List<List<String>> anagrams = findAnagrams(dictionary);
    	// for(List<String> list : anagrams){

    	// 	for(String s : list){
    	// 		System.out.print(s + " ");
    	// 	}
    	// 	System.out.println("");
    	// }


    // 13.2 Is the String a Palindrome

      // System.out.println(isPalindrome("racecar"));
      // System.out.println(isPalindrome("alex"));

    // 13.3 Is An Anonymous Letter Constructable ? 
      // String letter = "a joy to all";
      // String magazine1 = " the bear was a joy to all";
      // String magazine2 = " xbox is the best";

      // System.out.println(constructLetter(letter, magazine1));
      // System.out.println(constructLetter(letter, magazine2));
    // 13.4 Implement ISBN Cache
      // ISBN cache implements a LinkedHashMap 
      // implement methods lookup, insert and remove 
      // LRU Cache (Least Recently Used is a setting on Linked HashMap setting arg 3 to true)
      // Override the removeEldestEntry method by setting size() > capacity that you pass in 



  }
}