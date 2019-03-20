package com.blackmamba.deathkiss.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.blackmamba.deathkiss.connectionpool.JDBCConnectionPool;

public class TimeServer {
	/**
	 * Initialization of parameters
	 */
	private int port = 2345;
	private String host = "127.0.0.1";
	private ServerSocket server = null;
	private boolean isRunning = true;
	private static final Logger logger = LogManager.getLogger(TimeServer.class);

	/**
	 * Constructor without parameters
	 */
	public TimeServer() {
		try {
			server = new ServerSocket(port, 100, InetAddress.getByName(host));
		} catch (UnknownHostException e) {
			logger.log(Level.INFO, "IP Host dont find " + e.getClass().getCanonicalName());
		} catch (IOException e) {
			logger.log(Level.INFO, "Impossible create the socket" + e.getClass().getCanonicalName());
		}
	}

	/**
	 * Constructor
	 * 
	 * @param Host
	 * @param Port
	 */
	public TimeServer(String pHost, int pPort) {
		this.host = pHost;
		this.port = pPort;
		try {
			server = new ServerSocket(port, 100, InetAddress.getByName(host));
		} catch (UnknownHostException e) {
			logger.log(Level.INFO, "IP Host dont find " + e.getClass().getCanonicalName());
		} catch (IOException e) {
			logger.log(Level.INFO, "Impossible create the socket" + e.getClass().getCanonicalName());
		}
	}

	/**
	 * Launch the server Create a thread, and accept the socket if the number of
	 * connection dont was on maximum
	 */
	public void open() {
		Thread t = new Thread(new Runnable() {
			public void run() {
				JDBCConnectionPool pool;
				while (isRunning == true) {
					try {
						pool = new JDBCConnectionPool(false);
						Socket client = server.accept();
						logger.log(Level.INFO, "Client Connection recieved");
						Thread t = new Thread(new RequestHandler(client, pool));
						t.start();
					} catch (IOException | SQLException e) {
						logger.log(Level.INFO, "Server already launch " + e.getClass().getCanonicalName());
					}
				}
				try {
					server.close();
					logger.log(Level.INFO, "Server closed");
				} catch (IOException e) {
					logger.log(Level.INFO, "Impossible to close server " + e.getClass().getCanonicalName());
					server = null;
				}
			}
		});
		t.start();
	}

	/**
	 * Set false the Socket runner to Close the socket
	 */
	public void close() {
		isRunning = false;
	}
}
