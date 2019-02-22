package com.pds.blackmamba.dao;

import java.util.HashSet;
import java.util.Set;

public class CommonArea {

	private int id_commonArea = 0;
	private String name_commonArea = "";
	private int etage_commonArea = 0;
	
	private Set<Sensor> listSensor = new HashSet<Sensor>();
	
	public CommonArea(int id_commonArea, String name_commonArea, int etage_commonArea) {
		super();
		this.id_commonArea = id_commonArea;
		this.name_commonArea = name_commonArea;
		this.etage_commonArea = etage_commonArea;
	}
	
	public CommonArea() {}

	public int getId_commonArea() {
		return id_commonArea;
	}

	public void setId_commonArea(int id_commonArea) {
		this.id_commonArea = id_commonArea;
	}

	public String getName_commonArea() {
		return name_commonArea;
	}

	public void setName_commonArea(String name_commonArea) {
		this.name_commonArea = name_commonArea;
	}

	public int getEtage_commonArea() {
		return etage_commonArea;
	}

	public void setEtage_commonArea(int etage_commonArea) {
		this.etage_commonArea = etage_commonArea;
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
