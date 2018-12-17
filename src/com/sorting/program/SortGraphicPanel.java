package com.sorting.program;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JPanel;

public class SortGraphicPanel extends JPanel implements ComponentListener {
	private static final long serialVersionUID = 1L;
	private Integer[] data = null;
	private Color[] colorLookup = null;
	private int dataMin = 0;
	private int dataMax = 0;
	private boolean outline = true;
	private boolean rainbow = true;
	private List<Integer> indexBuffer = Collections.synchronizedList(new ArrayList<>());
	private int lastAccessedIndex = -1;
	
	public SortGraphicPanel(int width, int height) {
		super();
		this.addComponentListener(this);
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.BLACK);
	}
	
	public void setData(Integer[] data) {
		int n = data.length;
		dataMin = 0;
		dataMax = 0;
		this.data = data;
		
		for (int i = 0; i < n; i++) {
			if (data[i].intValue() > dataMax) 
				dataMax = data[i].intValue();
			else if (data[i].intValue() < dataMin) 
				dataMin = data[i].intValue();
		}
		
		colorLookup = new Color[dataMax - dataMin + 1];
		
		for (int i = 0; i < n; i++)
			colorLookup[data[i]] = Color.getHSBColor(data[i] * 1.0f / (dataMax - dataMin), 1.0f, 1.0f);
		
		refreshNow();
	}
	
	public void bufferAll() {
		if (data != null)
			for (int i = 0; i < data.length; i++)
				indexBuffer.add(i);
		lastAccessedIndex = -1;
		this.repaint();
	}
	public void buffer(int i) {
		indexBuffer.add(i);
		indexBuffer.add(lastAccessedIndex);
		lastAccessedIndex = i;
		this.repaint();
	}
	public void buffer(int i, int j) {
		indexBuffer.add(i);
		indexBuffer.add(j);
		indexBuffer.add(lastAccessedIndex);
		lastAccessedIndex = i;
		this.repaint();
	}
	public void buffer(List<Integer> list) {
		indexBuffer.addAll(list);
		indexBuffer.add(lastAccessedIndex);
		lastAccessedIndex = list.get(list.size() - 1);
		this.repaint();
	}
	public void refreshNow() {
		indexBuffer.clear();
		if (data != null)
			for (int i = 0; i < data.length; i++)
				indexBuffer.add(i);
		lastAccessedIndex = -1;
		this.paintImmediately(new Rectangle(0, 0, this.getWidth(), this.getHeight()));
	}
	
	@Override
    public void paintComponent(Graphics g) {

        if (indexBuffer != null && indexBuffer.size() > 0) {
	        List<Integer> indices = indexBuffer.stream().collect(Collectors.toList());
	        indexBuffer.clear();
			double w = this.getWidth() * 1.0 / data.length;
			
			for (int i = 0; i < indices.size(); i++) {
				int currentIndex = indices.get(i);
				int currentWidth = (int)Math.ceil(w);
				int currentHeight = this.getHeight() * data[currentIndex].intValue() / dataMax;
				int currentX = (int)(currentIndex * w);
				int currentY = this.getHeight() - currentHeight;
				
				g.setColor(Color.BLACK);
				g.fillRect(currentX, 0, currentWidth, this.getHeight());
		        
		        if (i == 0)
		        	g.setColor(Color.GREEN);
		        else if (rainbow)
		        	g.setColor(colorLookup[data[currentIndex]]);
		        else
		        	g.setColor(Color.RED);
		        
		        g.fillRect(currentX, currentY, currentWidth, currentHeight);
		        
		        if (outline && currentWidth >= 2) {
			        g.setColor(Color.BLACK);
			        g.drawRect(currentX, currentY, currentWidth, currentHeight);
		        }
		        
	        }
		}
        super.paintComponent(g);
    }

	public void setOutline(boolean enabled) {
		this.outline = enabled;
		this.refreshNow();
	}

	public void setRainbow(boolean enabled) {
		this.rainbow = enabled;
		this.refreshNow();
	}

	@Override
	public void componentResized(ComponentEvent e) {
		this.refreshNow();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
}
