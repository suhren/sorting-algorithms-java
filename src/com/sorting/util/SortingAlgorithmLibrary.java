package com.sorting.util;

import java.util.Arrays;
import java.util.function.Supplier;

import com.sorting.algorithms.BubbleSort;
import com.sorting.algorithms.CountingSort;
import com.sorting.algorithms.HeapSort;
import com.sorting.algorithms.InsertionSort;
import com.sorting.algorithms.MergeSort;
import com.sorting.algorithms.MergeSortInPlace;
import com.sorting.algorithms.QuickSort;
import com.sorting.algorithms.RadixSort;
import com.sorting.algorithms.SelectionSort;
import com.sorting.algorithms.ShellSort;

public class SortingAlgorithmLibrary {
	
	public enum Algorithm implements Supplier<SortingAlgorithm<Integer>> {
		BUBBLE_SORT			("BubbleSort", 			() -> new BubbleSort<Integer>()),
		HEAP_SORT			("HeapSort", 			() -> new HeapSort<Integer>()),
		INSERTION_SORT		("InsertionSort", 		() -> new InsertionSort<Integer>()),
		MERGE_SORT			("MergeSort", 			() -> new MergeSort<Integer>()),
		MERGE_SORT_IN_PLACE	("MergeSortInPlace", 	() -> new MergeSortInPlace<Integer>()),
		QUICK_SORT			("QuickSort", 			() -> new QuickSort<Integer>()),
		SHELL_SORT			("ShellSort", 			() -> new ShellSort<Integer>()),
		SELECTION_SORT		("SelectionSort", 		() -> new SelectionSort<Integer>()),
		COUNTING_SORT		("CountingSort", 		() -> new CountingSort()),
		RADIX_SORT			("RadixSort", 			() -> new RadixSort());
		
		private final String code;
		private final Supplier<SortingAlgorithm<Integer>> supplier;
		
		private Algorithm(String code, final Supplier<SortingAlgorithm<Integer>> supplier) {
			this.code = code;
			this.supplier = supplier;
		}
		public String code() {
			return code;
		}
		public static Algorithm fromCode(String code) {
			if (code != null)
				for (Algorithm a : Algorithm.values())
					if (code.equalsIgnoreCase(a.code))
						return a;
			return null;
		}
		@Override
		public SortingAlgorithm<Integer> get() {
			return supplier.get();
		}
	}
	
	public static String[] getAlgorithmNames() {
		return Arrays.stream(Algorithm.values()).map(Algorithm::code).toArray(String[]::new);
	}
	
	public static SortingAlgorithm<Integer> getAlgorithm(String name) {
		return Algorithm.fromCode(name).get();
	}
}