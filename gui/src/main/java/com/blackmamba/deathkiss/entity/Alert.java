package com.blackmamba.deathkiss.entity;

public class Alert {

	private int idAlert;
	private int idSensor;
	private AlertState alertState;

	public Alert() {
	}

	public int getIdAlert() {
		return idAlert;
	}

	public void setIdAlert(int idAlert) {
		this.idAlert = idAlert;
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
}
