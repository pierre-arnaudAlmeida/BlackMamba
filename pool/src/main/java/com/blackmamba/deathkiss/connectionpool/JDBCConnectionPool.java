package com.blackmamba.deathkiss.connectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class JDBCConnectionPool{

	/**
	 *  Definition of diferents fields
	 */
	private static final Logger logger = LogManager.getLogger(JDBCConnectionPool.class);
	private String driver;
	private String url;
	private String user;
	private String password;
	private int maxConnection, nbcon;
	private boolean busy;
	private boolean connectionPending = false;
	final Properties prop = new Properties();

	// Definition of Lists
	private List<Connection> availableConnections, availableConnectionsArrayList;

	/**
	 * Get informations from confirugation.propreties Initialization of fields with
	 * the content of this same file configuration.properties Create two Lists
	 * synchronized Initialization of the available list connection
	 * 
	 * @param busy
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws Exception
	 */
	public JDBCConnectionPool(boolean busy) throws SQLException {
		int initialConnections = 0;

		ResourceBundle rs = ResourceBundle.getBundle("config");
nbcon=0;
		this.driver = rs.getString("db.driver");
		this.url = rs.getString("db.url");
		this.user = rs.getString("db.username");
		this.password = rs.getString("db.password");
		int x = Integer.valueOf(rs.getString("db.maxConnections"));
		this.maxConnection = x;
		int y = Integer.valueOf(rs.getString("db.initialConnections"));
		initialConnections = y;
		this.busy = busy;

		if (initialConnections > maxConnection) {
			initialConnections = maxConnection;
		}
		availableConnectionsArrayList = new ArrayList<Connection>(initialConnections);
		availableConnections = Collections.synchronizedList(availableConnectionsArrayList);
		for (int i = 0; i < initialConnections; i++) {
			availableConnections.add(newConnection());
		}
	}

	/**
	 * Return a connection if is possible, and if the limit of connection are
	 * reached they insert in busy connection list
	 * 
	 * @return return the connection
	 * @throws SQLException
	 */
	public synchronized Connection getConnection() throws SQLException {
		if (!availableConnections.isEmpty()) {
			Connection existingConnection = (Connection) availableConnections.get(availableConnections.size() - 1);
			int lastIndex = availableConnections.size() - 1;
			availableConnections.remove(lastIndex);
			if (existingConnection.isClosed()) {
				nbcon--;
				notifyAll();
				return (getConnection());
			} else {
				nbcon++;
				return (existingConnection);
			}
		} else {
			if ((nbcon < maxConnection)) {
				Connection connection = newConnection();
				synchronized (this) {
					availableConnections.add(connection);
					notifyAll();}
			} else if (!busy) {
				throw new SQLException("Connection limit reached");
			}
			return (getConnection());
		}
	}


	/**
	 * Creation of the connection with the informations from the file @return
	 * Connection @throws
	 * 
	 * @throws ClassNotFoundException
	 */
	private Connection newConnection() throws SQLException {
		Connection connection = null;
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			throw new SQLException("ConnectionPool: SQLException encountered: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			logger.log(Level.ERROR, "Driver JDBC inconnu", e.getClass().getCanonicalName());
		}
		return (connection);
	}

	/**
	 * Add a connection from the busy connection list to available connection list
	 * and remove the connection from the busy connection list
	 * 
	 * @param connection
	 */
	public synchronized void free(Connection connection) {
		availableConnections.add(connection);
		nbcon--;
		notifyAll();
	}

	/**
	 * Return the total number of available connection list and busy connection list
	 * 
	 * @return
	 */
	public synchronized int getTotalConnections() {
		return (availableConnections.size());
	}

	/**
	 * CLose all connection of the available connection list and busy connection
	 * list
	 */
	public synchronized void closeAllConnections() {
		closeConnections(availableConnections);
		availableConnections = new ArrayList<Connection>();
		nbcon=0;
	}

	/**
	 * Close all connection of the list
	 * 
	 * @param List of connections
	 */
	private void closeConnections(List<Connection> connections) {
		try {
			for (int i = 0; i < connections.size(); i++) {
				Connection connection = (Connection) connections.get(i);
				if (!connection.isClosed()) {
					connection.close();
				}
			}
		} catch (SQLException e) {
			logger.log(Level.INFO, e.getClass().getCanonicalName());
		}
	}
}