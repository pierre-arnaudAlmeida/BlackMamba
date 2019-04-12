package com.blackmamba.deathkiss.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.blackmamba.deathkiss.entity.AlertState;
import com.blackmamba.deathkiss.entity.Message;
import com.blackmamba.deathkiss.entity.Sensor;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MonitoringAlert {

	private String requestType;
	private String table;
	private Message message;
	private Sensor sensor;
	private Date currentDate;
	private ObjectMapper objectMapper;
	private String jsonString;
	private List<Message> listMessage = new ArrayList<Message>();
	private static final Logger logger = LogManager.getLogger(MonitoringAlert.class);

	public MonitoringAlert() {
	}

	public void getMessages() {
		/**
		 * Declare the Object Messages
		 */
		currentDate = new Date();
		message.setAlertDate(currentDate);
		/**
		 * Find all the Messages in the data base and add on list to be treated
		 */
		requestType = "READ ALL";
		table = "Message";
		objectMapper = new ObjectMapper();
		try {
			jsonString = objectMapper.writeValueAsString(message);
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			Message[] messages = objectMapper.readValue(jsonString, Message[].class);
			listMessage = Arrays.asList(messages);
			logger.log(Level.INFO, "Find Messages datas succed");
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON Messages datas " + e1.getClass().getCanonicalName());
		}
		// AFFICHE les id des messages;
		for (Message mess : listMessage) {
			System.out.println(mess.getIdMessage());
		}
	}

	public void getSensor(int idSensor) {
		requestType = "READ";
		sensor = new Sensor();
		table = "Sensor";
		sensor.setIdSensor(idSensor);
		try {
			jsonString = objectMapper.writeValueAsString(sensor);
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			sensor = objectMapper.readValue(jsonString, Sensor.class);
			logger.log(Level.INFO, "Find Sensor data succed");
			// return sensor;
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON Sensor datas " + e1.getClass().getCanonicalName());
			// return null;
		}
	}

	public void alertTreatment() {
		getMessages();
		for (Message messages : listMessage) {
			if (messages.getAlertState() == AlertState.ALERT) {
				System.out.println("une alerte pour l'id : " + messages.getIdSensor());
				getSensor(messages.getIdSensor());
				if (sensor.getSensorState() == true) {
					System.out.println("et le capteur est allumer");
				}
			} else if (message.getAlertState() == AlertState.DOWN) {
				System.out.println("capteur en panne pour l'id : " + messages.getIdSensor());
				getSensor(messages.getIdSensor());
				if (sensor.getSensorState() == true) {
					System.out.println("et le capteur est allumer");
				}
			}
		}
	}
}
