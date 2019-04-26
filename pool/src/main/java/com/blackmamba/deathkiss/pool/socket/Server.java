package com.blackmamba.deathkiss.pool.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.blackmamba.deathkiss.pool.connectionpool.DataSource;
import com.blackmamba.deathkiss.pool.connectionpool.JDBCConnectionPool;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class Server {

	/**
	 * Initialization of parameters
	 */
	private int port;
	private int numberConnection;
	private String host;
	private ServerSocket server = null;
	private boolean isRunning = true;
	private JDBCConnectionPool pool;
	private Connection connectionGived;
	private Socket client;
	private MonitoringAlert monitoringAlert;
	private static final Logger logger = LogManager.getLogger(Server.class);
	private ResourceBundle rsAlert = ResourceBundle.getBundle("alert");
	private ResourceBundle rsConfig = ResourceBundle.getBundle("config");

	/**
	 * Constructor without parameters
	 */
	public Server() {
		try {
			server = new ServerSocket(Integer.parseInt(rsConfig.getString("server.default.port")), 100, InetAddress.getByName(rsConfig.getString("server.default.host")));
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
	public Server(String pHost, int pPort) {
		this.host = pHost;
		this.port = pPort;
		try {
			server = new ServerSocket(port, 100, InetAddress.getByName(host));
			pool = new JDBCConnectionPool(false);
			monitoringAlert = new MonitoringAlert(pool);

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
	 * connection don't was on maximum
	 */
	public void open() {
		Thread threadServer = new Thread(new Runnable() {
			public void run() {
				while (isRunning) {
					try {
						client = server.accept();
						connectionGived = DataSource.getConnectionFromJDBC(pool);
						logger.log(Level.INFO, "Client Connection recieved");
						Thread threadRequestHandler = new Thread(new RequestHandler(client, connectionGived, monitoringAlert));
						threadRequestHandler.start();
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
		threadServer.start();
	}

	/**
	 * Launch the server Create a thread, and accept the socket and get all the
	 * connection available to block the server and don't let the next users to make
	 * a request
	 */
	public void openFake() {
		numberConnection = 0;
		Thread threadFakeServer = new Thread(new Runnable() {
			public void run() {
				while (isRunning) {
					try {
						client = server.accept();
						while (numberConnection < DataSource.getMaxConnectionFromJDBC(pool)) {
							DataSource.getConnectionFromJDBC(pool);
							numberConnection++;
						}
						connectionGived = DataSource.getConnectionFromJDBC(pool);
						logger.log(Level.INFO, "Client Connection recieved");
						Thread threadRequestHandler = new Thread(new RequestHandler(client, connectionGived, monitoringAlert));
						threadRequestHandler.start();
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
		threadFakeServer.start();
	}

	/**
	 * Launch the execution of the different method to be execute during the
	 * activity of the server
	 */
	public void treatment() {
		Thread threadAlertTreatment = new Thread(new Runnable() {
			public void run() {
				while (true) {
					monitoringAlert.alertTreatment();
					try {
						Thread.sleep(Integer.parseInt(rsAlert.getString("time_alertTreatment")));
					} catch (InterruptedException e) {
						logger.log(Level.INFO, "Impossible to sleep the threadAlertTreatment " + e.getClass().getCanonicalName());
					}
				}
			}
		});

		Thread threadVerifySensorMessageBeforeActivity = new Thread(new Runnable() {
			public void run() {
				while (true) {
					monitoringAlert.verifySensorMessageBeforeActivity();
					try {
						Thread.sleep(Integer.parseInt(rsAlert.getString("time_verifyMessageBeforeActivity")));
					} catch (InterruptedException e) {
						logger.log(Level.INFO, "Impossible to sleep the threadVerifySensorMessageBeforeActivity " + e.getClass().getCanonicalName());
					}
				}
			}
		});
		threadAlertTreatment.start();
		//TODO PA peut etre le mettre dans le allertTreatment
		threadVerifySensorMessageBeforeActivity.start();
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

	/**
	 * @return the monitoringAlert
	 */
	public MonitoringAlert getMonitoringAlert() {
		return monitoringAlert;
	}

	/**
	 * @param monitoringAlert the monitoringAlert to set
	 */
	public void setMonitoringAlert(MonitoringAlert monitoringAlert) {
		this.monitoringAlert = monitoringAlert;
	}
}