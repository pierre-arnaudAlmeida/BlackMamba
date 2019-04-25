package com.blackmamba.deathkiss.pool.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		String request;
		try {
			Statement st = con.createStatement();
			Resident resident = objectMapper.readValue(jsonString, Resident.class);
			request = "insert into resident (nom_resident, prenom_resident) values ('" + resident.getLastnameResident() + "','" + resident.getNameResident() + "')";
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
		String request;
		try {
			Statement st = con.createStatement();
			Resident resident = objectMapper.readValue(jsonString, Resident.class);
			request = "DELETE FROM resident where id_resident = " + resident.getIdResident() + ";";
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
		String request;
		try {
			Statement st = con.createStatement();
			Resident resident = objectMapper.readValue(jsonString, Resident.class);
			if (resident.getLastnameResident().equals("") && !(resident.getNameResident().equals(""))) {
				request = "UPDATE resident SET prenom_resident = '" + resident.getNameResident() + "'";
			} else if (!(resident.getLastnameResident().equals("")) && resident.getNameResident().equals("")) {
				request = "UPDATE resident SET nom_resident = '" + resident.getLastnameResident() + "'";
			} else if (!(resident.getLastnameResident().equals("")) && !(resident.getNameResident().equals(""))) {
				request = "UPDATE resident SET nom_resident = '" + resident.getLastnameResident() + "', prenom_resident = '" + resident.getNameResident() + "'";
			} else
				return false;
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
		String request;
		try {
			Statement st = con.createStatement();
			Resident resident = objectMapper.readValue(jsonString, Resident.class);
			request = "SELECT * FROM resident where id_resident='" + resident.getIdResident() + "';";
			result = st.executeQuery(request);
			result.next();

			resident.setIdResident(Integer.parseInt(result.getObject(1).toString()));
			resident.setLastnameResident(result.getObject(2).toString());
			resident.setNameResident(result.getObject(3).toString());

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
		String request;
		Resident resident;
		List<Resident> listResident = new ArrayList<>();
		try {

			Statement st = con.createStatement();
			request = "SELECT * FROM resident";
			result = st.executeQuery(request);
			while (result.next()) {
				resident = new Resident();
				resident.setIdResident(Integer.parseInt(result.getObject(1).toString()));
				resident.setLastnameResident(result.getObject(2).toString());
				resident.setNameResident(result.getObject(3).toString());
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
		String request;
		Resident resident;
		List<Resident> listResident = new ArrayList<>();
		try {
			Statement st = con.createStatement();
			Resident resid = objectMapper.readValue(jsonString, Resident.class);
			request = "SELECT * FROM resident where ((nom_resident LIKE '%" + resid.getLastnameResident().toUpperCase() + "%') or (prenom_resident LIKE '%" + resid.getLastnameResident().toLowerCase() + "%') or (prenom_resident LIKE '%" + resid.getLastnameResident().toUpperCase()
					+ "%') or (poste LIKE '%" + resid.getLastnameResident().toLowerCase() + "%') or (poste LIKE '%" + resid.getLastnameResident().toUpperCase() + "%'))";
			result = st.executeQuery(request);
			while (result.next()) {
				resident = new Resident();
				resident.setIdResident(Integer.parseInt(result.getObject(1).toString()));
				resident.setLastnameResident(result.getObject(2).toString());
				resident.setNameResident(result.getObject(3).toString());
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
		String request;
		Date currentDate = new Date();
		Format formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Statement st = con.createStatement();
			request = "insert into badger (id_resident, id_capteur, date_badger) values ('" + idResident + "','" + idSensor + "','" + formater.format(currentDate) + "')";
			st.execute(request);
			logger.log(Level.INFO, "Resident succesfully inserted in BDD");
			return true;
		} catch (SQLException e) {
			logger.log(Level.INFO, "Impossible to insert resident datas in BDD" + e.getClass().getCanonicalName());
			return false;
		}
	}
}