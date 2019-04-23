package com.blackmamba.deathkiss.mock;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.blackmamba.deathkiss.mock.entity.Message;

public class GenerateMessage extends Thread {

	private boolean bool;
	private int nbMessageGenerate;
	private int threshold;
	private static final Logger logger = LogManager.getLogger(GenerateMessage.class);

	public GenerateMessage(Message message) {
		//TODO Recuperer le message de l'ihm
	}

	public void run() {
		bool = true;
		while (bool) {
			// TODO faire une generation de id capteur a partir de la liste pour chaque
			// message
			System.out.println(threshold);
			nbMessageGenerate++;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				logger.log(Level.INFO,
						"Impossible to sleep the threadGenerateMessage" + e.getClass().getCanonicalName());
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
}
