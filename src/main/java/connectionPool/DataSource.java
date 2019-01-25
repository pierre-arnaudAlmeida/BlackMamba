package connectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class DataSource {
	
	public static JDBCConnectionPool pool;
	
	public static Connection getConnectionFromJDBC(JDBCConnectionPool pool) throws SQLException {
		return pool.getConnection();
	}
	
	public static void returnConnection(JDBCConnectionPool pool,Connection connection) {
		pool.free(connection);
	}
	
	public static void closeConnectionsFromJDBC(JDBCConnectionPool pool) {
		pool.closeAllConnections();
	}
}
