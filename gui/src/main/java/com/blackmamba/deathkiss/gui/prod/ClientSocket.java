package com.blackmamba.deathkiss.gui.prod;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.blackmamba.deathkiss.entity.Employee;

public class ClientSocket {

	private Socket connexion = null;
	private PrintWriter writer = null;
	private BufferedInputStream reader = null;
	private static final Logger logger = LogManager.getLogger(ClientSocket.class);
	private String response;
	private static String responseRequest;
	private String requestType;
	private String jsonString;
	BufferedWriter buffer = null;
	static Employee emp = new Employee();

	public ClientSocket(String host, int port, String requestType, String jsonString) {
		this.requestType = requestType;
		this.jsonString = jsonString;
		try {
			connexion = new Socket(host, port);
			writer = new PrintWriter(connexion.getOutputStream(), true);
			reader = new BufferedInputStream(connexion.getInputStream());

			writer.write("OPEN");
			writer.flush();
			logger.log(Level.INFO, "Command OPEN connection send to server");

			response = read();
			if (response.equals("OK FOR CONNECTION")) {
				switch (this.requestType) {
				case "CONNECTION":
					response = jsonString;
					break;
				default:
					response = "";
				}
				writer.write(response);
				writer.flush();
				logger.log(Level.INFO, "Request Send to server");

				response = read();
				if (!response.equals("ERROR")) {
					logger.log(Level.INFO, response);
					responseRequest = response;
					
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
		} catch (UnknownHostException e) {
			logger.log(Level.INFO, "IP Host dont find " + e.getClass().getCanonicalName());
		} catch (IOException e) {
			logger.log(Level.INFO, "Impossible create the socket " + e.getClass().getCanonicalName());
		}
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

	/**
	 * Have the Json response from server
	 * @return JsonString 
	 */
	static String getJson() {
		return responseRequest;
	}
}
