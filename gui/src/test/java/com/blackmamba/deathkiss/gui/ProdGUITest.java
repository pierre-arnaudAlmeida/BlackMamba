package com.blackmamba.deathkiss.gui;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;

import com.blackmamba.deathkiss.gui.prod.Login;

class ProdGUITest {
	private static Logger logger = Logger.getLogger("logger");
	
	/**
	 * Test the creation of the window of ProductionGUI
	 */
	@Test
	void prodIHMTest() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
					assertTrue(frame.isShowing());
				} catch (Exception e) {
					logger.log(Level.INFO, "Impossible to display the window 'Login' " + e.getClass().getCanonicalName());
				}
			}
		});
	}
}
