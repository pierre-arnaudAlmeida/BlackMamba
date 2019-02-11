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
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.pds.blackmamba.ihm.prod.connectionpool.DataSource;
import com.pds.blackmamba.ihm.prod.connectionpool.JDBCConnectionPool;

public class FenetreResultat extends JFrame {
	private JPanel contentPane;
	String id_employee = "eee";
	String nom_employee = "fff";
	String prenom_employee = "rrr";
	
	ResultSet result = null;
	ResultSetMetaData resultMeta = null;

	public FenetreResultat(ResultSet resultat, ResultSetMetaData resultatMetaData) throws SQLException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
int x = 0;
		try {
			while (resultat.next()) {
				x = x+30;
				for(int i = 1; i <= resultatMetaData.getColumnCount(); i++) {
					if(i==1) {
						id_employee = resultat.getObject(i).toString();
					}
					if(i==2) {
						nom_employee = resultat.getObject(i).toString();
					}
					if(i==3) {
						prenom_employee = resultat.getObject(i).toString();
					}
				}
				JLabel prenom = new JLabel("Pr\u00E9nom " + prenom_employee);
				prenom.setBounds(270, x, 200, 14);
				contentPane.add(prenom);

				JLabel idemployee = new JLabel("Id employee " + id_employee);
				idemployee.setBounds(10, x, 200, 14);
				contentPane.add(idemployee);

				JLabel nom = new JLabel("Votre nom " + nom_employee);
				nom.setBounds(150, x, 200, 14);
				contentPane.add(nom);
				
				
				JButton suppbouton = new JButton("Supprimer :"+id_employee);
				suppbouton.setBounds(380, x, 100, 23);
				contentPane.add(suppbouton);
				
				suppbouton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						String s1= suppbouton.getText().substring(11);
						int i = Integer.parseInt(s1);
						
						JDBCConnectionPool p;
						try {
							p = new JDBCConnectionPool(false);
							Connection con = DataSource.getConnectionFromJDBC(p);
							Statement st = con.createStatement();
							String sql = "DELETE FROM employee where id_employee = "+i;
							st.execute(sql);
						} catch (SQLException e1) {
							System.out.println("erreur dans la suppression");
							
						}
						
						try {
							p = new JDBCConnectionPool(false);
							Connection con = DataSource.getConnectionFromJDBC(p);
							Statement st = con.createStatement();
							String sql = "SELECT * FROM employee";
							result = st.executeQuery(sql);
							resultMeta = result.getMetaData();
						} catch (SQLException e1) {
							System.out.println("erreur dans la recuperation");
						}

						try {
							FenetreResultat frame = new FenetreResultat(result, resultMeta);
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
				Fenetre frame = new Fenetre();
				frame.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
	}
}
