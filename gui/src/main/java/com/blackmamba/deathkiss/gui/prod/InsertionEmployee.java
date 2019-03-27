package com.blackmamba.deathkiss.gui.prod;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.blackmamba.deathkiss.entity.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InsertionEmployee extends JFrame {

	// Definition of differents fields
	private JPanel contentPane;
	private JTextField nameField;
	private JTextField lastnameField;
	private JTextField posteField;
	private JPasswordField passwordField;
	private Employee employee;
	private String requestType;
	private String table;
	private String jsonString;
	private static Logger logger = Logger.getLogger("logger");

	public InsertionEmployee() {

		setTitle("Insertion Employee");
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
		listCommonAreabouton.setBounds(147, 220, 214, 23);
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

		JButton insertCommonAreaButton = new JButton("Ajouter une partie commune");
		insertCommonAreaButton.setBounds(147, 250, 214, 23);
		contentPane.add(insertCommonAreaButton);
		insertCommonAreaButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					InsertionCommonArea frame = new InsertionCommonArea();
					frame.setVisible(true);
					setVisible(false);
				} catch (Exception e1) {
					logger.log(Level.INFO,
							"Impossible to access at window 'ListCommonArea' " + e1.getClass().getCanonicalName());
				}
			}
		});

		// Creation of label name
		// And display on the contentPane
		JLabel name = new JLabel("Pr\u00E9nom :");
		name.setBounds(147, 41, 72, 14);
		contentPane.add(name);

		// Creation of TextField for name
		// And display on the contentPane
		nameField = new JTextField();
		nameField.setBounds(147, 54, 96, 20);
		contentPane.add(nameField);
		nameField.setColumns(10);

		// Creation of label lastname
		// And display on the contentPane
		JLabel lastname = new JLabel("Nom :");
		lastname.setBounds(264, 41, 98, 14);
		contentPane.add(lastname);

		// Creation of TextField for lastname
		// And display on the contentPane
		lastnameField = new JTextField();
		lastnameField.setBounds(266, 54, 96, 20);
		contentPane.add(lastnameField);
		lastnameField.setColumns(10);

		// Creation of label password
		// And display on the contentPane
		JLabel password = new JLabel("Mot de passe :");
		password.setBounds(147, 100, 98, 14);
		contentPane.add(password);

		// Creation of TextField for password
		// And display on the contentPane
		passwordField = new JPasswordField();
		passwordField.setBounds(147, 123, 121, 20);
		contentPane.add(passwordField);

		// Creation of label poste
		// And display on the contentPane
		JLabel poste = new JLabel("Poste :");
		poste.setBounds(147, 150, 130, 14);
		contentPane.add(poste);

		// Creation of TextField for poste
		// And display on the contentPane
		posteField = new JTextField();
		posteField.setBounds(147, 170, 130, 20);
		contentPane.add(posteField);
		posteField.setColumns(10);

		// Display the button showButton on the contentPane
		final JCheckBox showButton = new JCheckBox("Montrer le mot de passe");
		showButton.setBounds(147, 190, 171, 23);
		contentPane.add(showButton);

		showButton.addActionListener(new ActionListener() {
			/**
			 * Display the content of TextField password employee When we check the CheckBox
			 * "Montrer le mot de passe"
			 */
			public void actionPerformed(ActionEvent e) {
				if (showButton.isSelected()) {
					passwordField.setEchoChar((char) 0);
				} else {
					passwordField.setEchoChar('*');
				}
			}
		});

		// Creation of a inscription button
		// And display on the contentPane
		JButton inscriptionbutton = new JButton("Inscrire");
		inscriptionbutton.setBounds(300, 122, 89, 23);
		contentPane.add(inscriptionbutton);

		inscriptionbutton.addActionListener(new ActionListener() {
			/**
			 * When the user click on inscription button, they will check if the fields are
			 * empty and display a popup. If all fields are completed, we insert in BDD and
			 * they redirect to the window 'ProfilClient'
			 */
			public void actionPerformed(ActionEvent e) {
				requestType = "CREATE";
				employee = new Employee();
				table = "Employee";

				String namefield = nameField.getText();
				String lastnamefield = lastnameField.getText();
				String postefield = posteField.getText();
				char[] password = passwordField.getPassword();
				String passwordfield = new String(password);

				if (namefield.equals("") || lastnamefield.equals("") || passwordfield.equals("")
						|| postefield.equals("")) {
					JOptionPane.showMessageDialog(null, "Vous n'avez pas remplis au moins l'un des 3 champs requis",
							"Erreur", JOptionPane.ERROR_MESSAGE);
					logger.log(Level.INFO, "Attempt of insertion without characters");
				} else {
					employee.setLastnameEmployee(lastnamefield);
					employee.setNameEmployee(namefield);
					employee.setPassword(passwordfield);
					employee.setPoste(postefield);
					ObjectMapper insertMapper = new ObjectMapper();
					try {
						jsonString = insertMapper.writeValueAsString(employee);
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

					try {
						ListEmployee frame = new ListEmployee();
						frame.setVisible(true);
						logger.log(Level.INFO, "Page List Employee opened");
						setVisible(false);
						dispose();
					} catch (Exception e1) {
						logger.log(Level.INFO,
								"Impossible to redirect to window 'ProfilClient' " + e1.getClass().getCanonicalName());
					}

				}
			}
		});
	}
}