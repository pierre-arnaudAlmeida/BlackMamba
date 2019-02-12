package com.pds.blackmamba.ihm.prod.connectionpool;

import java.sql.Connection;
import java.sql.SQLException;

import com.pds.blackmamba.ihm.prod.connectionpool.JDBCConnectionPool;

public class DataSource {

	public static JDBCConnectionPool pool;
	
	/**
	 * On récupère une connexion par le biais de la méthode getConnection()
	 * définit dans JDBCConnectionPool
	 * @param pool
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnectionFromJDBC(JDBCConnectionPool pool) throws Exception {
		return pool.getConnection();
	}
	
	/**
	 * On libère une connexion et on la remplace par une connexion en attente
	 * en utilisant la méthode free() définit dans JDBCConnectionPool
	 * @param pool
	 * @param connection
	 */
	public static void returnConnection(JDBCConnectionPool pool, Connection connection) {
		pool.free(connection);
	}

	/**
	 * On ferme toutes les connexions en utilisatn la méthode closeAllConnections() définit dans JDBCConnectionPool
	 * @param pool
	 */
	public static void closeConnectionsFromJDBC(JDBCConnectionPool pool) {
		pool.closeAllConnections();
	}
}
