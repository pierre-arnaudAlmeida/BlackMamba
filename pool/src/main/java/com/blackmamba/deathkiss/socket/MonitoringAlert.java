package com.blackmamba.deathkiss.socket;

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

import com.blackmamba.deathkiss.connectionpool.DataSource;
import com.blackmamba.deathkiss.connectionpool.JDBCConnectionPool;
import com.blackmamba.deathkiss.dao.DAO;
import com.blackmamba.deathkiss.dao.SensorDAO;
import com.blackmamba.deathkiss.dao.SensorHistoricalDAO;
import com.blackmamba.deathkiss.entity.Alert;
import com.blackmamba.deathkiss.entity.AlertState;
import com.blackmamba.deathkiss.entity.Message;
import com.blackmamba.deathkiss.entity.Sensor;
import com.blackmamba.deathkiss.entity.SensorHistorical;
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
	private SimpleDateFormat formater;
	private JDBCConnectionPool pool;
	private Thread threadVerifySensorActivity;
	private Connection connectionGived;
	private boolean result;
	private List<Alert> listAlert = new ArrayList<Alert>();
	private List<Message> listMessage = new ArrayList<Message>();
	private List<Sensor> listSensor = new ArrayList<Sensor>();
	private List<Sensor> listSensorDown = new ArrayList<Sensor>();
	private static final Logger logger = LogManager.getLogger(MonitoringAlert.class);
	private ResourceBundle rs = ResourceBundle.getBundle("alert");

	public MonitoringAlert(JDBCConnectionPool pool) {
		this.pool = pool;

	}

	public MonitoringAlert() {
	}

	// Fonctionne
	// envoyer des infos comme la temperature l taux de dioxyde carbone et ensuite
	// c'est ca q(uon choisi l'&actrion a faire
	public void alertTreatment() {
		getAllSensor();
		for (Sensor sensors : listSensor) {
			nbAlert = 0;
			for (Message messages : listMessage) {
				if (sensors.getIdSensor() == messages.getIdSensor()) {
					if ((sensors.getThresholdMin() >= messages.getThreshold())
							|| sensors.getThresholdMax() <= messages.getThreshold()) {
						if (nbAlert == 0) {
							firstAlertDate = messages.getAlertDate();
						}
						lastAlertDate = messages.getAlertDate();
						nbAlert++;
					} else {
						listMessage.remove(messages);
					}
					// trois date la premiere quand on recoit le premier message donc nbAlert=1
					// ensuite a chaque message reçu étant une alerte alors on sauvegarde la date
					// et a la fin de tout les messages on verifie avec une nouvelle date la date de
					// fin de la liste
					// si la différence entre les deux dates est inferieurs a 25 sec (25s000)

					// si le message ne dépasse pas le seuil on peut le supprimer comme ca on a
					// juste les alertes et si on le declare comme alerte on supprime tout les
					// messages avec l'idSensor de du capteur
				}
				// TODO quand il est en alerte on l'ajoute dans la listAlerte
				// et on supprime tout les messages de la listMessage avec l'idSensor
				// ou tout les messages avec une date inferieurs a 2min
			}
			difference = firstAlertDate.getTime() - lastAlertDate.getTime();
			if (nbAlert >= Integer.parseInt(rs.getString("nbOfAlertMessage"))
					&& difference <= Integer.parseInt(rs.getString("timeBetweenAlert"))) {
				// TODO le considerer comme une alerte
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
		calBefore.add(Calendar.MINUTE, Integer.parseInt(rs.getString("minute_before")));
		calAfter.add(Calendar.MINUTE, Integer.parseInt(rs.getString("minute_after")));
		beforeDate = calBefore.getTime();
		afterDate = calAfter.getTime();
		formater = new SimpleDateFormat("h:mm a");

		getAllSensor();
		for (Sensor sensors : listSensor) {
			if (sensors.getSensorState() == true
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
					sensors.setAlertState(AlertState.DOWN);
					updateSensorAlertState(sensors);
					addHistorical(sensors);
					// TODO ajouter au message d'alerte dans la base

				}
			}
		}
	}

	/**
	 * Create a thread and loop every 30 minutes to verify if the sensor is active
	 * and send messages
	 */
	public void verifySensorActivity(Date currentDate) {
		setThreadVerifySensorActivity(new Thread(new Runnable() {
			/**
			 * For every sensor on Data base they verify if the sensor is active and if the
			 * sensor is active they verify if an alert/message send by sensor contain all
			 * the sensor active if an sensor does'nt have send an alert/message to server
			 * they will be considerate to breakdown and we update is state on data base
			 */
			@Override
			public void run() {
				while (true) {
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

							sensor = new Sensor();
							sensor.setAlertState(AlertState.OVER);
							sensor.setIdSensor(0);
							sensor.setSensorState(true);
							addHistorical(sensor);
							// TODO ajouter a l'historique de la base message
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
								sensors.setAlertState(AlertState.DOWN);
								updateSensorAlertState(sensors);
								addHistorical(sensors);
								// TODO ajouter dans la base message
							}
						}
					}
					try {
						Thread.sleep(Integer.parseInt(rs.getString("time_verifySensorActivity")));
					} catch (InterruptedException e) {
						logger.log(Level.INFO, "Impossible to sleep the thread" + e.getClass().getCanonicalName());
					}
				}
			}
		}));
		threadVerifySensorActivity.start();
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

	public Thread getThreadVerifySensorActivity() {
		return threadVerifySensorActivity;
	}

	public void setThreadVerifySensorActivity(Thread threadVerifySensorActivity) {
		this.threadVerifySensorActivity = threadVerifySensorActivity;
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
