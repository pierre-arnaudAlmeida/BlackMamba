package com.blackmamba.deathkiss.socket;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.blackmamba.deathkiss.connectionpool.DataSource;
import com.blackmamba.deathkiss.connectionpool.JDBCConnectionPool;

public class RequestHandler implements Runnable {
	/**
	 * Initialization of diferents parameters
	 */
	private Socket sock;
	private JDBCConnectionPool pool;
	private PrintWriter writer = null;
	private BufferedInputStream reader = null;
	private ResultSet result = null;
	private static final Logger logger = LogManager.getLogger(RequestHandler.class);

	public RequestHandler(Socket pSock, JDBCConnectionPool pool) {
		this.sock = pSock;
		this.pool = pool;
	}

	/**
	 * Run the Thread
	 * while the client connection is active they loop
	 */
	public void run() {
		logger.log(Level.INFO, "Launch of treatement of client connection");
		while (!sock.isClosed()) {
			try {
				pool = new JDBCConnectionPool(false);
				writer = new PrintWriter(sock.getOutputStream(), true);
				reader = new BufferedInputStream(sock.getInputStream());

				Connection con = DataSource.getConnectionFromJDBC(pool);
				Statement st = con.createStatement();
				String sql = "SELECT * FROM Employee";
				result = st.executeQuery(sql);
				result.next();
				String nom = result.getObject(1).toString();
				writer.write(nom);
				writer.flush();
				String a = read();
				if (a.equals("CLOSE")) {
					sock.close();
				}
			} catch (SQLException | IOException e) {
				logger.log(Level.INFO, "Impossible to execute the request " + e.getClass().getCanonicalName());
			}
		}
		if (sock.isClosed())
			logger.log(Level.INFO, "Connection Closed");
	}

	/**
	 * Read the diferent response
	 */
	private String read() throws IOException {
		String response = "";
		int stream;
		byte[] b = new byte[4096];
		stream = reader.read(b);
		response = new String(b, 0, stream);
		return response;
	}
}
