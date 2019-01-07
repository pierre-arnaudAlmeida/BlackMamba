package connectionPool;

import java.sql.SQLException;

public class DateSource {
	
	public static JDBCConnectionPool pool;
	
	public static void getConnectionFromJDBC(JDBCConnectionPool pool) throws SQLException {
		pool.getConnection();
	}
	
	public static void returnConnection() {
		
	}
	
	public static void closeConnectionsFromJDBC(JDBCConnectionPool pool) {
		pool.closeAllConnections();
	}
}
