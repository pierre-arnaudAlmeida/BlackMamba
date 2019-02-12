package connectionpool;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import com.pds.blackmamba.connectionpool.DataSource;
import com.pds.blackmamba.connectionpool.JDBCConnectionPool;

class DataSourceTest {
	Logger logger = Logger.getLogger("logger");

	/**
	 * Test d'une connexion à la base données
	 */
	@Test
	void testGetConnectionFromJDBC() {
		JDBCConnectionPool p;
		try {
			p = new JDBCConnectionPool(false);
			Connection con = DataSource.getConnectionFromJDBC(p);
			assertNotNull(con);
		} catch (Exception e) {
			fail("Impossible de se connecter au SGBD");
		}
	}

	/**
	 * Test d'insertion de valeurs dans la base de données
	 */
	@Test
	void testInsertData() {
		JDBCConnectionPool p;
		try {
			p = new JDBCConnectionPool(false);
			Connection con = DataSource.getConnectionFromJDBC(p);
			Statement st = con.createStatement();
			String sql = "insert into employee (nom_employee, prenom_employee, mot_de_passe) values ('keita','raymond','test')";
			assertNotNull(st.execute(sql));
		} catch (Exception e) {
			fail("Une erreur SQL est survenue");
		}
	}

	/**
	 * Test de récupération des valeurs que l'on viens d'inserer dans la base de
	 * données
	 */
	@Test
	void testGetData() {
		JDBCConnectionPool p;
		try {
			p = new JDBCConnectionPool(false);
			Connection con = DataSource.getConnectionFromJDBC(p);
			Statement st = con.createStatement();
			String sql = "select * from employee";
			ResultSet rs = st.executeQuery(sql);
			assertNotNull(rs);
		} catch (Exception e) {
			fail("Une erreur SQL est survenue");
		}
	}

	/**
	 * Test de mise a jour des valeurs de la base de données
	 */
	@Test
	void testUpdateData() {
		JDBCConnectionPool p;
		try {
			p = new JDBCConnectionPool(false);
			Connection con = DataSource.getConnectionFromJDBC(p);
			Statement st = con.createStatement();
			String sql = "update employee set prenom_employee = 'arnaud'";
			assertNotNull(st.execute(sql));
		} catch (Exception e) {
			fail("Une erreur SQL est survenue");
		}
	}

	/**
	 * Test de suppression des valeurs que l'on viens d'inserer dans la base données
	 */
	@Test
	void testDeleteData() {
		JDBCConnectionPool p;
		try {
			p = new JDBCConnectionPool(false);
			Connection con = DataSource.getConnectionFromJDBC(p);
			Statement st = con.createStatement();
			String sql = "delete from employee where nom_employee = 'keita'";
			assertNotNull(st.execute(sql));
		} catch (Exception e) {
			fail("Une erreur SQL est survenue");
		}
	}

	/**
	 * Test de libération de la connexion utiliser
	 */
	@Test
	void testReturnConnection() {
		JDBCConnectionPool p;
		try {
			p = new JDBCConnectionPool(false);
			Connection con = DataSource.getConnectionFromJDBC(p);
			DataSource.returnConnection(p, con);
			assertTrue(true);
		} catch (Exception e) {
			fail("Impossible de libérer la connection");
		}
	}

	/**
	 * Test de fermeture de l'ensemble des connexions
	 */
	@Test
	void testCloseConnectionsFromJDBC() {
		JDBCConnectionPool p;
		try {
			p = new JDBCConnectionPool(false);
			DataSource.getConnectionFromJDBC(p);
			DataSource.closeConnectionsFromJDBC(p);
			assertTrue(true);
		} catch (Exception e) {
			fail("Impossible de fermer les connections");
		}
	}

	@Test
	void testMaxConnections() {
		JDBCConnectionPool p;
		try {
			p = new JDBCConnectionPool(false);
			Connection con = DataSource.getConnectionFromJDBC(p);
			assertNotNull(con);
			//assertNotEqual();
			logger.log(Level.INFO, "Connection 1 effectuer");
			Connection con2 = DataSource.getConnectionFromJDBC(p);
			assertNotNull(con2);
			logger.log(Level.INFO, "Connection 2 effectuer");
			Connection con3 = DataSource.getConnectionFromJDBC(p);
			assertNotNull(con3);
			logger.log(Level.INFO, "Connection 3 effectuer");
			Connection con4 = DataSource.getConnectionFromJDBC(p);
			logger.log(Level.INFO, "Connection 4 effectuer");
			
		} catch (Exception e) {
			logger.log(Level.INFO, "Limite atteinte, aucune connexion disponnible "+e.getClass().getCanonicalName());
		}
	}
		
	@Test
	void testMaxConnections2() {
		JDBCConnectionPool p;
		boolean bool = true;
		int nbconnexionscreated = 0;
		try {
			p = new JDBCConnectionPool(false);
			while(bool) {
				nbconnexionscreated++;
				Connection con = DataSource.getConnectionFromJDBC(p);
				logger.log(Level.INFO, "Connection "+nbconnexionscreated+" effectuer");
			}
		} catch (Exception e) {
			logger.log(Level.INFO, "Limite atteinte, aucune connexion disponnible "+e.getClass().getCanonicalName());
		}
	}
}