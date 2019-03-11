package com.pds.blackmamba.ihm.dev;

import java.awt.Color;
import javax.swing.JPanel;

public class TabCommonArea extends JPanel {
	private Color color;
	private String message;

	public TabCommonArea() {
	}

	public TabCommonArea(Color color, String title) {
		this.color = color;
		this.message = title;
	}
}
