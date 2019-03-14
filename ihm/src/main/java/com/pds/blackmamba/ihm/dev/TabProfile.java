package com.pds.blackmamba.ihm.dev;

import java.awt.Color;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TabProfile extends JPanel {
	private String message;
	private Integer id_employee;
	private String password;
	private JOptionPane popupConnexion;
	
	public TabProfile(Color color, String title) {
		this.message= title;
		this.setBackground(color);
		popupConnexion = new JOptionPane();
		String textInput_id_employee = popupConnexion.showInputDialog(null,"Inserez vos identifiant", "Connexion", JOptionPane.INFORMATION_MESSAGE);
		String textInput_password = popupConnexion.showInputDialog(null, "Mot de passe", JOptionPane.INFORMATION_MESSAGE);
	}
}
