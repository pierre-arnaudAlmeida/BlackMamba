package com.pds.blackmamba.ihm.dev;

import java.awt.Color;
import javax.swing.JPanel;

public class TabHistorical extends JPanel {
	private String message;

	public TabHistorical() {
	}

	public TabHistorical(Color color, String title) {
		this.message = title;
		this.setBackground(color);
	}
}
