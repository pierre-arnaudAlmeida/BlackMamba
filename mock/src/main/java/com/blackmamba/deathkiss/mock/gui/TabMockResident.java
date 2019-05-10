package com.blackmamba.deathkiss.mock.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.blackmamba.deathkiss.mock.GenerateResident;

public class TabMockResident extends JPanel {
	/**
	 * Different parameters used
	 */
	private static final long serialVersionUID = 1L;
	private Font policeLabel;
	private JLabel labelNbGenerate;
	private JTextField textInputNbGenerate;
	private JButton generateButton;
	private JButton restaureButton;
	private GenerateResident generateResident;

	/**
	 * Constructor
	 */
	public TabMockResident() {
	}

	/**
	 * Constructor
	 * 
	 * @param color
	 * @param title
	 */
	public TabMockResident(Color color, String title) {

		///////////////////////// LABEL///////////////////////////////////////////////
		/**
		 * Definition of labelNbGenerate
		 */
		policeLabel = new Font("Arial", Font.BOLD, 20);
		labelNbGenerate = new JLabel("Number resident to generate : ");
		labelNbGenerate.setBounds(100, 100, 350, 30);
		labelNbGenerate.setFont(policeLabel);
		this.add(labelNbGenerate);

		//////////////////// TEXT AREA////////////////////////////////////////////////
		/**
		 * Definition of textArea IdSensor
		 */
		textInputNbGenerate = new JTextField();
		textInputNbGenerate.setBounds(100, 150, 250, 30);
		textInputNbGenerate.setFont(policeLabel);
		textInputNbGenerate.setText("");
		this.add(textInputNbGenerate);

		///////////////////////// BUTTON/////////////////////////////////////////////////
		/**
		 * Definition of Button AddResident
		 */
		generateButton = new JButton("Generate");
		generateButton.setBounds(100, 350, 100, 40);
		this.add(generateButton);
		generateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (textInputNbGenerate.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Number of Resident to generate is empty", "Information", JOptionPane.INFORMATION_MESSAGE);
				} else {
					if (textInputNbGenerate.getText().trim().matches("[0-9]+[0-9]*")) {
						generateResident = new GenerateResident(Integer.parseInt(textInputNbGenerate.getText().trim()));
						generateResident.generate();
					} else {
						JOptionPane.showMessageDialog(null, "The number of generation is an integer", "Information", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});

		/**
		 * Set to default values the different fields
		 */
		restaureButton = new JButton("Restore");
		restaureButton.setBounds(500, 350, 100, 40);
		this.add(restaureButton);
		restaureButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textInputNbGenerate.setText("");
			}
		});

		///////////////////////// FRAME/////////////////////////////////////////////////
		/**
		 * Different parameters of the window
		 */
		this.setLayout(new BorderLayout());
		this.setBackground(color);
	}
}
