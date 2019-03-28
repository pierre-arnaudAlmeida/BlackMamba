package com.blackmamba.deathkiss.socket;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class MainServerGUI extends JFrame {
	/**
	 * Initialization of diferents parameters
	 */
	private static String host = "127.0.0.1";
	private static int port = 2345;
	private static int nbServer = 0;
	private static final Logger logger = LogManager.getLogger(MainServerGUI.class);
	private int heure = 0;
	private int minute = 0;
	private int seconde = 0;
	private ActionListener tache_timer;

	public MainServerGUI() {
		JLabel time = new JLabel();
		/**
		 * Create a button to launch the server
		 */
		JButton launch = new JButton("Lancer le server");
		launch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (nbServer == 0) {
					TimeServer ts = new TimeServer(host, port);
					ts.open();
					nbServer++;
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
							time.setText(heure + ":" + minute + ":" + seconde);
							//logger.log(Level.INFO, "Time launched : "+heure + ":" + minute + ":" + seconde);
						}
					};
					final Timer timer1 = new Timer(delais, tache_timer);
					timer1.start();

				} else {
					logger.log(Level.INFO, "Server already launch");
				}
			}
		});

		/**
		 * Create a button to stop the server and close the window
		 */
		JButton stop = new JButton("Arreter le server");
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.log(Level.INFO, "Server Closed");
				logger.log(Level.INFO, "Application closed");
				System.exit(DISPOSE_ON_CLOSE);
			}
		});

		/**
		 * Create of panel to add the button
		 */
		JPanel container = new JPanel();
		container.add(launch);
		container.add(stop);
		container.add(time);

		this.setContentPane(container);
		this.setTitle("Server");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setSize(300, 100);
	}

	/**
	 * Main to open the Server
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MainServerGUI frame = new MainServerGUI();
		frame.setVisible(true);
	}
}
