package com.blackmamba.deathkiss.entity;

import java.util.Date;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class Message {

	private int idMessage = 0;
	private int threshold = 0;
	private int idSensor = 0;
	private Date alertDate = null;

	public Message(int idMessage, int threshold, int idSensor, Date alertDate, AlertState alertState) {
		this.idMessage = idMessage;
		this.threshold = threshold;
		this.idSensor = idSensor;
		this.alertDate = alertDate;

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

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
}
