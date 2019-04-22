package com.blackmamba.deathkiss.gui;

import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class MainGUI {

	private static Connexion popupConnexion;
	private static Logger logger = Logger.getLogger("logger");

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