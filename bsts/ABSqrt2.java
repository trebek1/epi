class ABSqrt2 implements Comparable<ABSqrt2>{
	int a, b;
	double val;

	public ABSqrt2(int a, int b){
		this.a = a;
		this.b = b;
		this.val = a + b * Math.sqrt(2);
	}

	@Override
	public int compareTo(ABSqrt2 o){
		return Double.compare(val, o.val);
	}
}
