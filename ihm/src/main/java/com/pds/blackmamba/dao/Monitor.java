package com.pds.blackmamba.dao;

public class Monitor {

	private int id_employee = 0;
	private int id_resident = 0;

	public Monitor(int id_employee, int id_resident) {
		super();
		this.id_employee = id_employee;
		this.id_resident = id_resident;
	}

	public Monitor() {
	}

	public int getId_employee() {
		return id_employee;
	}

	public void setId_employee(int id_employee) {
		this.id_employee = id_employee;
	}

	public int getId_resident() {
		return id_resident;
	}

	public void setId_resident(int id_resident) {
		this.id_resident = id_resident;
	}

}
