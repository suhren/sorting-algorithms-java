package com.sorting.program;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;

public class SortGraphicPanel extends JPanel implements ComponentListener {
	private static final long serialVersionUID = 1L;
	private Integer[] data = null;
	private Color[] colorLookup = null;
	private int iLastAccessed = 0;
	private int dataMin = 0;
	private int dataMax = 0;
	private boolean outline = true;
	private boolean rainbow = true;
	private int currentIndex = 0;
	private int currentX, currentY, currentWidth, currentHeight;
	
	public SortGraphicPanel(int width, int height) {
		super();
		this.addComponentListener(this);
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.BLACK);
	}
	
	public void setLastAccessed(int iLastAccessed) {
		int old = this.iLastAccessed;
		this.iLastAccessed = iLastAccessed;
		if (old != -1)
			drawBar(old);
		if (iLastAccessed != -1)
			drawBar(iLastAccessed);
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
	}
	
	public void refreshAll() {
		if (data != null)
			for (int i = 0; i < data.length; i++)
				drawBar(i);
	}
	public void refresh(int i) {
		drawBar(i);
	}
	public void refresh(int i, int j) {
		drawBar(i);
		drawBar(j);
	}
	public void drawBar(int i) {
		currentIndex = i;
		double w = this.getWidth() * 1.0 / data.length;
		currentX = (int)(i * w);
		currentHeight = this.getHeight() * data[i].intValue() / dataMax;
		currentY = this.getHeight() - currentHeight;
		currentWidth = (int)Math.ceil(w);
		
		this.paintImmediately(new Rectangle(currentX, 0, currentWidth, this.getHeight()));
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setColor(Color.BLACK);
        g.fillRect(currentX, 0, currentWidth, this.getHeight());
        
        if (data != null && data.length > 0) {
	        if (currentIndex == iLastAccessed)
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
        
//        if (data != null && data.length > 0) {
//			double w = this.getWidth() * 1.0 / data.length;
//			
//			int lastX = -1;
//			
//	        for (int i = 0; i < data.length; i++) {
//	        	if ((int)(i * w) > lastX) {
//			        lastX = (int)(i * w);
//			        if (i == iLastAccessed)
//			        	g.setColor(Color.GREEN);
//			        else if (rainbow)
//			        	g.setColor(colorLookup[data[i]]);
//			        else
//			        	g.setColor(Color.RED);
//			        
//			        int h = this.getHeight() * data[i].intValue() / dataMax;
//			        g.fillRect(lastX, this.getHeight() - h, (int)Math.ceil(w), h);
//			        
//			        if (outline && w >= 2) {
//				        g.setColor(Color.BLACK);
//				        g.drawRect(lastX, this.getHeight() - h, (int)w, h);
//			        }
//	        	}
//	        }
//        }
    }

	public void setOutline(boolean enabled) {
		this.outline = enabled;
		this.refreshAll();
	}

	public void setRainbow(boolean enabled) {
		this.rainbow = enabled;
		this.refreshAll();
	}

	@Override
	public void componentResized(ComponentEvent e) {
		this.refreshAll();
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
