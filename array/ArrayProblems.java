import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

// Array Notes
// good for retrieving and updating (O(1))
// deleting is simply moving all over one to the left O(n - i)
// A[i] denotes i + 1 element
// resizing affects worst case time of insertion

// 6.1 The dutch national flag problem

class ArrayProblems {
	static enum Color {RED, WHITE, BLUE}
	// Color.BLUE.ordinal() --> 2 
	// Color.RED.ordinal() --> 0 

	static List<Integer> dutchFlag(int pivotIndex, List<Integer> listToSort){
		Integer pivot = listToSort.get(pivotIndex);
		int smaller = 0; int equal = 0; int larger = listToSort.size();
		// smaller section is listToSort.sublist(0, smaller);
		// equal section is listToSort.sublist(smaller, equal);
		// unclassified is sublist(equal, larger);
		// top group is sublist(larger, listToSort.size());

		// equal or larger needs to change after each operation 
		while(equal < larger){
			if(listToSort.get(equal) < pivot){
				Collections.swap(listToSort, smaller++, equal++);
			} else if(listToSort.get(equal) == pivot){
				++equal;
			} else {
				// have to decrement first because list is not ever equal to listToSort.size();
				Collections.swap(listToSort, equal, --larger);
			}
		}
		return listToSort;
	}

	static List<Integer> incrementInteger(List<Integer> list){
		int index = list.size() - 1; 
		int num =  list.get(index) + 1;
		list.set(index, num);
		while(index >= 0){
			int val = list.get(index);
			if(val < 10){
				return list;
			} else {
				if((index - 1) > -1){
					list.set(index, 0);
					list.set(index - 1, list.get(index - 1) + 1);
					index--;
				} else {
					list.set(index, 0);
					list.add(0, 1);
					return list;
				}
			}
		}
		return list;
	}

	static List<Integer> multiply(List<Integer> l1, List<Integer> l2){
		// initialize array to size of possible solution 
		List<Integer> solution = new ArrayList<>(l1.size() + l2.size());

		// add zeroes to all positions in the array 
		for(int i = 0; i < l1.size() + l2.size(); i++){
			solution.add(0);
		}

		int digit1 = l1.get(0);
		int digit2 = l2.get(0);
		boolean negative = false;

		// check if digits make a negative number 
		if((digit1 < 0 && digit2 >= 0) || (digit2 < 0 && digit1 >= 0)){
			negative = true;
		}

		// set digits to positive version of number 
		l1.set(0, Math.abs(l1.get(0)));
		l2.set(0, Math.abs(l2.get(0)));

		// reverse digits for easier logic 
		Collections.reverse(l1);
		Collections.reverse(l2);

		// loop through all digits of one number and set to current number + mult of two
		// set next digit if needed 
		// adjust original to be less than 10 

		for(int i = 0; i < l1.size(); i++){
			for(int j = 0; j < l2.size(); j++){
				int num = solution.get(i + j) + l1.get(i) * l2.get(j);
				solution.set(i + j, num);
				solution.set(i + j + 1, num / 10 + solution.get(i + j + 1));
				solution.set(i + j, num % 10);
			}
		}

		// remove any extra zeroes at end of number that are not used 
		while(solution.size() > 1 && solution.get(solution.size() - 1) == 0){
			solution.remove(solution.size() - 1);
		}

		// reverse array back to correct orientation 
		Collections.reverse(solution);

		// if the number should be negative then make it neg 
		if(negative){
			solution.set(0, solution.get(0) * -1);
		}

		return solution;
	}

	static boolean advance(List<Integer> maze){
		int start = 0; 
		int range = 0;

		for(int i = 0; i < maze.size() - 1 && i <= range && range <= maze.size() - 1; i++){
			int current = maze.get(i);
			range = Math.max(range, i + current);
		}
		
		return range >= maze.size() - 1; 
	}

	static int deleteKey(List<Integer> array, int key){
		int writeIndex = 0;
		for(int i = 0; i < array.size(); i++){
			int current = array.get(i);
			if(current != key){
				array.set(writeIndex++, array.get(i));
			}
		}
		return writeIndex;
	}

	static List<Integer> removeOrderedDuplicates(List<Integer> list){
		if(list.isEmpty()){
			return null;
		}
		int next = 1;
		// 1 1 1 3 3 5 9 12 12 15
		for(int i = 1; i < list.size(); i++){
			int current = list.get(i);
			int previous = list.get(i - 1);
			if(current != previous){
				list.set(next++, list.get(i));
			}
		}	

		return list.subList(0, next);
	}

	static int buySellOnce(List<Integer> array){
		int smallest = array.get(0);
		int maxGain = Integer.MIN_VALUE;

		for(int i = 0; i < array.size(); i++){
			int current = array.get(i);
			int gain = current - smallest;
			if(gain > maxGain){
				maxGain = gain;
			}
			if(current < smallest){
				smallest = current;
			}
		}
		return maxGain;
	}

	static int buySellTwice(List<Integer> array){
		int solution = Integer.MIN_VALUE;
		int maxSoFar = Integer.MIN_VALUE;
		List<Integer> maxProfitForward = new ArrayList<>();
		int min = array.get(0);
		for(int i = 0; i < array.size(); i++){
			int current = array.get(i);

			if(current < min){
				min = current;
			}
			int profit = current - min;
			if(profit > maxSoFar){
				maxSoFar = profit;
			}
			maxProfitForward.add(maxSoFar);
		}

		int max = array.get(array.size() - 1);
		for(int i = array.size() - 1; i >= 0; i--){
			int current = array.get(i);
			int trial = max - current + maxProfitForward.get(i);
			if(current > max){
				max = current;
			}
			if(trial > solution){
				solution = trial;
			}
		}
		return solution;
	}

	static List<Integer> findPrimes(int n){
		// collection of primes 
		List<Integer> primes = new ArrayList<>();

		// is prime section
		List<Boolean> list = new ArrayList<>(Collections.nCopies(n + 1, true));
		// make zero and one false 
		list.set(0, false);
		list.set(1, false);

		for(int i = 2; i <= n; i++){
			if(list.get(i)){
				primes.add(i);
				for(int k = i; k <= n; k += i){
					list.set(k, false);
				}
			}
		}
		return primes;
	}

	static List<Integer> permuteElements(List<Integer> perms, List<Integer> A){
		for(int k : A){
			System.out.println(k);
		}
		for(int k = 0; k < A.size(); k++){
			int i = k; 
			while(perms.get(i) >= 0){
				System.out.println("Swapping " + A.get(k) + " and " + A.get(perms.get(i)));
				Collections.swap(A, k, perms.get(i));
				int temp = perms.get(i);
				perms.set(i, perms.get(i) - perms.size());
				i = temp;
			}	
		}
		
		return A;
	}

	static List<Integer> findNextPerm(List<Integer> nums){
		// has to be a SUFFIX 

		int k = nums.size() - 2;
		while(k >= 0 && nums.get(k) >= nums.get(k + 1)){
			k--;
		}
		if(k == -1){
			return Collections.emptyList();
		}
		// this just swaps the target and the one closest to it 
		for(int i = nums.size() - 1; i > k; i--){
			if(nums.get(i) > nums.get(k)){
				Collections.swap(nums, k, i);
				break;
			}
		}

		Collections.reverse(nums.subList(k + 1, nums.size()));
		return nums;

	}

	// this is 0(k) time and 0(k) space 
	static List<Integer> randomSubset(List<Integer> set, int size){

		// how do you take a set and return a subset with equal probability 

		List<Integer> usedIdx = new ArrayList<>();
		List<Integer> soln = new ArrayList<>();

		// 0 --> set size - 1
		int idx;

		for(int i = 0; i < size; i++){
			while(true){
				idx = (int) Math.floor(Math.random() * set.size());
				if(!usedIdx.contains(idx)){
					usedIdx.add(idx);
					soln.add(set.get(idx));
					break;
				}
			}
		}
		return soln;
	}
	// solution for 0(k) time and O(1) space 
	static List<Integer> randomSubset2(List<Integer> set, int size){
		int idx;
		for(int i = 0; i < size; i++){
			idx = i + (int) Math.floor(Math.random() * (set.size() - i));
			Collections.swap(set, i, idx);
		}
		return set.subList(0, size);
	}

	static List<Integer> randomPermutation(int n){
		List<Integer> arr = new ArrayList<>(n);
		for(int i = 0; i < n; i++){
			arr.add(i);
		}

		return ArrayProblems.randomSubset2(arr, n);
	}

	static List<Integer> randomSubset3(int n, int k){
		// k is size of subset
		// n is size of initial array

		Map<Integer, Integer> elements = new HashMap<>();

		Random randIdxGen = new Random();

		for(int i = 0; i < k; i++){

			// grab an index in the remaining elements that aren't set 
			int randIdx = i + randIdxGen.nextInt(n - i);

			// element that you are using 
			Integer randomValue = elements.get(randIdx);

			// index you are at 
			Integer currentIndexValue = elements.get(i);

			// both are null 
			if(randomValue == null && currentIndexValue == null){
				// really this is the only value being added to the solution here 
				// 0, 10
				elements.put(i, randIdx);

				// example setting both values when at index 0
				// 10, 0
				elements.put(randIdx, i);

			} else if(randomValue == null && currentIndexValue != null){
				// again set I to the randIndex 
				elements.put(i, randIdx);
				// set the random index to the value at i 
				elements.put(randIdx, currentIndexValue);

			} else if(randomValue != null && currentIndexValue == null){
				
				// here sine p1 isnt null just set value to p1 (rand index value)
				elements.put(i, randomValue);

				// set randIndex to index where last found 
				elements.put(randIdx, i);

			} else {
				
				// really this is the only value being added to the solution here 
				elements.put(i, randomValue); // set i to the rand index val (so update to last value found)
				
				elements.put(randIdx, currentIndexValue); // set randIndex to last index found at 
			}
		}
		List<Integer> result = new ArrayList<>(k);
		for(int i = 0; i < k; i++){
			result.add(elements.get(i));
		}
		return result;
	}

	static List<Integer> randomSubset4(int n, int k){
		// k is size of subset
		// n is size of initial array

		// idea is you are finding current and random values;
		// if random val is null use rand idx otherwise use rand val
		// if current val is null use current idx for rand val else use current val 	

		Map<Integer, Integer> elements = new HashMap<>();

		Random randIdxGen = new Random();

		for(int i = 0; i < k; i++){

			int rand = i + randIdxGen.nextInt(n - i);

			// element that you are using 
			Integer randomValue = elements.get(rand);

			// index you are at 
			Integer currentIndexValue = elements.get(i);

			int first = (randomValue == null) ? rand : randomValue;
			int second = (currentIndexValue == null) ? i : currentIndexValue;

			elements.put(i, first);
			elements.put(rand, second);

		}
		List<Integer> result = new ArrayList<>(k);
		for(int i = 0; i < k; i++){
			result.add(elements.get(i));
		}
		return result;
	}

	static List<Integer> findNonUniformNumbers(List<Integer> values, List<Double> p, int num){
		List<Integer> soln = new ArrayList<>();
		List<Double> prefixPx = new ArrayList<>();

		prefixPx.add(0.0);
		Random r = new Random();

		// calculate a prefix array
		for(int i = 0; i < p.size(); i++){
			double px = p.get(i);
			double lastPx = prefixPx.get(i);
			prefixPx.add(px + lastPx);
		}
		for(int i = 0; i < num; i++){
			// get a double
			double d = r.nextDouble();
			// look for double in prefixes
			int it = Collections.binarySearch(prefixPx, d);
			// System.out.println("this is it " + it);
			if(it < 0){
				// want the index of the first element greater than the key 
				// System.out.println("This is d " + d);
				// System.out.println("This is it " + it);
				// returns -(index greater than value) - 1
				// convert that value to an index that is useful 
				int idx = (Math.abs(it) - 2); 
				// System.out.println("This is idx " + idx);
				soln.add(values.get(idx));
			} else {
				// if it finds a value exactly just look it up and return the value
				soln.add(values.get(it));
			}
		}
		return soln;
	}


	public static void main(String[] args){

		// 6.1 Dutch Flag Problem
		// Idea --> Keep Indices of small equal and set large to end of the array 
		// With each iteration increase equal pointer or decrease larger pointer while keeping track of where next 
		// smaller would be until equal is not < larger 

		// List<Integer> ary = new ArrayList<>();
		// ary.add(5);
		// ary.add(25);
		// ary.add(15);
		// ary.add(45);
		// ary.add(22);
		// ary.add(11);
		// ary.add(7);
		// ary.add(47);
		// ary.add(55);
		// ary.add(1);

		// List<Integer> soln = ArrayProblems.dutchFlag(1, ary);
		// // 5 15 1 22 11 7 25 55 47 45 -- > Can see 25 is in the correct relative position
		// for(Integer i : ary){
		// 	System.out.println(i);
		// }

		// 6.2 Increment an Arbitrary Precision Integer 


		// List<Integer> array = new ArrayList<Integer>();
		// // array.add(1);
		// // array.add(2);
		// // array.add(9);

		// array.add(9);
		// array.add(9);
		// array.add(9);
		// array.add(9);
		// array.add(9);
		// array.add(9);

		// List<Integer> soln = ArrayProblems.incrementInteger(array);

		// for(int i : soln){
		// 	System.out.println(i);
		// }

		// 6.3 Multiply two arbitrary precision integers expressed as arrays 

			// List<Integer> array1 = new ArrayList<>();
			// array1.add(1);
			// array1.add(9);
			// array1.add(3);
			// array1.add(7);
			// array1.add(0);
			// array1.add(7);
			// array1.add(7);
			// array1.add(2);
			// array1.add(1);
			
			// List<Integer> array2 = new ArrayList<>();
			// array2.add(-7);
			// array2.add(6);
			// array2.add(1);
			// array2.add(8);
			// array2.add(3);
			// array2.add(8);
			// array2.add(2);
			// array2.add(5);
			// array2.add(7);
			// array2.add(2);
			// array2.add(8);
			// array2.add(7);

			// List<Integer> solution = ArrayProblems.multiply(array1, array2);
			// for(Integer i : solution){
			// 	System.out.println("this is i " + i);
			// }

		// 6.4 Advancing Through an Array 
			// idea is to keep track of the furthest you can get at each position
			// if you cant keep going then return false;

			// List<Integer> array = new ArrayList<Integer>();
			// array.add(3);
			// array.add(2);
			// array.add(0);
			// array.add(0);
			// array.add(2);
			// array.add(0);
			// array.add(1);

			// System.out.println(ArrayProblems.advance(array));

		// 6.5 Delete a key from an array 
		  // List<Integer> array = new ArrayList<Integer>();
		  // idea --> keep track of index where you should write to 
		  // as you find values that shouldnt be deleted, add to the index 
		  // final x duplicates will be wrong but index - 1 will be last index of actual sorted array; 

		  // array.add(5);
		  // array.add(3);
		  // array.add(7);
		  // array.add(11);
		  // array.add(2);
		  // array.add(3);
		  // array.add(13);
		  // array.add(5);
		  // array.add(7);

		  // System.out.println("This is index " + ArrayProblems.deleteKey(array, 3));
		  // for(Integer i : array){
		  // 	System.out.println(i);
		  // }

		// 6.6 Delete Duplicates from a sorted array 

			// List<Integer> array = new ArrayList<Integer>();
			// array.add(1);
			// array.add(1);
			// array.add(1);
			// array.add(3);
			// array.add(3);
			// array.add(5);
			// array.add(10);
			// array.add(25);
			// array.add(25);
			// array.add(50);
			// array.add(75);

			// List<Integer> solution = ArrayProblems.removeOrderedDuplicates(array);

			// for(Integer i : solution){
			// 	System.out.println(i);
			// }
		// 6.7 Buy and Sell A Stock Once 
			// keep track of lowest found and difference of that with current 
			// List<Integer> array = new ArrayList<Integer>();
			// array.add(310);
			// array.add(315);
			// array.add(275);
			// array.add(295);
			// array.add(260);
			// array.add(270);
			// array.add(290);
			// array.add(230);
			// array.add(255);
			// array.add(250);
			// System.out.println(ArrayProblems.buySellOnce(array));

		// 6.8 Buy and Sell A Stock Twice
		    // pass once over the array and find largest gains 
			// pass backwards calculating largest gain + largest gain in other direction to find 2 
			// List<Integer> array = new ArrayList<Integer>();
			// array.add(12);
			// array.add(11);
			// array.add(13);
			// array.add(9);
			// array.add(12);
			// array.add(8);
			// array.add(14);
			// array.add(13);
			// array.add(15);
			// System.out.println(ArrayProblems.buySellTwice(array));
		// 6.9 Enumerate all primes to N
			//  use a sieve to remove multiples of a found prime
			// after all mults are removed as you move across array 
			// any true value is a prime 
			// Collections.nCopies(int i , value) to distribute values in arrayList instantiation
			// List<Integer> solution = ArrayProblems.findPrimes(50);

			// for(int i : solution){
			// 	System.out.println(i);
			// }
		// 6.10 Permute elements of an array 
			// List<Integer> perm = new ArrayList<>();
			// perm.add(4);
			// perm.add(1);
			// perm.add(3);
			// perm.add(0);
			// perm.add(2);
			// List<Integer> ints = new ArrayList<>();
			// ints.add(77);
			// ints.add(23);
			// ints.add(11);
			// ints.add(3);
			// ints.add(4);

			// List<Integer> a = ArrayProblems.permuteElements(perm, ints);

			// for(int i : a){
			// 	System.out.println(i);
			// }
		// 6.11 Next Permutation

			// List<Integer> nums = new ArrayList<Integer>();
			// nums.add(6);
			// nums.add(2);
			// nums.add(1);
			// nums.add(5);
			// nums.add(4);
			// nums.add(3);
			// nums.add(0);

			// List<Integer> next = findNextPerm(nums);

			// for(int i : next){
			// 	System.out.println(i);
			// }
		// 6.12 Sample offline data 

			// take a subset of an array of data randomly from a larger set with each set equally likely 

		// 		List<Integer> set = new ArrayList<>();

		// 		set.add(45);
		// 		set.add(10);
		// 		set.add(7);
		// 		set.add(82);
		// 		set.add(77);
		// 		set.add(12);
		// 		set.add(55);
		// 		set.add(99);
		// 		set.add(44);
		// 		set.add(3);

		// 		List<Integer> solution = ArrayProblems.randomSubset2(set, 5);

		// 		for(int i : solution){
		// 			System.out.println(i);
		// 		}
		// }

		// 6.13 Sample Online data 
		// packed sniffer.
		// design program that takes input size k and reads packets 
		// continuously maintaing a uniform random subset of size k of read packets 

		// maintain some subset of the k packets taken as new packets are coming in 

		// idea: Find the subset using the strategy from the previous problem 
		// as n + 1 packet comes in. 
		// Find one random element from the whole set. 
		// if element is not in the set already or is the new element added, 
		// find a random element in the set and swap it with the new element 
		// the new set is a subset of the whole set including the new element added 

		// 6.14 Compute a random Permutation

		// Brute force approach
		// iteratively pick random numbers from 0 -> n - 1 and discard if repeating 
		// store seen in hashTable. 
		// this would work 

		// coupon collectors problem O(nlog(n)) 
		// O(n) space

		// better approach is to use same algorithm as finding a random subset of an array of data 

		// List<Integer> solution = ArrayProblems.randomPermutation(5);
		// for (int i : solution) {
		// 	System.out.println(i);
		// }

		// 6.15 Compute a random subset 

			// List<Integer> solution = ArrayProblems.randomSubset4(10, 5);
			// for(int i : solution){
			// 	System.out.println(i);
			// }

		// 6.16 Generate NonUniform Random Numbers 

			// idea, update the range and pick a number from it. If falls in certain parts of the range 
			// then use values allocated to that range. Find where you fall in the range using a binary tree 
			// can then find the correct weighted value in O(log(n)) time 

			// List<Integer> numbers = new ArrayList<>();
			// List<Double> probabilities = new ArrayList<>();
			// Map<Integer, Integer> map = new HashMap<>();

			// numbers.add(7);
			// numbers.add(11);
			// numbers.add(25);
			// numbers.add(77);
			// numbers.add(1337);

			// probabilities.add(0.05);
			// probabilities.add(0.5);
			// probabilities.add(0.2);
			// probabilities.add(0.1);
			// probabilities.add(0.15);

			// List<Integer> arr = ArrayProblems.findNonUniformNumbers(numbers, probabilities, 5000);
			// for(int i : arr){
			// 	if(map.containsKey(i)){
			// 		map.put(i, map.get(i) + 1);
			// 	} else {
			// 		map.put(i, 1);
			// 	}
			// }
			// for(int key : map.keySet()){
			// 	System.out.println(key + " --> " + map.get(key));
			// }
	}
}




