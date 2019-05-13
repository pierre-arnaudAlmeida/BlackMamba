package com.blackmamba.deathkiss.utils;

import java.util.Comparator;
import com.blackmamba.deathkiss.entity.Sensor;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class SortByIdSensor implements Comparator<Sensor> {
	/**
	 * Compare the id Sensor
	 */
	@Override
	public int compare(Sensor s1, Sensor s2) {
		return s1.getIdSensor() - s2.getIdSensor();
	}
}
