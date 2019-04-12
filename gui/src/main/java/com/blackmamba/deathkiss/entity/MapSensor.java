package com.blackmamba.deathkiss.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Raymond
 *
 */
public class MapSensor {
	
/**
	A modifier et finaliser plus tard
*/	

	private int idMapSensor = 0;
	//private Message message = null;
	private List<Sensor> listSensor = new ArrayList<Sensor>();
	
	public MapSensor(int idMapSensor, List<Sensor> listSensor) {
		super();
		this.idMapSensor = idMapSensor;
		this.listSensor = listSensor;
	}
	
	
	public int getIdMapSensor() {
		return idMapSensor;
	}

	public void setIdMapSensor(int idMapSensor) {
		this.idMapSensor = idMapSensor;
	}

	public List<Sensor> getListSensor() {
		return listSensor;
	}

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
