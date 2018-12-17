package com.sorting.util;

public class Utils {
	public static <E> String arrayToString(E[] data) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < data.length - 1; i++)
			s.append(data[i].toString() + " ");
		s.append(data[data.length - 1]);
		return s.toString();
	}
}
