package com.pds.blackmamba.bean;

public class Report {

	private int id_sensor = 0;
	private int id_employee = 0;
	
	public Report(int id_sensor, int id_employee) {
		super();
		this.id_sensor = id_sensor;
		this.id_employee = id_employee;
	}
	
	public Report(){}

	public int getId_sensor() {
		return id_sensor;
	}

	public void setId_sensor(int id_sensor) {
		this.id_sensor = id_sensor;
	}

	public int getId_employee() {
		return id_employee;
	}

	public void setId_employee(int id_employee) {
		this.id_employee = id_employee;
	}
	
}
