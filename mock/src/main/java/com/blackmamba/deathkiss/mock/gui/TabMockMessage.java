package com.blackmamba.deathkiss.mock.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.blackmamba.deathkiss.mock.GenerateMessage;
import com.blackmamba.deathkiss.mock.MockSocket;
import com.blackmamba.deathkiss.mock.entity.Message;
import com.blackmamba.deathkiss.mock.entity.Sensor;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TabMockMessage extends JPanel {

	/**
	 * Different parameters used
	 */
	private static final long serialVersionUID = 1L;
	private int nbThread;
	private int threshold;
	private int NbOfWindow;
	private int nbMessageGenerate;
	private boolean bool;
	private String requestType;
	private String table;
	private String jsonString;
	private Font policeLabel;
	private JLabel labelIdSensor;
	private JLabel labelThreshold;
	private JLabel labelSensorType;
	private JLabel labelRandomSensor;
	private JLabel labelMessageGenerate;
	private JTextField textInputIdSensor;
	private JSlider sliderThreshold;
	private ButtonGroup buttonGroup;
	private JRadioButton positiveRadio;
	private JRadioButton negativeRadio;
	private JButton generateButton;
	private JButton restaureButton;
	private Thread threadGenerateMessage;
	private Message message;
	private Sensor sensor;
	private ObjectMapper objectMapper;
	private JComboBox<String> textInputTypeSensor;
	private List<Sensor> listSensor = new ArrayList<Sensor>();
	private List<Sensor> listSensorRestricted = new ArrayList<Sensor>();
	private static final Logger logger = LogManager.getLogger(TabMockMessage.class);
	private GenerateMessage generateMessage;

	public TabMockMessage() {

	}

	public TabMockMessage(Color color, String title) {
		///////////////////////// LABEL///////////////////////////////////////////////
		/**
		 * Definition of label IdSensor
		 */
		policeLabel = new Font("Arial", Font.BOLD, 20);
		labelIdSensor = new JLabel("Id Capteur : ");
		labelIdSensor.setBounds(100, 100, 200, 30);
		labelIdSensor.setFont(policeLabel);
		this.add(labelIdSensor);

		/**
		 * Definition of label Threshold
		 */
		labelThreshold = new JLabel("Seuil : ");
		labelThreshold.setBounds(100, 200, 200, 30);
		labelThreshold.setFont(policeLabel);
		this.add(labelThreshold);

		/**
		 * Definition of label SensorType
		 */
		labelSensorType = new JLabel("Type capteur : ");
		labelSensorType.setBounds(400, 200, 200, 30);
		labelSensorType.setFont(policeLabel);
		this.add(labelSensorType);

		/**
		 * Definition of label RandomSensor
		 */
		labelRandomSensor = new JLabel("Capteur Aleatoire : ");
		labelRandomSensor.setBounds(400, 100, 200, 30);
		labelRandomSensor.setFont(policeLabel);
		this.add(labelRandomSensor);

		/**
		 * Definition of label MessageGenerate
		 */
		labelMessageGenerate = new JLabel();
		labelMessageGenerate.setBounds(250, 450, 350, 30);
		labelMessageGenerate.setFont(policeLabel);
		labelMessageGenerate.setText("Nombre de messages généré : " + nbMessageGenerate);
		this.add(labelMessageGenerate);
		//////////////////// TEXT AREA////////////////////////////////////////////////
		/**
		 * Definition of textArea IdSensor
		 */
		textInputIdSensor = new JTextField();
		textInputIdSensor.setBounds(100, 150, 250, 30);
		textInputIdSensor.setFont(policeLabel);
		textInputIdSensor.setText("");
		this.add(textInputIdSensor);

		/**
		 * Definition of Slider Threshold
		 */
		sliderThreshold = new JSlider();
		sliderThreshold.setPaintTicks(true);
		sliderThreshold.setPaintLabels(true);
		sliderThreshold.setMaximum(100);
		sliderThreshold.setMinimum(0);
		sliderThreshold.setValue(30);
		sliderThreshold.setMinorTickSpacing(5);
		sliderThreshold.setMajorTickSpacing(10);

		sliderThreshold.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				if (textInputTypeSensor.getSelectedItem().equals("ELEVATOR")) {
					labelThreshold.setText("Seuil : " + ((JSlider) event.getSource()).getValue() + "00kg");
					threshold = ((JSlider) event.getSource()).getValue() * 100;
					if (generateMessage != null) {
						generateMessage.setThreshold(((JSlider) event.getSource()).getValue() * 100);
					}
				} else if (textInputTypeSensor.getSelectedItem().equals("SMOKE")) {
					labelThreshold.setText("Seuil : " + ((JSlider) event.getSource()).getValue() + "0ppm");
					threshold = ((JSlider) event.getSource()).getValue() * 10;
					if (generateMessage != null) {
						generateMessage.setThreshold(((JSlider) event.getSource()).getValue() * 10);
					}
				} else {
					labelThreshold.setText("Seuil : " + ((JSlider) event.getSource()).getValue());
					threshold = ((JSlider) event.getSource()).getValue();
					if (generateMessage != null) {
						generateMessage.setThreshold(((JSlider) event.getSource()).getValue());
					}
				}
			}
		});

		sliderThreshold.setBackground(color);
		sliderThreshold.setBounds(100, 250, 250, 50);
		this.add(sliderThreshold);

		/**
		 * Definition of ButtonGroup RandomSensor
		 */
		positiveRadio = new JRadioButton("Oui");
		positiveRadio.setBounds(400, 150, 100, 30);
		positiveRadio.setFont(policeLabel);
		positiveRadio.setBackground(color);

		negativeRadio = new JRadioButton("Non");
		negativeRadio.setBounds(500, 150, 100, 30);
		negativeRadio.setFont(policeLabel);
		negativeRadio.setBackground(color);

		buttonGroup = new ButtonGroup();
		buttonGroup.add(positiveRadio);
		buttonGroup.add(negativeRadio);

		this.add(positiveRadio);
		this.add(negativeRadio);

		/**
		 * Definition of ComboBox TypeSensor
		 */
		String[] types = { "SMOKE", "MOVE", "TEMPERATURE", "WINDOW", "DOOR", "ELEVATOR", "LIGHT", "FIRE", "BADGE",
				"ROUTER" };
		textInputTypeSensor = new JComboBox<String>(types);
		textInputTypeSensor.setBounds(400, 250, 250, 40);
		textInputTypeSensor.setFont(policeLabel);
		textInputTypeSensor.setSelectedItem(null);
		this.add(textInputTypeSensor);
		textInputTypeSensor.addItemListener(new ItemListener() {
			/**
			 * All type of Sensor
			 */
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getItem().toString().equals("SMOKE")) {
					sliderThreshold.setMaximum(50);
					sliderThreshold.setMinimum(0);
					sliderThreshold.setValue(10);
					sliderThreshold.setMinorTickSpacing(5);
					sliderThreshold.setMajorTickSpacing(10);
				} else if (e.getItem().toString().equals("MOVE") || e.getItem().toString().equals("WINDOW")
						|| e.getItem().toString().equals("DOOR") || e.getItem().toString().equals("LIGHT")
						|| e.getItem().toString().equals("FIRE") || e.getItem().toString().equals("BADGE")
						|| e.getItem().toString().equals("ROUTER")) {
					sliderThreshold.setMaximum(1);
					sliderThreshold.setMinimum(0);
					sliderThreshold.setValue(0);
				} else if (e.getItem().toString().equals("TEMPERATURE")) {
					sliderThreshold.setMaximum(50);
					sliderThreshold.setMinimum(-20);
					sliderThreshold.setValue(20);
					sliderThreshold.setMinorTickSpacing(5);
					sliderThreshold.setMajorTickSpacing(10);
				} else if (e.getItem().toString().equals("ELEVATOR")) {
					sliderThreshold.setMaximum(8);
					sliderThreshold.setMinimum(0);
					sliderThreshold.setValue(4);
					sliderThreshold.setMinorTickSpacing(1);
					sliderThreshold.setMajorTickSpacing(2);
				}
			}
		});
		///////////////////////// BUTTON/////////////////////////////////////////////////
		Thread threadCountMessage = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					nbMessageGenerate = generateMessage.getNbMessageGenerate();
					labelMessageGenerate.setText("Nombre de messages généré : " + nbMessageGenerate);
				}
			}
		});

		/**
		 * Definition of Button AddSensor
		 */
		generateButton = new JButton("Generer");
		generateButton.setBounds(100, 350, 100, 40);
		this.add(generateButton);
		generateButton.addActionListener(new ActionListener() {
			@Override
			public synchronized void actionPerformed(ActionEvent e) {
				if (textInputTypeSensor.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(null, "Vous n'avez pas choisi de type de capteur", "Infos",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					if (positiveRadio.isSelected() == false && negativeRadio.isSelected() == false) {
						JOptionPane.showMessageDialog(null,
								"Vous n'avez pas choisi la forme de génération aléatoire ou non ?", "Infos",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						if (textInputIdSensor.getText().trim().equals("") && negativeRadio.isSelected() == true) {
							JOptionPane.showMessageDialog(null, "Vous n'avez pas renseigné l'id du capteur", "Infos",
									JOptionPane.INFORMATION_MESSAGE);
						} else {
							if ((textInputIdSensor.getText().trim().matches("[0-9]+[0-9]*")
									&& negativeRadio.isSelected() == true) || positiveRadio.isSelected() == true) {
								message = new Message();
								if (positiveRadio.isSelected() == true) {
									// TODO verifier si on peut pas faire un find ou find all pour recuperer les
									// capteur avec le type capteur
									requestType = "READ ALL";
									table = "Sensor";
									objectMapper = new ObjectMapper();
									try {
										jsonString = "READ ALL";
										new MockSocket(requestType, jsonString, table);
										jsonString = MockSocket.getJson();
										Sensor[] sensors = objectMapper.readValue(jsonString, Sensor[].class);
										listSensor = Arrays.asList(sensors);
										logger.log(Level.INFO, "Find Sensor data succed");
									} catch (Exception e1) {
										logger.log(Level.INFO, "Impossible to parse in JSON Sensor data "
												+ e1.getClass().getCanonicalName());
									}
									listSensorRestricted.removeAll(listSensorRestricted);
									for (Sensor sensors : listSensor) {
										if (sensors.getTypeSensor().toString()
												.equals(textInputTypeSensor.getSelectedItem().toString()) && sensors.getSensorState()==true) {
											listSensorRestricted.add(sensors);
										}
									}
								} else {
									requestType = "READ";
									sensor = new Sensor();
									table = "Sensor";
									sensor.setIdSensor(Integer.parseInt(textInputIdSensor.getText().trim()));
									try {
										jsonString = objectMapper.writeValueAsString(sensor);
										new MockSocket(requestType, jsonString, table);
										jsonString = MockSocket.getJson();
										sensor = objectMapper.readValue(jsonString, Sensor.class);
										logger.log(Level.INFO, "Find Sensor data succed");
									} catch (Exception e1) {
										logger.log(Level.INFO, "Impossible to parse in JSON Sensor datas "
												+ e1.getClass().getCanonicalName());
									}
									if (sensor.getTypeSensor().toString()
											.equals(textInputTypeSensor.getSelectedItem().toString())) {
										message.setIdSensor(sensor.getIdSensor());
										listSensorRestricted.add(sensor);
									} else {
										JOptionPane.showMessageDialog(null,
												"Ce capteur, n'existe pas pour cet ID et ce type de capteur", "Infos",
												JOptionPane.INFORMATION_MESSAGE);
									}
								}
								if (nbThread == 0) {
									bool = true;
									generateMessage = new GenerateMessage(message, listSensorRestricted);
									generateMessage.setThreshold(threshold);
									generateMessage.start();
									nbThread++;
									NbOfWindow++;
								}
								if (NbOfWindow == 1) {
									threadCountMessage.start();
								}
							} else {
								JOptionPane.showMessageDialog(null, "Veuillez inserer un chiffre pour id capteur",
										"Infos", JOptionPane.INFORMATION_MESSAGE);
							}
						}
					}
				}
			}
		});

		/**
		 * Set to default values the different fields
		 */
		restaureButton = new JButton("Annuler");
		restaureButton.setBounds(500, 350, 100, 40);
		this.add(restaureButton);
		restaureButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textInputIdSensor.setText("");
				textInputTypeSensor.setSelectedItem(null);
				negativeRadio.setSelected(true);
				labelThreshold.setText("Seuil : ");
			}
		});

		/**
		 * Stop the generation of message with the boolean bool and restart the number
		 * of thread
		 */
		restaureButton = new JButton("Stopper la generation");
		restaureButton.setBounds(250, 350, 200, 40);
		this.add(restaureButton);
		restaureButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				generateMessage.setBool(false);
				bool = false;
				nbThread = 0;
				logger.log(Level.INFO, "Generation stopped");
			}
		});
		///////////////////////// FRAME/////////////////////////////////////////////////
		/**
		 * Different parameters of the window
		 */
		this.setLayout(new BorderLayout());
		this.setBackground(color);
	}

	public Thread getThreadGenerateMessage() {
		return threadGenerateMessage;
	}

	public void setThreadGenerateMessage(Thread threadGenerateMessage) {
		this.threadGenerateMessage = threadGenerateMessage;
	}
}
