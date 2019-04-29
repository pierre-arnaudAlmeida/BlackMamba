package com.blackmamba.deathkiss.gui;

import java.util.List;
import com.blackmamba.deathkiss.entity.CommonArea;
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

	private static final long serialVersionUID = -1793953027487918460L;

	public SurfacePolygon(List<Point> listPoint) {
		for (Point point : listPoint) {
			this.addPoint(point.x, point.y);
		}

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
