package connectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

public class JDBCConnectionPool {

	private String driver;
	private String url;
	private String user;
	private String password;
	private int maxConnection;
	public Vector<Connection> connexions;

//constructeur fait
	public JDBCConnectionPool() {
	}

//fait
	public JDBCConnectionPool(String driver, String url, String username, String password, int maxConnections)
			throws SQLException {
		this.driver = driver;
		this.url = url;
		this.user = username;
		this.password = password;
		this.maxConnection = maxConnections;
		connexions = new Vector<Connection>(maxConnection);
		for (int i = 0; i < maxConnection; i++) {
			connexions.addElement(newConnection());
		}
	}

//fait
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

	public synchronized Connection getConnection() throws SQLException {
		
	}

	// fait
	public synchronized void closeAllConnections(Vector<Connection> connections) {
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
		connexions = new Vector<Connection>();
	}

}
