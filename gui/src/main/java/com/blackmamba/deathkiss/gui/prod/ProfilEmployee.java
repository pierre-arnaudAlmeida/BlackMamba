package com.blackmamba.deathkiss.gui.prod;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.blackmamba.deathkiss.entity.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProfilEmployee extends JFrame {

	// Definition of differents fields
	private JPanel contentPane;
	private JTextField nameField;
	private JTextField lastnameField;
	private JTextField posteField;
	private JPasswordField passwordField;
	private static Logger logger = Logger.getLogger("logger");
	private Employee employee, emp;
	private String requestType;
	private String table;
	private String jsonString;

	public ProfilEmployee(int id) {

		setTitle("Profil Employee");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Creation of a back button
		// And display on the contentPane
		JButton backButton = new JButton("Liste Employes");
		backButton.setBounds(0, 0, 200, 23);
		contentPane.add(backButton);

		backButton.addActionListener(new ActionListener() {
			/**
			 * If they click in backButton c'est will redirect to ListEmployee
			 */
			public void actionPerformed(ActionEvent e) {
				try {
					ListEmployee frame = new ListEmployee();
					frame.setVisible(true);
					setVisible(false);
					dispose();
				} catch (Exception e1) {
					logger.log(Level.INFO,
							"Impossible redirect to window 'List Employee' " + e1.getClass().getCanonicalName());
				}
			}
		});

		requestType = "READ";
		employee = new Employee();
		table = "Employee";
		ObjectMapper readMapper = new ObjectMapper();
		employee.setIdEmployee(id);
		try {
			jsonString = readMapper.writeValueAsString(employee);
			;
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			employee = readMapper.readValue(jsonString, Employee.class);
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
		}

		// Creation of label id employee
		// And display on the contentPane
		JLabel idemployee = new JLabel("Id employé : " + employee.getIdEmployee());
		idemployee.setBounds(10, 30, 200, 14);
		contentPane.add(idemployee);

		// Creation of label lastname
		// And display on the contentPane
		JLabel lastname = new JLabel("Nom : " + employee.getLastnameEmployee());
		lastname.setBounds(150, 30, 200, 14);
		contentPane.add(lastname);

		// Creation of label name
		// And display on the contentPane
		JLabel name = new JLabel("Pr\u00E9nom : " + employee.getNameEmployee());
		name.setBounds(270, 30, 200, 14);
		contentPane.add(name);

		// Creation of label password
		// And display on the contentPane
		JLabel password = new JLabel("Mot de passe : *****");
		password.setBounds(150, 90, 200, 14);
		contentPane.add(password);

		// Creation of label poste
		// And display on the contentPane
		JLabel poste = new JLabel("Poste : " + employee.getPoste());
		poste.setBounds(270, 90, 200, 14);
		contentPane.add(poste);

		// Creation of a delete button
		// And display on the contentPane
		JButton deleteButton = new JButton("Supprimer");
		deleteButton.setBounds(380, 30, 100, 23);
		contentPane.add(deleteButton);

		deleteButton.addActionListener(new ActionListener() {
			/**
			 * When the user pressed the button, they delete the user corresponding And open
			 * the window 'ProfilClient' with the list of users actualized
			 */
			public void actionPerformed(ActionEvent e) {
				requestType = "DELETE";
				employee = new Employee();
				table = "Employee";
				employee.setIdEmployee(id);
				ObjectMapper connectionMapper = new ObjectMapper();
				try {
					jsonString = connectionMapper.writeValueAsString(employee);
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
					ListEmployee frame = new ListEmployee();
					frame.setVisible(true);
					setVisible(false);
					dispose();
				} catch (Exception e1) {
					logger.log(Level.INFO,
							"Impossible redirect to window 'ProfilClient' " + e1.getClass().getCanonicalName());
				}
			}
		});

		// Creation of label modification
		// And display on the contentPane
		JLabel modification = new JLabel("Modifiez");
		modification.setBounds(10, 60, 200, 14);
		contentPane.add(modification);

		// Creation of TextField for lastname
		// And display on the contentPane
		lastnameField = new JTextField();
		lastnameField.setBounds(150, 60, 101, 20);
		contentPane.add(lastnameField);
		lastnameField.setColumns(10);

		// Creation of TextField for name
		// And display on the contentPane
		nameField = new JTextField();
		nameField.setBounds(270, 60, 101, 20);
		contentPane.add(nameField);
		nameField.setColumns(10);

		// Creation of TextField for password
		// And display on the contentPane
		passwordField = new JPasswordField();
		passwordField.setBounds(150, 120, 101, 20);
		contentPane.add(passwordField);
		passwordField.setColumns(10);

		// Creation of TextField for poste
		// And display on the contentPane
		posteField = new JTextField();
		posteField.setBounds(270, 120, 101, 20);
		contentPane.add(posteField);
		posteField.setColumns(10);

		// Creation of a modification button
		// And display on the contentPane
		JButton modificationButton = new JButton("Sauvegarder");
		modificationButton.setBounds(380, 120, 100, 23);
		contentPane.add(modificationButton);

		modificationButton.addActionListener(new ActionListener() {
			/**
			 * When they pressed on modification button they check if one of the two
			 * TextField contains characters and update the BDD If no one have characters
			 * they display a popup
			 */
			public void actionPerformed(ActionEvent e) {
				requestType = "UPDATE";
				employee = new Employee();
				table = "Employee";

				String lastnamefield = lastnameField.getText();
				String namefield = nameField.getText();
				String postefield = posteField.getText();
				char[] password = passwordField.getPassword();
				String passwordfield = new String(password);

				if (lastnamefield.equals("")
						&& (namefield.equals("") && postefield.equals("") && passwordfield.equals(""))) {
					JOptionPane.showMessageDialog(null, "Champs vide", "Erreur", JOptionPane.ERROR_MESSAGE);
				} else {
					if (passwordfield.equals("") && postefield.equals("") && lastnamefield.equals("")
							&& !(namefield.equals(""))) {
						employee.setIdEmployee(id);
						employee.setNameEmployee(namefield);
						employee.setLastnameEmployee("");
						employee.setPassword("");
						employee.setPoste("");
					} else if (passwordfield.equals("") && postefield.equals("") && !(lastnamefield.equals(""))
							&& namefield.equals("")) {
						employee.setIdEmployee(id);
						employee.setNameEmployee("");
						employee.setLastnameEmployee(lastnamefield);
						employee.setPassword("");
						employee.setPoste("");
					} else if (passwordfield.equals("") && postefield.equals("") && !(lastnamefield.equals(""))
							&& !(namefield.equals(""))) {
						employee.setIdEmployee(id);
						employee.setNameEmployee(namefield);
						employee.setLastnameEmployee(lastnamefield);
						employee.setPassword("");
						employee.setPoste("");
					} else if (passwordfield.equals("") && !(postefield.equals("")) && lastnamefield.equals("")
							&& namefield.equals("")) {
						employee.setIdEmployee(id);
						employee.setNameEmployee("");
						employee.setLastnameEmployee("");
						employee.setPassword("");
						employee.setPoste(postefield);
					} else if (passwordfield.equals("") && !(postefield.equals("")) && lastnamefield.equals("")
							&& !(namefield.equals(""))) {
						employee.setIdEmployee(id);
						employee.setNameEmployee(namefield);
						employee.setLastnameEmployee("");
						employee.setPassword("");
						employee.setPoste(postefield);
					} else if (passwordfield.equals("") && !(postefield.equals("")) && !(lastnamefield.equals(""))
							&& namefield.equals("")) {
						employee.setIdEmployee(id);
						employee.setNameEmployee("");
						employee.setLastnameEmployee(lastnamefield);
						employee.setPassword("");
						employee.setPoste(postefield);
					} else if (passwordfield.equals("") && !(postefield.equals("")) && !(lastnamefield.equals(""))
							&& !(namefield.equals(""))) {
						employee.setIdEmployee(id);
						employee.setNameEmployee(namefield);
						employee.setLastnameEmployee(lastnamefield);
						employee.setPassword("");
						employee.setPoste(postefield);
					} else if (!(passwordfield.equals(""))) {
						String verificationPassword = JOptionPane.showInputDialog(null, "Inserez votre mot de passe", "");
						String request = "CONNECTION";
						emp = new Employee();
						
						emp.setIdEmployee(id);
						emp.setPassword(verificationPassword);
						ObjectMapper connectionMapper = new ObjectMapper();
						try { 
							jsonString = connectionMapper.writeValueAsString(emp);
							new ClientSocket(request, jsonString, table);
							jsonString = ClientSocket.getJson();
							emp = connectionMapper.readValue(jsonString, Employee.class);
						} catch (IOException e1) {
							logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
						}
						if (!emp.getLastnameEmployee().equals("")) {
							if (postefield.equals("") && lastnamefield.equals("") && namefield.equals("")) {
								employee.setIdEmployee(id);
								employee.setNameEmployee("");
								employee.setLastnameEmployee("");
								employee.setPassword(passwordfield);
								employee.setPoste("");
							} else if (postefield.equals("") && lastnamefield.equals("") && !(namefield.equals(""))) {
								employee.setIdEmployee(id);
								employee.setNameEmployee(namefield);
								employee.setLastnameEmployee("");
								employee.setPassword(passwordfield);
								employee.setPoste("");
							} else if (postefield.equals("") && !(lastnamefield.equals("")) && namefield.equals("")) {
								employee.setIdEmployee(id);
								employee.setNameEmployee("");
								employee.setLastnameEmployee(lastnamefield);
								employee.setPassword(passwordfield);
								employee.setPoste("");
							} else if (postefield.equals("") && !(lastnamefield.equals(""))
									&& !(namefield.equals(""))) {
								employee.setIdEmployee(id);
								employee.setNameEmployee(namefield);
								employee.setLastnameEmployee(lastnamefield);
								employee.setPassword(passwordfield);
								employee.setPoste("");
							} else if (!(postefield.equals("")) && lastnamefield.equals("") && namefield.equals("")) {
								employee.setIdEmployee(id);
								employee.setNameEmployee("");
								employee.setLastnameEmployee("");
								employee.setPassword(passwordfield);
								employee.setPoste(postefield);
							} else if (!(postefield.equals("")) && lastnamefield.equals("")
									&& !(namefield.equals(""))) {
								employee.setIdEmployee(id);
								employee.setNameEmployee(namefield);
								employee.setLastnameEmployee("");
								employee.setPassword(passwordfield);
								employee.setPoste(postefield);
							} else if (!(postefield.equals("")) && !(lastnamefield.equals(""))
									&& namefield.equals("")) {
								employee.setIdEmployee(id);
								employee.setNameEmployee("");
								employee.setLastnameEmployee(lastnamefield);
								employee.setPassword(passwordfield);
								employee.setPoste(postefield);
							} else if (!(postefield.equals("")) && !(lastnamefield.equals(""))
									&& !(namefield.equals(""))) {
								employee.setIdEmployee(id);
								employee.setNameEmployee(namefield);
								employee.setLastnameEmployee(lastnamefield);
								employee.setPassword(passwordfield);
								employee.setPoste(postefield);
							}
						} else {
							JOptionPane.showMessageDialog(null,
									"Prend nous pour des amateurs encore une fois et on te bloque",
									"Erreur, Mauvais mot de passe", JOptionPane.ERROR_MESSAGE);
						}
					}

					ObjectMapper connectionMapper = new ObjectMapper();
					try {
						jsonString = connectionMapper.writeValueAsString(employee);
						new ClientSocket(requestType, jsonString, table);
						jsonString = ClientSocket.getJson();
						if (!jsonString.equals("UPDATED")) {
							JOptionPane.showMessageDialog(null, "La mise a jour a échoué", "Erreur",
									JOptionPane.ERROR_MESSAGE);
							logger.log(Level.INFO, "Impossible to update employee");
						}
					} catch (Exception e1) {
						logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
					}
				}
				try {
					ProfilEmployee frame = new ProfilEmployee(id);
					frame.setVisible(true);
					setVisible(false);
					dispose();
				} catch (Exception e1) {
					logger.log(Level.INFO,
							"Impossible redirect to window 'ProfilClient' " + e1.getClass().getCanonicalName());
				}
			}
		});
	}
}