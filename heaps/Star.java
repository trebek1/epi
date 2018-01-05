import java.lang.Comparable;

public class Star implements Comparable<Star>{
	public double x,y,z;

	public Star(double x, double y, double z){
		this.x = x;
		this.y = y; 
		this.z = z;
	}

	public double distance(){
		return Math.sqrt(x*x + y*y + z*z);
	}

	// java review! compareTo takes one arg to compare to while compare compares 2 objects 
	// comparator --> compare Comparable ---> compareTo
	// compare returns distance, -distance or 0! 

	@Override
	public int compareTo(Star that){
		// this will give 1, 0, -1 instead of a double
		return Double.compare(this.distance(), that.distance());
	}
}
