public class BubbleSort implements ISortingAlgorithm {
	@Override
	public void sort(SortableArray a) {
		a.startSort();
		for (int i = 0; i < a.length(); i++)
			for (int j = 0; j < a.length() - 1 - i; j++) {
				if (a.compare(j, j + 1) > 0)
					a.swap(j, j + 1);
			}
		a.endSort();
	}
}