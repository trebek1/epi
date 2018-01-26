import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;

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

  // Only nested classes can be static in java 
  // Can only access outer vars that are static 
  // Do not need instance of outer class to make instance of this inner class 
  static class Subarray {
    Integer start; 
    Integer end;
    Subarray(int start, int end){
      this.start = start;
      this.end = end;
    }
  }

  // O(2n) = O(n)
  static Subarray smallestSubarray(List<String> paragraph, Set<String> set){
    Subarray soln = new Subarray(-1, -1);
    Map<String, Integer> keywordsToCount = new HashMap<>();
    int left = 0, right = 0;

    // goes until all the words are found once, now keywords ToCount has frequencies of each word in interval
    while(right < paragraph.size()){
      while(right < paragraph.size() && keywordsToCount.size() < set.size()){
        String  word = paragraph.get(right);
        if(set.contains(word)){
          keywordsToCount.put(word, keywordsToCount.containsKey(word) ? keywordsToCount.get(word) + 1 : 1);
        }
        right++;
      }
      boolean foundAll = keywordsToCount.size() == set.size();
      boolean noneYet = soln.start == -1 && soln.end == -1;
      boolean smallerRange = right - 1 - left < soln.end - soln.start;

      // replace value if found the smallest range;
      if (foundAll && (noneYet || smallerRange)) {
        soln.start = left;
        soln.end = right - 1;
      }

      // from left to right keep looking for smaller range until 
      // you dont have all the elements required for the set 
      while(left < right && foundAll){
        String l = paragraph.get(left);
        if(set.contains(l)){
          int keywordCount = keywordsToCount.get(l);
          keywordsToCount.put(l, --keywordCount);
          if(keywordCount == 0){
            keywordsToCount.remove(l);
            smallerRange = right - 1 - left < soln.end - soln.start;
            noneYet = soln.start == -1 && soln.end == -1;
            if(noneYet || smallerRange){
              soln.start = left;
              soln.end = right - 1;
            }
          }
        }
        left++;
        foundAll = keywordsToCount.size() == set.size();
      }
    }
    return soln;
  }

  // idea is to map all the words to their index since its unique 
  // you have to find the first one and set its shortest length to 1 
  // once you have one you look for the next element and so forth. 
  static Subarray smallestSubarraySequential(List<String> document, List<String> orderedList){
    Subarray soln = new Subarray(-1, -1); 

    // keyword to index in orderedList (keyword, idx)
    Map<String, Integer> keywordToIdx = new HashMap<>();

    List<Integer> latestOccurance = new ArrayList<>(orderedList.size());

    List<Integer> shortestSubarrayLength = new ArrayList<>(orderedList.size());

    // setup data structures 
    for(int i = 0; i < orderedList.size(); i++){
      latestOccurance.add(-1);
      shortestSubarrayLength.add(Integer.MAX_VALUE);
      keywordToIdx.put(orderedList.get(i), i);
    }

    int shortestDistance = Integer.MAX_VALUE;

    for(int i = 0; i < document.size(); i++){
      Integer keywordIdx = keywordToIdx.get(document.get(i)); // index of current word in orderedList

      if(keywordIdx != null){
        // first keyword 
        if(keywordIdx == 0){
          shortestSubarrayLength.set(0, 1);
        } else if(shortestSubarrayLength.get(keywordIdx - 1) != Integer.MAX_VALUE){
          int distanceToPreviousKeyword = i - latestOccurance.get(keywordIdx - 1);
          shortestSubarrayLength.set(keywordIdx, distanceToPreviousKeyword + shortestSubarrayLength.get(keywordIdx - 1));
        }
        latestOccurance.set(keywordIdx, i);

        // last keyword 
        if(keywordIdx == orderedList.size() - 1 && shortestSubarrayLength.get(shortestSubarrayLength.size() - 1) < shortestDistance){
          shortestDistance = shortestSubarrayLength.get(shortestSubarrayLength.size() - 1);
          soln.start = i - shortestSubarrayLength.get(shortestSubarrayLength.size() - 1) + 1;
          soln.end = i; 
        }
      }
    }

    return soln;

  }

  static List<Integer> longestDistinct(List<String> range){
    List<Integer> soln = new ArrayList<>();
    soln.add(0);
    soln.add(0);

    Map<String, Integer> map = new HashMap<>();
    int start = 0;
    int maxLength = 0;
    for(int i = 0; i < range.size(); i++){
      String target = range.get(i);
      if(map.containsKey(target)){
        int length = i - start;
        if(length > maxLength){
          maxLength = length;
          soln.set(1, i - 1);
          soln.set(0, start);
        } 
          length = 0;
          start = map.get(target) + 1;
          map.put(target, i);
      } else {
        map.put(target, i);
      }
    }
    return soln;
  }

  static int longestInterval(List<Integer> list){
    int soln = 0; 

    Set<Integer> hashSet = new HashSet<>();

    for(int i = 0; i < list.size(); i++){
      hashSet.add(list.get(i));
    }

    // numbers are in hashSet 
    while(!hashSet.isEmpty()){
      int next = hashSet.iterator().next();
      hashSet.remove(next);

      int upper = next + 1; 

      while(hashSet.contains(upper)){
        hashSet.remove(upper);
        upper++;
      }

      int lower = next - 1;
      while(hashSet.contains(lower)){
        hashSet.remove(lower);
        lower--;
      }
      
      soln = Math.max(soln, upper - lower - 1);

    }

    return soln;
  }

  static int topThree(){
    String line = null;
    int winner = 0;
    String fileName = "scores.txt";

    Map<String, PriorityQueue<Integer>> map = new HashMap<>(); 

    try {
        // FileReader reads text files in the default encoding.
        FileReader fileReader = 
            new FileReader(fileName);

        // Always wrap FileReader in BufferedReader.
        BufferedReader bufferedReader = 
            new BufferedReader(fileReader);

        while((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
            String[] split = line.split(" ");
            String id = split[0];
            int score = Integer.parseInt(split[1]);
            
            PriorityQueue<Integer> scores = map.get(id);
            if(scores == null){
              scores = new PriorityQueue<>();
              map.put(id, scores);
            } 

            scores.add(score);

            if(scores.size() > 3){
              scores.poll(); // only keep the top 3 scores 
            }
        }   
        // Always close files.
        bufferedReader.close();         
        String topStudent = "none";

        for(Map.Entry<String, PriorityQueue<Integer>> scores : map.entrySet()){
          if(scores.getValue().size() == 3){
            int sum = topThreeSum(scores.getValue());
            if(sum > winner){
              winner = sum; 
              topStudent = scores.getKey();
            }
          }
        }
    }
    catch(FileNotFoundException ex) {
        System.out.println(
            "Unable to open file '" + 
            fileName + "'");                
    }
    catch(IOException ex) {
        System.out.println(
            "Error reading file '" 
            + fileName + "'");                  
        // Or we could just do this: 
        // ex.printStackTrace();
    }


    return winner;
  }

  private static int topThreeSum(PriorityQueue<Integer> q){
      Iterator<Integer> it = q.iterator();
      int result = 0;
      while(it.hasNext()){
        result += it.next();
      }
      return result;
    }

  static List<Integer> stringDecomposition(String sentence, List<String> subs){
    List<Integer> result = new ArrayList<>();

    Map<String, Integer> freq = new HashMap<>();
    for(int i = 0; i < subs.size(); i++){
      String target = subs.get(i);
      if(freq.containsKey(target)){
        freq.put(target, freq.get(target) + 1);
      } else {
        freq.put(target, 1);
      }
    }

    // we know all the subs need to be in the string or nothing 
    // range is size of sub * subs + i < length of sentence 
    int subSize = subs.get(0).length();
    int numSubs = subs.size();

    for(int i = 0; i + subSize * numSubs < sentence.length(); i++){
      if(checkRange(i, sentence, subs, freq)){
        result.add(i);
      }
    }

    return result;
  }

  private static boolean checkRange(int idx, String sentence, List<String> subs, Map<String, Integer> freq){

    int len = subs.get(0).length();
    Map<String, Integer> actualFreq = new HashMap<>();
    for(int i = 0; i < subs.size(); i++){
      String s = sentence.substring(idx + i * len, idx + (i + 1) * len);
      if(freq.get(s) == null) {
        return false;
      }
      if(actualFreq.containsKey(s)){
        actualFreq.put(s, actualFreq.get(s) + 1);
      } else {
        actualFreq.put(s, 1);
      }
      if(freq.get(s) != null && actualFreq.get(s) > freq.get(s)){
        return false;
      }
    }

    return true;
  }

  static Pair affinity(){
    Pair most = null;
    HashMap<String, Set<String>> map = new HashMap<>();

    try {

      FileReader fileReader = new FileReader("affinity.txt");
      BufferedReader buff = new BufferedReader(fileReader);
      String line = null;

      while((line = buff.readLine()) != null){
        String[] data = line.split(" ");
        String site = data[0];
        String user = data[1];

        Set<String> users = map.get(site);
        if(users == null){
          users = new HashSet<>();
        }
        users.add(user);
        map.put(site, users);
      }

    } catch (IOException e) {
      System.out.println("IOException");
    } 

    int max = 0;
    List<String> keys = new ArrayList<>(map.keySet());
    for(int i = 0; i < keys.size(); i++){
      for(int k = i + 1; k < keys.size(); k++){
        Set<String> base = new HashSet<>(map.get(keys.get(i)));
        base.retainAll(map.get(keys.get(k)));
        if(base.size() > max){
          max = base.size();
          most = new Pair(keys.get(i), keys.get(k));
        }
      }
    }

    return most;
  }

  static class Pair {
    String site1;
    String site2;
    Pair(String site1, String site2){
      this.site1 = site1;
      this.site2 = site2;
    }
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

    // 13.8 Find smallest subarray covering all values 
      // List<String> list = new ArrayList<>();
      // looking for banana cat 
      // first 0 --> 5
      // list.add("apple");   // 0
      // // now 1 --> 5 // min is now 4 
      // list.add("banana"); // 1
      // list.add("apple");   // 2
      // list.add("apple");   // 3
      // list.add("dog");     // 4
      // list.add("cat");     // 5
      // // skip to 5 
      // list.add("apple");   // 6
      // list.add("dog");     // 7
      // list.add("banana"); // 8
      // // 5 --> 8 is 3 so new min 

      // list.add("apple");   // 9 
      // list.add("cat");     // 10
      // // 8 --> 10 is new min 

      // list.add("dog");     // 11

      // Set<String> subset = new HashSet<>();
      // subset.add("banana");
      // subset.add("cat");

      // Subarray s = smallestSubarray(list, subset);
      // System.out.println("Start " + s.start + " end " + s.end);

    // 13.9 Find smallest subarray SEQUENTIALLY covering all values; 

      //  List<String> list = new ArrayList<>();
      // // looking for banana cat 
      
      // list.add("apple");   // 0
      // list.add("dog"); // 1
      // list.add("cat");   // 2
      // list.add("apple");   // 3
      // list.add("panda");     // 4
      // list.add("panda");     // 5
      // list.add("apple");   // 6
      // list.add("dog");     // 7
      // list.add("banana"); // 8
      // list.add("apple");   // 9 
      // list.add("cat");     // 10
      // list.add("panda");     // 11

      // List<String> subset = new ArrayList<>();
      // subset.add("cat");
      // subset.add("dog");

      // Subarray s = smallestSubarraySequential(list, subset);
      // System.out.println("Start " + s.start + " end " + s.end);

      // 13.10 Find Longest string of distinct words 

      // List<String> list = new ArrayList<>();
      // list.add("a");
      // list.add("b");
      // list.add("c");
      // list.add("a");
      // list.add("d");
      // list.add("e");
      // list.add("f");
      // list.add("g");
      // list.add("g");
      // list.add("h");
      // list.add("i");
      // list.add("j");

      // List<Integer> range = longestDistinct(list);

      // for(Integer i : range){
      //   System.out.println(i);
      // }

    // 13.11 Find Longest continuous interval in array 
    // List<Integer> list = new ArrayList<>(); 
    // list.add(10);
    // list.add(5);
    // list.add(3);
    // list.add(11);
    // list.add(6);
    // list.add(100);
    // list.add(4);

    // int len = longestInterval(list);
    // System.out.println(len);

    // 13.12 Top 3 scores given that they have three scores

    // int average = topThree();
    // System.out.println(average/3);

    // 13.13 Compute all string decompositions 

    // String sentence = "amanaplanacanal";
    // List<String> subs = new ArrayList<>();
    // subs.add("can");
    // subs.add("apl");
    // subs.add("ana");

    //   List<Integer> results = stringDecomposition(sentence, subs);
    //   for(Integer i : results){
    //     System.out.println(i);
    //   }

    // 13.14 Highest Affinity 
      // Pair most = affinity();
      // System.out.println(most.site1);
      // System.out.println(most.site2);

  }
}





