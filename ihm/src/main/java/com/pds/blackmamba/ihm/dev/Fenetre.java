package com.pds.blackmamba.ihm.dev;

import java.awt.Color;

import javax.swing.*;
import javax.swing.border.Border;

public class Fenetre extends JFrame {

	private JTabbedPane onglet;

	public Fenetre() {

		Onglet ongletEmployes = new Onglet(Color.RED, "Employés");
		Onglet ongletPartiesCommunes = new Onglet(Color.GREEN, "Parties Communes");
		Onglet ongletCapteurs = new Onglet(Color.BLUE, "Capteurs");
		Onglet ongletResidants = new Onglet(Color.RED, "Résidants");
		Onglet ongletHistorique = new Onglet(Color.RED, "Historiques");

		Onglet[] tPan = { ongletEmployes, ongletPartiesCommunes, ongletCapteurs, ongletResidants, ongletHistorique };

		onglet = new JTabbedPane();
		int i = -1;
		String tableauOnglet[] = { "Employés", "Parties Communes", "Capteurs", "Résidants", "Historiques" };
		for (Onglet pan : tPan) {
			onglet.add("Onglet " + tableauOnglet[++i], pan);
		}

		this.setTitle("BlackMamba");
		this.setSize((int) getToolkit().getScreenSize().getWidth(), (int) getToolkit().getScreenSize().getHeight());
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().add(onglet);
	}
}
