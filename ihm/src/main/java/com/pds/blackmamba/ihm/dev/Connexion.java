package com.pds.blackmamba.ihm.dev;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Connexion extends JFrame {

	private JPanel container;
	private JPasswordField textInputPassword;
	private JTextField textInputIdEmployee;
	private JLabel labelIdEmployee;
	private JLabel labelPassword;
	private JButton buttonConnection, buttonLeave;
	private JPanel pan, pan2, pan3;
	private Font police;
	private String idEmployee, password;
	char[] passwordfield;
	private static final Logger logger = LogManager.getLogger(Connexion.class);

	public Connexion() {
		container = new JPanel();
		labelIdEmployee = new JLabel("Identifiant");
		textInputIdEmployee = new JTextField();
		labelPassword = new JLabel("Mot de Passe");
		textInputPassword = new JPasswordField();
		buttonConnection = new JButton("Se Connecter");
		buttonLeave = new JButton("Quitter");

		container.setBackground(Color.DARK_GRAY);
		pan = new JPanel();
		pan2 = new JPanel();
		pan3 = new JPanel();
		police = new Font("Arial", Font.BOLD, 14);

		/**
		 * Line who get the identifiant employee
		 */
		labelIdEmployee.setPreferredSize(new Dimension(100, 30));
		pan.add(labelIdEmployee);
		textInputIdEmployee.setFont(police);
		textInputIdEmployee.setPreferredSize(new Dimension(150, 30));
		textInputIdEmployee.setForeground(Color.BLACK);
		pan.add(textInputIdEmployee);
		pan.setBackground(Color.WHITE);

		/**
		 * Line who get the password employee
		 */
		labelPassword.setPreferredSize(new Dimension(100, 30));
		pan2.add(labelPassword);
		textInputPassword.setFont(police);
		textInputPassword.setPreferredSize(new Dimension(150, 30));
		textInputPassword.setForeground(Color.BLACK);
		pan2.add(textInputPassword);
		pan2.setBackground(Color.WHITE);

		
		final JCheckBox showButton = new JCheckBox("Montrer le mot de passe");
		showButton.setBounds(147, 150, 171, 23);
		showButton.addActionListener(new ActionListener() {
			/**
			 * Display the content of TextField password employee When we check the CheckBox
			 * "Montrer le mot de passe"
			 */
			public void actionPerformed(ActionEvent e) {
				if (showButton.isSelected()) {
					textInputPassword.setEchoChar((char) 0);
				} else {
					textInputPassword.setEchoChar('*');
				}
			}
		});
		
		/**
		 * Actions when we pressed the button Connection 
		 */
		buttonConnection.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				idEmployee = textInputIdEmployee.getText();
				passwordfield = textInputPassword.getPassword();
				password = new String(passwordfield);

				if (!(idEmployee.matches("[0-9]+[0-9]*")) || password.equals("")) {

					JOptionPane.showMessageDialog(null, "Vous n'avez pas renseigné tout les champs", "Attention",
							JOptionPane.WARNING_MESSAGE);
				} else if ((idEmployee.matches("[0-9]+[0-9]*")) && !(password.equals(""))) {					
					Window frame = new Window();
					setVisible(false);
					dispose();
					frame.setVisible(true);
					System.out.println(idEmployee + " " + password);
				} else {
					JOptionPane.showMessageDialog(null, "Une Erreur est survenue, relancez l'application", "Attention",
							JOptionPane.WARNING_MESSAGE);
					// TODO mettre un lister au bouton ok et fermer la fenetre connexion
				}
				/**
				 * Envoi d'une requete de verif des id et mot de passe et ensuite on fait un
				 * System.exit(DISPOSE_ON_CLOSE); quand le resultat est positif
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
		container.add(showButton);
		container.add(pan3);

		this.setContentPane(container);
		this.setTitle("Connexion");
		this.setSize(350, 200);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
