package com.sorting.program;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
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
	JTextArea textAreaLog;
	JTextArea textAreaInput;
	JTextArea textAreaOutput;
    JScrollPane scrollAreaLog;
    JScrollPane scrollAreaInput;
    JScrollPane scrollAreaOutput;
	JPanel bottomPanel;
	JPanel controlPanel;
	
	Integer[] data;
	SortingAlgorithm<Integer> sortingAlgorithm;
	
	int delay = 0;
	
	public TestFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		graphicsPanel = new SortGraphicPanel(1000, 500);
		this.getContentPane().add(graphicsPanel, BorderLayout.CENTER);
		
		bottomPanel = new JPanel();
		this.getContentPane().add(bottomPanel, BorderLayout.PAGE_END);

		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(controlPanel, BorderLayout.PAGE_START);

		JPanel textAreaPanel = new JPanel();
		bottomPanel.add(textAreaPanel, BorderLayout.PAGE_END);

		GroupLayout textAreaPanelLayout = new GroupLayout(textAreaPanel);
		
		JPanel textAreaPanelInput = new JPanel();
		textAreaPanelInput.setBorder(BorderFactory.createTitledBorder("Input"));
		textAreaInput = new JTextArea();
		textAreaInput.setEditable(true);
		textAreaInput.setLineWrap(true);
		textAreaInput.setColumns(40);
		textAreaInput.setRows(10);
		scrollAreaInput = new JScrollPane(textAreaInput, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		textAreaPanelInput.add(scrollAreaInput, BorderLayout.WEST);

		JPanel textAreaPanelOutput = new JPanel();
		textAreaPanelOutput.setBorder(BorderFactory.createTitledBorder("Output"));
		textAreaOutput = new JTextArea();
		textAreaOutput.setEditable(false);
		textAreaOutput.setLineWrap(true);
		textAreaOutput.setColumns(40);
		textAreaOutput.setRows(10);
		textAreaOutput.setBackground(new Color(240, 240, 240));
		scrollAreaOutput = new JScrollPane(textAreaOutput, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		textAreaPanelOutput.add(scrollAreaOutput, BorderLayout.CENTER);

		JPanel textAreaPanelLog = new JPanel();
		textAreaPanelLog.setBorder(BorderFactory.createTitledBorder("Event log"));
		textAreaLog = new JTextArea();
		textAreaLog.setEditable(false);
		textAreaLog.setLineWrap(true);
		textAreaLog.setColumns(40);
		textAreaLog.setRows(10);
		textAreaLog.setBackground(new Color(240, 240, 240));
		scrollAreaLog = new JScrollPane(textAreaLog, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		textAreaPanelLog.add(scrollAreaLog, BorderLayout.EAST);

		textAreaPanelLayout.setAutoCreateGaps(true);
		textAreaPanelLayout.setAutoCreateContainerGaps(true);
		textAreaPanelLayout.setHorizontalGroup(
				textAreaPanelLayout.createSequentialGroup()
					.addComponent(textAreaPanelInput)
					.addComponent(textAreaPanelOutput)
					.addComponent(textAreaPanelOutput)
				);
		textAreaPanelLayout.setVerticalGroup(
				textAreaPanelLayout.createParallelGroup()
					.addComponent(textAreaPanelInput)
					.addComponent(textAreaPanelOutput)
					.addComponent(textAreaPanelLog)
				);
			
				
		JPanel optionsPanel = new JPanel();
		optionsPanel.setBorder(BorderFactory.createTitledBorder("Options"));
		GroupLayout optionsLayout = new GroupLayout(optionsPanel);
		
		optionsPanel.setLayout(optionsLayout);
		controlPanel.add(optionsPanel);
		
		checkStartEnd = new JCheckBox("Enable start end events");
		checkStartEnd.addActionListener(e -> startEndPrintout = (checkStartEnd.isSelected()));
		checkStartEnd.setSelected(startEndPrintout);
		
		checkEvents = new JCheckBox("Enable array operation events");
		checkEvents.addActionListener(e -> eventPrintout = (checkEvents.isSelected()));
		checkEvents.setSelected(eventPrintout);
		
		checkArray = new JCheckBox("Enable array printout");
		checkArray.addActionListener(e -> arrayPrintout = (checkArray.isSelected()));
		checkArray.setSelected(arrayPrintout);
		
		checkDraw = new JCheckBox("Enable array drawing");
		checkDraw.addActionListener(e -> drawWhileSorting = (checkDraw.isSelected()));
		checkDraw.setSelected(drawWhileSorting);
		
		JLabel labelDelay = new JLabel("Delay (ms):");
		textDelay = new JFormattedTextField(NumberFormat.getNumberInstance());
		textDelay.setValue(delay);
		textDelay.setColumns(3);
		textDelay.addPropertyChangeListener(e -> setDelay());

		JLabel labelSort = new JLabel("Sorting algorithm:");
		String[] names = { "BubbleSort", "HeapSort", "InsertionSort", "MergeSort", "MergeSortInPlace", "QuickSort", "SelectionSort" };
		comboBoxSort = new JComboBox<>(names);
		comboBoxSort.addActionListener(e -> pick());
		
		optionsLayout.setAutoCreateGaps(true);
		optionsLayout.setAutoCreateContainerGaps(true);
		optionsLayout.setHorizontalGroup(
			optionsLayout.createSequentialGroup()
				.addGroup(optionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(checkStartEnd)
					.addComponent(checkEvents))
				.addGroup(optionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(checkArray)
					.addComponent(checkDraw))
				.addGroup(optionsLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
					.addComponent(labelSort)
					.addComponent(labelDelay))
				.addGroup(optionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(comboBoxSort)
					.addComponent(textDelay))
			);
		optionsLayout.setVerticalGroup(
			optionsLayout.createSequentialGroup()
				.addGroup(optionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(checkStartEnd)
					.addComponent(checkArray)
					.addComponent(labelSort)
					.addComponent(comboBoxSort))
		      .addGroup(optionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
	    		  	.addComponent(checkEvents)
		           	.addComponent(checkDraw)
		           	.addComponent(labelDelay)
		      		.addComponent(textDelay))
			);
		
		JPanel generatePanel = new JPanel();
		generatePanel.setBorder(BorderFactory.createTitledBorder("Array generation"));
		GroupLayout generationLayout = new GroupLayout(generatePanel);
		generatePanel.setLayout(generationLayout);
		controlPanel.add(generatePanel);
		
		buttonGenerate = new JButton("Generate");
		buttonGenerate.addActionListener(e -> generate());
		
		JLabel labelSize = new JLabel("Size:");
		textGenerateNumber = new JFormattedTextField(NumberFormat.getNumberInstance());
		textGenerateNumber.setValue(200);
		textGenerateNumber.setColumns(8);

		JLabel labelLow = new JLabel("Low:");
		textGenerateLow = new JFormattedTextField(NumberFormat.getNumberInstance());
		textGenerateLow.setValue(0);
		textGenerateLow.setColumns(8);

		JLabel labelHigh = new JLabel("High:");
		textGenerateHigh = new JFormattedTextField(NumberFormat.getNumberInstance());
		textGenerateHigh.setValue(1000);
		textGenerateHigh.setColumns(8);
		
		generationLayout.setAutoCreateGaps(true);
		generationLayout.setAutoCreateContainerGaps(true);
		generationLayout.setHorizontalGroup(
				generationLayout.createSequentialGroup()
				.addGroup(generationLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
					.addGroup(generationLayout.createSequentialGroup()
						.addComponent(labelSize)
						.addComponent(textGenerateNumber)
						.addComponent(labelLow)
						.addComponent(textGenerateLow)
						.addComponent(labelHigh)
						.addComponent(textGenerateHigh))
					.addComponent(buttonGenerate))
			);
		generationLayout.setVerticalGroup(
				generationLayout.createSequentialGroup()
				.addGroup(generationLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(labelSize)
					.addComponent(textGenerateNumber)
					.addComponent(labelLow)
					.addComponent(textGenerateLow)
					.addComponent(labelHigh)
					.addComponent(textGenerateHigh))
		      .addGroup(generationLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		      		.addComponent(buttonGenerate))
			);

		JPanel actionPanel = new JPanel();
		actionPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
		controlPanel.add(actionPanel);
		
		buttonSort = new JButton("Sort");
		buttonSort.addActionListener(e -> sort());
		actionPanel.add(buttonSort);
		
		buttonRead = new JButton("Load CSV");
		buttonRead.addActionListener(e -> readIntegerFromFile());
		actionPanel.add(buttonRead);
		
		buttonClear = new JButton("Clear log");
		buttonClear.addActionListener(e -> textAreaLog.setText(""));
		actionPanel.add(buttonClear);
		
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
		
		textAreaInput.setText(arrayToString(data));
		graphicsPanel.setData(data);
		graphicsPanel.setLastAccessed(-1);
		graphicsPanel.refresh();
	}
	
	private static String arrayToString(Number[] data) {
		StringBuilder s = new StringBuilder();
		s.append("[ ");
		for (int i = 0; i < data.length - 1; i++)
			s.append(data[i].toString() + ", ");
		s.append(data[data.length - 1] + " ]");
		return s.toString();
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
			textAreaInput.setText(arrayToString(data));
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
		textAreaOutput.setText(arrayToString(data));
		
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
		textAreaLog.append(s);
		scrollAreaLog.paintImmediately(scrollAreaLog.getBounds());
		textAreaLog.paintImmediately(textAreaLog.getBounds());
	}
}