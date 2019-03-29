package com.blackmamba.deathkiss.gui.prod;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.blackmamba.deathkiss.entity.Resident;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ListResident extends JFrame {

	// Definition of differents fields
	private JPanel contentPane;
	private String requestType;
	private String table;
	private String jsonString;
	private Resident resident;
	private List<Resident> listResident = new ArrayList();
	private static Logger logger = Logger.getLogger("logger");

	public ListResident() {
		setTitle("Liste Resident");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Creation of a back button
		// And display on the contentPane
		JButton backButton = new JButton("Ajouter un employé");
		backButton.setBounds(147, 0, 214, 23);
		contentPane.add(backButton);

		backButton.addActionListener(new ActionListener() {
			/**
			 * If they click in backButton it will be redirect to InsertionClient
			 */
			public void actionPerformed(ActionEvent e) {
				try {
					InsertionEmployee frame = new InsertionEmployee();
					frame.setVisible(true);
					setVisible(false);
					logger.log(Level.INFO, "Back to Insertion Employee");
					dispose();
				} catch (Exception e1) {
					logger.log(Level.INFO,
							"Impossible redirect to window 'InsertionClient' " + e1.getClass().getCanonicalName());
				}
			}
		});

		// Creation of a insert button
		// And display on the contentPane
		JButton insertButton = new JButton("Ajouter un resident");
		insertButton.setBounds(147, 30, 214, 23);
		contentPane.add(insertButton);

		insertButton.addActionListener(new ActionListener() {
			/**
			 * If they click in backButton c'est will redirect to InsertionClient
			 */
			public void actionPerformed(ActionEvent e) {
				try {
					InsertionResident frame = new InsertionResident();
					frame.setVisible(true);
					setVisible(false);
					logger.log(Level.INFO, "Go to Insertion Resident");
					dispose();
				} catch (Exception e1) {
					logger.log(Level.INFO,
							"Impossible redirect to window 'InsertionResident' " + e1.getClass().getCanonicalName());
				}
			}
		});

		requestType = "READ ALL";
		resident = new Resident();
		table = "Employee";
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			jsonString = "READ ALL";
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			Resident[] resident = objectMapper.readValue(jsonString, Resident[].class);
			listResident = Arrays.asList(resident);
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
		}
		int x = 0;
		for (Resident resident : listResident) {
			x = x + 30;
			// Creation of label id resident
			// And display on the contentPane
			JLabel idresident = new JLabel("Id : " + resident.getIdResident());
			idresident.setBounds(10, x, 100, 14);
			contentPane.add(idresident);

			// Creation of label name
			// And display on the contentPane
			JLabel name = new JLabel("Pr\u00E9nom : " + resident.getNameResident());
			name.setBounds(240, x, 200, 14);
			contentPane.add(name);

			// Creation of label lastname
			// And display on the contentPane
			JLabel lastname = new JLabel("Nom : " + resident.getLastnameResident());
			lastname.setBounds(100, x, 200, 14);
			contentPane.add(lastname);

			// Creation of a access button
			// And display on the contentPane
			JButton accessButton = new JButton("Voir");
			accessButton.setBounds(400, x, 100, 23);
			contentPane.add(accessButton);

			accessButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						ProfilResident frame = new ProfilResident(resident.getIdResident());
						frame.setVisible(true);
						setVisible(false);
						logger.log(Level.INFO, "Go to Profil Resident");
						dispose();
					} catch (Exception e1) {
						logger.log(Level.INFO,
								"Impossible redirect to window 'ProfilResident' " + e1.getClass().getCanonicalName());
					}
				}
			});

		}
	}

}
