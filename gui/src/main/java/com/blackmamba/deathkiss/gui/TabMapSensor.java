package com.blackmamba.deathkiss.gui;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;

/**
 * @author Raymond
 *
 */

public class TabMapSensor extends JPanel {

	public void paint(Graphics g) {
		g.setColor(Color.black);

		g.drawRect(100, 100, 100, 100);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new TabMapSensor());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 700);
		frame.setVisible(true);
	}
}
