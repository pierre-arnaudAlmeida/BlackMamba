package com.pds.blackmamba.ihm.dev;

import java.awt.Color;

import javax.swing.JPanel;

public class TabResident extends JPanel{

	private Color color;
	private String message;
	
	public TabResident() {
	}
	
	public TabResident(Color color,String title) {
		this.color= color;
		this.message=title;
	}
}
