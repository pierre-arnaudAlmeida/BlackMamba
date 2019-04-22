package com.blackmamba.deathkiss.mock.gui;

import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainMockGUI {

	private static MockFrame mockFrame;
	private static Logger logger = Logger.getLogger("logger");

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mockFrame = new MockFrame();
					mockFrame.setVisible(true);
					logger.log(Level.INFO, "Application Mock runned");
				} catch (Exception e) {
					logger.log(Level.INFO,
							"Impossible to create the window 'frame' " + e.getClass().getCanonicalName());
				}
			}
		});
	}
}
