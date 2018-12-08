package com.sorting.algorithms;

/**
 * The SelectionSort algorithm works by dividing the array into two parts.
 * One part which is sorted at the left of the array and one unsorted at the right.
 * It then looks for the smallest unsorted element in the right part and swaps it 
 * into the end of the sorted part until the unsorted part is empty.
 * @author Adam
 */
public class SelectionSort<E extends Comparable<? super E>> extends SortingAlgorithm<E> {
	
	public SelectionSort() {
		super("SelectionSort");
	}

	/**
	 * Sort the array using the SelectionSort algorithm.
	 * @param a The array to be sorted.
	 */
	@Override
	protected E[] sort(E[] a) {
		if (a != null && a.length > 1)
			selectionSort(a);
		return a;
	}
	
	protected void selectionSort(E[] a) {
		// Loop through the list n times
		for (int i = 0; i < a.length; i++) {
			// Find the smallest unsorted element
			int iMin = i;
			for (int j = i + 1; j < a.length; j++)
				if (compare(a, j, iMin) < 0)
					iMin = j;
			// Swap in the smallest unsorted element at the end of the sorted elements
			if (i != iMin)
				swap(a, i, iMin);
		}
	}
}