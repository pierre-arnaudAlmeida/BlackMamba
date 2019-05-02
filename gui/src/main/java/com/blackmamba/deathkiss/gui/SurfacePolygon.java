package com.blackmamba.deathkiss.gui;

import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.blackmamba.deathkiss.entity.CommonArea;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.IOException;

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
	private Image image;
	
	Rectangle polygon1 = new Rectangle(0, 0, 10, 10);
	Rectangle polygon2 = new Rectangle(0, 0, 20, 20);
	Rectangle polygon3 = new Rectangle(0, 0, 30, 30);
	Rectangle polygon4 = new Rectangle(0, 0, 40, 40);
	

	private static final long serialVersionUID = -1793953027487918460L;

	public SurfacePolygon(List<Point> listPoint) throws IOException {
		for (Point point : listPoint) {
			this.addPoint(point.x, point.y);
			image = ImageIO.read(getClass().getClassLoader().getResource("resources/image.gif"));
		}

	}
	
	public void paint(Graphics g) {
	    int xpoints[] = {25, 145, 25, 145, 25};
	    int ypoints[] = {25, 25, 145, 145, 25};
	    int npoints = 5;
	    
	    g.setColor(Color.GREEN);
	    g.drawRect(polygon1.x, polygon1.y, polygon1.width, polygon1.height);
	    g.drawRect(polygon2.x, polygon2.y, polygon2.width, polygon2.height);
	    g.drawRect(polygon3.x, polygon3.y, polygon3.width, polygon3.height);
	    g.drawRect(polygon4.x, polygon4.y, polygon4.width, polygon4.height);
	  }
	
	  public void paintComponent(Graphics g) {
//          // dessine l'image
//          g.drawImage(buff, 0, 0, buff.getWidth(), buff.getHeight(), this);
//          // juste histoire de voir ou se trouvent les zones (plus facil pour le test ^^)
//          g.setColor(Color.GREEN);
//          g.drawRect(ZONE_IMAGE_1.x, ZONE_IMAGE_1.y, ZONE_IMAGE_1.width, ZONE_IMAGE_1.height);
//          g.drawRect(ZONE_IMAGE_2.x, ZONE_IMAGE_2.y, ZONE_IMAGE_2.width, ZONE_IMAGE_2.height);
      }
	  
	  
      private void testLocation(Point mouse, Rectangle area, String text) {
//          // test si la souris est dans les data de l'image
//          if(area.contains(mouse))
//              System.out.println(text + " - image");
//          else
//              System.out.println(text + " - !image");
      }

      public void mouseClicked(MouseEvent e) {
//          //récupération de la position de la souri
//          Point p = e.getPoint();
//          testLocation(p, ZONE_IMAGE_1, "mouseClicked - data 1");
//          testLocation(p, ZONE_IMAGE_2, "mouseClicked - data 2");
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
