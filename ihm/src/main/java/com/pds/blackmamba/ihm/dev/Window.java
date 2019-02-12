package com.pds.blackmamba.ihm.dev;

import java.awt.Color;
import javax.swing.*;

public class Window extends JFrame {

	private JTabbedPane tab;

	public Window() {
		
		// Creation Tabs
		Tab tabEmployes = new Tab(Color.RED, "Employés");
		Tab tabCommonArea = new Tab(Color.GREEN, "Parties Communes");
		Tab tabsensor = new Tab(Color.BLUE, "Capteurs");
		Tab tabResident = new Tab(Color.RED, "Résidants");
		Tab tabHistorical = new Tab(Color.RED, "Historiques");

		Tab[] tPan = { tabEmployes, tabCommonArea, tabsensor, tabResident, tabHistorical };

		tab = new JTabbedPane();
		int i = -1;
		String tabOfTab[] = { "Employés", "Parties Communes", "Capteurs", "Résidants", "Historiques" };
		for (Tab pan : tPan) {
			tab.add("Onglet " + tabOfTab[++i], pan);
		}

		this.setTitle("BlackMamba");
		this.setSize((int) getToolkit().getScreenSize().getWidth(), (int) getToolkit().getScreenSize().getHeight());
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().add(tab);
	}
}
