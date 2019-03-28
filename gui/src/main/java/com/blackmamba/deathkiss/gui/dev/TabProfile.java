package com.blackmamba.deathkiss.gui.dev;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class TabProfile extends JPanel {
	private String message;
	private int idemployee;
	private JPanel bar;
	private JLabel labelIdEmployee;
	private JLabel idEmployee;
	private JLabel labelLastnameEmployee;
	private JLabel labelNameEmployee;
	private JLabel labelFunction;
	private JLabel labelPassword;
	private JTextField textInputLastnameEmployee;
	private JTextField textInputPasswordEmployee;
	private JTextField textInputNameEmployee;
	private JTextField textInputFunctionEmployee;
	private Font policeBar;
	private Font policeLabel;
	private JButton disconnection;
	private static final Logger logger = LogManager.getLogger(TabProfile.class);

	public TabProfile(Color color, String title, int idemployee) {
		this.idemployee = idemployee;

		/**
		 * Definition of the structures of this tab
		 */
		bar = new JPanel();
		bar.setBackground(Color.DARK_GRAY);
		bar.setPreferredSize(new Dimension((int) getToolkit().getScreenSize().getWidth(), 70));
		bar.setLayout(new BorderLayout());
		bar.setBorder(BorderFactory.createMatteBorder(20, 100, 20, 100, bar.getBackground()));

		/***********
		 * Label Bar Definition******************* Definition of label Identifiant on
		 * header bar
		 */
		labelIdEmployee = new JLabel("Identifiant :   ");
		policeBar = new Font("Arial", Font.BOLD, 16);
		labelIdEmployee.setForeground(Color.WHITE);
		labelIdEmployee.setFont(policeBar);
		bar.add(labelIdEmployee, BorderLayout.WEST);

		/**
		 * Definition of the label idEmployee on header bar
		 */
		idEmployee = new JLabel();
		idEmployee.setText("" + this.idemployee + "");
		idEmployee.setFont(policeBar);
		idEmployee.setForeground(Color.WHITE);
		bar.add(idEmployee, BorderLayout.CENTER);

		/**
		 * Definition of the button and the different action after pressed the button
		 */
		disconnection = new JButton("Se Déconnecter");
		bar.add(disconnection, BorderLayout.EAST);
		disconnection.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.log(Level.INFO, "Application closed, after disconnection");
				System.exit(ABORT);

			}
		});

		/**
		 * Definition of label LastnameEmployee
		 */
		policeLabel = new Font("Arial", Font.BOLD, 20);
		labelLastnameEmployee = new JLabel("Nom : ");
		labelLastnameEmployee.setBounds(330, 200, 100, 30);
		labelLastnameEmployee.setFont(policeLabel);
		this.add(labelLastnameEmployee);

		/**
		 * Definition of label NameEmployee
		 */
		labelNameEmployee = new JLabel("Prenom : ");
		labelNameEmployee.setBounds(770, 200, 100, 30);
		labelNameEmployee.setFont(policeLabel);
		this.add(labelNameEmployee);

		/**
		 * Definition of label Function
		 */
		labelFunction = new JLabel("Poste : ");
		labelFunction.setBounds(770, 350, 100, 30);
		labelFunction.setFont(policeLabel);
		this.add(labelFunction);

		/**
		 * Definition of label Password
		 */
		labelPassword = new JLabel("Mot de passe : ");
		labelPassword.setBounds(330, 350, 200, 30);
		labelPassword.setFont(policeLabel);
		this.add(labelPassword);

		/**
		 * Definition of textArea LastnameEmployee
		 */
		textInputLastnameEmployee = new JTextField();
		textInputLastnameEmployee.setBounds(330, 250, 300, 40);
		textInputLastnameEmployee.setFont(policeLabel);
		this.add(textInputLastnameEmployee);

		/**
		 * Definition of textArea NameEmployee
		 */
		textInputNameEmployee = new JTextField();
		textInputNameEmployee.setBounds(770, 250, 300, 40);
		textInputNameEmployee.setFont(policeLabel);
		this.add(textInputNameEmployee);

		/**
		 * Definition of textArea Password
		 */
		textInputPasswordEmployee = new JTextField();
		textInputPasswordEmployee.setBounds(330, 400, 300, 40);
		textInputPasswordEmployee.setFont(policeLabel);
		this.add(textInputPasswordEmployee);

		/**
		 * Definition of textArea Function
		 */
		textInputFunctionEmployee = new JTextField();
		textInputFunctionEmployee.setBounds(770, 400, 300, 40);
		textInputFunctionEmployee.setFont(policeLabel);
		this.add(textInputFunctionEmployee);

		/**
		 * Diferent parameters of the window
		 */
		this.setLayout(new BorderLayout());
		this.add(bar, BorderLayout.NORTH);
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
