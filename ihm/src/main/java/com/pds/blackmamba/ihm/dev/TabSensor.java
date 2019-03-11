package com.pds.blackmamba.ihm.dev;

import java.awt.Color;
import javax.swing.JPanel;

public class TabSensor extends JPanel{
	private String message;
	
	public TabSensor() {
	}
	
	public TabSensor(Color color, String title) {
		this.message=title;
		this.setBackground(color);
	}
}
