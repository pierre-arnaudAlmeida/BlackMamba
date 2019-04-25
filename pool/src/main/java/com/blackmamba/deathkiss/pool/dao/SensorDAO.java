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
		String request;
		try {
			Statement st = con.createStatement();
			Sensor sensor = objectMapper.readValue(jsonString, Sensor.class);
			String state = null;
			if (sensor.getSensorState() == true) {
				state = "ON";
			} else
				state = "OFF";

			request = "insert into capteur (type_capteur, etat, id_partie_commune,type_alert,sensibilite,heure_debut,heure_fin,parametre) values ('"
					+ sensor.getTypeSensor() + "','" + state + "','" + sensor.getIdCommonArea() + "','"
					+ sensor.getAlertState() + "','" + sensor.getSensitivity() + "','" + sensor.getStartActivity()
					+ "','" + sensor.getEndActivity() + "','" + "seuilMin:" + sensor.getThresholdMin() + "seuilMax:"
					+ sensor.getThresholdMax() + "')";
			st.execute(request);
			logger.log(Level.INFO, "Sensor succesfully inserted in BDD");
			return true;
		} catch (IOException | SQLException e) {
			logger.log(Level.INFO, "Impossible to insert sensor datas in BDD" + e.getClass().getCanonicalName());
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
		String request;
		try {
			Statement st = con.createStatement();
			Sensor sensor = objectMapper.readValue(jsonString, Sensor.class);
			request = "DELETE FROM capteur where id_capteur = " + sensor.getIdSensor() + ";";
			st.execute(request);
			logger.log(Level.INFO, "Sensor succesfully deleted in BDD");
			return true;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to delete sensor data in BDD" + e.getClass().getCanonicalName());
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
		String request;
		try {
			Statement st = con.createStatement();
			Sensor sensor = objectMapper.readValue(jsonString, Sensor.class);
			if (sensor.getSensorState() == true) {
				if (sensor.getIdCommonArea() == 0) {
					request = "UPDATE capteur SET id_partie_commune = null, etat = 'ON', type_capteur = '"
							+ sensor.getTypeSensor() + "',type_alert='" + sensor.getAlertState() + "',sensibilite='"
							+ sensor.getSensitivity() + "',heure_debut='" + sensor.getStartActivity() + "',heure_fin='"
							+ sensor.getEndActivity() + "',parametre='" + "seuilMin:" + sensor.getThresholdMin()
							+ "seuilMax:" + sensor.getThresholdMax() + "' where id_capteur = " + sensor.getIdSensor();
				} else {
					request = "UPDATE capteur SET id_partie_commune = " + sensor.getIdCommonArea()
							+ ", etat = 'ON', type_capteur = '" + sensor.getTypeSensor() + "',type_alert='"
							+ sensor.getAlertState() + "',sensibilite='" + sensor.getSensitivity() + "',heure_debut='"
							+ sensor.getStartActivity() + "',heure_fin='" + sensor.getEndActivity() + "',parametre='"
							+ "seuilMin:" + sensor.getThresholdMin() + "seuilMax:" + sensor.getThresholdMax()
							+ "' where id_capteur = " + sensor.getIdSensor();
				}
			} else if (sensor.getSensorState() == false) {
				if (sensor.getIdCommonArea() == 0) {
					request = "UPDATE capteur SET id_partie_commune = null, etat = 'OFF', type_capteur = '"
							+ sensor.getTypeSensor() + "',type_alert='" + sensor.getAlertState() + "',sensibilite='"
							+ sensor.getSensitivity() + "',heure_debut='" + sensor.getStartActivity() + "',heure_fin='"
							+ sensor.getEndActivity() + "',parametre='" + "seuilMin:" + sensor.getThresholdMin()
							+ "seuilMax:" + sensor.getThresholdMax() + "' where id_capteur = " + sensor.getIdSensor();
				} else {
					request = "UPDATE capteur SET id_partie_commune = " + sensor.getIdCommonArea()
							+ ", etat = 'OFF', type_capteur = '" + sensor.getTypeSensor() + "',type_alert='"
							+ sensor.getAlertState() + "',sensibilite='" + sensor.getSensitivity() + "',heure_debut='"
							+ sensor.getStartActivity() + "',heure_fin='" + sensor.getEndActivity() + "',parametre='"
							+ "seuilMin:" + sensor.getThresholdMin() + "seuilMax:" + sensor.getThresholdMax()
							+ "' where id_capteur = " + sensor.getIdSensor();
				}
			} else
				return false;
			st.execute(request);
			logger.log(Level.INFO, "Sensor succesfully update in BDD");
			return true;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to update sensor datas in BDD" + e.getClass().getCanonicalName());
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
		String request;
		try {
			Statement st = con.createStatement();
			Sensor sensor = objectMapper.readValue(jsonString, Sensor.class);
			request = "SELECT * FROM capteur where id_capteur='" + sensor.getIdSensor() + "';";
			result = st.executeQuery(request);
			result.next();

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

			String substring = result.getObject(9).toString();
			int positionMin = substring.indexOf("seuilMin");
			int positionMax = substring.indexOf("seuilMax");
			if (positionMin > -1 && positionMax > -1) {
				String thresholdMin = substring.substring(positionMin + 8, positionMax).trim();
				sensor.setThresholdMin(Integer.parseInt(thresholdMin));
				String thresholdMax = substring.substring(positionMax + 8).trim();
				sensor.setThresholdMax(Integer.parseInt(thresholdMax));
			} else if (positionMin > -1 && positionMax == -1) {
				String thresholdMin = substring.substring(positionMin + 8).trim();
				sensor.setThresholdMin(Integer.parseInt(thresholdMin));
			} else if (positionMin == -1 && positionMax > -1) {
				String thresholdMax = substring.substring(positionMax + 8).trim();
				sensor.setThresholdMax(Integer.parseInt(thresholdMax));
			}
			ObjectMapper obj = new ObjectMapper();
			jsonString = obj.writeValueAsString(sensor);
			logger.log(Level.INFO, "Sensor succesfully find in BDD");
			return jsonString;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to get sensor datas from BDD " + e.getClass().getCanonicalName());
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
		String request;
		Sensor sensor;
		List<Sensor> listSensor = new ArrayList<>();
		try {

			Statement st = con.createStatement();
			request = "SELECT * FROM capteur";
			result = st.executeQuery(request);
			while (result.next()) {
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

				String substring = result.getObject(9).toString();
				int positionMin = substring.indexOf("seuilMin");
				int positionMax = substring.indexOf("seuilMax");
				if (positionMin > -1 && positionMax > -1) {
					String thresholdMin = substring.substring(positionMin + 9, positionMax).trim();
					sensor.setThresholdMin(Integer.parseInt(thresholdMin));
					String thresholdMax = substring.substring(positionMax + 9).trim();
					sensor.setThresholdMax(Integer.parseInt(thresholdMax));
				} else if (positionMin > -1 && positionMax == -1) {
					String thresholdMin = substring.substring(positionMin + 9).trim();
					sensor.setThresholdMin(Integer.parseInt(thresholdMin));
				} else if (positionMin == -1 && positionMax > -1) {
					String thresholdMax = substring.substring(positionMax + 9).trim();
					sensor.setThresholdMax(Integer.parseInt(thresholdMax));
				}

				listSensor.add(sensor);
			}
			ObjectMapper obj = new ObjectMapper();
			jsonString = obj.writeValueAsString(listSensor);
			logger.log(Level.INFO, "Sensors succesfully find in BDD");
			return jsonString;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to get sensor datas from BDD " + e.getClass().getCanonicalName());
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
		String request;
		List<Sensor> listSensor = new ArrayList<>();
		try {
			System.out.println(jsonString);
			Statement st = con.createStatement();
			Sensor sensor = objectMapper.readValue(jsonString, Sensor.class);
			if (sensor.getIdCommonArea() != 0) {
				request = "SELECT * FROM capteur where id_partie_commune = '" + sensor.getIdCommonArea() + "'";
			} else {
				request = "SELECT * FROM capteur where type_capteur = '" + sensor.getTypeSensor() + "'";
			}
			System.out.println(request);
			result = st.executeQuery(request);
			while (result.next()) {
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

				String substring = result.getObject(9).toString();
				int positionMin = substring.indexOf("seuilMin");
				int positionMax = substring.indexOf("seuilMax");
				if (positionMin > -1 && positionMax > -1) {
					String thresholdMin = substring.substring(positionMin + 9, positionMax).trim();
					sensor.setThresholdMin(Integer.parseInt(thresholdMin));
					String thresholdMax = substring.substring(positionMax + 9).trim();
					sensor.setThresholdMax(Integer.parseInt(thresholdMax));
				} else if (positionMin > -1 && positionMax == -1) {
					String thresholdMin = substring.substring(positionMin + 9).trim();
					sensor.setThresholdMin(Integer.parseInt(thresholdMin));
				} else if (positionMin == -1 && positionMax > -1) {
					String thresholdMax = substring.substring(positionMax + 9).trim();
					sensor.setThresholdMax(Integer.parseInt(thresholdMax));
				}

				listSensor.add(sensor);
			}
			ObjectMapper obj = new ObjectMapper();
			jsonString = obj.writeValueAsString(listSensor);
			System.out.println(jsonString);
			logger.log(Level.INFO, "Sensors succesfully find in BDD");
			return jsonString;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to get sensor datas from BDD " + e.getClass().getCanonicalName());
		}
		jsonString = "ERROR";
		return jsonString;
	}
}