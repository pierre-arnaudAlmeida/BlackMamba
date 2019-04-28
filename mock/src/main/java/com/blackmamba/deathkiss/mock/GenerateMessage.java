package com.blackmamba.deathkiss.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.blackmamba.deathkiss.mock.entity.Message;
import com.blackmamba.deathkiss.mock.entity.Sensor;
import com.blackmamba.deathkiss.mock.entity.SensorType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GenerateMessage extends Thread {

	/**
	 * Different parameters used
	 */
	private boolean bool;
	private Date currentDate;
	private int nbMessageGenerate;
	private int threshold;
	private Message message;
	private Message message2;
	private String request;
	private String requestType;
	private String jsonString;
	private SensorType sensorType;
	private ObjectMapper objectMapper;
	private List<Sensor> listSensor = new ArrayList<Sensor>();
	private static final Logger logger = LogManager.getLogger(GenerateMessage.class);

	/**
	 * Constructor
	 * 
	 * @param message    from the tabMockMessage
	 * @param listSensor from the tabMockMessage
	 * @param request    from the tabMockMessage
	 */
	public GenerateMessage(Message message, List<Sensor> listSensor, String request) {
		this.message = message;
		this.listSensor = listSensor;
		this.request = request;
	}

	/**
	 * generate messages for every sensors in listSensor and affect some different
	 * datas in function of the selection of user in the GUI
	 */
	public void run() {
		bool = true;
		// while (bool) { TODO PA supprimer les commentaires
		if (request.equals("ALL")) {
			/**
			 * Generate an message with basic parameters for every sensor
			 */
			for (Sensor sensors : listSensor) {
				if (sensors.getSensorState()) {
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
			/**
			 * Generate an message for every sensor with an id different of the idSensor
			 * input by user in GUI with basic parameter and for the idSensor they input the
			 * threshold value display on GUI
			 */
			for (Sensor sensors : listSensor) {
				message2 = new Message();
				if (sensors.getIdSensor() == message.getIdSensor()) {
					currentDate = new Date();
					message2.setThreshold(threshold);
					message2.setAlertDate(currentDate);
					message2.setIdSensor(message.getIdSensor());
					sendMessage(message2);
					nbMessageGenerate++;
				} else if (sensors.getSensorState()) {
					currentDate = new Date();
					message2.setThreshold((sensors.getThresholdMax() - sensors.getThresholdMin()) / 2);
					message2.setAlertDate(currentDate);
					message2.setIdSensor(sensors.getIdSensor());
					sendMessage(message2);
					nbMessageGenerate++;
				}

			}
		} else if (request.equals("TYPE")) {
			/**
			 * Generate an message for every sensor and if the type of sensor if the same of
			 * the sensorType selected by user in the GUI they affect the threshold input
			 */
			for (Sensor sensors : listSensor) {
				if (sensors.getIdSensor() == message.getIdSensor()) {
					sensorType = sensors.getTypeSensor();
				}
				if (sensors.getSensorState() && sensorType.equals(sensors.getTypeSensor())) {
					message2 = new Message();
					currentDate = new Date();
					message2.setIdSensor(sensors.getIdSensor());
					message2.setThreshold(threshold);
					message2.setAlertDate(currentDate);
					sendMessage(message2);
					nbMessageGenerate++;
				} else if (sensors.getSensorState()) {
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
			logger.log(Level.INFO, "Impossible to sleep the threadGenerateMessage " + e.getClass().getCanonicalName());
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