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

	/**
	 * Initialization of parameters
	 */
	private CommonArea commonArea;
	private ResultSet result = null;
	private String request;
	private static final Logger logger = LogManager.getLogger(CommonAreaDAO.class);

	/**
	 * Constructor
	 * 
	 * @param con
	 */
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
		try {
			CommonArea area = objectMapper.readValue(jsonString, CommonArea.class);
			request = "insert into partie_commune (nom_partie_commune, etage_partie_commune) values ('"
					+ area.getNameCommonArea() + "','" + area.getEtageCommonArea() + "');";
			Statement st = con.createStatement();
			st.execute(request);
			logger.log(Level.INFO, "CommonArea succesfully inserted in BDD");
			return true;
		} catch (IOException | SQLException e) {
			logger.log(Level.INFO, "Impossible to insert commonArea datas in BDD " + e.getClass().getCanonicalName());
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
		try {
			CommonArea area = objectMapper.readValue(jsonString, CommonArea.class);
			request = "DELETE FROM partie_commune where id_partie_commune = " + area.getIdCommonArea() + ";";
			Statement st = con.createStatement();
			st.execute(request);
			logger.log(Level.INFO, "CommonArea succesfully deleted in BDD");
			return true;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to delete commonArea datas in BDD " + e.getClass().getCanonicalName());
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
		try {
			CommonArea area = objectMapper.readValue(jsonString, CommonArea.class);
			if (area.getNameCommonArea().equals("") && area.getEtageCommonArea() < 99) {
				request = "UPDATE partie_commune SET etage_partie_commune = '" + area.getEtageCommonArea()
						+ "' where id_partie_commune = '" + area.getIdCommonArea() + "';";
			} else if (!(area.getNameCommonArea().equals("")) && area.getEtageCommonArea() >= 99) {
				request = "UPDATE partie_commune SET nom_partie_commune = '" + area.getNameCommonArea()
						+ "' where id_partie_commune = '" + area.getIdCommonArea() + "';";
			} else if (!(area.getNameCommonArea().equals("")) && area.getEtageCommonArea() < 99) {
				request = "UPDATE partie_commune SET etage_partie_commune = '" + area.getEtageCommonArea()
						+ "', nom_partie_commune = '" + area.getNameCommonArea() + "' where id_partie_commune = '"
						+ area.getIdCommonArea() + "';";
			} else
				return false;
			Statement st = con.createStatement();
			st.execute(request);
			logger.log(Level.INFO, "CommonArea succesfully update in BDD");
			return true;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to update commonArea datas in BDD " + e.getClass().getCanonicalName());
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
		try {
			CommonArea area = objectMapper.readValue(jsonString, CommonArea.class);
			request = "SELECT * FROM partie_commune where id_partie_commune='" + area.getIdCommonArea() + "';";
			Statement st = con.createStatement();
			result = st.executeQuery(request);
			result.next();
			convertDatas(result);
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
		List<CommonArea> listCommonArea = new ArrayList<>();
		try {
			request = "SELECT * FROM partie_commune;";
			Statement st = con.createStatement();
			result = st.executeQuery(request);
			while (result.next()) {
				convertDatas(result);
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
		List<CommonArea> listCommonArea = new ArrayList<>();
		try {
			CommonArea area = objectMapper.readValue(jsonString, CommonArea.class);
			if (!(area.getNameCommonArea().equals("")))
				request = "SELECT * FROM partie_commune where nom_partie_commune LIKE '%"
						+ area.getNameCommonArea().toUpperCase() + "%';";
			else
				request = "SELECT * FROM partie_commune where etage_partie_commune = '" + area.getEtageCommonArea()
						+ "';";
			Statement st = con.createStatement();
			result = st.executeQuery(request);
			while (result.next()) {
				convertDatas(result);
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

	public void convertDatas(ResultSet result) throws NumberFormatException, SQLException {
		commonArea = new CommonArea();
		commonArea.setIdCommonArea(Integer.parseInt(result.getObject(1).toString()));
		commonArea.setNameCommonArea(result.getObject(2).toString());
		commonArea.setEtageCommonArea(Integer.parseInt(result.getObject(3).toString()));
		commonArea.setListSensor(null);
	}
}