package com.blackmamba.deathkiss.utils;

import java.util.Comparator;
import com.blackmamba.deathkiss.entity.Employee;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class SortByIdEmployee implements Comparator<Employee> {
	/**
	 * Compare the id Employee
	 */
	@Override
	public int compare(Employee e1, Employee e2) {
		return e1.getIdEmployee() - e2.getIdEmployee();
	}
}
