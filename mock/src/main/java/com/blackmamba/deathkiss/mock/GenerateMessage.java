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
	private String request;
	private String requestType;
	private String jsonString;
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
		while (bool) {
			if (request.equals("ALL")) {
				for (Sensor sensors : listSensor) {
					if (sensors.getSensorState() == true) {
						currentDate = new Date();
						message.setIdSensor(sensors.getIdSensor());
						message.setThreshold((sensors.getThresholdMax() - sensors.getThresholdMin()) / 2);
						message.setAlertDate(currentDate);
						sendMessage();
					}
				}
			} else if (request.equals("ONE")) {
				for (Sensor sensors : listSensor) {
					if (sensors.getIdSensor() == message.getIdSensor()) {
						currentDate = new Date();
						message.setThreshold(threshold);
						message.setAlertDate(currentDate);
					} else {
						currentDate = new Date();
						message.setIdSensor(sensors.getIdSensor());
						message.setThreshold((sensors.getThresholdMax() - sensors.getThresholdMin()) / 2);
						message.setAlertDate(currentDate);
					}
					sendMessage();
				}
			} else if (request.equals("TYPE")) {
				for (Sensor sensors : listSensor) {
					currentDate = new Date();
					message.setIdSensor(sensors.getIdSensor());
					message.setThreshold((sensors.getThresholdMax() - sensors.getThresholdMin()) / 2);
					message.setAlertDate(currentDate);
					sendMessage();
				}
			}
			// TODO voir combien de temps prend cette methode a s'executer
			nbMessageGenerate++;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				logger.log(Level.INFO, "Impossible to sleep the threadGenerateMessage" + e.getClass().getCanonicalName());
			}
		}
	}

	/**
	 * Send the message to server
	 */
	public void sendMessage() {
		requestType = "ALERT";
		objectMapper = new ObjectMapper();
		try {
			jsonString = objectMapper.writeValueAsString(message);
			System.out.println(jsonString);// TODO FUCK a supprimer
			new MockSocket(requestType, jsonString, null);
			logger.log(Level.INFO, "Message sent to server");
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
