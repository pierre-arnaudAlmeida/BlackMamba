package com.pds.blackmamba.ihm.dev;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TabHistorical extends JPanel {
	private String message;
	private JPanel bar;
	private JLabel labelIdEmployee, idEmployee;
	private Font police;
	private JButton disconnection;

	public TabHistorical() {
	}

	public TabHistorical(Color color, String title) {
		bar = new JPanel();
		bar.setBackground(Color.DARK_GRAY);
		bar.setPreferredSize(new Dimension((int) getToolkit().getScreenSize().getWidth(), 70));
		bar.setLayout(new BorderLayout());
		
		labelIdEmployee = new JLabel("Identifiant :   ");
		police = new Font("Arial", Font.BOLD, 16);
		labelIdEmployee.setForeground(Color.WHITE);
		labelIdEmployee.setFont(police);
		bar.add(labelIdEmployee, BorderLayout.WEST);
		bar.setBorder(BorderFactory.createMatteBorder(20, 100, 20, 100, bar.getBackground()));
		
		idEmployee = new JLabel();
		idEmployee.setText("1");
		idEmployee.setFont(police);
		idEmployee.setForeground(Color.WHITE);
		bar.add(idEmployee, BorderLayout.CENTER);
		
		disconnection = new JButton("Se Déconnecter");
		bar.add(disconnection,BorderLayout.EAST);
		
		this.setLayout(new BorderLayout());
		this.add(bar, BorderLayout.NORTH);
		this.message = title;
		this.setBackground(color);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
