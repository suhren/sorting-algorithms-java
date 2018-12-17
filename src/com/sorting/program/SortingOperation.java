package com.sorting.program;

import java.util.ArrayList;
import java.util.List;

import com.sorting.algorithms.SortingAlgorithm;
import com.sorting.util.SortingAlgorithmListener;
import com.sorting.util.SortingOperationListener;
import com.sorting.util.Utils;

public class SortingOperation extends Thread implements SortingAlgorithmListener {

	private SortingAlgorithm<Integer> sortingAlgorithm;
	private SortingOperationListener listener;
	private SortGraphicPanel panel;
	
	private Integer[] data;
	private int delay;
	
	private boolean bufferEventStartEnd = true;
	private boolean bufferEventMessage = false;
	private boolean bufferEventOperation = false;
	private boolean bufferEventArray = false;
	
	private List<String> eventBuffer = new ArrayList<>();
	private String outputString;
	
	public SortingOperation(SortingAlgorithm<Integer> sortingAlgorithm, Integer[] data, SortingOperationListener listener, SortGraphicPanel panel, int delay, boolean logStartEnd, boolean logEvent, boolean logArray) {
		this.sortingAlgorithm = sortingAlgorithm;
		this.data = data;
		this.listener = listener;
		this.panel = panel;
		this.delay = delay;
		this.bufferEventStartEnd = logStartEnd;
		this.bufferEventMessage = logEvent;
		this.bufferEventOperation = logEvent;
		this.bufferEventArray = logArray;
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
			System.out.println("Thread " + Thread.currentThread().getId() + " is running!");
			System.out.println("Started sort.");
			sortingAlgorithm.sortArray(data, this);
			System.out.println("Finished sort.");
		}
		catch(Exception e) {
			System.out.println("Exception is caught: " + e.getMessage());
		}
	}
	
	@Override
	public <E> void sortMessage(SortingAlgorithm<?> s, String message) {
		if (bufferEventMessage)
			eventBuffer.add(generateStatusString(s, message));
		listener.operationEvent(this);
	}	
	@Override
	public <E> void sortGet(SortingAlgorithm<?> s, int i, E ei) {
		if (bufferEventOperation)
			eventBuffer.add(generateStatusString(s, "Get " + i + ":" + ei));
		listener.operationEvent(this);
	}
	@Override
	public <E> void sortSet(SortingAlgorithm<?> s, int i, E prev, E next) {
		if (bufferEventOperation)
			eventBuffer.add(generateStatusString(s, "Set " + i + ":" + prev + " to " + i + ":" + next));
		
		panel.buffer(i);
		panel.repaint();
		listener.operationEvent(this);
		listener.operationRequestRedraw(this);
		
		delay();
	}
	@Override
	public <E> void sortSwap(SortingAlgorithm<?> s, int i, int j, E ei, E ej) {
		if (bufferEventOperation)
			eventBuffer.add(generateStatusString(s, "Swapped " + i + ":" + ei + " <-> " + j + ":" + ej));
		
		if (bufferEventArray)
			eventBuffer.add(generateStatusString(s, s.toString()));
		
		panel.buffer(i, j);
		panel.repaint();
		listener.operationEvent(this);
		listener.operationRequestRedraw(this);
		
		delay();
	}
	@Override
	public <E> void sortCompare(SortingAlgorithm<?> s, int i, int j, E ei, E ej, int c) {
		if (bufferEventOperation) {
			String t = (c > 0) ? " > " : (c < 0) ? " < " : " == ";
			eventBuffer.add(generateStatusString(s, "Compared " + i + ":" + ei + t + j + ":" + ej));
		}
		
		panel.buffer(i);
		panel.repaint();
		listener.operationEvent(this);
		
		delay();
	}
	@Override
	public <E> void sortCompare(SortingAlgorithm<?> s, E ei, E ej, int c) {
		if (bufferEventOperation) {
			String t = (c > 0) ? " > " : (c < 0) ? " < " : " == ";
			eventBuffer.add(generateStatusString(s, "Compared " + ei + t + ej));
		}
		listener.operationEvent(this);
	}
	@Override
	public void sortStart(SortingAlgorithm<?> s) {
		if (bufferEventStartEnd)
			eventBuffer.add(generateStatusString(s, "Started sort of array with " + s.data().length + " elements."));
		if (bufferEventArray)
			eventBuffer.add(generateStatusString(s, s.toString()));
		listener.operationEvent(this);
	}
	@Override
	public void sortEnd(SortingAlgorithm<?> s) {
		outputString = Utils.arrayToString(data);
		
		if (bufferEventStartEnd)
			eventBuffer.add(generateStatusString(s, "Sorted with " + s.nGet() + " Gets, " + s.nSet() + " Sets, " + s.nComp() + " Comparisions, and " + s.nSwap() + " Swaps."));
		if (bufferEventArray)
			eventBuffer.add(generateStatusString(s, s.toString()));
		listener.operationEvent(this);
		
		Integer[] res = new Integer[s.data().length];
		for (int i = 0; i < res.length; i++)
			res[i] = (Integer)s.data()[i];
		
		this.data = res;
		listener.operationDone(this);
	}
	
	private String generateStatusString(SortingAlgorithm<?> s, String t) {
		return (s.getElapsedTime() + "ms: " + s.getID() + ": " + t);
	}
	
	private void delay() {
		if (delay > 0) {
			try {
				Thread.sleep(delay);
	        } catch(Exception e) {
	        	
	        }
		}
	}
	
	public List<String> getEventBuffer() {
		return eventBuffer;
	}
	
	public String getOutput() {
		return outputString;
	}
}