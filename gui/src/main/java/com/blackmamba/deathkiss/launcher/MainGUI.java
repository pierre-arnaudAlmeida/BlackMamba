package com.blackmamba.deathkiss.launcher;

import java.awt.EventQueue;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.blackmamba.deathkiss.gui.Connexion;
import com.blackmamba.deathkiss.gui.Frame;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class MainGUI {

	/**
	 * Different parameters used
	 */
	private static Connexion popupConnexion;
	private static Logger logger = LogManager.getLogger(MainGUI.class);

	/**
	 * Method Main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//popupConnexion = new Connexion();
					//popupConnexion.setVisible(true);
					Frame frame = new Frame(1);
					frame.setVisible(true);
					logger.log(Level.INFO, "Application Deathkiss runned");
				} catch (Exception e) {
					logger.log(Level.WARN, "Impossible to create the window 'frame' " + e.getClass().getCanonicalName());
				}
			}
		});
	}
}