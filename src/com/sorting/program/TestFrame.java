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
	
	private SortGraphicPanel graphicsPanel;
	private JFormattedTextField textGenerateNumber, textGenerateLow, textGenerateHigh, textDelay;
	private JTextArea textAreaLog, textAreaInput, textAreaOutput;
	private JScrollPane scrollAreaLog, scrollAreaInput, scrollAreaOutput;
	
	private boolean drawBarOutline = true;
	private boolean drawBarRainbow = true;
	private boolean startEndPrintout = true;
	private boolean eventPrintout = false;
	private boolean arrayPrintout = false;
	private boolean drawWhileSorting = true;
	
	private int delay = 0;
    private Integer[] data;
	private SortingAlgorithm<Integer> sortingAlgorithm;	
	
	public TestFrame() {
        setupFrame();
	}
	
	private void setupFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		graphicsPanel = new SortGraphicPanel(1000, 500);
		this.getContentPane().add(graphicsPanel, BorderLayout.CENTER);
		
		JPanel bottomPanel = new JPanel();
		this.getContentPane().add(bottomPanel, BorderLayout.PAGE_END);

		JPanel controlPanel = new JPanel();
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
		textAreaInput.setColumns(44);
		textAreaInput.setRows(10);
		scrollAreaInput = new JScrollPane(textAreaInput, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		textAreaPanelInput.add(scrollAreaInput, BorderLayout.WEST);

		JPanel textAreaPanelOutput = new JPanel();
		textAreaPanelOutput.setBorder(BorderFactory.createTitledBorder("Output"));
		textAreaOutput = new JTextArea();
		textAreaOutput.setEditable(false);
		textAreaOutput.setLineWrap(true);
		textAreaOutput.setColumns(44);
		textAreaOutput.setRows(10);
		textAreaOutput.setBackground(new Color(240, 240, 240));
		scrollAreaOutput = new JScrollPane(textAreaOutput, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		textAreaPanelOutput.add(scrollAreaOutput, BorderLayout.CENTER);

		JPanel textAreaPanelLog = new JPanel();
		textAreaPanelLog.setBorder(BorderFactory.createTitledBorder("Event log"));
		textAreaLog = new JTextArea();
		textAreaLog.setEditable(false);
		textAreaLog.setLineWrap(true);
		textAreaLog.setColumns(44);
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
		
		JCheckBox checkBarOutline = new JCheckBox("Draw bar outline");
		checkBarOutline.addActionListener(e -> graphicsPanel.setOutline(checkBarOutline.isSelected()));
		checkBarOutline.setSelected(drawBarOutline);
		JCheckBox checkBarRainbow = new JCheckBox("Rainbow bar color");
		checkBarRainbow.addActionListener(e -> graphicsPanel.setRainbow(checkBarRainbow.isSelected()));
		checkBarRainbow.setSelected(drawBarRainbow);
		JCheckBox checkStartEnd = new JCheckBox("Enable start end events");
		checkStartEnd.addActionListener(e -> startEndPrintout = (checkStartEnd.isSelected()));
		checkStartEnd.setSelected(startEndPrintout);
		JCheckBox checkEvents = new JCheckBox("Enable array operation events");
		checkEvents.addActionListener(e -> eventPrintout = (checkEvents.isSelected()));
		checkEvents.setSelected(eventPrintout);
		JCheckBox checkArray = new JCheckBox("Enable array printout");
		checkArray.addActionListener(e -> arrayPrintout = (checkArray.isSelected()));
		checkArray.setSelected(arrayPrintout);
		JCheckBox checkDraw = new JCheckBox("Enable array drawing");
		checkDraw.addActionListener(e -> drawWhileSorting = (checkDraw.isSelected()));
		checkDraw.setSelected(drawWhileSorting);
		
		JLabel labelDelay = new JLabel("Delay (ms):");
		textDelay = new JFormattedTextField(NumberFormat.getNumberInstance());
		textDelay.setValue(delay);
		textDelay.setColumns(3);
		textDelay.addPropertyChangeListener(e -> delay = ((Number)textDelay.getValue()).intValue());

		JLabel labelSort = new JLabel("Sorting algorithm:");
		JComboBox<String> comboBoxSort = new JComboBox<>(SortingAlgorithmLibrary.getAlgorithmNames());
		comboBoxSort.addActionListener(e -> sortingAlgorithm = SortingAlgorithmLibrary.getAlgorithm((String)comboBoxSort.getSelectedItem()));
		
		optionsLayout.setAutoCreateGaps(true);
		optionsLayout.setAutoCreateContainerGaps(true);
		optionsLayout.setHorizontalGroup(
			optionsLayout.createSequentialGroup()
				.addGroup(optionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(checkBarOutline)
					.addComponent(checkBarRainbow))
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
					.addComponent(checkBarOutline)
					.addComponent(checkStartEnd)
					.addComponent(checkArray)
					.addComponent(labelSort)
					.addComponent(comboBoxSort))
		      .addGroup(optionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(checkBarRainbow)
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
		
		JButton buttonGenerate = new JButton("Generate");
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
		JButton buttonSort = new JButton("Sort");
		buttonSort.addActionListener(e -> sort());
		actionPanel.add(buttonSort);
		JButton buttonReload = new JButton("Reload input");
		buttonReload.addActionListener(e -> reload());
		actionPanel.add(buttonReload);
		JButton buttonRead = new JButton("Load CSV");
		buttonRead.addActionListener(e -> readIntegerFromFile());
		actionPanel.add(buttonRead);
		JButton buttonClear = new JButton("Clear log");
		buttonClear.addActionListener(e -> textAreaLog.setText(""));
		actionPanel.add(buttonClear);
		
		sortingAlgorithm = SortingAlgorithmLibrary.getAlgorithm((String)comboBoxSort.getSelectedItem());
		this.pack();
	}
	
	private void reload() {
		List<Integer> res = new ArrayList<>();
		Scanner sc = new Scanner(textAreaInput.getText());
		while (sc.hasNextInt())
			res.add(sc.nextInt());
		sc.close();
		Integer[] temp = new Integer[res.size()];
		for (int i = 0; i < res.size(); i++)
			temp[i] = res.get(i);
		loadData(temp);
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
		
		loadData(data);
		textAreaInput.setText(arrayToString(data));
		
	}
	private void loadData(Integer[] data) {
		this.data = data;
		graphicsPanel.setData(data);
		graphicsPanel.setLastAccessed(-1);
		graphicsPanel.refreshAll();
		printLine("Loaded data with " + data.length + " elements.");
	}
	private <E> String arrayToString(E[] data) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < data.length - 1; i++)
			s.append(data[i].toString() + " ");
		s.append(data[data.length - 1]);
		return s.toString();
	}
	private void sort() {
		if (data != null && data.length > 0) {
			//sortingAlgorithm.sortArray(data, this);
			Thread sortThread = new Thread(new SortOperation(sortingAlgorithm, data, this)); 
			sortThread.run();
		} else
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
			graphicsPanel.refreshAll();
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
		
		if (drawWhileSorting) {
			graphicsPanel.setLastAccessed(i);
			graphicsPanel.refresh(i);
			if (delay > 0) {
				try {
					Thread.sleep(delay);
		        } catch(Exception e) {
		        	
		        }
			}
		}
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
			graphicsPanel.refresh(i, j);
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
		
		if (drawWhileSorting) {
			graphicsPanel.setLastAccessed(i);
			graphicsPanel.refresh(i, j);
			if (delay > 0) {
				try {
					Thread.sleep(delay);
		        } catch(Exception e) {
		        	
		        }
			}
		}
	}
	
	@Override
	public <E> void sortCompare(SortingAlgorithm<?> s, E ei, E ej, int c) {
		if (eventPrintout) {
			String t = (c > 0) ? " > " : (c < 0) ? " < " : " == ";
			printStatus(s, "Compared " + ei + t + ej);
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
			printStatus(s, "Sorted with " + s.nGet() + " Gets, " + s.nSet() + " Sets, " + s.nComp() + " Comparisions, and " + s.nSwap() + " Swaps.");
			if (arrayPrintout)
				printStatus(s, s.toString());
		}
		
		Integer[] res = new Integer[s.data().length];
		for (int i = 0; i < res.length; i++)
			res[i] = (Integer)s.data()[i];
		
		this.data = res;
		
		graphicsPanel.setData(res);
		graphicsPanel.refreshAll();
	}
	
	private void printStatus(SortingAlgorithm<?> s, String t) {
		printLine(s.getElapsedTime() + "ms: " + s.getID() + ": " + t);
		graphicsPanel.refreshAll();
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