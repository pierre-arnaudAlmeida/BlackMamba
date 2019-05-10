package com.blackmamba.deathkiss.pool.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.blackmamba.deathkiss.pool.entity.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class MessageDAO extends DAO<Message> {

	/**
	 * Initialization of parameters
	 */
	private ResultSet result = null;
	private SimpleDateFormat dateFormat;
	private Date alertDate;
	private String request;
	private Message message;
	private static final Logger logger = LogManager.getLogger(MessageDAO.class);

	/**
	 * Constructor
	 * 
	 * @param con
	 */
	public MessageDAO(Connection con) {
		super(con);
	}

	/**
	 * Convert the JSON string in Object and create a request to insert values in
	 * table 'message' return a boolean
	 */
	@Override
	public boolean create(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Message mess = objectMapper.readValue(jsonString, Message.class);
			java.sql.Date sqlDate = new java.sql.Date(mess.getAlertDate().getTime());
			request = "insert into message (id_capteur,date_alerte,seuil) values ('" + mess.getIdSensor() + "','" + sqlDate + "', '" + mess.getThreshold() + "');";
			Statement st = con.createStatement();
			st.execute(request);
			logger.log(Level.DEBUG, "Message succesfully inserted in BDD ");
			return true;
		} catch (IOException | SQLException e) {
			e.printStackTrace();
			logger.log(Level.WARN, "Impossible to insert message datas in BDD " + e.getClass().getCanonicalName());
			return false;
		}
	}

	/**
	 * Delete an message on Data Base when the date is inferior at the date set
	 */
	@Override
	public boolean delete(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Message mess = objectMapper.readValue(jsonString, Message.class);
			request = "DELETE FROM message where date_alerte < " + mess.getAlertDate() + ";";
			Statement st = con.createStatement();
			st.execute(request);
			logger.log(Level.DEBUG, "Messages succesfully deleted in BDD ");
			return true;
		} catch (SQLException | IOException e) {
			logger.log(Level.WARN, "Impossible to delete Message datas in BDD " + e.getClass().getCanonicalName());
			return false;
		}
	}

	/**
	 * Update an message with the values get from GUI
	 */
	@Override
	public boolean update(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Message mess = objectMapper.readValue(jsonString, Message.class);
			java.sql.Date sqlDate = new java.sql.Date(mess.getAlertDate().getTime());
			request = "UPDATE message SET id_capteur = '" + mess.getIdSensor() + "', date_alerte = '" + sqlDate + "', seuil='" + mess.getThreshold() + "';";
			Statement st = con.createStatement();
			st.execute(request);
			logger.log(Level.DEBUG, "Message succesfully update in BDD");
			return true;
		} catch (SQLException | IOException e) {
			logger.log(Level.WARN, "Impossible to update message datas in BDD" + e.getClass().getCanonicalName());
			return false;
		}
	}

	/**
	 * Get a specific message with his ID
	 */
	@Override
	public String read(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Message mess = objectMapper.readValue(jsonString, Message.class);
			request = "SELECT * FROM message where id_message='" + mess.getIdMessage() + "';";
			Statement st = con.createStatement();
			result = st.executeQuery(request);
			result.next();
			convertDatas(result);
			ObjectMapper obj = new ObjectMapper();
			jsonString = obj.writeValueAsString(message);
			logger.log(Level.DEBUG, "Message succesfully find in BDD");
			return jsonString;
		} catch (SQLException | IOException | ParseException e) {
			logger.log(Level.WARN, "Impossible to get message datas from BDD " + e.getClass().getCanonicalName());
		}
		jsonString = "ERROR";
		return jsonString;
	}

	/**
	 * Get all messages
	 */
	@Override
	public String readAll(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		List<Message> listMessage = new ArrayList<>();
		try {
			request = "SELECT * FROM message;";
			Statement st = con.createStatement();
			result = st.executeQuery(request);
			while (result.next()) {
				convertDatas(result);
				listMessage.add(message);
			}
			jsonString = objectMapper.writeValueAsString(listMessage);
			logger.log(Level.DEBUG, "Message succesfully find in BDD");
			return jsonString;
		} catch (SQLException | IOException | ParseException e) {
			logger.log(Level.WARN, "Impossible to get messages datas from BDD " + e.getClass().getCanonicalName());
		}
		jsonString = "ERROR";
		return jsonString;
	}

	/**
	 * Transform the result of the request in one Message object
	 * 
	 * @param result
	 * @throws NumberFormatException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void convertDatas(ResultSet result) throws NumberFormatException, SQLException, ParseException {
		message = new Message();
		message.setIdMessage(result.getInt("id_message"));
		message.setIdSensor(result.getInt("id_capteur"));
		message.setAlertDate(result.getDate("date_alerte"));
		message.setThreshold(result.getInt("seuil"));
		logger.log(Level.DEBUG, "Convertion resultSet into message java object succeed");
	}
}