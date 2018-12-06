public class SortableArray<E extends Comparable<? super E>> {
	private ISortableArrayListener listener; 
	private E[] data;
	private int nComp = 0;
	private int nSwap = 0;
	private int nGet = 0;
	private int nSet = 0;
	private String id = null;
	private long timeStart = 0;
	private long timeEnd = 0;
	private boolean sorted = false;
	private boolean started = false;
	
	public SortableArray(E[] data, String id, ISortableArrayListener listener) {
		this.data = data;
		this.id = id;
		this.listener = listener;
	}
	
	public int nComp() { return nComp; }
	public int nSwap() { return nSwap; }
	public int nGet() { return nGet; }
	public int nSet() { return nSet; }
	public String getID() { return id; }
	public long getElapsedTime( ) { 
		if (sorted)
			return timeEnd - timeStart;
		else if (started)
			return System.currentTimeMillis() - timeStart;
		else
			return 0;
	}
	
	public int length() { return data.length; }
	
	public E get(int i) {
		nGet++;
		listener.arrayGet(this, i, data[i]);
		return data[i];
	}
	
	public void set(int i, E value) {
		E prev = data[i];
		nSet++;
		data[i] = value;
		listener.arraySet(this, i, prev, value);
	}
	
	public void swap(int i, int j) {
		nSwap++;
		E temp = data[i];
		data[i] = data[j];
		data[j] = temp;
		listener.arraySwap(this, i, j, data[j], data[i]);
	}
	
	public int compare(int i, int j) {
		nComp++;
		int c = data[i].compareTo(data[j]);
		listener.arrayCompare(this, i, j, data[i], data[j], c);
		return c;
	}
	
	public void startSort() {
		listener.arrayStartSort(this);
		started = true;
		timeStart = System.currentTimeMillis();
	}
	
	public void endSort() {
		sorted = true;
		timeEnd = System.currentTimeMillis();
		listener.arrayEndSort(this);
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("[ ");
		for (int i = 0; i < data.length - 1; i++)
			s.append(data[i].toString() + ", ");
		s.append(data[data.length - 1] + " ]");
		return s.toString();
	}
}