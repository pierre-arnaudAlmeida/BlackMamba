package com.blackmamba.deathkiss.entity;

import java.util.Date;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class Message {

	private int idMessage = 0;
	private String threshold = null;
	private AlertState alertState = null;
	private int idSensor = 0;
	private Date alertDate = null;

	public Message(int idMessage, String threshold, int idSensor, Date alertDate, AlertState alertState) {
		this.idMessage = idMessage;
		this.threshold = threshold;
		this.idSensor = idSensor;
		this.alertDate = alertDate;
		this.alertState = alertState;

	}

	public Message() {
	}

	public int getIdMessage() {
		return idMessage;
	}

	public void setIdMessage(int idMessage) {
		this.idMessage = idMessage;
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

	public String getThreshold() {
		return threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}

	public AlertState getAlertState() {
		return alertState;
	}

	public void setAlertState(AlertState alertState) {
		this.alertState = alertState;
	}
}
