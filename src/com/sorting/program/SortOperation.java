package com.sorting.program;

import com.sorting.algorithms.SortingAlgorithm;
import com.sorting.util.ISortingAlgorithmListener;

public class SortOperation extends Thread {

	private SortingAlgorithm<Integer> sortingAlgorithm;
	private Integer[] data;
	private ISortingAlgorithmListener listener;
	
	public SortOperation(SortingAlgorithm<Integer> sortingAlgorithm, Integer[] data, ISortingAlgorithmListener listener) {
		this.sortingAlgorithm = sortingAlgorithm;
		this.data = data;
		this.listener = listener;
		
	}
	@Override
	public void run() {
		try {
			System.out.println("Thread " + Thread.currentThread().getId() + " is running!");
			sortingAlgorithm.sortArray(data, listener);
		}
		catch(Exception e) {
			System.out.println("Exception is caught: " + e.getMessage());
		}
	}
}
