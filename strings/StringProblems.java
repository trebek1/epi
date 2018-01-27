import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

class StringProblems {

	public static String convertBase(String s, int b1, int b2){
		boolean isNegative = s.startsWith("-");
		int x = 0;
		// Find base 10 
		for(int i = (isNegative) ? 1 : 0; i < s.length(); i++){
			x *= b1;
			x += Character.isDigit(s.charAt(i)) ? s.charAt(i) - '0' : s.charAt(i) - 'A' + 10;
		}

		StringBuilder result = new StringBuilder();
		// once you have base 10 can find reverse of answer 

		// get new base by repeatedly dividing by new base until find that value is zero 
		do{
			int remainder = x % b2;
			result.append((char)(remainder >= 10 ? 'A' + remainder - 10 : '0' + remainder));
			x/=b2;
		} while (x != 0);

		// if neg add a neg sign as its reversed so it needs to go on the end 
		if(isNegative){
			result.append('-');
		}

		// reverse 
		result.reverse();

		// return string after reversing it 
		return result.toString();
	}

	public static String intToString(int x) {
    	boolean isNegative = false;
	    if (x < 0) {
	      isNegative = true;
	    }

	    StringBuilder s = new StringBuilder();
	    do {
	      s.append((char)('0' + Math.abs(x % 10)));
	      x /= 10;
	    } while (x != 0);

	    if (isNegative) {
	      s.append('-'); // Adds the negative sign back.
	    }
	    s.reverse();
	    return s.toString();
	}

    public static int stringToInt(String s) {
	    int result = 0;
	    int start =  s.charAt(0) == '-' ? 1 : 0;
	    for (int i = start; i < s.length(); i++) {
	      int digit = s.charAt(i) - '0';
	      result = result * 10 + digit;
	    }
	    return s.charAt(0) == '-' ? -result : result;
	}

	public static int spreadSheetNumber(String number){
		// A = 65, a = 97 --> + 32
		int sum = 0;
		int multiplier = 0;
		int current = 0;
		int next = 0;
		for(int i = number.length() - 1; i >= 0; i--){
			multiplier = (int)Math.pow(26, current);
			current++;
			next = (number.charAt(i) - 64) * multiplier;
			sum += next;
		}
		return sum;
	}

	static char[] removeAndReplace(char[] arr, int size, int originalSize){
		// make another array
		// if array != a or b, add it 
		// if a, add two d chars 
		// if b, dont add anything 

		int a = 0;
		int writeIndex = 0;


		for(int i = 0; i < size; i++){
			if(i < originalSize){
				if(arr[i] != 'b' && arr[i] != 'a'){
					arr[writeIndex++] = arr[i];
				}
				if(arr[i] == 'a'){
					++a;
				}	
			}
		}
		int curIdx = writeIndex;
		int extra = writeIndex + a - 1; 
		int finalSize = extra + 1;
		
		while(curIdx < size){
			arr[curIdx] = 'd';
			curIdx++;
		}
		return arr;
	}

	static boolean isPalindrome(String str){
		int l = 0;
		int r = str.length() - 1; 
		// could use method Character.isLetterOrDigit to test 
		// for spaces before making comparison if that was required 

		// could also make strings lowercase first as well 

		while(l <= r){
			if(str.charAt(l) != str.charAt(r)){
				return false;
			}
			l++;
			r--;
		}

		return true;
	}

	static String reverse(String s){
		// split by blank space
		String[] arr = s.split("\\s+");

		// reverse the string
		for(int i = 0; i < arr.length/2; i++){
			int l = i;
			int r = arr.length - 1 - i;
			String temp = arr[i];
			arr[i] = arr[r];
			arr[r] = temp;
		}

		// joint back together 
		StringBuilder sb = new StringBuilder();
		for(String string : arr){
			sb.append(string);
			sb.append(" ");
		}

		// turn stringbuilder to string 
		String ans = sb.toString();

		return ans;
	}

	static List<String> mnemonics(String number){
		List<String> ans = new ArrayList<>();
		char[] partial = new char[number.length()];
		mnemonicHelper(number, 0, partial, ans);

		return ans;
	}
	// O(4^n) for calculation, need a copy of partial for each recursive call so O(n) so (O(n*4^n))
	static void mnemonicHelper(String number, int digit, char[] partial, List<String> ans){
		if(digit == number.length()){
			ans.add(new String(partial)); // can create a new String from a char[]!! 
		} else {
				// 0 to size of lookup digit 
			int phoneDigit = number.charAt(digit) - '0'; // the phone digit in numeric terms 
			for(int i = 0; i < MAPPING[phoneDigit].length(); i++){
				char c = MAPPING[phoneDigit].charAt(i);
				partial[digit] = c; 
				mnemonicHelper(number, digit + 1, partial, ans);
			}
		}
	}

	static final String[] MAPPING = new String[] {
		"0", "1", "ABC", "DEF", "GHI", "JKL", "MNO", "PQRS", "TUV", "WXYZ" };

	// Page 100
	static String lookSay(int n){

		String ans = "1";
		for(int i = 1; i < n; i++){
			ans = nextNumber(ans);
		}

		return ans;
	}

	private static String nextNumber(String s){
		StringBuilder ans = new StringBuilder();
		int count = 1; 
		char current = s.charAt(0);

		for(int i = 1; i < s.length(); i++){
			char target = s.charAt(i);

			if(target != current){
				ans.append(count);
				ans.append(current);
				current = target;
				count = 1;
			} else {
				count++;
			}
		}
		ans.append(count);
		ans.append(current);
		return ans.toString();
	}
	
	static int romanToDec(String s){
		Map<Character, Integer> map = new HashMap<>();
		map.put('I', 1);
		map.put('V', 5);
		map.put('X', 10);
		map.put('L', 50);
		map.put('C', 100);
		map.put('D', 500);
		map.put('M', 1000);

		int ans = 0;

		for(int i = 0; i < s.length(); i++){
			char next = s.charAt(i);
			int value = map.get(next);
			if(i != s.length() - 1){
				char special = s.charAt(i + 1);
				if(next == 'I'){
					if(special == 'V'){
						value = 4;
						i++;
					} else if(special == 'X'){
						value = 9;
						i++;
					}
				} else if(next == 'X'){
					if(special == 'L'){
						value = 40;
						i++;
					} else if(special == 'C'){
						value = 90;
						i++;
					}
				} else if(next == 'C'){
					if(special == 'D'){
						value = 400;
						i++;
					} else if(special == 'M'){
						value = 900;
						i++;
					}
				}
			}
			ans += value;
		}
		return ans;
	}

	public static void main(String[] args){
		// 7.1 Convert String to int or int to string
		// int five = 511;
		// String s5 = "500";
		// String s1 = intToString(five);
		// int s2 = stringToInt(s5);
		// System.out.println("String  " + s1);
		// System.out.println("Integer " + s2);

		// 7.2 Base Conversion 

			// String ans = convertBase("615", 7, 13);
			// System.out.println(ans);
			// String ans = convertBase("61523453464576578", 7, 13);
			// System.out.println(ans);
		// 7.3 Compute Spreadsheet Column Encoding 

			// "D" --> 4 , ZZ --> 702
			// int a = spreadSheetNumber("ZZ");
			// int b = spreadSheetNumber("D");

			// System.out.println(a + " " + b);
		// 7.4 Remove and Replace 

			// char[] input = {'a', 'a', 'b', 'a', 'd', 'k', 'b','b','r','y','a','n','a','a','a'};
			
			// char[] soln = removeAndReplace(input, 15,13); 

			// for(int i = 0; i < soln.length; i++){
			// 	System.out.println(soln[i]);
			// }
		// 7.5 Test for a palindrome 
			// String word = "amanaplanacanalpanama";
			// String word2 = "alex";
			// String word3 = "racecar";
			// String word4 = "fabio";
			// boolean soln = isPalindrome(word);
			// boolean soln2 = isPalindrome(word2);
			// boolean soln3 = isPalindrome(word3);
			// boolean soln4 = isPalindrome(word4);
			// System.out.println(soln);
			// System.out.println(soln2);
			// System.out.println(soln3);
			// System.out.println(soln4);
		// 7.6 Reverse all the words in a sentence 
			// alice meets bob --> bob meets alice 
			// String s = "John and Michael"; 
			// String ans = reverse(s);
			// System.out.println(ans);
		// 7.7 mnemonics from a phone number 
			// String number = "2276696";
			// List<String> ans =  mnemonics(number);
			// System.out.println("count " + ans.size());
			// for(int i = 0; i < ans.size(); i++){
			// 	System.out.println(ans.get(i));
			// }
		// 7.8 Look and Say problem
			// String s = lookSay(8);
			// System.out.println(s);
		// 7.9 Convert from Roman to Decimal 
			String s = "XXXXXIIIIIIIII"; // 59 
			String s2 = "LIX"; // 59 
			int ans = romanToDec(s);
			int ans2 = romanToDec(s2);
			System.out.println(ans);
			System.out.println(ans2);
	}	
}



