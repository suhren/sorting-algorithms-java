package com.sorting.algorithms;

public class ShellSort<E extends Comparable<? super E>> extends SortingAlgorithm<E> {

	public ShellSort() {
		super("ShellSort");
	}

	@Override
	protected E[] sort(E[] a) throws SortAbortedException {
		if (a != null && a.length > 1)
			shellSort(a);
		return a;
	}

	private void shellSort(E[] a) throws SortAbortedException {
		int n = a.length;
		// Start with a big gap, then reduce the gap.
		for (int gap = n/2; gap > 0; gap /= 2) {
			message("Gap = " + gap);
			// Do a gapped insertion sort for this gap size.
			for (int i = gap; i < n; i++) {
				// Add a[i] to the elements that have been gap sorted.
	            // Save a[i] in temp and make a hole at position i.
				E temp = a[i];
				// Shift earlier gap-sorted elements up until the correct
	            // location for a[i] is found.
				int j;
				for (j = i; j >= gap && compare(a[j - gap], temp) > 0; j -= gap)
					set(a, j, a[j - gap]);
				
				// Put temp (the original a[i]) in its correct location.
				set(a, j, temp);
			}
		}
	}
}
