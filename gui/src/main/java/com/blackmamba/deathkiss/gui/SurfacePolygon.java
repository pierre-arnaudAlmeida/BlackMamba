package com.blackmamba.deathkiss.gui;

import java.util.List;

import javax.swing.JFrame;

import com.blackmamba.deathkiss.entity.CommonArea;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

/**
 * @author Raymond
 */
public class SurfacePolygon extends Polygon {

	private CommonArea idCommonArea;
	private float surface;
	// Point coordinates table
	private int tab;
	private int[] xPoints;
	private int[] yPoints;
	private Graphics e;

	private static final long serialVersionUID = -1793953027487918460L;

	public SurfacePolygon(List<Point> listPoint) {
		for (Point point : listPoint) {
			this.addPoint(point.x, point.y);
		}

	}
	
	public void paint(Graphics g) {
	    int xpoints[] = {25, 145, 25, 145, 25};
	    int ypoints[] = {25, 25, 145, 145, 25};
	    int npoints = 5;
	    
	    
	    g.drawPolygon(xpoints, ypoints, npoints);
	    e.drawPolygon(xPoints, yPoints, tab);
	  }
	
	public static void main(String[] args) {
	    JFrame frame = new JFrame();
	    frame.getContentPane().add(new TabMapSensor());

	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(1000,500);
	    frame.setVisible(true);
	}

	public SurfacePolygon(int tab, int[] xPoints, int[] yPoints) {
		super(yPoints, xPoints, tab);
	}

	public CommonArea getIdCommonArea() {
		return idCommonArea;
	}

	public void setIdCommonArea(CommonArea idCommonArea) {
		this.idCommonArea = idCommonArea;
	}

	public float getSurface() {
		return surface;
	}

	public void setSurface(float surface) {
		this.surface = surface;
	}

	public int getTab() {
		return tab;
	}

	public void setTab(int tab) {
		this.tab = tab;
	}

	public int[] getxPoints() {
		return xPoints;
	}

	public void setxPoints(int[] xPoints) {
		this.xPoints = xPoints;
	}

	public int[] getyPoints() {
		return yPoints;
	}

	public void setyPoints(int[] yPoints) {
		this.yPoints = yPoints;
	}

}
