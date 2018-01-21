import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

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


  static Node LCACloseOptimization(Node c1, Node c2){
    // a set of unordered values 
    Set<Node> nodes = new HashSet<>();

    while(c1 != null && c2 != null){
      if(nodes.contains(c1)){
        return c1;
      } else if(nodes.contains(c2)){
        return c2;
      } else {
        nodes.add(c1);
        nodes.add(c2);
      }
      c1 = c1.p;
      c2 = c2.p;
    }
    return null;
  }

  static int minDistance(List<String> words){
    Map<String, Integer> map = new HashMap<>();

    Integer min = Integer.MAX_VALUE;
    int diff = 0;
    for(int i = 0; i < words.size(); i++){
      String word = words.get(i);
      if(map.containsKey(word)){
        diff = i - map.get(word);
        if(diff < min){
          min = diff;
        }
        map.put(word, i);
      } else {
        map.put(word, i);
      }
    }
    return min;
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
    //13.5 Compute the LCA, optimizing for close ancestors 
      // idea is that old solution is O(h) time with O(1) space, use more space to trade off for
      // less time on average 
      // Keep track of nodes visited alternating between the two nodes. This leads to 
      // O(D1 + D2) space and time on average
      // BinaryTree tree = new BinaryTree();  
      // tree.add(25);
      // tree.add(11);
      // tree.add(40);
      // tree.add(6);
      // tree.add(15);
      // tree.add(35);
      // tree.add(55);
      // tree.add(1);
      // tree.add(33);
      // tree.add(30);

      // Node n1 = tree.treeSearch(1);
      // Node n2 = tree.treeSearch(15);

      //       25
      //    11.    40
      //  6.  15. 35. 55
      // 1.      33

      // Node lca = LCACloseOptimization(n1, n2);
      // System.out.println(lca.value); // Should return 11 
      // Sets have contains method, Maps have containsKey methods 
    //13.6 Compute the k most frequent queries 
        // with HT you have to keep track of frequencies in a HT and keep track of top 6 
        // frequencies in a Min Heap Concurrently - This leads to O(n + mlog(k) efficiency and O(m) space)
        // with m = distinct keys, n = total strings and k number of elements looking for (k min)
    //13.7 Find the Smallest Distance between Two of the same strings in a sentence 

      // List<String> words = new ArrayList<>();
      // words.add("all");
      // words.add("work");
      // words.add("and");
      // words.add("no");
      // words.add("play");
      // words.add("makes");
      // words.add("for");
      // words.add("no");
      // words.add("work");
      // words.add("no");
      // words.add("fun");
      // words.add("and");
      // words.add("no");
      // words.add("results");

      // int minDistance = minDistance(words);

      // System.out.println(minDistance); // expect 2


  }
}