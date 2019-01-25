package connectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Vector;


public class JDBCConnectionPool implements Runnable {

	private String driver;
	private String url;
	private String user;
	private String password;
	private int maxConnection;
	private boolean busy;
	public Vector<Connection> availableConnections;
	public Vector<Connection> busyConnections;
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
		availableConnections = new Vector<Connection>(initialConnections);
		busyConnections = new Vector<Connection>();
		for (int i = 0; i < initialConnections; i++) {
			availableConnections.addElement(newConnection());
		}
	}

	public synchronized Connection getConnection() throws SQLException {
		if (!availableConnections.isEmpty()) {
			Connection existingConnection = (Connection) availableConnections.lastElement();
			int lastIndex = availableConnections.size() - 1;
			availableConnections.removeElementAt(lastIndex);
			if (existingConnection.isClosed()) {
				notifyAll();
				return (getConnection());
			} else {
				busyConnections.addElement(existingConnection);
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
				availableConnections.addElement(connection);
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
		busyConnections.removeElement(connection);
		availableConnections.addElement(connection);
		notifyAll();
	}

	public synchronized int totalConnections() {
		return (availableConnections.size() + busyConnections.size());
	}

	public synchronized void closeAllConnections() {
		closeConnections(availableConnections);
		availableConnections = new Vector<Connection>();
		closeConnections(busyConnections);
		busyConnections = new Vector<Connection>();
	}

	private void closeConnections(Vector<Connection> connections) {
		try {
			for (int i = 0; i < connections.size(); i++) {
				Connection connection = (Connection) connections.elementAt(i);
				if (!connection.isClosed()) {
					connection.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
