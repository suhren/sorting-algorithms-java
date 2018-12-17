package com.sorting.algorithms;

import com.sorting.util.SortingAlgorithmListener;

public abstract class SortingAlgorithm<E extends Comparable<? super E>> {
	private SortingAlgorithmListener listener; 
	private E[] data;
	private int nComp = 0;
	private int nSwap = 0;
	private int nGet = 0;
	private int nSet = 0;
	private String id = null;
	private long timeStart = 0;
	private long timeEnd = 0;
	private boolean sorted = false;
	private boolean started = false;
	
	public SortingAlgorithm(String id) {
		this.id = id;
	}
	
	public E[] sortArray(E[] a, SortingAlgorithmListener listener) {
		this.timeStart = 0;
		this.timeEnd = 0;
		this.sorted = false;
		this.started = false;
		this.nComp = 0;
		this.nSwap = 0;
		this.nGet = 0;
		this.nSet = 0;
		this.data = a;
		this.listener = listener;
		startSort();
		this.data= sort(a);
		endSort();
		return data;
	};
	protected abstract E[] sort(E[] a);
	
	public int nComp() { return nComp; }
	public int nSwap() { return nSwap; }
	public int nGet() { return nGet; }
	public int nSet() { return nSet; }
	public String getID() { return id; }
	public long getElapsedTime( ) { 
		if (sorted)
			return timeEnd - timeStart;
		else if (started)
			return System.currentTimeMillis() - timeStart;
		else
			return 0;
	}
	public E[] data() { return data; }
	
	protected void message(String s) {
		if (listener != null)
			listener.sortMessage(this, s);
	}
 
	protected E get(E[] a, int i) {
		nGet++;
		if (listener != null)
			listener.sortGet(this, i, a[i]);
		return a[i];
	}
	protected void set(E[] a, int i, E value) {
		E prev = a[i];
		nSet++;
		a[i] = value;
		if (listener != null)
			listener.sortSet(this, i, prev, value);
	}
	protected void swap(E[] a, int i, int j) {
		nSwap++;
		E temp = a[i];
		a[i] = a[j];
		a[j] = temp;
		if (listener != null)
			listener.sortSwap(this, i, j, a[j], a[i]);
	}
	protected int compare(E[] a, int i, int j) {
		nComp++;
		int c = a[i].compareTo(a[j]);
		if (listener != null)
			listener.sortCompare(this, i, j, a[i], a[j], c);
		return c;
	}
	protected int compare(E ei, E ej) {
		nComp++;
		int c = ei.compareTo(ej);
		if (listener != null)
			listener.sortCompare(this, ei, ej, c);
		return c;
	}
	protected void startSort() {
		if (listener != null)
			listener.sortStart(this);
		started = true;
		timeStart = System.currentTimeMillis();
	}
	protected void endSort() {
		sorted = true;
		timeEnd = System.currentTimeMillis();
		if (listener != null)
			listener.sortEnd(this);
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("[ ");
		for (int i = 0; i < data.length - 1; i++)
			s.append(data[i].toString() + ", ");
		s.append(data[data.length - 1] + " ]");
		return s.toString();
	}
}