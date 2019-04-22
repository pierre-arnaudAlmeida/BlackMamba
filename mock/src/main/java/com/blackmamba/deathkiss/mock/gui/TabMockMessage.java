package com.blackmamba.deathkiss.mock.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TabMockMessage extends JPanel {

	/**
	 * Different parameters used
	 */
	private static final long serialVersionUID = 1L;
	private Font policeLabel;
	private JLabel labelIdSensor;
	private JLabel labelThreshold;
	private JTextField textInputIdSensor;

	public TabMockMessage() {

	}

	public TabMockMessage(Color color, String title) {
		///////////////////////// LABEL///////////////////////////////////////////////
		/**
		 * Definition of label IdSensor
		 */
		policeLabel = new Font("Arial", Font.BOLD, (int) getToolkit().getScreenSize().getWidth() / 80);
		labelIdSensor = new JLabel(" Id Capteur : ");
		labelIdSensor.setBounds(100, 100, 200, 30);
		labelIdSensor.setFont(policeLabel);
		this.add(labelIdSensor);

		/**
		 * Definition of label Threshold
		 */
		labelThreshold = new JLabel(" Sensibilité : ");
		labelThreshold.setBounds(100, 200, 200, 30);
		labelThreshold.setFont(policeLabel);
		this.add(labelThreshold);

		//////////////////// TEXT AREA////////////////////////////////////////////////
		/**
		 * Definition of textArea IdSensor
		 */
		textInputIdSensor = new JTextField();
		textInputIdSensor.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 5 / 20, 300, 40);
		textInputIdSensor.setFont(policeLabel);
		textInputIdSensor.setText("");
		this.add(textInputIdSensor);

		/**
		 * Definition of textArea Threshold
		 */
		// TODO mettre une barre

		// TODO mettre un choix du capteur
		// si on choisi un capteur on va regarder dans la table une fois la list des
		// capteurs et generer des capteurs en fonction du type et des sensors dispo
		// en supprimant tout les capteurs qui ne correspondent pas

		// TODO mettre un bouton a cocher si on veut un capteur random ou un precis en
		// fonction du champs de textInputIdSensor
		
		///////////////////////// FRAME/////////////////////////////////////////////////
		/**
		 * Different parameters of the window
		 */
		this.setLayout(new BorderLayout());
		this.setBackground(color);
	}
}
