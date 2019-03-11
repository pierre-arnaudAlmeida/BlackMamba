package com.pds.blackmamba.ihm.dev;

import java.awt.Color;

import javax.swing.JPanel;

public class TabEmployes extends JPanel {
	private Color color;
	private String message;

	public TabEmployes() {
	}

	public TabEmployes(Color color, String title) {
		this.color = color;
		this.message = title;
	}
}
