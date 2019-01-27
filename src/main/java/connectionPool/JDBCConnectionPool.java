package connectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class JDBCConnectionPool implements Runnable {

	private String driver;
	private String url;
	private String user;
	private String password;
	private int maxConnection;
	private boolean busy;
	public List<Connection> availableConnections,availableConnectionsArrayList;
	public List<Connection> busyConnections, busyConnectionsArrayList;
	private boolean connectionPending = false;

	final Properties prop = new Properties();

	public JDBCConnectionPool(boolean busy) throws SQLException {
		int initialConnections = 0;

		ResourceBundle rs = ResourceBundle.getBundle("config");

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
		busyConnectionsArrayList = new ArrayList<Connection>();
		busyConnections = Collections.synchronizedList(busyConnectionsArrayList);
		for (int i = 0; i < initialConnections; i++) {
			availableConnections.add(newConnection());
		}
	}

	public synchronized Connection getConnection() throws SQLException {
		if (!availableConnections.isEmpty()) {
			Connection existingConnection = (Connection) availableConnections.get(availableConnections.size()-1);
			int lastIndex = availableConnections.size() - 1;
			availableConnections.remove(lastIndex);
			if (existingConnection.isClosed()) {
				notifyAll();
				return (getConnection());
			} else {
				busyConnections.add(existingConnection);
				return (existingConnection);
			}
		} else {
			if ((totalConnections() < maxConnection) && !connectionPending) {
				backgroundConnection();
			} else if (!busy) {
				throw new SQLException("Connection limit reached");
			}
			try {
				wait();
			} catch (InterruptedException e) {
			}
			return (getConnection());
		}
	}

	private void backgroundConnection() {
		connectionPending = true;
		try {
			Thread connectThread = new Thread(this);
			connectThread.start();
		} catch (OutOfMemoryError e) {

		}
	}

	public void run() {
		try {
			Connection connection = newConnection();
			synchronized (this) {
				availableConnections.add(connection);
				connectionPending = false;
				notifyAll();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Connection newConnection() throws SQLException {
		try {
			Class.forName(driver);
			Connection connection = DriverManager.getConnection(url, user, password);
			return (connection);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("ConnectionPool:: SQLException encountered:: " + e.getMessage());
		}
	}

	public synchronized void free(Connection connection) {
		busyConnections.remove(connection);
		availableConnections.add(connection);
		notifyAll();
	}

	public synchronized int totalConnections() {
		return (availableConnections.size() + busyConnections.size());
	}

	public synchronized void closeAllConnections() {
		closeConnections(availableConnections);
		availableConnections = new ArrayList<Connection>();
		closeConnections(busyConnections);
		busyConnections = new ArrayList<Connection>();
	}

	private void closeConnections(List<Connection> connections) {
		try {
			for (int i = 0; i < connections.size(); i++) {
				Connection connection = (Connection) connections.get(i);
				if (!connection.isClosed()) {
					connection.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
