import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

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

			List<Integer> array = new ArrayList<Integer>();
			array.add(1);
			array.add(1);
			array.add(1);
			array.add(3);
			array.add(3);
			array.add(5);
			array.add(10);
			array.add(25);
			array.add(25);
			array.add(50);
			array.add(75);

			List<Integer> solution = ArrayProblems.removeOrderedDuplicates(array);

			for(Integer i : solution){
				System.out.println(i);
			}
	}
}

