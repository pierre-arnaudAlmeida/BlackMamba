package com.pds.blackmamba.ihm.dev;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Onglet extends JPanel {
	private Color color = Color.white;
	private static int COUNT = 0;
	private String message = "";

	public Onglet() {
	}
	
	//définition du corps de chaque onglet, créer une classe onglet pour chaque onglet différent
	public Onglet(Color color, String title) {
		this.color = color;
		this.message = title;
		this.COUNT = ++COUNT;
		JButton b = new JButton("bonjour");
		this.add(b);
		JButton c = new JButton("bonjour");
		this.add(c);
	}

	public void paintComponent(Graphics g) {
		g.setColor(this.color);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.drawString(this.message, 10, 20);
	}
}
