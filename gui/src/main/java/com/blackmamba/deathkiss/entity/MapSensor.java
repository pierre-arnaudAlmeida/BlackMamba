package com.blackmamba.deathkiss.entity;

import java.util.ArrayList;
import java.util.List;

import com.blackmamba.deathkiss.pool.entity.Message;
import com.blackmamba.deathkiss.pool.entity.Sensor;

/**
 * 
 * @author Raymond
 *
 */
public class MapSensor {
	
/**
*	TODO A modifier et finaliser plus tard
*/	

	private int idMapSensor = 0;
	private List<Message> listMessage = new ArrayList<Message>();
	private List<Sensor> listSensor = new ArrayList<Sensor>();
	
	public MapSensor(int idMapSensor, List<Sensor> listSensor, List<Message> listMessage) {
		super();
		this.idMapSensor = idMapSensor;
		this.listSensor = listSensor;
		this.listMessage = listMessage;
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
