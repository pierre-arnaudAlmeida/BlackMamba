package com.blackmamba.deathkiss.dao;

import java.sql.Connection;

/**
 * 
 * @author Pierre-Arnaud
 *
 * @param <T>
 */
public abstract class DAO<T> {

	protected Connection con = null;

	public DAO(Connection connection) {
		this.con = connection;
	}

	/**
	 * Creation method
	 * 
	 * @param obj
	 * @return boolean
	 */
	public abstract boolean create(String jsonString);

	/**
	 * Delete method
	 * 
	 * @param obj
	 * @return boolean
	 */
	public abstract boolean delete(String jsonString);

	/**
	 * Update method
	 * 
	 * @param obj
	 * @return boolean
	 */
	public abstract boolean update(String jsonString);

	/**
	 * Find informations with the id
	 * 
	 * @param obj
	 * @return String
	 */
	public abstract String read(String jsonString);

	/**
	 * Give all lines from table
	 * 
	 * @param jsonString
	 * @return String
	 */
	public abstract String readAll(String jsonString);

}
