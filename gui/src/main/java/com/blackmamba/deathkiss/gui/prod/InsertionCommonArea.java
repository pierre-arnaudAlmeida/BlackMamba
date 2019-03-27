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

import com.blackmamba.deathkiss.entity.CommonArea;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InsertionCommonArea extends JFrame {

	// Definition of differents fields
	private JPanel contentPane;
	private JTextField nameCommonAreaField;
	private JTextField stageCommonAreaField;
	private CommonArea commonArea;
	private String requestType;
	private String table;
	private String jsonString;
	private static Logger logger = Logger.getLogger("logger");

	public InsertionCommonArea() {

		setTitle("Insertion Partie Commune");
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
					ListCommonArea frame = new ListCommonArea();
					frame.setVisible(true);
					setVisible(false);
				} catch (Exception e1) {
					logger.log(Level.INFO,
							"Impossible to access at window 'ListCommonArea' " + e1.getClass().getCanonicalName());
				}
			}
		});

		// Creation of label name CommonArea
		// And display on the contentPane
		JLabel nameCommonArea = new JLabel("Nom du Lieux :");
		nameCommonArea.setBounds(147, 60, 112, 14);
		contentPane.add(nameCommonArea);

		// Creation of TextField for name of CommonArea
		// And display on the contentPane
		nameCommonAreaField = new JTextField();
		nameCommonAreaField.setBounds(147, 90, 96, 20);
		contentPane.add(nameCommonAreaField);
		nameCommonAreaField.setColumns(10);

		// Creation of label stage of CommonArea
		// And display on the contentPane
		JLabel stageCommonArea = new JLabel("Etage :");
		stageCommonArea.setBounds(264, 60, 72, 14);
		contentPane.add(stageCommonArea);

		// Creation of TextField for stage CommonArea
		// And display on the contentPane
		stageCommonAreaField = new JTextField();
		stageCommonAreaField.setBounds(264, 90, 96, 20);
		contentPane.add(stageCommonAreaField);
		stageCommonAreaField.setColumns(10);

		// Creation of a inscription button
		// And display on the contentPane
		JButton addButton = new JButton("Ajouter");
		addButton.setBounds(147, 120, 214, 23);
		contentPane.add(addButton);

		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				requestType = "CREATE";
				commonArea = new CommonArea();
				table = "CommonArea";

				String nameCommonAreafield = nameCommonAreaField.getText().trim();
				String stageCommonAreafield = stageCommonAreaField.getText();

				if (nameCommonAreafield.equals("") || stageCommonAreafield.equals("")) {
					JOptionPane.showMessageDialog(null, "Vous n'avez pas remplis au moins l'un des deux champs requis",
							"Erreur", JOptionPane.ERROR_MESSAGE);
					logger.log(Level.INFO, "Attempt of insertion without characters");
				} else if (!(stageCommonAreafield.matches("[0-9]+[0-9]*"))) {
					JOptionPane.showMessageDialog(null, "L'etage ne contient pas de lettre uniquement un chiffre",
							"Erreur", JOptionPane.ERROR_MESSAGE);
				} else {
					commonArea.setNameCommonArea(nameCommonAreafield);
					commonArea.setEtageCommonArea(Integer.parseInt(stageCommonAreafield));
					ObjectMapper insertMapper = new ObjectMapper();
					try {
						jsonString = insertMapper.writeValueAsString(commonArea);
						new ClientSocket(requestType, jsonString, table);
						jsonString = ClientSocket.getJson();
						if (!jsonString.equals("INSERTED")) {
							JOptionPane.showMessageDialog(null, "L'insertion a échoué", "Erreur",
									JOptionPane.ERROR_MESSAGE);
							logger.log(Level.INFO, "Impossible to insert employee");
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
