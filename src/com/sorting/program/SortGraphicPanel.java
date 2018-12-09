package com.sorting.program;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class SortGraphicPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Integer[] data = null;
	private Color[] colorLookup = null;
	private int iLastAccessed = 0;
	private int dataMin = 0;
	private int dataMax = 0;
	private boolean outline = true;
	private boolean rainbow = true;
	
	public SortGraphicPanel(int width, int height) {
		super();
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.BLACK);
	}
	
	public void setLastAccessed(int iLastAccessed) {
		this.iLastAccessed = iLastAccessed;
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
	
	public void refresh() {
		this.paintImmediately(this.getBounds());
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (data != null && data.length > 0) {
			double w = this.getWidth() * 1.0 / data.length;
			
			int lastX = -1;
			
	        for (int i = 0; i < data.length; i++) {
	        	if ((int)(i * w) > lastX) {
			        lastX = (int)(i * w);
			        if (i == iLastAccessed)
			        	g.setColor(Color.GREEN);
			        else if (rainbow)
			        	g.setColor(colorLookup[data[i]]);
			        else
			        	g.setColor(Color.RED);
			        
			        int h = this.getHeight() * data[i].intValue() / dataMax;
			        g.fillRect(lastX, this.getHeight() - h, (int)Math.ceil(w), h);
			        
			        if (outline && w >= 2) {
				        g.setColor(Color.BLACK);
				        g.drawRect(lastX, this.getHeight() - h, (int)w, h);
			        }
	        	}
	        }
        }
    }

	public void setOutline(boolean enabled) {
		this.outline = enabled;
		this.repaint();
	}

	public void setRainbow(boolean enabled) {
		this.rainbow = enabled;
		this.repaint();
	}
}
