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
	private Date curDate;
	private Date beforeDate;
	private Date afterDate;
	private Calendar calBefore;
	private Calendar calAfter;
	private ObjectMapper objectMapper;
	private String jsonString;
	private int numberOfMessages;
	private int numberOfIteration;
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
		getAllMessages(currentDate);
		for (Message messages : listMessage) {
			if (messages.getAlertState() == AlertState.ALERT) {
				getSensor(messages.getIdSensor());
				if (sensor.getSensorState() == true) {
					sensor.setAlertState(AlertState.ALERT);
					updateSensorAlertState(sensor);
				}
			} else if (messages.getAlertState() == AlertState.DOWN) {
				getSensor(messages.getIdSensor());
				if (sensor.getSensorState() == true) {
					sensor.setAlertState(AlertState.DOWN);
					updateSensorAlertState(sensor);
				}
			}
		}
	}

	public void verifySensorMessageBeforeActivity() {
		curDate = new Date();
		calBefore = Calendar.getInstance();
		calAfter = Calendar.getInstance();
		calBefore.add(Calendar.MINUTE, 29);
		calAfter.add(Calendar.MINUTE, 31);
		beforeDate = calBefore.getTime();
		afterDate = calAfter.getTime();
		formater = new SimpleDateFormat("h:mm a");

		getAllSensor();
		getAllMessages(curDate);

		for (Sensor sensors : listSensor) {
			if (sensors.getSensorState() == true
					&& (formater.format(sensors.getStartActivity()).compareTo(formater.format(beforeDate)) >= 0)
					&& (formater.format(sensors.getStartActivity()).compareTo(formater.format(afterDate)) <= 0)) {
				numberOfMessages = 0;
				for (Message messages : listMessage) {
					if ((messages.getIdSensor() == sensors.getIdSensor())
							&& (formater.format(messages.getAlertDate()).compareTo(formater.format(afterDate)) <= 0)) {
						numberOfMessages++;
					}
				}
				if (numberOfMessages == 0) {
					sensors.setAlertState(AlertState.DOWN);
					updateSensorAlertState(sensors);
				}
			}
		}
	}

	// Fonctionne
	public void verifySensorActivity(Date currentDate) {
		for (int i = 0; i < listSensorDown.size(); i++)
			listSensorDown.remove(i);

		getAllSensor();
		getAllMessages(currentDate);
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
	public void getAllMessages(Date curDate) {
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
