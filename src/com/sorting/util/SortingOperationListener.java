package com.sorting.util;

import com.sorting.program.SortingOperation;

public interface SortingOperationListener {
	void operationEvent(SortingOperation op, String message);
	void operationRequestRedraw(SortingOperation op, int i);
	void operationRequestRedraw(SortingOperation op, int i, int j);
	void operationRequestRedrawAll(SortingOperation op);
}
