package com.pds.blackmamba.ihm.dev;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TabProfile extends JPanel {
	private String message;
	private JPanel bar,body, lineLabel, lineLabel2, lineTextField, lineTextField2;
	private JLabel labelIdEmployee, idEmployee, labelLastnameEmployee, labelNameEmployee, labelFunction, labelPassword ;
	private JTextField textInputLastnameEmployee;
	private Font police;
	private JButton disconnection;
	private static final Logger logger = LogManager.getLogger(TabProfile.class);

	public TabProfile(Color color, String title) {
		/**
		 * Definition of the structures of this tab
		 */
		bar = new JPanel();
		bar.setBackground(Color.DARK_GRAY);
		bar.setPreferredSize(new Dimension((int) getToolkit().getScreenSize().getWidth(), 70));
		bar.setLayout(new BorderLayout());
		bar.setBorder(BorderFactory.createMatteBorder(20, 100, 20, 100, bar.getBackground()));
		
		body = new JPanel();
		body.setLayout(new BorderLayout());
		body.setBackground(Color.GRAY);
		body.setBorder(BorderFactory.createMatteBorder(50, 150, 50, 150, bar.getBackground()));
		
		lineLabel = new JPanel();
		lineLabel.setLayout(new BorderLayout());
		body.add(lineLabel, BorderLayout.NORTH);
		
		lineTextField = new JPanel();
		lineTextField.setLayout(new BorderLayout());
		body.add(lineTextField,BorderLayout.CENTER);
		
		lineLabel2 = new JPanel();
		lineLabel2.setLayout(new BorderLayout());
		body.add(lineLabel2, BorderLayout.SOUTH);
		
		lineTextField2 = new JPanel();
		lineTextField2.setLayout(new BorderLayout());
		body.add(lineTextField2);

		/**
		 * Definition of label Identifiant on header bar
		 */
		labelIdEmployee = new JLabel("Identifiant :   ");
		police = new Font("Arial", Font.BOLD, 16);
		labelIdEmployee.setForeground(Color.WHITE);
		labelIdEmployee.setFont(police);
		bar.add(labelIdEmployee, BorderLayout.WEST);
		
		/**
		 * Definition of the label idEmployee on header bar
		 */
		idEmployee = new JLabel();
		idEmployee.setText("1");
		idEmployee.setFont(police);
		idEmployee.setForeground(Color.WHITE);
		bar.add(idEmployee, BorderLayout.CENTER);
		
		/**
		 * Definition of the button and the different action after pressed the button
		 */
		disconnection = new JButton("Se Déconnecter");
		bar.add(disconnection,BorderLayout.EAST);
		disconnection.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.log(Level.INFO, "Application closed, after disconnection");
				System.exit(ABORT);
				
			}
		});
		
		labelLastnameEmployee = new JLabel("Nom : ");
		lineLabel.add(labelLastnameEmployee, BorderLayout.WEST);
		
		labelNameEmployee = new JLabel("Prenom : ");
		lineLabel.add(labelNameEmployee, BorderLayout.EAST);
		
		textInputLastnameEmployee = new JTextField();
		lineTextField.add(textInputLastnameEmployee, BorderLayout.WEST);
		
		labelFunction = new JLabel("Poste : ");
		lineLabel2.add(labelFunction, BorderLayout.WEST);
		
		labelPassword = new JLabel("Mot de passe : ");
		lineLabel2.add(labelPassword, BorderLayout.EAST);
		
		
		
		/**
		 * Diferent parameters of the window
		 */
		this.setLayout(new BorderLayout());
		this.add(bar, BorderLayout.NORTH);
		this.add(body,BorderLayout.CENTER);
		this.message = title;
		this.setBackground(color);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
