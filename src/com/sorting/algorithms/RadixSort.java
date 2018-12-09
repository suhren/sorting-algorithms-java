package com.sorting.algorithms;

import java.util.Arrays;

public class RadixSort extends SortingAlgorithm<Integer> {

	public RadixSort() {
		super("RadixSort");
	}

	@Override
	protected Integer[] sort(Integer[] a) {
		if (a != null && a.length > 1)
			radixSort(a, a.length);
		return a;
	}
	
	private void radixSort(Integer[] a, int n) {
		// Find the maximum number to know number of digits.
		int iMax = 0;
		for (int i = 0; i < n; i++)
			if (compare(a, i, iMax) > 0)
				iMax = i;
		Integer max = a[iMax];
		
		// Do counting sort for every digit.
		// Note that instead of passing digit number, exp is passed.
		// exp is 10^i where is is the current digit number.
		for (int exp = 1; max/exp > 0; exp *= 10)
			countingSort(a, n, exp);
	}
	
	private void countingSort(Integer[] a, int n, int exp) {
		Integer[] output = new Integer[n];
		
		// Initialize a count array with zeroes.
		int[] count = new int[10];
		Arrays.fill(count, 0);
		
		// Store count of occurances in count[].
		for (int i = 0; i < n; i++)
			count[(a[i] / exp) % 10]++;
		
		// Change count[i] so that count[i] now contains
		// actual position of this digit in output[].
		for (int i = 1; i < 10; i++)
			count[i] += count[i - 1];
		
		// Build the output array.
		for (int i = n - 1; i >= 0; i--) {
			output[count[(a[i] / exp) % 10] - 1] = a[i];
			count[(a[i] / exp) % 10]--;
		}
		
		// Copy over the output array to the original one so that it now conatins sorted numbers according to current digit.
		for (int i = 0; i < n; i++)
			set(a, i, output[i]);
		
	}
}