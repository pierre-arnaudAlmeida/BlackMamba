package com.blackmamba.deathkiss.gui;

import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.blackmamba.deathkiss.commons.entity.CommonArea;

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
	private static CommonArea commonArea;
	
	private static final Rectangle entranceHall = new Rectangle(490, 99, 95, 197);
	private static final Rectangle livingRoomE0 = new Rectangle(343, 2, 145, 97);
	private static final Rectangle sittingRoom = new Rectangle(343, 297, 146, 96);
	private static final Rectangle corridorE0_A = new Rectangle(148, 102, 340, 48);
	private static final Rectangle corridorE0_B = new Rectangle(148, 241, 340, 54);
	private static final Rectangle corridorE0_C = new Rectangle(103, 2, 43, 391);
	private static final Rectangle elevatorA = new Rectangle(147, 152, 33, 31);
	private static final Rectangle elevatorB = new Rectangle(148, 207, 33, 31);
	private static final Rectangle diningRoomE0 = new Rectangle(2, 100, 99, 195);

	private static final Rectangle staffRoom = new Rectangle(245, 296, 146, 99);
	private static final Rectangle livingRoomE1 = new Rectangle(246, 4, 144, 96);
	private static final Rectangle corridorE1_A = new Rectangle(490, 4, 36, 390);
	private static final Rectangle corridorE1_B = new Rectangle(148, 102, 340, 48);
	private static final Rectangle corridorE1_C = new Rectangle(103, 3, 43, 394);
	private static final Rectangle corridorE1_D = new Rectangle(103, 3, 43, 394);
	private static final Rectangle diningRoomE1 = new Rectangle(2, 101, 100, 194);

	private static final long serialVersionUID = 1L;

	private static class Canvas extends JComponent implements MouseListener {

		private static final long serialVersionUID = 1L;
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

//			g.setColor(Color.GREEN);
			// Draw rectangle
			g.drawRect(entranceHall.x, entranceHall.y, entranceHall.width, entranceHall.height);
			g.drawRect(livingRoomE0.x, livingRoomE0.y, livingRoomE0.width, livingRoomE0.height);
			g.drawRect(sittingRoom.x, sittingRoom.y, sittingRoom.width, sittingRoom.height);
			g.drawRect(corridorE0_A.x, corridorE0_A.y, corridorE0_A.width, corridorE0_A.height);
			g.drawRect(corridorE0_B.x, corridorE0_B.y, corridorE0_B.width, corridorE0_B.height);
			g.drawRect(corridorE0_C.x, corridorE0_C.y, corridorE0_C.width, corridorE0_C.height);
			g.drawRect(elevatorA.x, elevatorA.y, elevatorA.width, elevatorA.height);
			g.drawRect(elevatorB.x, elevatorB.y, elevatorB.width, elevatorB.height);
			g.drawRect(diningRoomE0.x, diningRoomE0.y, diningRoomE0.width, diningRoomE0.height);

//			g.drawRect(staffRoom.x, staffRoom.y, staffRoom.width, staffRoom.height);
//			g.drawRect(livingRoomE1.x, livingRoomE1.y, livingRoomE1.width, livingRoomE1.height);
//			g.drawRect(corridorE1_A.x, corridorE1_A.y, corridorE1_A.width, corridorE1_A.height);
//			g.drawRect(corridorE1_B.x, corridorE1_B.y, corridorE1_B.width, corridorE1_B.height);
//			g.drawRect(corridorE1_C.x, corridorE1_C.y, corridorE1_C.width, corridorE1_C.height);
//			g.drawRect(corridorE1_D.x, corridorE1_D.y, corridorE1_D.width, corridorE1_D.height);
//			g.drawRect(elevatorA.x, elevatorA.y, elevatorA.width, elevatorA.height);
//			g.drawRect(elevatorB.x, elevatorB.y, elevatorB.width, elevatorB.height);
//			g.drawRect(diningRoomE1.x, diningRoomE1.y, diningRoomE1.width, diningRoomE1.height);
		}

		private void testLocation(Point mouse, Rectangle commoonArea, String text) {
			// if the mouse if here
			if (commoonArea.contains(mouse)) {
				System.out.println(text + " - image");
				System.out.println(commonArea.getNameCommonArea());
			} else {
				System.out.println(text + " - !image");
			}
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
			testLocation(p, entranceHall, "mouseClicked - data 1");
			testLocation(p, livingRoomE0, "mouseClicked - data 2");
			testLocation(p, sittingRoom, "mouseClicked - data 3");
			testLocation(p, corridorE0_A, "mouseClicked - data 4_1");
			testLocation(p, corridorE0_B, "mouseClicked - data 4_2");

			if (location(p, entranceHall) == true) {
				// new SurfacePolygon(polygon1).setVisible(true);
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
			img = ImageIO.read(getClass().getClassLoader().getResource("floor0.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setContentPane(new Canvas(img));

	}

	public static void main(String[] args) {
		new SurfacePolygon().setVisible(true);
	}
	
//	private JButton overButton;
//
//	public SurfacePolygon(String title) {
//		super(title);
//		initialize();
//	}
//
//	private void initialize() {
//		JPanel buttonPanel = new JPanel();
//		buttonPanel.setSize(320, 200);
//		overButton = new JButton("Survolez-moi");
//		overButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
//		overButton.addMouseListener(new MouseListener() {
//			public void mouseClicked(MouseEvent e) {
//			}
//
//			public void mouseEntered(MouseEvent e) {
//				JButton source = (JButton) e.getSource();
//				source.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
//			}
//
//			public void mouseExited(MouseEvent e) {
//				JButton source = (JButton) e.getSource();
//				source.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
//			}
//
//			public void mousePressed(MouseEvent e) {
//			}
//
//			public void mouseReleased(MouseEvent e) {
//			}
//		});
//		buttonPanel.add(overButton);
//		getContentPane().add(buttonPanel);
//	}
//
//	public static void main(String[] args) {
//		SurfacePolygon demo = new SurfacePolygon("Démo de survol de bouton");
//		demo.pack();
//		demo.setVisible(true);


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
