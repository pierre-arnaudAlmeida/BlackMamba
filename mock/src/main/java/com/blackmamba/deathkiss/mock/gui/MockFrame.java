package com.blackmamba.deathkiss.mock.gui;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MockFrame extends JFrame {

	/**
	 * Different parameters used
	 */
	private static final long serialVersionUID = 1L;
	private static JTabbedPane tab;
	private TabMockMessage tabMockMessage;
	private static final Logger logger = LogManager.getLogger(MockFrame.class);

	/**
	 * Constructor
	 */
	public MockFrame() {

		/**
		 * LOGO
		 */
		logger.log(Level.INFO, "___  ___           _     ______           _   _     _    _         ");
		logger.log(Level.INFO, "|  \\/  |          | |    |  _  \\         | | | |   | |  (_)        ");
		logger.log(Level.INFO, "| .  . | ___   ___| | __ | | | |___  __ _| |_| |__ | | ___ ___ ___ ");
		logger.log(Level.INFO, "| |\\/| |/ _ \\ / __| |/ / | | | / _ \\/ _` | __| '_ \\| |/ / / __/ __|");
		logger.log(Level.INFO, "| |  | | (_) | (__|   <  | |/ /  __/ (_| | |_| | | |   <| \\__ \\__ \\");
		logger.log(Level.INFO, "\\_|  |_/\\___/ \\___|_|\\_\\ |___/ \\___|\\__,_|\\__|_| |_|_|\\_\\_|___/___/");

		/**
		 * Creation of different tabs
		 */
		tabMockMessage = new TabMockMessage(Color.GRAY, "Tab Message");

		/**
		 * Add of the title of tabs
		 */
		tab = new JTabbedPane();
		String tabOfTab[] = { "Message" };

		/**
		 * Add of tabs on the window
		 */
		tab.add("Tab " + tabOfTab[0], tabMockMessage);

		///////////////////////// FRAME/////////////////////////////////////////////////
		/**
		 * Different parameters of the window
		 */
		this.setTitle("Deathkiss Mock");
		this.setSize(850, 600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().add(tab);
	}

	/**
	 * 
	 * @return the width of the frame
	 */
	public int getWidthMockFrame() {
		return this.getWidth();
	}
}