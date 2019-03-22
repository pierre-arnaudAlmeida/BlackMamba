package com.blackmamba.deathkiss.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.blackmamba.deathkiss.entity.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EmployeeDAO extends DAO<Employee> {

	private ResultSet result = null;
	private String request;
	private static final Logger logger = LogManager.getLogger(EmployeeDAO.class);

	public EmployeeDAO(Connection con) {
		super(con);
	}

	@Override
	public boolean create(Employee obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Employee obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Employee obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Employee find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public String connection(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		String request;
		try {
			Statement st = con.createStatement();
			Employee employee = objectMapper.readValue(jsonString, Employee.class);
			request = "SELECT * FROM Employee where id_employee='" + employee.getIdEmployee() + "' and mot_de_passe='"+employee.getPassword()+"';";
			result = st.executeQuery(request);
			result.next();
			
			employee.setIdEmployee(Integer.parseInt(result.getObject(1).toString()));
			employee.setLastnameEmployee(result.getObject(2).toString());
			employee.setNameEmployee(result.getObject(3).toString());
			employee.setPassword(result.getObject(4).toString());
			employee.setPoste(result.getObject(5).toString());
			ObjectMapper obj = new ObjectMapper();
			jsonString = obj.writeValueAsString(employee);

			return jsonString;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to get datas from BDD " + e.getClass().getCanonicalName());
		}
		jsonString = "ERROR";
		return jsonString;
	}

}
