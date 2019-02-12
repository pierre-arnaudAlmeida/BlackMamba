package com.pds.blackmamba.ihm.prod;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
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
import javax.swing.border.EmptyBorder;

import com.pds.blackmamba.ihm.prod.connectionpool.DataSource;
import com.pds.blackmamba.ihm.prod.connectionpool.JDBCConnectionPool;

public class Login extends JFrame{
	
	// définition des champs
	private JPanel contentPane;
	private JTextField id_employeeField;
	private JPasswordField passwordField;
	ResultSet resultat = null;
	JOptionPane jop3;
	
	public Login() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel id_employee = new JLabel("Id employee");
		id_employee.setBounds(147, 21, 81, 14);
		contentPane.add(id_employee);
		
		id_employeeField = new JTextField();
		id_employeeField.setBounds(147, 44, 96, 20);
		contentPane.add(id_employeeField);
		id_employeeField.setColumns(10);
		
		JLabel password_employee = new JLabel("Mot de passe");
		password_employee.setBounds(147, 100, 81, 14);
		contentPane.add(password_employee);
		
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
		
		JButton validerbouton = new JButton("Valider");
		validerbouton.setBounds(300, 122, 89, 23);
		contentPane.add(validerbouton);
		
		validerbouton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String identifiantfield = id_employeeField.getText();
				char[] password = passwordField.getPassword();
				String passwordfield = new String(password);
				
				if (passwordfield.equals("")|| !(identifiantfield.matches("[0-9]+[0-9]*"))) {
					jop3 = new JOptionPane();
					jop3.showMessageDialog(null, "L'identifiant ou le mot de passe est incorrect", "Erreur",
							JOptionPane.ERROR_MESSAGE);
				}else {
					JDBCConnectionPool p;
					try {
						p = new JDBCConnectionPool(false);
						Connection con = DataSource.getConnectionFromJDBC(p);
						Statement st = con.createStatement();
						String sql = "SELECT nom_employee,prenom_employee FROM employee where ("+identifiantfield+" = id_employee) and ('"+passwordfield+"' = mot_de_passe)";
						resultat = st.executeQuery(sql);
						
						resultat.next();
						String nom = resultat.getObject(1).toString();
						String prenom = resultat.getObject(2).toString();
						if (!nom.equals("")) {
							InsertionClient frame = new InsertionClient();
							frame.setVisible(true);
							setVisible(false);
							dispose();
						}
					} catch (Exception e1) {
						System.out.println("erreur dans la verification");
						jop3 = new JOptionPane();
						jop3.showMessageDialog(null, "L'identifiant ou le mot de passe est incorrect", "Erreur",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
	}
}
