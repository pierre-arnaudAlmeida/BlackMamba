package com.pds.blackmamba.bean;

public class Sensor {

	private int id_sensor = 0;
	private String type_sensor = null;
	private int sensor_state = 0;
	private int id_commonArea = 0;

	public Sensor(int id_sensor, String type_sensor, int sensor_state, int id_commonArea) {
		super();
		this.id_sensor = id_sensor;
		this.type_sensor = type_sensor;
		this.sensor_state = sensor_state;
		this.id_commonArea = id_commonArea;
	}

	public Sensor() {
	}

	public int getId_sensor() {
		return id_sensor;
	}

	public void setId_sensor(int id_sensor) {
		this.id_sensor = id_sensor;
	}

	public String getType_sensor() {
		return type_sensor;
	}

	public void setType_sensor(String type_sensor) {
		this.type_sensor = type_sensor;
	}

	public int getSensor_state() {
		return sensor_state;
	}

	public void setSensor_state(int sensor_state) {
		this.sensor_state = sensor_state;
	}

	public int getId_commonArea() {
		return id_commonArea;
	}

	public void setId_commonArea(int id_commonArea) {
		this.id_commonArea = id_commonArea;
	}

}
