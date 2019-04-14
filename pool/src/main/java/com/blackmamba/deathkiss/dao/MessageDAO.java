package com.blackmamba.deathkiss.dao;

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
import com.blackmamba.deathkiss.entity.AlertState;
import com.blackmamba.deathkiss.entity.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class MessageDAO extends DAO<Message> {

	private ResultSet result = null;
	private SimpleDateFormat dateFormat;
	private Date alertDate;
	private static final Logger logger = LogManager.getLogger(MessageDAO.class);

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
		String request;

		try {
			Statement st = con.createStatement();
			Message message = objectMapper.readValue(jsonString, Message.class);
			java.sql.Date sqlDate = new java.sql.Date(message.getAlertDate().getTime());
			request = "insert into message (type_alerte,id_capteur,date_alerte) values ('" + message.getAlertState()
					+ "','" + message.getIdSensor() + "','" + sqlDate + ")";
			st.execute(request);
			logger.log(Level.INFO, "Message succesfully inserted in BDD ");
			return true;
		} catch (IOException | SQLException e) {
			logger.log(Level.INFO, "Impossible to insert message datas in BDD " + e.getClass().getCanonicalName());
			return false;
		}
	}

	@Override
	public boolean delete(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		String request;
		try {
			Statement st = con.createStatement();
			Message message = objectMapper.readValue(jsonString, Message.class);
			request = "DELETE FROM message where date_alerte < " + message.getAlertDate() + ";";
			st.execute(request);
			logger.log(Level.INFO, "Messages succesfully deleted in BDD ");
			return true;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to delete Message datas in BDD " + e.getClass().getCanonicalName());
			return false;
		}
	}

	@Override
	public boolean update(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		String request;
		try {
			Statement st = con.createStatement();
			Message message = objectMapper.readValue(jsonString, Message.class);
			java.sql.Date sqlDate = new java.sql.Date(message.getAlertDate().getTime());
			request = "UPDATE message SET type_alerte = '" + message.getAlertState() + "', id_capteur = '"
					+ message.getIdSensor() + "', date_alerte = '" + sqlDate;
			st.execute(request);
			logger.log(Level.INFO, "Message succesfully update in BDD");
			return true;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to update message datas in BDD" + e.getClass().getCanonicalName());
			return false;
		}
	}

	@Override
	public String read(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		String request;
		try {
			Statement st = con.createStatement();
			Message message = objectMapper.readValue(jsonString, Message.class);
			request = "SELECT * FROM message where id_message='" + message.getIdMessage() + "';";
			result = st.executeQuery(request);
			result.next();

			message.setIdMessage(Integer.parseInt(result.getObject(1).toString()));
			if (result.getObject(2).toString().equals("NORMAL")) {
				message.setAlertState(AlertState.NORMAL);
			} else if (result.getObject(2).toString().equals("ALERT")) {
				message.setAlertState(AlertState.ALERT);
			} else if (result.getObject(2).toString().equals("DOWN")) {
				message.setAlertState(AlertState.DOWN);
			} else
				message.setAlertState(null);
			message.setIdSensor(Integer.parseInt(result.getObject(3).toString()));

			dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			alertDate = dateFormat.parse(result.getObject(4).toString());
			message.setAlertDate(alertDate);

			ObjectMapper obj = new ObjectMapper();
			jsonString = obj.writeValueAsString(message);
			logger.log(Level.INFO, "Message succesfully find in BDD");
			return jsonString;
		} catch (SQLException | IOException | ParseException e) {
			logger.log(Level.INFO, "Impossible to get message datas from BDD " + e.getClass().getCanonicalName());
		}
		jsonString = "ERROR";
		return jsonString;
	}

	@Override
	public String readAll(String jsonString) {
		String request;
		Message message2;
		ObjectMapper objectMapper = new ObjectMapper();
		List<Message> listMessage = new ArrayList<>();
		try {

			Statement st = con.createStatement();
			Message message = objectMapper.readValue(jsonString, Message.class);
			request = "SELECT * FROM message"; // where date_alerte >=" + message.getAlertDate() + ";";
			result = st.executeQuery(request);
			while (result.next()) {
				message2 = new Message();
				message2.setIdMessage(Integer.parseInt(result.getObject(1).toString()));
				if (result.getObject(2).toString().equals("NORMAL")) {
					message2.setAlertState(AlertState.NORMAL);
				} else if (result.getObject(2).toString().equals("ALERT")) {
					message2.setAlertState(AlertState.ALERT);
				} else if (result.getObject(2).toString().equals("DOWN")) {
					message2.setAlertState(AlertState.DOWN);
				}
				message2.setIdSensor(Integer.parseInt(result.getObject(3).toString()));

				dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				alertDate = dateFormat.parse(result.getObject(4).toString());
				message2.setAlertDate(alertDate);

				listMessage.add(message2);
			}
			jsonString = objectMapper.writeValueAsString(listMessage);
			logger.log(Level.INFO, "Message succesfully find in BDD");
			return jsonString;
		} catch (SQLException | IOException | ParseException e) {
			logger.log(Level.INFO, "Impossible to get messages datas from BDD " + e.getClass().getCanonicalName());
		}
		jsonString = "ERROR";
		return jsonString;
	}

}
