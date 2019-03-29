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


public class InsertionResident extends JFrame {

	// Definition of differents fields
	private JPanel contentPane;
	private JTextField nameResidentField;
	private JTextField lastnameResidentField;
	private Resident resident;
	private String requestType;
	private String table;
	private String jsonString;
	private static Logger logger = Logger.getLogger("logger");

	public InsertionResident() {

		setTitle("Insertion Resident");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Creation of a profil button
		// And display on the contentPane
		JButton profilbouton = new JButton("Liste des employes");
		profilbouton.setBounds(147, 0, 214, 23);
		contentPane.add(profilbouton);
		profilbouton.addActionListener(new ActionListener() {
			/**
			 * If we click in the profil button we are redirect to the window 'ProfilClient'
			 * where we have the list of users
			 */
			public void actionPerformed(ActionEvent e) {
				try {
					ListEmployee frame = new ListEmployee();
					frame.setVisible(true);
					setVisible(false);
				} catch (Exception e1) {
					logger.log(Level.INFO,
							"Impossible to access at window 'ListEmployee' " + e1.getClass().getCanonicalName());
				}
			}
		});

		// Creation of a list common area button
		// And display on the contentPane
		JButton listCommonAreabouton = new JButton("Liste des Parties Communes");
		listCommonAreabouton.setBounds(147, 30, 214, 23);
		contentPane.add(listCommonAreabouton);
		listCommonAreabouton.addActionListener(new ActionListener() {
			/**
			 * If we click in the listCommonAreabouton we are redirect to the window
			 * 'CommonArea' where we have the list of users
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ListResident frame = new ListResident();
					frame.setVisible(true);
					setVisible(false);
				} catch (Exception e1) {
					logger.log(Level.INFO,
							"Impossible to access at window 'ListCommonArea' " + e1.getClass().getCanonicalName());
				}
			}
		});

		// Creation of a list resident button
		// And display on the contentPane
		JButton listResidentbouton = new JButton("Liste des Residents");
		listResidentbouton.setBounds(147, 30, 214, 23);
		contentPane.add(listResidentbouton);
		listResidentbouton.addActionListener(new ActionListener() {
			/**
			 * If we click in the listResident we are redirect to the window 'Resident'
			 * where we have the list of users
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ListResident frame = new ListResident();
					frame.setVisible(true);
					setVisible(false);
				} catch (Exception e1) {
					logger.log(Level.INFO,
							"Impossible to access at window 'ListResident' " + e1.getClass().getCanonicalName());
				}
			}
		});

		// Creation of label name of Resident
		// And display on the contentPane
		JLabel nameResident = new JLabel("Prenom du resident :");
		nameResident.setBounds(147, 60, 112, 14);
		contentPane.add(nameResident);

		// Creation of TextField for name of Resident
		// And display on the contentPane
		nameResidentField = new JTextField();
		nameResidentField.setBounds(147, 90, 96, 20);
		contentPane.add(nameResidentField);
		nameResidentField.setColumns(10);

		// Creation of label lastname of Resident
		// And display on the contentPane
		JLabel lastnameResident = new JLabel("nom du resident :");
		lastnameResident.setBounds(264, 60, 72, 14);
		contentPane.add(lastnameResident);

		// Creation of TextField for lastname of Resident
		// And display on the contentPane
		lastnameResidentField = new JTextField();
		lastnameResidentField.setBounds(264, 90, 96, 20);
		contentPane.add(lastnameResidentField);
		lastnameResidentField.setColumns(10);

		// Creation of a add button
		// And display on the contentPane
		JButton addButton = new JButton("Ajouter");
		addButton.setBounds(147, 120, 214, 23);
		contentPane.add(addButton);

		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				requestType = "CREATE";
				resident = new Resident();
				table = "CommonArea";

				String nameResidentfield = nameResidentField.getText().trim();
				String lastnameResidentfield = lastnameResidentField.getText();

				if (nameResidentfield.equals("") || lastnameResidentfield.equals("")) {
					JOptionPane.showMessageDialog(null, "Vous n'avez pas remplis au moins l'un des deux champs requis",
							"Erreur", JOptionPane.ERROR_MESSAGE);
					logger.log(Level.INFO, "Attempt of insertion without characters");
				} else {
					resident.setNameResident(nameResidentfield);
					resident.setLastnameResident(lastnameResidentfield);
					ObjectMapper insertMapper = new ObjectMapper();
					try {
						jsonString = insertMapper.writeValueAsString(resident);
						new ClientSocket(requestType, jsonString, table);
						jsonString = ClientSocket.getJson();
						if (!jsonString.equals("INSERTED")) {
							JOptionPane.showMessageDialog(null, "L'insertion a échoué", "Erreur",
									JOptionPane.ERROR_MESSAGE);
							logger.log(Level.INFO, "Impossible to insert resident");
						} else {
							logger.log(Level.INFO, "Insertion Succeded");
						}
					} catch (Exception e1) {
						logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
					}
				}
			}
		});
	}

}
