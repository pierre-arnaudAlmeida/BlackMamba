package com.blackmamba.deathkiss.pool.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.blackmamba.deathkiss.pool.entity.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class EmployeeDAO extends DAO<Employee> {

	/**
	 * Initialization of parameters
	 */
	private Employee employee;
	private ResultSet result = null;
	private String request;
	private static final Logger logger = LogManager.getLogger(EmployeeDAO.class);

	/**
	 * Constructor
	 * 
	 * @param con
	 */
	public EmployeeDAO(Connection con) {
		super(con);
	}

	/**
	 * Convert the JSON string in Object and create a request to insert values in
	 * table 'employee' return a boolean
	 */
	@Override
	public boolean create(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Employee emp = objectMapper.readValue(jsonString, Employee.class);
			request = "insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('" + emp.getLastnameEmployee() + "','" + emp.getNameEmployee() + "','" + emp.getPassword() + "','" + emp.getPoste() + "')";
			PreparedStatement st = con.prepareStatement(request);
			st.execute(request);
			logger.log(Level.INFO, "Employee succesfully inserted in BDD");
			return true;
		} catch (IOException | SQLException e) {
			logger.log(Level.INFO, "Impossible to insert employee datas in BDD" + e.getClass().getCanonicalName());
			return false;
		}
	}

	/**
	 * Convert the JSON string in Object and create a request to delete values in
	 * table 'employee' return a boolean
	 */
	@Override
	public boolean delete(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Employee emp = objectMapper.readValue(jsonString, Employee.class);
			request = "DELETE FROM employee where id_employee = " + emp.getIdEmployee() + ";";
			PreparedStatement st = con.prepareStatement(request);
			st.execute(request);
			logger.log(Level.INFO, "Employee succesfully deleted in BDD");
			return true;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to delete employee datas  in BDD" + e.getClass().getCanonicalName());
			return false;
		}
	}

	/**
	 * Convert the JSON string in Object and create a request to update values in
	 * table 'employee' return a boolean
	 */
	@Override
	public boolean update(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Employee emp = objectMapper.readValue(jsonString, Employee.class);
			if (employee.getPassword().equals("")) {
				request = "UPDATE employee SET prenom_employee = '" + emp.getNameEmployee() + "', nom_employee = '" + emp.getLastnameEmployee() + "',poste = '" + emp.getPoste() + "' where id_employee = " + emp.getIdEmployee();
			} else {
				request = "UPDATE employee SET mot_de_passe = '" + emp.getPassword() + "', nom_employee = '" + emp.getLastnameEmployee() + "', prenom_employee = '" + emp.getNameEmployee() + "' where id_employee = " + emp.getIdEmployee();
			}
			PreparedStatement st = con.prepareStatement(request);
			st.execute(request);
			logger.log(Level.INFO, "Employee succesfully update in BDD");
			return true;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to update employee datas in BDD" + e.getClass().getCanonicalName());
			return false;
		}
	}

	/**
	 * Convert the JSON string in Object and create a request to read (select)
	 * values in table 'employee' with the id and the password return a JSON string
	 */
	public String connection(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Employee emp = objectMapper.readValue(jsonString, Employee.class);
			request = "SELECT * FROM Employee where id_employee='" + emp.getIdEmployee() + "' and mot_de_passe='" + emp.getPassword() + "';";
			PreparedStatement st = con.prepareStatement(request);
			result = st.executeQuery(request);
			result.next();
			convertDatas(result);

			ObjectMapper obj = new ObjectMapper();
			jsonString = obj.writeValueAsString(employee);
			logger.log(Level.INFO, "Employee succesfully recognized in BDD");
			return jsonString;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to get employee datas from BDD " + e.getClass().getCanonicalName());
		}
		jsonString = "ERROR";
		return jsonString;
	}

	/**
	 * Convert the JSON string in Object and create a request to read (select)
	 * values in table 'employee' return a JSON string
	 */
	@Override
	public String read(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Employee emp = objectMapper.readValue(jsonString, Employee.class);
			request = "SELECT * FROM Employee where id_employee='" + emp.getIdEmployee() + "';";
			PreparedStatement st = con.prepareStatement(request);
			result = st.executeQuery(request);
			result.next();
			convertDatas(result);

			ObjectMapper obj = new ObjectMapper();
			jsonString = obj.writeValueAsString(employee);
			logger.log(Level.INFO, "Employee succesfully find in BDD");
			return jsonString;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to get employee datas from BDD " + e.getClass().getCanonicalName());
		}
		jsonString = "ERROR";
		return jsonString;
	}

	/**
	 * Convert the JSON string in Object and create a request to read (select) all
	 * values in table 'employee' return a JSON string
	 */
	@Override
	public String readAll(String jsonString) {
		List<Employee> listEmployee = new ArrayList<>();
		try {
			request = "SELECT * FROM Employee";
			PreparedStatement st = con.prepareStatement(request);
			result = st.executeQuery(request);
			while (result.next()) {
				convertDatas(result);
				listEmployee.add(employee);
			}
			ObjectMapper obj = new ObjectMapper();
			jsonString = obj.writeValueAsString(listEmployee);
			logger.log(Level.INFO, "Employees succesfully find in BDD");
			return jsonString;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to get employee datas from BDD " + e.getClass().getCanonicalName());
		}
		jsonString = "ERROR";
		return jsonString;
	}

	/**
	 * Convert the JSON string in Object and create a request to read (select) all
	 * values in table 'employee' by the name or lastName or function
	 */
	public String findByName(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		List<Employee> listEmployee = new ArrayList<>();
		try {
			Employee emp = objectMapper.readValue(jsonString, Employee.class);
			request = "SELECT * FROM employee where ((nom_employee LIKE '%" + emp.getLastnameEmployee().toUpperCase() + "%') or (prenom_employee LIKE '%" + emp.getLastnameEmployee().toLowerCase() + "%') or (prenom_employee LIKE '%" + emp.getLastnameEmployee().toUpperCase() + "%') or (poste LIKE '%"
					+ emp.getLastnameEmployee().toLowerCase() + "%') or (poste LIKE '%" + emp.getLastnameEmployee().toUpperCase() + "%'))";
			PreparedStatement st = con.prepareStatement(request);
			result = st.executeQuery(request);
			while (result.next()) {
				convertDatas(result);
				listEmployee.add(employee);
			}
			ObjectMapper obj = new ObjectMapper();
			jsonString = obj.writeValueAsString(listEmployee);
			logger.log(Level.INFO, "Employees succesfully find in BDD");
			return jsonString;
		} catch (SQLException | IOException e) {
			logger.log(Level.INFO, "Impossible to get employee datas from BDD " + e.getClass().getCanonicalName());
		}
		jsonString = "ERROR";
		return jsonString;
	}

	public void convertDatas(ResultSet result) throws NumberFormatException, SQLException {
		employee = new Employee();
		employee.setIdEmployee(Integer.parseInt(result.getObject(1).toString()));
		employee.setLastnameEmployee(result.getObject(2).toString());
		employee.setNameEmployee(result.getObject(3).toString());
		employee.setPassword(result.getObject(4).toString());
		employee.setPoste(result.getObject(5).toString());
	}
}