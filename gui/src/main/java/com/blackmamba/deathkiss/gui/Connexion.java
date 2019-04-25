package com.blackmamba.deathkiss.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.blackmamba.deathkiss.entity.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class Connexion extends JFrame {

	/**
	 * Different parameters used
	 */
	private static final long serialVersionUID = 1L;
	private JPanel frameContainer;
	private JPanel idEmployeePan;
	private JPanel passwordEmployeePan;
	private JPanel buttonPan;
	private JPasswordField textInputPassword;
	private JTextField textInputIdEmployee;
	private JLabel labelIdEmployee;
	private JLabel labelPassword;
	private JButton connectionButton;
	private JButton leaveButton;
	private Font police;
	private String idEmployee;
	private String password;
	private String requestType;
	private String table;
	private String jsonString;
	private Employee employee;
	private char[] passwordfield;
	private static final Logger logger = LogManager.getLogger(Connexion.class);

	/**
	 * Constructor
	 */
	public Connexion() {

		/**
		 * initialization of container of frame
		 */
		frameContainer = new JPanel();
		frameContainer.setBackground(Color.WHITE);

		idEmployeePan = new JPanel();
		idEmployeePan.setBackground(Color.WHITE);
		passwordEmployeePan = new JPanel();
		passwordEmployeePan.setBackground(Color.WHITE);
		buttonPan = new JPanel();

		/**
		 * Definition of font
		 */
		police = new Font("Arial", Font.BOLD, 14);

		///////////////////////// LABEL/////////////////////////////////////////////////
		/**
		 * Definition of labelIdEmployee
		 */
		labelIdEmployee = new JLabel("Identifiant");
		labelIdEmployee.setPreferredSize(new Dimension(100, 30));
		idEmployeePan.add(labelIdEmployee);

		/**
		 * Definition of labelPassord
		 */
		labelPassword = new JLabel("Mot de Passe");
		labelPassword.setPreferredSize(new Dimension(100, 30));
		passwordEmployeePan.add(labelPassword);

		///////////////////////// TEXTFIELD////////////////////////////////////////////
		/**
		 * Definition of IdEmployee TextField
		 */
		textInputIdEmployee = new JTextField();
		textInputIdEmployee.setFont(police);
		textInputIdEmployee.setPreferredSize(new Dimension(150, 30));
		textInputIdEmployee.setForeground(Color.BLACK);
		idEmployeePan.add(textInputIdEmployee);

		/**
		 * Definition of PasswordField
		 */
		textInputPassword = new JPasswordField();
		textInputPassword.setFont(police);
		textInputPassword.setPreferredSize(new Dimension(150, 30));
		textInputPassword.setForeground(Color.BLACK);
		passwordEmployeePan.add(textInputPassword);

		///////////////////////// BUTTON/////////////////////////////////////////////////
		/**
		 * Definition of connectionButton
		 * 
		 * Actions when we pressed the button Connection Send parameter to create an
		 * socket to connect the employee
		 */
		connectionButton = new JButton("Se Connecter");
		buttonPan.add(connectionButton);
		connectionButton.addActionListener(new ActionListener() {
			/**
			 * We check if the id are just numerics and if his not null and the password
			 * need to be not null to send the request, else we display a pop-up to inform
			 * the user
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				requestType = "CONNECTION";
				employee = new Employee();
				table = "Employee";

				idEmployee = textInputIdEmployee.getText();
				passwordfield = textInputPassword.getPassword();
				password = new String(passwordfield);

				if (!(idEmployee.matches("[0-9]+[0-9]*")) || password.equals("")) {

					JOptionPane.showMessageDialog(null, "Vous n'avez pas renseigné tout les champs", "Attention", JOptionPane.WARNING_MESSAGE);
				} else if ((idEmployee.matches("[0-9]+[0-9]*")) && !(password.equals(""))) {
					employee.setIdEmployee(Integer.parseInt(idEmployee));
					employee.setPassword(password);
					ObjectMapper connectionMapper = new ObjectMapper();
					try {
						jsonString = connectionMapper.writeValueAsString(employee);
						new ClientSocket(requestType, jsonString, table);
						jsonString = ClientSocket.getJson();
						employee = connectionMapper.readValue(jsonString, Employee.class);
					} catch (IOException e1) {
						logger.log(Level.INFO, "Impossible to parse in JSON connection datas" + e1.getClass().getCanonicalName());
					}
					if (!employee.getPoste().equals("")) {
						Frame frame = new Frame(employee.getIdEmployee());
						setVisible(false);
						dispose();
						frame.setVisible(true);
						logger.log(Level.INFO, "Connection succesfuly accepted, redirection to Window");
					} else {
						logger.log(Level.INFO, "Attempt of connection with wrong password employee or id employee");
						JOptionPane.showMessageDialog(null, "L'identifiant ou le mot de passe est incorrect", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					logger.log(Level.INFO, "Crash of application, Application closed");
					JOptionPane.showMessageDialog(null, "Une Erreur est survenue, relancez l'application", "Attention", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		/**
		 * Definition of leaveButton
		 * 
		 * Close the window if we press the button Quitter
		 */
		leaveButton = new JButton("Quitter");
		buttonPan.add(leaveButton);
		leaveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.log(Level.INFO, "Application closed, after disconnection");
				System.exit(DISPOSE_ON_CLOSE);
			}
		});

		/**
		 * Definition of showButton
		 */
		final JCheckBox showButton = new JCheckBox("Montrer le mot de passe");
		showButton.setBounds(147, 150, 171, 23);
		showButton.addActionListener(new ActionListener() {
			/**
			 * Display the content of TextField password employee When we check the CheckBox
			 * "Montrer le mot de passe"
			 */
			public void actionPerformed(ActionEvent e) {
				if (showButton.isSelected()) {
					textInputPassword.setEchoChar((char) 0);
				} else {
					textInputPassword.setEchoChar('*');
				}
			}
		});

		/**
		 * Add components in the window container
		 */
		frameContainer.add(idEmployeePan);
		frameContainer.add(passwordEmployeePan);
		frameContainer.add(showButton);
		frameContainer.add(buttonPan);

		///////////////////////// FRAME/////////////////////////////////////////////////
		/**
		 * different parameters of the window
		 */
		this.setContentPane(frameContainer);
		this.setTitle("Connexion");
		this.setSize(350, 200);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}