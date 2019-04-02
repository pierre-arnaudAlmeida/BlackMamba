package com.blackmamba.deathkiss.gui.dev;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

	private static final long serialVersionUID = 1L;
	private String requestType;
	private String table;
	private String jsonString;
	private int idemployee;
	private int index;
	private JPanel bar;
	private JLabel labelIdEmployee;
	private JLabel idEmployee;
	private JLabel labelIdSensor;
	private JLabel labelNameCommonArea;
	private JLabel labelTypeSensor;
	private JLabel labelStateSensor;
	private JTextField textInputIdSensor;
	private Font policeBar;
	private Font policeLabel;
	private JButton disconnection;
	private JButton addSensor;
	private JButton save;
	private JButton restaure;
	private JButton delete;
	private JButton switchButton;
	private Sensor sensor;
	private JScrollPane sc;
	private CommonArea commonArea;
	private ObjectMapper objectMapper;
	private List<Sensor> listSensor = new ArrayList<Sensor>();
	private List<CommonArea> listCommonArea = new ArrayList<CommonArea>();
	private static final Logger logger = LogManager.getLogger(TabProfile.class);
	private JComboBox textInputNameCommonArea;
	private JComboBox textInputTypeSensor;
	private DefaultListModel listM;
	private JList list;

	public TabSensor() {
	}

	public TabSensor(Color color, int idemployee, String title, int idSensor) {
		this.idemployee = idemployee;

		/**
		 * Definition of the structure of this tab
		 */
		bar = new JPanel();
		bar.setBackground(Color.DARK_GRAY);
		bar.setPreferredSize(new Dimension((int) getToolkit().getScreenSize().getWidth(), 70));
		bar.setLayout(new BorderLayout());
		bar.setBorder(BorderFactory.createMatteBorder(20, 100, 20, 100, bar.getBackground()));

		///////////////////////// BAR/////////////////////////////////////////////////
		/**
		 * Definition of label Identifiant on header bar
		 */
		labelIdEmployee = new JLabel("Identifiant :   ");
		policeBar = new Font("Arial", Font.BOLD, 16);
		labelIdEmployee.setForeground(Color.WHITE);
		labelIdEmployee.setFont(policeBar);
		bar.add(labelIdEmployee, BorderLayout.WEST);

		/**
		 * Definition of the label idEmployee on header bar
		 */
		idEmployee = new JLabel();
		idEmployee.setText("" + this.idemployee + "");
		idEmployee.setFont(policeBar);
		idEmployee.setForeground(Color.WHITE);
		bar.add(idEmployee, BorderLayout.CENTER);

		/**
		 * Definition of the button and the different action after pressed the button
		 */
		disconnection = new JButton("Se Déconnecter");
		bar.add(disconnection, BorderLayout.EAST);
		disconnection.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.log(Level.INFO, "Application closed, after disconnection");
				System.exit(ABORT);

			}
		});

		/**
		 * Definition of the List CommonArea
		 */
		textInputNameCommonArea = new JComboBox();
		
		///////////////////////// FROM LIST SENSOR//////////////////////////////////////
		if (idSensor != 0) {
			requestType = "READ";
			sensor = new Sensor();
			table = "Sensor";
			ObjectMapper readMapper = new ObjectMapper();
			sensor.setIdSensor(idSensor);
			try {
				jsonString = readMapper.writeValueAsString(sensor);
				;
				new ClientSocket(requestType, jsonString, table);
				jsonString = ClientSocket.getJson();
				sensor = readMapper.readValue(jsonString, Sensor.class);
			} catch (Exception e1) {
				logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
			}

		}

		///////////////////////// LIST SENSOR///////////////////////////////////////////
		sensor = new Sensor();
		sensor.setIdCommonArea(0);
		sensor.setSensorState(false);
		sensor.setTypeSensor(null);

		requestType = "READ ALL";
		table = "Sensor";
		objectMapper = new ObjectMapper();
		try {
			jsonString = "READ ALL";
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			Sensor[] sensors = objectMapper.readValue(jsonString, Sensor[].class);
			listSensor = Arrays.asList(sensors);
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
		}

		listM = new DefaultListModel();
		for (Sensor sensors : listSensor) {
			listM.addElement(sensors.getIdSensor() + "#" + sensors.getTypeSensor() + " " + sensors.getSensorState()
					+ " " + sensors.getIdCommonArea());
		}

		list = new JList(listM);
		sc = new JScrollPane(list);

		sc.setBounds(30, 120, 300, ((int) getToolkit().getScreenSize().getHeight() - 300));
		this.add(sc);

		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				index = list.locationToIndex(e.getPoint());
				String substring = listM.getElementAt(index).toString();
				int position = substring.indexOf("#");
				String id = substring.substring(0, position);

				requestType = "READ";
				sensor = new Sensor();
				table = "Sensor";
				sensor.setIdSensor(Integer.parseInt(id));
				try {
					jsonString = objectMapper.writeValueAsString(sensor);
					;
					new ClientSocket(requestType, jsonString, table);
					jsonString = ClientSocket.getJson();
					sensor = objectMapper.readValue(jsonString, Sensor.class);
				} catch (Exception e1) {
					logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
				}
				requestType = "READ";
				commonArea = new CommonArea();
				table = "CommonArea";
				ObjectMapper readMapper = new ObjectMapper();
				commonArea.setIdCommonArea(sensor.getIdCommonArea());
				try {
					jsonString = readMapper.writeValueAsString(commonArea);
					;
					new ClientSocket(requestType, jsonString, table);
					jsonString = ClientSocket.getJson();
					commonArea = readMapper.readValue(jsonString, CommonArea.class);
				} catch (Exception e1) {
					logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
				}
				
				textInputIdSensor.setText(Integer.toString(sensor.getIdSensor()));
				String str = commonArea.getNameCommonArea()+" #"+sensor.getIdCommonArea();
				for (int i=0; i<textInputNameCommonArea.getItemCount(); i++) {
					if(textInputNameCommonArea.getItemAt(i).toString().contains(str)) {
					textInputNameCommonArea.setSelectedIndex(i);
					}
				}
				textInputTypeSensor.setSelectedItem(sensor.getTypeSensor().toString());
				if (sensor.getSensorState() == true) {
					switchButton.setText("ON");
					switchButton.setBackground(Color.GREEN);
				} else {
					switchButton.setText("OFF");
					switchButton.setBackground(Color.RED);
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
		labelIdSensor = new JLabel("Id : ");
		labelIdSensor.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 2 / 10, 200, 30);
		labelIdSensor.setFont(policeLabel);
		this.add(labelIdSensor);

		/**
		 * Definition of label NameCommonArea
		 */
		labelNameCommonArea = new JLabel("Nom de la Partie Commune : ");
		labelNameCommonArea.setBounds((int) getToolkit().getScreenSize().getWidth() * 4 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 2 / 10, 250, 30);
		labelNameCommonArea.setFont(policeLabel);
		this.add(labelNameCommonArea);

		/**
		 * Definition of label TypeSensor
		 */
		labelTypeSensor = new JLabel("Type Capteur : ");
		labelTypeSensor.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 4 / 10, 200, 30);
		labelTypeSensor.setFont(policeLabel);
		this.add(labelTypeSensor);

		/**
		 * Definition of label StateSensor
		 */
		labelStateSensor = new JLabel("Etat du Capteur : ");
		labelStateSensor.setBounds((int) getToolkit().getScreenSize().getWidth() * 4 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 4 / 10, 200, 30);
		labelStateSensor.setFont(policeLabel);
		this.add(labelStateSensor);

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
		String[] types = { "SMOKE", "MOVE", "TEMPERATURE", "WINDOW", "DOOR", "ELEVATOR", "LIGHT", "FIRE", "BADGE",
				"ROUTER" };
		textInputTypeSensor = new JComboBox(types);
		textInputTypeSensor.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 9 / 20, 300, 40);
		textInputTypeSensor.setFont(policeLabel);
		this.add(textInputTypeSensor);
		textInputTypeSensor.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getItem().toString().equals("SMOKE"))
					sensor.setTypeSensor(SensorType.SMOKE);
				else if (e.getItem().toString().equals("MOVE"))
					sensor.setTypeSensor(SensorType.MOVE);
				else if (e.getItem().toString().equals("TEMPERATURE"))
					sensor.setTypeSensor(SensorType.TEMPERATURE);
				else if (e.getItem().toString().equals("WINDOW"))
					sensor.setTypeSensor(SensorType.WINDOW);
				else if (e.getItem().toString().equals("DOOR"))
					sensor.setTypeSensor(SensorType.DOOR);
				else if (e.getItem().toString().equals("ELEVATOR"))
					sensor.setTypeSensor(SensorType.ELEVATOR);
				else if (e.getItem().toString().equals("LIGHT"))
					sensor.setTypeSensor(SensorType.LIGHT);
				else if (e.getItem().toString().equals("FIRE"))
					sensor.setTypeSensor(SensorType.FIRE);
				else if (e.getItem().toString().equals("BADGE"))
					sensor.setTypeSensor(SensorType.BADGE);
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

		///////////////////////// BUTTON/////////////////////////////////////////////////
		/**
		 * Definition of Button AddSensor
		 */
		addSensor = new JButton("Ajouter");
		addSensor.setBounds(30, (int) getToolkit().getScreenSize().getHeight() - 150, 300, 40);
		this.add(addSensor);
		addSensor.addActionListener(new ActionListener() {

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

				String newTypeSensor = textInputTypeSensor.getSelectedItem().toString();
				if (newTypeSensor.equals("SMOKE"))
					sensor.setTypeSensor(SensorType.SMOKE);
				else if (newTypeSensor.equals("MOVE"))
					sensor.setTypeSensor(SensorType.MOVE);
				else if (newTypeSensor.equals("TEMPERATURE"))
					sensor.setTypeSensor(SensorType.TEMPERATURE);
				else if (newTypeSensor.equals("WINDOW"))
					sensor.setTypeSensor(SensorType.WINDOW);
				else if (newTypeSensor.equals("DOOR"))
					sensor.setTypeSensor(SensorType.DOOR);
				else if (newTypeSensor.equals("ELEVATOR"))
					sensor.setTypeSensor(SensorType.ELEVATOR);
				else if (newTypeSensor.equals("LIGHT"))
					sensor.setTypeSensor(SensorType.LIGHT);
				else if (newTypeSensor.equals("FIRE"))
					sensor.setTypeSensor(SensorType.FIRE);
				else if (newTypeSensor.equals("BADGE"))
					sensor.setTypeSensor(SensorType.BADGE);

				ObjectMapper insertMapper = new ObjectMapper();
				try {
					jsonString = insertMapper.writeValueAsString(sensor);
					new ClientSocket(requestType, jsonString, table);
					jsonString = ClientSocket.getJson();
					if (!jsonString.equals("INSERTED")) {
						JOptionPane.showMessageDialog(null, "L'ajout a échoué", "Erreur", JOptionPane.ERROR_MESSAGE);
						logger.log(Level.INFO, "Impossible to insert sensor");
					} else {
						logger.log(Level.INFO, "Insertion Succeded");
						requestType = "READ ALL";
						table = "Sensor";
						jsonString = "READ ALL";
						new ClientSocket(requestType, jsonString, table);
						jsonString = ClientSocket.getJson();
						Sensor[] sensors = objectMapper.readValue(jsonString, Sensor[].class);
						listSensor = Arrays.asList(sensors);

						int x = listSensor.size() - 1;

						sensor = listSensor.get(x);
						listM.addElement(sensor.getIdSensor() + "#" + sensor.getTypeSensor() + " "
								+ sensor.getSensorState() + " " + sensor.getIdCommonArea());
						JOptionPane.showMessageDialog(null, "Linsertion à été effectuer", "Infos",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (Exception e1) {
					logger.log(Level.INFO,
							"Impossible to parse in JSON Sensor Datas" + e1.getClass().getCanonicalName());
				}
			}
		});

		/**
		 * Definition of Button Save
		 */
		save = new JButton("Sauvegarder");
		save.setBounds(((int) getToolkit().getScreenSize().getWidth() * 2 / 4) + 250,
				(int) getToolkit().getScreenSize().getHeight() * 15 / 20, 200, 40);
		this.add(save);
		save.addActionListener(new ActionListener() {

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

				String newStateSensor = switchButton.getText();
				if (newStateSensor.equals("ON"))
					sensor.setSensorState(true);
				else
					sensor.setSensorState(false);

				String newTypeSensor = textInputTypeSensor.getSelectedItem().toString();
				if (newTypeSensor.equals("SMOKE"))
					sensor.setTypeSensor(SensorType.SMOKE);
				else if (newTypeSensor.equals("MOVE"))
					sensor.setTypeSensor(SensorType.MOVE);
				else if (newTypeSensor.equals("TEMPERATURE"))
					sensor.setTypeSensor(SensorType.TEMPERATURE);
				else if (newTypeSensor.equals("WINDOW"))
					sensor.setTypeSensor(SensorType.WINDOW);
				else if (newTypeSensor.equals("DOOR"))
					sensor.setTypeSensor(SensorType.DOOR);
				else if (newTypeSensor.equals("ELEVATOR"))
					sensor.setTypeSensor(SensorType.ELEVATOR);
				else if (newTypeSensor.equals("LIGHT"))
					sensor.setTypeSensor(SensorType.LIGHT);
				else if (newTypeSensor.equals("FIRE"))
					sensor.setTypeSensor(SensorType.FIRE);
				else if (newTypeSensor.equals("BADGE"))
					sensor.setTypeSensor(SensorType.BADGE);

				if (sensor.getIdSensor() == 0) {
					JOptionPane.showMessageDialog(null, "Veuillez selectionner un capteur à mettre a jour", "Erreur",
							JOptionPane.ERROR_MESSAGE);
				} else {
					ObjectMapper insertMapper = new ObjectMapper();
					try {
						jsonString = insertMapper.writeValueAsString(sensor);
						new ClientSocket(requestType, jsonString, table);
						jsonString = ClientSocket.getJson();
						if (!jsonString.equals("UPDATED")) {
							JOptionPane.showMessageDialog(null, "La Mise a jour a échoué", "Erreur",
									JOptionPane.ERROR_MESSAGE);
							logger.log(Level.INFO, "Impossible to update sensor");
						} else {
							logger.log(Level.INFO, "Update Succeded");
							listM.set(index, sensor.getIdSensor() + "#" + sensor.getTypeSensor() + " "
									+ sensor.getSensorState() + " " + sensor.getIdCommonArea());
							JOptionPane.showMessageDialog(null, "Données Mises à jours", "Infos",
									JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (Exception e1) {
						logger.log(Level.INFO,
								"Impossible to parse in JSON sensor datas" + e1.getClass().getCanonicalName());
					}
				}
			}
		});

		/**
		 * Definition of Button Restaure
		 */
		restaure = new JButton("Annuler");
		restaure.setBounds(((int) getToolkit().getScreenSize().getWidth() * 2 / 4),
				(int) getToolkit().getScreenSize().getHeight() * 15 / 20, 200, 40);
		this.add(restaure);
		restaure.addActionListener(new ActionListener() {
			/**
			 * when the button Restaure is pressed, we define the textAreas as the last
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
				textInputTypeSensor.setSelectedItem(sensor.getTypeSensor().toString());
				if (sensor.getSensorState() == true) {
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
		delete = new JButton("Supprimer");
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
							JOptionPane.showMessageDialog(null, "La suppression a échoué", "Erreur",
									JOptionPane.ERROR_MESSAGE);
							logger.log(Level.INFO, "Impossible to delete this sensor");
						} else {
							JOptionPane.showMessageDialog(null, "Suppression du capteur", "Infos",
									JOptionPane.INFORMATION_MESSAGE);
							logger.log(Level.INFO, "Deletion of sensor succed");
						}
					} catch (Exception e1) {
						logger.log(Level.INFO,
								"Impossible to parse in JSON Sensor datas" + e1.getClass().getCanonicalName());
					}
					listM.removeElementAt(index);
					textInputIdSensor.setText("");
					switchButton.setText("OFF");
					switchButton.setBackground(Color.RED);
				} else {
					JOptionPane.showMessageDialog(null, "Veuillez selectionner un capteur à supprimer", "Erreur",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		///////////////////////// WINDOW/////////////////////////////////////////////////
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
		for (CommonArea commonAreas : listCommonArea) {
			if (!areasAdd.contains(commonAreas.getNameCommonArea() + " #" + commonAreas.getIdCommonArea()))
				textInputNameCommonArea.addItem(commonAreas.getNameCommonArea() + " #" + commonAreas.getIdCommonArea());
			areasAdd = areasAdd + commonAreas.getNameCommonArea() + " #" + commonAreas.getIdCommonArea() + ",";
		}
		logger.log(Level.INFO, "Convertion of all Common Areas available in a list succed");
	}
	// TODO mettre la recherche de nom de partie commune
	// définir un thread qui va faire des requete toute les 10sec
	// TODO affiche pas la bonne partie commune
	//pas d'actualisation de la list des partie commune COmbobox
	//pas d'actualisation de la listM des capteurs a gauche créer une methode + la synchro
}
