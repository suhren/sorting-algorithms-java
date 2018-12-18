package com.sorting.util;

/**
 * Utility function library.
 * @author Adam
 *
 */
public class Utils {
	/**
	 * Return a string representation of an array.
	 * @param data The array represent as a string.
	 * @return All the elements in the array listed as a single string.
	 */
	public static <E> String arrayToString(E[] data) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < data.length - 1; i++)
			s.append(data[i].toString() + " ");
		s.append(data[data.length - 1]);
		return s.toString();
	}
}
