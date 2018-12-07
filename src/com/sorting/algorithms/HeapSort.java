package com.sorting.algorithms;

public class HeapSort<E extends Comparable<? super E>> extends SortingAlgorithm<E> {

	public HeapSort() {
		super("HeapSort");
	}

	@Override
	protected E[] sort(E[] a) {
		if (a != null && a.length > 1)
			hs(a, a.length);
		return a;
	}
	
	private void hs(E[] a, int n) {
		// Build heap (rearrange array) 
		for (int i = n / 2 - 1; i >= 0; i--)
			heapify(a, n, i);
		// One by one extract an element from heap 
		for (int i = n - 1; i >= 0; i--) {
			// Move current root to end 
			swap(a, 0, i);
			// Call max heapify on the reduced heap 
			heapify(a, i, 0);
		}
	}
	
	/**
	 * To heapify a subtree rooted with node i which is an index in a. n is size of heap 
	 * @param arr
	 * @param n
	 * @param i
	 */
    void heapify(E[] a, int n, int i) { 
        int largest = i; // Initialize largest as root 
        int l = 2*i + 1; // left = 2*i + 1 
        int r = 2*i + 2; // right = 2*i + 2 
  
        // If left child is larger than root 
        if (l < n && compare(a, l, largest) > 0) 
            largest = l; 
  
        // If right child is larger than largest so far 
        if (r < n && compare(a, r, largest) > 0) 
            largest = r; 
  
        // If largest is not root 
        if (largest != i) { 
        	swap(a, i, largest);
            // Recursively heapify the affected sub-tree 
            heapify(a, n, largest); 
        } 
    } 
}