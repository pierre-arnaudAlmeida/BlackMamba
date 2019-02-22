package com.pds.blackmamba.dao.classes;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pds.blackmamba.ihm.prod.connectionpool.DataSource;
import com.pds.blackmamba.ihm.prod.connectionpool.JDBCConnectionPool;

public abstract class DAO<T> {

	protected Connection con = null;
	JDBCConnectionPool p;
	Logger logger = Logger.getLogger("logger");

	public DAO() {
		try {
			p = new JDBCConnectionPool(false);
			this.con = DataSource.getConnectionFromJDBC(p);
		} catch (Exception e) {
			logger.log(Level.INFO, "SGBD connection is impossible " + e.getClass().getCanonicalName());
		}
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
