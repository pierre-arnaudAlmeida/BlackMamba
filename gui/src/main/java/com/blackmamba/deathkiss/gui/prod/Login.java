package com.blackmamba.deathkiss.gui.prod;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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

public class Login extends JFrame {
	// Definition of differents fields
	private JPanel contentPane;
	private JTextField id_employeeField;
	private JPasswordField passwordField;
	private static Logger logger = Logger.getLogger("logger");
	private String requestType;
	private Employee employee;
	private String jsonString;
	private String table;

	public Login() {

		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Creation of label id employee
		// And display on the contentPane
		JLabel id_employee = new JLabel("Id employé");
		id_employee.setBounds(147, 21, 81, 14);
		contentPane.add(id_employee);

		// Creation of TextField for id employee
		// And display on the contentPane
		id_employeeField = new JTextField();
		id_employeeField.setBounds(147, 44, 96, 20);
		contentPane.add(id_employeeField);
		id_employeeField.setColumns(10);

		// Creation of label password employee
		// And display on the contentPane
		JLabel password_employee = new JLabel("Mot de passe");
		password_employee.setBounds(147, 100, 81, 14);
		contentPane.add(password_employee);

		// Creation of TextField for password employee
		// And display on the contentPane
		passwordField = new JPasswordField();
		passwordField.setBounds(147, 123, 121, 20);
		contentPane.add(passwordField);

		// Display the button showButton on the contentPane
		final JCheckBox showButton = new JCheckBox("Montrer le mot de passe");
		showButton.setBounds(147, 150, 171, 23);
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

		// Creation of a button validation
		// And display on the contentPane
		JButton validationbutton = new JButton("Valider");
		validationbutton.setBounds(300, 122, 89, 23);
		contentPane.add(validationbutton);

		validationbutton.addActionListener(new ActionListener() {
			/**
			 * Check if the two fields contains caracters, and if the id employee is an
			 * integer when we press the button validationButton Display a popup if the
			 * condition are not satisfied And if the user have correctly filled the fields,
			 * we check on BDD (getting a connection) if they exist this user on BDD and we
			 * redirect to a new window if
			 */
			public void actionPerformed(ActionEvent e) {
				requestType = "CONNECTION";
				employee = new Employee();
				table = "Employee";

				String idfield = id_employeeField.getText();
				char[] password = passwordField.getPassword();
				String passwordfield = new String(password);

				if (passwordfield.equals("") || !(idfield.matches("[0-9]+[0-9]*"))) {
					JOptionPane.showMessageDialog(null, "L'identifiant ou le mot de passe est incorrect", "Erreur",
							JOptionPane.ERROR_MESSAGE);
					logger.log(Level.INFO, "Attempt of connection without or with wrong characters");
				} else {
					employee.setIdEmployee(Integer.parseInt(idfield));
					employee.setPassword(passwordfield);
					ObjectMapper connectionMapper = new ObjectMapper();
					try {
						jsonString = connectionMapper.writeValueAsString(employee);
						new ClientSocket(requestType, jsonString, table);
						jsonString = ClientSocket.getJson();
						employee = connectionMapper.readValue(jsonString, Employee.class);
					} catch (IOException e1) {
						logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
					}
					if (!employee.getLastnameEmployee().equals("")) {
						InsertionEmployee frame = new InsertionEmployee();
						frame.setVisible(true);
						setVisible(false);
						logger.log(Level.INFO, "Good id employee and good password employee, Connection accepted");
						dispose();
					} else {
						logger.log(Level.INFO, "Attempt of connection with wrong password employee or id employee");
						JOptionPane.showMessageDialog(null, "L'identifiant ou le mot de passe est incorrect", "Erreur",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}
}