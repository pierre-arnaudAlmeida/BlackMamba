package com.pds.blackmamba.ihm.dev;

import java.awt.Color;
import javax.swing.*;

public class Window extends JFrame {

	private JTabbedPane tab;

	public Window() {
		
		// Creation Tabs
		TabEmployes tabEmployes = new TabEmployes(Color.DARK_GRAY, "Employés");
		TabCommonArea tabCommonArea = new TabCommonArea(Color.DARK_GRAY, "Parties Communes");
		TabSensor tabSensor = new TabSensor(Color.DARK_GRAY, "Capteurs");
		TabResident tabResident = new TabResident(Color.DARK_GRAY, "Résidants");
		TabHistorical tabHistorical = new TabHistorical(Color.DARK_GRAY, "Historiques");
		TabProfile tlogin = new TabProfile(Color.DARK_GRAY, "Profil");

		tab = new JTabbedPane();
		String tabOfTab[] = { "Employés", "Parties Communes", "Capteurs", "Résidants", "Historiques", "Profil" };
		
		tab.add("Onglet " + tabOfTab[0], tabEmployes);
		tab.add("Onglet " + tabOfTab[1], tabCommonArea);
		tab.add("Onglet " + tabOfTab[2], tabSensor);
		tab.add("Onglet " + tabOfTab[3], tabResident);
		tab.add("Onglet " + tabOfTab[4], tabHistorical);
		tab.add("Onglet " + tabOfTab[5], tlogin);

		this.setTitle("BlackMamba");
		this.setSize((int) getToolkit().getScreenSize().getWidth(), (int) getToolkit().getScreenSize().getHeight());
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().add(tab);
	}
}
