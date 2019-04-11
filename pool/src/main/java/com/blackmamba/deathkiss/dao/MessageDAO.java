package com.blackmamba.deathkiss.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.blackmamba.deathkiss.entity.Employee;
import com.blackmamba.deathkiss.entity.Message;
import com.blackmamba.deathkiss.entity.Resident;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class MessageDAO extends DAO<Message> {

	private ResultSet result = null;
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
		//TODO date
		ObjectMapper objectMapper = new ObjectMapper();
		String request;
		try {
			Statement st = con.createStatement();
			Message message = objectMapper.readValue(jsonString, Message.class);
			request = "insert into message (type_alerte,id_capteur,date_alerte,parametre) values ('"
					+ message.getAlertState() + "','" + message.getIdSensor() + "','" + message.getAlertDate() + "','"
					+ message.getParameter() + "')";
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
			request = "DELETE FROM message where date_alerte = " + message.getAlertDate() + ";";// TODO mettre quand
																								// c'est inferieur a une
																								// certaine date
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
		//TODO date
		ObjectMapper objectMapper = new ObjectMapper();
		String request;
		try {
			Statement st = con.createStatement();
			Message message = objectMapper.readValue(jsonString, Message.class);
			request = "UPDATE message SET type_alerte = '" + message.getAlertState() + "', id_capteur = '"
					+ message.getIdSensor() + "', date_alerte = '" + message.getAlertDate() + "', parametre = '"
					+ message.getParameter();
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
		// TODO
		ObjectMapper objectMapper = new ObjectMapper();
		String request;
		try {
			Statement st = con.createStatement();
			Message message = objectMapper.readValue(jsonString, Message.class);
			request = "SELECT * FROM message where id_message='" + message.getIdMessage() + "';";
			result = st.executeQuery(request);
			result.next();

			message.setIdMessage(Integer.parseInt(result.getObject(1).toString()));
			message.setAlertState(result.getObject(2).toString());
			message.setIdSensor(Integer.parseInt(result.getObject(3).toString()));
			message.setAlertDate(result.getObject(4).toString());
			message.setParameter(result.getObject(5).toString());

			ObjectMapper obj = new ObjectMapper();
			jsonString = obj.writeValueAsString(message);
			logger.log(Level.INFO, "Message succesfully find in BDD");
			return jsonString;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to get message datas from BDD " + e.getClass().getCanonicalName());
		}
		jsonString = "ERROR";
		return jsonString;
	}

	@Override
	public String readAll(String jsonString) {
		// TODO
		String request;
		Message message;
		List<Message> listMessage = new ArrayList<>();
		try {

			Statement st = con.createStatement();
			request = "SELECT * FROM message";
			result = st.executeQuery(request);
			while (result.next()) {
				message = new Message();
				message.setIdMessage(Integer.parseInt(result.getObject(1).toString()));
				message.setAlertState(result.getObject(2).toString());
				message.setIdSensor(Integer.parseInt(result.getObject(3).toString()));
				message.setAlertDate(result.getObject(4).toString());
				message.setParameter(result.getObject(5).toString());
				listMessage.add(message);
			}
			ObjectMapper obj = new ObjectMapper();
			jsonString = obj.writeValueAsString(listMessage);
			logger.log(Level.INFO, "Message succesfully find in BDD");
			return jsonString;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to get messages datas from BDD " + e.getClass().getCanonicalName());
		}
		jsonString = "ERROR";
		return jsonString;
	}

}
