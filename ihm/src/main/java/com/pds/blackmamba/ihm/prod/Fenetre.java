package com.pds.blackmamba.ihm.prod;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.pds.blackmamba.ihm.prod.connectionpool.DataSource;
import com.pds.blackmamba.ihm.prod.connectionpool.JDBCConnectionPool;

public class Fenetre extends JFrame {

	// définition des champs
	private JPanel contentPane;
	private JTextField prenomField;
	private JTextField nomField;
	private JTextField id_employeeField;
	private JPasswordField passwordField;
	ResultSet resultat = null;
	ResultSetMetaData resultMeta = null;
	JOptionPane jop3;

	public Fenetre() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// affichage des zones de textes
		JLabel prenom = new JLabel("Votre Pr\u00E9nom");
		prenom.setBounds(147, 21, 72, 14);
		contentPane.add(prenom);

		prenomField = new JTextField();
		prenomField.setBounds(147, 34, 96, 20);
		contentPane.add(prenomField);
		prenomField.setColumns(10);

		// quand on appuye sur le bouton valider on va se connecter a la base et
		// effectuer une requete
		// avec les infos inserer dans les champs
		JButton validerbouton = new JButton("Valider");
		validerbouton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String prenomfield = prenomField.getText();
				String nomfield = nomField.getText();
				char[] password = passwordField.getPassword();
				String passwordfield = new String(password);

				if (prenomfield.equals("") || nomfield.equals("") || passwordfield.equals("")) {
					jop3 = new JOptionPane();
					jop3.showMessageDialog(null, "Vous n'avez pas remplis l'un des 3 champs requis", "Erreur",
							JOptionPane.ERROR_MESSAGE);
				} else {
					JDBCConnectionPool p;
					try {
						p = new JDBCConnectionPool(false);
						Connection con = DataSource.getConnectionFromJDBC(p);
						Statement st = con.createStatement();
						String sql = "insert into employee (nom_employee, prenom_employee, mot_de_passe) values ('"
								+ nomfield + "','" + prenomfield + "','" + passwordfield + "')";
						st.execute(sql);
					} catch (SQLException e1) {
						System.out.println("erreur dans l'insertion");
					}

					try {
						p = new JDBCConnectionPool(false);
						Connection con = DataSource.getConnectionFromJDBC(p);
						Statement st = con.createStatement();
						String sql = "SELECT * FROM employee";
						resultat = st.executeQuery(sql);
						resultMeta = resultat.getMetaData();
					} catch (SQLException e1) {
						System.out.println("erreur dans la recuperation");
					}

					try {
						FenetreResultat frame = new FenetreResultat(resultat, resultMeta);
						frame.setVisible(true);
						setVisible(false);
						dispose();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		validerbouton.setBounds(300, 122, 89, 23);
		contentPane.add(validerbouton);

		nomField = new JTextField();
		nomField.setBounds(266, 34, 96, 20);
		contentPane.add(nomField);
		nomField.setColumns(10);

		JLabel nom = new JLabel("Votre nom");
		nom.setBounds(264, 21, 98, 14);
		contentPane.add(nom);

		JLabel id_employee = new JLabel("Id employee");
		id_employee.setBounds(10, 21, 81, 14);
		contentPane.add(id_employee);

		id_employeeField = new JTextField();
		id_employeeField.setBounds(10, 34, 96, 20);
		contentPane.add(id_employeeField);
		id_employeeField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(147, 123, 121, 20);
		contentPane.add(passwordField);

		// permet d'afficher le contenu du champs mot de passe
		final JCheckBox Montrermdp = new JCheckBox("Montrer le mot de passe");
		Montrermdp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Montrermdp.isSelected()) {
					passwordField.setEchoChar((char) 0);
				} else {
					passwordField.setEchoChar('*');
				}
			}
		});
		Montrermdp.setBounds(147, 150, 171, 23);
		contentPane.add(Montrermdp);
	}
}