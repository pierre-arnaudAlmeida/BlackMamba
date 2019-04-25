package com.blackmamba.deathkiss.pool.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.blackmamba.deathkiss.pool.entity.AlertState;
import com.blackmamba.deathkiss.pool.entity.SensorHistorical;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class SensorHistoricalDAO extends DAO<SensorHistorical> {

	/**
	 * Initialization of parameters
	 */
	private ResultSet result = null;
	private static final Logger logger = LogManager.getLogger(SensorHistoricalDAO.class);

	/**
	 * Constructor
	 * 
	 * @param con
	 */
	public SensorHistoricalDAO(Connection con) {
		super(con);
	}

	/**
	 * Convert the JSON string in Object and create a request to insert values in
	 * table 'historique' return a boolean
	 */
	@Override
	public boolean create(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		String request;
		String sensorState;
		Format formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Statement st = con.createStatement();
			SensorHistorical sensorHistorical = objectMapper.readValue(jsonString, SensorHistorical.class);

			if (sensorHistorical.getSensorState() == true)
				sensorState = "ON";
			else
				sensorState = "OFF";
			request = "insert into historique (date_historique, etat_capteur, type_alerte, id_capteur) values ('" + formater.format(sensorHistorical.getDate()) + "','" + sensorState + "','" + sensorHistorical.getAlertState().toString() + "', " + sensorHistorical.getIdSensor() + ")";

			st.execute(request);
			logger.log(Level.INFO, "SensorHistorical succesfully inserted in BDD");
			return true;
		} catch (IOException | SQLException e) {
			e.printStackTrace();
			logger.log(Level.INFO, "Impossible to insert sensorHistorical datas in BDD" + e.getClass().getCanonicalName());
			return false;
		}
	}

	/**
	 * Convert the JSON string in Object and create a request to delete values in
	 * table 'historique' return a boolean
	 */
	@Override
	public boolean delete(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		String request;
		try {
			Statement st = con.createStatement();
			SensorHistorical sensorHistorical = objectMapper.readValue(jsonString, SensorHistorical.class);
			request = "DELETE FROM historique where id_historique = " + sensorHistorical.getIdHistorical() + ";";
			st.execute(request);
			logger.log(Level.INFO, "SensorHistorical succesfully deleted in BDD");
			return true;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to delete sensorHistorical datas  in BDD" + e.getClass().getCanonicalName());
			return false;
		}
	}

	/**
	 * Convert the JSON string in Object and create a request to update values in
	 * table 'historique' return a boolean
	 */
	@Override
	public boolean update(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		String request;
		String sensorState;
		Format formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Statement st = con.createStatement();
			SensorHistorical sensorHistorical = objectMapper.readValue(jsonString, SensorHistorical.class);
			if (sensorHistorical.getSensorState() == true)
				sensorState = "ON";
			else
				sensorState = "OFF";
			request = "UPDATE message SET id_capteur = '" + sensorHistorical.getIdSensor() + "', date_historique = '" + formater.format(sensorHistorical.getDate()) + "', etat_capteur='" + sensorState + "', type_alerte='" + sensorHistorical.getAlertState().toString() + "'";
			st.execute(request);
			logger.log(Level.INFO, "Message succesfully update in BDD");
			return true;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to update message datas in BDD" + e.getClass().getCanonicalName());
			return false;
		}
	}

	/**
	 * Convert the JSON string in Object and create a request to read (select)
	 * values in table 'historique' return a JSON string
	 */
	@Override
	public String read(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		String request;
		try {
			Statement st = con.createStatement();
			SensorHistorical sensorHistorical = objectMapper.readValue(jsonString, SensorHistorical.class);
			request = "SELECT * FROM historique where id_historique='" + sensorHistorical.getIdHistorical() + "';";
			result = st.executeQuery(request);
			result.next();

			sensorHistorical.setIdHistorical(Integer.parseInt(result.getObject(1).toString()));
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = formatter.parse(result.getObject(2).toString());
			sensorHistorical.setDate(date);
			if (result.getObject(3).toString().equals("ON"))
				sensorHistorical.setSensorState(true);
			else
				sensorHistorical.setSensorState(false);

			if (result.getObject(4).toString().equals("NORMAL")) {
				sensorHistorical.setAlertState(AlertState.NORMAL);
			} else if (result.getObject(4).toString().equals("ALERT")) {
				sensorHistorical.setAlertState(AlertState.ALERT);
			} else if (result.getObject(4).toString().equals("DOWN")) {
				sensorHistorical.setAlertState(AlertState.DOWN);
			} else if (result.getObject(4).toString().equals("OVER")) {
				sensorHistorical.setAlertState(AlertState.OVER);
			}
			sensorHistorical.setIdSensor(Integer.parseInt(result.getObject(5).toString()));

			ObjectMapper obj = new ObjectMapper();
			jsonString = obj.writeValueAsString(sensorHistorical);
			logger.log(Level.INFO, "SensorHistorical succesfully find in BDD");
			return jsonString;
		} catch (SQLException | IOException | ParseException e) {
			logger.log(Level.INFO, "Impossible to get sensorHistorical datas from BDD " + e.getClass().getCanonicalName());
		}
		jsonString = "ERROR";
		return jsonString;
	}

	/**
	 * Convert the JSON string in Object and create a request to read (select) all
	 * values in table 'historique' return a JSON string
	 */
	@Override
	public String readAll(String jsonString) {
		String request;
		SensorHistorical sensorHistorical;
		List<SensorHistorical> listSensorHistorical = new ArrayList<>();
		try {

			Statement st = con.createStatement();
			request = "SELECT * FROM historique";
			result = st.executeQuery(request);
			while (result.next()) {
				sensorHistorical = new SensorHistorical();
				sensorHistorical.setIdHistorical(Integer.parseInt(result.getObject(1).toString()));
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = formatter.parse(result.getObject(2).toString());
				sensorHistorical.setDate(date);
				if (result.getObject(3).toString().equals("ON"))
					sensorHistorical.setSensorState(true);
				else
					sensorHistorical.setSensorState(false);

				if (result.getObject(4).toString().equals("NORMAL")) {
					sensorHistorical.setAlertState(AlertState.NORMAL);
				} else if (result.getObject(4).toString().equals("ALERT")) {
					sensorHistorical.setAlertState(AlertState.ALERT);
				} else if (result.getObject(4).toString().equals("DOWN")) {
					sensorHistorical.setAlertState(AlertState.DOWN);
				} else if (result.getObject(4).toString().equals("OVER")) {
					sensorHistorical.setAlertState(AlertState.OVER);
				}

				sensorHistorical.setIdSensor(Integer.parseInt(result.getObject(5).toString()));
				listSensorHistorical.add(sensorHistorical);
			}
			ObjectMapper obj = new ObjectMapper();
			jsonString = obj.writeValueAsString(listSensorHistorical);
			logger.log(Level.INFO, "SensorHistorical succesfully find in BDD");
			return jsonString;
		} catch (SQLException | IOException | ParseException e) {
			logger.log(Level.INFO, "Impossible to get sensorHistorical datas from BDD " + e.getClass().getCanonicalName());
		}
		jsonString = "ERROR";
		return jsonString;
	}

	/**
	 * Convert the JSON string in Object and create a request to read (select) all
	 * values in table 'historique' by the id of commonArea or the type of sensor or
	 * the state of sensor
	 */
	public String findBySensor(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		String request;
		SensorHistorical sensorHistorical;
		try {
			Statement st = con.createStatement();
			SensorHistorical sensorH = objectMapper.readValue(jsonString, SensorHistorical.class);
			request = "SELECT * FROM historique where id_capteur='" + sensorH.getIdSensor() + "';";
			result = st.executeQuery(request);
			result.next();

			sensorHistorical = new SensorHistorical();
			sensorHistorical.setIdHistorical(Integer.parseInt(result.getObject(1).toString()));
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = formatter.parse(result.getObject(2).toString());
			sensorHistorical.setDate(date);
			if (result.getObject(3).toString().equals("ON"))
				sensorHistorical.setSensorState(true);
			else
				sensorHistorical.setSensorState(false);

			if (result.getObject(4).toString().equals("NORMAL")) {
				sensorHistorical.setAlertState(AlertState.NORMAL);
			} else if (result.getObject(4).toString().equals("ALERT")) {
				sensorHistorical.setAlertState(AlertState.ALERT);
			} else if (result.getObject(4).toString().equals("DOWN")) {
				sensorHistorical.setAlertState(AlertState.DOWN);
			} else if (result.getObject(4).toString().equals("OVER")) {
				sensorHistorical.setAlertState(AlertState.OVER);
			}
			sensorHistorical.setIdSensor(Integer.parseInt(result.getObject(5).toString()));

			ObjectMapper obj = new ObjectMapper();
			jsonString = obj.writeValueAsString(sensorHistorical);
			logger.log(Level.INFO, "Sensor succesfully find in BDD");
			return jsonString;
		} catch (SQLException | IOException | ParseException e) {
			logger.log(Level.INFO, "Impossible to get sensor datas from BDD " + e.getClass().getCanonicalName());
		}
		jsonString = "ERROR";
		return jsonString;
	}
}