class StringProblems {

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

	public static void main(String[] args){

		// 7.1 Convert String to int or int to string
		int five = 511;
		String s5 = "500";
		// String s1 = intToString(five);
		int s2 = stringToInt(s5);

		// System.out.println("String  " + s1);
		System.out.println("Integer " + s2);
	}	
}



