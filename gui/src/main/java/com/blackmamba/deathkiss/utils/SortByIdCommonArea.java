package com.blackmamba.deathkiss.utils;

import java.util.Comparator;
import com.blackmamba.deathkiss.commons.entity.CommonArea;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class SortByIdCommonArea implements Comparator<CommonArea> {
	/**
	 * Compare the id CommonArea
	 */
	@Override
	public int compare(CommonArea cA1, CommonArea cA2) {
		return cA1.getIdCommonArea() - cA2.getIdCommonArea();
	}
}
