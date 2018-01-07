import java.util.Objects;

class ArrayData implements Comparable<ArrayData>{
	int val; 
	int idx;

	ArrayData(int idx, int val){
		this.val = val;
		this.idx = idx;
	}

	@Override
	public int compareTo(ArrayData o){
		int result = Integer.compare(val, o.val);
		if(result == 0){
			result = Integer.compare(idx, o.idx);
		}
		return result;
	}

	@Override
	public boolean equals(Object obj){
		if(obj == null || !(obj instanceof ArrayData)){
			return false;
		}
		if(this == obj){
			return true;
		}
		ArrayData that = (ArrayData)obj;
		return this.val == that.val && this.idx == that.idx;
	}

	@Override
	public int hashCode(){
		return Objects.hash(val, idx);
	}
}