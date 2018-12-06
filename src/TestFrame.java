import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;

public class TestFrame extends JFrame implements ISortableArrayListener {
	private static final long serialVersionUID = 1L;
	JButton buttonRun;
	
	public TestFrame() {
		Integer[] data1 = new Integer[] { 3, 1, 4, 4, 2, 6 };
		Integer[] data2 = new Integer[] { 3, 1, 5, 4, 2, 5, 7, 1, 2, 9 };
		Integer[] data3 = generateRandomIntegers(20, 10, 100);
		SortableArray<Integer> a = new SortableArray<>(data2, "Test", this);
		
		SortingAlgorithm sortingAlgorithm = new QuickSort();
		sortingAlgorithm.sortArray(a);
		
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
	public void arrayStartSort(SortableArray<?> a) {
		printStatus(a, "Started sort");
		printStatus(a, a.toString());
	}
	
	@Override
	public <E> void arrayGet(SortableArray<?> a, int i, E e) {
		printStatus(a, "Get " + i + ":" + e);
	}

	@Override
	public <E> void arraySet(SortableArray<?> a, int i, E prev, E next) {
		printStatus(a, "Set " + i + ":" + prev + " to " + i + ":" + next);
	}

	@Override
	public <E> void arraySwap(SortableArray<?> a, int i, int j, E ei, E ej) {
		printStatus(a, "Swapped " + i + ":" + ei + " <-> " + j + ":" + ej);
		printStatus(a, a.toString());
	}

	@Override
	public <E> void arrayCompare(SortableArray<?> a, int i, int j, E ei, E ej, int c) {
		String s = (c > 0) ? " > " : (c < 0) ? " < " : " == ";
		printStatus(a, "Compared " + i + ":" + ei + s + j + ":" + ej);
	}

	@Override
	public void arrayEndSort(SortableArray<?> a) {
		printStatus(a, ": Array sorted with " + a.nGet() + " Gets, " + a.nSet() + " Sets, " + a.nComp() + " Comparisions, and " + a.nSwap() + " Swaps.");
	}
	
	private void printStatus(SortableArray<?> a, String s) {
		System.out.println(a.getElapsedTime() + ": " + a.getID() + ": " + s);
	}
}