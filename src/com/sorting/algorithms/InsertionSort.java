package com.sorting.algorithms;

/**
 * The InsertionSort algorithm works by dividing the list into the left part which is ordered, 
 * and the right part which is unordered. The algorithm compares the leftmost element in the 
 * preceding element and swaps until it encounters and element in the ordered part that is greater.
 * This continues until the list is sorted.
 * @author Adam
 */
public class InsertionSort<E extends Comparable<? super E>> extends SortingAlgorithm<E> {

	public InsertionSort() {
		super("InsertionSort");
	}

	/**
	 * Sort the array using the InsertionSort algorithm.
	 * @param a The array to be sorted.
	 */
	@Override
	protected E[] sort(E[] a) throws SortAbortedException {
		if (a != null && a.length > 1)
			insertionSort(a);
		return a;
	}
	
	private void insertionSort(E[] a) throws SortAbortedException {
		// Perform the operation for each element in the list.
		for (int i = 1; i < a.length; i++) {
			// The j index is the border of the sorted and unsorted sublists.
			int j = i;
			// Keep swapping the element we found at the border left until we find a greater one.
			while (j > 0 && compare(a, j - 1, j) > 0) {
				swap(a, j - 1, j);
				j--;
			}
		}
	}
}