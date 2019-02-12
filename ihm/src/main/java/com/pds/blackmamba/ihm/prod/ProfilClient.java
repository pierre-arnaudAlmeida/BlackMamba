package com.pds.blackmamba.ihm.prod;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import com.pds.blackmamba.ihm.prod.connectionpool.DataSource;
import com.pds.blackmamba.ihm.prod.connectionpool.JDBCConnectionPool;

public class ProfilClient extends JFrame {

	// Definition of differents fields
	private JPanel contentPane;
	private String id_employee;
	private String lastname_employee;
	private String name_employee;
	private JTextField nameField;
	private JTextField lastnameField;
	static Logger logger = Logger.getLogger("logger");
	ResultSet result = null;
	ResultSetMetaData resultMeta = null;

	public ProfilClient() {

		setTitle("Liste Employé");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Creation of a back button
		// And display on the contentPane
		JButton backButton = new JButton("Insertion");
		backButton.setBounds(0, 0, 100, 23);
		contentPane.add(backButton);

		backButton.addActionListener(new ActionListener() {
			/**
			 * If they click in backButton c'est will redirect to InsertionClient
			 */
			public void actionPerformed(ActionEvent e) {
				try {
					InsertionClient frame = new InsertionClient();
					frame.setVisible(true);
					setVisible(false);
					dispose();
				} catch (Exception e1) {
					logger.log(Level.INFO,
							"Impossible to redirect to window 'InsertionClient' " + e1.getClass().getCanonicalName());
				}
			}
		});

		int x, y = 0;
		// Get all datas from the table employee in BDD
		try {
			JDBCConnectionPool p;
			p = new JDBCConnectionPool(false);
			Connection con = DataSource.getConnectionFromJDBC(p);
			Statement st = con.createStatement();
			String sql = "SELECT * FROM employee";
			result = st.executeQuery(sql);
			resultMeta = result.getMetaData();
			logger.log(Level.INFO, "Get datas in BDD succed ");
		} catch (Exception e1) {
			logger.log(Level.INFO, "Get datas in BDD failed " + e1.getClass().getCanonicalName());
		}

		try {
			// Display this for row stocked in result get from the table employee in BDD
			while (result.next()) {
				x = y + 30;
				y = x + 30;

				// Get and stock in fields the employee id, the employee lastname and employee
				// name
				for (int i = 1; i <= resultMeta.getColumnCount(); i++) {
					if (i == 1) {
						id_employee = result.getObject(i).toString();
					}
					if (i == 2) {
						lastname_employee = result.getObject(i).toString();
					}
					if (i == 3) {
						name_employee = result.getObject(i).toString();
					}
				}

				// Creation of label name
				// And display on the contentPane
				JLabel name = new JLabel("Pr\u00E9nom " + name_employee);
				name.setBounds(270, x, 200, 14);
				contentPane.add(name);

				// Creation of label id employee
				// And display on the contentPane
				JLabel idemployee = new JLabel("Id employé " + id_employee);
				idemployee.setBounds(10, x, 200, 14);
				contentPane.add(idemployee);

				// Creation of label lastname
				// And display on the contentPane
				JLabel lastname = new JLabel("Nom " + lastname_employee);
				lastname.setBounds(150, x, 200, 14);
				contentPane.add(lastname);

				// Creation of a delete button
				// And display on the contentPane
				JButton deleteButton = new JButton("Supprimer :" + id_employee);
				deleteButton.setBounds(380, x, 100, 23);
				contentPane.add(deleteButton);

				deleteButton.addActionListener(new ActionListener() {
					/**
					 * When the user pressed the button, they delete the user corresponding And open
					 * the window 'ProfilClient' with the list of users actualized
					 */
					public void actionPerformed(ActionEvent e) {

						String s1 = deleteButton.getText().substring(11);
						int i = Integer.parseInt(s1);

						JDBCConnectionPool p;
						try {
							p = new JDBCConnectionPool(false);
							Connection con = DataSource.getConnectionFromJDBC(p);
							Statement st = con.createStatement();
							String sql = "DELETE FROM employee where id_employee = " + i;
							st.execute(sql);
							logger.log(Level.INFO, "Delete data in BDD succed ");
						} catch (Exception e1) {
							logger.log(Level.INFO, "Delete data in BDD failed " + e1.getClass().getCanonicalName());
						}

						try {
							ProfilClient frame = new ProfilClient();
							frame.setVisible(true);
							setVisible(false);
							dispose();
						} catch (Exception e1) {
							logger.log(Level.INFO, "Impossible to redirect to window 'ProfilClient' "
									+ e1.getClass().getCanonicalName());
						}
					}
				});

				// Creation of label modification
				// And display on the contentPane
				JLabel modification = new JLabel("Modifiez");
				modification.setBounds(10, y, 200, 14);
				contentPane.add(modification);

				// Creation of TextField for name
				// And display on the contentPane
				nameField = new JTextField();
				nameField.setBounds(270, y, 101, 20);
				contentPane.add(nameField);
				nameField.setColumns(10);

				// Creation of TextField for lastname
				// And display on the contentPane
				lastnameField = new JTextField();
				lastnameField.setBounds(150, y, 101, 20);
				contentPane.add(lastnameField);
				lastnameField.setColumns(10);

				// Creation of a modification button
				// And display on the contentPane
				JButton modificationButton = new JButton("Sauvegarder :" + id_employee);
				modificationButton.setBounds(380, y, 100, 23);
				contentPane.add(modificationButton);

				modificationButton.addActionListener(new ActionListener() {
					/**
					 * When they pressed on modification button they check if one of the two
					 * TextField contains characters and update the BDD If no one have characters
					 * they display a popup
					 */
					public void actionPerformed(ActionEvent e) {

						String s1 = modificationButton.getText().substring(13);
						int i = Integer.parseInt(s1);
						String lastnamefield = lastnameField.getText();
						String namefield = nameField.getText();

						JDBCConnectionPool p;
						try {
							String sql = null;
							p = new JDBCConnectionPool(false);
							Connection con = DataSource.getConnectionFromJDBC(p);
							Statement st = con.createStatement();

							if (lastnamefield.equals("") && !(namefield.equals(""))) {
								sql = "UPDATE employee SET prenom_employee = '" + namefield + "' where id_employee = "
										+ i;
							}
							if (namefield.equals("") && !(lastnamefield.equals(""))) {
								sql = "UPDATE employee SET nom_employee = '" + lastnamefield + "' where id_employee = "
										+ i;
							}
							if (lastnamefield.equals("") && (namefield.equals(""))) {
								JOptionPane.showMessageDialog(null, "Champs vide", "Erreur", JOptionPane.ERROR_MESSAGE);
							}
							if (!(namefield.equals("")) && !(lastnamefield.equals(""))) {
								sql = "UPDATE employee SET nom_employee = '" + lastnamefield + "', prenom_employee = '"
										+ namefield + "' where id_employee = " + i;
							}
							st.execute(sql);
							logger.log(Level.INFO, "Update in BDD succed ");
						} catch (Exception e1) {
							logger.log(Level.INFO, "Update in BDD failed " + e1.getClass().getCanonicalName());
						}

						try {
							ProfilClient frame = new ProfilClient();
							frame.setVisible(true);
							setVisible(false);
							dispose();
						} catch (Exception e1) {
							logger.log(Level.INFO, "Impossible to redirect to window 'ProfilClient' "
									+ e1.getClass().getCanonicalName());
						}
					}
				});
			}
		} catch (Exception e) {
			logger.log(Level.INFO, "Impossible to display more rows' " + e.getClass().getCanonicalName());
		}
	}
}