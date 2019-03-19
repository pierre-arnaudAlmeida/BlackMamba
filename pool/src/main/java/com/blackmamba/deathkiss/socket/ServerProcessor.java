package com.blackmamba.deathkiss.socket;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.blackmamba.deathkiss.connectionpool.DataSource;
import com.blackmamba.deathkiss.connectionpool.JDBCConnectionPool;

public class ServerProcessor implements Runnable{
	private Socket sock;
	private PrintWriter writer = null;
	private BufferedInputStream reader = null;
	private ResultSet result = null;

	public ServerProcessor(Socket pSock){
	      sock = pSock;
	   }

	// Le traitement lancé dans un thread séparé
	public void run() {
		System.err.println("Lancement du traitement de la connexion cliente");

		boolean closeConnexion = false;
		// tant que la connexion est active, on traite les demandes
		while (!sock.isClosed()) {
			JDBCConnectionPool p;
			try {
				p = new JDBCConnectionPool(false);
				Connection con = DataSource.getConnectionFromJDBC(p);
				Statement st = con.createStatement();
				String sql ="SELECT * FROM Employee";
				result = st.executeQuery(sql);
				result.next();
				String nom = result.getObject(1).toString();
				System.out.println(nom);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// La méthode que nous utilisons pour lire les réponses
	private String read() throws IOException {
		String response = "";
		int stream;
		byte[] b = new byte[4096];
		stream = reader.read(b);
		response = new String(b, 0, stream);
		return response;
	}
}
