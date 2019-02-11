package com.pds.blackmamba.ihm.prod;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class FenetreResultat extends JFrame {
	private JPanel contentPane;
	String id_employee = "eee";
	String nom_employee = "fff";
	String prenom_employee = "rrr";

	public FenetreResultat(ResultSet resultat, ResultSetMetaData resultatMetaData) throws SQLException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
int x = 20;
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
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
