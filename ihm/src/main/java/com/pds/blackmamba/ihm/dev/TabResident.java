package com.pds.blackmamba.ihm.dev;

import java.awt.Color;

import javax.swing.JPanel;

public class TabResident extends JPanel{
	private String message;
	
	public TabResident() {
	}
	
	public TabResident(Color color,String title) {
		this.message=title;
		this.setBackground(color);
	}
}
