package com.sorting.algorithms;

public class MergeSort<E extends Comparable<? super E>> extends SortingAlgorithm<E> {

	public MergeSort() {
		super("MergeSort");
	}

	@Override
	protected E[] sort(E[] a) {
		if (a != null && a.length > 0)
			return mergeSort(a);
		return a;
	}
	
	protected E[] mergeSort(E[] a) {
		if (a.length >= 2) {
			boolean odd = a.length % 2 != 0;
			int l1 = a.length / 2;
			int l2 = l1 + (odd ? 1 : 0);
			
			E[] a1 = (E[]) new Comparable[l1];
			E[] a2 = (E[]) new Comparable[l2];
			
			for (int i = 0; i < l1; i++) {
				set(a1, i, get(a, i));
				set(a2, i, get(a, l1 + i));
			}
			if (odd)
				set(a2, l2 - 1, get(a, a.length - 1));
			
			return merge(mergeSort(a1), mergeSort(a2));
		}
		return a;
	}
	
	private E[] merge(E[] a1, E[] a2) {
		int a1High = a1.length - 1;
		int a2High = a2.length - 1;
		E[] res = (E[]) new Comparable[a1.length + a2.length];
		
		int resIndex = 0;
		int a1Index = 0;
		int a2Index = 0;
		boolean a1End = a1Index > a1.length;
		boolean a2End = a2Index > a2.length;
		
		while (!a1End && !a2End) {
			if (compare(a1[a1Index], a2[a2Index]) < 0) {
				set(res, resIndex, get(a1, a1Index));
				a1Index++;
				a1End = (a1Index > a1High);
			}
			else {
				set(res, resIndex, get(a2, a2Index));
				a2Index++;
				a2End = (a2Index > a2High);
			}
			resIndex++;
		}
		
		if (a1End) {
			while (resIndex < res.length) {
				set(res, resIndex, get(a2, a2Index));
				resIndex++;
				a2Index++;
			}
		}
		else {
			while (resIndex < res.length) {
				set(res, resIndex, get(a1, a1Index));
				resIndex++;
				a1Index++;
			}
		}
		
		return res;
	}
}