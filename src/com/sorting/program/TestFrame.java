package com.sorting.program;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;
import javax.swing.JFormattedTextField;

import com.sorting.algorithms.*;
import com.sorting.util.*;

public class TestFrame extends JFrame implements ISortingAlgorithmListener {
	private static final long serialVersionUID = 1L;
	private boolean startEndPrintout = true;
	private boolean eventPrintout = false;
	private boolean arrayPrintout = false;
	private boolean drawWhileSorting = true;
	JComboBox<String> comboBoxSort;
	JCheckBox checkEvents, checkStartEnd, checkArray, checkDraw;
	JButton buttonSort, buttonGenerate, buttonRead, buttonClear;
	JFormattedTextField textGenerateNumber, textGenerateLow, textGenerateHigh, textDelay;
	SortGraphicPanel graphicsPanel;
	JTextArea textArea;
    JScrollPane scrollArea;
	JPanel bottomPanel;
	
	Integer[] data;
	SortingAlgorithm<Integer> sortingAlgorithm;
	
	int delay = 0;
	
	public TestFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		graphicsPanel = new SortGraphicPanel(1000, 500);
		this.getContentPane().add(graphicsPanel, BorderLayout.CENTER);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setColumns(50);
		
		scrollArea = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.getContentPane().add(scrollArea, BorderLayout.EAST);
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		
		checkStartEnd = new JCheckBox("Enable start end events");
		checkStartEnd.addActionListener(e -> startEndPrintout = (checkStartEnd.isSelected()));
		checkStartEnd.setSelected(startEndPrintout);
		bottomPanel.add(checkStartEnd);
		
		checkEvents = new JCheckBox("Enable array operation events");
		checkEvents.addActionListener(e -> eventPrintout = (checkEvents.isSelected()));
		checkEvents.setSelected(eventPrintout);
		bottomPanel.add(checkEvents);
		
		checkArray = new JCheckBox("Enable array printout");
		checkArray.addActionListener(e -> arrayPrintout = (checkArray.isSelected()));
		checkArray.setSelected(arrayPrintout);
		bottomPanel.add(checkArray);
		
		checkDraw = new JCheckBox("Enable array drawing");
		checkDraw.addActionListener(e -> drawWhileSorting = (checkDraw.isSelected()));
		checkDraw.setSelected(drawWhileSorting);
		bottomPanel.add(checkDraw);
		
		String[] names = { "BubbleSort", "HeapSort", "InsertionSort", "MergeSort", "MergeSortInPlace", "QuickSort", "SelectionSort" };
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
		
		buttonRead = new JButton("Load CSV");
		buttonRead.addActionListener(e -> readIntegerFromFile());
		bottomPanel.add(buttonRead);
		
		textDelay = new JFormattedTextField(NumberFormat.getNumberInstance());
		textDelay.setValue(delay);
		textDelay.setColumns(3);
		textDelay.addPropertyChangeListener(e -> setDelay());
		bottomPanel.add(textDelay);
		
		buttonClear = new JButton("Clear text area");
		buttonClear.addActionListener(e -> textArea.setText(""));
		bottomPanel.add(buttonClear);
		
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
        else if (name.equals("MergeSortInPlace"))
        	sortingAlgorithm = new MergeSortInPlace<>();
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
		
		printLine("Generated array with " + n + " elements from " + low + " to " + high + ".");
		graphicsPanel.setData(data);
		graphicsPanel.setLastAccessed(-1);
		graphicsPanel.refresh();
	}

	private void sort() {
		if (data != null && data.length > 0)
			sortingAlgorithm.sortArray(data, this);
		else
			print("No data specified.");
	}
	
	public void readIntegerFromFile() {
		JFileChooser fc = new JFileChooser();
		fc.setAcceptAllFileFilterUsed(false);
		fc.addChoosableFileFilter(new FileNameExtensionFilter("Text files", "txt"));
		fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int returnVal = fc.showOpenDialog(this);
		
		 if (returnVal == JFileChooser.APPROVE_OPTION) {
			Scanner sc = null;
			try {
				sc = new Scanner(fc.getSelectedFile());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			List<Integer> input = new ArrayList<Integer>();
			while (sc.hasNextInt())
				input.add(sc.nextInt());
				
			Integer[] temp = new Integer[input.size()];
			for (int i = 0; i < input.size(); i++)
				temp[i] = input.get(i);
				
			data = temp;
			printLine("Loaded array with " + data.length + " elements from " + fc.getSelectedFile().getName() + ".");
			graphicsPanel.setData(data);
			graphicsPanel.setLastAccessed(-1);
			graphicsPanel.refresh();
		}
	}

	@Override
	public <E> void sortMessage(SortingAlgorithm<?> s, String message) {
		if (eventPrintout)
			printStatus(s, message);
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
		
		if (drawWhileSorting) {
			graphicsPanel.setLastAccessed(i);
			graphicsPanel.refresh();
			if (delay > 0) {
				try {
					Thread.sleep(delay);
		        } catch(Exception e) {
		        	
		        }
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
			printStatus(s, "Started sort of array with " + s.data().length + " elements.");
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
		if (drawWhileSorting) {
			Integer[] res = new Integer[s.data().length];
			for (int i = 0; i < res.length; i++)
				res[i] = (Integer)s.data()[i];
			
			this.data = res;
			
			graphicsPanel.setData(res);
			graphicsPanel.refresh();
		}
	}
	
	private void printStatus(SortingAlgorithm<?> s, String t) {
		printLine(s.getElapsedTime() + "ms: " + s.getID() + ": " + t);
		graphicsPanel.refresh();
	}
	
	private void printLine(String s) {
		print(s + "\n");
	}
	
	private void print(String s) {
		textArea.append(s);
		scrollArea.paintImmediately(scrollArea.getBounds());
		textArea.paintImmediately(textArea.getBounds());
	}
}