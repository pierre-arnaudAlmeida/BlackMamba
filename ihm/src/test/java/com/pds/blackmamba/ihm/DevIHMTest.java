package com.pds.blackmamba.ihm;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.EventQueue;
import org.junit.jupiter.api.Test;
import com.pds.blackmamba.ihm.dev.Window;

class DevIHMTest {

	@Test
	void devIHMTest() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
					frame.setVisible(true);
					assertTrue(frame.isShowing());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
