package com.sorting.program;

import com.sorting.util.SortAbortedException;
import com.sorting.util.SortingAlgorithm;
import com.sorting.util.SortingAlgorithmListener;
import com.sorting.util.SortingOperationListener;

public class SortingOperation extends Thread implements SortingAlgorithmListener {

	private SortingAlgorithm<Integer> sortingAlgorithm;
	private SortingOperationListener listener;

	private Integer[] data;
	private int delay;

	private boolean bufferEventStartEnd = true;
	private boolean bufferEventMessage = false;
	private boolean bufferEventOperation = false;
	private boolean bufferEventArray = false;

	public SortingOperation(SortingAlgorithm<Integer> sortingAlgorithm, Integer[] data,
			SortingOperationListener listener, int delay, boolean logStartEnd, boolean logEvent, boolean logArray) {
		this.sortingAlgorithm = sortingAlgorithm;
		this.data = data;
		this.listener = listener;
		this.delay = delay;
		this.bufferEventStartEnd = logStartEnd;
		this.bufferEventMessage = logEvent;
		this.bufferEventOperation = logEvent;
		this.bufferEventArray = logArray;
	}

	public void abort() {
		sortingAlgorithm.abort();
	}
	public void setBufferEventStartEnd(boolean enabled) {
		this.bufferEventStartEnd = enabled;
	}

	public void bufferEventMessage(boolean enabled) {
		this.bufferEventMessage = enabled;
	}

	public void bufferEventOperation(boolean enabled) {
		this.bufferEventStartEnd = enabled;
	}

	public void bufferEventArray(boolean enabled) {
		this.bufferEventStartEnd = enabled;
	}

	@Override
	public void run() {
		try {
			sortingAlgorithm.sortArray(data, this);
		} catch (SortAbortedException e) {
			listener.operationEvent(this, generateStatusString(sortingAlgorithm, e.getMessage()));
			listener.operationAborted(this);
		} 
		catch (Exception e) {
			System.out.println("Exception is caught: " + e.getMessage());
		}
	}

	@Override
	public <E> void sortMessage(SortingAlgorithm<?> s, String message) {
		if (bufferEventMessage)
			listener.operationEvent(this, generateStatusString(s, message));
	}

	@Override
	public <E> void sortGet(SortingAlgorithm<?> s, int i, E ei) {
		if (bufferEventOperation)
			listener.operationEvent(this, generateStatusString(s, "Get " + i + ":" + ei));
	}

	@Override
	public <E> void sortSet(SortingAlgorithm<?> s, int i, E prev, E next) {
		if (bufferEventOperation)
			listener.operationEvent(this, generateStatusString(s, "Set " + i + ":" + prev + " to " + i + ":" + next));

		listener.operationRequestRedraw(this, i);

		delay();
	}

	@Override
	public <E> void sortSwap(SortingAlgorithm<?> s, int i, int j, E ei, E ej) {
		if (bufferEventOperation)
			listener.operationEvent(this, generateStatusString(s, "Swapped " + i + ":" + ei + " <-> " + j + ":" + ej));

		if (bufferEventArray)
			listener.operationEvent(this, generateStatusString(s, s.toString()));

		listener.operationRequestRedraw(this, i, j);
		delay();
	}

	@Override
	public <E> void sortCompare(SortingAlgorithm<?> s, int i, int j, E ei, E ej, int c) {
		if (bufferEventOperation) {
			String t = (c > 0) ? " > " : (c < 0) ? " < " : " == ";
			listener.operationEvent(this, generateStatusString(s, "Compared " + i + ":" + ei + t + j + ":" + ej));
		}
		listener.operationRequestRedraw(this, i, j);
		delay();
	}

	@Override
	public <E> void sortCompare(SortingAlgorithm<?> s, E ei, E ej, int c) {
		if (bufferEventOperation) {
			String t = (c > 0) ? " > " : (c < 0) ? " < " : " == ";
			listener.operationEvent(this, generateStatusString(s, "Compared " + ei + t + ej));
		}
	}

	@Override
	public void sortStart(SortingAlgorithm<?> s) {
		if (bufferEventStartEnd)
			listener.operationEvent(this,
					generateStatusString(s, "Started sort of array with " + s.data().length + " elements."));
		if (bufferEventArray)
			listener.operationEvent(this, generateStatusString(s, s.toString()));
	}

	@Override
	public void sortEnd(SortingAlgorithm<?> s) {
		if (bufferEventStartEnd)
			listener.operationEvent(this, generateStatusString(s, "Sorted with " + s.nGet() + " Gets, " + s.nSet()
					+ " Sets, " + s.nComp() + " Comparisions, and " + s.nSwap() + " Swaps."));
		if (bufferEventArray)
			listener.operationEvent(this, generateStatusString(s, s.toString()));

		listener.operationRequestRedrawAll(this);
		
		Integer[] res = new Integer[s.data().length];
		for (int i = 0; i < res.length; i++)
			res[i] = (Integer) s.data()[i];

		this.data = res;
		listener.operationDone(this);
	}

	private String generateStatusString(SortingAlgorithm<?> s, String t) {
		return (s.getElapsedTime() + "ms: " + s.getID() + ": " + t);
	}

	private void delay() {
		if (delay > 0) {
			long start = System.nanoTime();
			long end = 0;
			do {
				end = System.nanoTime();
			} while (start + delay * 1000 >= end);
		}
	}
}