package com.blackmamba.deathkiss.dao;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.blackmamba.deathkiss.connectionpool.DataSource;
import com.blackmamba.deathkiss.connectionpool.JDBCConnectionPool;

public abstract class DAO<T> {

	protected Connection con = null;
	JDBCConnectionPool p;
	Logger logger = Logger.getLogger("logger");

	public DAO() {
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
