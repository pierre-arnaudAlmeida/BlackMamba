package com.blackmamba.deathkiss.dao;

import java.sql.Connection;
import java.util.logging.Logger;

public abstract class DAO<T> {

	protected Connection con = null;
	
	Logger logger = Logger.getLogger("logger");

	public DAO(Connection connection) {
		this.con=connection;
	}

	/**
	 * Creation method
	 * 
	 * @param obj
	 * @return boolean
	 */
	public abstract boolean create(T obj);

	/**
	 * Delete method
	 * 
	 * @param obj
	 * @return boolean
	 */
	public abstract boolean delete(T obj);

	/**
	 * Update method
	 * 
	 * @param obj
	 * @return boolean
	 */
	public abstract boolean update(T obj);

	/**
	 * Find informations with the id method
	 * 
	 * @param obj
	 * @return T
	 */
	public abstract T find(int id);

}
