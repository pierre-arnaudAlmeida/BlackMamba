package com.pds.blackmamba.ihm;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import com.pds.blackmamba.ihm.dev.Window;

class DevIHMTest {

	Logger logger = Logger.getLogger("logger");
	
	@Test
	void devIHMTest() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
					frame.setVisible(true);
					assertTrue(frame.isShowing());
				} catch (Exception e) {
					logger.log(Level.INFO, "Impossible to display the window 'Window' " + e.getClass().getCanonicalName());
				}
			}
		});
	}

}
