package com.blackmamba.deathkiss.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.blackmamba.deathkiss.pool.entity.Alert;
import com.blackmamba.deathkiss.pool.entity.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class Frame extends JFrame {

	/**
	 * Different parameters used
	 */
	private static final long serialVersionUID = 1L;
	private static JTabbedPane tab;
	private TabEmployes tabEmployes;
	private TabCommonArea tabCommonArea;
	private TabSensor tabSensor;
	private TabResident tabResident;
	private TabHistorical tabHistorical;
	private TabProfile tabProfile;
	private TabMapSensor tabMapSensor;
	private int idEmployee;
	private String requestType;
	private String table;
	private String jsonString;
	private Employee employee;
	private ObjectMapper objectMapper;
	private Thread threadFrame;
	private Thread threadAlert;
	private List<Alert> listAlert = new ArrayList<Alert>();
	private static final Logger logger = LogManager.getLogger(Frame.class);
	private ResourceBundle rs = ResourceBundle.getBundle("parameters");

	public Frame(int idEmployee) {
		this.idEmployee = idEmployee;

		///////////////////////// Thread/////////////////////////////////////////////////
		setThreadFrame(new Thread(new Runnable() {
			/**
			 * Loop and update every 30 sec the list of employees
			 */
			@Override
			public void run() {
				while (true) {
					verificationUser(idEmployee);
					if (employee.getLastnameEmployee().equals("")) {
						JOptionPane.showMessageDialog(null, "Vous allez etre déconnecter", "Erreur",
								JOptionPane.ERROR_MESSAGE);
						System.exit(ABORT);
					}
					try {
						Thread.sleep(Integer.parseInt(rs.getString("time_threadSleep")));
					} catch (InterruptedException e) {
						logger.log(Level.INFO, "Impossible to sleep the thread" + e.getClass().getCanonicalName());
					}
				}
			}
		}));

		/**
		 * LOGO
		 */
		logger.log(Level.INFO, "______           _   _     _    _         ");
		logger.log(Level.INFO, "|  _  \\         | | | |   | |  (_)        ");
		logger.log(Level.INFO, "| | | |___  __ _| |_| |__ | | ___ ___ ___ ");
		logger.log(Level.INFO, "| | | / _ \\/ _` | __| '_ \\| |/ / / __/ __|");
		logger.log(Level.INFO, "| |/ /  __/ (_| | |_| | | |   <| \\__ \\__ \\");
		logger.log(Level.INFO, "|___/ \\___|\\__,_|\\__|_| |_|_|\\_\\_|___/___/");
		logger.log(Level.INFO, "                                          ");

		/**
		 * Creation of different tabs
		 */
		tabEmployes = new TabEmployes(Color.GRAY, this.idEmployee, "Onglet Employés");
		tabCommonArea = new TabCommonArea(Color.GRAY, this.idEmployee, "Onglet Parties Communes");
		tabSensor = new TabSensor(Color.GRAY, this.idEmployee, "Onglet Capteurs", 0);
		tabResident = new TabResident(Color.GRAY, this.idEmployee, "Onglet Résidents");
		tabHistorical = new TabHistorical(Color.GRAY, this.idEmployee, "Onglet Historiques");
		tabProfile = new TabProfile(Color.GRAY, this.idEmployee, "Onglet Profil");
		tabMapSensor = new TabMapSensor(Color.GRAY, this.idEmployee, "Onglet Map");

		/**
		 * Add of the title of tabs
		 */
		tab = new JTabbedPane();
		String tabOfTab[] = { "Employés", "Parties Communes", "Capteurs", "Résidents", "Historiques", "Profil", "Map" };

		/**
		 * Add of tabs on the window
		 */
		tab.add("Onglet " + tabOfTab[0], tabEmployes);
		tab.add("Onglet " + tabOfTab[1], tabCommonArea);
		tab.add("Onglet " + tabOfTab[2], tabSensor);
		tab.add("Onglet " + tabOfTab[3], tabResident);
		tab.add("Onglet " + tabOfTab[4], tabHistorical);
		tab.add("Onglet " + tabOfTab[5], tabProfile);
		tab.add("Onglet " + tabOfTab[6], tabMapSensor);

		///////////////////////// ALERT/////////////////////////////////////////////////
		setThreadAlert(new Thread(new Runnable() {
			/**
			 * Loop and update every second, if the listAlert is'nt empty they actualize the
			 * listSensor with the different states contained in the listAlert
			 */
			@Override
			public void run() {
				while (true) {
					getAlert();
					if (!listAlert.isEmpty())
						tabSensor.ActualizationListSensor(listAlert);
					try {
						Thread.sleep(Integer.parseInt(rs.getString("time_threadAlert")));
					} catch (InterruptedException e) {
						logger.log(Level.INFO,
								"Impossible to sleep the thread Alert" + e.getClass().getCanonicalName());
					}
				}
			}
		}));

		threadAlert.start();
		threadFrame.start();
		///////////////////////// FRAME/////////////////////////////////////////////////
		/**
		 * Different parameters of the window
		 */
		this.setTitle("Deathkiss");
		this.setSize((int) getToolkit().getScreenSize().getWidth(), (int) getToolkit().getScreenSize().getHeight());
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().add(tab);
	}

	public void verificationUser(int id) {
		requestType = "READ";
		employee = new Employee();
		table = "Employee";
		employee.setIdEmployee(id);
		objectMapper = new ObjectMapper();
		try {
			jsonString = objectMapper.writeValueAsString(employee);
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			employee = objectMapper.readValue(jsonString, Employee.class);
			logger.log(Level.INFO, "Find Employee data succed");
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON Employee datas" + e1.getClass().getCanonicalName());
		}
	}

	/**
	 * Get all Alert stocked on server and add on listAlert
	 */
	public void getAlert() {
		requestType = "GET ALERT";
		table = "Alert";
		objectMapper = new ObjectMapper();
		try {
			jsonString = "GET ALERT";
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			Alert[] alerts = objectMapper.readValue(jsonString, Alert[].class);
			listAlert = Arrays.asList(alerts);
			logger.log(Level.INFO, "Find Messages datas succed");
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON Messages datas " + e1.getClass().getCanonicalName());
		}
	}

	/**
	 * Method to switch to as other tab
	 * 
	 * @param index
	 */
	public static void goToTab(int index) {
		tab.setSelectedIndex(index);
	}

	/**
	 * Method to get the actual tab of frame
	 * 
	 * @return
	 */
	public static JTabbedPane getTab() {
		return tab;
	}

	/**
	 * Method to add a new tab on frame
	 * 
	 * @param tab
	 */
	public void setTab(JTabbedPane tab) {
		Frame.tab = tab;
	}

	public Thread getThreadFrame() {
		return threadFrame;
	}

	public void setThreadFrame(Thread threadFrame) {
		this.threadFrame = threadFrame;
	}

	public Thread getThreadAlert() {
		return threadAlert;
	}

	public void setThreadAlert(Thread threadAlert) {
		this.threadAlert = threadAlert;
	}
}
