package com.blackmamba.deathkiss.entity;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class Sensor {

	private int idSensor = 0;
	private SensorType typeSensor = null;
	private boolean sensorState = false;
	private boolean sensorNextState = false;
	private int idCommonArea = 0;

	public Sensor(int idSensor, SensorType typeSensor, boolean sensorState, int idCommonArea) {
		super();
		this.idSensor = idSensor;
		this.typeSensor = typeSensor;
		this.sensorState = sensorState;
		this.idCommonArea = idCommonArea;
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

	public boolean getSensorNextState() {
		return sensorNextState;
	}

	public void setSensorNextState(boolean sensorNextState) {
		this.sensorNextState = sensorNextState;
	}

}
