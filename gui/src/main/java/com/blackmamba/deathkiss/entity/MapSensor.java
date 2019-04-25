package com.blackmamba.deathkiss.entity;

import java.util.ArrayList;
import java.util.List;
import com.blackmamba.deathkiss.entity.Alert;
import com.blackmamba.deathkiss.entity.Sensor;

/**
 * 
 * @author Raymond
 *
 */
public class MapSensor {

	/**
	 * TODO A modifier et finaliser plus tard
	 */

	private int idMapSensor = 0;
	private List<Alert> listAlert = new ArrayList<Alert>();
	private List<Sensor> listSensor = new ArrayList<Sensor>();

	public MapSensor(int idMapSensor, List<Sensor> listSensor, List<Alert> listAlert) {
		super();
		this.idMapSensor = idMapSensor;
		this.listSensor = listSensor;
		this.listAlert = listAlert;
	}

	/**
	 * @return the idMapSensor
	 */
	public int getIdMapSensor() {
		return idMapSensor;
	}

	/**
	 * @param idMapSensor the idMapSensor to set
	 */
	public void setIdMapSensor(int idMapSensor) {
		this.idMapSensor = idMapSensor;
	}

	/**
	 * @return the listAlert
	 */
	public List<Alert> getListAlert() {
		return listAlert;
	}

	/**
	 * @param listAlert the listAlert to set
	 */
	public void setListAlert(List<Alert> listAlert) {
		this.listAlert = listAlert;
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

	public void addSensor(Sensor sensor) {
		this.listSensor.add(sensor);
	}

	public void removeSensor(Sensor sensor) {
		this.listSensor.remove(sensor);
	}
}
