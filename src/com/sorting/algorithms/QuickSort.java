package com.sorting.algorithms;

/**
 * The QuickSort algorithm divides the array into two smaller sub-arrays using a pivot value. 
 * The elements smaller than the pivot will be partitioned to the left and the larger elements 
 * to the right with the pivot in the middle. The method will then be called recursively on the
 * sub-arrays until the entire array is sorted.
 * @author Adam
 * @param <E>
 */
public class QuickSort<E extends Comparable<? super E>> extends SortingAlgorithm<E> {
	
	public QuickSort() {
		super("QuickSort");
	}

	/**
	 * Sort the array using the QuickSort algorithm.
	 * @param a The array to sort.
	 */
	@Override
	protected E[] sort(E[] a) {
		if (a != null && a.length > 1)
			qs(a, 0, a.length - 1);
		return a;
	}
	
	/**
	 * The recursive method used to sort the array.
	 * @param a The array to sort.
	 * @param iLow The lower index of the span.
	 * @param iHigh The upper index of the span.
	 */
	private void qs(E[] a, int iLow, int iHigh) {
		// Check if all elements are equal. Otherwise set the pivot index to -1.
		int iPivot = -1;
		for (int i = iLow; i < iHigh; i++)
			if (compare(a, i, i+1) != 0)
				iPivot = (iLow + iHigh) / 2;
		
		// If the pivot index is -1 we are done.
		if (iPivot == -1)
			return;

		// Otherwise save the pivot element at the end of the array.
		swap(a, iPivot, iHigh);
		iPivot = iHigh;
		
		// Partition the array using the pivot element (minus the end where the pivot is now located).
		int i = iLow;
		int j = iHigh - 1;
		
		while (i < j) {
			while (i < j && compare(a, i, iPivot) < 0)
				i++;
			while (i < j && compare(a, j, iPivot) >= 0)
				j--;
			if (i < j)
				swap(a, i, j);
		}

		// Swap back the pivot element between the two partitions.
		swap(a, i, iPivot);
		
		// Recursively call QuickSort on the two partitions.
		qs(a, iLow, i - 1);
		qs(a, i + 1, iHigh);
	}
}