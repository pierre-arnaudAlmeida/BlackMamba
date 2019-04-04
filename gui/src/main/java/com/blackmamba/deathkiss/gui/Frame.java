package com.blackmamba.deathkiss.gui;

import java.awt.Color;
import javax.swing.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class Frame extends JFrame {

	/**
	 * Diferent parameters used
	 */
	private static final long serialVersionUID = 1L;
	private static JTabbedPane tab;
	private TabEmployes tabEmployes;
	private TabCommonArea tabCommonArea;
	private TabSensor tabSensor;
	private TabResident tabResident;
	private TabHistorical tabHistorical;
	private TabProfile tabProfile;
	private int idEmployee;
	private static final Logger logger = LogManager.getLogger(Frame.class);

	public Frame(int idEmployee) {
		this.idEmployee = idEmployee;

		/**
		 * LOGO
		 */
		logger.log(Level.INFO, "______           _   _     _    _         ");
		logger.log(Level.INFO, "|  _  \\         | | | |   | |  (_)        ");
		logger.log(Level.INFO, "| | | |___  __ _| |_| |__ | | ___ ___ ___ ");
		logger.log(Level.INFO, "| | | / _ \\/ _` | __| '_ \\| |/ / / __/ __|");
		logger.log(Level.INFO, "| |/ /  __/ (_| | |_| | | |   <| \\__ \\__ \\");
		logger.log(Level.INFO, "|___/ \\___|\\__,_|\\__|_| |_|_|\\_\\_|___/___/");
		logger.log(Level.INFO, "                                          ");

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

		///////////////////////// FRAME/////////////////////////////////////////////////
		/**
		 * Diferent parameters of the window
		 */
		this.setTitle("Deathkiss");
		this.setSize((int) getToolkit().getScreenSize().getWidth(), (int) getToolkit().getScreenSize().getHeight());
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().add(tab);
	}

	/**
	 * Method to switch to as other tab
	 * 
	 * @param index
	 */
	public static void goToTab(int index) {
		tab.setSelectedIndex(index);
	}

	/**
	 * Method to get the actual tab of frame
	 * 
	 * @return
	 */
	public static JTabbedPane getTab() {
		return tab;
	}

	/**
	 * Method to add a new tab on frame
	 * 
	 * @param tab
	 */
	public void setTab(JTabbedPane tab) {
		Frame.tab = tab;
	}
}
