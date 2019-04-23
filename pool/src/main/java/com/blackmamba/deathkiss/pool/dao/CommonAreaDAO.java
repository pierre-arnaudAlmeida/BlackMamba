package com.blackmamba.deathkiss.pool.dao;

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

import com.blackmamba.deathkiss.pool.entity.CommonArea;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class CommonAreaDAO extends DAO<CommonArea> {

	private ResultSet result = null;
	private static final Logger logger = LogManager.getLogger(CommonAreaDAO.class);

	public CommonAreaDAO(Connection con) {
		super(con);
	}

	/**
	 * Convert the JSON string in Object and create a request to insert values in
	 * table 'partie_commune' return a boolean
	 */
	@Override
	public boolean create(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		String request;
		try {
			Statement st = con.createStatement();
			CommonArea commonArea = objectMapper.readValue(jsonString, CommonArea.class);
			request = "insert into partie_commune (nom_partie_commune, etage_partie_commune) values ('" + commonArea.getNameCommonArea() + "','" + commonArea.getEtageCommonArea() + "')";
			st.execute(request);
			logger.log(Level.INFO, "CommonArea succesfully inserted in BDD");
			return true;
		} catch (IOException | SQLException e) {
			logger.log(Level.INFO, "Impossible to insert commonArea datas in BDD" + e.getClass().getCanonicalName());
			return false;
		}
	}

	/**
	 * Convert the JSON string in Object and create a request to delete values in
	 * table 'partie_commune' return a boolean
	 */
	@Override
	public boolean delete(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		String request;
		try {
			Statement st = con.createStatement();
			CommonArea commonArea = objectMapper.readValue(jsonString, CommonArea.class);
			request = "DELETE FROM partie_commune where id_partie_commune = " + commonArea.getIdCommonArea();
			st.execute(request);
			logger.log(Level.INFO, "CommonArea succesfully deleted in BDD");
			return true;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to delete commonArea datas in BDD" + e.getClass().getCanonicalName());
			return false;
		}
	}

	/**
	 * Convert the JSON string in Object and create a request to update values in
	 * table 'partie_commune' return a boolean
	 */
	@Override
	public boolean update(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		String request;
		try {
			Statement st = con.createStatement();
			CommonArea commonArea = objectMapper.readValue(jsonString, CommonArea.class);
			if (commonArea.getNameCommonArea().equals("") && commonArea.getEtageCommonArea() < 99) {
				request = "UPDATE partie_commune SET etage_partie_commune = '" + commonArea.getEtageCommonArea() + "' where id_partie_commune = '" + commonArea.getIdCommonArea() + "'";
			} else if (!(commonArea.getNameCommonArea().equals("")) && commonArea.getEtageCommonArea() >= 99) {
				request = "UPDATE partie_commune SET nom_partie_commune = '" + commonArea.getNameCommonArea() + "' where id_partie_commune = '" + commonArea.getIdCommonArea() + "'";
			} else if (!(commonArea.getNameCommonArea().equals("")) && commonArea.getEtageCommonArea() < 99) {
				request = "UPDATE partie_commune SET etage_partie_commune = '" + commonArea.getEtageCommonArea() + "', nom_partie_commune = '" + commonArea.getNameCommonArea() + "' where id_partie_commune = '" + commonArea.getIdCommonArea() + "'";
			} else
				return false;
			st.execute(request);
			logger.log(Level.INFO, "CommonArea succesfully update in BDD");
			return true;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to update commonArea datas in BDD" + e.getClass().getCanonicalName());
			return false;
		}
	}

	/**
	 * Convert the JSON string in Object and create a request to read (select)
	 * values in table 'partie_commune' return a JSON string
	 */
	@Override
	public String read(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		String request;
		try {
			Statement st = con.createStatement();
			CommonArea commonArea = objectMapper.readValue(jsonString, CommonArea.class);
			request = "SELECT * FROM partie_commune where id_partie_commune='" + commonArea.getIdCommonArea() + "';";
			result = st.executeQuery(request);
			result.next();

			commonArea.setIdCommonArea(Integer.parseInt(result.getObject(1).toString()));
			commonArea.setNameCommonArea(result.getObject(2).toString());
			commonArea.setEtageCommonArea(Integer.parseInt(result.getObject(3).toString()));
			ObjectMapper obj = new ObjectMapper();
			jsonString = obj.writeValueAsString(commonArea);
			logger.log(Level.INFO, "CommonArea succesfully find in BDD");
			return jsonString;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to get commonArea datas from BDD " + e.getClass().getCanonicalName());
		}
		jsonString = "ERROR";
		return jsonString;
	}

	/**
	 * Convert the JSON string in Object and create a request to read (select) all
	 * values in table 'partie_commune' return a JSON string
	 */
	@Override
	public String readAll(String jsonString) {
		String request;
		CommonArea commonArea;
		List<CommonArea> listCommonArea = new ArrayList<>();
		try {

			Statement st = con.createStatement();
			request = "SELECT * FROM partie_commune";
			result = st.executeQuery(request);
			while (result.next()) {
				commonArea = new CommonArea();
				commonArea.setIdCommonArea(Integer.parseInt(result.getObject(1).toString()));
				commonArea.setNameCommonArea(result.getObject(2).toString());
				commonArea.setEtageCommonArea(Integer.parseInt(result.getObject(3).toString()));
				commonArea.setListSensor(null);
				listCommonArea.add(commonArea);
			}
			ObjectMapper obj = new ObjectMapper();
			jsonString = obj.writeValueAsString(listCommonArea);
			logger.log(Level.INFO, "CommonAreas succesfully find in BDD");
			return jsonString;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to get commonArea datas from BDD " + e.getClass().getCanonicalName());
		}
		jsonString = "ERROR";
		return jsonString;
	}

	/**
	 * Convert the JSON string in Object and create a request to read (select) all
	 * values in table 'partie_commune' by the name or the stage of 'partie_commune'
	 */
	public String findByName(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		String request;
		CommonArea commonArea;
		List<CommonArea> listCommonArea = new ArrayList<>();
		try {

			Statement st = con.createStatement();
			CommonArea area = objectMapper.readValue(jsonString, CommonArea.class);
			if (!(area.getNameCommonArea().equals("")))
				request = "SELECT * FROM partie_commune where nom_partie_commune LIKE '%" + area.getNameCommonArea().toUpperCase() + "%'";
			else
				request = "SELECT * FROM partie_commune where etage_partie_commune = '" + area.getEtageCommonArea() + "'";
			result = st.executeQuery(request);
			while (result.next()) {
				commonArea = new CommonArea();
				commonArea.setIdCommonArea(Integer.parseInt(result.getObject(1).toString()));
				commonArea.setNameCommonArea(result.getObject(2).toString());
				commonArea.setEtageCommonArea(Integer.parseInt(result.getObject(3).toString()));
				commonArea.setListSensor(null);
				listCommonArea.add(commonArea);
			}
			ObjectMapper obj = new ObjectMapper();
			jsonString = obj.writeValueAsString(listCommonArea);
			logger.log(Level.INFO, "CommonAreas succesfully find in BDD");
			return jsonString;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to get commonArea datas from BDD " + e.getClass().getCanonicalName());
		}
		jsonString = "ERROR";
		return jsonString;
	}
}
