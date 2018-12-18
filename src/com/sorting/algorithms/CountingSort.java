package com.sorting.algorithms;

import com.sorting.util.SortAbortedException;
import com.sorting.util.SortingAlgorithm;

public class CountingSort extends SortingAlgorithm<Integer> {

	public CountingSort() {
		super("CountingSort");
	}

	@Override
	protected Integer[] sort(Integer[] a) throws SortAbortedException {
		if (a != null && a.length > 1)
			countingSort(a);
		return a;
	}
	
	protected void countingSort(Integer[] a) throws SortAbortedException {
		int n = a.length;
		
		// Find the max value of the array.
		int iMax = 0;
		for (int i = 0; i < n; i++)
			if (compare(a, i, iMax) > 0)
				iMax = i;
		
		int max = get(a, iMax);
		
		// Create a count array to store count of individual elements and initialize the count array to zeroes.
		int[] count = new int[max + 1];
		for (int i = 0; i < count.length; i++)
			count[i] = 0;
		
		// Store count of each element
		for (int i = 0; i < n; i++)
			count[a[i]]++;
		
		int currentSortedIndex = 0;
		
		// for each num in numCounts
	    for (int i = 0; i < count.length; i++) {
	        int nCount = count[i];

	        // for the number of times the item occurs
	        for (int j = 0; j < nCount; j++) {
	            // add it to the sorted array
	            set(a, currentSortedIndex, i);
	            currentSortedIndex++;
	        }
	    }
	}
}