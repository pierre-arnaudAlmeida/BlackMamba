package com.blackmamba.deathkiss.gui.prod;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.blackmamba.deathkiss.entity.Resident;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ProfilResident extends JFrame {
	private JPanel contentPane;
	private JTextField nameResidentField;
	private JTextField lastnameResidentField;
	private String requestType;
	private String table;
	private String jsonString;
	private Resident resident;
	private static Logger logger = Logger.getLogger("logger");

	public ProfilResident(int idResident) {

		setTitle("Profil Resident");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		requestType = "READ";
		resident = new Resident();
		table = "Resident";
		ObjectMapper readMapper = new ObjectMapper();
		resident.setIdResident(idResident);
		try {
			jsonString = readMapper.writeValueAsString(resident);
			;
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			resident = readMapper.readValue(jsonString, Resident.class);
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
		}

		// Creation of label idResident
		// And display on the contentPane
		JLabel idresident = new JLabel("Id Resident : " + resident.getIdResident());
		idresident.setBounds(147, 30, 214, 14);
		contentPane.add(idresident);

		// Creation of label nameResident
		// And display on the contentPane
		JLabel name = new JLabel("Nom Resident : " + resident.getNameResident());
		name.setBounds(147, 60, 214, 14);
		contentPane.add(name);

		// Creation of TextField for nameResident
		// And display on the contentPane
		nameResidentField = new JTextField();
		nameResidentField.setBounds(147, 90, 214, 20);
		contentPane.add(nameResidentField);
		nameResidentField.setColumns(10);

		// Creation of label StageResident
		// And display on the contentPane
		JLabel lastanme = new JLabel("Prenom Resident : " + resident.getLastnameResident());
		lastanme.setBounds(147, 120, 214, 14);
		contentPane.add(lastanme);

		// Creation of TextField for stageResident
		// And display on the contentPane
		lastnameResidentField = new JTextField();
		lastnameResidentField.setBounds(147, 150, 214, 20);
		contentPane.add(lastnameResidentField);
		lastnameResidentField.setColumns(10);

		// Creation of a delete button
		// And display on the contentPane
		JButton deleteButton = new JButton("Supprimer");
		deleteButton.setBounds(147, 210, 214, 23);
		contentPane.add(deleteButton);
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				requestType = "DELETE";
				resident = new Resident();
				table = "Resident";
				resident.setIdResident(idResident);
				ObjectMapper connectionMapper = new ObjectMapper();
				try {
					jsonString = connectionMapper.writeValueAsString(resident);
					new ClientSocket(requestType, jsonString, table);
					jsonString = ClientSocket.getJson();
					if (!jsonString.equals("DELETED")) {
						JOptionPane.showMessageDialog(null, "La suppression a échoué", "Erreur",
								JOptionPane.ERROR_MESSAGE);
						logger.log(Level.INFO, "Impossible to delete this resident");
					}
				} catch (Exception e1) {
					logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
				}

				try {
					ListResident frame = new ListResident();
					frame.setVisible(true);
					setVisible(false);
					dispose();
				} catch (Exception e1) {
					logger.log(Level.INFO,
							"Impossible redirect to window 'ProfilResident' " + e1.getClass().getCanonicalName());
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
			 * If they click in backButton c'est will redirect to InsertionResident
			 */
			public void actionPerformed(ActionEvent e) {
				try {
					ListResident frame = new ListResident();
					frame.setVisible(true);
					setVisible(false);
					dispose();
				} catch (Exception e1) {
					logger.log(Level.INFO,
							"Impossible to redirect to window 'ListResident' " + e1.getClass().getCanonicalName());
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
				resident = new Resident();
				table = "Resident";

				String nameResidentfield = nameResidentField.getText().trim();
				String lastnameResidentfield = lastnameResidentField.getText().trim();

				if (nameResidentfield.equals("") && lastnameResidentfield.equals("")) {
					JOptionPane.showMessageDialog(null, "Champs vide", "Erreur", JOptionPane.ERROR_MESSAGE);
				} else {
					if (nameResidentfield.equals("") && !(lastnameResidentfield.equals(""))) {
						resident.setLastnameResident(lastnameResidentfield);
						resident.setNameResident("");
						resident.setIdResident(idResident);
					} else if (!(nameResidentfield.equals("")) && lastnameResidentfield.equals("")) {
						resident.setNameResident(nameResidentfield);
						resident.setLastnameResident("");
						resident.setIdResident(idResident);
					} else if (!(nameResidentfield.equals("")) && !(lastnameResidentfield.equals(""))) {
						resident.setNameResident(nameResidentfield);
						resident.setLastnameResident(lastnameResidentfield);
						resident.setIdResident(idResident);
					}
					ObjectMapper connectionMapper = new ObjectMapper();
					try {
						jsonString = connectionMapper.writeValueAsString(resident);
						new ClientSocket(requestType, jsonString, table);
						jsonString = ClientSocket.getJson();
						if (!jsonString.equals("UPDATED")) {
							JOptionPane.showMessageDialog(null, "La mise a jour a échoué", "Erreur",
									JOptionPane.ERROR_MESSAGE);
							logger.log(Level.INFO, "Impossible to update Resident");
						}
					} catch (Exception e1) {
						logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
					}
				}
				try {
					ProfilResident frame = new ProfilResident(idResident);
					frame.setVisible(true);
					setVisible(false);
					dispose();
				} catch (Exception e1) {
					logger.log(Level.INFO,
							"Impossible redirect to window 'ProfilResident' " + e1.getClass().getCanonicalName());
				}
			}
		});

	}

}
