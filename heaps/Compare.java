import java.util.Comparator;

class Compare implements Comparator<HeapStackItem>{
	@Override
	public int compare(HeapStackItem v1, HeapStackItem v2){
		return Integer.compare(v2.priority, v1.priority);
	}

	public static final Compare COMPARE_HEAPSTACKITEMS = new Compare();
}