package com.sorting.program;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class SortGraphicPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	Number[] data = null;
	int iLastAccessed = 0;
	int dataMax = 0;
	
	public SortGraphicPanel(int width, int height) {
		super();
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.BLACK);
	}
	
	public void setLastAccessed(int iLastAccessed) {
		this.iLastAccessed = iLastAccessed;
	}
	
	public void setData(Number[] data) {
		dataMax = 0;
		this.data = data;
		
		for (int i = 0; i < data.length; i++)
			if (data[i].intValue() > dataMax)
				dataMax = data[i].intValue();
	}
	
	public void refresh() {
		this.paintImmediately(this.getBounds());
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (data != null && data.length > 0) {
			float w = this.getWidth() * 1.0f / data.length;
			if (w < 1)
				w = 1;
			
			int lastX = -1;
			
	        for (int i = 0; i < data.length; i++) {
	        	if ((int)(i * w) > lastX) {
			        lastX = (int)(i * w);
			        if (i == iLastAccessed)
			        	g.setColor(Color.GREEN);
			        else
			        	g.setColor(Color.RED);
			        
			        int h = this.getHeight() * data[i].intValue() / dataMax;
			        g.fillRect(lastX, this.getHeight() - h, (int)w, h);
			        if (w >= 2) {
				        g.setColor(Color.BLACK);
				        g.drawRect(lastX, this.getHeight() - h, (int)w, h);
			        }
	        	}
	        }
        }
    }
}
