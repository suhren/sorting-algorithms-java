package com.sorting.algorithms;

import com.sorting.util.SortableArray;

/**
 * The SelectionSort algorithm works by dividing the array into two parts.
 * One part which is sorted at the left of the array and one unsorted at the right.
 * It then looks for the smallest unsorted element in the right part and swaps it 
 * into the end of the sorted part until the unsorted part is empty.
 * @author Adam
 *
 */
public class SelectionSort extends SortingAlgorithm {

	@Override
	public void sort(SortableArray<?> a) {
		// Loop through the list n times
		for (int i = 0; i < a.length(); i++) {
			// Find the smallest unsorted element
			int iMin = i;
			for (int j = i + 1; j < a.length(); j++)
				if (a.compare(j, iMin) < 0)
					iMin = j;
			// Swap in the smallest unsorted element at the end of the sorted elements
			if (i != iMin)
				a.swap(i, iMin);
		}
	}
}