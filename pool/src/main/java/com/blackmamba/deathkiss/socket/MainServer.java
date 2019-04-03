package com.blackmamba.deathkiss.socket;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class MainServer {
	/**
	 * Initialization of diferents parameters
	 */
	private static String host = "127.0.0.1";
	private static int port = 2345;
	private static final Logger logger = LogManager.getLogger(MainServer.class);
	private static int heure = 0;
	private static int minute = 0;
	private static int seconde = 0;
	private static ActionListener tache_timer;

	/**
	 * Main to open the Server
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		TimeServer ts = new TimeServer(host, port);
		ts.open();
		logger.log(Level.INFO, "Server Initialized");

		/**
		 * Creation of Timer to know how many time the server was launch
		 */
		int delais = 1000;
		tache_timer = new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				seconde++;
				if (seconde == 60) {
					seconde = 0;
					minute++;
				}
				if (minute == 60) {
					minute = 0;
					heure++;
				}
				logger.log(Level.INFO, "Time launched : " + heure + ":" + minute + ":" + seconde);
			}
		};
		final Timer timer1 = new Timer(delais, tache_timer);
		timer1.start();
	}
}