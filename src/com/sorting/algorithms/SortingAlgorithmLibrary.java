package com.sorting.algorithms;

public class SortingAlgorithmLibrary {
	
	private static final String[] names = { "BubbleSort", "HeapSort", "InsertionSort", "MergeSort", "MergeSortInPlace", "QuickSort", "ShellSort", "SelectionSort", "CountingSort", "RadixSort" };
	
	public static String[] getAlgorithmNames() {
		return names;
	}
	
	public static SortingAlgorithm<Integer> getAlgorithm(String name) {
		switch (name) {
		    case "BubbleSort":
		    	return new BubbleSort<>();
		    case "HeapSort":
		    	return new HeapSort<>();
		    case "InsertionSort":
		    	return new InsertionSort<>();
		    case "MergeSort":
		    	return new MergeSort<>();
		    case "MergeSortInPlace":
		    	return new MergeSortInPlace<>();
		    case "QuickSort":
		    	return new QuickSort<>();
		    case "ShellSort":
		    	return new ShellSort<>();
		    case "SelectionSort":
		    	return new SelectionSort<>();
		    case "CountingSort":
		    	return new CountingSort();
		    case "RadixSort":
		    	return new RadixSort();
		    default:
		    	throw new IllegalArgumentException("No such algorihm");
		}
	}
}