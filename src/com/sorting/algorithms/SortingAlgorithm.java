package com.sorting.algorithms;

import com.sorting.util.SortableArray;

public abstract class SortingAlgorithm {
	public void sortArray(SortableArray<?> a) {
		a.startSort();
		sort(a);
		a.endSort();
	};
	public abstract void sort(SortableArray<?> a);
}