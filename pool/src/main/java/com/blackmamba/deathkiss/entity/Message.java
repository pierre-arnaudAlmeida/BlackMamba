package com.blackmamba.deathkiss.entity;

import java.util.Date;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class Message {

	private int idMessage = 0;
	private AlertState alertState = null;
	private int idSensor = 0;
	private Date alertDate = null;
	private String parameter = null;

	public Message(int idMessage, AlertState alertState, int idSensor, Date alertDate, String parameter) {
		this.idMessage = idMessage;
		this.alertState = alertState;
		this.idSensor = idSensor;
		this.alertDate = alertDate;
		this.parameter = parameter;
	}

	public Message() {
	}

	public int getIdMessage() {
		return idMessage;
	}

	public void setIdMessage(int idMessage) {
		this.idMessage = idMessage;
	}

	public AlertState getAlertState() {
		return alertState;
	}

	public void setAlertState(AlertState alertState) {
		this.alertState = alertState;
	}

	public int getIdSensor() {
		return idSensor;
	}

	public void setIdSensor(int idSensor) {
		this.idSensor = idSensor;
	}

	public Date getAlertDate() {
		return alertDate;
	}

	public void setAlertDate(Date alertDate) {
		this.alertDate = alertDate;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

}
