package com.blackmamba.deathkiss.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;

import com.blackmamba.deathkiss.connectionpool.DataSource;
import com.blackmamba.deathkiss.connectionpool.JDBCConnectionPool;

public class TimeServer {
	// On initialise des valeurs par défaut
	private int port = 2345;
	private String host = "127.0.0.1";
	private ServerSocket server = null;
	private boolean isRunning = true;

	public TimeServer(){
	      try {
	         server = new ServerSocket(port, 100, InetAddress.getByName(host));
	      } catch (UnknownHostException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	   }

	public TimeServer(String pHost, int pPort){
	      host = pHost;
	      port = pPort;
	      try {
	         server = new ServerSocket(port, 100, InetAddress.getByName(host));
	      } catch (UnknownHostException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	   }

	// On lance notre serveur
	public void open() {

		// Toujours dans un thread à part vu qu'il est dans une boucle infinie
		Thread t = new Thread(new Runnable() {
			public void run() {
				JDBCConnectionPool pool;			
				while (isRunning == true) {
					try {
						pool = new JDBCConnectionPool(false);
						// On attend une connexion d'un client
						Socket client = server.accept();

						// Une fois reçue, on la traite dans un thread séparé
						System.out.println("Connexion cliente reçue.");
						Thread t = new Thread(new ResquestHandler(client,pool));
						t.start();

					} catch (IOException | SQLException e) {
						e.printStackTrace();
					} 
				}
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
					server = null;
				}
			}
		});

		t.start();
	}

	public void close() {
		isRunning = false;
	}
}
