package com.blackmamba.deathkiss.entity;

import java.sql.Time;
import java.util.Date;

import com.blackmamba.deathkiss.pool.entity.AlertState;
import com.blackmamba.deathkiss.pool.entity.Sensitivity;
import com.blackmamba.deathkiss.pool.entity.SensorType;

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
	private Time startActivity = null;
	private Time endActivity = null;
	private int thresholdMin = 0;
	private int thresholdMax = 0;

	public Sensor(int idSensor, SensorType typeSensor, boolean sensorState, int idCommonArea, AlertState alertState,
			Sensitivity maxSensitivity, Time startActivity, Time endActivity, int thresholdMin, int thresholdMax) {
		super();
		this.idSensor = idSensor;
		this.typeSensor = typeSensor;
		this.sensorState = sensorState;
		this.idCommonArea = idCommonArea;
		this.alertState = alertState;
		this.maxSensitivity = maxSensitivity;
		this.startActivity = startActivity;
		this.endActivity = endActivity;
		this.thresholdMin = thresholdMin;
		this.thresholdMax = thresholdMax;
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

	public void setStartActivity(Time startActivity) {
		this.startActivity = startActivity;
	}

	public Date getEndActivity() {
		return endActivity;
	}

	public void setEndActivity(Time endActivity) {
		this.endActivity = endActivity;
	}

	/**
	 * @return the thresholdMin
	 */
	public int getThresholdMin() {
		return thresholdMin;
	}

	/**
	 * @param thresholdMin the thresholdMin to set
	 */
	public void setThresholdMin(int thresholdMin) {
		this.thresholdMin = thresholdMin;
	}

	/**
	 * @return the thresholdMax
	 */
	public int getThresholdMax() {
		return thresholdMax;
	}

	/**
	 * @param thresholdMax the thresholdMax to set
	 */
	public void setThresholdMax(int thresholdMax) {
		this.thresholdMax = thresholdMax;
	}

}
