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
	private String request;
	private String sensorState;
	private SensorHistorical sensorHistorical;
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
		Format formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			SensorHistorical sensorH = objectMapper.readValue(jsonString, SensorHistorical.class);
			if (sensorH.getSensorState())
				sensorState = "ON";
			else
				sensorState = "OFF";
			request = "insert into historique (date_historique, etat_capteur, type_alerte, id_capteur) values ('"
					+ formater.format(sensorH.getDate()) + "','" + sensorState + "','"
					+ sensorH.getAlertState().toString() + "', " + sensorH.getIdSensor() + ");";
			Statement st = con.createStatement();
			st.execute(request);
			logger.log(Level.DEBUG, "SensorHistorical succesfully inserted in BDD");
			return true;
		} catch (IOException | SQLException e) {
			e.printStackTrace();
			logger.log(Level.WARN,
					"Impossible to insert sensorHistorical datas in BDD" + e.getClass().getCanonicalName());
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
		try {
			SensorHistorical sensorH = objectMapper.readValue(jsonString, SensorHistorical.class);
			request = "DELETE FROM historique where id_historique = " + sensorH.getIdHistorical() + ";";
			Statement st = con.createStatement();
			st.execute(request);
			logger.log(Level.DEBUG, "SensorHistorical succesfully deleted in BDD");
			return true;
		} catch (SQLException | IOException e) {
			logger.log(Level.WARN,
					"Impossible to delete sensorHistorical datas  in BDD" + e.getClass().getCanonicalName());
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
		Format formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			SensorHistorical sensorH = objectMapper.readValue(jsonString, SensorHistorical.class);
			if (sensorH.getSensorState())
				sensorState = "ON";
			else
				sensorState = "OFF";
			request = "UPDATE message SET id_capteur = '" + sensorH.getIdSensor() + "', date_historique = '"
					+ formater.format(sensorH.getDate()) + "', etat_capteur='" + sensorState + "', type_alerte='"
					+ sensorH.getAlertState().toString() + "';";
			Statement st = con.createStatement();
			st.execute(request);
			logger.log(Level.DEBUG, "Message succesfully update in BDD");
			return true;
		} catch (SQLException | IOException e) {
			logger.log(Level.WARN, "Impossible to update message datas in BDD" + e.getClass().getCanonicalName());
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
		try {
			SensorHistorical sensorH = objectMapper.readValue(jsonString, SensorHistorical.class);
			request = "SELECT * FROM historique where id_historique='" + sensorH.getIdHistorical() + "';";
			Statement st = con.createStatement();
			result = st.executeQuery(request);
			result.next();
			convertDatas(result);
			ObjectMapper obj = new ObjectMapper();
			jsonString = obj.writeValueAsString(sensorHistorical);
			logger.log(Level.DEBUG, "SensorHistorical succesfully find in BDD");
			return jsonString;
		} catch (SQLException | IOException | ParseException e) {
			logger.log(Level.WARN,
					"Impossible to get sensorHistorical datas from BDD " + e.getClass().getCanonicalName());
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
		List<SensorHistorical> listSensorHistorical = new ArrayList<>();
		try {
			request = "SELECT * FROM historique;";
			Statement st = con.createStatement();
			result = st.executeQuery(request);
			while (result.next()) {
				convertDatas(result);
				listSensorHistorical.add(sensorHistorical);
			}
			ObjectMapper obj = new ObjectMapper();
			jsonString = obj.writeValueAsString(listSensorHistorical);
			logger.log(Level.DEBUG, "SensorHistorical succesfully find in BDD");
			return jsonString;
		} catch (SQLException | IOException | ParseException e) {
			logger.log(Level.WARN,
					"Impossible to get sensorHistorical datas from BDD " + e.getClass().getCanonicalName());
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
		try {
			SensorHistorical sensorH = objectMapper.readValue(jsonString, SensorHistorical.class);
			request = "SELECT * FROM historique where id_capteur='" + sensorH.getIdSensor() + "';";
			Statement st = con.createStatement();
			result = st.executeQuery(request);
			result.next();
			convertDatas(result);

			ObjectMapper obj = new ObjectMapper();
			jsonString = obj.writeValueAsString(sensorHistorical);
			logger.log(Level.DEBUG, "Sensor succesfully find in BDD");
			return jsonString;
		} catch (SQLException | IOException | ParseException e) {
			logger.log(Level.WARN, "Impossible to get sensor datas from BDD " + e.getClass().getCanonicalName());
		}
		jsonString = "ERROR";
		return jsonString;
	}

	public void convertDatas(ResultSet result) throws NumberFormatException, SQLException, ParseException {
		sensorHistorical = new SensorHistorical();
		sensorHistorical.setIdHistorical(Integer.parseInt(result.getObject(1).toString()));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = formatter.parse(result.getObject(2).toString());
		sensorHistorical.setDate(date);
		if (result.getObject(3).toString().equals("ON"))
			sensorHistorical.setSensorState(true);
		else
			sensorHistorical.setSensorState(false);
		AlertState alertStateElement = AlertState.valueOf(result.getObject(4).toString());
		sensorHistorical.setAlertState(alertStateElement);
		sensorHistorical.setIdSensor(Integer.parseInt(result.getObject(5).toString()));
	}
}