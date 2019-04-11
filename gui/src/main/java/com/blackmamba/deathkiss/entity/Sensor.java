package com.blackmamba.deathkiss.entity;

import java.util.Date;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class Sensor {

	private int idSensor = 0;
	private SensorType typeSensor = null;
	private boolean sensorState = false;
	private int idCommonArea = 0;
	private AlertState alertState = null;
	private Sensitivity maxSensitivity = null;
	private Date startActivity = null;
	private Date endActivity = null;
	private String parameter = null;

	public Sensor(int idSensor, SensorType typeSensor, boolean sensorState, int idCommonArea, AlertState alertState,
			Sensitivity maxSensitivity, Date startActivity, Date endActivity, String parameter) {
		super();
		this.idSensor = idSensor;
		this.typeSensor = typeSensor;
		this.sensorState = sensorState;
		this.idCommonArea = idCommonArea;
		this.alertState = alertState;
		this.maxSensitivity = maxSensitivity;
		this.startActivity = startActivity;
		this.endActivity = endActivity;
		this.parameter = parameter;
	}

	public Sensor() {
	}

	public int getIdSensor() {
		return idSensor;
	}

	public void setIdSensor(int idSensor) {
		this.idSensor = idSensor;
	}

	public SensorType getTypeSensor() {
		return typeSensor;
	}

	public void setTypeSensor(SensorType typeSensor) {
		this.typeSensor = typeSensor;
	}

	public boolean getSensorState() {
		return sensorState;
	}

	public void setSensorState(boolean sensorState) {
		this.sensorState = sensorState;
	}

	public int getIdCommonArea() {
		return idCommonArea;
	}

	public void setIdCommonArea(int idCommonArea) {
		this.idCommonArea = idCommonArea;
	}

	public AlertState getAlertState() {
		return alertState;
	}

	public void setAlertState(AlertState alertState) {
		this.alertState = alertState;
	}

	public Sensitivity getSensitivity() {
		return maxSensitivity;
	}

	public void setSensitivity(Sensitivity sensitivity) {
		this.maxSensitivity = sensitivity;
	}

	public Date getStartActivity() {
		return startActivity;
	}

	public void setStartActivity(Date startActivity) {
		this.startActivity = startActivity;
	}

	public Date getEndActivity() {
		return endActivity;
	}

	public void setEndActivity(Date endActivity) {
		this.endActivity = endActivity;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

}
