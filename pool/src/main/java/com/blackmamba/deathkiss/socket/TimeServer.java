package com.blackmamba.deathkiss.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.blackmamba.deathkiss.connectionpool.DataSource;
import com.blackmamba.deathkiss.connectionpool.JDBCConnectionPool;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class TimeServer {
	/**
	 * Initialization of parameters
	 */
	private int port = 2345;
	private String host = "127.0.0.1";
	private ServerSocket server = null;
	private boolean isRunning = true;
	private static final Logger logger = LogManager.getLogger(TimeServer.class);
	private JDBCConnectionPool pool;
	private int numberConnection;
	private Connection connectionGived;
	private Socket client;

	/**
	 * Constructor without parameters
	 */
	public TimeServer() {
		try {
			server = new ServerSocket(port, 100, InetAddress.getByName(host));
		} catch (UnknownHostException e) {
			logger.log(Level.INFO, "IP Host dont find " + e.getClass().getCanonicalName());
		} catch (IOException e) {
			logger.log(Level.INFO, "Impossible create the socket" + e.getClass().getCanonicalName());
		}
	}

	/**
	 * Constructor
	 * 
	 * @param Host
	 * @param Port
	 */
	public TimeServer(String pHost, int pPort) {
		this.host = pHost;
		this.port = pPort;
		try {
			server = new ServerSocket(port, 100, InetAddress.getByName(host));
			pool = new JDBCConnectionPool(false);

		} catch (UnknownHostException e) {
			logger.log(Level.INFO, "IP Host dont find " + e.getClass().getCanonicalName());
		} catch (IOException e) {
			logger.log(Level.INFO, "Impossible create the socket " + e.getClass().getCanonicalName());
		} catch (SQLException e) {
			logger.log(Level.INFO, "Acces to COnnectionPool impossible " + e.getClass().getCanonicalName());
		}
	}

	/**
	 * Launch the server Create a thread, and accept the socket if the number of
	 * connection dont was on maximum
	 */
	public void open() {
		Thread th = new Thread(new Runnable() {
			public void run() {
				while (isRunning == true) {
					try {
						client = server.accept();
						connectionGived = DataSource.getConnectionFromJDBC(pool);
						logger.log(Level.INFO, "Client Connection recieved");
						Thread t = new Thread(new RequestHandler(client, connectionGived));
						t.start();
						DataSource.returnConnection(pool, connectionGived);

					} catch (IOException | SQLException e) {
					}
				}
				try {
					server.close();
					logger.log(Level.INFO, "Server closed");
				} catch (IOException e) {
					logger.log(Level.INFO, "Impossible to close server " + e.getClass().getCanonicalName());
					server = null;
				}
			}
		});
		th.start();
	}

	/**
	 * Launch the server Create a thread, and accept the socket and get all the
	 * connection available to block the server and don't let the next users to make
	 * a request
	 */
	public void openFake() {
		numberConnection = 0;
		Thread th = new Thread(new Runnable() {
			public void run() {
				while (isRunning == true) {
					try {
						client = server.accept();
						while (numberConnection < DataSource.getMaxConnectionFromJDBC(pool)) {
							DataSource.getConnectionFromJDBC(pool);
							numberConnection++;
						}
						connectionGived = DataSource.getConnectionFromJDBC(pool);
						logger.log(Level.INFO, "Client Connection recieved");
						Thread t = new Thread(new RequestHandler(client, connectionGived));
						t.start();
						DataSource.returnConnection(pool, connectionGived);

					} catch (IOException | SQLException e) {
					}
				}
				try {
					server.close();
					logger.log(Level.INFO, "Server closed");
				} catch (IOException e) {
					logger.log(Level.INFO, "Impossible to close server " + e.getClass().getCanonicalName());
					server = null;
				}
			}
		});
		th.start();
	}

	/**
	 * Set false the Socket runner to Close the socket
	 */
	public void close() {
		isRunning = false;
	}

	/**
	 * Set true the Socket runner to open the socket
	 */
	public void load() {
		isRunning = true;
	}
}
