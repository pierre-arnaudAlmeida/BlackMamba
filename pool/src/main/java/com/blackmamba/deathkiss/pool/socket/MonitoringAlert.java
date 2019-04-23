package com.blackmamba.deathkiss.pool.socket;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.blackmamba.deathkiss.pool.connectionpool.DataSource;
import com.blackmamba.deathkiss.pool.connectionpool.JDBCConnectionPool;
import com.blackmamba.deathkiss.pool.dao.DAO;
import com.blackmamba.deathkiss.pool.dao.SensorDAO;
import com.blackmamba.deathkiss.pool.dao.SensorHistoricalDAO;
import com.blackmamba.deathkiss.pool.entity.Alert;
import com.blackmamba.deathkiss.pool.entity.AlertState;
import com.blackmamba.deathkiss.pool.entity.Message;
import com.blackmamba.deathkiss.pool.entity.Sensitivity;
import com.blackmamba.deathkiss.pool.entity.Sensor;
import com.blackmamba.deathkiss.pool.entity.SensorHistorical;
import com.blackmamba.deathkiss.pool.entity.SensorType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class MonitoringAlert {

	private String requestType;
	private String jsonString;
	private Alert alert;
	private Sensor sensor;
	private SensorHistorical sensorHistorical;
	private Date curDate;
	private Date beforeDate;
	private Date afterDate;
	private Date firstAlertDate;
	private Date lastAlertDate;
	private Calendar calBefore;
	private Calendar calAfter;
	private ObjectMapper objectMapper;
	private int numberOfMessages;
	private int numberOfIteration;
	private int nbAlert;
	private long difference;
	private int sensitivity;
	private SimpleDateFormat formater;
	private JDBCConnectionPool pool;
	private Connection connectionGived;
	private boolean result;
	private List<Alert> listAlert = new ArrayList<Alert>();
	private List<Message> listMessage = new ArrayList<Message>();
	private List<Sensor> listSensor = new ArrayList<Sensor>();
	private List<Sensor> listSensorDown = new ArrayList<Sensor>();
	private static final Logger logger = LogManager.getLogger(MonitoringAlert.class);
	private ResourceBundle rsAlert = ResourceBundle.getBundle("alert");
	private ResourceBundle rsSensorDefaultParameters = ResourceBundle.getBundle("sensor_default_parameters");

	public MonitoringAlert(JDBCConnectionPool pool) {
		this.pool = pool;
	}

	public MonitoringAlert() {
	}

	// TODO IA pour les alertes
	/**
	 * Verify if the messages send by the different sensor can be considerate to an
	 * alert verify if the threshold have been reached and if they did'nt have an
	 * threshold they use the default threshold of the sensor type then if they
	 * considerate the messages to an alert they add an alert on alertList to be
	 * send at Client
	 */
	public void alertTreatment() {
		getAllSensor();
		for (Sensor sensors : listSensor) {
			nbAlert = 0;
			for (Message messages : listMessage) {
				if (sensors.getIdSensor() == messages.getIdSensor() && sensors.getSensorState() == true) {
					if (!(sensors.getThresholdMin() == 0 && sensors.getThresholdMax() == 0)) {
						if ((sensors.getThresholdMin() >= messages.getThreshold())
								|| sensors.getThresholdMax() <= messages.getThreshold()) {
							if (nbAlert == 0) {
								firstAlertDate = messages.getAlertDate();
							}
							lastAlertDate = messages.getAlertDate();
							nbAlert++;
							logger.log(Level.INFO, "Sensor : " + sensors.getIdSensor() + " on commonArea : "
									+ sensors.getIdCommonArea() + "threshold reached, threshold minimum : "
									+ sensors.getThresholdMin() + " sensor threshold : " + messages.getThreshold()
									+ "threshold maximum : " + sensors.getThresholdMax());
						} else {
							listMessage.remove(messages);
						}
					} else {
						if (sensors.getTypeSensor().equals(SensorType.SMOKE)) {
							sensors.setThresholdMin(
									Integer.parseInt(rsSensorDefaultParameters.getString("smokeThresholdMin")));
							sensors.setThresholdMax(
									Integer.parseInt(rsSensorDefaultParameters.getString("smokeThresholdMax")));
						} else if (sensors.getTypeSensor().equals(SensorType.MOVE)) {
							sensors.setThresholdMin(
									Integer.parseInt(rsSensorDefaultParameters.getString("moveThresholdMin")));
							sensors.setThresholdMax(
									Integer.parseInt(rsSensorDefaultParameters.getString("moveThresholdMax")));
						} else if (sensors.getTypeSensor().equals(SensorType.TEMPERATURE)) {
							sensors.setThresholdMin(
									Integer.parseInt(rsSensorDefaultParameters.getString("temperatureThresholdMin")));
							sensors.setThresholdMax(
									Integer.parseInt(rsSensorDefaultParameters.getString("temperatureThresholdMax")));
						} else if (sensors.getTypeSensor().equals(SensorType.WINDOW)) {
							sensors.setThresholdMin(
									Integer.parseInt(rsSensorDefaultParameters.getString("windowThresholdMin")));
							sensors.setThresholdMax(
									Integer.parseInt(rsSensorDefaultParameters.getString("windowThresholdMax")));
						} else if (sensors.getTypeSensor().equals(SensorType.DOOR)) {
							sensors.setThresholdMin(
									Integer.parseInt(rsSensorDefaultParameters.getString("doorThresholdMin")));
							sensors.setThresholdMax(
									Integer.parseInt(rsSensorDefaultParameters.getString("doorThresholdMax")));
						} else if (sensors.getTypeSensor().equals(SensorType.ELEVATOR)) {
							sensors.setThresholdMin(
									Integer.parseInt(rsSensorDefaultParameters.getString("elevatorThresholdMin")));
							sensors.setThresholdMax(
									Integer.parseInt(rsSensorDefaultParameters.getString("elevatorThresholdMax")));
						} else if (sensors.getTypeSensor().equals(SensorType.LIGHT)) {
							sensors.setThresholdMin(
									Integer.parseInt(rsSensorDefaultParameters.getString("lightThresholdMin")));
							sensors.setThresholdMax(
									Integer.parseInt(rsSensorDefaultParameters.getString("lightThresholdMax")));
						} else if (sensors.getTypeSensor().equals(SensorType.FIRE)) {
							sensors.setThresholdMin(
									Integer.parseInt(rsSensorDefaultParameters.getString("fireThresholdMin")));
							sensors.setThresholdMax(
									Integer.parseInt(rsSensorDefaultParameters.getString("fireThresholdMax")));
						} else if (sensors.getTypeSensor().equals(SensorType.BADGE)) {
							sensors.setThresholdMin(
									Integer.parseInt(rsSensorDefaultParameters.getString("badgeThresholdMin")));
							sensors.setThresholdMax(
									Integer.parseInt(rsSensorDefaultParameters.getString("badgeThresholdMax")));
						} else if (sensors.getTypeSensor().equals(SensorType.ROUTER)) {
							sensors.setThresholdMin(
									Integer.parseInt(rsSensorDefaultParameters.getString("routerThresholdMin")));
							sensors.setThresholdMax(
									Integer.parseInt(rsSensorDefaultParameters.getString("routerThresholdMax")));
						}
						if ((sensors.getThresholdMin() >= messages.getThreshold())
								|| sensors.getThresholdMax() <= messages.getThreshold()) {
							if (nbAlert == 0) {
								firstAlertDate = messages.getAlertDate();
							}
							lastAlertDate = messages.getAlertDate();
							nbAlert++;
							logger.log(Level.INFO, "Sensor : " + sensors.getIdSensor() + " on commonArea : "
									+ sensors.getIdCommonArea() + "threshold reached, threshold minimum : "
									+ sensors.getThresholdMin() + " sensor threshold : " + messages.getThreshold()
									+ "threshold maximum : " + sensors.getThresholdMax());
						} else {
							listMessage.remove(messages);
						}
					}
				}
			}
			difference = firstAlertDate.getTime() - lastAlertDate.getTime();
			if (sensors.getSensitivity().equals(Sensitivity.LOW)) {
				sensitivity = Integer.parseInt(rsAlert.getString("nbOfAlertMessageLow"));
			} else if (sensors.getSensitivity().equals(Sensitivity.MEDIUM)) {
				sensitivity = Integer.parseInt(rsAlert.getString("nbOfAlertMessageMedium"));
			} else if (sensors.getSensitivity().equals(Sensitivity.HIGH)) {
				sensitivity = Integer.parseInt(rsAlert.getString("nbOfAlertMessageHigh"));
			}
			// TODO manque l'heure a laquelle ca c'est déclancher
			if (nbAlert >= sensitivity && difference <= Integer.parseInt(rsAlert.getString("timeBetweenAlert"))) {
				alert = new Alert();
				alert.setAlertDate(lastAlertDate);
				alert.setAlertState(AlertState.ALERT);
				alert.setIdAlert(0);
				alert.setIdSensor(sensors.getIdSensor());
				listAlert.add(alert);

				for (Message messages : listMessage) {
					if (messages.getIdSensor() == alert.getIdSensor()) {
						listMessage.remove(messages);
					}
				}

				logger.log(Level.INFO, "ALERT DETECTED!!");
				logger.log(Level.INFO,
						"Sensor : " + sensors.getIdSensor() + " on ALERT on commonArea : " + sensors.getIdCommonArea());
				sensors.setAlertState(AlertState.ALERT);
				updateSensorAlertState(sensors);
				addHistorical(sensors);
			}
		}
		for (Message messages : listMessage) {
			difference = System.currentTimeMillis() - messages.getAlertDate().getTime();
			if (difference > Integer.parseInt(rsAlert.getString("timeBetweenAlert"))) {
				listMessage.remove(messages);
			}
		}
	}

	/**
	 * Check every 30 minutes if the sensor have send a message to the system, and
	 * if they did'nt send it's because he is breakdown
	 */
	public void verifySensorMessageBeforeActivity() {
		curDate = new Date();
		calBefore = Calendar.getInstance();
		calAfter = Calendar.getInstance();
		calBefore.add(Calendar.MINUTE, Integer.parseInt(rsAlert.getString("minute_before")));
		calAfter.add(Calendar.MINUTE, Integer.parseInt(rsAlert.getString("minute_after")));
		beforeDate = calBefore.getTime();
		afterDate = calAfter.getTime();
		formater = new SimpleDateFormat("h:mm a");

		getAllSensor();
		for (Sensor sensors : listSensor) {
			if (sensors.getIdCommonArea() != 0 && sensors.getSensorState() == true
					&& (formater.format(sensors.getStartActivity()).compareTo(formater.format(beforeDate)) >= 0)
					&& (formater.format(sensors.getStartActivity()).compareTo(formater.format(afterDate)) <= 0)) {
				numberOfMessages = 0;
				for (Message alerts : listMessage) {
					if ((alerts.getIdSensor() == sensors.getIdSensor())
							&& (formater.format(alerts.getAlertDate()).compareTo(formater.format(afterDate)) <= 0)) {
						numberOfMessages++;
					}
				}
				if (numberOfMessages == 0) {
					alert = new Alert();
					alert.setAlertDate(curDate);
					alert.setAlertState(AlertState.DOWN);
					alert.setIdAlert(0);
					alert.setIdSensor(sensors.getIdSensor());
					listAlert.add(alert);
					logger.log(Level.INFO, "No response from sensor before activity !!");
					logger.log(Level.INFO,
							"Sensor : " + sensors.getIdSensor() + " DOWN on commonArea : " + sensors.getIdCommonArea());
					sensors.setAlertState(AlertState.DOWN);
					updateSensorAlertState(sensors);
					addHistorical(sensors);
				}
			}
		}
	}

	/**
	 * Create a thread and loop every 30 minutes to verify if the sensor is active
	 * and send messages
	 */
	public void verifySensorActivity(Date currentDate) {
		/**
		 * For every sensor on Data base they verify if the sensor is active and if the
		 * sensor is active they verify if an alert/message send by sensor contain all
		 * the sensor active if an sensor does'nt have send an alert/message to server
		 * they will be considerate to breakdown and we update is state on data base
		 */
		cleanListSensor(listSensorDown);

		getAllSensor();
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
			if (listSensorDown.size() == listSensor.size()) {
				alert = new Alert();
				alert.setAlertDate(currentDate);
				alert.setAlertState(AlertState.OVER);
				alert.setIdAlert(0);
				alert.setIdSensor(0);
				listAlert.add(alert);
				logger.log(Level.INFO, "No response from all sensors !!");
				sensor = new Sensor();
				sensor.setAlertState(AlertState.OVER);
				sensor.setIdSensor(0);
				sensor.setSensorState(true);
				addHistorical(sensor);
			} else {
				// TODO remplir la list d'alerte a envoyer au client avec des valeurs spéciale
				// pour détecter rapidement la grosse panne
				// faire un tri comptage sur le tableau de listSensor
				// et sur celui de listSensorDown
				// si on a un certain pourcentage de capteurs down on peut dire que c'est le
				// zbeul dans la maison de retraite
				for (Sensor sensors : listSensorDown) {
					alert = new Alert();
					alert.setAlertDate(currentDate);
					alert.setAlertState(AlertState.DOWN);
					alert.setIdAlert(0);
					alert.setIdSensor(sensors.getIdSensor());
					listAlert.add(alert);
					logger.log(Level.INFO, "No response from sensor !!");
					logger.log(Level.INFO,
							"Sensor : " + sensors.getIdSensor() + " DOWN on commonArea : " + sensors.getIdCommonArea());
					sensors.setAlertState(AlertState.DOWN);
					updateSensorAlertState(sensors);
					addHistorical(sensors);
				}
			}
		}
		try {
			Thread.sleep(Integer.parseInt(rsAlert.getString("time_verifySensorActivity")));
		} catch (InterruptedException e) {
			logger.log(Level.INFO, "Impossible to sleep the thread" + e.getClass().getCanonicalName());
		}
	}

	/**
	 * Update the sensor Alert State on Data base
	 * 
	 * @param sensor to be updated
	 */
	public void updateSensorAlertState(Sensor sensor) {
		objectMapper = new ObjectMapper();
		try {
			jsonString = objectMapper.writeValueAsString(sensor);
			connectionGived = DataSource.getConnectionFromJDBC(pool);
			DAO<Sensor> sensorDao = new SensorDAO(connectionGived);
			setResult(((SensorDAO) sensorDao).update(jsonString));
			if (result == true)
				logger.log(Level.INFO, "Update Succeded");
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON sensor datas" + e1.getClass().getCanonicalName());
		}
	}

	/**
	 * Get informations about a specific sensor with the idSensor
	 * 
	 * @param idSensor to be find
	 */
	public void getSensor(int idSensor) {
		sensor = new Sensor();
		objectMapper = new ObjectMapper();
		sensor.setIdSensor(idSensor);
		try {
			jsonString = objectMapper.writeValueAsString(sensor);
			DAO<Sensor> sensorDao = new SensorDAO(connectionGived);
			jsonString = ((SensorDAO) sensorDao).read(jsonString);
			sensor = objectMapper.readValue(jsonString, Sensor.class);
			logger.log(Level.INFO, "Find Sensor data succed");
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON Sensor datas " + e1.getClass().getCanonicalName());
		}
	}

	/**
	 * Get all sensor from the table Sensor and add on listSensor
	 */
	public void getAllSensor() {
		requestType = "READ ALL";
		try {
			connectionGived = DataSource.getConnectionFromJDBC(pool);
			DAO<Sensor> sensorDao = new SensorDAO(connectionGived);
			jsonString = ((SensorDAO) sensorDao).readAll(requestType);
			DataSource.returnConnection(pool, connectionGived);
			Sensor[] sensors = objectMapper.readValue(jsonString, Sensor[].class);
			listSensor = Arrays.asList(sensors);
			logger.log(Level.INFO, "Find Sensor datas succed");
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to get datas " + e.getClass().getCanonicalName());
		}

	}

	/**
	 * Add on table Sensor Historical the sensor and his state and date
	 * 
	 * @param sensor
	 */
	public void addHistorical(Sensor sensor) {
		curDate = new Date();
		sensorHistorical = new SensorHistorical();
		sensorHistorical.setIdSensor(sensor.getIdSensor());
		sensorHistorical.setAlertState(sensor.getAlertState());
		sensorHistorical.setSensorState(sensor.getSensorState());
		sensorHistorical.setDate(curDate);
		objectMapper = new ObjectMapper();
		try {
			jsonString = objectMapper.writeValueAsString(sensorHistorical);
			connectionGived = DataSource.getConnectionFromJDBC(pool);
			DAO<SensorHistorical> sensorHistoricalDao = new SensorHistoricalDAO(connectionGived);
			setResult(((SensorHistoricalDAO) sensorHistoricalDao).create(jsonString));
			DataSource.returnConnection(pool, connectionGived);
			if (result == true)
				logger.log(Level.INFO, "Insertion SensorHistorical datas succed");
		} catch (SQLException | JsonProcessingException e) {
			logger.log(Level.INFO, "Impossible to insert datas " + e.getClass().getCanonicalName());
		}
	}

	public void cleanListMessage(List<Message> list) {
		list.removeAll(list);
	}

	public void cleanListAlert(List<Alert> list) {
		list.removeAll(list);
	}

	public void cleanListSensor(List<Sensor> list) {
		list.removeAll(list);
	}

	public void addListMessage(Message message, List<Message> list) {
		list.add(message);
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public List<Alert> getListAlert() {
		return listAlert;
	}

	public void setListAlert(List<Alert> listAlert) {
		this.listAlert = listAlert;
	}

	public List<Message> getListMessage() {
		return listMessage;
	}

	public void setListMessage(List<Message> listMessage) {
		this.listMessage = listMessage;
	}
}