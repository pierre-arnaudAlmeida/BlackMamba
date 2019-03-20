package com.blackmamba.deathkiss.socket;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainServer {
	/**
	 * Initialization of diferents parameters
	 */
	private static String host = "127.0.0.1";
	private static int port = 2345;
	private static final Logger logger = LogManager.getLogger(MainServer.class);

	/**
	 * Main to open the Server
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		TimeServer ts = new TimeServer(host, port);
		ts.open();
		logger.log(Level.INFO, "Server Initialized");
	}
}