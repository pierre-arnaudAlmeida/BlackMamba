package com.pds.blackmamba.ihm.dev;


import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DevIHM {
	
	static Logger logger = Logger.getLogger("logger");
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//Lignes qui doivent etre décommenter pour que l'application fonctionne correctement
					//Connexion popupConnexion = new Connexion();
					//popupConnexion.setVisible(true);
					
					//pour aller plus vite dans le visionnage de la seconde fenetre
					Window frame = new Window();
					frame.setVisible(true);
				} catch (Exception e) {
					logger.log(Level.INFO, "Impossible to create the window 'window' "+e.getClass().getCanonicalName());
				}
			}
		});
	}
}
