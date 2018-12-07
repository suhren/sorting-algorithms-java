package com.sorting.program;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JFormattedTextField;

import com.sorting.algorithms.*;
import com.sorting.util.*;

public class TestFrame extends JFrame implements ISortingAlgorithmListener {
	private static final long serialVersionUID = 1L;
	private boolean eventPrintout = false;
	private boolean startEndPrintout = true;
	private boolean arrayPrintout = false;
	JComboBox<String> comboBoxSort;
	JButton buttonSort;
	JButton buttonGenerate;
	JFormattedTextField textGenerateNumber;
	JFormattedTextField textGenerateLow;
	JFormattedTextField textGenerateHigh;
	JFormattedTextField textDelay;
	SortGraphicPanel graphicsPanel;
	JPanel bottomPanel;
	
	Integer[] data;
	SortingAlgorithm<Integer> sortingAlgorithm;
	
	int delay = 0;
	
	public TestFrame() {
		graphicsPanel = new SortGraphicPanel(800, 600);
		this.getContentPane().add(graphicsPanel, BorderLayout.CENTER);
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		
		String[] names = { "BubbleSort", "HeapSort", "InsertionSort", "MergeSort", "QuickSort", "SelectionSort" };
		comboBoxSort = new JComboBox<>(names);
		comboBoxSort.addActionListener(e -> pick());
		bottomPanel.add(comboBoxSort);
		
		buttonSort = new JButton("Sort");
		buttonSort.addActionListener(e -> sort());
		buttonGenerate = new JButton("Generate");
		buttonGenerate.addActionListener(e -> generate());
		bottomPanel.add(buttonSort);
		bottomPanel.add(buttonGenerate);
		
		textGenerateNumber = new JFormattedTextField(NumberFormat.getNumberInstance());
		textGenerateNumber.setValue(200);
		textGenerateNumber.setColumns(10);
		bottomPanel.add(textGenerateNumber);
		
		textGenerateLow = new JFormattedTextField(NumberFormat.getNumberInstance());
		textGenerateLow.setValue(0);
		textGenerateLow.setColumns(10);
		bottomPanel.add(textGenerateLow);
		
		textGenerateHigh = new JFormattedTextField(NumberFormat.getNumberInstance());
		textGenerateHigh.setValue(100);
		textGenerateHigh.setColumns(10);
		bottomPanel.add(textGenerateHigh);
		
		textDelay = new JFormattedTextField(NumberFormat.getNumberInstance());
		textDelay.setValue(delay);
		textDelay.setColumns(3);
		textDelay.addPropertyChangeListener(e -> setDelay());
		bottomPanel.add(textDelay);
		
		this.getContentPane().add(bottomPanel, BorderLayout.PAGE_END);
		
		pick();
		this.pack();
	}
	
	private void setDelay() {
		delay = ((Number)textDelay.getValue()).intValue();
	}

	private void pick() {
        String name = (String)comboBoxSort.getSelectedItem();
        if (name.equals("BubbleSort"))
        	sortingAlgorithm = new BubbleSort<>();
        else if (name.equals("HeapSort"))
        	sortingAlgorithm = new HeapSort<>();
        else if (name.equals("InsertionSort"))
        	sortingAlgorithm = new InsertionSort<>();
        else if (name.equals("MergeSort"))
        	sortingAlgorithm = new MergeSort<>();
        else if (name.equals("QuickSort"))
        	sortingAlgorithm = new QuickSort<>();
        else if (name.equals("SelectionSort"))
        	sortingAlgorithm = new SelectionSort<>();
	}

	private void generate() {
		int n = ((Number)textGenerateNumber.getValue()).intValue();
		int low = ((Number)textGenerateLow.getValue()).intValue();
		int high = ((Number)textGenerateHigh.getValue()).intValue();
		
		data = new Integer[n];
		Random rand = new Random();
		for (int i = 0; i < n; i++)
			data[i] = low + rand.nextInt(high - low + 1);

		graphicsPanel.setData(data);
		graphicsPanel.setLastAccessed(0);
		graphicsPanel.refresh();
	}

	private void sort() {
		sortingAlgorithm.sortArray(data, this);
	}
	
	public Integer[] readFromFile(String filePath) {
		File file = new File(filePath);
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		List<Integer> data = new ArrayList<Integer>();
		while (sc.hasNextInt())
			data.add(sc.nextInt());
		return (Integer[]) data.toArray();
	}

	@Override
	public <E> void sortGet(SortingAlgorithm<?> s, int i, E ei) {
		if (eventPrintout)
			printStatus(s, "Get " + i + ":" + ei);
	}

	@Override
	public <E> void sortSet(SortingAlgorithm<?> s, int i, E prev, E next) {
		if (eventPrintout)
			printStatus(s, "Set " + i + ":" + prev + " to " + i + ":" + next);
	}

	@Override
	public <E> void sortSwap(SortingAlgorithm<?> s, int i, int j, E ei, E ej) {
		if (eventPrintout) {
			printStatus(s, "Swapped " + i + ":" + ei + " <-> " + j + ":" + ej);
			if (arrayPrintout)
				printStatus(s, s.toString());
		}
		
		graphicsPanel.setLastAccessed(i);
		graphicsPanel.refresh();

		if (delay > 0) {
			try {
				Thread.sleep(delay);
	        } catch(Exception e) {
	        	
	        }
		}
	}

	@Override
	public <E> void sortCompare(SortingAlgorithm<?> s, int i, int j, E ei, E ej, int c) {
		if (eventPrintout) {
			String t = (c > 0) ? " > " : (c < 0) ? " < " : " == ";
			printStatus(s, "Compared " + i + ":" + ei + t + j + ":" + ej);
		}
	}

	@Override
	public void sortStart(SortingAlgorithm<?> s) {
		if (startEndPrintout) {
			printStatus(s, "Started sort");
			if (arrayPrintout)
				printStatus(s, s.toString());
		}
	}

	@Override
	public void sortEnd(SortingAlgorithm<?> s) {
		if (startEndPrintout) {
			printStatus(s, "Array sorted with " + s.nGet() + " Gets, " + s.nSet() + " Sets, " + s.nComp() + " Comparisions, and " + s.nSwap() + " Swaps.");
			if (arrayPrintout)
				printStatus(s, s.toString());
		}
	}
	
	private void printStatus(SortingAlgorithm<?> s, String t) {
		System.out.println(s.getElapsedTime() + ": " + s.getID() + ": " + t);
	}
}