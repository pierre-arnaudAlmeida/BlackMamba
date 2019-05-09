package com.blackmamba.deathkiss.gui;

import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.blackmamba.deathkiss.entity.CommonArea;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Raymond
 */
public class SurfacePolygon extends JFrame {

	private CommonArea idCommonArea; 
	private float surface;
	// Point coordinates table
	private int tab;
	private int[] xPoints;
	private int[] yPoints;
	private static JPopupMenu popup;

	private static final Rectangle polygon1 = new Rectangle(7, 56, 108, 313);
	private static final Rectangle polygon2 = new Rectangle(129, 72, 105, 97);
	private static final Rectangle polygon3 = new Rectangle(240, 171, 346, 45);
	private static final Rectangle polygon4_1 = new Rectangle(591, 171, 282, 182);
	private static final Rectangle polygon4_2 = new Rectangle(733, 71, 140, 99);

	private static final long serialVersionUID = -1793953027487918460L;

	private static class Canvas extends JComponent implements MouseListener {

		private static final long serialVersionUID = -2873372597458047717L;
		private BufferedImage buffer = null;
		Point p = null;

		public Canvas(BufferedImage image) {
			this.addMouseListener(this);
			this.buffer = image;
		}

		public void paint(Graphics g) {
			
			// 50% transparent
			int alpha = 127; 
			
			// Draw image
			g.drawImage(buffer, 0, 0, buffer.getWidth(), buffer.getHeight(), this);
			
			Color myColour = new Color(255, 255, 255, alpha);

			g.setColor(myColour);
			// Draw rectangle
			g.drawRect(polygon1.x, polygon1.y, polygon1.width, polygon1.height);
			g.drawRect(polygon2.x, polygon2.y, polygon2.width, polygon2.height);
			g.drawRect(polygon3.x, polygon3.y, polygon3.width, polygon3.height);
			g.drawRect(polygon4_1.x, polygon4_1.y, polygon4_1.width, polygon4_1.height);
			g.drawRect(polygon4_2.x, polygon4_2.y, polygon4_2.width, polygon4_2.height);
		}

		private void testLocation(Point mouse, Rectangle commonArea, String text) {
			// if the mouse if here
			if (commonArea.contains(mouse))
				System.out.println(text + " - image");
			else
				System.out.println(text + " - !image");
		}

		private boolean location(Point mouse, Rectangle commonArea) {
			if (commonArea.contains(mouse))
				return true;
			else
				return false;
		}

		public void mouseClicked(MouseEvent e) {
			// récupération de la position de la souri
			p = e.getPoint();
			testLocation(p, polygon1, "mouseClicked - data 1");
			testLocation(p, polygon2, "mouseClicked - data 2");
			testLocation(p, polygon3, "mouseClicked - data 3");
			testLocation(p, polygon4_1, "mouseClicked - data 4_1");
			testLocation(p, polygon4_2, "mouseClicked - data 4_2");

			if (location(p, polygon1) == true) {
				//new SurfacePolygon(polygon1).setVisible(true);
				System.out.println("Polygon1");
			}
		}

		public void mousePressed(MouseEvent e) {
//			// récupération de la position de la souri
//			Point p = e.getPoint();
//			testLocation(p, polygon1, "mousePressed - data 1");
//			testLocation(p, polygon2, "mousePressed - data 2");
		}

		public void mouseReleased(MouseEvent e) {
//			// récupération de la position de la souri
//			Point p = e.getPoint();
//			testLocation(p, polygon1, "mouseReleased - data 1");
//			testLocation(p, polygon2, "mouseReleased - data 2");
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

	}

	public SurfacePolygon() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1200, 700);
		setLocationRelativeTo(null);
		BufferedImage img = null;
		try {
			img = ImageIO.read(getClass().getClassLoader().getResource("image.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setContentPane(new Canvas(img));

	}
	


//	private void formMouseMoved(MouseEvent evt) {
//		   SurfacePolygon.getToolTipLocation(evt);
//			SurfacePolygon.setToolTipText("Infos"); // Active l'infobulle
//			SurfacePolygon.setToolTipText(null); // Désactive l'infobulle
//	}
	
	
//	JPanel southPanel = new JPanel(new BorderLayout());
//	add(southPanel, BorderLayout.SOUTH);
//	JLabel logo = new JLabel(new ImageIcon(TabMapSensor.class.getResource("image.png")));
//	southPanel.add(logo, BorderLayout.EAST);

//	canvas = new JPanel();
//	canvas.setSize(1200, 700);
//	canvas.setLocation(null);
//	try {
//		img = ImageIO.read(getClass().getClassLoader().getResource("image.jpg"));
//	} catch (IOException e) {
//		e.printStackTrace();
//	}
	

	public static void main(String[] args) {
		new SurfacePolygon().setVisible(true);
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
