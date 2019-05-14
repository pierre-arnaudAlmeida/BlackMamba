package com.blackmamba.deathkiss.utils;

import java.util.Comparator;
import com.blackmamba.deathkiss.commons.entity.Resident;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class SortByIdResident implements Comparator<Resident> {
	/**
	 * Compare the id Resident
	 */
	@Override
	public int compare(Resident r1, Resident r2) {
		return r1.getIdResident() - r2.getIdResident();
	}
}
