package com.blackmamba.deathkiss.pool.socket;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
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
import com.blackmamba.deathkiss.pool.dao.CommonAreaDAO;
import com.blackmamba.deathkiss.pool.dao.DAO;
import com.blackmamba.deathkiss.pool.dao.MessageDAO;
import com.blackmamba.deathkiss.pool.dao.SensorDAO;
import com.blackmamba.deathkiss.pool.dao.SensorHistoricalDAO;
import com.blackmamba.deathkiss.pool.entity.Alert;
import com.blackmamba.deathkiss.pool.entity.AlertState;
import com.blackmamba.deathkiss.pool.entity.CommonArea;
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

	/**
	 * Different parameters used
	 */
	private Alert alert;
	private Sensor sensor;
	private SensorHistorical sensorHistorical;
	private Message message;
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
	private int lastThreshold;
	private int nbAlert;
	private int sensitivity;
	private int nbSensor;
	private int nbSensorDown;
	private int nbOfTimeWithoutMessage = 0;
	private long difference;
	private boolean result;
	private String requestType;
	private String jsonString;
	private SimpleDateFormat formater;
	private JDBCConnectionPool pool;
	private Connection connectionGived;
	private List<Alert> listAlert = new ArrayList<Alert>();
	private List<Message> listMessage = new ArrayList<Message>();
	private List<CommonArea> listCommonArea = new ArrayList<CommonArea>();
	private List<Message> listMessageInTreatment = new ArrayList<Message>();
	private List<Message> listMessageTreated = new ArrayList<Message>();
	private List<Sensor> listSensor = new ArrayList<Sensor>();
	private List<Sensor> listSensorDown = new ArrayList<Sensor>();
	private static final Logger logger = LogManager.getLogger(MonitoringAlert.class);
	private ResourceBundle rsAlert = ResourceBundle.getBundle("alert");
	private ResourceBundle rsSensorDefaultParameters = ResourceBundle.getBundle("sensor_default_parameters");

	/**
	 * Constructor
	 * 
	 * @param pool
	 */
	public MonitoringAlert(JDBCConnectionPool pool) {
		this.pool = pool;
	}

	/**
	 * Constructor
	 */
	public MonitoringAlert() {
	}

	/**
	 * Verify if the messages send by the different sensor can be considerate to an
	 * alert verify if the threshold have been reached and if they did'nt have an
	 * threshold they use the default threshold of the sensor type then if they
	 * considerate the messages to an alert they add an alert on alertList to be
	 * send at Client
	 */
	// TODO PA a verifier si c'est un capteur badge ou fenetre ou porte ou autres en
	// 0-1 dans la nuit il faut qu'il se déclanche
	public void alertTreatment() {
		getAllSensor();
		curDate = new Date();
		verifySensorActivity(curDate);
		verifySensorMessageBeforeActivity();
		if (!listMessage.isEmpty()) {
			for (Sensor sensors : listSensor) {
				for (Message messages : listMessage) {
					if (sensors.getSensorState() && sensors.getIdSensor() == messages.getIdSensor()) {
						listMessageInTreatment.add(messages);
					}
				}
				listMessage.removeAll(listMessageInTreatment);
				nbAlert = 0;
				if (sensors.getThresholdMin() == 0 && sensors.getThresholdMax() == 0) {
					for (SensorType type : SensorType.values()) {
						if (type.equals(sensors.getTypeSensor())) {
							sensors.setThresholdMin(Integer.parseInt(
									rsSensorDefaultParameters.getString(type.name().toLowerCase() + "ThresholdMin")));
							sensors.setThresholdMax(Integer.parseInt(
									rsSensorDefaultParameters.getString(type.name().toLowerCase() + "ThresholdMax")));
						}
					}
					detectAlert(sensors);
				} else {
					detectAlert(sensors);
				}
				if (nbAlert != 0) {
					difference = firstAlertDate.getTime() - lastAlertDate.getTime();
					for (Sensitivity type : Sensitivity.values()) {
						if (type.equals(sensors.getSensitivity())) {
							sensitivity = Integer
									.parseInt(rsAlert.getString(type.name().toLowerCase() + "NbOfAlertMessage"));
						}
					}
				} else {
					sensitivity = Integer.parseInt(rsAlert.getString("lowNbOfAlertMessage"));
				}
				if (sensitivity != Integer.parseInt(rsAlert.getString("lowNbOfAlertMessage"))) {
					Time lastTime = new java.sql.Time(lastAlertDate.getTime());
					String str = lastTime.toString();
					Time timeAlert = Time.valueOf(str);
					if ((timeAlert.after(sensors.getEndActivity()) && timeAlert.before(sensors.getStartActivity()))) {
						sensitivity = Integer.parseInt(rsAlert.getString("lowNbOfAlertMessage"));
					} else {
						if ((sensors.getEndActivity().getTime() - sensors.getStartActivity().getTime()) >= 0) {
							if (timeAlert.after(sensors.getEndActivity())) {
								sensitivity = Integer.parseInt(rsAlert.getString("lowNbOfAlertMessage"));
							} else if (timeAlert.before(sensors.getStartActivity())) {
								sensitivity = Integer.parseInt(rsAlert.getString("lowNbOfAlertMessage"));
							}
						}
					}
				}
				if (nbAlert >= sensitivity && nbAlert > 0
						&& difference <= Integer.parseInt(rsAlert.getString("timeBetweenAlert"))) {
					alert = new Alert();
					alert.setAlertDate(lastAlertDate);
					alert.setAlertState(AlertState.ALERT);
					alert.setIdAlert(0);
					alert.setIdSensor(sensors.getIdSensor());
					listAlert.add(alert);
					logger.log(Level.INFO, "*********************************************************************");
					logger.log(Level.INFO, "ALERT DETECTED!!");
					logger.log(Level.INFO, "Sensor : " + sensors.getIdSensor() + " on ALERT on commonArea : "
							+ sensors.getIdCommonArea());
					logger.log(Level.INFO, "Threshold reached : " + lastThreshold + ", Min : "
							+ sensors.getThresholdMin() + " Max : " + sensors.getThresholdMax());
					logger.log(Level.INFO, "*********************************************************************");
					sensors.setAlertState(AlertState.ALERT);
					updateSensorAlertState(sensors);
					addHistorical(sensors);
					addMessage(lastThreshold, sensors);
					for (Message messages : listMessageInTreatment) {
						if (messages.getIdSensor() == sensors.getIdSensor()) {
							listMessageTreated.add(messages);
						}
					}
					listMessageInTreatment.removeAll(listMessageTreated);
					listMessageTreated.clear();
				}
			}
			listMessageInTreatment.removeAll(listMessageTreated);
			listMessageTreated.clear();
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

		listMessageInTreatment = listMessage;
		if (!listMessageInTreatment.isEmpty()) {
			for (Sensor sensors : listSensor) {
				if (sensors.getIdCommonArea() != 0 && sensors.getSensorState()
						&& (formater.format(sensors.getStartActivity()).compareTo(formater.format(beforeDate)) >= 0)
						&& (formater.format(sensors.getStartActivity()).compareTo(formater.format(afterDate)) <= 0)) {
					numberOfMessages = 0;
					for (Message alerts : listMessageInTreatment) {
						if ((alerts.getIdSensor() == sensors.getIdSensor()) && (formater.format(alerts.getAlertDate())
								.compareTo(formater.format(afterDate)) <= 0)) {
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
						logger.log(Level.INFO, "*********************************************************************");
						logger.log(Level.INFO, "No Activity Sensor : " + sensors.getIdSensor()
								+ " DOWN on commonArea : " + sensors.getIdCommonArea());
						logger.log(Level.INFO, "*********************************************************************");
						sensors.setAlertState(AlertState.DOWN);
						updateSensorAlertState(sensors);
						addHistorical(sensors);
						addMessage(0, sensors);
					}
				}
			}
		}
		listMessageInTreatment.clear();

	}

	/**
	 * Create a thread and loop to verify if the sensor is active and send messages
	 */
	public void verifySensorActivity(Date currentDate) {
		/**
		 * For every sensor on Data base they verify if the sensor is active and if the
		 * sensor is active they verify if an alert/message send by sensor contain all
		 * the sensor active if an sensor does'nt have send an alert/message to server
		 * they will be considerate to breakdown and we update is state on data base
		 */
		cleanListSensor(listSensorDown);
		listMessageInTreatment = listMessage;
		if (!listMessageInTreatment.isEmpty()) {
			nbOfTimeWithoutMessage = 0;
			for (Sensor sensors : listSensor) {
				numberOfIteration = 0;
				if (sensors.getSensorState()) {
					for (Message messages : listMessageInTreatment) {
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
					logger.log(Level.INFO, "*********************************************************************");
					logger.log(Level.INFO, "No Activity from all sensors !!");
					logger.log(Level.INFO, "*********************************************************************");
					sensor = new Sensor();
					sensor.setAlertState(AlertState.OVER);
					sensor.setIdSensor(0);
					sensor.setSensorState(true);
					addHistorical(sensor);
					addMessage(0, sensor);
				} else {
					for (Sensor sensors : listSensorDown) {
						alert = new Alert();
						alert.setAlertDate(currentDate);
						alert.setAlertState(AlertState.DOWN);
						alert.setIdAlert(0);
						alert.setIdSensor(sensors.getIdSensor());
						listAlert.add(alert);
						logger.log(Level.INFO, "*********************************************************************");
						logger.log(Level.INFO, "No Activity Sensor : " + sensors.getIdSensor()
								+ " DOWN on commonArea : " + sensors.getIdCommonArea());
						logger.log(Level.INFO, "*********************************************************************");
						sensors.setAlertState(AlertState.DOWN);
						updateSensorAlertState(sensors);
						addHistorical(sensors);
						addMessage(0, sensors);
					}

					getAllCommonArea();
					for (CommonArea commonArea : listCommonArea) {
						for (Sensor sensors : listSensor) {
							if (sensors.getIdCommonArea() == commonArea.getIdCommonArea() && sensors.getSensorState()) {
								nbSensor++;
							}
						}
						for (Sensor sensors : listSensorDown) {
							if (sensors.getIdCommonArea() == commonArea.getIdCommonArea()) {
								nbSensorDown++;
							}
						}
						nbSensor = (nbSensorDown * 100) / nbSensor;
						if (nbSensor >= Integer.parseInt(rsAlert.getString("percentageOfSensorDown"))) {
							alert = new Alert();
							alert.setAlertDate(currentDate);
							alert.setAlertState(AlertState.OVER);
							alert.setIdAlert(0);
							alert.setIdSensor(commonArea.getIdCommonArea());
							listAlert.add(alert);
							logger.log(Level.INFO,
									"*********************************************************************");
							logger.log(Level.INFO, "No Activity on commonArea : " + commonArea.getIdCommonArea());
							logger.log(Level.INFO,
									"*********************************************************************");
							sensor = new Sensor();
							sensor.setIdSensor(commonArea.getIdCommonArea());
							addMessage(9999, sensor);
						}
					}
				}
			}
		} else {
			if (nbOfTimeWithoutMessage >= Integer.parseInt(rsAlert.getString("nbOfTimeWithoutMessage"))) {
				alert = new Alert();
				alert.setAlertDate(currentDate);
				alert.setAlertState(AlertState.OVER);
				alert.setIdAlert(0);
				alert.setIdSensor(0);
				listAlert.add(alert);
				logger.log(Level.INFO, "*********************************************************************");
				logger.log(Level.INFO, "No Activity from all sensors !!");
				logger.log(Level.INFO, "*********************************************************************");
				nbOfTimeWithoutMessage = 0;
				sensor = new Sensor();
				sensor.setIdSensor(0);
				addMessage(0, sensor);
			} else {
				nbOfTimeWithoutMessage++;
				if (nbOfTimeWithoutMessage % 5 == 0) {
					logger.log(Level.INFO, nbOfTimeWithoutMessage + " Secondes without messages");
				}
			}
		}
		listMessageInTreatment.clear();
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
			if (result)
				logger.log(Level.DEBUG, "Update Succeded");
		} catch (Exception e1) {
			logger.log(Level.WARN, "Impossible to parse in JSON sensor datas" + e1.getClass().getCanonicalName());
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
			logger.log(Level.DEBUG, "Find Sensor data succed");
		} catch (Exception e1) {
			logger.log(Level.WARN, "Impossible to parse in JSON Sensor datas " + e1.getClass().getCanonicalName());
		}
	}

	/**
	 * Get all sensor from the table Sensor and add on listSensor
	 */
	public void getAllSensor() {
		requestType = "READ ALL";
		try {
			objectMapper = new ObjectMapper();
			connectionGived = DataSource.getConnectionFromJDBC(pool);
			DAO<Sensor> sensorDao = new SensorDAO(connectionGived);
			jsonString = ((SensorDAO) sensorDao).readAll(requestType);
			DataSource.returnConnection(pool, connectionGived);
			Sensor[] sensors = objectMapper.readValue(jsonString, Sensor[].class);
			listSensor = Arrays.asList(sensors);
			logger.log(Level.DEBUG, "Find Sensor datas succed");
		} catch (SQLException | IOException e) {
			logger.log(Level.WARN, "Impossible to get datas " + e.getClass().getCanonicalName());
		}
	}

	/**
	 * Get all commonArea and add on listCommonArea
	 */
	public void getAllCommonArea() {
		requestType = "READ ALL";
		try {
			objectMapper = new ObjectMapper();
			connectionGived = DataSource.getConnectionFromJDBC(pool);
			DAO<CommonArea> commonAreaDao = new CommonAreaDAO(connectionGived);
			jsonString = ((CommonAreaDAO) commonAreaDao).readAll(requestType);
			DataSource.returnConnection(pool, connectionGived);
			CommonArea[] commonAreas = objectMapper.readValue(jsonString, CommonArea[].class);
			listCommonArea = Arrays.asList(commonAreas);
			logger.log(Level.DEBUG, "Find CommonArea datas succed");
		} catch (SQLException | IOException e) {
			logger.log(Level.WARN, "Impossible to get datas " + e.getClass().getCanonicalName());
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
			if (result)
				logger.log(Level.DEBUG, "Insertion SensorHistorical datas succed");
		} catch (SQLException | JsonProcessingException e) {
			logger.log(Level.WARN, "Impossible to insert datas " + e.getClass().getCanonicalName());
		}
	}

	/**
	 * Add on table Sensor Historical the string converted in sensor and his state
	 * and date
	 * 
	 * @param str
	 */
	public void addHistorical(String str) {
		objectMapper = new ObjectMapper();
		try {
			sensor = objectMapper.readValue(str, Sensor.class);
			curDate = new Date();
			sensorHistorical = new SensorHistorical();
			sensorHistorical.setIdSensor(sensor.getIdSensor());
			sensorHistorical.setAlertState(sensor.getAlertState());
			sensorHistorical.setSensorState(sensor.getSensorState());
			sensorHistorical.setDate(curDate);
			jsonString = objectMapper.writeValueAsString(sensorHistorical);
			connectionGived = DataSource.getConnectionFromJDBC(pool);
			DAO<SensorHistorical> sensorHistoricalDao = new SensorHistoricalDAO(connectionGived);
			setResult(((SensorHistoricalDAO) sensorHistoricalDao).create(jsonString));
			DataSource.returnConnection(pool, connectionGived);
			if (result)
				logger.log(Level.DEBUG, "Insertion SensorHistorical datas succed");
		} catch (IOException | SQLException e) {
			logger.log(Level.WARN, "Impossible to insert datas " + e.getClass().getCanonicalName());
		}
	}

	/**
	 * Add on table Message the sensor and his state and date
	 * 
	 * @param sensor
	 * @param threshold
	 */
	public void addMessage(int threshold, Sensor sens) {
		objectMapper = new ObjectMapper();
		curDate = new Date();
		message = new Message();
		message.setAlertDate(curDate);
		message.setIdSensor(sens.getIdSensor());
		message.setThreshold(threshold);
		try {
			jsonString = objectMapper.writeValueAsString(message);
			connectionGived = DataSource.getConnectionFromJDBC(pool);
			DAO<Message> messageDao = new MessageDAO(connectionGived);
			setResult(((MessageDAO) messageDao).create(jsonString));
			DataSource.returnConnection(pool, connectionGived);
			if (result)
				logger.log(Level.DEBUG, "Insertion Message datas succed");
		} catch (JsonProcessingException | SQLException e) {
			logger.log(Level.WARN, "Impossible to insert datas " + e.getClass().getCanonicalName());
		}
	}

	/**
	 * delete alert list an alert
	 * 
	 * @param str
	 */
	public void deleteAlert(String str) {
		objectMapper = new ObjectMapper();
		try {
			System.out.println(str);
			sensor = objectMapper.readValue(str, Sensor.class);
			for (Alert alerts : listAlert) {
				if (alerts.getIdSensor() == sensor.getIdSensor()) {
					listAlert.remove(alerts);
				}
			}
		} catch (IOException e) {
			logger.log(Level.WARN, "Impossible to delete on Alert list " + e.getClass().getCanonicalName());
		}
	}

	/**
	 * Detect if the messages send by one sensor reached the sensor threshold
	 * 
	 * @param sensors
	 */
	public void detectAlert(Sensor sensors) {
		for (Message messages : listMessageInTreatment) {
			if (sensors.getSensorState()) {
				if (sensors.getTypeSensor().equals(SensorType.SMOKE)
						|| sensors.getTypeSensor().equals(SensorType.ELEVATOR)
						|| sensors.getTypeSensor().equals(SensorType.TEMPERATURE)) {
					if (((sensors.getThresholdMin() >= messages.getThreshold())
							|| sensors.getThresholdMax() <= messages.getThreshold())) {
						if (nbAlert == 0) {
							firstAlertDate = messages.getAlertDate();
						}
						lastAlertDate = messages.getAlertDate();
						nbAlert++;
						logger.log(Level.DEBUG,
								"Sensor : " + sensors.getIdSensor() + " threshold reached : " + messages.getThreshold()
										+ ", Min : " + sensors.getThresholdMin() + " Max : "
										+ sensors.getThresholdMax());
						lastThreshold = messages.getThreshold();
					} else {
						listMessageTreated.add(messages);
					}
				} else {
					if (sensors.getThresholdMax() == messages.getThreshold()) {
						if (nbAlert == 0) {
							firstAlertDate = messages.getAlertDate();
						}
						lastAlertDate = messages.getAlertDate();
						nbAlert++;
						logger.log(Level.DEBUG, "Sensor : " + sensors.getIdSensor() + " threshold reached : "
								+ messages.getThreshold() + ", Max : " + sensors.getThresholdMax());
						lastThreshold = messages.getThreshold();
					} else {
						listMessageTreated.add(messages);
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param list remove all the list of Message type
	 */
	public void cleanListMessage(List<Message> list) {
		list.removeAll(list);
	}

	/**
	 * 
	 * @param list remove all the list of Alert type
	 */
	public void cleanListAlert(List<Alert> list) {
		list.removeAll(list);
	}

	/**
	 * 
	 * @param list remove all the list of Sensor type
	 */
	public void cleanListSensor(List<Sensor> list) {
		list.removeAll(list);
	}

	/**
	 * 
	 * @param message
	 * @param list    add at list the message
	 */
	public void addListMessage(Message message, List<Message> list) {
		list.add(message);
	}

	/**
	 * @return the listAlert
	 */
	public List<Alert> getListAlert() {
		return listAlert;
	}

	/**
	 * @param listAlert the listAlert to set
	 */
	public void setListAlert(List<Alert> listAlert) {
		this.listAlert = listAlert;
	}

	/**
	 * @return the listMessage
	 */
	public List<Message> getListMessage() {
		return listMessage;
	}

	/**
	 * @param listMessage the listMessage to set
	 */
	public void setListMessage(List<Message> listMessage) {
		this.listMessage = listMessage;
	}

	/**
	 * @return the result
	 */
	public boolean isResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(boolean result) {
		this.result = result;
	}
}