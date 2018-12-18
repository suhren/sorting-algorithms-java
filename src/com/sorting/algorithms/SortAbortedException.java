package com.sorting.algorithms;

public class SortAbortedException extends Exception {
	private static final long serialVersionUID = 1L;

	public SortAbortedException() {
		super("The sorting operation was aborted.");
	}
}