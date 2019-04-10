package com.blackmamba.deathkiss.entity;

import java.util.Date;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class SensorHistorical {

	private int idHistorical = 0;
	private Date date = null;
	private int idSensor = 0;
	private boolean previousState = false;
	private boolean nextState = false;
	private AlertState alertState = null;

	public SensorHistorical(int idHistorical, Date date, int idSensor, boolean previousState, boolean nextState,
			AlertState alertState) {
		super();
		this.idHistorical = idHistorical;
		this.date = date;
		this.idSensor = idSensor;
		this.previousState = previousState;
		this.nextState = nextState;
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

	public boolean getPreviousState() {
		return previousState;
	}

	public void setPreviousState(boolean previousState) {
		this.previousState = previousState;
	}

	public boolean getNextState() {
		return nextState;
	}

	public void setNextState(boolean nextState) {
		this.nextState = nextState;
	}

	public AlertState getAlertState() {
		return alertState;
	}

	public void setAlertState(AlertState alertState) {
		this.alertState = alertState;
	}

}
