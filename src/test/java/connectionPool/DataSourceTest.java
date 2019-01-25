package connectionPool;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.Test;

class DataSourceTest {

	@Test
	void testGetConnectionFromJDBC() {
		JDBCConnectionPool p;
		try {
			p = new JDBCConnectionPool(false);
			Connection con = DataSource.getConnectionFromJDBC(p);
			assertNotNull(con);
		} catch (SQLException e) {
			fail("Impossible de se connecter au SGBD");
		}
	}
	
	void testExecuteQuery() {
		JDBCConnectionPool p;
		try {
			p = new JDBCConnectionPool(false);
			Connection con = DataSource.getConnectionFromJDBC(p);
			Statement st = con.createStatement();
			String sql = "select * from employeee";
			ResultSet rs = st.executeQuery(sql);
			assertNotNull(rs);
//			rs.next();
//			System.out.println(rs.getString("nom_employee") + ' ' + rs.getString("prenom_employee"));
		} catch (SQLException e) {
			fail("Une erreur SQL est survenue");
		}

	}

	@Test
	void testReturnConnection() {
	}

	@Test
	void testCloseConnectionsFromJDBC() {

	}

}
