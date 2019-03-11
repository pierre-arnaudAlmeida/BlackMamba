package com.pds.blackmamba.bean;

import java.util.Date;

public class Badger {
	private int id_sensor = 0;
	private int id_resident = 0;
	private Date date_badger = null;

	public Badger() {
	}

	public Badger(int id_sensor, int id_resident, Date date_badger) {
		super();
		this.id_sensor = id_sensor;
		this.id_resident = id_resident;
		this.date_badger = date_badger;
	}

	public int getId_sensor() {
		return id_sensor;
	}

	public void setId_sensor(int id_sensor) {
		this.id_sensor = id_sensor;
	}

	public int getId_resident() {
		return id_resident;
	}

	public void setId_resident(int id_resident) {
		this.id_resident = id_resident;
	}

	public Date getDate_badger() {
		return date_badger;
	}

	public void setDate_badger(Date date_badger) {
		this.date_badger = date_badger;
	}
}
