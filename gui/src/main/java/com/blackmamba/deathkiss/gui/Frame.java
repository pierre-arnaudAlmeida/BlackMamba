package com.blackmamba.deathkiss.gui;

import java.awt.Color;
import javax.swing.*;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JTabbedPane tab;
	private TabEmployes tabEmployes;
	private TabCommonArea tabCommonArea;
	private TabSensor tabSensor;
	private TabResident tabResident;
	private TabHistorical tabHistorical;
	private TabProfile tabProfile;
	private int idEmployee;

	public Frame(int idEmployee) {
		this.idEmployee = idEmployee;

		/**
		 * Creation of diferents tabs
		 */
		tabEmployes = new TabEmployes(Color.GRAY, this.idEmployee, "Onglet Employés");
		tabCommonArea = new TabCommonArea(Color.GRAY, this.idEmployee, "Onglet Parties Communes");
		tabSensor = new TabSensor(Color.GRAY, this.idEmployee, "Onglet Capteurs", 0);
		tabResident = new TabResident(Color.GRAY, this.idEmployee, "Onglet Résidents");
		tabHistorical = new TabHistorical(Color.GRAY, this.idEmployee, "Onglet Historiques");
		tabProfile = new TabProfile(Color.GRAY, this.idEmployee, "Onglet Profil");

		/**
		 * Add of the title of tabs
		 */
		tab = new JTabbedPane();
		String tabOfTab[] = { "Employés", "Parties Communes", "Capteurs", "Résidents", "Historiques", "Profil" };

		/**
		 * Add of tabs on the window
		 */
		tab.add("Onglet " + tabOfTab[0], tabEmployes);
		tab.add("Onglet " + tabOfTab[1], tabCommonArea);
		tab.add("Onglet " + tabOfTab[2], tabSensor);
		tab.add("Onglet " + tabOfTab[3], tabResident);
		tab.add("Onglet " + tabOfTab[4], tabHistorical);
		tab.add("Onglet " + tabOfTab[5], tabProfile);

		/**
		 * Diferent parameters of the window
		 */
		this.setTitle("Deathkiss");
		this.setSize((int) getToolkit().getScreenSize().getWidth(), (int) getToolkit().getScreenSize().getHeight());
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().add(tab);
		
	}
	public static void goToTab(int index){
	tab.setSelectedIndex(index);
	}
	
	public static JTabbedPane getTab() {
		return tab;
	}

	public void setTab(JTabbedPane tab) {
		Frame.tab = tab;
	}
}
