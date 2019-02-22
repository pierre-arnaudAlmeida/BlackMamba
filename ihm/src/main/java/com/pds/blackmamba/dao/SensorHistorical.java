package com.pds.blackmamba.dao;

import java.util.Date;

public class SensorHistorical {

	private int id_historical = 0;
	private Date date = null;
	private int id_sensor = 0;
	private int previous_state = 0;
	private int next_state = 0;
	
	public SensorHistorical(int id_historical, Date date, int id_sensor, int previous_state, int next_state) {
		super();
		this.id_historical = id_historical;
		this.date = date;
		this.id_sensor = id_sensor;
		this.previous_state = previous_state;
		this.next_state = next_state;
	}
	
	public SensorHistorical() {}

	public int getId_historical() {
		return id_historical;
	}

	public void setId_historical(int id_historical) {
		this.id_historical = id_historical;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getId_sensor() {
		return id_sensor;
	}

	public void setId_sensor(int id_sensor) {
		this.id_sensor = id_sensor;
	}

	public int getPrevious_state() {
		return previous_state;
	}

	public void setPrevious_state(int previous_state) {
		this.previous_state = previous_state;
	}

	public int getNext_state() {
		return next_state;
	}

	public void setNext_state(int next_state) {
		this.next_state = next_state;
	}
	
}
