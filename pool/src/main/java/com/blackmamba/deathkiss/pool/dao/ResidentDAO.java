package com.blackmamba.deathkiss.pool.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.blackmamba.deathkiss.pool.entity.Resident;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class ResidentDAO extends DAO<Resident> {

	/**
	 * Initialization of parameters
	 */
	private String request;
	private Resident resident;
	private ResultSet result = null;
	private static final Logger logger = LogManager.getLogger(ResidentDAO.class);

	public ResidentDAO(Connection con) {
		super(con);
	}

	/**
	 * Convert the JSON string in Object and create a request to insert values in
	 * table 'resident' return a boolean
	 */
	@Override
	public boolean create(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Resident resid = objectMapper.readValue(jsonString, Resident.class);
			request = "insert into resident (nom_resident, prenom_resident) values ('" + resid.getLastnameResident() + "','" + resid.getNameResident() + "')";
			PreparedStatement st = con.prepareStatement(request);
			st.execute(request);
			logger.log(Level.INFO, "Resident succesfully inserted in BDD");
			return true;
		} catch (IOException | SQLException e) {
			logger.log(Level.INFO, "Impossible to insert resident datas in BDD" + e.getClass().getCanonicalName());
			return false;
		}
	}

	/**
	 * Convert the JSON string in Object and create a request to delete values in
	 * table 'resident' return a boolean
	 */
	@Override
	public boolean delete(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Resident resid = objectMapper.readValue(jsonString, Resident.class);
			request = "DELETE FROM resident where id_resident = " + resid.getIdResident() + ";";
			PreparedStatement st = con.prepareStatement(request);
			st.execute(request);
			logger.log(Level.INFO, "Resident succesfully deleted in BDD");
			return true;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to delete resident datas in BDD" + e.getClass().getCanonicalName());
			return false;
		}
	}

	/**
	 * Convert the JSON string in Object and create a request to update values in
	 * table 'resident' return a boolean
	 */
	@Override
	public boolean update(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Resident resid = objectMapper.readValue(jsonString, Resident.class);
			if (resid.getLastnameResident().equals("") && !(resid.getNameResident().equals(""))) {
				request = "UPDATE resident SET prenom_resident = '" + resid.getNameResident() + "'";
			} else if (!(resid.getLastnameResident().equals("")) && resid.getNameResident().equals("")) {
				request = "UPDATE resident SET nom_resident = '" + resid.getLastnameResident() + "'";
			} else if (!(resid.getLastnameResident().equals("")) && !(resid.getNameResident().equals(""))) {
				request = "UPDATE resident SET nom_resident = '" + resid.getLastnameResident() + "', prenom_resident = '" + resid.getNameResident() + "'";
			} else
				return false;
			PreparedStatement st = con.prepareStatement(request);
			st.execute(request);
			logger.log(Level.INFO, "Resident succesfully update in BDD");
			return true;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to update resident datas in BDD" + e.getClass().getCanonicalName());
			return false;
		}
	}

	/**
	 * Convert the JSON string in Object and create a request to read (select)
	 * values in table 'resident' return a JSON string
	 */
	@Override
	public String read(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Resident resid = objectMapper.readValue(jsonString, Resident.class);
			request = "SELECT * FROM resident where id_resident='" + resid.getIdResident() + "';";
			PreparedStatement st = con.prepareStatement(request);
			result = st.executeQuery(request);
			result.next();
			convertDatas(result);

			ObjectMapper obj = new ObjectMapper();
			jsonString = obj.writeValueAsString(resident);
			logger.log(Level.INFO, "Resident succesfully find in BDD");
			return jsonString;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to get resident datas from BDD " + e.getClass().getCanonicalName());
		}
		jsonString = "ERROR";
		return jsonString;
	}

	/**
	 * Convert the JSON string in Object and create a request to read (select) all
	 * values in table 'resident' return a JSON string
	 */
	@Override
	public String readAll(String jsonString) {
		List<Resident> listResident = new ArrayList<>();
		try {
			request = "SELECT * FROM resident";
			PreparedStatement st = con.prepareStatement(request);
			result = st.executeQuery(request);
			while (result.next()) {
				convertDatas(result);
				listResident.add(resident);
			}
			ObjectMapper obj = new ObjectMapper();
			jsonString = obj.writeValueAsString(listResident);
			logger.log(Level.INFO, "Residents succesfully find in BDD");
			return jsonString;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to get resident datas from BDD " + e.getClass().getCanonicalName());
		}
		jsonString = "ERROR";
		return jsonString;
	}

	/**
	 * Convert the JSON string in Object and create a request to read (select) all
	 * values in table 'resident' by the name or lastName
	 */
	public String findByName(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		List<Resident> listResident = new ArrayList<>();
		try {
			Resident resid = objectMapper.readValue(jsonString, Resident.class);
			request = "SELECT * FROM resident where ((nom_resident LIKE '%" + resid.getLastnameResident().toUpperCase() + "%') or (prenom_resident LIKE '%" + resid.getLastnameResident().toLowerCase() + "%') or (prenom_resident LIKE '%" + resid.getLastnameResident().toUpperCase() + "%'))";
			PreparedStatement st = con.prepareStatement(request);
			result = st.executeQuery(request);
			while (result.next()) {
				convertDatas(result);
				listResident.add(resident);
			}
			ObjectMapper obj = new ObjectMapper();
			jsonString = obj.writeValueAsString(listResident);
			logger.log(Level.INFO, "Residents succesfully find in BDD");
			return jsonString;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to get residents datas from BDD " + e.getClass().getCanonicalName());
		}
		jsonString = "ERROR";
		return jsonString;
	}

	/**
	 * Insert on table 'badger' when the resident badge in the elevator
	 * 
	 * @param idResident
	 * @param idSensor
	 * @return
	 */
	public boolean badger(int idResident, int idSensor) {
		Date currentDate = new Date();
		Format formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			request = "insert into badger (id_resident, id_capteur, date_badger) values ('" + idResident + "','" + idSensor + "','" + formater.format(currentDate) + "')";
			PreparedStatement st = con.prepareStatement(request);
			st.execute(request);
			logger.log(Level.INFO, "Resident succesfully inserted in BDD");
			return true;
		} catch (SQLException e) {
			logger.log(Level.INFO, "Impossible to insert resident datas in BDD" + e.getClass().getCanonicalName());
			return false;
		}
	}

	public void convertDatas(ResultSet result) throws NumberFormatException, SQLException {
		resident = new Resident();
		resident.setIdResident(Integer.parseInt(result.getObject(1).toString()));
		resident.setLastnameResident(result.getObject(2).toString());
		resident.setNameResident(result.getObject(3).toString());
	}
}