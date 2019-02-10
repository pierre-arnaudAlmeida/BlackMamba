package com.pds.blackmamba.ihm;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.EventQueue;
import org.junit.jupiter.api.Test;
import com.pds.blackmamba.ihm.prod.Fenetre;

class ProdIHMTest {

	@Test
	void prodIHMTest() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Fenetre frame = new Fenetre();
					frame.setVisible(true);
					assertTrue(frame.isShowing());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
