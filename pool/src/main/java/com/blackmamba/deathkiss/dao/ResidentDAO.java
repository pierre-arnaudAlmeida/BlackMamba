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

import com.blackmamba.deathkiss.entity.Resident;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class ResidentDAO extends DAO<Resident> {

	private ResultSet result = null;
	private static final Logger logger = LogManager.getLogger(ResidentDAO.class);

	public ResidentDAO(Connection con) {
		super(con);
	}

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

	public String findByName(String jsonString) {
		// TODO
		return null;
	}

	public boolean badger(int idResident, int idSensor) {
		// TODO Auto-generated method stub
		// Quand on badge on ajoute une date
		// Dans la table badger
		return false;
	}
}
