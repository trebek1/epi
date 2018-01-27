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

	}	
}



