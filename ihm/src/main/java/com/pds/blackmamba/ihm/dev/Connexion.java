package com.pds.blackmamba.ihm.dev;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Connexion extends JFrame{
	
	  private JPanel container;
	  private JTextField textInputPassword;
	  private JTextField textInputIdEmployee;
	  private JLabel labelIdEmployee;
	  private JLabel labelPassword;
	  private JButton buttonConnection,buttonLeave;
	  private JPanel pan,pan2,pan3;
	  private Font police;
	  private String idEmployee, password;
	  private static final Logger logger = LogManager.getLogger(Connexion.class);
	  
	public Connexion() {
		container = new JPanel();
		labelIdEmployee = new JLabel("Identifiant");
		textInputIdEmployee = new JTextField();
		labelPassword = new JLabel("Mot de Passe");
		textInputPassword = new JTextField();
		buttonConnection = new JButton ("Se Connecter");
		buttonLeave = new JButton ("Quitter");
				
		container.setBackground(Color.WHITE);
	    pan = new JPanel(); 
	    pan2 = new JPanel();
	    pan3 = new JPanel();
	    police = new Font("Arial", Font.BOLD, 14);
	      
	    labelIdEmployee.setPreferredSize(new Dimension(100, 30));
	    pan.add(labelIdEmployee);
	    textInputIdEmployee.setFont(police);
	    textInputIdEmployee.setPreferredSize(new Dimension(150, 30));
	    textInputIdEmployee.setForeground(Color.BLACK);
	    pan.add(textInputIdEmployee);
	    pan.setBackground(Color.GRAY);
	    
	    labelPassword.setPreferredSize(new Dimension(100, 30));
	    pan2.add(labelPassword);
	    textInputPassword.setFont(police);
	    textInputPassword.setPreferredSize(new Dimension(150, 30));
	    textInputPassword.setForeground(Color.BLACK);
	    pan2.add(textInputPassword);
	    pan2.setBackground(Color.GRAY);
	    
	    buttonConnection.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				idEmployee = textInputIdEmployee.getText();
				password = textInputPassword.getText();
				if(idEmployee.equals("") || password.equals("")) {
					JOptionPane.showMessageDialog(null, "Vous n'avez pas renseigné tout les champs","Attention" ,  JOptionPane.WARNING_MESSAGE);
				}
				if(!(idEmployee.equals("")) && !(password.equals(""))) {
					System.out.println(idEmployee +" "+password);
				}else {
					JOptionPane.showMessageDialog(null, "Une Erreur est survenue, relancez l'application","Attention" ,  JOptionPane.WARNING_MESSAGE);
					// TODO mettre un lister au bouton ok et fermer la fenetre connexion
				}
				/**
				 * Envoi d'une requete de verif des id et mot de passe
				 * et ensuite on fait un System.exit(DISPOSE_ON_CLOSE);
				 * quand le resultat est positif
				 */
			}
		});
	    
	    buttonLeave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.log(Level.INFO, "Fermeture de l'application, connexion non voulu");
				System.exit(DISPOSE_ON_CLOSE);
			}
		});
	    pan3.add(buttonConnection);
	    pan3.add(buttonLeave);
	    container.add(pan);
	    container.add(pan2);
	    container.add(pan3);
	    this.setContentPane(container);
		this.setTitle("Connexion");
		this.setSize(350,200);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
