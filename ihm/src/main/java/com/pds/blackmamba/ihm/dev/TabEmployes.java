package com.pds.blackmamba.ihm.dev;

import java.awt.Color;
import javax.swing.JPanel;

public class TabEmployes extends JPanel {
	private String message;

	public TabEmployes() {
	}

	public TabEmployes(Color color, String title) {
		this.message = title;

	this.setBackground(color);
	}
}
