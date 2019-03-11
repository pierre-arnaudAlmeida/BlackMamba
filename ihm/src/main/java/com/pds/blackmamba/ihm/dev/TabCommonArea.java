package com.pds.blackmamba.ihm.dev;

import java.awt.Color;
import javax.swing.JPanel;

public class TabCommonArea extends JPanel {
	private String message;

	public TabCommonArea() {
	}

	public TabCommonArea(Color color, String title) {
		this.message = title;
		this.setBackground(color);
	}
}
