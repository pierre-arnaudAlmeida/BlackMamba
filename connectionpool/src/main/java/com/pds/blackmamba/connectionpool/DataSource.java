package com.pds.blackmamba.connectionpool;

import java.sql.Connection;
import java.sql.SQLException;
import com.pds.blackmamba.connectionpool.JDBCConnectionPool;

public class DataSource {

	public static JDBCConnectionPool pool;

	/**
	 * Get a connection using the method getConnection() from JDBCCOnnectionPool
	 * 
	 * @param pool
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnectionFromJDBC(JDBCConnectionPool pool) throws Exception {
		return pool.getConnection();
	}

	/**
	 * Free a connection using the method free() from JDBCCOnnectionPool
	 * 
	 * @param pool
	 * @param connection
	 */
	public static void returnConnection(JDBCConnectionPool pool, Connection connection) {
		pool.free(connection);
	}

	/**
	 * Close all connection using the method closeAllConnection from JDBCCOnnectionPool
	 * On ferme toutes les connexions en utilisatn la méthode closeAllConnections()
	 * 
	 * @param pool
	 */
	public static void closeConnectionsFromJDBC(JDBCConnectionPool pool) {
		pool.closeAllConnections();
	}
}
