package com.blackmamba.deathkiss.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.blackmamba.deathkiss.mock.entity.Message;
import com.blackmamba.deathkiss.mock.entity.Sensor;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GenerateMessage extends Thread {

	private boolean bool;
	private Date currentDate;
	private int nbMessageGenerate;
	private int threshold;
	private Message message;
	private Message message2;
	private String request;
	private String requestType;
	private String jsonString;
	private String sensorType;
	private ObjectMapper objectMapper;
	private List<Sensor> listSensor = new ArrayList<Sensor>();
	private static final Logger logger = LogManager.getLogger(GenerateMessage.class);

	public GenerateMessage(Message message, List<Sensor> listSensor, String request) {
		this.message = message;
		this.listSensor = listSensor;
		this.request = request;
	}

	public void run() {
		bool = true;
		// while (bool) {TODO supprimer les commentaires
		if (request.equals("ALL")) {
			for (Sensor sensors : listSensor) {
				if (sensors.getSensorState() == true) {
					currentDate = new Date();
					message2 = new Message();
					message2.setIdSensor(sensors.getIdSensor());
					message2.setThreshold((sensors.getThresholdMax() - sensors.getThresholdMin()) / 2);
					message2.setAlertDate(currentDate);
					sendMessage(message2);
					nbMessageGenerate++;
				}
			}
		} else if (request.equals("ONE")) {
			for (Sensor sensors : listSensor) {
				message2 = new Message();
				if (sensors.getIdSensor() == message.getIdSensor()) {
					currentDate = new Date();
					message2.setThreshold(threshold);
					message2.setAlertDate(currentDate);
					message2.setIdSensor(message.getIdSensor());
					sendMessage(message2);
					nbMessageGenerate++;
				} else if (sensors.getSensorState() == true) {
					currentDate = new Date();
					message2.setThreshold((sensors.getThresholdMax() - sensors.getThresholdMin()) / 2);
					message2.setAlertDate(currentDate);
					message2.setIdSensor(sensors.getIdSensor());
					sendMessage(message2);
					nbMessageGenerate++;
				}

			}
		} else if (request.equals("TYPE")) {
			for (Sensor sensors : listSensor) {
				if (sensors.getIdSensor() == message.getIdSensor()) {
					sensorType = sensors.getTypeSensor().toString();
				}
				if (sensors.getSensorState() == true && sensorType.equals(sensors.getTypeSensor().toString())) {
					message2 = new Message();
					currentDate = new Date();
					message2.setIdSensor(sensors.getIdSensor());
					message2.setThreshold(threshold);
					message2.setAlertDate(currentDate);
					sendMessage(message2);
					nbMessageGenerate++;
				} else if (sensors.getSensorState() == true) {
					message2 = new Message();
					currentDate = new Date();
					message2.setIdSensor(sensors.getIdSensor());
					message2.setThreshold((sensors.getThresholdMax() - sensors.getThresholdMin()) / 2);
					message2.setAlertDate(currentDate);
					sendMessage(message2);
					nbMessageGenerate++;
				}
			}
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			logger.log(Level.INFO, "Impossible to sleep the threadGenerateMessage" + e.getClass().getCanonicalName());
		}
		// }
	}

	/**
	 * Send the message to server
	 */
	public void sendMessage(Message message) {
		requestType = "ALERT";
		objectMapper = new ObjectMapper();
		try {
			jsonString = objectMapper.writeValueAsString(message);
			new MockSocket(requestType, jsonString, null);
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON Message data " + e1.getClass().getCanonicalName());
		}
	}

	/**
	 * @return the nbMessageGenerate
	 */
	public int getNbMessageGenerate() {
		return nbMessageGenerate;
	}

	/**
	 * @param nbMessageGenerate the nbMessageGenerate to set
	 */
	public void setNbMessageGenerate(int nbMessageGenerate) {
		this.nbMessageGenerate = nbMessageGenerate;
	}

	/**
	 * @return the bool
	 */
	public boolean isBool() {
		return bool;
	}

	/**
	 * @param bool the bool to set
	 */
	public void setBool(boolean bool) {
		this.bool = bool;
	}

	/**
	 * @return the threshold
	 */
	public int getThreshold() {
		return threshold;
	}

	/**
	 * @param threshold the threshold to set
	 */
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	/**
	 * @return the message
	 */
	public Message getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(Message message) {
		this.message = message;
	}

	/**
	 * @return the request
	 */
	public String getRequest() {
		return request;
	}

	/**
	 * @param request the request to set
	 */
	public void setRequest(String request) {
		this.request = request;
	}
}
