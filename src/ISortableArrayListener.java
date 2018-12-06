public interface ISortableArrayListener {
	<E> void arrayGet(SortableArray<?> a, int i, E ei);
	<E> void arraySet(SortableArray<?> a, int i, E prev, E next);
	<E> void arraySwap(SortableArray<?> a, int i, int j, E ei, E ej);
	<E> void arrayCompare(SortableArray<?> a, int i, int j, E ei, E ej, int c);
	void arrayStartSort(SortableArray<?> a);
	void arrayEndSort(SortableArray<?> a);
}