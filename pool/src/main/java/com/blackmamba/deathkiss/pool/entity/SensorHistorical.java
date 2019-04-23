package com.blackmamba.deathkiss.pool.entity;

import java.util.Date;

import com.blackmamba.deathkiss.pool.entity.AlertState;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class SensorHistorical {

	private int idHistorical = 0;
	private Date date = null;
	private int idSensor = 0;
	private boolean sensorState = false;
	private AlertState alertState = null;

	public SensorHistorical(int idHistorical, Date date, int idSensor, boolean sensorState, AlertState alertState) {
		super();
		this.idHistorical = idHistorical;
		this.date = date;
		this.idSensor = idSensor;
		this.sensorState = sensorState;
		this.alertState = alertState;
	}

	public SensorHistorical() {
	}

	public int getIdHistorical() {
		return idHistorical;
	}

	public void setIdHistorical(int idHistorical) {
		this.idHistorical = idHistorical;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getIdSensor() {
		return idSensor;
	}

	public void setIdSensor(int idSensor) {
		this.idSensor = idSensor;
	}

	public AlertState getAlertState() {
		return alertState;
	}

	public void setAlertState(AlertState alertState) {
		this.alertState = alertState;
	}

	public boolean getSensorState() {
		return sensorState;
	}

	public void setSensorState(boolean sensorState) {
		this.sensorState = sensorState;
	}

}
