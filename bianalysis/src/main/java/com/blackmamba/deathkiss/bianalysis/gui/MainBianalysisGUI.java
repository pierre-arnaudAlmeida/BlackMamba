package com.blackmamba.deathkiss.bianalysis.gui;

import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class MainBianalysisGUI extends JFrame {

//	private static Connexion popupConnexion;
	private static Logger logger = Logger.getLogger("logger");

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// popupConnexion = new Connexion();
					// popupConnexion.setVisible(true);
					logger.log(Level.INFO, "Application runned");

					GUIBi frame = new GUIBi();
					frame.setVisible(true);
				} catch (Exception e) {
					logger.log(Level.INFO,
							"Impossible to create the window 'window' " + e.getClass().getCanonicalName());
				}
			}
		});
	}
}