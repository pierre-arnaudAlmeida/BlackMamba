package com.blackmamba.deathkiss.pool.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.blackmamba.deathkiss.pool.entity.AlertState;
import com.blackmamba.deathkiss.pool.entity.SensorHistorical;
import com.fasterxml.jackson.core.JsonProcessingException;
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
	private SensorHistorical sensorHistorical;
	private SensorHistorical sensorH;
	private StringBuilder requestSB;
	private Statement st;
	private PreparedStatement prepareStatement;
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
		boolean result = false;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			sensorH = objectMapper.readValue(jsonString, SensorHistorical.class);
			prepareStatement = con.prepareStatement(
					"INSERT INTO historique (date_historique, etat_capteur, type_alerte, id_capteur) values (?,?,?,?");
			prepareStatement.setDate(1, new java.sql.Date(sensorH.getDate().getTime()));
			if (sensorH.getSensorState())
				prepareStatement.setString(2, "ON");
			else
				prepareStatement.setString(2, "OFF");
			prepareStatement.setString(3, sensorH.getAlertState().name());
			prepareStatement.setInt(4, sensorH.getIdSensor());
			result = prepareStatement.execute();
			logger.log(Level.DEBUG, "SensorHistorical succesfully inserted in BDD");
		} catch (IOException | SQLException e) {
			logger.log(Level.WARN,
					"Impossible to insert sensorHistorical datas in BDD " + e.getClass().getCanonicalName());
		}
		return result;
	}

	/**
	 * Convert the JSON string in Object and create a request to delete values in
	 * table 'historique' return a boolean
	 */
	@Override
	public boolean delete(String jsonString) {
		boolean result = false;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			sensorH = objectMapper.readValue(jsonString, SensorHistorical.class);
			requestSB = new StringBuilder("DELETE FROM historique where id_historique = ");
			requestSB.append(sensorH.getIdHistorical());
			st = con.createStatement();
			result = st.execute(requestSB.toString());
			logger.log(Level.DEBUG, "SensorHistorical succesfully deleted in BDD");
		} catch (SQLException | IOException e) {
			logger.log(Level.WARN,
					"Impossible to delete sensorHistorical datas  in BDD " + e.getClass().getCanonicalName());
		}
		return result;
	}

	/**
	 * Convert the JSON string in Object and create a request to update values in
	 * table 'historique' return a boolean
	 */
	@Override
	public boolean update(String jsonString) {
		boolean result = false;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			sensorH = objectMapper.readValue(jsonString, SensorHistorical.class);
			prepareStatement = con.prepareStatement(
					"UPDATE historique SET id_capteur = ?, date_historique = ?, etat_capteur= ?, type_alerte= ? where id_historique = ?");
			prepareStatement.setInt(1, sensorH.getIdSensor());
			prepareStatement.setDate(2, new java.sql.Date(sensorH.getDate().getTime()));
			if (sensorH.getSensorState())
				prepareStatement.setString(3, "ON");
			else
				prepareStatement.setString(3, "OFF");
			prepareStatement.setString(4, sensorH.getAlertState().name());
			prepareStatement.setInt(5, sensorH.getIdHistorical());
			result = prepareStatement.execute();
			logger.log(Level.DEBUG, "Message succesfully update in BDD");
		} catch (SQLException | IOException e) {
			logger.log(Level.WARN, "Impossible to update message datas in BDD " + e.getClass().getCanonicalName());
		}
		return result;
	}

	/**
	 * Convert the JSON string in Object and create a request to read (select)
	 * values in table 'historique' return a JSON string
	 */
	@Override
	public String read(String jsonString) {
		ObjectMapper objWriter = new ObjectMapper();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			sensorH = objectMapper.readValue(jsonString, SensorHistorical.class);
			requestSB = new StringBuilder(
					"SELECT id_historique,date_historique, etat_capteur, type_alerte, id_capteur ");
			requestSB.append("FROM historique where id_historique=");
			requestSB.append(sensorH.getIdHistorical());
			st = con.createStatement();
			result = st.executeQuery(requestSB.toString());
			result.next();
			convertDatas(result);
			jsonString = objWriter.writeValueAsString(sensorHistorical);
			logger.log(Level.DEBUG, "SensorHistorical succesfully find in BDD");
		} catch (SQLException | IOException | ParseException e) {
			logger.log(Level.WARN,
					"Impossible to get sensorHistorical datas from BDD " + e.getClass().getCanonicalName());
			jsonString = "ERROR";
		}
		return jsonString;
	}

	/**
	 * Convert the JSON string in Object and create a request to read (select) all
	 * values in table 'historique' return a JSON string
	 */
	@Override
	public String readAll(String jsonString) {
		ObjectMapper objWriter = new ObjectMapper();
		List<SensorHistorical> listSensorHistorical = new ArrayList<>();
		try {
			request = "SELECT id_historique,date_historique, etat_capteur, type_alerte, id_capteur FROM historique";
			st = con.createStatement();
			result = st.executeQuery(request);
			while (result.next()) {
				convertDatas(result);
				listSensorHistorical.add(sensorHistorical);
			}
			jsonString = objWriter.writeValueAsString(listSensorHistorical);
			logger.log(Level.DEBUG, "SensorHistorical succesfully find in BDD");
		} catch (SQLException | IOException | ParseException e) {
			logger.log(Level.WARN,
					"Impossible to get sensorHistorical datas from BDD " + e.getClass().getCanonicalName());
			jsonString = "ERROR";
		}
		return jsonString;
	}

	/**
	 * Convert the JSON string in Object and create a request to read (select) all
	 * values in table 'historique' by the id of commonArea or the type of sensor or
	 * the state of sensor
	 * 
	 * @param jsonString
	 * @return jsonString
	 */
	public String findBySensor(String jsonString) {
		ObjectMapper objWriter = new ObjectMapper();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			sensorH = objectMapper.readValue(jsonString, SensorHistorical.class);
			requestSB = new StringBuilder(
					"SELECT id_historique,date_historique, etat_capteur, type_alerte, id_capteur ");
			requestSB.append("FROM historique where id_capteur=");
			requestSB.append(sensorH.getIdSensor());
			st = con.createStatement();
			result = st.executeQuery(requestSB.toString());
			result.next();
			convertDatas(result);
			jsonString = objWriter.writeValueAsString(sensorHistorical);
			logger.log(Level.DEBUG, "Sensor succesfully find in BDD");
		} catch (SQLException | IOException | ParseException e) {
			logger.log(Level.WARN, "Impossible to get sensor datas from BDD " + e.getClass().getCanonicalName());
			jsonString = "ERROR";
		}
		return jsonString;
	}

	/**
	 * Count the number of lines in the table 'historique'
	 * 
	 * @return
	 */
	public String countSensorHistorical() {
		ObjectMapper objWriter = new ObjectMapper();
		String jsonString = "";
		try {
			request = "SELECT COUNT (*) FROM historique";
			st = con.createStatement();
			result = st.executeQuery(request);
			result.next();
			jsonString = result.getObject(1).toString();
			jsonString = objWriter.writeValueAsString(jsonString);
		} catch (SQLException | JsonProcessingException e) {
			logger.log(Level.WARN,
					"Impossible to get SensorHistorical datas from BDD " + e.getClass().getCanonicalName());
		}
		return jsonString;
	}

	/**
	 * Count the number of lines in the table 'historique' where date condition
	 * 
	 * @return
	 */
	public String countUpdateSensorHistorical() {
		ObjectMapper objWriter = new ObjectMapper();
		String jsonString = "";
		try {
			request = "SELECT COUNT(*) FROM historique WHERE date_historique BETWEEN '2000-10-01' AND '2008-10-01' AND ( type_alerte = 'NORMAL' )";
			st = con.createStatement();
			result = st.executeQuery(request);
			result.next();
			jsonString = result.getObject(1).toString();
			jsonString = objWriter.writeValueAsString(jsonString);
		} catch (SQLException | JsonProcessingException e) {
			logger.log(Level.WARN,
					"Impossible to get SensorHistorical datas from BDD " + e.getClass().getCanonicalName());
		}
		return jsonString;
	}

	/**
	 * Count the number of lines in the table 'historique' for every state
	 * 
	 * @return
	 */
	public String countAllStateSensorHistorical() {
		ObjectMapper objWriter = new ObjectMapper();
		String jsonString = "";
		try {
			request = "SELECT sum(case when type_alerte = 'DELETED' then 1 else 0 end ) As DeletedCount,sum(case when type_alerte = 'NORMAL' then 1 else 0 end ) As NormalCount,sum(case when type_alerte = 'DOWN' then 1 else 0 end ) As DownCount,sum(case when type_alerte = 'ALERT' then 1 else 0 end ) As AlertCount FROM historique";
			st = con.createStatement();
			result = st.executeQuery(request);
			result.next();
			jsonString = result.getInt(1) + "," + result.getInt(2) + "," + result.getInt(3) + "," + result.getInt(4);
			jsonString = objWriter.writeValueAsString(jsonString);
		} catch (SQLException | JsonProcessingException e) {
			logger.log(Level.WARN,
					"Impossible to get SensorHistorical datas from BDD " + e.getClass().getCanonicalName());
		}
		return jsonString;
	}

	/**
	 * Transform the result of the request in one SensorHistorique object
	 * 
	 * @param result
	 * @throws NumberFormatException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void convertDatas(ResultSet result) throws NumberFormatException, SQLException, ParseException {
		sensorHistorical = new SensorHistorical();
		sensorHistorical.setIdHistorical(result.getInt("id_historique"));
		sensorHistorical.setDate(result.getDate("date_historique"));
		if (result.getString("etat_capteur").equals("ON"))
			sensorHistorical.setSensorState(true);
		else
			sensorHistorical.setSensorState(false);
		sensorHistorical.setAlertState(AlertState.valueOf(result.getString("type_alerte")));
		sensorHistorical.setIdSensor(result.getInt("id_capteur"));
		logger.log(Level.DEBUG, "Convertion resultSet into SensorHistorique java object succeed");
	}
}