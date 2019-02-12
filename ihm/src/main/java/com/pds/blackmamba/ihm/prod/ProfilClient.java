package com.pds.blackmamba.ihm.prod;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.pds.blackmamba.ihm.prod.connectionpool.DataSource;
import com.pds.blackmamba.ihm.prod.connectionpool.JDBCConnectionPool;

public class ProfilClient extends JFrame {
	private JPanel contentPane;
	String id_employee;
	String nom_employee;
	String prenom_employee;
	private JTextField prenomField;
	private JTextField nomField;
	private JPasswordField passwordField;
	JOptionPane jop3;

	ResultSet result = null;
	ResultSetMetaData resultMeta = null;

	public ProfilClient() throws SQLException {
		setTitle("Liste Employee");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		int x = 0;
		int y = 0;
		try {
			JDBCConnectionPool p;
			p = new JDBCConnectionPool(false);
			Connection con = DataSource.getConnectionFromJDBC(p);
			Statement st = con.createStatement();
			String sql = "SELECT * FROM employee";
			result = st.executeQuery(sql);
			resultMeta = result.getMetaData();
		} catch (Exception e1) {
			System.out.println("erreur dans la recuperation");
		}
		try {
			while (result.next()) {
				x = y + 30;
				y = x + 30;
				for (int i = 1; i <= resultMeta.getColumnCount(); i++) {
					if (i == 1) {
						id_employee = result.getObject(i).toString();
					}
					if (i == 2) {
						nom_employee = result.getObject(i).toString();
					}
					if (i == 3) {
						prenom_employee = result.getObject(i).toString();
					}
				}
				JLabel prenom = new JLabel("Pr\u00E9nom " + prenom_employee);
				prenom.setBounds(270, x, 200, 14);
				contentPane.add(prenom);

				JLabel idemployee = new JLabel("Id employé " + id_employee);
				idemployee.setBounds(10, x, 200, 14);
				contentPane.add(idemployee);

				JLabel nom = new JLabel("Votre nom " + nom_employee);
				nom.setBounds(150, x, 200, 14);
				contentPane.add(nom);

				JButton suppbouton = new JButton("Supprimer :" + id_employee);
				suppbouton.setBounds(380, x, 100, 23);
				contentPane.add(suppbouton);

				suppbouton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						String s1 = suppbouton.getText().substring(11);
						int i = Integer.parseInt(s1);

						JDBCConnectionPool p;
						try {
							p = new JDBCConnectionPool(false);
							Connection con = DataSource.getConnectionFromJDBC(p);
							Statement st = con.createStatement();
							String sql = "DELETE FROM employee where id_employee = " + i;
							st.execute(sql);
						} catch (Exception e1) {
							System.out.println("erreur dans la suppression");

						}

						try {
							ProfilClient frame = new ProfilClient();
							frame.setVisible(true);
							setVisible(false);
							dispose();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				JLabel modif = new JLabel("Modifiez");
				modif.setBounds(10, y, 200, 14);
				contentPane.add(modif);
				
				prenomField = new JTextField();
				prenomField.setBounds(270, y, 101, 20);
				contentPane.add(prenomField);
				prenomField.setColumns(10);
				
				nomField = new JTextField();
				nomField.setBounds(150, y, 101, 20);
				contentPane.add(nomField);
				nomField.setColumns(10);
				
				JButton modifbouton = new JButton("Sauvegarder :" + id_employee);
				modifbouton.setBounds(380, y, 100, 23);
				contentPane.add(modifbouton);

				modifbouton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						String s1 = modifbouton.getText().substring(13);
						int i = Integer.parseInt(s1);
						String nomfield = nomField.getText();
						String prenomfield = prenomField.getText();

						JDBCConnectionPool p;
						try {
							String sql = null;
							p = new JDBCConnectionPool(false);
							Connection con = DataSource.getConnectionFromJDBC(p);
							Statement st = con.createStatement();
							if (nomfield.equals("") && !(prenomfield.equals(""))) {
								sql = "UPDATE employee SET prenom_employee = '"+prenomfield+"' where id_employee = " + i;
							}
							if (prenomfield.equals("") && !(nomfield.equals(""))) {
								sql = "UPDATE employee SET nom_employee = '"+nomfield+"' where id_employee = " + i;
							}
							if (nomfield.equals("") && (prenomfield.equals(""))) {
								jop3 = new JOptionPane();
								jop3.showMessageDialog(null, "Champs vide", "Erreur",
										JOptionPane.ERROR_MESSAGE);
							}
							if (!(prenomfield.equals("")) && !(nomfield.equals(""))) {
								sql = "UPDATE employee SET nom_employee = '"+nomfield+"' where id_employee = " + i;
							}
							st.execute(sql);
						} catch (Exception e1) {
							System.out.println("erreur dans l'update");

						}

						try {
							ProfilClient frame = new ProfilClient();
							frame.setVisible(true);
							setVisible(false);
							dispose();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JButton backbouton = new JButton("Retour");
		backbouton.setBounds(0, 0, 80, 23);
		contentPane.add(backbouton);

		backbouton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InsertionClient frame = new InsertionClient();
				frame.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
	}
}
