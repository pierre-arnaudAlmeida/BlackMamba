package com.blackmamba.deathkiss.gui.prod;

import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProdGUI {

	static Logger logger = Logger.getLogger("logger");
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					logger.log(Level.INFO, "Impossible to create the window 'Login' "+e.getClass().getCanonicalName());
				}
			}
		});
	}
}
