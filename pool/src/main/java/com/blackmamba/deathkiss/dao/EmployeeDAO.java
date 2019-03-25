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
	private static final Logger logger = LogManager.getLogger(EmployeeDAO.class);

	public EmployeeDAO(Connection con) {
		super(con);
	}

	@Override
	public boolean create(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		String request;
		try {
			Statement st = con.createStatement();
			Employee employee = objectMapper.readValue(jsonString, Employee.class);
			request = "insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('"
					+ employee.getLastnameEmployee() + "','" + employee.getNameEmployee() + "','"
					+ employee.getPassword() + "','" + employee.getPoste() + "')";
			st.execute(request);
			logger.log(Level.INFO, "User succesfully inserted in BDD");
			return true;
		} catch (IOException | SQLException e) {
			logger.log(Level.INFO, "Impossible to insert data in BDD" + e.getClass().getCanonicalName());
			return false;
		}
	}

	@Override
	public boolean delete(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		String request;
		try {
			Statement st = con.createStatement();
			Employee employee = objectMapper.readValue(jsonString, Employee.class);
			request = "DELETE FROM employee where id_employee = "+employee.getIdEmployee()+";";
			st.execute(request);
			logger.log(Level.INFO, "User succesfully deleted in BDD");
			return true;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to delete data in BDD" + e.getClass().getCanonicalName());
			return false;
		}
	}

	@Override
	public boolean update(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		String request;
		try {
			Statement st = con.createStatement();
			Employee employee = objectMapper.readValue(jsonString, Employee.class);
			if (employee.getLastnameEmployee().equals("") && (!employee.getNameEmployee().equals(""))) {
			request = "UPDATE employee SET prenom_employee = '"+ employee.getNameEmployee()+"' where id_employee = "
					+employee.getIdEmployee();
			} else if((!employee.getLastnameEmployee().equals("")) && employee.getNameEmployee().equals("")) {
				request = "UPDATE employee SET nom_employee = '"+ employee.getLastnameEmployee()+"' where id_employee = "
						+employee.getIdEmployee();
			} else if((!employee.getLastnameEmployee().equals("")) && (!employee.getNameEmployee().equals(""))) {
				request = "UPDATE employee SET nom_employee = '"+ employee.getLastnameEmployee()+"',prenom_employee = '"+ employee.getNameEmployee()+"' where id_employee = "
						+employee.getIdEmployee();
			} else return false;
			st.execute(request);
			logger.log(Level.INFO, "User succesfully update in BDD");
			return true;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to update data in BDD" + e.getClass().getCanonicalName());
			return false;
		}
	}

	public String connection(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		String request;
		try {
			Statement st = con.createStatement();
			Employee employee = objectMapper.readValue(jsonString, Employee.class);
			request = "SELECT * FROM Employee where id_employee='" + employee.getIdEmployee() + "' and mot_de_passe='"
					+ employee.getPassword() + "';";
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

	@Override
	public String read(String jsonString) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String readAll(String jsonString) {
		// TODO Auto-generated method stub
		return null;
	}

}
