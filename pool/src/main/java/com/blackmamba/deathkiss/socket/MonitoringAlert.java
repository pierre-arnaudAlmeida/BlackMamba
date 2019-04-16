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
import com.blackmamba.deathkiss.entity.Alert;
import com.blackmamba.deathkiss.entity.AlertState;
import com.blackmamba.deathkiss.entity.Message;
import com.blackmamba.deathkiss.entity.Sensor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class MonitoringAlert {

	private String requestType;
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
	private JDBCConnectionPool pool;
	private Connection connectionGived;
	private boolean result;
	private List<Alert> listAlert = new ArrayList<Alert>();
	private List<Sensor> listSensor = new ArrayList<Sensor>();
	private List<Sensor> listSensorDown = new ArrayList<Sensor>();
	private static final Logger logger = LogManager.getLogger(MonitoringAlert.class);

	public MonitoringAlert(JDBCConnectionPool pool) {
		this.pool = pool;
		ResourceBundle rs = ResourceBundle.getBundle("alert");
	}

	// Fonctionne
	// envoyer des infos comme la temperature l taux de dioxyde carbone et ensuite
	// c'est ca q(uon choisi l'&actrion a faire
	public void alertTreatment() {
		currentDate = new Date();
		// getAllMessages(currentDate); remplacer par la list TODO
		for (Alert alerts : listAlert) {
			if (alerts.getAlertState() == AlertState.ALERT) {
				getSensor(alerts.getIdSensor());
				if (sensor.getSensorState() == true) {
					sensor.setAlertState(AlertState.ALERT);
					updateSensorAlertState(sensor);
				}
			} else if (alerts.getAlertState() == AlertState.DOWN) {
				getSensor(alerts.getIdSensor());
				if (sensor.getSensorState() == true) {
					sensor.setAlertState(AlertState.DOWN);
					updateSensorAlertState(sensor);
				}
			}
		}
	}

	// Fonctionne
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
		// getAllMessages(curDate); remplacer par la list TODO

		for (Sensor sensors : listSensor) {
			if (sensors.getSensorState() == true
					&& (formater.format(sensors.getStartActivity()).compareTo(formater.format(beforeDate)) >= 0)
					&& (formater.format(sensors.getStartActivity()).compareTo(formater.format(afterDate)) <= 0)) {
				numberOfMessages = 0;
				for (Alert alerts : listAlert) {
					// if ((alerts.getIdSensor() == sensors.getIdSensor())
					// &&
					// (formater.format(alerts.getAlertDate()).compareTo(formater.format(afterDate))
					// <= 0)) {
					// numberOfMessages++;
					// }
				}
				if (numberOfMessages == 0) {
					sensors.setAlertState(AlertState.DOWN);
					updateSensorAlertState(sensors);
				}
			}
		}
	}

	// Fonctionne
	// TODO faire un thread qui boucle toute les 30min
	public void verifySensorActivity(Date currentDate) {
		for (int i = 0; i < listSensorDown.size(); i++)
			listSensorDown.remove(i);

		getAllSensor();
		// getAllMessages(currentDate); remplacer par la liste TODO
		for (Sensor sensors : listSensor) {
			numberOfIteration = 0;
			if (sensors.getSensorState() == true) {
				for (Alert alerts : listAlert) {
					if (sensors.getIdSensor() == alerts.getIdSensor()) {
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

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
}
