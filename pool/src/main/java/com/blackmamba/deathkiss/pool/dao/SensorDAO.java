package com.blackmamba.deathkiss.pool.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.blackmamba.deathkiss.pool.entity.AlertState;
import com.blackmamba.deathkiss.pool.entity.Sensitivity;
import com.blackmamba.deathkiss.pool.entity.Sensor;
import com.blackmamba.deathkiss.pool.entity.SensorType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class SensorDAO extends DAO<Sensor> {

	/**
	 * Initialization of parameters
	 */
	private ResultSet result = null;
	private Sensor sensor;
	private String request;
	private static final Logger logger = LogManager.getLogger(SensorDAO.class);

	/**
	 * Constructor
	 * 
	 * @param con
	 */
	public SensorDAO(Connection con) {
		super(con);
	}

	/**
	 * Convert the JSON string in Object and create a request to insert values in
	 * table 'capteur' return a boolean
	 */
	@Override
	public boolean create(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Sensor sensor = objectMapper.readValue(jsonString, Sensor.class);
			String state = null;
			if (sensor.getSensorState()) {
				state = "ON";
			} else
				state = "OFF";// TODO PA ,parametre
			request = "insert into capteur (type_capteur, etat, id_partie_commune,type_alert,sensibilite,heure_debut,heure_fin) values ('"
					+ sensor.getTypeSensor() + "','" + state + "','" + sensor.getIdCommonArea() + "','"
					+ sensor.getAlertState() + "','" + sensor.getSensitivity() + "','" + sensor.getStartActivity()
					+ "','" + sensor.getEndActivity() + "');";
			// TODO + "','" + "seuilMin:" +
			// sensor.getThresholdMin() + "seuilMax:"
			// + sensor.getThresholdMax() + "');";
			Statement st = con.createStatement();
			st.execute(request);
			logger.log(Level.DEBUG, "Sensor succesfully inserted in BDD");
			return true;
		} catch (IOException | SQLException e) {
			e.printStackTrace();
			logger.log(Level.WARN, "Impossible to insert sensor datas in BDD" + e.getClass().getCanonicalName());
			return false;
		}
	}

	/**
	 * Convert the JSON string in Object and create a request to delete values in
	 * table 'capteur' return a boolean
	 */
	@Override
	public boolean delete(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Sensor sensor = objectMapper.readValue(jsonString, Sensor.class);
			request = "DELETE FROM capteur where id_capteur = " + sensor.getIdSensor() + ";";
			Statement st = con.createStatement();
			st.execute(request);
			logger.log(Level.DEBUG, "Sensor succesfully deleted in BDD");
			return true;
		} catch (SQLException | IOException e) {
			logger.log(Level.WARN, "Impossible to delete sensor data in BDD" + e.getClass().getCanonicalName());
			return false;
		}
	}

	/**
	 * Convert the JSON string in Object and create a request to update values in
	 * table 'capteur' return a boolean
	 */
	@Override
	public boolean update(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Sensor sensor = objectMapper.readValue(jsonString, Sensor.class);
			if (sensor.getSensorState()) {
				if (sensor.getIdCommonArea() == 0) {
					request = "UPDATE capteur SET id_partie_commune = null, etat = 'ON', type_capteur = '"
							+ sensor.getTypeSensor() + "',type_alert='" + sensor.getAlertState() + "',sensibilite='"
							+ sensor.getSensitivity() + "',heure_debut='" + sensor.getStartActivity() + "',heure_fin='"
							+ sensor.getEndActivity() + "',parametre='" + "seuilMin:" + sensor.getThresholdMin()
							+ "seuilMax:" + sensor.getThresholdMax() + "' where id_capteur = " + sensor.getIdSensor()
							+ ";";
				} else {
					request = "UPDATE capteur SET id_partie_commune = " + sensor.getIdCommonArea()
							+ ", etat = 'ON', type_capteur = '" + sensor.getTypeSensor() + "',type_alert='"
							+ sensor.getAlertState() + "',sensibilite='" + sensor.getSensitivity() + "',heure_debut='"
							+ sensor.getStartActivity() + "',heure_fin='" + sensor.getEndActivity() + "',parametre='"
							+ "seuilMin:" + sensor.getThresholdMin() + "seuilMax:" + sensor.getThresholdMax()
							+ "' where id_capteur = " + sensor.getIdSensor() + ";";
				}
			} else if (!sensor.getSensorState()) {
				if (sensor.getIdCommonArea() == 0) {
					request = "UPDATE capteur SET id_partie_commune = null, etat = 'OFF', type_capteur = '"
							+ sensor.getTypeSensor() + "',type_alert='" + sensor.getAlertState() + "',sensibilite='"
							+ sensor.getSensitivity() + "',heure_debut='" + sensor.getStartActivity() + "',heure_fin='"
							+ sensor.getEndActivity() + "',parametre='" + "seuilMin:" + sensor.getThresholdMin()
							+ "seuilMax:" + sensor.getThresholdMax() + "' where id_capteur = " + sensor.getIdSensor()
							+ ";";
				} else {
					request = "UPDATE capteur SET id_partie_commune = " + sensor.getIdCommonArea()
							+ ", etat = 'OFF', type_capteur = '" + sensor.getTypeSensor() + "',type_alert='"
							+ sensor.getAlertState() + "',sensibilite='" + sensor.getSensitivity() + "',heure_debut='"
							+ sensor.getStartActivity() + "',heure_fin='" + sensor.getEndActivity() + "',parametre='"
							+ "seuilMin:" + sensor.getThresholdMin() + "seuilMax:" + sensor.getThresholdMax()
							+ "' where id_capteur = " + sensor.getIdSensor() + ";";
				}
			} else
				return false;
			Statement st = con.createStatement();
			st.execute(request);
			logger.log(Level.DEBUG, "Sensor succesfully update in BDD");
			return true;
		} catch (SQLException | IOException e) {
			logger.log(Level.WARN, "Impossible to update sensor datas in BDD" + e.getClass().getCanonicalName());
			return false;
		}
	}

	/**
	 * Convert the JSON string in Object and create a request to read (select)
	 * values in table 'capteur' return a JSON string
	 */
	@Override
	public String read(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			sensor = objectMapper.readValue(jsonString, Sensor.class);
			request = "SELECT * FROM capteur where id_capteur='" + sensor.getIdSensor() + "';";
			Statement st = con.createStatement();
			result = st.executeQuery(request);
			result.next();
			convertDatas(result);

			ObjectMapper obj = new ObjectMapper();
			jsonString = obj.writeValueAsString(sensor);
			logger.log(Level.DEBUG, "Sensor succesfully find in BDD");
			return jsonString;
		} catch (SQLException | IOException e) {
			logger.log(Level.WARN, "Impossible to get sensor datas from BDD " + e.getClass().getCanonicalName());
		}
		jsonString = "ERROR";
		return jsonString;
	}

	/**
	 * Convert the JSON string in Object and create a request to read (select) all
	 * values in table 'capteur' return a JSON string
	 */
	@Override
	public String readAll(String jsonString) {
		List<Sensor> listSensor = new ArrayList<>();
		try {
			request = "SELECT * FROM capteur;";
			Statement st = con.createStatement();
			result = st.executeQuery(request);
			while (result.next()) {
				convertDatas(result);
				listSensor.add(sensor);
			}
			ObjectMapper obj = new ObjectMapper();
			jsonString = obj.writeValueAsString(listSensor);
			logger.log(Level.DEBUG, "Sensors succesfully find in BDD");
			return jsonString;
		} catch (SQLException | IOException e) {
			logger.log(Level.WARN, "Impossible to get sensor datas from BDD " + e.getClass().getCanonicalName());
		}
		jsonString = "ERROR";
		return jsonString;
	}

	/**
	 * Convert the JSON string in Object and create a request to read (select) all
	 * values in table 'capteur' by the type of sensor or the id of commonArea
	 */
	public String findAll(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		List<Sensor> listSensor = new ArrayList<>();
		try {
			System.out.println(jsonString);
			sensor = objectMapper.readValue(jsonString, Sensor.class);
			if (sensor.getIdCommonArea() != 0) {
				request = "SELECT * FROM capteur where id_partie_commune = '" + sensor.getIdCommonArea() + "';";
			} else {
				request = "SELECT * FROM capteur where type_capteur = '" + sensor.getTypeSensor() + "';";
			}
			Statement st = con.createStatement();
			result = st.executeQuery(request);
			while (result.next()) {
				convertDatas(result);
				listSensor.add(sensor);
			}
			ObjectMapper obj = new ObjectMapper();
			jsonString = obj.writeValueAsString(listSensor);
			System.out.println(jsonString);
			logger.log(Level.DEBUG, "Sensors succesfully find in BDD");
			return jsonString;
		} catch (SQLException | IOException e) {
			logger.log(Level.WARN, "Impossible to get sensor datas from BDD " + e.getClass().getCanonicalName());
		}
		jsonString = "ERROR";
		return jsonString;
	}

	public Sensor getThreshold(Sensor sens, String string) {
		int positionMin = string.indexOf("seuilMin");
		int positionMax = string.indexOf("seuilMax");
		if (positionMin > -1 && positionMax > -1) {
			String thresholdMin = string.substring(positionMin + 9, positionMax).trim();
			sens.setThresholdMin(Integer.parseInt(thresholdMin));
			String thresholdMax = string.substring(positionMax + 9).trim();
			sens.setThresholdMax(Integer.parseInt(thresholdMax));
		} else if (positionMin > -1 && positionMax == -1) {
			String thresholdMin = string.substring(positionMin + 9).trim();
			sens.setThresholdMin(Integer.parseInt(thresholdMin));
		} else if (positionMin == -1 && positionMax > -1) {
			String thresholdMax = string.substring(positionMax + 9).trim();
			sens.setThresholdMax(Integer.parseInt(thresholdMax));
		}
		return sens;
	}

	public void convertDatas(ResultSet result) throws NumberFormatException, SQLException {
		sensor = new Sensor();
		sensor.setIdSensor(Integer.parseInt(result.getObject(1).toString()));
		SensorType element = SensorType.valueOf(result.getObject(2).toString());
		sensor.setTypeSensor(element);
		if (result.getObject(3).toString().equals("ON")) {
			sensor.setSensorState(true);
		} else if (result.getObject(3).toString().equals("OFF")) {
			sensor.setSensorState(false);
		}
		try {
			if (!result.getObject(4).equals("")) {
				sensor.setIdCommonArea(Integer.parseInt(result.getObject(4).toString()));
			}
		} catch (Exception e) {
			sensor.setIdCommonArea(0);
		}
		AlertState alertStateElement = AlertState.valueOf(result.getObject(5).toString());
		sensor.setAlertState(alertStateElement);
		Sensitivity sensitivityElement = Sensitivity.valueOf(result.getObject(6).toString());
		sensor.setSensitivity(sensitivityElement);
		sensor.setStartActivity(Time.valueOf(result.getObject(7).toString()));
		sensor.setEndActivity(Time.valueOf(result.getObject(8).toString()));
		// sensor = getThreshold(sensor, result.getObject(9).toString());
	}
}