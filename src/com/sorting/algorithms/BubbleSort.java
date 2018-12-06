package com.sorting.algorithms;

import com.sorting.util.SortableArray;

/**
 * The BubbleSort algorithm works by letting the elements "bubble up" 
 * to the surface of the array. This is done by consecutively comparing 
 * an element to it's neighbor and swapping them if it is larger.
 * @author Adam
 *
 */
public class BubbleSort extends SortingAlgorithm {
	@Override
	public void sort(SortableArray<?> a) {
		if (a != null && a.length() > 1) {
			// Loop for each element in the array
			for (int i = 0; i < a.length(); i++) {
				// After each loop we know that the largest unsorted element will be 
				// at the top. This means we only need to loop n-1 times in the next iteration.
				for (int j = 0; j < a.length() - 1 - i; j++) {
					// Compare each element to its neighbor and swap if needed.
					if (a.compare(j, j + 1) > 0)
						a.swap(j, j + 1);
				}
			}
		}
	}
}