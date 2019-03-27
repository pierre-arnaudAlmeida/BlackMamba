package com.blackmamba.deathkiss.gui.prod;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.blackmamba.deathkiss.entity.CommonArea;
import com.blackmamba.deathkiss.entity.Sensor;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProfilCommonArea extends JFrame {
	private JPanel contentPane;
	private JTextField nameCommonAreaField;
	private JTextField stageCommonAreaField;
	private String requestType;
	private String table;
	private String jsonString;
	private int nbSensor;
	private CommonArea commonArea;
	private List<Sensor> listDeSensor = new ArrayList();
	private static Logger logger = Logger.getLogger("logger");

	public ProfilCommonArea(int idCommonArea) {
		setTitle("Profil CommonArea");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		requestType = "READ";
		commonArea = new CommonArea();
		table = "CommonArea";
		ObjectMapper readMapper = new ObjectMapper();
		commonArea.setIdCommonArea(idCommonArea);
		try {
			jsonString = readMapper.writeValueAsString(commonArea);
			;
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			commonArea = readMapper.readValue(jsonString, CommonArea.class);
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
		}

		// Creation of label idCommonArea
		// And display on the contentPane
		JLabel idcommonArea = new JLabel("Id Partie Commune : " + commonArea.getIdCommonArea());
		idcommonArea.setBounds(147, 30, 214, 14);
		contentPane.add(idcommonArea);

		// Creation of label nameCommonArea
		// And display on the contentPane
		JLabel nameCommonArea = new JLabel("Nom Partie Commune : " + commonArea.getNameCommonArea());
		nameCommonArea.setBounds(147, 60, 214, 14);
		contentPane.add(nameCommonArea);

		// Creation of TextField for nameCommonArea
		// And display on the contentPane
		nameCommonAreaField = new JTextField();
		nameCommonAreaField.setBounds(147, 90, 214, 20);
		contentPane.add(nameCommonAreaField);
		nameCommonAreaField.setColumns(10);

		// Creation of label StageCommonArea
		// And display on the contentPane
		JLabel stageCommonArea = new JLabel("Etage Partie Commune : " + commonArea.getEtageCommonArea());
		stageCommonArea.setBounds(147, 120, 214, 14);
		contentPane.add(stageCommonArea);

		// Creation of TextField for stageCommonArea
		// And display on the contentPane
		stageCommonAreaField = new JTextField();
		stageCommonAreaField.setBounds(147, 150, 214, 20);
		contentPane.add(stageCommonAreaField);
		stageCommonAreaField.setColumns(10);

		// Creation of label Number of Sensor in CommonArea
		// And display on the contentPane
		JLabel nbSensorCommonArea = new JLabel("Nombre de capteur : ");// TODO probleme connaitre la taille de la list
		nbSensorCommonArea.setBounds(147, 180, 214, 14);
		contentPane.add(nbSensorCommonArea);

		// Creation of a delete button
		// And display on the contentPane
		JButton deleteButton = new JButton("Supprimer");
		deleteButton.setBounds(147, 210, 214, 23);
		contentPane.add(deleteButton);
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				requestType = "DELETE";
				commonArea = new CommonArea();
				table = "CommonArea";
				commonArea.setIdCommonArea(idCommonArea);
				ObjectMapper connectionMapper = new ObjectMapper();
				try {
					jsonString = connectionMapper.writeValueAsString(commonArea);
					new ClientSocket(requestType, jsonString, table);
					jsonString = ClientSocket.getJson();
					if (!jsonString.equals("DELETED")) {
						JOptionPane.showMessageDialog(null, "La suppression a échoué", "Erreur",
								JOptionPane.ERROR_MESSAGE);
						logger.log(Level.INFO, "Impossible to delete this employee");
					}
				} catch (Exception e1) {
					logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
				}

				try {
					ListCommonArea frame = new ListCommonArea();
					frame.setVisible(true);
					setVisible(false);
					dispose();
				} catch (Exception e1) {
					logger.log(Level.INFO,
							"Impossible redirect to window 'ProfilClient' " + e1.getClass().getCanonicalName());
				}
			}
		});

		// Creation of a back button
		// And display on the contentPane
		JButton backButton = new JButton("Retour");
		backButton.setBounds(147, 0, 214, 23);
		contentPane.add(backButton);
		backButton.addActionListener(new ActionListener() {
			/**
			 * If they click in backButton c'est will redirect to InsertionClient
			 */
			public void actionPerformed(ActionEvent e) {
				try {
					ListCommonArea frame = new ListCommonArea();
					frame.setVisible(true);
					setVisible(false);
					dispose();
				} catch (Exception e1) {
					logger.log(Level.INFO,
							"Impossible to redirect to window 'ListCommonArea' " + e1.getClass().getCanonicalName());
				}
			}
		});

		// Creation of a Update button
		// And display on the contentPane
		JButton updateButton = new JButton("Sauvegarder");
		updateButton.setBounds(147, 240, 214, 23);
		contentPane.add(updateButton);
		updateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				requestType = "UPDATE";
				commonArea = new CommonArea();
				table = "CommonArea";

				String nameCommonAreafield = nameCommonAreaField.getText().trim();
				String stageCommonAreafield = stageCommonAreaField.getText().trim();

				if (nameCommonAreafield.equals("") && stageCommonAreafield.equals("")) {
					JOptionPane.showMessageDialog(null, "Champs vide", "Erreur", JOptionPane.ERROR_MESSAGE);
				} else {
					if (nameCommonAreafield.equals("") && !(stageCommonAreafield.equals(""))) {
						commonArea.setEtageCommonArea(Integer.parseInt(stageCommonAreafield));
						commonArea.setNameCommonArea("");
						commonArea.setIdCommonArea(idCommonArea);
					} else if (!(nameCommonAreafield.equals("")) && stageCommonAreafield.equals("")) {
						commonArea.setNameCommonArea(nameCommonAreafield);
						commonArea.setEtageCommonArea(99);
						commonArea.setIdCommonArea(idCommonArea);
					} else if (!(nameCommonAreafield.equals("")) && !(stageCommonAreafield.equals(""))) {
						commonArea.setNameCommonArea(nameCommonAreafield);
						commonArea.setEtageCommonArea(Integer.parseInt(stageCommonAreafield));
						commonArea.setIdCommonArea(idCommonArea);
					}
					ObjectMapper connectionMapper = new ObjectMapper();
					try {
						jsonString = connectionMapper.writeValueAsString(commonArea);
						new ClientSocket(requestType, jsonString, table);
						jsonString = ClientSocket.getJson();
						if (!jsonString.equals("UPDATED")) {
							JOptionPane.showMessageDialog(null, "La mise a jour a échoué", "Erreur",
									JOptionPane.ERROR_MESSAGE);
							logger.log(Level.INFO, "Impossible to update CommonArea");
						}
					} catch (Exception e1) {
						logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
					}
				}
				try {
					ProfilCommonArea frame = new ProfilCommonArea(idCommonArea);
					frame.setVisible(true);
					setVisible(false);
					dispose();
				} catch (Exception e1) {
					logger.log(Level.INFO,
							"Impossible redirect to window 'ProfilCommonArea' " + e1.getClass().getCanonicalName());
				}
			}
		});

		// Creation of label ListSensors
		// And display on the contentPane
		JLabel listSensor = new JLabel("Liste des capteurs : ");
		listSensor.setBounds(147, 270, 214, 14);
		contentPane.add(listSensor);

		requestType = "FIND ALL";
		Sensor sensor = new Sensor();
		table = "Sensor";
		sensor.setIdCommonArea(idCommonArea);
		try {
			jsonString = readMapper.writeValueAsString(sensor);
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			Sensor[] listSensors = readMapper.readValue(jsonString, Sensor[].class);
			listDeSensor = Arrays.asList(listSensors);
			commonArea.setListSensor(listDeSensor);
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
		}
		int x = 270;
		for (Sensor sensors : listDeSensor) {
			x = x + 30;
			// Creation of label idCommonArea
			// And display on the contentPane
			JLabel idSensor = new JLabel("Id : " + sensors.getIdSensor());
			idSensor.setBounds(10, x, 100, 14);
			contentPane.add(idSensor);

			// Creation of label name
			// And display on the contentPane
			JLabel nameSensor = new JLabel("Type du capteur : " + sensors.getTypeSensor());
			nameSensor.setBounds(100, x, 200, 14);
			contentPane.add(nameSensor);

			// Creation of label stage
			// And display on the contentPane
			JLabel stateSensor = new JLabel("Etat : " + sensors.getSensorState());
			stateSensor.setBounds(240, x, 200, 14);
			contentPane.add(stateSensor);

			// Creation of a access button
			// And display on the contentPane
			JButton accessButton = new JButton("Voir");
			accessButton.setBounds(400, x, 100, 23);
			contentPane.add(accessButton);

			accessButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						// TODO créer la classe ProfilSensor
						// ProfilSensor frame = new ProfilSensor(sensors.getIdSensor());
						// frame.setVisible(true);
						// setVisible(false);
						// logger.log(Level.INFO, "Go to Profil Common Area");
						// dispose();
					} catch (Exception e1) {
						logger.log(Level.INFO,
								"Impossible redirect to window 'CommonArea' " + e1.getClass().getCanonicalName());
					}
				}
			});
		}
	}
}
