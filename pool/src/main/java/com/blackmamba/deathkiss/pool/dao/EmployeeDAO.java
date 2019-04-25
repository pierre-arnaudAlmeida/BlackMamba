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
	private ResultSet result = null;
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
		String request;
		try {
			Statement st = con.createStatement();
			Employee employee = objectMapper.readValue(jsonString, Employee.class);
			request = "insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('"
					+ employee.getLastnameEmployee() + "','" + employee.getNameEmployee() + "','"
					+ employee.getPassword() + "','" + employee.getPoste() + "')";
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
		String request;
		try {
			Statement st = con.createStatement();
			Employee employee = objectMapper.readValue(jsonString, Employee.class);
			request = "DELETE FROM employee where id_employee = " + employee.getIdEmployee() + ";";
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
		String request;
		try {
			Statement st = con.createStatement();
			Employee employee = objectMapper.readValue(jsonString, Employee.class);
			if (employee.getPassword().equals("")) {
				request = "UPDATE employee SET prenom_employee = '" + employee.getNameEmployee() + "', nom_employee = '"
						+ employee.getLastnameEmployee() + "',poste = '" + employee.getPoste()
						+ "' where id_employee = " + employee.getIdEmployee();
			} else {
				request = "UPDATE employee SET mot_de_passe = '" + employee.getPassword() + "', nom_employee = '"
						+ employee.getLastnameEmployee() + "', prenom_employee = '" + employee.getNameEmployee()
						+ "' where id_employee = " + employee.getIdEmployee();
			}
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
		String request;
		try {
			Statement st = con.createStatement();
			Employee employee = objectMapper.readValue(jsonString, Employee.class);
			request = "SELECT * FROM Employee where id_employee='" + employee.getIdEmployee() + "';";
			result = st.executeQuery(request);
			result.next();

			employee.setIdEmployee(Integer.parseInt(result.getObject(1).toString()));
			employee.setLastnameEmployee(result.getObject(2).toString());
			employee.setNameEmployee(result.getObject(3).toString());
			employee.setPassword(result.getObject(4).toString());
			employee.setPoste(result.getObject(5).toString());
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
		String request;
		Employee employee;
		List<Employee> listEmployee = new ArrayList<>();
		try {

			Statement st = con.createStatement();
			request = "SELECT * FROM Employee";
			result = st.executeQuery(request);
			while (result.next()) {
				employee = new Employee();
				employee.setIdEmployee(Integer.parseInt(result.getObject(1).toString()));
				employee.setLastnameEmployee(result.getObject(2).toString());
				employee.setNameEmployee(result.getObject(3).toString());
				employee.setPassword(result.getObject(4).toString());
				employee.setPoste(result.getObject(5).toString());
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
		String request;
		Employee employee;
		List<Employee> listEmployee = new ArrayList<>();
		try {

			Statement st = con.createStatement();
			Employee emp = objectMapper.readValue(jsonString, Employee.class);
			request = "SELECT * FROM employee where ((nom_employee LIKE '%" + emp.getLastnameEmployee().toUpperCase()
					+ "%') or (prenom_employee LIKE '%" + emp.getLastnameEmployee().toLowerCase()
					+ "%') or (prenom_employee LIKE '%" + emp.getLastnameEmployee().toUpperCase()
					+ "%') or (poste LIKE '%" + emp.getLastnameEmployee().toLowerCase() + "%') or (poste LIKE '%"
					+ emp.getLastnameEmployee().toUpperCase() + "%'))";
			result = st.executeQuery(request);
			while (result.next()) {
				employee = new Employee();
				employee.setIdEmployee(Integer.parseInt(result.getObject(1).toString()));
				employee.setLastnameEmployee(result.getObject(2).toString());
				employee.setNameEmployee(result.getObject(3).toString());
				employee.setPassword(result.getObject(4).toString());
				employee.setPoste(result.getObject(5).toString());
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
}