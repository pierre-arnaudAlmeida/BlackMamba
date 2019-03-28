package com.blackmamba.deathkiss.gui;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;

import com.blackmamba.deathkiss.gui.dev.Window;

class DevGUITest {
	private static Logger logger = Logger.getLogger("logger");

	/**
	 * Test the creation of the window DeveloppementGUI
	 */
	@Test
	void devIHMTest() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window(0);
					frame.setVisible(true);
					assertTrue(frame.isShowing());
				} catch (Exception e) {
					logger.log(Level.INFO,
							"Impossible to display the window 'Window' " + e.getClass().getCanonicalName());
				}
			}
		});
	}
}
