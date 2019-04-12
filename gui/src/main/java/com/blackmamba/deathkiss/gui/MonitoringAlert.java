package com.blackmamba.deathkiss.gui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
	private SimpleDateFormat formater;
	private List<Message> listMessage = new ArrayList<Message>();
	private List<Sensor> listSensor = new ArrayList<Sensor>();
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

	public void getAllSensor() {
		requestType = "READ ALL";
		table = "Sensor";
		objectMapper = new ObjectMapper();
		try {
			jsonString = "READ ALL";
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			Sensor[] sensors = objectMapper.readValue(jsonString, Sensor[].class);
			listSensor = Arrays.asList(sensors);
			logger.log(Level.INFO, "Find Sensor datas succed");
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON Sensor data " + e1.getClass().getCanonicalName());
		}
	}

	// verification si le capteur est actif et a envoyer un message 30 min avant le
	// passage a la sensibilité HIGH
	public void verifySensorMessageBeforActivity(List<Message> listMessage) {
		getAllSensor();
		for (Sensor sensors : listSensor) {
			if (sensors.getSensorState() == true) {
				// si le capteur est alllumer

				// si la date acutelle+30 == la date de début du capteur
				// alors on verifie dans la list des messages si le capteur (avec idsensor) si
				// il ya eu un message normal alert ou down avec la date actuelle a la minute
				// si oui on fait rien sinon on envoi une pop-up down
			}
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, 30);
			Date currentDate = cal.getTime();
			formater = new SimpleDateFormat("h:mm a");
			if (formater.format(sensors.getStartActivity()) == formater.format(currentDate)) {

			}
		}
		// TODO on cherche tout les capteurs et pour chaque capteur on va verifier
		// l'heure a laquelle il change de sensibilité et on va vérifier si dans les
		// message pour l'idSensor on trouve un message NORMAL pour prévenir de sont
		// activité
		// sinon on envoi un message vers l'ihm en le catégorisant comme DOWN
	}

	// TODO verifier si le capteur a envoyer un message toute les heures
	// faire un thread dans Frame qui se met en pause toute les heures
	// et qui appele la methode
	public void verifySensorActivity() {
	}
}
