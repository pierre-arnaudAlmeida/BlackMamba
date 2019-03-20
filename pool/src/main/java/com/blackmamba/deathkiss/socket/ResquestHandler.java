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

public class ResquestHandler implements Runnable{
	private Socket sock;
	private JDBCConnectionPool pool;
	private PrintWriter writer = null;
	private BufferedInputStream reader = null;
	private ResultSet result = null;

	public ResquestHandler(Socket pSock, JDBCConnectionPool pool){
	      this.sock = pSock;
	      this.pool = pool;
	   }

	// Le traitement lancé dans un thread séparé
	public void run() {

		System.err.println("Lancement du traitement de la connexion cliente");

		boolean closeConnexion = false;
		// tant que la connexion est active, on traite les demandes
		while (!sock.isClosed()) {
			try {
				pool = new JDBCConnectionPool(false);
				writer = new PrintWriter(sock.getOutputStream(), true);
				reader = new BufferedInputStream(sock.getInputStream());
			
				Connection con = DataSource.getConnectionFromJDBC(pool);
				Statement st = con.createStatement();
				String sql ="SELECT * FROM Employee";
				result = st.executeQuery(sql);
				result.next();
				String nom = result.getObject(1).toString();
				//System.out.println(nom);
				writer.write(nom);
				writer.flush();
				String a = read();
				if (a.equals("CLOSE")) {
					sock.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} if (sock.isClosed())System.out.println("connection closed");
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
