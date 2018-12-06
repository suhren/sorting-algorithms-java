public class QuickSort extends SortingAlgorithm {
	
	@Override
	public void sort(SortableArray<?> a) {
		if (a != null && a.length() > 1)
			qs(a, 0, a.length() - 1);
	}
	
	/**
	 * @param The array to sort.
	 * @param The lower index of the span.
	 * @param The upper index of the span.
	 */
	private void qs(SortableArray<?> a, int iLow, int iHigh) {
		// Check if all elements are equal. Otherwise set the pivot index to -1.
		int iPivot = -1;
		for (int i = iLow; i < iHigh; i++)
			if (a.compare(i, i+1) != 0)
				iPivot = (iLow + iHigh) / 2;
		
		// If the pivot index is -1 we are done.
		if (iPivot == -1)
			return;

		// Otherwise save the pivot element at the end of the array.
		a.swap(iPivot, iHigh);
		iPivot = iHigh;
		
		// Partition the array using the pivot element (minus the end where the pivot is now located).
		int i = iLow;
		int j = iHigh - 1;
		
		while (i < j) {
			while (i < j && a.compare(i, iPivot) < 0)
				i++;
			while (i < j && a.compare(j, iPivot) >= 0)
				j--;
			if (i < j)
				a.swap(i, j);
		}

		// Swap back the pivot element between the two partitions.
		a.swap(i, iPivot);
		
		// Recursively call QuickSort on the two partitions.
		qs(a, iLow, i - 1);
		qs(a, i + 1, iHigh);
	}
}