package com.blackmamba.deathkiss.socket;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ResourceBundle;
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
	 * Initialization of different parameters
	 */
	private static String host = "127.0.0.1";
	private static int port = 2345;
	private static final Logger logger = LogManager.getLogger(MainServer.class);
	private static int heure = 0;
	private static int minute = 0;
	private static int seconde = 0;
	private static ActionListener tache_timer;
	private static Server ts;
	static Timer timer1;

	/**
	 * Main to open the Server
	 * 
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		ResourceBundle rs = ResourceBundle.getBundle("config");
		host = rs.getString("server.host");
		port = Integer.parseInt(rs.getString("server.port"));
		ts = new Server(host, port);
		ts.open();

		logger.log(Level.INFO, "______           _   _     _    _         ");
		logger.log(Level.INFO, "|  _  \\         | | | |   | |  (_)        ");
		logger.log(Level.INFO, "| | | |___  __ _| |_| |__ | | ___ ___ ___ ");
		logger.log(Level.INFO, "| | | / _ \\/ _` | __| '_ \\| |/ / / __/ __|");
		logger.log(Level.INFO, "| |/ /  __/ (_| | |_| | | |   <| \\__ \\__ \\");
		logger.log(Level.INFO, "|___/ \\___|\\__,_|\\__|_| |_|_|\\_\\_|___/___/");
		logger.log(Level.INFO, "                                          ");
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
			}
		};

		timer1 = new Timer(delais, tache_timer);
		timer1.start();
	}
}