package com.blackmamba.deathkiss.pool.entity;

import java.util.ArrayList;
import java.util.List;

import com.blackmamba.deathkiss.pool.entity.Sensor;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class CommonArea {

	private int idCommonArea = 0;
	private String nameCommonArea = "";
	private int etageCommonArea = 0;
	private List<Sensor> listSensor = new ArrayList<Sensor>();

	public CommonArea(int idCommonArea, String nameCommonArea, int etageCommonArea) {
		super();
		this.idCommonArea = idCommonArea;
		this.nameCommonArea = nameCommonArea;
		this.etageCommonArea = etageCommonArea;
	}

	public CommonArea() {
	}

	/**
	 * @return the idCommonArea
	 */
	public int getIdCommonArea() {
		return idCommonArea;
	}

	/**
	 * @param idCommonArea the idCommonArea to set
	 */
	public void setIdCommonArea(int idCommonArea) {
		this.idCommonArea = idCommonArea;
	}

	/**
	 * @return the nameCommonArea
	 */
	public String getNameCommonArea() {
		return nameCommonArea;
	}

	/**
	 * @param nameCommonArea the nameCommonArea to set
	 */
	public void setNameCommonArea(String nameCommonArea) {
		this.nameCommonArea = nameCommonArea;
	}

	/**
	 * @return the etageCommonArea
	 */
	public int getEtageCommonArea() {
		return etageCommonArea;
	}

	/**
	 * @param etageCommonArea the etageCommonArea to set
	 */
	public void setEtageCommonArea(int etageCommonArea) {
		this.etageCommonArea = etageCommonArea;
	}

	/**
	 * @return the listSensor
	 */
	public List<Sensor> getListSensor() {
		return listSensor;
	}

	/**
	 * @param listSensor the listSensor to set
	 */
	public void setListSensor(List<Sensor> listSensor) {
		this.listSensor = listSensor;
	}

	/**
	 * @param Sensor the listSensor to add
	 */
	public void addSensor(Sensor sensor) {
		this.listSensor.add(sensor);
	}

	/**
	 * @param Sensor the listSensor to add
	 */
	public void removeSensor(Sensor sensor) {
		this.listSensor.remove(sensor);
	}

}
