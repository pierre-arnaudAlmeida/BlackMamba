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
	
	public static void main(String[] args) throws SQLException {
		JDBCConnectionPool p = new JDBCConnectionPool(false);
		try {
			//getConnectionFromJDBC(p);
			Connection con = getConnectionFromJDBC(p); //p.getConnection();
			Statement st = con.createStatement();
			String sql = "select * from employeee";
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			System.out.println(rs.getString("nom_employee")+' '+rs.getString("prenom_employee"));
		} catch (SQLException e) {
			System.out.println("Requete impossible");
		}
	}
}
