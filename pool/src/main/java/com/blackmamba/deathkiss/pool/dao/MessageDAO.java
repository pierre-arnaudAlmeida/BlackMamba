package com.blackmamba.deathkiss.pool.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.blackmamba.deathkiss.pool.entity.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
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
	private String request;
	private Statement st;
	private StringBuilder requestSB;
	private PreparedStatement prepareStatement;
	private Message message;
	private Message mess;
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
		boolean result = false;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			mess = objectMapper.readValue(jsonString, Message.class);
			prepareStatement = con
					.prepareStatement("INSERT INTO message (id_capteur,date_alerte,seuil) values (?,?,?)");
			prepareStatement.setInt(1, mess.getIdSensor());
			prepareStatement.setDate(2, new java.sql.Date(mess.getAlertDate().getTime()));
			prepareStatement.setInt(3, mess.getThreshold());
			result = prepareStatement.execute();
			logger.log(Level.DEBUG, "Message succesfully inserted in BDD ");
		} catch (IOException | SQLException e) {
			logger.log(Level.WARN, "Impossible to insert message datas in BDD " + e.getClass().getCanonicalName());
		}
		return result;
	}

	/**
	 * Delete an message on Data Base when the date is inferior at the date set
	 */
	@Override
	public boolean delete(String jsonString) {
		boolean result = false;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			mess = objectMapper.readValue(jsonString, Message.class);
			requestSB = new StringBuilder("DELETE FROM message where date_alerte <");
			requestSB.append(new java.sql.Date(mess.getAlertDate().getTime()));
			st = con.createStatement();
			result = st.execute(requestSB.toString());
			logger.log(Level.DEBUG, "Messages succesfully deleted in BDD ");
		} catch (SQLException | IOException e) {
			logger.log(Level.WARN, "Impossible to delete Message datas in BDD " + e.getClass().getCanonicalName());
		}
		return result;
	}

	/**
	 * Update an message with the values get from GUI
	 */
	@Override
	public boolean update(String jsonString) {
		boolean result = false;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			mess = objectMapper.readValue(jsonString, Message.class);
			prepareStatement = con.prepareStatement(
					"UPDATE message SET id_capteur = ?, date_alerte = ?, seuil= ? where id_message = ?");
			prepareStatement.setInt(1, mess.getIdSensor());
			prepareStatement.setDate(2, new java.sql.Date(mess.getAlertDate().getTime()));
			prepareStatement.setInt(3, mess.getThreshold());
			prepareStatement.setInt(4, mess.getIdMessage());
			result = prepareStatement.execute();
			logger.log(Level.DEBUG, "Message succesfully update in BDD");
		} catch (SQLException | IOException e) {
			logger.log(Level.WARN, "Impossible to update message datas in BDD" + e.getClass().getCanonicalName());
		}
		return result;
	}

	/**
	 * Get a specific message with his ID
	 */
	@Override
	public String read(String jsonString) {
		ObjectMapper objWriter = new ObjectMapper();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			mess = objectMapper.readValue(jsonString, Message.class);
			requestSB = new StringBuilder("SELECT id_message,id_capteur,date_alerte,seuil FROM message ");
			requestSB.append("FROM message where id_message=");
			requestSB.append(mess.getIdMessage());
			st = con.createStatement();
			result = st.executeQuery(requestSB.toString());
			result.next();
			convertDatas(result);
			jsonString = objWriter.writeValueAsString(message);
			logger.log(Level.DEBUG, "Message succesfully find in BDD");
		} catch (SQLException | IOException | ParseException e) {
			logger.log(Level.WARN, "Impossible to get message datas from BDD " + e.getClass().getCanonicalName());
			jsonString = "ERROR";
		}
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
			request = "SELECT id_message,id_capteur,date_alerte,seuil FROM message;";
			st = con.createStatement();
			result = st.executeQuery(request);
			while (result.next()) {
				convertDatas(result);
				listMessage.add(message);
			}
			jsonString = objectMapper.writeValueAsString(listMessage);
			logger.log(Level.DEBUG, "Message succesfully find in BDD");
		} catch (SQLException | IOException | ParseException e) {
			logger.log(Level.WARN, "Impossible to get messages datas from BDD " + e.getClass().getCanonicalName());
			jsonString = "ERROR";
		}
		return jsonString;
	}

	/**
	 * Count the number of sensor down and the number of sensor over
	 * 
	 * @return
	 */
	public String countMessage() {
		ObjectMapper objWriter = new ObjectMapper();
		String jsonString = "";
		try {
			request = "SELECT sum(case when seuil = '0' AND (id_capteur !=0 AND id_capteur != 9999) then 1 else 0 end ) As nbSensorDown, sum(case when seuil = '0' AND (id_capteur !=0 AND id_capteur = 9999) then 1 else 0 end ) As nbSensorOver FROM message";
			st = con.createStatement();
			result = st.executeQuery(request);
			result.next();
			jsonString = result.getInt(1) + "," + result.getInt(2);
			jsonString = objWriter.writeValueAsString(jsonString);
		} catch (SQLException | JsonProcessingException e) {
			logger.log(Level.WARN, "Impossible to get Message datas from BDD " + e.getClass().getCanonicalName());
		}
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