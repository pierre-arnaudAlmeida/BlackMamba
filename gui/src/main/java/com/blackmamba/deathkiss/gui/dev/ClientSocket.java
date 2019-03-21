package com.blackmamba.deathkiss.gui.dev;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientSocket implements Runnable {
	private Socket connexion = null;
	private PrintWriter writer = null;
	private BufferedInputStream reader = null;
	private static final Logger logger = LogManager.getLogger(ClientSocket.class);
	private String response;

	public ClientSocket(String host, int port) {
		try {
			connexion = new Socket(host, port);
		} catch (UnknownHostException e) {
			logger.log(Level.INFO, "IP Host dont find " + e.getClass().getCanonicalName());
		} catch (IOException e) {
			logger.log(Level.INFO, "Impossible create the socket" + e.getClass().getCanonicalName());
		}
	}

	public void run() {
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			writer = new PrintWriter(connexion.getOutputStream(), true);
			reader = new BufferedInputStream(connexion.getInputStream());

			writer.write("OPEN");
			writer.flush();
			logger.log(Level.INFO, "Command OPEN connection send to server");

			response = read();
			if (response.equals("OK FOR CONNECTION")) {
				response = "SELECT * FROM Employee";
				writer.write(response);
				writer.flush();
				logger.log(Level.INFO, "Request Send to server");

				response = read();
				if (!response.equals("ERROR")) {
					logger.log(Level.INFO, response);

					response = "CLOSE";
					writer.write(response);
					writer.flush();

					logger.log(Level.INFO, "Command CLOSE connection send to server");
					writer.close();
					logger.log(Level.INFO, "Connection Closed by client");
				} else {
					logger.log(Level.INFO, "ERROR, Impossible to Receive datas");
				}
			} else {
				writer.close();
				logger.log(Level.INFO, "Connection Closed by client");
			}
		} catch (IOException e1) {
			logger.log(Level.INFO, "Impossible to OPEN the connection to server " + e1.getClass().getCanonicalName());
		}

		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// writer.write("CLOSE");
		// writer.flush();
		// writer.close();
	}

	/**
	 * Read the diferent response
	 */
	private String read() throws IOException {
		String response = "";
		int stream;
		byte[] b = new byte[4096];
		stream = reader.read(b);
		response = new String(b, 0, stream);
		return response;
	}
}
