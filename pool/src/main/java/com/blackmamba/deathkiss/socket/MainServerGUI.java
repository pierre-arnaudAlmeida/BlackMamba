package com.blackmamba.deathkiss.socket;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainServerGUI extends JFrame {
	/**
	 * Initialization of diferents parameters
	 */
	private static String host = "127.0.0.1";
	private static int port = 2345;
	private static int nbServer = 0;
	private static final Logger logger = LogManager.getLogger(MainServer.class);

	public MainServerGUI() {
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

		this.setContentPane(container);
		this.setTitle("Server");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setSize(300, 80);
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
