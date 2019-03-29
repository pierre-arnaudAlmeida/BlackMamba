package com.blackmamba.deathkiss.gui.dev;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.blackmamba.deathkiss.entity.Employee;
import com.blackmamba.deathkiss.gui.dev.ClientSocket;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class TabProfile extends JPanel {
	private String message;
	private String requestType;
	private String table;
	private String jsonString;
	private int idemployee;
	private JPanel bar;
	private JLabel labelIdEmployee;
	private JLabel idEmployee;
	private JLabel labelLastnameEmployee;
	private JLabel labelNameEmployee;
	private JLabel labelFunction;
	private JLabel labelPassword;
	private JTextField textInputLastnameEmployee;
	private JTextField textInputNameEmployee;
	private JTextField textInputFunctionEmployee;
	private JPasswordField textInputPasswordEmployee;
	private Font policeBar;
	private Font policeLabel;
	private Employee employee;
	private Employee employee2;
	private JButton disconnection;
	private JButton save;
	private JButton delete;
	private JButton restaure;
	private static final Logger logger = LogManager.getLogger(TabProfile.class);

	public TabProfile(Color color, String title, int idemployee) {
		this.idemployee = idemployee;

		/**
		 * Definition of the structures of this tab
		 */
		bar = new JPanel();
		bar.setBackground(Color.DARK_GRAY);
		bar.setPreferredSize(new Dimension((int) getToolkit().getScreenSize().getWidth(), 80));
		bar.setLayout(new BorderLayout());
		bar.setBorder(BorderFactory.createMatteBorder(20, 100, 20, 100, bar.getBackground()));

		///////////////////////// BAR/////////////////////////////////////////////////
		/**
		 * Definition of label Identifiant on header bar
		 */
		labelIdEmployee = new JLabel("Identifiant :   ");
		policeBar = new Font("Arial", Font.BOLD, 16);
		labelIdEmployee.setForeground(Color.WHITE);
		labelIdEmployee.setFont(policeBar);
		bar.add(labelIdEmployee, BorderLayout.WEST);

		/**
		 * Definition of the label idEmployee on header bar
		 */
		idEmployee = new JLabel();
		idEmployee.setText("" + this.idemployee + "");
		idEmployee.setFont(policeBar);
		idEmployee.setForeground(Color.WHITE);
		bar.add(idEmployee, BorderLayout.CENTER);

		/**
		 * Definition of the button and the different action after pressed the button
		 */
		disconnection = new JButton("Se Déconnecter");
		bar.add(disconnection, BorderLayout.EAST);
		disconnection.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.log(Level.INFO, "Application closed, after disconnection");
				System.exit(ABORT);

			}
		});

		/**
		 * Recuparation of information about employee
		 */
		requestType = "READ";
		employee = new Employee();
		table = "Employee";
		ObjectMapper readMapper = new ObjectMapper();
		employee.setIdEmployee(idemployee);
		try {
			jsonString = readMapper.writeValueAsString(employee);
			;
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			employee = readMapper.readValue(jsonString, Employee.class);
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
		}

		///////////////////////// LABEL///////////////////////////////////////////////
		/**
		 * Definition of label LastnameEmployee
		 */
		policeLabel = new Font("Arial", Font.BOLD, (int) getToolkit().getScreenSize().getWidth() / 80);
		labelLastnameEmployee = new JLabel("Nom : ");
		labelLastnameEmployee.setBounds((int) getToolkit().getScreenSize().getWidth() * 1 / 4, (int) getToolkit().getScreenSize().getHeight() * 2 / 10, 200, 30);
		// labelLastnameEmployee.setBounds(330, 200, 100, 30);
		labelLastnameEmployee.setFont(policeLabel);
		this.add(labelLastnameEmployee);

		/**
		 * Definition of label NameEmployee
		 */
		labelNameEmployee = new JLabel("Prenom : ");
		labelNameEmployee.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 4, (int) getToolkit().getScreenSize().getHeight() * 2 / 10, 200, 30);
		// labelNameEmployee.setBounds(770, 200, 100, 30);
		labelNameEmployee.setFont(policeLabel);
		this.add(labelNameEmployee);

		/**
		 * Definition of label Function
		 */
		labelFunction = new JLabel("Poste : ");
		labelFunction.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 4, (int) getToolkit().getScreenSize().getHeight() * 4 / 10, 100, 30);
		// labelFunction.setBounds(770, 350, 100, 30);
		labelFunction.setFont(policeLabel);
		this.add(labelFunction);

		/**
		 * Definition of label Password
		 */
		labelPassword = new JLabel("Mot de passe : ");
		labelPassword.setBounds((int) getToolkit().getScreenSize().getWidth() * 1 / 4, (int) getToolkit().getScreenSize().getHeight() * 4 / 10, 200, 30);
		// labelPassword.setBounds(330, 350, 200, 30);
		labelPassword.setFont(policeLabel);
		this.add(labelPassword);

		//////////////////// TEXT AREA////////////////////////////////////////////////
		/**
		 * Definition of textArea LastnameEmployee
		 */
		textInputLastnameEmployee = new JTextField();
		textInputLastnameEmployee.setBounds((int) getToolkit().getScreenSize().getWidth() * 1 / 4, (int) getToolkit().getScreenSize().getHeight() * 5 / 20, 300, 40);
		// textInputLastnameEmployee.setBounds(330, 250, 300, 40);
		textInputLastnameEmployee.setFont(policeLabel);
		textInputLastnameEmployee.setText(employee.getLastnameEmployee());
		this.add(textInputLastnameEmployee);

		/**
		 * Definition of textArea NameEmployee
		 */
		textInputNameEmployee = new JTextField();
		textInputNameEmployee.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 4, (int) getToolkit().getScreenSize().getHeight() * 5 / 20, 300, 40);
		// textInputNameEmployee.setBounds(770, 250, 300, 40);
		textInputNameEmployee.setFont(policeLabel);
		textInputNameEmployee.setText(employee.getNameEmployee());
		this.add(textInputNameEmployee);

		/**
		 * Definition of textArea Password
		 */
		textInputPasswordEmployee = new JPasswordField();
		textInputPasswordEmployee.setBounds((int) getToolkit().getScreenSize().getWidth() * 1 / 4, (int) getToolkit().getScreenSize().getHeight() * 9 / 20, 300, 40);
		// textInputPasswordEmployee.setBounds(330, 400, 300, 40);
		textInputPasswordEmployee.setFont(policeLabel);
		this.add(textInputPasswordEmployee);

		/**
		 * Definition of textArea Function
		 */
		textInputFunctionEmployee = new JTextField();
		textInputFunctionEmployee.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 4, (int) getToolkit().getScreenSize().getHeight() * 9 / 20, 300, 40);
		// textInputFunctionEmployee.setBounds(770, 400, 300, 40);
		textInputFunctionEmployee.setFont(policeLabel);
		textInputFunctionEmployee.setText(employee.getPoste());
		this.add(textInputFunctionEmployee);

		//////////////////// BUTTON////////////////////////////////////////////////
		/**
		 * Definition of Button Save
		 */
		save = new JButton("Sauvegarder");
		save.setBounds(((int) getToolkit().getScreenSize().getWidth() * 2 / 4) + 250, (int) getToolkit().getScreenSize().getHeight() * 15 / 20, 200, 40);
		// save.setBounds(147, 300, 100, 23);
		this.add(save);
		save.addActionListener(new ActionListener() {
			/**
			 * Get datas inserted on text area
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				requestType = "UPDATE";
				employee = new Employee();
				table = "Employee";

				String newLastnameEmployee = textInputLastnameEmployee.getText().trim();
				String newNameEmployee = textInputNameEmployee.getText().trim();
				String newFunctionEmployee = textInputFunctionEmployee.getText().trim();
				char[] password = textInputPasswordEmployee.getPassword();
				String newPasswordEmployee = new String(password);
				
				/**
				 * if text area are empty they open an popup
				 */
				if (newLastnameEmployee.equals("") || (newNameEmployee.equals("") || newFunctionEmployee.equals(""))) {
					JOptionPane.showMessageDialog(null, "Champs vide", "Erreur", JOptionPane.ERROR_MESSAGE);
				} else {
					/**
					 * if they want to update the password they will input the current password to be verify by system
					 */
					if (!(newPasswordEmployee.equals(""))) {
						String verificationPassword = JOptionPane.showInputDialog(null, "Inserez ancien votre mot de passe", "");
						String request = "CONNECTION";
						employee2 = new Employee();

						employee2.setIdEmployee(employee.getIdEmployee());
						employee2.setPassword(verificationPassword);
						ObjectMapper connectionMapper = new ObjectMapper();
						try {
							jsonString = connectionMapper.writeValueAsString(employee2);
							new ClientSocket(request, jsonString, table);
							jsonString = ClientSocket.getJson();
							employee2 = connectionMapper.readValue(jsonString, Employee.class);
						} catch (IOException e1) {
							logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
						}
						if (!(employee2.getLastnameEmployee().equals(""))) {
							employee.setIdEmployee(idemployee);
							employee.setLastnameEmployee(newLastnameEmployee);
							employee.setNameEmployee(newNameEmployee);
							employee.setPoste(newFunctionEmployee);
							employee.setPassword(newPasswordEmployee);
						} else {
							JOptionPane.showMessageDialog(null, "Prend nous pour des amateurs encore une fois et on te bloque", "Erreur, Mauvais mot de passe", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						employee.setIdEmployee(idemployee);
						employee.setLastnameEmployee(newLastnameEmployee);
						employee.setNameEmployee(newNameEmployee);
						employee.setPoste(newFunctionEmployee);
					}
					ObjectMapper connectionMapper = new ObjectMapper();
					try {
						jsonString = connectionMapper.writeValueAsString(employee);
						new ClientSocket(requestType, jsonString, table);
						jsonString = ClientSocket.getJson();
						if (!jsonString.equals("UPDATED")) {
							JOptionPane.showMessageDialog(null, "La mise a jour a échoué", "Erreur", JOptionPane.ERROR_MESSAGE);
							logger.log(Level.INFO, "Impossible to update employee");
						}
					} catch (Exception e1) {
						logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
					}
				}
			}
		});

		/**
		 * Definition of Button Restaure
		 */
		restaure = new JButton("Annuler");
		restaure.setBounds(((int) getToolkit().getScreenSize().getWidth() * 2 / 4), (int) getToolkit().getScreenSize().getHeight() * 15 / 20, 200, 40);
		// restaure.setBounds(147, 400, 100, 23);
		this.add(restaure);
		restaure.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textInputLastnameEmployee.setText(employee.getLastnameEmployee());
				textInputNameEmployee.setText(employee.getNameEmployee());
				textInputFunctionEmployee.setText(employee.getPoste());
				textInputPasswordEmployee.setText("");
			}
		});

		/**
		 * Diferent parameters of the window
		 */
		this.setLayout(new BorderLayout());
		this.add(bar, BorderLayout.NORTH);
		this.message = title;
		this.setBackground(color);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
