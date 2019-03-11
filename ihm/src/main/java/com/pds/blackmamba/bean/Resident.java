package com.pds.blackmamba.bean;

public class Resident {

	private int id_resident = 0;
	private String lastname_resident = "";
	private String name_resident = "";

	public Resident(int id_resident, String lastname_resident, String name_resident) {
		super();
		this.id_resident = id_resident;
		this.lastname_resident = lastname_resident;
		this.name_resident = name_resident;
	}

	public Resident() {
	}

	public int getId_resident() {
		return id_resident;
	}

	public void setId_resident(int id_resident) {
		this.id_resident = id_resident;
	}

	public String getLastname_resident() {
		return lastname_resident;
	}

	public void setLastname_resident(String lastname_resident) {
		this.lastname_resident = lastname_resident;
	}

	public String getName_resident() {
		return name_resident;
	}

	public void setName_resident(String name_resident) {
		this.name_resident = name_resident;
	}

}
