package com.sorting.algorithms;

import com.sorting.util.SortAbortedException;
import com.sorting.util.SortingAlgorithm;

public class MergeSortInPlace<E extends Comparable<? super E>> extends SortingAlgorithm<E> {

	public MergeSortInPlace() {
		super("MergeSortInPlace");
	}

	@Override
	protected E[] sort(E[] a) throws SortAbortedException {
		if (a != null && a.length > 1)
			mergeSortInPlace(a, 0, a.length - 1);
		return a;
	}

	private void mergeSortInPlace(E[] a, int min, int max) throws SortAbortedException {
		if (max - min == 0) {
			return;
		}
		else if (max - min == 1) {
			if (compare(a, min, max) > 0)
				swap(a, min, max);
		}
		else {
			int mid = (min + max) >> 1;
			mergeSortInPlace(a, min, mid);
			mergeSortInPlace(a, mid + 1, max);
			merge(a, min, max, mid);
		}
	}

	/**
	 * The merge method combines the two sorted portions of the array.
	 * @param a The array being merged.
	 * @param min The minimum index to be merged.
	 * @param max The maximum index to be merged.
	 * @param mid The mid point in the section of the array to be merged. It's also the last index of the left portion of the array
	 * and mid+1 is the first index in the right portion.
	 */
	private void merge(E[] a, int min, int max, int mid) throws SortAbortedException {
		  int left = min;  int right = mid+1;
	      // One extra check:  can we SKIP the merge?
	      if (compare(a, mid, right) <= 0)
	         return;

	      while (left <= mid && right <= max)
	      {  // Select from left:  no change, just advance left
	         if (compare(a, left, right) <= 0)
	            left++;
	         // Select from right:  rotate [left..right] and correct
	         else { 
				E tmp = a[right];     // Will move to [left]
	             System.arraycopy(a, left, a, left+1, right-left);
				a[left] = tmp;
//				for (int i = 0; i < right - left; i++)
//					swap(a, right - i, right - i - 1);
	            // EVERYTHING has moved up by one
	            left++;  mid++;  right++;
	         }
	      }
	      // Whatever remains in [right..last] is in place
	}
}