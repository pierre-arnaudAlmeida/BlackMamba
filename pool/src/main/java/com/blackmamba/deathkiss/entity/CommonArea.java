package com.blackmamba.deathkiss.entity;

import java.util.HashSet;
import java.util.Set;

public class CommonArea {

	private int idCommonArea = 0;
	private String nameCommonArea = "";
	private int etageCommonArea = 0;

	private Set<Sensor> listSensor = new HashSet<Sensor>();

	public CommonArea(int idCommonArea, String nameCommonArea, int etageCommonArea) {
		super();
		this.idCommonArea = idCommonArea;
		this.nameCommonArea = nameCommonArea;
		this.etageCommonArea = etageCommonArea;
	}

	public CommonArea() {
	}

	public int getIdCommonArea() {
		return idCommonArea;
	}

	public void setIdCommonArea(int idCommonArea) {
		this.idCommonArea = idCommonArea;
	}

	public String getNameCommonArea() {
		return nameCommonArea;
	}

	public void setNameCommonArea(String nameCommonArea) {
		this.nameCommonArea = nameCommonArea;
	}

	public int getEtageCommonArea() {
		return etageCommonArea;
	}

	public void setEtageCommonArea(int etageCommonArea) {
		this.etageCommonArea = etageCommonArea;
	}

	public Set<Sensor> getListSensor() {
		return listSensor;
	}

	public void setListSensor(Set<Sensor> listSensor) {
		this.listSensor = listSensor;
	}

	public void addSensor(Sensor sensor) {
		this.listSensor.add(sensor);
	}

	public void removeSensor(Sensor sensor) {
		this.listSensor.remove(sensor);
	}
}
