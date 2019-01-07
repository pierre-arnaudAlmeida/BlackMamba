package connectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public class DateSource {
	
	public static JDBCConnectionPool pool;
	
	public static void getConnectionFromJDBC(JDBCConnectionPool pool) throws SQLException {
		pool.getConnection();
	}
	
	public static void returnConnection(JDBCConnectionPool pool,Connection connection) {
		pool.free(connection);
	}
	
	public static void closeConnectionsFromJDBC(JDBCConnectionPool pool) {
		pool.closeAllConnections();
	}
}
