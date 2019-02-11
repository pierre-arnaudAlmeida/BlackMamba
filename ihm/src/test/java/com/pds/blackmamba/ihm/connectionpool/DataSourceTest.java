package com.pds.blackmamba.ihm.connectionpool;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.Test;

import com.pds.blackmamba.ihm.prod.connectionpool.DataSource;
import com.pds.blackmamba.ihm.prod.connectionpool.JDBCConnectionPool;

class DataSourceTest {
	
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
		} catch (SQLException e) {
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
			st.execute(sql);
			assertTrue(true);
		} catch (SQLException e) {
			fail("Une erreur SQL est survenue");
		}
	}
	
	/**
	 * Test de récupération des valeurs que l'on viens d'inserer dans la base de données
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
//			rs.next();
//			System.out.println(rs.getString("nom_employee") + ' ' + rs.getString("prenom_employee"));
		} catch (SQLException e) {
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
			st.execute(sql);
			assertTrue(true);
		} catch (SQLException e) {
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
			st.execute(sql);
			assertTrue(true);
		} catch (SQLException e) {
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
		} catch (SQLException e) {
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
		} catch (SQLException e) {
			fail("Impossible de fermer les connections");
		}
	}
}