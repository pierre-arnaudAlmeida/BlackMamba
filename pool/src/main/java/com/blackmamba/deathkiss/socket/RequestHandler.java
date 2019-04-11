package com.blackmamba.deathkiss.socket;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.blackmamba.deathkiss.dao.CommonAreaDAO;
import com.blackmamba.deathkiss.dao.DAO;
import com.blackmamba.deathkiss.dao.EmployeeDAO;
import com.blackmamba.deathkiss.dao.MessageDAO;
import com.blackmamba.deathkiss.dao.ResidentDAO;
import com.blackmamba.deathkiss.dao.SensorDAO;
import com.blackmamba.deathkiss.dao.SensorHistoricalDAO;
import com.blackmamba.deathkiss.entity.CommonArea;
import com.blackmamba.deathkiss.entity.Employee;
import com.blackmamba.deathkiss.entity.Message;
import com.blackmamba.deathkiss.entity.Resident;
import com.blackmamba.deathkiss.entity.Sensor;
import com.blackmamba.deathkiss.entity.SensorHistorical;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class RequestHandler implements Runnable {

	/**
	 * Initialization of diferents parameters
	 */
	private Socket sock;
	private PrintWriter writer = null;
	private BufferedInputStream reader = null;
	private String response;
	private String jsonString;
	private static final Logger logger = LogManager.getLogger(RequestHandler.class);
	private ObjectMapper objectMapper;
	private JsonNode jsonNode;
	private boolean result;
	private Connection connection;

	public RequestHandler(Socket pSock, Connection connection) {
		this.sock = pSock;
		this.connection = connection;
	}

	/**
	 * Run the Thread while the client connection is active they loop when
	 * connection is not closed
	 */
	public void run() {
		logger.log(Level.INFO, "Launch of treatement of client connection");
		while (!sock.isClosed()) {
			try {
				writer = new PrintWriter(sock.getOutputStream(), true);
				reader = new BufferedInputStream(sock.getInputStream());
				objectMapper = new ObjectMapper();

				/**
				 * wait the request from client
				 */
				response = read();
				if (response.equals("OPEN")) {
					response = "OK FOR EXCHANGE";
					writer.write(response);
					writer.flush();

					/**
					 * wait the requestType and the table from client and choose witch case they
					 * will execute with the requestType and the table
					 */
					response = read();
					if (!response.equals("")) {
						jsonNode = objectMapper.readTree(response);
						switch (jsonNode.get("request").asText()) {
						case "FIND ALL":
							response = "OK FOR REQUEST FIND ALL";
							writer.write(response);
							writer.flush();
							logger.log(Level.INFO, "Request Type accepted by server");

							response = read();
							switch (jsonNode.get("table").asText()) {
							case "Sensor":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<Sensor> sensorDao = new SensorDAO(connection);
									jsonString = ((SensorDAO) sensorDao).findAll(response);
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							case "Employee":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<Employee> employeeDao = new EmployeeDAO(connection);
									jsonString = ((EmployeeDAO) employeeDao).findByName(response);
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							case "Resident":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<Resident> residentDao = new ResidentDAO(connection);
									jsonString = ((ResidentDAO) residentDao).findByName(response);
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							case "CommonArea":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<CommonArea> commonAreaDao = new CommonAreaDAO(connection);
									jsonString = ((CommonAreaDAO) commonAreaDao).findByName(response);
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							case "SensorHistorical":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<SensorHistorical> sensorHistoricalDao = new SensorHistoricalDAO(connection);
									jsonString = ((SensorHistoricalDAO) sensorHistoricalDao).findBySensor(response);
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							}
							break;
						case "CONNECTION":
							response = "OK FOR REQUEST CONNECTION";
							writer.write(response);
							writer.flush();
							logger.log(Level.INFO, "Request Type accepted by server");

							response = read();
							if (!response.equals("")) {
								logger.log(Level.INFO, "Request received on server");
								DAO<Employee> employeeDao = new EmployeeDAO(connection);
								jsonString = ((EmployeeDAO) employeeDao).connection(response);
								writer.write(jsonString);
								writer.flush();
								logger.log(Level.INFO, "Response send to client");
							} else {
								logger.log(Level.INFO, "Request not recognized");
							}
							break;
						case "CREATE":
							response = "OK FOR REQUEST CREATE";
							writer.write(response);
							writer.flush();
							logger.log(Level.INFO, "Request Type accepted by server");

							response = read();
							switch (jsonNode.get("table").asText()) {
							case "Employee":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<Employee> employeeDao = new EmployeeDAO(connection);
									setResult(((EmployeeDAO) employeeDao).create(response));
									jsonString = "INSERTED";
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							case "Resident":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<Resident> residentDao = new ResidentDAO(connection);
									setResult(((ResidentDAO) residentDao).create(response));
									jsonString = "INSERTED";
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							case "Sensor":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<Sensor> sensorDao = new SensorDAO(connection);
									setResult(((SensorDAO) sensorDao).create(response));
									jsonString = "INSERTED";
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							case "CommonArea":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<CommonArea> commonAreaDao = new CommonAreaDAO(connection);
									setResult(((CommonAreaDAO) commonAreaDao).create(response));
									jsonString = "INSERTED";
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							case "SensorHistorical":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<SensorHistorical> sensorHistoricalDao = new SensorHistoricalDAO(connection);
									setResult(((SensorHistoricalDAO) sensorHistoricalDao).create(response));
									jsonString = "INSERTED";
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							case "Message":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<Message> messageDao = new MessageDAO(connection);
									setResult(((MessageDAO) messageDao).create(response));
									jsonString = "INSERTED";
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							}
							break;
						case "UPDATE":
							response = "OK FOR REQUEST UPDATE";
							writer.write(response);
							writer.flush();
							logger.log(Level.INFO, "Request Type accepted by server");

							response = read();
							switch (jsonNode.get("table").asText()) {
							case "Employee":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<Employee> employeeDao = new EmployeeDAO(connection);
									setResult(((EmployeeDAO) employeeDao).update(response));
									jsonString = "UPDATED";
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							case "Resident":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<Resident> residentDao = new ResidentDAO(connection);
									setResult(((ResidentDAO) residentDao).update(response));
									jsonString = "UPDATED";
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							case "Sensor":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<Sensor> sensorDao = new SensorDAO(connection);
									setResult(((SensorDAO) sensorDao).update(response));
									jsonString = "UPDATED";
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							case "CommonArea":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<CommonArea> commonAreaDao = new CommonAreaDAO(connection);
									setResult(((CommonAreaDAO) commonAreaDao).update(response));
									jsonString = "UPDATED";
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							case "SensorHistorical":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<SensorHistorical> sensorHistoricalDao = new SensorHistoricalDAO(connection);
									setResult(((SensorHistoricalDAO) sensorHistoricalDao).update(response));
									jsonString = "UPDATED";
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							case "Message":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<Message> messageDao = new MessageDAO(connection);
									setResult(((MessageDAO) messageDao).update(response));
									jsonString = "UPDATED";
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							}
							break;
						case "DELETE":
							response = "OK FOR REQUEST DELETE";
							writer.write(response);
							writer.flush();
							logger.log(Level.INFO, "Request Type accepted by server");

							response = read();
							switch (jsonNode.get("table").asText()) {
							case "Employee":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<Employee> employeeDao = new EmployeeDAO(connection);
									setResult(((EmployeeDAO) employeeDao).delete(response));
									jsonString = "DELETED";
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							case "Resident":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<Resident> residentDao = new ResidentDAO(connection);
									setResult(((ResidentDAO) residentDao).delete(response));
									jsonString = "DELETED";
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							case "Sensor":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<Sensor> sensorDao = new SensorDAO(connection);
									setResult(((SensorDAO) sensorDao).delete(response));
									jsonString = "DELETED";
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							case "CommonArea":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<CommonArea> commonAreaDao = new CommonAreaDAO(connection);
									setResult(((CommonAreaDAO) commonAreaDao).delete(response));
									jsonString = "DELETED";
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							case "SensorHistorical":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<SensorHistorical> sensorHistoricalDao = new SensorHistoricalDAO(connection);
									setResult(((SensorHistoricalDAO) sensorHistoricalDao).delete(response));
									jsonString = "DELETED";
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							case "Message":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<Message> messageDao = new MessageDAO(connection);
									setResult(((MessageDAO) messageDao).delete(response));
									jsonString = "DELETED";
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							}
							break;
						case "READ":
							response = "OK FOR REQUEST READ";
							writer.write(response);
							writer.flush();
							logger.log(Level.INFO, "Request Type accepted by server");

							response = read();
							switch (jsonNode.get("table").asText()) {
							case "Employee":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<Employee> employeeDao = new EmployeeDAO(connection);
									jsonString = ((EmployeeDAO) employeeDao).read(response);
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							case "Resident":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<Resident> residentDao = new ResidentDAO(connection);
									jsonString = ((ResidentDAO) residentDao).read(response);
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							case "Sensor":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<Sensor> sensorDao = new SensorDAO(connection);
									jsonString = ((SensorDAO) sensorDao).read(response);
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							case "CommonArea":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<CommonArea> commonAreaDao = new CommonAreaDAO(connection);
									jsonString = ((CommonAreaDAO) commonAreaDao).read(response);
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							case "SensorHistorical":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<SensorHistorical> sensorHistoricalDao = new SensorHistoricalDAO(connection);
									jsonString = ((SensorHistoricalDAO) sensorHistoricalDao).read(response);
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							case "Message":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<Message> messageDao = new MessageDAO(connection);
									jsonString = ((MessageDAO) messageDao).read(response);
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							}
							break;
						case "READ ALL":
							response = "OK FOR REQUEST READ ALL";
							writer.write(response);
							writer.flush();
							logger.log(Level.INFO, "Request Type accepted by server");

							response = read();
							switch (jsonNode.get("table").asText()) {
							case "Employee":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<Employee> employeeDao = new EmployeeDAO(connection);
									jsonString = ((EmployeeDAO) employeeDao).readAll(response);
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							case "Resident":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<Resident> residentDao = new ResidentDAO(connection);
									jsonString = ((ResidentDAO) residentDao).readAll(response);
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							case "Sensor":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<Sensor> sensorDao = new SensorDAO(connection);
									jsonString = ((SensorDAO) sensorDao).readAll(response);
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							case "CommonArea":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<CommonArea> commonAreaDao = new CommonAreaDAO(connection);
									jsonString = ((CommonAreaDAO) commonAreaDao).readAll(response);
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							case "SensorHistorical":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<SensorHistorical> sensorHistoricalDao = new SensorHistoricalDAO(connection);
									jsonString = ((SensorHistoricalDAO) sensorHistoricalDao).readAll(response);
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							case "Message":
								if (!response.equals("")) {
									logger.log(Level.INFO, "Request received on server");
									DAO<Message> messageDao = new MessageDAO(connection);
									jsonString = ((MessageDAO) messageDao).readAll(response);
									writer.write(jsonString);
									writer.flush();
									logger.log(Level.INFO, "Response send to client");
								} else {
									logger.log(Level.INFO, "Request not recognized");
								}
								break;
							}
							break;
						}
						response = read();
						if (response.equals("CLOSE")) {
							sock.close();
							logger.log(Level.INFO, "Socket Closed by Server");
						}
					} else {
						response = "ERROR";
						writer.write(response);
						writer.flush();
						logger.log(Level.INFO, "Resquest not recognized");

						response = read();
						if (response.equals("CLOSE")) {
							sock.close();
							logger.log(Level.INFO, "Socket Closed by Server");
						}
					}
				} else {
					response = "ERROR";
					writer.write(response);
					writer.flush();

					response = read();
					if (response.equals("CLOSE")) {
						sock.close();
						logger.log(Level.INFO, "Socket Closed by Server");
					}
				}
			} catch (IOException e) {
				logger.log(Level.INFO, "Impossible to execute the request " + e.getClass().getCanonicalName());
			}
		}
		if (sock.isClosed())
			logger.log(Level.INFO, "Connection Closed");
	}

	/**
	 * Read the different response
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
	 * return the result of the execution of request
	 */
	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
}