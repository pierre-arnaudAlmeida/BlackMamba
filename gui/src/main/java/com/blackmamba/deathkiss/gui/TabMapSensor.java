package com.blackmamba.deathkiss.gui;

import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.blackmamba.deathkiss.entity.Message;
import com.blackmamba.deathkiss.entity.Sensor;

/**
 * @author Raymond
 *
 */

public class TabMapSensor extends JPanel implements MouseListener {



	/**
	 * 
	 */
	private static final long serialVersionUID = 7348020021300445245L;
	private Polygon surface;
	private List<Sensor> listSensor = new ArrayList<Sensor>();
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
//	  public void paint(Graphics g) {
//		    int xpoints[] = {25, 145, 25, 145, 25};
//		    int ypoints[] = {25, 25, 145, 145, 25};
//		    int npoints = 5;
//		    
//		    g.drawPolygon(xpoints, ypoints, npoints);
//		  }
//
//		  public static void main(String[] args) {
//		    JFrame frame = new JFrame();
//		    frame.getContentPane().add(new TabMapSensor());
//
//		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		    frame.setSize(1000,500);
//		    frame.setVisible(true);
//		  }
	
	
}
