package com.blackmamba.deathkiss.gui.dev;

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

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

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
	private JTextField textInputNameCommonArea; // TODO changer faire une lite déroulante avec tout les nom de partie
												// commune
	private JTextField textInputTypeSensor; // TODO mettre liste déroulante
	private Font policeBar;
	private Font policeLabel;
	private JButton disconnection;
	private JButton addSensor;
	private JButton save;
	private JButton restaure;
	private JButton delete;
	private Sensor sensor;
	private CommonArea commonArea;
	private ObjectMapper objectMapper;
	private List<Sensor> listSensor = new ArrayList<Sensor>();
	private static final Logger logger = LogManager.getLogger(TabProfile.class);
	private DefaultListModel listM;
	private JList list;
	private JScrollPane sc;

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
			listM.addElement(sensors.getIdSensor() + "# " + sensors.getTypeSensor() + " " + sensors.getSensorState()
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
				textInputIdSensor.setText(Integer.toString(sensor.getIdSensor()));
				textInputNameCommonArea.setText("");// TODO faire une recherche du nom de la partie commune
				// textInputTypeSensor.setText(sensor.getTypeSensor());//TODO faire la liste
				// déroulante
				// TODO mettre le state du sensor;
			}
		};
		list.addMouseListener(mouseListener);
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
		if (sensor.getIdSensor() == 0)
			textInputIdSensor.setText("");
		else
			textInputIdSensor.setText(Integer.toString(sensor.getIdSensor()));
		textInputIdSensor.setEditable(false);
		this.add(textInputIdSensor);

		/**
		 * Definition of textArea NameCommonArea
		 */
		textInputNameCommonArea = new JTextField();
		textInputNameCommonArea.setBounds((int) getToolkit().getScreenSize().getWidth() * 4 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 5 / 20, 300, 40);
		textInputNameCommonArea.setFont(policeLabel);
		textInputNameCommonArea.setText("");// TODO set = commonArea.getNameCommonAre() on doit pour ca faire une
											// requete en fonction de l'idsensor recuperer apres le clic sur la ligne
											// dans la list
		this.add(textInputNameCommonArea);

		/**
		 * Definition of textArea TypeSensor
		 */
		textInputTypeSensor = new JPasswordField();
		textInputTypeSensor.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 9 / 20, 300, 40);
		textInputTypeSensor.setFont(policeLabel);
		textInputTypeSensor.setText("");// TODO mettre un des choix de la liste déroulante qui est equal a celui de
										// l'objet Sensor
		this.add(textInputTypeSensor);

		/**
		 * Definition of textArea StateSensor
		 */
		// TODO mettre un check box
		// textInputFunctionEmployee = new JTextField();
		// textInputFunctionEmployee.setBounds((int)
		// getToolkit().getScreenSize().getWidth() * 4 / 7,
		// (int) getToolkit().getScreenSize().getHeight() * 9 / 20, 300, 40);
		// textInputFunctionEmployee.setFont(policeLabel);
		// textInputFunctionEmployee.setText(employee.getPoste());
		// this.add(textInputFunctionEmployee);

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
				// TODO Auto-generated method stub

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
				// TODO Auto-generated method stub

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

			@Override
			public void actionPerformed(ActionEvent e) {
				textInputIdSensor.setText(Integer.toString(sensor.getIdCommonArea()));
				textInputNameCommonArea.setText(commonArea.getNameCommonArea());
				textInputTypeSensor.setText("");// TODO mettre la valeur de la list
				// TODO mettre l'etat du capteur

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

			@Override
			public void actionPerformed(ActionEvent e) {
				if (sensor.getIdSensor() != 0) {
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
					}
				} catch (Exception e1) {
					logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
				}
				listM.removeElementAt(index);
				textInputIdSensor.setText("");
				textInputNameCommonArea.setText("");
				textInputTypeSensor.setText("");
				// TODO mettre l'etat du sensor a false
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
}
