package com.pds.blackmamba.bean;

public class Employee {

	private int id_employee = 0;
	private String lastname_employee = "";
	private String name_employee = "";
	private String password = "";
	private String poste = "";

	public Employee(int id_employee, String lastname_employee, String name_employee, String password, String poste) {
		super();
		this.id_employee = id_employee;
		this.lastname_employee = lastname_employee;
		this.name_employee = name_employee;
		this.password = password;
		this.poste = poste;
	}

	public Employee() {
	}

	public int getId_employee() {
		return id_employee;
	}

	public void setId_employee(int id_employee) {
		this.id_employee = id_employee;
	}

	public String getLastname_employee() {
		return lastname_employee;
	}

	public void setLastname_employee(String lastname_employee) {
		this.lastname_employee = lastname_employee;
	}

	public String getName_employee() {
		return name_employee;
	}

	public void setName_employee(String name_employee) {
		this.name_employee = name_employee;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPoste() {
		return poste;
	}

	public void setPoste(String poste) {
		this.poste = poste;
	}

}
