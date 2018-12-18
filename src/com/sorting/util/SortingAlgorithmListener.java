package com.sorting.util;

public interface SortingAlgorithmListener {
	<E> void sortMessage(SortingAlgorithm<?> s, String message);
	<E> void sortGet(SortingAlgorithm<?> s, int i, E ei);
	<E> void sortSet(SortingAlgorithm<?> s, int i, E prev, E next);
	<E> void sortSwap(SortingAlgorithm<?> s, int i, int j, E ei, E ej);
	<E> void sortCompare(SortingAlgorithm<?> s, int i, int j, E ei, E ej, int c);
	void sortStart(SortingAlgorithm<?> s);
	void sortEnd(SortingAlgorithm<?> s);
	<E> void sortCompare(SortingAlgorithm<?> s, E ei, E ej, int c);
}