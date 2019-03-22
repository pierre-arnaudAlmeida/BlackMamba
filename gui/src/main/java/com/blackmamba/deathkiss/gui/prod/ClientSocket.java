package com.blackmamba.deathkiss.gui.prod;

import java.io.BufferedInputStream;
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
	private String requestType;
	private String response;
	private String table;
	private static String jsonString;
	private static String result;
	static Employee emp = new Employee();
	private String host = "127.0.0.1";
	private int port = 2345;

	public ClientSocket(String requestType, String jsonString, String table) {
		this.requestType = requestType;
		this.table = table;
		ClientSocket.jsonString = jsonString;
		try {
			connexion = new Socket(host, port);
			writer = new PrintWriter(connexion.getOutputStream(), true);
			reader = new BufferedInputStream(connexion.getInputStream());

			writer.write("OPEN");
			writer.flush();
			logger.log(Level.INFO, "Command OPEN connection send to server");

			response = read();
			if (response.equals("OK FOR EXCHANGE")) {
				switch (this.requestType) {
				case "CONNECTION":
					response = "{ \"request\" : \"CONNECTION\", \"table\" : \"" + this.table + "\" }";
					break;
				case "CREATE":
					response = "{ \"request\" : \"CREATE\", \"table\" : \"" + this.table + "\" }";
					break;
				case "UPDATE":
					response = "{ \"request\" : \"UPDATE\", \"table\" : \"" + this.table + "\" }";
					break;
				case "DELETE":
					response = "{ \"request\" : \"DELETE\", \"table\" : \"" + this.table + "\" }";
					break;
				case "READ":
					response = "{ \"request\" : \"READ\", \"table\" : \"" + this.table + "\" }";
					break;
				case "READ ALL":
					response = "{ \"request\" : \"READ ALL\", \"table\" : \"" + this.table + "\" }";
					break;
				default:
					response = "";
				}
				writer.write(response);
				writer.flush();
				logger.log(Level.INFO, "Request Type Send to server");

				response = read();
				if (!response.equals("ERROR")) {
					response = jsonString;
					writer.write(response);
					writer.flush();
					logger.log(Level.INFO, "Request Send to server");

					response = read();
					if (!response.equals("ERROR")) {
						this.jsonString = response;
						logger.log(Level.INFO, "Datas received on client");
						response = "CLOSE";
						writer.write(response);
						writer.flush();
						logger.log(Level.INFO, "Command CLOSE connection send to server");
						writer.close();
						logger.log(Level.INFO, "Connection Closed by client");
					} else {
						response = "CLOSE";
						writer.write(response);
						writer.flush();
						logger.log(Level.INFO, "Command CLOSE connection send to server");
						writer.close();
						logger.log(Level.INFO, "Connection Closed by client");
					}
				} else {
					response = "CLOSE";
					writer.write(response);
					writer.flush();
					logger.log(Level.INFO, "Command CLOSE connection send to server");
					writer.close();
					logger.log(Level.INFO, "Connection Closed by client");
				}
			} else {
				logger.log(Level.INFO, "ERROR, Impossible to Receive datas");
				response = "CLOSE";
				writer.write(response);
				writer.flush();
				logger.log(Level.INFO, "Command CLOSE connection send to server");
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
	 * 
	 * @return JsonString
	 */
	static String getJson() {
		return jsonString;
	}
}
