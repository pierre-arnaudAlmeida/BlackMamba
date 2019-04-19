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
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class TabEmployes extends JPanel {

	/**
	 * Different parameters used
	 */
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
	private JLabel labelLastnameEmployee;
	private JLabel labelNameEmployee;
	private JLabel labelFunction;
	private JLabel labelPassword;
	private JLabel labelHeadList;
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
	private Thread threadEmployee;
	private JList<String> list;
	private DefaultListModel<String> listM;
	private List<Employee> listEmployee = new ArrayList<Employee>();
	private List<Employee> listSearchEmployee = new ArrayList<Employee>();
	private static final Logger logger = LogManager.getLogger(TabEmployes.class);
	private ResourceBundle rs = ResourceBundle.getBundle("parameters");

	public TabEmployes() {
	}

	public TabEmployes(Color color, int idemployee, String title) {
		this.idemployee = idemployee;

		///////////////////////// Thread/////////////////////////////////////////////////
		setThreadEmployee(new Thread(new Runnable() {
			/**
			 * Loop and update every 30 sec the list of employees
			 */
			@Override
			public void run() {
				while (true) {
					updateEmployeeSelected();
					updateListEmployee();
					try {
						Thread.sleep(Integer.parseInt(rs.getString("time_threadSleep")));
					} catch (InterruptedException e) {
						logger.log(Level.INFO, "Impossible to sleep the thread" + e.getClass().getCanonicalName());
					}
				}
			}
		}));

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
		labelIdEmployee = new JLabel("Identifiant :   " + this.idemployee + "    ");
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
			/**
			 * They close the application
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.log(Level.INFO, "Application closed, after disconnection");
				Thread.interrupted();
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
		 * Definition of the ValidButton
		 */
		validButton = new JButton();
		validButton.setText("Valider");
		search.add(validButton);
		validButton.addActionListener(new ActionListener() {
			/**
			 * Verify the content of the search if they match with just numerics they will
			 * send a request to search the Employee with the id written in the research the
			 * idEmployee. And add all the results on a list to display But if there is
			 * letter and numerics they will send a request to return all the Employees when
			 * the lastName, name and function contains the research
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				employee2 = new Employee();
				String searchReceived = searchBar.getText().trim();
				if (!searchReceived.equals("")) {
					/**
					 * If the research is just numerics they find first the IdEmployee
					 */
					if (searchReceived.matches("[0-9]+[0-9]*")) {
						requestType = "READ";
						employee2 = new Employee();
						table = "Employee";
						employee2.setIdEmployee(Integer.parseInt(searchReceived));
						employee2 = getEmployee(employee2, requestType, table);
						listM.removeAllElements();
						if (!employee2.getLastnameEmployee().equals("")) {
							listM.addElement("Résultat pour l'employé avec l'id : " + searchReceived);
							listM.addElement(employee2.getIdEmployee() + "# " + employee2.getLastnameEmployee() + " "
									+ employee2.getNameEmployee() + " ," + employee2.getPoste());
						}
					} else {
						/**
						 * If the research contains letter and numerics
						 */
						searchReceived = Normalizer.normalize(searchReceived, Normalizer.Form.NFD)
								.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
						employee2.setLastnameEmployee(searchReceived);
						requestType = "FIND ALL";
						table = "Employee";
						listSearchEmployee = getAllEmployee(employee2, requestType, table);
						listM.removeAllElements();
						if (listSearchEmployee.size() > 0)
							listM.addElement("Résultat pour : " + searchReceived);
						for (Employee employees : listSearchEmployee) {
							listM.addElement(employees.getIdEmployee() + "# " + employees.getLastnameEmployee() + " "
									+ employees.getNameEmployee() + " ," + employees.getPoste());
						}
					}
				} else {
					/**
					 * If the research is empty they display all the Employees
					 */
					requestType = "READ ALL";
					table = "Employee";
					listEmployee = getAllEmployee(null, requestType, table);
					listM.removeAllElements();
					listM.addElement("Tout les employés ");
					for (Employee employees : listEmployee) {
						listM.addElement(employees.getIdEmployee() + "# " + employees.getLastnameEmployee() + " "
								+ employees.getNameEmployee() + " ," + employees.getPoste());
					}
				}
				searchBar.setText("");
			}
		});

		///////////////////////// LIST EMPLOYEE////////////////////////////////////////
		listM = new DefaultListModel<String>();
		list = new JList<String>(listM);
		updateListEmployee();

		sc = new JScrollPane(list);
		sc.setBounds(30, 120, 300, ((int) getToolkit().getScreenSize().getHeight() - 300));
		this.add(sc);

		/**
		 * when we pressed a line in the list they will send a request to get all the
		 * information about the Employee selected to be displayed on the textField
		 */
		index = -9999;
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				index = list.locationToIndex(e.getPoint());
				String substring = listM.getElementAt(index).toString();
				int position = substring.indexOf("#");
				if (position > -1) {
					String id = substring.substring(0, position);

					requestType = "READ";
					employee = new Employee();
					table = "Employee";
					employee.setIdEmployee(Integer.parseInt(id));
					employee = getEmployee(employee, requestType, table);
					textInputLastnameEmployee.setText(employee.getLastnameEmployee());
					textInputNameEmployee.setText(employee.getNameEmployee());
					textInputFunctionEmployee.setText(employee.getPoste());
					textInputPasswordEmployee.setText("");
				}
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

		/**
		 * Definition of label HeadList
		 */
		labelHeadList = new JLabel("ID /Nom /Prenom /Poste");
		labelHeadList.setBounds(40, 90, 200, 30);
		labelHeadList.setFont(policeBar);
		this.add(labelHeadList);
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
			/**
			 * When we pressed the button addEmployee we supress the space and we get out
			 * the special caraters and verify if the textField are empty or not If one of
			 * them is empty they send a message to user else they send the request to
			 * server
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				String newLastnameEmployee = textInputLastnameEmployee.getText().trim();
				newLastnameEmployee = Normalizer.normalize(newLastnameEmployee, Normalizer.Form.NFD)
						.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
				String newNameEmployee = textInputNameEmployee.getText().trim();
				newNameEmployee = Normalizer.normalize(newNameEmployee, Normalizer.Form.NFD)
						.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
				String newFunctionEmployee = textInputFunctionEmployee.getText().trim();
				newFunctionEmployee = Normalizer.normalize(newFunctionEmployee, Normalizer.Form.NFD)
						.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
				char[] password = textInputPasswordEmployee.getPassword();
				String newPasswordEmployee = new String(password).trim();

				if (newLastnameEmployee.equals("") || newNameEmployee.equals("") || newFunctionEmployee.equals("")
						|| newPasswordEmployee.equals("")) {
					JOptionPane.showMessageDialog(null, "Champs vide ou contient uniquement des espaces", "Erreur",
							JOptionPane.ERROR_MESSAGE);
				} else {
					/**
					 * Get the information about the Employee and if the user want to insert a
					 * employee already inserted with the last id selected in the list, we display
					 * an pop-up
					 * 
					 */
					requestType = "READ";
					table = "Employee";
					employee = getEmployee(employee, requestType, table);
					if (newLastnameEmployee.equals(employee.getLastnameEmployee())
							&& newNameEmployee.equals(employee.getNameEmployee())
							&& newFunctionEmployee.equals(employee.getPoste())) {
						JOptionPane.showMessageDialog(null, "Un employé possédant déja ces informations existe déja",
								"Infos", JOptionPane.WARNING_MESSAGE);
					} else {
						/**
						 * Send the information to server to be inserted in data base
						 */
						requestType = "CREATE";
						table = "Employee";

						employee.setLastnameEmployee(newLastnameEmployee.toUpperCase());
						employee.setNameEmployee(newNameEmployee);
						employee.setPassword(newPasswordEmployee);
						employee.setPoste(newFunctionEmployee.toUpperCase());
						try {
							jsonString = objectMapper.writeValueAsString(employee);
							new ClientSocket(requestType, jsonString, table);
							jsonString = ClientSocket.getJson();
							if (!jsonString.equals("INSERTED")) {
								JOptionPane.showMessageDialog(null, "L'insertion a échoué", "Erreur",
										JOptionPane.ERROR_MESSAGE);
								logger.log(Level.INFO, "Impossible to insert employee");
							} else {
								/**
								 * After insertion we get the informations about the last employee inserted to
								 * be added and displayed on list
								 */
								logger.log(Level.INFO, "Insertion Succeded");
								requestType = "READ ALL";
								table = "Employee";
								listEmployee = getAllEmployee(null, requestType, table);

								int x = listEmployee.size() - 1;

								employee = listEmployee.get(x);
								listM.addElement(employee.getIdEmployee() + "# " + employee.getLastnameEmployee() + " "
										+ employee.getNameEmployee() + " ," + employee.getPoste());
								JOptionPane.showMessageDialog(null, "L'insertion a été éffectué", "Infos",
										JOptionPane.INFORMATION_MESSAGE);

								textInputLastnameEmployee.setText("");
								textInputNameEmployee.setText("");
								textInputFunctionEmployee.setText("");
								textInputPasswordEmployee.setText("");
							}
						} catch (Exception e1) {
							logger.log(Level.INFO,
									"Impossible to parse in JSON Employee datas" + e1.getClass().getCanonicalName());
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
			 * When we pressed the button save we update the Employee datas we check if the
			 * informations are correct, if the textField are not empty and we supress the
			 * special caracters
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				requestType = "UPDATE";
				table = "Employee";

				String newLastnameEmployee = textInputLastnameEmployee.getText().trim();
				newLastnameEmployee = Normalizer.normalize(newLastnameEmployee, Normalizer.Form.NFD)
						.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
				String newNameEmployee = textInputNameEmployee.getText().trim();
				newNameEmployee = Normalizer.normalize(newNameEmployee, Normalizer.Form.NFD)
						.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
				String newFunctionEmployee = textInputFunctionEmployee.getText().trim();
				newFunctionEmployee = Normalizer.normalize(newFunctionEmployee, Normalizer.Form.NFD)
						.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
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
						/**
						 * If the password is correct they send the datas to be updated
						 */
						if (!(employee2.getLastnameEmployee().equals(""))) {
							employee.setLastnameEmployee(newLastnameEmployee.toUpperCase());
							employee.setNameEmployee(newNameEmployee);
							employee.setPoste(newFunctionEmployee.toUpperCase());
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
											+ " " + employee.getNameEmployee() + " ," + employee.getPoste() + "");
									JOptionPane.showMessageDialog(null, "Données Mises à jours", "Infos",
											JOptionPane.INFORMATION_MESSAGE);
								}
							} catch (Exception e1) {
								logger.log(Level.INFO, "Impossible to parse in JSON Employee datas"
										+ e1.getClass().getCanonicalName());
							}
						} else {
							JOptionPane.showMessageDialog(null, "Isouuuuuuu", "Erreur, Mauvais mot de passe",
									JOptionPane.ERROR_MESSAGE);
						}
					} else {
						employee.setLastnameEmployee(newLastnameEmployee.toUpperCase());
						employee.setNameEmployee(newNameEmployee);
						employee.setPoste(newFunctionEmployee.toUpperCase());

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
								listM.set(index, employee.getIdEmployee() + "# " + employee.getLastnameEmployee() + " "
										+ employee.getNameEmployee() + " ," + employee.getPoste() + "");
								JOptionPane.showMessageDialog(null, "Données Mises à jours", "Infos",
										JOptionPane.INFORMATION_MESSAGE);
							}
						} catch (Exception e1) {
							logger.log(Level.INFO,
									"Impossible to parse in JSON Employee datas" + e1.getClass().getCanonicalName());
						}
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
			 * set the values of the last employee selected in the list and if there is
			 * deleted they display nothing
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
				System.out.println(index);
				if (index != -9999) {
					String substring = listM.getElementAt(index).toString();
					int position = substring.indexOf("#");
					if (position > -1) {
						if (employee.getIdEmployee() != idemployee && employee.getIdEmployee() != 1) {
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
									JOptionPane.showMessageDialog(null, "Suppression de l'Employé", "Info",
											JOptionPane.INFORMATION_MESSAGE);
								}
							} catch (Exception e1) {
								logger.log(Level.INFO, "Impossible to parse in JSON Employee datas"
										+ e1.getClass().getCanonicalName());
							}
							listM.removeElementAt(index);
							index = (-9999);
							employee.setIdEmployee(0);
							employee.setLastnameEmployee("");
							employee.setNameEmployee("");
							employee.setPoste("");
							employee.setPassword("");

							textInputLastnameEmployee.setText("");
							textInputNameEmployee.setText("");
							textInputFunctionEmployee.setText("");
							textInputPasswordEmployee.setText("");
						} else {
							JOptionPane.showMessageDialog(null, "Vous ne pouvez pas vous auto supprimer", "Erreur",
									JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Veuillez selectionner un employé à supprimer", "Erreur",
								JOptionPane.ERROR_MESSAGE);

					}
				} else {
					JOptionPane.showMessageDialog(null, "Veuillez selectionner un employé à supprimer", "Erreur",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		threadEmployee.start();

		///////////////////////// FRAME/////////////////////////////////////////////////
		/**
		 * Different parameters of the window
		 */
		this.setLayout(new BorderLayout());
		this.add(bar, BorderLayout.NORTH);
		this.setBackground(color);
	}

	/**
	 * Method to update the set the listEmployee with the data received from Server
	 */
	public void updateListEmployee() {
		/**
		 * Declare the Object Employee
		 */
		employee = new Employee();
		employee.setLastnameEmployee("");
		employee.setNameEmployee("");
		employee.setPassword("");
		employee.setPoste("");

		/**
		 * Find all the Employee in the data base and add on list to be displayed
		 */
		requestType = "READ ALL";
		table = "Employee";
		listEmployee = getAllEmployee(null, requestType, table);
		listM.removeAllElements();
		listM.addElement("Tout les employés ");
		for (Employee employees : listEmployee) {
			listM.addElement(employees.getIdEmployee() + "# " + employees.getLastnameEmployee() + " "
					+ employees.getNameEmployee() + " ," + employees.getPoste());
		}
		if (listM.isEmpty() && (!listEmployee.isEmpty())) {
			updateListEmployee();
		}

	}

	/**
	 * After have clicked on employee on list, the information will be updated to
	 * have the last informations
	 */
	public void updateEmployeeSelected() {
		if (index != -9999) {
			String substring = listM.getElementAt(index).toString();
			int position = substring.indexOf("#");
			if (position > -1) {
				String id = substring.substring(0, position);

				requestType = "READ";
				employee = new Employee();
				table = "Employee";
				employee.setIdEmployee(Integer.parseInt(id));
				employee = getEmployee(employee, requestType, table);
				textInputLastnameEmployee.setText(employee.getLastnameEmployee());
				textInputNameEmployee.setText(employee.getNameEmployee());
				textInputFunctionEmployee.setText(employee.getPoste());
				textInputPasswordEmployee.setText("");
			}
		}
	}

	public Employee getEmployee(Employee employee, String requestType, String table) {
		objectMapper = new ObjectMapper();
		try {
			jsonString = objectMapper.writeValueAsString(employee);
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			employee = objectMapper.readValue(jsonString, Employee.class);
			logger.log(Level.INFO, "Find Employees datas succed");
			return employee;
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON Employees datas" + e1.getClass().getCanonicalName());
			return null;
		}
	}

	public List<Employee> getAllEmployee(Employee employee, String requestType, String table) {
		objectMapper = new ObjectMapper();
		try {
			jsonString = objectMapper.writeValueAsString(employee);
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			Employee[] employees = objectMapper.readValue(jsonString, Employee[].class);
			logger.log(Level.INFO, "Find Employees datas succed");
			return Arrays.asList(employees);
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON Employee datas" + e1.getClass().getCanonicalName());
			return null;
		}
	}

	public Thread getThreadEmployee() {
		return threadEmployee;
	}

	public void setThreadEmployee(Thread threadEmployee) {
		this.threadEmployee = threadEmployee;
	}
}