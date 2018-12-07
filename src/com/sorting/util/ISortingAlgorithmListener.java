package com.sorting.util;

import com.sorting.algorithms.SortingAlgorithm;

public interface ISortingAlgorithmListener {
	<E> void sortGet(SortingAlgorithm<?> s, int i, E ei);
	<E> void sortSet(SortingAlgorithm<?> s, int i, E prev, E next);
	<E> void sortSwap(SortingAlgorithm<?> s, int i, int j, E ei, E ej);
	<E> void sortCompare(SortingAlgorithm<?> s, int i, int j, E ei, E ej, int c);
	void sortStart(SortingAlgorithm<?> s);
	void sortEnd(SortingAlgorithm<?> s);
}