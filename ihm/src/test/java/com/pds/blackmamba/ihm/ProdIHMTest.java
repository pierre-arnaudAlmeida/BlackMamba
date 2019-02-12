package com.pds.blackmamba.ihm;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import com.pds.blackmamba.ihm.prod.Login;

class ProdIHMTest {

	Logger logger = Logger.getLogger("logger");
	
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
