package com.pds.blackmamba.ihm.dev;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;

public class TabConnexion extends JPanel {
	private Color color;
	private String message="";
	
	
	public TabConnexion(Color color, String title) {
		this.color = color;
		this.message = title;
		JButton b = new JButton("bute");
		this.add(b);
		JButton c = new JButton("bonjour");
		this.add(c);
	}
}
