package com.blackmamba.deathkiss.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.blackmamba.deathkiss.entity.CommonArea;
import com.blackmamba.deathkiss.entity.Sensor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class TabListSensor extends JPanel {

	/**
	 * Different parameters used
	 */
	private static final long serialVersionUID = 1L;
	private int idemployee;
	private int index;
	private String requestType;
	private String table;
	private String jsonString;
	private JPanel bar;
	private JLabel labelIdEmployee;
	private Font police;
	private JButton disconnection;
	private JButton checkSensor;
	private JButton newCommonArea;
	private CommonArea commonArea;
	private CommonArea area;
	private JScrollPane sc;
	private Object[][] listM;
	private JTabbedPane tab;
	private TabSensor tabSensor;
	private Thread threadListSensor;
	private JList<String> tableau;
	private DefaultListModel<String> tableModel;
	private List<Sensor> listSensor = new ArrayList<Sensor>();
	private static final Logger logger = LogManager.getLogger(TabListSensor.class);
	private ResourceBundle rs = ResourceBundle.getBundle("parameters");

	public TabListSensor() {
	}

	public TabListSensor(CommonArea area, int idemployee, String title) {
		this.idemployee = idemployee;
		this.setArea(area);

		setThreadListSensor(new Thread(new Runnable() {
			/**
			 * Loop and update every 30 seconds the list of Sensors
			 */
			@Override
			public void run() {
				while (true) {
					updateListSensor();
					try {
						Thread.sleep(Integer.parseInt(rs.getString("time_threadSleep")));
					} catch (InterruptedException e) {
						logger.log(Level.INFO, "Impossible to sleep the thread" + e.getClass().getCanonicalName());
					}
				}
			}
		}));
		/**
		 * Definition of the structure of this tab
		 */
		bar = new JPanel();
		bar.setBackground(Color.DARK_GRAY);
		bar.setPreferredSize(new Dimension((int) getToolkit().getScreenSize().getWidth(), 80));
		bar.setLayout(new BorderLayout());
		bar.setBorder(BorderFactory.createMatteBorder(20, 100, 20, 100, bar.getBackground()));

		///////////////////////// BAR/////////////////////////////////////////////////
		/**
		 * Definition of label Identifiant on header bar
		 */
		labelIdEmployee = new JLabel("Identifiant :   " + this.idemployee + "    ");
		police = new Font("Arial", Font.BOLD, 16);
		labelIdEmployee.setForeground(Color.WHITE);
		labelIdEmployee.setFont(police);
		bar.add(labelIdEmployee, BorderLayout.WEST);

		/**
		 * Definition of the button and the different action after pressed the button
		 */
		disconnection = new JButton("Se Déconnecter");
		bar.add(disconnection, BorderLayout.EAST);
		disconnection.addActionListener(new ActionListener() {
			@Override
			/**
			 * Close the application
			 */
			public void actionPerformed(ActionEvent e) {
				logger.log(Level.INFO, "Application closed, after disconnection");
				System.exit(ABORT);

			}
		});

		///////////////////////// TABLE/////////////////////////////////////////////////
		tableModel = new DefaultListModel<String>();
		tableau = new JList<String>(tableModel);
		updateListSensor();

		sc = new JScrollPane(tableau);
		sc.setBounds((int) getToolkit().getScreenSize().getWidth() * 3 / 10,
				(int) getToolkit().getScreenSize().getHeight() * 2 / 10,
				(int) getToolkit().getScreenSize().getWidth() * 1 / 2,
				(int) getToolkit().getScreenSize().getHeight() * 1 / 2);
		this.add(sc);

		/**
		 * Get the id of the sensor selected by the user to be used after
		 */
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				index = tableau.locationToIndex(e.getPoint());
				String substring = tableModel.getElementAt(index).toString();
				int position = substring.indexOf(" ");
				index = Integer.parseInt(substring.substring(0, position));
			}
		};
		tableau.addMouseListener(mouseListener);

		///////////////////////// BUTTON/////////////////////////////////////////////////
		/**
		 * Definition of Button CheckSensor
		 */
		checkSensor = new JButton("Afficher le capteur");
		checkSensor.setBounds(((int) getToolkit().getScreenSize().getWidth() * 5 / 10),
				(int) getToolkit().getScreenSize().getHeight() * 15 / 20, 150, 40);
		this.add(checkSensor);
		checkSensor.addActionListener(new ActionListener() {
			/**
			 * If we pressed the CheckSensorButton we go to the TabSensor to watch the
			 * informations about this sensor
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				if (index == 0) {
					JOptionPane.showMessageDialog(null, "Veuillez selectionner un capteur", "Erreur",
							JOptionPane.ERROR_MESSAGE);
				} else {
					tab = new JTabbedPane();
					tab = Frame.getTab();

					tab.remove(2);
					tabSensor = new TabSensor(Color.GRAY, idemployee, "Onglet Capteurs", index);
					tab.add(tabSensor, 2);
					tab.setTitleAt(2, "Onglet Capteurs");
					Frame.goToTab(2);
				}
			}
		});

		/**
		 * Definition of Button newCommonArea
		 */
		newCommonArea = new JButton("Nouvelle Partie Commune");
		newCommonArea.setBounds(((int) getToolkit().getScreenSize().getWidth() * 3 / 10),
				(int) getToolkit().getScreenSize().getHeight() * 15 / 20, 200, 40);
		this.add(newCommonArea);
		newCommonArea.addActionListener(new ActionListener() {
			/**
			 * If we pressed the newCommonAreaButton we close this Tab, and we go to
			 * TabCommonArea to find a new CommonArea
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				tab = new JTabbedPane();
				tab = Frame.getTab();
				tab.remove(6);
				Frame.goToTab(1);
			}
		});
		threadListSensor.start();

		///////////////////////// FRAME/////////////////////////////////////////////////
		/**
		 * Different parameters of the window
		 */
		this.setLayout(new BorderLayout());
		this.add(bar, BorderLayout.NORTH);
		this.setBackground(Color.GRAY);

	}

	public void updateListSensor() {
		/**
		 * They find all the Sensors present in the CommonArea with the id sent by the
		 * TabCommonArea And add all this sensor in a list do be displayed
		 */
		requestType = "FIND ALL";
		commonArea = new CommonArea();
		Sensor sensor = new Sensor();
		table = "Sensor";
		ObjectMapper readMapper = new ObjectMapper();
		sensor.setIdCommonArea(area.getIdCommonArea());
		try {
			jsonString = readMapper.writeValueAsString(sensor);
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			Sensor[] listSensors = readMapper.readValue(jsonString, Sensor[].class);
			listSensor = Arrays.asList(listSensors);
			commonArea.setListSensor(listSensor);
			logger.log(Level.INFO, "Find all Sensors datas succed");
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON Sensor datas" + e1.getClass().getCanonicalName());
		}

		tableModel.removeAllElements();
		tableModel.addElement("Identifiant, Capteur Type, Etat, Nom Partie Commune");

		for (Sensor sensors : listSensor) {
			tableModel.addElement(Integer.toString(sensors.getIdSensor()) + " " + sensors.getTypeSensor() + " "
					+ sensors.getSensorState() + " " + area.getNameCommonArea());
		}
	}

	/**
	 * Getter of list with all the sensor of the CommonArea selected before
	 * 
	 * @return listM
	 */
	public Object[][] getListM() {
		return listM;
	}

	/**
	 * Setter of list, update the value of the listM
	 * 
	 * @param listM
	 */
	public void setListM(Object[][] listM) {
		this.listM = listM;
	}

	public CommonArea getArea() {
		return area;
	}

	public void setArea(CommonArea area) {
		this.area = area;
	}

	public Thread getThreadListSensor() {
		return threadListSensor;
	}

	public void setThreadListSensor(Thread threadListSensor) {
		this.threadListSensor = threadListSensor;
	}
}