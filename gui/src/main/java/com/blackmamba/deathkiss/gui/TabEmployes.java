package com.blackmamba.deathkiss.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
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
public class TabEmployes extends JPanel {

	private static final long serialVersionUID = 1L;
	private String requestType;
	private String table;
	private String jsonString;
	private int idemployee;
	private int index;
	private JPanel bar;
	private JPanel search;
	private JLabel labelIdEmployee;
	private JLabel labelSearch;
	private JLabel idEmployee;
	private JLabel labelLastnameEmployee;
	private JLabel labelNameEmployee;
	private JLabel labelFunction;
	private JLabel labelPassword;
	private JTextField textInputLastnameEmployee;
	private JTextField textInputNameEmployee;
	private JTextField textInputFunctionEmployee;
	private JTextField searchBar;
	private JPasswordField textInputPasswordEmployee;
	private Font policeBar;
	private Font policeLabel;
	private JButton disconnection;
	private JButton addEmployee;
	private JButton save;
	private JButton restaure;
	private JButton delete;
	private JButton validButton;
	private JCheckBox showButton;
	private Employee employee;
	private Employee employee2;
	private JScrollPane sc;
	private ObjectMapper objectMapper;
	private JComboBox typeSearch;
	private List<Employee> listEmployee = new ArrayList<Employee>();
	private static final Logger logger = LogManager.getLogger(TabProfile.class);
	private JList list;
	private DefaultListModel listM;

	public TabEmployes() {
	}

	public TabEmployes(Color color, int idemployee, String title) {
		this.idemployee = idemployee;

		/**
		 * Definition of the structure of this tab
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

		/**
		 * Definition of the panel Search
		 */
		search = new JPanel();
		search.setBackground(Color.DARK_GRAY);
		search.setBorder(BorderFactory.createMatteBorder(0, 25, 0, 25, bar.getBackground()));
		bar.add(search);

		/**
		 * Definition of the label search and add on panel search
		 */
		labelSearch = new JLabel();
		labelSearch.setText("Recherche : ");
		labelSearch.setFont(policeBar);
		labelSearch.setForeground(Color.WHITE);
		search.add(labelSearch);

		/**
		 * Definition of the textField seachBar and add panel search
		 */
		searchBar = new JTextField();
		searchBar.setPreferredSize(new Dimension(350, 30));
		search.add(searchBar);

		/**
		 * Definition of the list of possible choice
		 */
		typeSearch = new JComboBox();
		typeSearch.setPreferredSize(new Dimension(150, 30));
		search.add(typeSearch);

		/**
		 * Definition of the ValidButton
		 */
		validButton = new JButton();
		validButton.setText("Valider");
		search.add(validButton);
		validButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		
		///////////////////////// LIST EMPLOYEE////////////////////////////////////////
		employee = new Employee();
		employee.setLastnameEmployee("");
		employee.setNameEmployee("");
		employee.setPassword("");
		employee.setPoste("");

		requestType = "READ ALL";
		table = "Employee";
		objectMapper = new ObjectMapper();
		try {
			jsonString = "READ ALL";
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			Employee[] employees = objectMapper.readValue(jsonString, Employee[].class);
			listEmployee = Arrays.asList(employees);
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
		}

		listM = new DefaultListModel();
		for (Employee employees : listEmployee) {
			listM.addElement(employees.getIdEmployee() + "# " + employees.getLastnameEmployee() + " "
					+ employees.getNameEmployee() + " " + employees.getPoste());
		}

		list = new JList(listM);
		sc = new JScrollPane(list);

		sc.setBounds(30, 120, 300, ((int) getToolkit().getScreenSize().getHeight() - 300));
		this.add(sc);

		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				index = list.locationToIndex(e.getPoint());
				String substring = listM.getElementAt(index).toString();
				int position = substring.indexOf("#");
				String id = substring.substring(0, position);

				requestType = "READ";
				employee = new Employee();
				table = "Employee";
				employee.setIdEmployee(Integer.parseInt(id));
				try {
					jsonString = objectMapper.writeValueAsString(employee);
					;
					new ClientSocket(requestType, jsonString, table);
					jsonString = ClientSocket.getJson();
					employee = objectMapper.readValue(jsonString, Employee.class);
				} catch (Exception e1) {
					logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
				}
				textInputLastnameEmployee.setText(employee.getLastnameEmployee());
				textInputNameEmployee.setText(employee.getNameEmployee());
				textInputFunctionEmployee.setText(employee.getPoste());
				textInputPasswordEmployee.setText("");
			}
		};
		list.addMouseListener(mouseListener);

		///////////////////////// LABEL///////////////////////////////////////////////
		/**
		 * Definition of label LastnameEmployee
		 */
		policeLabel = new Font("Arial", Font.BOLD, (int) getToolkit().getScreenSize().getWidth() / 80);
		labelLastnameEmployee = new JLabel("Nom : ");
		labelLastnameEmployee.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 2 / 10, 200, 30);
		labelLastnameEmployee.setFont(policeLabel);
		this.add(labelLastnameEmployee);

		/**
		 * Definition of label NameEmployee
		 */
		labelNameEmployee = new JLabel("Prenom : ");
		labelNameEmployee.setBounds((int) getToolkit().getScreenSize().getWidth() * 4 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 2 / 10, 200, 30);
		labelNameEmployee.setFont(policeLabel);
		this.add(labelNameEmployee);

		/**
		 * Definition of label Password
		 */
		labelPassword = new JLabel("Mot de passe : ");
		labelPassword.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 4 / 10, 200, 30);
		labelPassword.setFont(policeLabel);
		this.add(labelPassword);

		/**
		 * Definition of label Function
		 */
		labelFunction = new JLabel("Poste : ");
		labelFunction.setBounds((int) getToolkit().getScreenSize().getWidth() * 4 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 4 / 10, 100, 30);
		labelFunction.setFont(policeLabel);
		this.add(labelFunction);

		//////////////////// TEXT AREA////////////////////////////////////////////////
		/**
		 * Definition of textArea LastnameEmployee
		 */
		textInputLastnameEmployee = new JTextField();
		textInputLastnameEmployee.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 5 / 20, 300, 40);
		textInputLastnameEmployee.setFont(policeLabel);
		textInputLastnameEmployee.setText(employee.getLastnameEmployee());
		this.add(textInputLastnameEmployee);

		/**
		 * Definition of textArea NameEmployee
		 */
		textInputNameEmployee = new JTextField();
		textInputNameEmployee.setBounds((int) getToolkit().getScreenSize().getWidth() * 4 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 5 / 20, 300, 40);
		textInputNameEmployee.setFont(policeLabel);
		textInputNameEmployee.setText(employee.getNameEmployee());
		this.add(textInputNameEmployee);

		/**
		 * Definition of textArea Password
		 */
		textInputPasswordEmployee = new JPasswordField();
		textInputPasswordEmployee.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 9 / 20, 300, 40);
		textInputPasswordEmployee.setFont(policeLabel);
		this.add(textInputPasswordEmployee);

		/**
		 * Definition of textArea Function
		 */
		textInputFunctionEmployee = new JTextField();
		textInputFunctionEmployee.setBounds((int) getToolkit().getScreenSize().getWidth() * 4 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 9 / 20, 300, 40);
		textInputFunctionEmployee.setFont(policeLabel);
		textInputFunctionEmployee.setText(employee.getPoste());
		this.add(textInputFunctionEmployee);

		///////////////////////// BUTTON/////////////////////////////////////////////////
		/**
		 * Button to displpay the password
		 */
		showButton = new JCheckBox("Montrer le mot de passe");
		showButton.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 7,
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

		/**
		 * Definition of Button AddEmployee
		 */
		addEmployee = new JButton("Ajouter");
		addEmployee.setBounds(30, (int) getToolkit().getScreenSize().getHeight() - 150, 300, 40);
		this.add(addEmployee);
		addEmployee.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String newLastnameEmployee = textInputLastnameEmployee.getText().trim();
				String newNameEmployee = textInputNameEmployee.getText().trim();
				String newFunctionEmployee = textInputFunctionEmployee.getText().trim();
				char[] password = textInputPasswordEmployee.getPassword();
				String newPasswordEmployee = new String(password);

				if (newLastnameEmployee.equals("") || newNameEmployee.equals("") || newFunctionEmployee.equals("")) {
					JOptionPane.showMessageDialog(null, "Champs vide", "Erreur", JOptionPane.ERROR_MESSAGE);
				} else {
					requestType = "READ";
					table = "Employee";
					try {
						jsonString = objectMapper.writeValueAsString(employee);
						;
						new ClientSocket(requestType, jsonString, table);
						jsonString = ClientSocket.getJson();
						employee = objectMapper.readValue(jsonString, Employee.class);
					} catch (Exception e1) {
						logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
					}
					if (newLastnameEmployee.equals(employee.getLastnameEmployee())
							&& newNameEmployee.equals(employee.getNameEmployee())
							&& newFunctionEmployee.equals(employee.getPoste())) {
						JOptionPane.showMessageDialog(null, "Un employé possédant déja ces informations existe déja",
								"Infos", JOptionPane.WARNING_MESSAGE);
					} else {
						requestType = "CREATE";
						table = "Employee";

						employee.setLastnameEmployee(newLastnameEmployee);
						employee.setNameEmployee(newNameEmployee);
						employee.setPassword(newPasswordEmployee);
						employee.setPoste(newFunctionEmployee);
						try {
							jsonString = objectMapper.writeValueAsString(employee);
							new ClientSocket(requestType, jsonString, table);
							jsonString = ClientSocket.getJson();
							if (!jsonString.equals("INSERTED")) {
								JOptionPane.showMessageDialog(null, "L'insertion a échoué", "Erreur",
										JOptionPane.ERROR_MESSAGE);
								logger.log(Level.INFO, "Impossible to insert employee");
							} else {
								logger.log(Level.INFO, "Insertion Succeded");
								requestType = "READ ALL";
								table = "Employee";
								jsonString = "READ ALL";
								new ClientSocket(requestType, jsonString, table);
								jsonString = ClientSocket.getJson();
								Employee[] employees = objectMapper.readValue(jsonString, Employee[].class);
								listEmployee = Arrays.asList(employees);

								int x = listEmployee.size() - 1;

								employee = listEmployee.get(x);
								listM.addElement(employee.getIdEmployee() + "# " + employee.getLastnameEmployee() + " "
										+ employee.getNameEmployee() + " " + employee.getPoste());
								JOptionPane.showMessageDialog(null, "L'insertion a été éffectué", "Infos",
										JOptionPane.INFORMATION_MESSAGE);
							}
						} catch (Exception e1) {
							logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
						}
					}
				}
			}
		});

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

						employee2.setIdEmployee(employee.getIdEmployee());
						employee2.setPassword(verificationPassword);
						try {
							jsonString = objectMapper.writeValueAsString(employee2);
							new ClientSocket(request, jsonString, table);
							jsonString = ClientSocket.getJson();
							employee2 = objectMapper.readValue(jsonString, Employee.class);
						} catch (IOException e1) {
							logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
						}
						if (!(employee2.getLastnameEmployee().equals(""))) {
							employee.setLastnameEmployee(newLastnameEmployee);
							employee.setNameEmployee(newNameEmployee);
							employee.setPoste(newFunctionEmployee);
							employee.setPassword(newPasswordEmployee);
							try {
								jsonString = objectMapper.writeValueAsString(employee);
								new ClientSocket(requestType, jsonString, table);
								jsonString = ClientSocket.getJson();
								if (!jsonString.equals("UPDATED")) {
									JOptionPane.showMessageDialog(null, "La mise a jour a échoué", "Erreur",
											JOptionPane.ERROR_MESSAGE);
									logger.log(Level.INFO, "Impossible to update employee");
								} else {
									logger.log(Level.INFO, "Update Succeded");
									textInputPasswordEmployee.setText("");
									listM.set(index, employee.getIdEmployee() + "# " + employee.getLastnameEmployee()
											+ " " + employee.getNameEmployee() + " " + employee.getPoste() + "");
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

			@Override
			public void actionPerformed(ActionEvent e) {
				textInputLastnameEmployee.setText(employee.getLastnameEmployee());
				textInputNameEmployee.setText(employee.getNameEmployee());
				textInputFunctionEmployee.setText(employee.getPoste());
				textInputPasswordEmployee.setText("");
			}
		});

		/**
		 * Definition of Button Delete
		 */
		delete = new JButton("Supprimer");
		delete.setBounds(((int) getToolkit().getScreenSize().getWidth() * 2 / 4) - 250,
				(int) getToolkit().getScreenSize().getHeight() * 15 / 20, 200, 40);
		this.add(delete);
		delete.addActionListener(new ActionListener() {
			/**
			 * When we pressed the button delete we get the id of the employee and we send
			 * it to server, to be deleted by him
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				if (employee.getIdEmployee() != 0) {
					requestType = "DELETE";
					table = "Employee";
					try {
						jsonString = objectMapper.writeValueAsString(employee);
						new ClientSocket(requestType, jsonString, table);
						jsonString = ClientSocket.getJson();
						if (!jsonString.equals("DELETED")) {
							JOptionPane.showMessageDialog(null, "La suppression a échoué", "Erreur",
									JOptionPane.ERROR_MESSAGE);
							logger.log(Level.INFO, "Impossible to delete this employee");
						} else {
							JOptionPane.showMessageDialog(null, "Suppression de l'Employé", "Infos",
									JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (Exception e1) {
						logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
					}
					listM.removeElementAt(index);
					textInputLastnameEmployee.setText("");
					textInputNameEmployee.setText("");
					textInputFunctionEmployee.setText("");
					textInputPasswordEmployee.setText("");
				} else {
					JOptionPane.showMessageDialog(null, "Veuillez selectionner un employé à supprimer", "Erreur",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		/**
		 * Diferent parameters of the window
		 */
		this.setLayout(new BorderLayout());
		this.add(bar, BorderLayout.NORTH);
		this.setBackground(color);

		// TODO mettre une barre de recherche et on affiche les résultat dans le Jlist
	}
}
