package com.blackmamba.deathkiss.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.blackmamba.deathkiss.entity.Alert;
import com.blackmamba.deathkiss.entity.CommonArea;
import com.blackmamba.deathkiss.entity.Sensor;
import com.blackmamba.deathkiss.entity.SensorType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class TabSensor extends JPanel {

	/**
	 * Different parameters used
	 */
	private static final long serialVersionUID = 1L;
	private String requestType;
	private String table;
	private String jsonString;
	private int idemployee;
	private int index;
	private JPanel bar;
	private JPanel search;
	private JLabel labelIdEmployee;
	private JLabel labelSearch;
	private JLabel labelIdSensor;
	private JLabel labelNameCommonArea;
	private JLabel labelTypeSensor;
	private JLabel labelStateSensor;
	private JLabel labelHeadList;
	private JTextField textInputIdSensor;
	private JTextField searchBar;
	private Font policeBar;
	private Font policeLabel;
	private JButton disconnection;
	private JButton addSensor;
	private JButton save;
	private JButton restaure;
	private JButton delete;
	private JButton switchButton;
	private JButton validButton;
	private Sensor sensor;
	private Sensor sensor2;
	private JScrollPane sc;
	private Alert alert;
	private CommonArea commonArea;
	private ObjectMapper objectMapper;
	private Thread threadSensor;
	private JComboBox<String> textInputNameCommonArea;
	private JComboBox<String> textInputTypeSensor;
	private DefaultListModel<String> listM;
	private JList<String> list;
	private List<Alert> listAlert = new ArrayList<Alert>();
	private List<Sensor> listSensor = new ArrayList<Sensor>();
	private List<Sensor> listSearchSensor = new ArrayList<Sensor>();
	private List<CommonArea> listCommonArea = new ArrayList<CommonArea>();
	private static final Logger logger = LogManager.getLogger(TabSensor.class);
	private ResourceBundle rs = ResourceBundle.getBundle("parameters");

	/**
	 * Constructor
	 */
	public TabSensor() {
	}

	/**
	 * Constructor
	 * 
	 * @param color
	 * @param idemployee
	 * @param title
	 * @param idSensor
	 */
	public TabSensor(Color color, int idemployee, String title, int idSensor) {
		this.idemployee = idemployee;

		///////////////////////// Thread/////////////////////////////////////////////////
		setThreadSensor(new Thread(new Runnable() {
			/**
			 * Loop and update every 30 sec the list of employees
			 */
			@Override
			public void run() {
				while (true) {
					updateSensorSelected();
					updateListAreas();
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
		labelIdEmployee = new JLabel("Login :   " + this.idemployee + "    ");
		policeBar = new Font("Arial", Font.BOLD, 16);
		labelIdEmployee.setForeground(Color.WHITE);
		labelIdEmployee.setFont(policeBar);
		bar.add(labelIdEmployee, BorderLayout.WEST);

		/**
		 * Definition of the button and the different action after pressed the button
		 */
		disconnection = new JButton("Disconnect");
		bar.add(disconnection, BorderLayout.EAST);
		disconnection.addActionListener(new ActionListener() {
			/**
			 * Close the application
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.log(Level.INFO, "Application closed, after disconnection");
				System.exit(ABORT);
			}
		});

		/**
		 * Definition of the panel Search
		 */
		search = new JPanel();
		search.setBackground(Color.DARK_GRAY);
		search.setBorder(BorderFactory.createMatteBorder(0, 25, 0, 25, bar.getBackground()));
		bar.add(search);

		/**
		 * Definition of the label search and add on panel search
		 */
		labelSearch = new JLabel();
		labelSearch.setText("Research : ");
		labelSearch.setFont(policeBar);
		labelSearch.setForeground(Color.WHITE);
		search.add(labelSearch);

		/**
		 * Definition of the textField seachBar and add panel search
		 */
		searchBar = new JTextField();
		searchBar.setPreferredSize(new Dimension(350, 30));
		search.add(searchBar);

		/**
		 * Definition of the ValidButton
		 */
		validButton = new JButton();
		validButton.setText("Search");
		search.add(validButton);
		validButton.addActionListener(new ActionListener() {
			/**
			 * Verify the content of the search if they match with just numerics they will
			 * send a request to search with the id written in the research the IdSensor or
			 * the IdCommonArea of the Sensor. And add all the results on a list to display
			 * But if there is letter and numerics they will send a request to return all
			 * the Sensor when the type of Sensor contains the research
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				sensor2 = new Sensor();
				String searchReceived = searchBar.getText().trim();
				if (!searchReceived.equals("")) {
					/**
					 * If the research is just numerics they find first the IdSensor
					 */
					if (searchReceived.matches("[0-9]+[0-9]*")) {
						requestType = "READ";
						sensor2 = new Sensor();
						table = "Sensor";
						sensor2.setIdSensor(Integer.parseInt(searchReceived));
						try {
							jsonString = objectMapper.writeValueAsString(sensor2);
							new ClientSocket(requestType, jsonString, table);
							jsonString = ClientSocket.getJson();
							sensor2 = objectMapper.readValue(jsonString, Sensor.class);
							logger.log(Level.INFO, "Find Sensor data succed");
						} catch (Exception e1) {
							logger.log(Level.INFO,
									"Impossible to parse in JSON Sensor datas " + e1.getClass().getCanonicalName());
						}
						listM.removeAllElements();
						if (sensor2.getTypeSensor() != null) {
							listM.addElement("Result for sensor with id : " + searchReceived);
							listM.addElement(sensor2.getIdSensor() + "# " + sensor2.getTypeSensor() + " ,"
									+ sensor2.getSensorState() + " ," + sensor2.getIdCommonArea());
						}
						/**
						 * Find Sensor with IdCommonArea
						 */
						searchReceived = Normalizer.normalize(searchReceived, Normalizer.Form.NFD)
								.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
						sensor2.setIdCommonArea(Integer.parseInt(searchReceived));
						findAllSensor(sensor2);
						if (listSearchSensor.size() > 0)
							listM.addElement("Results for sensor in common area : " + searchReceived);
						for (Sensor sensors : listSearchSensor) {
							listM.addElement(sensors.getIdSensor() + "# " + sensors.getTypeSensor() + " ,"
									+ sensors.getSensorState() + " ," + sensors.getIdCommonArea());
						}
					} else {
						/**
						 * If the research contains letter and numerics we match with the types of
						 * Sensor
						 */
						sensor2 = new Sensor();
						for (SensorType type : SensorType.values()) {
							if (type.name().startsWith(searchReceived.toUpperCase())) {
								sensor2.setTypeSensor(type);
							}
						}
						findAllSensor(sensor2);
						listM.removeAllElements();
						if (listSearchSensor.size() > 0)
							listM.addElement("Results for sensor type : " + searchReceived);
						for (Sensor sensors : listSearchSensor) {
							listM.addElement(sensors.getIdSensor() + "# " + sensors.getTypeSensor() + " ,"
									+ sensors.getSensorState() + " ," + sensors.getIdCommonArea());
						}
					}
				} else {
					/**
					 * If the research is empty they display all the Sensors
					 */
					updateListSensor();
				}
				searchBar.setText("");
			}
		});

		/**
		 * Definition of the List CommonArea
		 */
		textInputNameCommonArea = new JComboBox<String>();

		///////////////////////// FROM LIST SENSOR//////////////////////////////////////
		/**
		 * if the is before on TabListSensor they send a idSensor and if they are
		 * different to 0 they send a request to get informations about this Sensor
		 */
		if (idSensor != 0) {
			requestType = "READ";
			sensor = new Sensor();
			table = "Sensor";
			ObjectMapper readMapper = new ObjectMapper();
			sensor.setIdSensor(idSensor);
			try {
				jsonString = readMapper.writeValueAsString(sensor);
				new ClientSocket(requestType, jsonString, table);
				jsonString = ClientSocket.getJson();
				sensor = readMapper.readValue(jsonString, Sensor.class);
				logger.log(Level.INFO, "Find Sensor datas succed");
			} catch (Exception e1) {
				logger.log(Level.INFO, "Impossible to parse in JSON Sensor datas " + e1.getClass().getCanonicalName());
			}
		}

		///////////////////////// LIST SENSOR///////////////////////////////////////////
		listM = new DefaultListModel<String>();
		list = new JList<String>(listM);
		updateListSensor();

		/**
		 * Add a scrollBar on list
		 */
		sc = new JScrollPane(list);
		sc.setBounds(30, 120, 300, ((int) getToolkit().getScreenSize().getHeight() - 300));
		this.add(sc);

		/**
		 * when we pressed a line in the list they will send a request to get all the
		 * information about the Sensor selected to be displayed on the textField
		 */
		index = -9999;
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				index = list.locationToIndex(e.getPoint());
				String substring = listM.getElementAt(index).toString();
				int position = substring.indexOf("#");
				if (position > -1) {
					String id = substring.substring(0, position);

					/**
					 * Find the Sensor by the id get on list
					 */
					requestType = "READ";
					sensor = new Sensor();
					table = "Sensor";
					sensor.setIdSensor(Integer.parseInt(id));
					try {
						jsonString = objectMapper.writeValueAsString(sensor);
						new ClientSocket(requestType, jsonString, table);
						jsonString = ClientSocket.getJson();
						sensor = objectMapper.readValue(jsonString, Sensor.class);
					} catch (IOException e1) {
						logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
					}
					/**
					 * Find the CommonAreaName by the idCommonArea get on list
					 */
					requestType = "READ";
					commonArea = new CommonArea();
					table = "CommonArea";
					ObjectMapper readMapper = new ObjectMapper();
					commonArea.setIdCommonArea(sensor.getIdCommonArea());
					try {
						jsonString = readMapper.writeValueAsString(commonArea);
						new ClientSocket(requestType, jsonString, table);
						jsonString = ClientSocket.getJson();
						commonArea = readMapper.readValue(jsonString, CommonArea.class);
					} catch (IOException e1) {
						logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
					}

					textInputIdSensor.setText(Integer.toString(sensor.getIdSensor()));
					String str = commonArea.getNameCommonArea() + " #" + sensor.getIdCommonArea();
					for (int i = 0; i < textInputNameCommonArea.getItemCount(); i++) {
						if (textInputNameCommonArea.getItemAt(i).toString().contains(str)) {
							textInputNameCommonArea.setSelectedIndex(i);
						}
					}
					textInputTypeSensor.setSelectedItem(sensor.getTypeSensor().name());
					if (sensor.getSensorState()) {
						switchButton.setText("ON");
						switchButton.setBackground(Color.GREEN);
					} else {
						switchButton.setText("OFF");
						switchButton.setBackground(Color.RED);
					}
				}
			}
		};
		list.addMouseListener(mouseListener);

		/**
		 * Call the method define at end to update the list of CommonAreas availables
		 * they will be execute one time at creation of the window
		 */
		updateListAreas();

		///////////////////////// LABEL/////////////////////////////////////////////////
		/**
		 * Definition of label IdSensor
		 */
		policeLabel = new Font("Arial", Font.BOLD, (int) getToolkit().getScreenSize().getWidth() / 80);
		labelIdSensor = new JLabel("ID : ");
		labelIdSensor.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 2 / 10, 200, 30);
		labelIdSensor.setFont(policeLabel);
		this.add(labelIdSensor);

		/**
		 * Definition of label NameCommonArea
		 */
		labelNameCommonArea = new JLabel("Common Area name : ");
		labelNameCommonArea.setBounds((int) getToolkit().getScreenSize().getWidth() * 4 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 2 / 10, 300, 30);
		labelNameCommonArea.setFont(policeLabel);
		this.add(labelNameCommonArea);

		/**
		 * Definition of label TypeSensor
		 */
		labelTypeSensor = new JLabel("Sensor type : ");
		labelTypeSensor.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 4 / 10, 200, 30);
		labelTypeSensor.setFont(policeLabel);
		this.add(labelTypeSensor);

		/**
		 * Definition of label StateSensor
		 */
		labelStateSensor = new JLabel("Sensor state : ");
		labelStateSensor.setBounds((int) getToolkit().getScreenSize().getWidth() * 4 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 4 / 10, 200, 30);
		labelStateSensor.setFont(policeLabel);
		this.add(labelStateSensor);

		/**
		 * Definition of label HeadList
		 */
		labelHeadList = new JLabel("ID /Type /State /ID common area");
		labelHeadList.setBounds(40, 90, 300, 30);
		labelHeadList.setFont(policeBar);
		this.add(labelHeadList);

		//////////////////// TEXT AREA////////////////////////////////////////////////
		/**
		 * Definition of textArea IdSensor
		 */
		textInputIdSensor = new JTextField();
		textInputIdSensor.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 5 / 20, 300, 40);
		textInputIdSensor.setFont(policeLabel);
		if (idSensor == 0)
			textInputIdSensor.setText("");
		else
			textInputIdSensor.setText(Integer.toString(idSensor));
		textInputIdSensor.setEditable(false);
		this.add(textInputIdSensor);

		/**
		 * Definition of textArea NameCommonArea
		 */
		textInputNameCommonArea.setBounds((int) getToolkit().getScreenSize().getWidth() * 4 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 5 / 20, 300, 40);
		textInputNameCommonArea.setFont(policeLabel);
		this.add(textInputNameCommonArea);

		/**
		 * Definition of textArea TypeSensor
		 */
		String[] types = { SensorType.SMOKE.name(), SensorType.MOVE.name(), SensorType.TEMPERATURE.name(),
				SensorType.WINDOW.name(), SensorType.DOOR.name(), SensorType.ELEVATOR.name(), SensorType.LIGHT.name(),
				SensorType.FIRE.name(), SensorType.BADGE.name(), SensorType.ROUTER.name() };
		textInputTypeSensor = new JComboBox<String>(types);
		textInputTypeSensor.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 9 / 20, 300, 40);
		textInputTypeSensor.setFont(policeLabel);
		this.add(textInputTypeSensor);
		textInputTypeSensor.addItemListener(new ItemListener() {
			/**
			 * All type of Sensor
			 */
			@Override
			public void itemStateChanged(ItemEvent e) {
				SensorType element = SensorType.valueOf(e.getItem().toString());
				sensor.setTypeSensor(element);
			}
		});

		/**
		 * Definition of textArea StateSensor
		 */
		switchButton = new JButton();
		switchButton.setBounds((int) getToolkit().getScreenSize().getWidth() * 4 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 9 / 20, 100, 40);
		switchButton.setText("OFF");
		switchButton.setBackground(Color.RED);
		switchButton.setFont(policeLabel);
		this.add(switchButton);
		switchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if (switchButton.getText().equals("ON")) {
					switchButton.setText("OFF");
					switchButton.setBackground(Color.RED);
				} else if (switchButton.getText().equals("OFF")) {
					switchButton.setText("ON");
					switchButton.setBackground(Color.GREEN);
				}
			}
		});

		if (idSensor != 0 && index == -9999) {
			/**
			 * Find the Sensor by the id get on list
			 */
			requestType = "READ";
			sensor = new Sensor();
			table = "Sensor";
			sensor.setIdSensor(idSensor);
			try {
				jsonString = objectMapper.writeValueAsString(sensor);
				new ClientSocket(requestType, jsonString, table);
				jsonString = ClientSocket.getJson();
				sensor = objectMapper.readValue(jsonString, Sensor.class);
			} catch (Exception e1) {
				logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
			}
			/**
			 * Find the CommonAreaName by the idCommonArea get on list
			 */
			requestType = "READ";
			commonArea = new CommonArea();
			table = "CommonArea";
			ObjectMapper readMapper = new ObjectMapper();
			commonArea.setIdCommonArea(sensor.getIdCommonArea());
			try {
				jsonString = readMapper.writeValueAsString(commonArea);
				new ClientSocket(requestType, jsonString, table);
				jsonString = ClientSocket.getJson();
				commonArea = readMapper.readValue(jsonString, CommonArea.class);
			} catch (Exception e1) {
				logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
			}
			textInputTypeSensor.setSelectedItem(sensor.getTypeSensor().name());
			textInputNameCommonArea
					.setSelectedItem(commonArea.getNameCommonArea() + " #" + commonArea.getIdCommonArea());
			if (sensor.getSensorState()) {
				switchButton.setText("ON");
				switchButton.setBackground(Color.GREEN);
			} else {
				switchButton.setText("OFF");
				switchButton.setBackground(Color.RED);
			}
		}

		///////////////////////// BUTTON/////////////////////////////////////////////////
		/**
		 * Definition of Button AddSensor
		 */
		addSensor = new JButton("Add");
		addSensor.setBounds(30, (int) getToolkit().getScreenSize().getHeight() - 150, 300, 40);
		this.add(addSensor);
		addSensor.addActionListener(new ActionListener() {
			/**
			 * When we add an sensor, we get the commonArea, the state and the type of the
			 * sensor from the textFields and the item selected by the user
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				requestType = "CREATE";
				table = "Sensor";

				if (textInputIdSensor.getText().toString().equals("")) {
					sensor.setIdSensor(0);
				} else {
					sensor.setIdSensor(Integer.parseInt(textInputIdSensor.getText().toString()));
				}
				String newNameCommonArea = textInputNameCommonArea.getSelectedItem().toString();
				int position = newNameCommonArea.indexOf("#");
				String id = newNameCommonArea.substring(position + 1, newNameCommonArea.length());
				sensor.setIdCommonArea(Integer.parseInt(id));

				String newStateSensor = switchButton.getText();
				if (newStateSensor.equals("ON"))
					sensor.setSensorState(true);
				else
					sensor.setSensorState(false);

				/**
				 * Read the sensor type selected
				 */
				String newTypeSensor = textInputTypeSensor.getSelectedItem().toString();
				SensorType element = SensorType.valueOf(newTypeSensor);
				sensor.setTypeSensor(element);

				ObjectMapper insertMapper = new ObjectMapper();
				try {
					jsonString = insertMapper.writeValueAsString(sensor);
					new ClientSocket(requestType, jsonString, table);
					jsonString = ClientSocket.getJson();
					if (!jsonString.equals("INSERTED")) {
						JOptionPane.showMessageDialog(null, "Insertion failed", "Error", JOptionPane.ERROR_MESSAGE);
						logger.log(Level.INFO, "Impossible to insert sensor");
					} else {
						logger.log(Level.INFO, "Insertion Succeeded");
						requestType = "READ ALL";
						table = "Sensor";
						jsonString = "READ ALL";
						new ClientSocket(requestType, jsonString, table);
						jsonString = ClientSocket.getJson();
						Sensor[] sensors = objectMapper.readValue(jsonString, Sensor[].class);
						listSensor = Arrays.asList(sensors);
						int x = listSensor.size() - 1;
						sensor = listSensor.get(x);
						listM.addElement(sensor.getIdSensor() + "# " + sensor.getTypeSensor() + " ,"
								+ sensor.getSensorState() + " ," + sensor.getIdCommonArea());
						logger.log(Level.INFO, "Find Sensor succeeded");
						JOptionPane.showMessageDialog(null, "Insertion succeeded", "Information",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (Exception e1) {
					logger.log(Level.INFO,
							"Impossible to parse in JSON Sensor datas " + e1.getClass().getCanonicalName());
				}
			}
		});

		/**
		 * Definition of Button Save
		 */
		save = new JButton("Save");
		save.setBounds(((int) getToolkit().getScreenSize().getWidth() * 2 / 4) + 250,
				(int) getToolkit().getScreenSize().getHeight() * 15 / 20, 200, 40);
		this.add(save);
		save.addActionListener(new ActionListener() {
			/**
			 * Get the selected commonArea ,the state and the type of sensor and send to
			 * server the request
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				requestType = "UPDATE";
				table = "Sensor";

				if (textInputIdSensor.getText().toString().equals("")) {
					sensor.setIdSensor(0);
				} else {
					sensor.setIdSensor(Integer.parseInt(textInputIdSensor.getText().toString()));
				}
				String newNameCommonArea = textInputNameCommonArea.getSelectedItem().toString();
				int position = newNameCommonArea.indexOf("#");
				String id = newNameCommonArea.substring(position + 1, newNameCommonArea.length());
				sensor.setIdCommonArea(Integer.parseInt(id));

				/**
				 * Get the state of sensor
				 */
				String newStateSensor = switchButton.getText();
				if (newStateSensor.equals("ON"))
					sensor.setSensorState(true);
				else
					sensor.setSensorState(false);

				/**
				 * Select the type of sensor
				 */
				String newTypeSensor = textInputTypeSensor.getSelectedItem().toString();
				SensorType element = SensorType.valueOf(newTypeSensor);
				sensor.setTypeSensor(element);

				if (sensor.getIdSensor() == 0) {
					JOptionPane.showMessageDialog(null, "Please select an sensor to be update", "Error",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					ObjectMapper insertMapper = new ObjectMapper();
					try {
						jsonString = insertMapper.writeValueAsString(sensor);
						new ClientSocket(requestType, jsonString, table);
						jsonString = ClientSocket.getJson();
						if (!jsonString.equals("UPDATED")) {
							JOptionPane.showMessageDialog(null, "Update failed", "Error", JOptionPane.ERROR_MESSAGE);
							logger.log(Level.INFO, "Impossible to update sensor");
						} else {
							logger.log(Level.INFO, "Update Succeeded");
							listM.set(index, sensor.getIdSensor() + "# " + sensor.getTypeSensor() + " ,"
									+ sensor.getSensorState() + " ," + sensor.getIdCommonArea());
							JOptionPane.showMessageDialog(null, "Datas updated", "Information",
									JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (Exception e1) {
						logger.log(Level.INFO,
								"Impossible to parse in JSON sensor datas " + e1.getClass().getCanonicalName());
					}
				}
			}
		});

		/**
		 * Definition of Button Restore
		 */
		restaure = new JButton("Restore");
		restaure.setBounds(((int) getToolkit().getScreenSize().getWidth() * 2 / 4),
				(int) getToolkit().getScreenSize().getHeight() * 15 / 20, 200, 40);
		this.add(restaure);
		restaure.addActionListener(new ActionListener() {
			/**
			 * when the button Restore is pressed, we define the textAreas as the last
			 * sensor datas selected in the list by the user
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				if (sensor.getIdSensor() == 0) {
					textInputIdSensor.setText("");
				} else {
					textInputIdSensor.setText(Integer.toString(sensor.getIdSensor()));
				}
				textInputNameCommonArea.setSelectedItem(commonArea.getNameCommonArea());
				textInputTypeSensor.setSelectedItem(sensor.getTypeSensor().name());
				if (sensor.getSensorState()) {
					switchButton.setText("ON");
					switchButton.setBackground(Color.GREEN);
				} else {
					switchButton.setText("OFF");
					switchButton.setBackground(Color.RED);
				}
			}
		});

		/**
		 * Definition of Button Delete
		 */
		delete = new JButton("Delete");
		delete.setBounds(((int) getToolkit().getScreenSize().getWidth() * 2 / 4) - 250,
				(int) getToolkit().getScreenSize().getHeight() * 15 / 20, 200, 40);
		this.add(delete);
		delete.addActionListener(new ActionListener() {
			/**
			 * When we pressed the button delete we get the id of the sensor and we send it
			 * to server, to be deleted by him
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!textInputIdSensor.getText().toString().equals("")) {
					requestType = "DELETE";
					table = "Sensor";
					ObjectMapper connectionMapper = new ObjectMapper();
					try {
						jsonString = connectionMapper.writeValueAsString(sensor);
						new ClientSocket(requestType, jsonString, table);
						jsonString = ClientSocket.getJson();
						if (!jsonString.equals("DELETED")) {
							JOptionPane.showMessageDialog(null, "Deletion failed", "Error", JOptionPane.ERROR_MESSAGE);
							logger.log(Level.INFO, "Impossible to delete this sensor");
						} else {
							JOptionPane.showMessageDialog(null, "Deletion succeeded", "Information",
									JOptionPane.INFORMATION_MESSAGE);
							logger.log(Level.INFO, "Deletion of sensor succed");
						}
					} catch (Exception e1) {
						logger.log(Level.INFO,
								"Impossible to parse in JSON Sensor datas " + e1.getClass().getCanonicalName());
					}
					sensor.setIdCommonArea(0);
					sensor.setIdSensor(0);

					listM.removeElementAt(index);
					index = -9999;
					textInputIdSensor.setText("");
					switchButton.setText("OFF");
					switchButton.setBackground(Color.RED);
				} else {
					JOptionPane.showMessageDialog(null, "Please select an sensor to deleted", "Error",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		/**
		 * Launch thread
		 */
		threadSensor.start();

		///////////////////////// FRAME/////////////////////////////////////////////////
		/**
		 * Diferent parameter of the window
		 */
		this.setLayout(new BorderLayout());
		this.add(bar, BorderLayout.NORTH);
		this.setBackground(color);
	}

	///////////////////////// LIST COMMON AREA//////////////////////////////////////
	/**
	 * updateListAreas get all the CommonAreas available in the data base and will
	 * transform that in a list
	 */
	public void updateListAreas() {
		requestType = "READ ALL";
		table = "CommonArea";
		objectMapper = new ObjectMapper();
		try {
			jsonString = "READ ALL";
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			CommonArea[] commonAreas = objectMapper.readValue(jsonString, CommonArea[].class);
			listCommonArea = Arrays.asList(commonAreas);
			logger.log(Level.INFO, "Recuperation of all Common Areas available succed");
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON Common Area datas " + e1.getClass().getCanonicalName());
		}
		String areasAdd = "";
		textInputNameCommonArea.removeAllItems();
		for (CommonArea commonAreas : listCommonArea) {
			if (!areasAdd.contains(commonAreas.getNameCommonArea() + " #" + commonAreas.getIdCommonArea()))
				textInputNameCommonArea.addItem(commonAreas.getNameCommonArea() + " #" + commonAreas.getIdCommonArea());
			areasAdd = areasAdd + commonAreas.getNameCommonArea() + " #" + commonAreas.getIdCommonArea() + ",";
		}
		logger.log(Level.INFO, "Convertion of all Common Areas available in a list succed");
	}

	/**
	 * Find all the Sensor in the data base and add on list to be displayed
	 */
	public void updateListSensor() {
		requestType = "READ ALL";
		table = "Sensor";
		objectMapper = new ObjectMapper();
		try {
			jsonString = "READ ALL";
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			Sensor[] sensors = objectMapper.readValue(jsonString, Sensor[].class);
			listSensor = Arrays.asList(sensors);
			logger.log(Level.INFO, "Find Sensor data succed");
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON Sensor data " + e1.getClass().getCanonicalName());
		}
		listM.removeAllElements();
		listM.addElement("All sensors");
		for (Sensor sens : listSensor) {
			listM.addElement(sens.getIdSensor() + "# " + sens.getTypeSensor() + " ," + sens.getSensorState() + " ,"
					+ sens.getIdCommonArea());
		}
	}

	/**
	 * Find the Sensor by the id get on list
	 */
	public void updateSensorSelected() {
		if (index != -9999) {
			String substring = listM.getElementAt(index).toString();
			int position = substring.indexOf("#");
			if (position > -1) {
				String id = substring.substring(0, position);

				requestType = "READ";
				sensor = new Sensor();
				table = "Sensor";
				sensor.setIdSensor(Integer.parseInt(id));
				try {
					jsonString = objectMapper.writeValueAsString(sensor);
					new ClientSocket(requestType, jsonString, table);
					jsonString = ClientSocket.getJson();
					sensor = objectMapper.readValue(jsonString, Sensor.class);
				} catch (Exception e1) {
					logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
				}

				textInputIdSensor.setText(Integer.toString(sensor.getIdSensor()));
				String str = commonArea.getNameCommonArea() + " #" + sensor.getIdCommonArea();
				for (int i = 0; i < textInputNameCommonArea.getItemCount(); i++) {
					if (textInputNameCommonArea.getItemAt(i).toString().contains(str)) {
						textInputNameCommonArea.setSelectedIndex(i);
					}
				}
				textInputTypeSensor.setSelectedItem(sensor.getTypeSensor().name());
				if (sensor.getSensorState()) {
					switchButton.setText("ON");
					switchButton.setBackground(Color.GREEN);
				} else {
					switchButton.setText("OFF");
					switchButton.setBackground(Color.RED);
				}
			}
		}
	}

	/**
	 * Find all Alert in the data base and add on list to be displayed
	 */
	public void updateListAlert() {
		alert = new Alert();
		alert.setIdAlert(0);
		alert.setAlertState(null);
		alert.setIdSensor(0);
		alert.setAlertDate(null);

		requestType = "READ ALL";
		table = "Alert";
		listAlert = getAllAlert(null, requestType, table);

		listM.clear();
		listM = new DefaultListModel<>();
		listM.addElement("All alert");
		for (Alert alerts : listAlert) {
			listM.addElement(alerts.getIdAlert() + "# " + alerts.getAlertState() + " ," + alerts.getIdSensor() + " , "
					+ alerts.getIdAlert());
		}
		list.setModel(listM);
	}

	/**
	 * Find all the Sensor with the alert state changed in the data base and add on
	 * list to be displayed
	 */
	public void actualizationListSensor(List<Alert> list) {
//		// TODO RK
//		Sensor sensors = new Sensor();
//		updateListAlert();
//		updateListSensor();
//
//		// listM.removeAllElements();
//		listM.addElement("Sensors and their states");
//		for (Alert alerts : listAlert) {
//			if (alerts.getIdSensor() == sensors.getIdSensor() && (!alerts.getAlertState().equals(sensors.getAlertState()))) {
//				//listM.insertElementAt(listM.getElementAt(4), 4);
//				listM.addElement(sensors.getIdSensor() + "# " + sensors.getTypeSensor() + sensors.getSensorState() + sensors.getIdCommonArea() + alerts.getAlertState());
//			}
//		}
//		if (listM.isEmpty() && (listSensor.isEmpty())) {
//			actualizationListSensor(listAlert);
//		}
	}

	public void findAllSensor(Sensor sensor) {
		requestType = "FIND ALL";
		table = "Sensor";
		objectMapper = new ObjectMapper();
		try {
			jsonString = objectMapper.writeValueAsString(sensor);
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			Sensor[] sensors = objectMapper.readValue(jsonString, Sensor[].class);
			listSearchSensor = Arrays.asList(sensors);
			logger.log(Level.INFO, "Find Sensor data succed");
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON Sensor datas " + e1.getClass().getCanonicalName());
		}
	}

	public List<Alert> getAllAlert(Alert alert, String requestType, String table) {
		objectMapper = new ObjectMapper();
		try {
			jsonString = objectMapper.writeValueAsString(alert);
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			Alert[] alerts = objectMapper.readValue(jsonString, Alert[].class);
			logger.log(Level.INFO, "Find Alert data succed");
			return Arrays.asList(alerts);
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON Alert datas " + e1.getClass().getCanonicalName());
			return null;
		}
	}

	/**
	 * @return the threadSensor
	 */
	public Thread getThreadSensor() {
		return threadSensor;
	}

	/**
	 * @param threadSensor the threadSensor to set
	 */
	public void setThreadSensor(Thread threadSensor) {
		this.threadSensor = threadSensor;
	}
}