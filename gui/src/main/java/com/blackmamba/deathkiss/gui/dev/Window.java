package com.blackmamba.deathkiss.gui.dev;

import java.awt.Color;
import javax.swing.*;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class Window extends JFrame {

	private JTabbedPane tab;
	private int idEmployee;

	public Window(int idEmployee) {
		this.idEmployee = idEmployee;

		/**
		 * Creation of diferents tabs
		 */
		TabEmployes tabEmployes = new TabEmployes(Color.GRAY, "Employés", this.idEmployee);
		TabCommonArea tabCommonArea = new TabCommonArea(Color.GRAY, "Parties Communes", this.idEmployee);
		TabSensor tabSensor = new TabSensor(Color.GRAY, "Capteurs", this.idEmployee);
		TabResident tabResident = new TabResident(Color.GRAY, "Résidants", this.idEmployee);
		TabHistorical tabHistorical = new TabHistorical(Color.GRAY, "Historiques", this.idEmployee);
		TabProfile tlogin = new TabProfile(Color.GRAY, "Profil", this.idEmployee);

		/**
		 * Add of the title of tabs
		 */
		tab = new JTabbedPane();
		String tabOfTab[] = { "Employés", "Parties Communes", "Capteurs", "Résidants", "Historiques", "Profil" };

		/**
		 * Add of tabs on the window
		 */
		tab.add("Onglet " + tabOfTab[0], tabEmployes);
		tab.add("Onglet " + tabOfTab[1], tabCommonArea);
		tab.add("Onglet " + tabOfTab[2], tabSensor);
		tab.add("Onglet " + tabOfTab[3], tabResident);
		tab.add("Onglet " + tabOfTab[4], tabHistorical);
		tab.add("Onglet " + tabOfTab[5], tlogin);

		/**
		 * Diferent parameters of the window
		 */
		this.setTitle("Deathkiss");
		this.setSize((int) getToolkit().getScreenSize().getWidth(), (int) getToolkit().getScreenSize().getHeight());
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().add(tab);
	}
}
