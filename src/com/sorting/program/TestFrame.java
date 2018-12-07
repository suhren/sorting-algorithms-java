package com.sorting.program;

import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.sorting.algorithms.*;
import com.sorting.util.*;

public class TestFrame extends JFrame implements ISortingAlgorithmListener {
	private static final long serialVersionUID = 1L;
	JButton buttonRun;
	
	public TestFrame() {
		Integer[] data1 = new Integer[] { 3, 1, 4, 4, 2, 6 };
		Integer[] data2 = new Integer[] { 3, 1, 5, 4, 2, 5, 7, 1, 2, 9 };
		Integer[] data3 = generateRandomIntegers(20, 10, 100);
		//SortableArray<Integer> a = new SortableArray<>(data2, "Test", this);
		
		SortingAlgorithm<Integer> sortingAlgorithm = new HeapSort<>();
		sortingAlgorithm.sortArray(data3, this);
		
		buttonRun = new JButton("Press me!");
		// Old method with an anonymous class
//		buttonRun.addActionListener(new ActionListener() {
//			  public void actionPerformed(ActionEvent e) { a.swap(2, 3); }
//		});
		// New method with lambda listener
		// buttonRun.addActionListener(e -> a.swap(2, 3));
		
		this.getContentPane().setLayout(new GridLayout(3, 3));
		this.getContentPane().add(buttonRun);
		this.pack();
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
	
	public Integer[] generateRandomIntegers(int n, int min, int max) {
		Integer[] data = new Integer[n];
		Random rand = new Random();
		for (int i = 0; i < n; i++)
			data[i] = min + rand.nextInt(max - min + 1);
		return data;
	}

	@Override
	public <E> void sortGet(SortingAlgorithm<?> s, int i, E ei) {
		printStatus(s, "Get " + i + ":" + ei);
	}

	@Override
	public <E> void sortSet(SortingAlgorithm<?> s, int i, E prev, E next) {
		printStatus(s, "Set " + i + ":" + prev + " to " + i + ":" + next);
	}

	@Override
	public <E> void sortSwap(SortingAlgorithm<?> s, int i, int j, E ei, E ej) {
		printStatus(s, "Swapped " + i + ":" + ei + " <-> " + j + ":" + ej);
		printStatus(s, s.toString());
	}

	@Override
	public <E> void sortCompare(SortingAlgorithm<?> s, int i, int j, E ei, E ej, int c) {
		String t = (c > 0) ? " > " : (c < 0) ? " < " : " == ";
		printStatus(s, "Compared " + i + ":" + ei + t + j + ":" + ej);
	}

	@Override
	public void sortStart(SortingAlgorithm<?> s) {
		printStatus(s, "Started sort");
		printStatus(s, s.toString());
	}

	@Override
	public void sortEnd(SortingAlgorithm<?> s) {
		printStatus(s, "Array sorted with " + s.nGet() + " Gets, " + s.nSet() + " Sets, " + s.nComp() + " Comparisions, and " + s.nSwap() + " Swaps.");
		printStatus(s, s.toString());
	}
	
	private void printStatus(SortingAlgorithm<?> s, String t) {
		System.out.println(s.getElapsedTime() + ": " + s.getID() + ": " + t);
	}
}