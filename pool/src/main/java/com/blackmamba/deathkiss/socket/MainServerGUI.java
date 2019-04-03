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
	private static final long serialVersionUID = 1L;
	private static String host = "127.0.0.1";
	private static int port = 2345;
	private static int nbServer = 0;
	private static final Logger logger = LogManager.getLogger(MainServerGUI.class);
	private int heure = 0;
	private int minute = 0;
	private int seconde = 0;
	private ActionListener tache_timer;
	Timer timer1;

	public MainServerGUI() {
		JLabel time = new JLabel();
		JPanel container = new JPanel();
		/**
		 * Create a button to launch the server
		 */
		TimeServer ts = new TimeServer(host, port);

		JButton launch = new JButton("Lancer le server");
		launch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ts.load();
				if (nbServer == 0) {

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
						}
					};
					timer1 = new Timer(delais, tache_timer);
					timer1.start();

				} else {
					logger.log(Level.INFO, "Server already launch");
				}
			}
		});

		JButton launchFake = new JButton("Lancer le server Brider");
		launchFake.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ts.load();
				if (nbServer == 0) {

					ts.openFake();
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
						}
					};
					timer1 = new Timer(delais, tache_timer);
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
				ts.close();
				timer1.stop();
				nbServer = 0;
				logger.log(Level.INFO, "Server Closed");
				logger.log(Level.INFO, "Application closed");
				// System.exit(DISPOSE_ON_CLOSE);
			}
		});

		/**
		 * Create of panel to add the button
		 */

		container.add(launch);
		container.add(launchFake);
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
