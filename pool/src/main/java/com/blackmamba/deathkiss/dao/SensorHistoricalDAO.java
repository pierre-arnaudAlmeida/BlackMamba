package com.blackmamba.deathkiss.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.blackmamba.deathkiss.entity.SensorHistorical;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SensorHistoricalDAO extends DAO<SensorHistorical> {

	private ResultSet result = null;
	private static final Logger logger = LogManager.getLogger(EmployeeDAO.class);
	
	public SensorHistoricalDAO(Connection con) {
		super(con);
	}

	@Override
	public boolean create(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		String request;
		try {
			Statement st = con.createStatement();
			SensorHistorical sensorHistorical = objectMapper.readValue(jsonString, SensorHistorical.class);
			request = "insert into historique (date_historique, etat_avant, etat_apres, id_capteur) values ('','','','')";//TODO faire en fonction des etat avant et apres sachant que  les getstate sont des boolean transformer en ON OFF
			st.execute(request);
			logger.log(Level.INFO, "SensorHistorical succesfully inserted in BDD");
			return true;
		} catch (IOException | SQLException e) {
			logger.log(Level.INFO, "Impossible to insert sensorHistorical datas in BDD" + e.getClass().getCanonicalName());
			return false;
		}
	}

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

	@Override
	public boolean update(String jsonString) {
		// TODO Auto-generated method stub
		return false;
	}

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
			sensorHistorical.setDate(result.getObject(2).toString());//TODO convertir un String en Date
			sensorHistorical.setPreviousState(result.getObject(3).toString());//TODO  dans les deux cas du boolean
			sensorHistorical.setNextState(result.getObject(4).toString());//TODO
			sensorHistorical.setIdSensor(Integer.parseInt(result.getObject(5).toString()));
			
			ObjectMapper obj = new ObjectMapper();
			jsonString = obj.writeValueAsString(sensorHistorical);
			logger.log(Level.INFO, "SensorHistorical succesfully find in BDD");
			return jsonString;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to get sensorHistorical datas from BDD " + e.getClass().getCanonicalName());
		}
		jsonString = "ERROR";
		return jsonString;
	}

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
				sensorHistorical.setDate(result.getObject(2).toString());//TODO convertir un String en Date
				sensorHistorical.setPreviousState(result.getObject(3).toString());//TODO  dans les deux cas du boolean
				sensorHistorical.setNextState(result.getObject(4).toString());//TODO
				sensorHistorical.setIdSensor(Integer.parseInt(result.getObject(5).toString()));
				listSensorHistorical.add(sensorHistorical);
			}
			ObjectMapper obj = new ObjectMapper();
			jsonString = obj.writeValueAsString(listSensorHistorical);
			logger.log(Level.INFO, "SensorHistorical succesfully find in BDD");
			return jsonString;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to get sensorHistorical datas from BDD " + e.getClass().getCanonicalName());
		}
		jsonString = "ERROR";
		return jsonString;
	}

}
