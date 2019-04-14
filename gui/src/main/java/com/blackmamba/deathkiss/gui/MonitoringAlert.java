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
	private List<Sensor> listSensorDown = new ArrayList<Sensor>();
	private static final Logger logger = LogManager.getLogger(MonitoringAlert.class);

	public MonitoringAlert() {
	}

	// Fonctionne
	public void alertTreatment() {
		currentDate = new Date();
		getMessages(currentDate);
		for (Message messages : listMessage) {
			if (messages.getAlertState() == AlertState.ALERT) {
				System.out.println("une alerte pour l'id : " + messages.getIdSensor());
				getSensor(messages.getIdSensor());
				if (sensor.getSensorState() == true) {
					System.out.println("et le capteur est allumer");
				}
			} else if (messages.getAlertState() == AlertState.DOWN) {
				System.out.println("capteur en panne pour l'id : " + messages.getIdSensor());
				getSensor(messages.getIdSensor());
				if (sensor.getSensorState() == true) {
					System.out.println("et le capteur est allumer");
				}
			}
		}
	}

	// verification si le capteur est actif et a envoyer un message 30 min avant le
	// passage a la sensibilité HIGH
	public void verifySensorMessageBeforActivity(List<Message> listMessage) {
		getAllSensor();
		for (Sensor sensors : listSensor) {
			if (sensors.getSensorState() == true) {
				// si le capteur est alllumer
				// TODO
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

	// Fonctionne
	public void verifySensorActivity(Date currentDate) {
		int numberOfIteration;

		for (int i = 0; i < listSensorDown.size(); i++)
			listSensorDown.remove(i);

		getAllSensor();
		getMessages(currentDate);
		for (Sensor sensors : listSensor) {
			numberOfIteration = 0;
			if (sensors.getSensorState() == true) {
				for (Message messages : listMessage) {
					if (sensors.getIdSensor() == messages.getIdSensor()) {
						numberOfIteration++;
					}
				}
				if (numberOfIteration < 1) {
					listSensorDown.add(sensors);
				}
			}
		}
		if (!listSensorDown.isEmpty()) {
			for (Sensor sensors : listSensorDown) {
				sensors.setAlertState(AlertState.DOWN);
				updateSensorAlertState(sensors);
			}
		}
	}

	// Fonctionne
	public void updateSensorAlertState(Sensor sensor) {
		requestType = "UPDATE";
		table = "Sensor";
		objectMapper = new ObjectMapper();
		try {
			jsonString = objectMapper.writeValueAsString(sensor);
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			if (!jsonString.equals("UPDATED")) {
				logger.log(Level.INFO, "Impossible to update sensor");
			} else {
				logger.log(Level.INFO, "Update Succeded");
			}
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON sensor datas" + e1.getClass().getCanonicalName());
		}
	}

	// Fonctionne
	public void getMessages(Date curDate) {
		requestType = "READ ALL";
		table = "Message";
		message = new Message();
		message.setAlertDate(curDate);
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
	}

	// Fonctionne
	public void getSensor(int idSensor) {
		requestType = "READ";
		sensor = new Sensor();
		objectMapper = new ObjectMapper();
		table = "Sensor";
		sensor.setIdSensor(idSensor);
		try {
			jsonString = objectMapper.writeValueAsString(sensor);
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			logger.log(Level.INFO, jsonString);
			sensor = objectMapper.readValue(jsonString, Sensor.class);
			logger.log(Level.INFO, "Find Sensor data succed");
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON Sensor datas " + e1.getClass().getCanonicalName());
		}
	}

	// Fonctionne
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
}
