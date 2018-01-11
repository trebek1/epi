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

	public static void main(String[] args){
		List<Integer> ary = new ArrayList<>();
		ary.add(5);
		ary.add(25);
		ary.add(15);
		ary.add(45);
		ary.add(22);
		ary.add(11);
		ary.add(7);
		ary.add(47);
		ary.add(55);
		ary.add(1);

		List<Integer> soln = ArrayProblems.dutchFlag(1, ary);
		// 5 15 1 22 11 7 25 55 47 45 -- > Can see 25 is in the correct relative position
		for(Integer i : ary){
			System.out.println(i);
		}
	}
}

