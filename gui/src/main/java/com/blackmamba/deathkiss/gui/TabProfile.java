package com.blackmamba.deathkiss.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.blackmamba.deathkiss.entity.Employee;
import com.blackmamba.deathkiss.gui.ClientSocket;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class TabProfile extends JPanel {

	private static final long serialVersionUID = 1L;
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
	private JButton restaure;
	private JCheckBox showButton;
	private ObjectMapper readMapper;
	private static final Logger logger = LogManager.getLogger(TabProfile.class);

	public TabProfile(Color color, int idemployee, String title) {
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
		labelIdEmployee = new JLabel("Identifiant :   "+this.idemployee +"    ");
		policeBar = new Font("Arial", Font.BOLD, 16);
		labelIdEmployee.setForeground(Color.WHITE);
		labelIdEmployee.setFont(policeBar);
		bar.add(labelIdEmployee, BorderLayout.WEST);

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

		///////////////////////// GET DATAS//////////////////////////////////////////
		/**
		 * Recuparation of information about employee
		 */
		requestType = "READ";
		employee = new Employee();
		table = "Employee";
		readMapper = new ObjectMapper();
		employee.setIdEmployee(idemployee);
		try {
			jsonString = readMapper.writeValueAsString(employee);
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
		labelLastnameEmployee.setBounds((int) getToolkit().getScreenSize().getWidth() * 1 / 4,
				(int) getToolkit().getScreenSize().getHeight() * 2 / 10, 200, 30);
		labelLastnameEmployee.setFont(policeLabel);
		this.add(labelLastnameEmployee);

		/**
		 * Definition of label NameEmployee
		 */
		labelNameEmployee = new JLabel("Prenom : ");
		labelNameEmployee.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 4,
				(int) getToolkit().getScreenSize().getHeight() * 2 / 10, 200, 30);
		labelNameEmployee.setFont(policeLabel);
		this.add(labelNameEmployee);

		/**
		 * Definition of label Password
		 */
		labelPassword = new JLabel("Mot de passe : ");
		labelPassword.setBounds((int) getToolkit().getScreenSize().getWidth() * 1 / 4,
				(int) getToolkit().getScreenSize().getHeight() * 4 / 10, 200, 30);
		labelPassword.setFont(policeLabel);
		this.add(labelPassword);

		/**
		 * Definition of label Function
		 */
		labelFunction = new JLabel("Poste : ");
		labelFunction.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 4,
				(int) getToolkit().getScreenSize().getHeight() * 4 / 10, 100, 30);
		labelFunction.setFont(policeLabel);
		this.add(labelFunction);

		//////////////////// TEXT AREA////////////////////////////////////////////////
		/**
		 * Definition of textArea LastnameEmployee
		 */
		textInputLastnameEmployee = new JTextField();
		textInputLastnameEmployee.setBounds((int) getToolkit().getScreenSize().getWidth() * 1 / 4,
				(int) getToolkit().getScreenSize().getHeight() * 5 / 20, 300, 40);
		textInputLastnameEmployee.setFont(policeLabel);
		textInputLastnameEmployee.setText(employee.getLastnameEmployee());
		this.add(textInputLastnameEmployee);

		/**
		 * Definition of textArea NameEmployee
		 */
		textInputNameEmployee = new JTextField();
		textInputNameEmployee.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 4,
				(int) getToolkit().getScreenSize().getHeight() * 5 / 20, 300, 40);
		textInputNameEmployee.setFont(policeLabel);
		textInputNameEmployee.setText(employee.getNameEmployee());
		this.add(textInputNameEmployee);

		/**
		 * Definition of textArea Password
		 */
		textInputPasswordEmployee = new JPasswordField();
		textInputPasswordEmployee.setBounds((int) getToolkit().getScreenSize().getWidth() * 1 / 4,
				(int) getToolkit().getScreenSize().getHeight() * 9 / 20, 300, 40);
		textInputPasswordEmployee.setFont(policeLabel);
		this.add(textInputPasswordEmployee);

		/**
		 * Definition of textArea Function
		 */
		textInputFunctionEmployee = new JTextField();
		textInputFunctionEmployee.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 4,
				(int) getToolkit().getScreenSize().getHeight() * 9 / 20, 300, 40);
		textInputFunctionEmployee.setFont(policeLabel);
		textInputFunctionEmployee.setText(employee.getPoste());
		this.add(textInputFunctionEmployee);

		/**
		 * Button to displpay the password
		 */
		showButton = new JCheckBox("Montrer le mot de passe");
		showButton.setBounds((int) getToolkit().getScreenSize().getWidth() * 1 / 4,
				((int) getToolkit().getScreenSize().getHeight() * 9 / 20) + 50, 300, 40);
		showButton.setBackground(color);
		showButton.setFont(policeLabel);
		this.add(showButton);

		showButton.addActionListener(new ActionListener() {
			/**
			 * Display the content of TextField password employee When we check the CheckBox
			 * "Montrer le mot de passe"
			 */
			public void actionPerformed(ActionEvent e) {
				if (showButton.isSelected()) {
					textInputPasswordEmployee.setEchoChar((char) 0);
				} else {
					textInputPasswordEmployee.setEchoChar('*');
				}
			}
		});
		//////////////////// BUTTON////////////////////////////////////////////////
		/**
		 * Definition of Button Save
		 */
		save = new JButton("Sauvegarder");
		save.setBounds(((int) getToolkit().getScreenSize().getWidth() * 2 / 4) + 250,
				(int) getToolkit().getScreenSize().getHeight() * 15 / 20, 200, 40);
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
				if (newLastnameEmployee.equals("") || newNameEmployee.equals("") || newFunctionEmployee.equals("")) {
					JOptionPane.showMessageDialog(null, "Champs vide", "Erreur", JOptionPane.ERROR_MESSAGE);
				} else {
					/**
					 * if they want to update the password they will input the current password to
					 * be verify by system
					 */
					if (!(newPasswordEmployee.equals(""))) {
						String verificationPassword = JOptionPane.showInputDialog(null,
								"Inserez ancien votre mot de passe", "");
						String request = "CONNECTION";
						employee2 = new Employee();

						employee2.setIdEmployee(idemployee);
						employee2.setPassword(verificationPassword);
						try {
							jsonString = readMapper.writeValueAsString(employee2);
							new ClientSocket(request, jsonString, table);
							jsonString = ClientSocket.getJson();
							employee2 = readMapper.readValue(jsonString, Employee.class);
						} catch (IOException e1) {
							logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
						}
						if (!(employee2.getLastnameEmployee().equals(""))) {
							employee.setIdEmployee(idemployee);
							employee.setLastnameEmployee(newLastnameEmployee);
							employee.setNameEmployee(newNameEmployee);
							employee.setPoste(newFunctionEmployee);
							employee.setPassword(newPasswordEmployee);
							textInputPasswordEmployee.setText("");
							try {
								jsonString = readMapper.writeValueAsString(employee);
								new ClientSocket(requestType, jsonString, table);
								jsonString = ClientSocket.getJson();
								if (!jsonString.equals("UPDATED")) {
									JOptionPane.showMessageDialog(null, "La mise a jour a échoué", "Erreur",
											JOptionPane.ERROR_MESSAGE);
									logger.log(Level.INFO, "Impossible to update employee");
								} else {
									logger.log(Level.INFO, "Update Succeded");
									JOptionPane.showMessageDialog(null, "Données Mises à jours", "Infos",
											JOptionPane.INFORMATION_MESSAGE);
								}
							} catch (Exception e1) {
								logger.log(Level.INFO,
										"Impossible to parse in JSON " + e1.getClass().getCanonicalName());
							}
						} else {
							JOptionPane.showMessageDialog(null,
									"Prend nous pour des amateurs encore une fois et on te bloque",
									"Erreur, Mauvais mot de passe", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						employee.setIdEmployee(idemployee);
						employee.setLastnameEmployee(newLastnameEmployee);
						employee.setNameEmployee(newNameEmployee);
						employee.setPoste(newFunctionEmployee);
					}
				}
			}
		});

		/**
		 * Definition of Button Restaure
		 */
		restaure = new JButton("Annuler");
		restaure.setBounds(((int) getToolkit().getScreenSize().getWidth() * 2 / 4),
				(int) getToolkit().getScreenSize().getHeight() * 15 / 20, 200, 40);
		this.add(restaure);
		restaure.addActionListener(new ActionListener() {
			/**
			 * when we pressed the button restaure we initialize the textArea with the last
			 * informations of the employee
			 */
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
		this.setBackground(color);
	}
}
