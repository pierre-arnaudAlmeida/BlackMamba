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

	private static final long serialVersionUID = 1L;
	private JPanel container;
	private JPanel pan;
	private JPanel pan2;
	private JPanel pan3;
	private JPasswordField textInputPassword;
	private JTextField textInputIdEmployee;
	private JLabel labelIdEmployee;
	private JLabel labelPassword;
	private JButton buttonConnection;
	private JButton buttonLeave;
	private Font police;
	private String idEmployee;
	private String password;
	private String requestType;
	private String table;
	private String jsonString;
	private Employee employee;
	private char[] passwordfield;
	private static final Logger logger = LogManager.getLogger(Connexion.class);

	public Connexion() {
		/**
		 * initialisation of different element
		 */
		container = new JPanel();
		labelIdEmployee = new JLabel("Identifiant");
		textInputIdEmployee = new JTextField();
		labelPassword = new JLabel("Mot de Passe");
		textInputPassword = new JPasswordField();
		buttonConnection = new JButton("Se Connecter");
		buttonLeave = new JButton("Quitter");

		/**
		 * Definition of different pan present in the popup
		 */
		container.setBackground(Color.WHITE);
		pan = new JPanel();
		pan2 = new JPanel();
		pan3 = new JPanel();
		police = new Font("Arial", Font.BOLD, 14);

		/**
		 * Line who get the identifiant employee
		 */
		labelIdEmployee.setPreferredSize(new Dimension(100, 30));
		pan.add(labelIdEmployee);
		textInputIdEmployee.setFont(police);
		textInputIdEmployee.setPreferredSize(new Dimension(150, 30));
		textInputIdEmployee.setForeground(Color.BLACK);
		pan.add(textInputIdEmployee);
		pan.setBackground(Color.WHITE);

		/**
		 * Line who get the password employee
		 */
		labelPassword.setPreferredSize(new Dimension(100, 30));
		pan2.add(labelPassword);
		textInputPassword.setFont(police);
		textInputPassword.setPreferredSize(new Dimension(150, 30));
		textInputPassword.setForeground(Color.BLACK);
		pan2.add(textInputPassword);
		pan2.setBackground(Color.WHITE);

		/**
		 * Display the content of TextField password employee When we check the CheckBox
		 * "Montrer le mot de passe"
		 */
		final JCheckBox showButton = new JCheckBox("Montrer le mot de passe");
		showButton.setBounds(147, 150, 171, 23);
		showButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (showButton.isSelected()) {
					textInputPassword.setEchoChar((char) 0);
				} else {
					textInputPassword.setEchoChar('*');
				}
			}
		});

		/**
		 * Actions when we pressed the button Connection Send parameter to create an
		 * socket to connect the employee
		 */
		buttonConnection.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				requestType = "CONNECTION";
				employee = new Employee();
				table = "Employee";

				idEmployee = textInputIdEmployee.getText();
				passwordfield = textInputPassword.getPassword();
				password = new String(passwordfield);

				if (!(idEmployee.matches("[0-9]+[0-9]*")) || password.equals("")) {

					JOptionPane.showMessageDialog(null, "Vous n'avez pas renseigné tout les champs", "Attention",
							JOptionPane.WARNING_MESSAGE);
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
						logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
					}
					if (!employee.getLastnameEmployee().equals("")) {
						Window frame = new Window(employee.getIdEmployee());
						setVisible(false);
						dispose();
						frame.setVisible(true);
						logger.log(Level.INFO, "Connection succesfuly accepted, redirection to Window");
					} else {
						logger.log(Level.INFO, "Attempt of connection with wrong password employee or id employee");
						JOptionPane.showMessageDialog(null, "L'identifiant ou le mot de passe est incorrect", "Erreur",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Une Erreur est survenue, relancez l'application", "Attention",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		/**
		 * Close the window if we press the button Quitter
		 */
		buttonLeave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.log(Level.INFO, "Application closed, after disconnection");
				System.exit(DISPOSE_ON_CLOSE);
			}
		});

		/**
		 * Add components in the window container
		 */
		pan3.add(buttonConnection);
		pan3.add(buttonLeave);
		container.add(pan);
		container.add(pan2);
		container.add(showButton);
		container.add(pan3);

		/**
		 * diferent parameters of the window
		 */
		this.setContentPane(container);
		this.setTitle("Connexion");
		this.setSize(350, 200);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
