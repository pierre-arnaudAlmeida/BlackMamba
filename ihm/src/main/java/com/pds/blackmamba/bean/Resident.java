package com.pds.blackmamba.bean;

public class Resident {

	private int idResident = 0;
	private String lastnameResident = "";
	private String nameResident = "";

	public Resident(int idResident, String lastnameResident, String nameResident) {
		super();
		this.idResident = idResident;
		this.lastnameResident = lastnameResident;
		this.nameResident = nameResident;
	}

	public Resident() {
	}

	public int getIdResident() {
		return idResident;
	}

	public void setIdResident(int idResident) {
		this.idResident = idResident;
	}

	public String getLastnameResident() {
		return lastnameResident;
	}

	public void setLastnameResident(String lastnameResident) {
		this.lastnameResident = lastnameResident;
	}

	public String getNameResident() {
		return nameResident;
	}

	public void setNameResident(String nameResident) {
		this.nameResident = nameResident;
	}

}
