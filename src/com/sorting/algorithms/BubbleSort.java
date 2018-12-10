package com.sorting.algorithms;

/**
 * The BubbleSort algorithm works by letting the elements "bubble up" 
 * to the surface of the array. This is done by consecutively comparing 
 * an element to it's neighbor and swapping them if it is larger.
 * @author Adam
 */
public class BubbleSort<E extends Comparable<? super E>> extends SortingAlgorithm<E> {
	
	public BubbleSort() {
		super("BubbleSort");
	}

	/**
	 * Sort the array using the BubbleSort algorithm.
	 * @param a The array to be sorted.
	 */
	@Override
	protected E[] sort(E[] a) {
		if (a != null && a.length > 1)
			bubbleSort(a);
		return a;
	}
	
	private void bubbleSort(E[] a) {
		// Loop for each element in the array
		for (int i = 0; i < a.length; i++) {
			// After each loop we know that the largest unsorted element will be 
			// at the top. This means we only need to loop n-1 times in the next iteration.
			for (int j = 0; j < a.length - 1 - i; j++) {
				// Compare each element to its neighbor and swap if needed.
				if (compare(a, j, j + 1) > 0)
					swap(a, j, j + 1);
			}
		}
	}
}