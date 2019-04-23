package com.blackmamba.deathkiss.gui;

import java.awt.EventQueue;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class MainGUI {

	private static Connexion popupConnexion;
	private static Logger logger = LogManager.getLogger(MainGUI.class);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// TODO remettre comme c'etait cette fucking connexion avant la R3
					// popupConnexion = new Connexion();
					// popupConnexion.setVisible(true);
					logger.log(Level.INFO, "Application Deathkiss runned");

					Frame frame = new Frame(1);
					frame.setVisible(true);
				} catch (Exception e) {
					logger.log(Level.INFO,
							"Impossible to create the window 'frame' " + e.getClass().getCanonicalName());
				}
			}
		});
	}
}