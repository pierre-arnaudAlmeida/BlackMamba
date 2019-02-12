package com.pds.blackmamba.ihm;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.EventQueue;
import org.junit.jupiter.api.Test;
import com.pds.blackmamba.ihm.prod.InsertionClient;

class ProdIHMTest {

	@Test
	void prodIHMTest() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InsertionClient frame = new InsertionClient();
					frame.setVisible(true);
					assertTrue(frame.isShowing());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
