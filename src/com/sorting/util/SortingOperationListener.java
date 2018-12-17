package com.sorting.util;

import com.sorting.program.SortingOperation;

public interface SortingOperationListener {
	void operationEvent(SortingOperation op);
	void operationRequestRedraw(SortingOperation op);
}
