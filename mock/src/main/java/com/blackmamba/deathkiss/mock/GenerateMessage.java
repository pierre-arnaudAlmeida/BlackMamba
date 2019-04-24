package com.blackmamba.deathkiss.mock;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.blackmamba.deathkiss.mock.entity.Message;
import com.blackmamba.deathkiss.mock.entity.Sensor;

public class GenerateMessage extends Thread {

	private boolean bool;
	private int nbMessageGenerate;
	private int threshold;
	private Message message;
	private String request;
	private List<Sensor> listSensor = new ArrayList<Sensor>();
	private static final Logger logger = LogManager.getLogger(GenerateMessage.class);

	public GenerateMessage(Message message, List<Sensor> listSensor, String request) {
		this.message = message;
		this.listSensor = listSensor;
		this.request = request;
	}

	// TODO quand on entre l'id du capteur on envoie un message pour chacun des
	// capteurs mais pour l'id de capteur renseigner on envoie le seuil indiquer
	// pour quand on a pas l'id du capteur on envoie un message pour le type de
	// capteur précis et pour les autres en normal
	// et un radiobutton pour ne pas envoyer de message pour soit l'idcapteur soit
	// pour un type de capteur
	public void run() {
		bool = true;
		while (bool) {
			if (request.equals("ALL")) {
				// TODO
			} else if (request.equals("ONE")) {
				// TODO
			} else if (request.equals("TYPE")) {
				// TODO
			}

			// TODO faire une generation de id capteur a partir de la liste pour chaque
			// message
			System.out.println(message.getIdSensor());
			System.out.println(threshold);
			for (Sensor sen : listSensor) {
				System.out.println(sen.getIdSensor());
			}
			nbMessageGenerate++;
			// TODO constitué le message et utiliser ALERT pour lenvoyer au server pour
			// l'ajouter a la listMessage
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				logger.log(Level.INFO, "Impossible to sleep the threadGenerateMessage" + e.getClass().getCanonicalName());
			}
		}
	}

	/**
	 * @return the nbMessageGenerate
	 */
	public int getNbMessageGenerate() {
		return nbMessageGenerate;
	}

	/**
	 * @param nbMessageGenerate the nbMessageGenerate to set
	 */
	public void setNbMessageGenerate(int nbMessageGenerate) {
		this.nbMessageGenerate = nbMessageGenerate;
	}

	/**
	 * @return the bool
	 */
	public boolean isBool() {
		return bool;
	}

	/**
	 * @param bool the bool to set
	 */
	public void setBool(boolean bool) {
		this.bool = bool;
	}

	/**
	 * @return the threshold
	 */
	public int getThreshold() {
		return threshold;
	}

	/**
	 * @param threshold the threshold to set
	 */
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	/**
	 * @return the message
	 */
	public Message getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(Message message) {
		this.message = message;
	}

	/**
	 * @return the request
	 */
	public String getRequest() {
		return request;
	}

	/**
	 * @param request the request to set
	 */
	public void setRequest(String request) {
		this.request = request;
	}
}
