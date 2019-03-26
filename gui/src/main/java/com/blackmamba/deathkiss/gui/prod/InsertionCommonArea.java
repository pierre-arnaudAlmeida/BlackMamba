package com.blackmamba.deathkiss.gui.prod;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class InsertionCommonArea extends JFrame {

	// Definition of differents fields
	private JPanel contentPane;
	private static Logger logger = Logger.getLogger("logger");

	public InsertionCommonArea() {

		setTitle("Insertion Employee");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Creation of a profil button
		// And display on the contentPane
		JButton profilbouton = new JButton("Liste des employes");
		profilbouton.setBounds(147, 0, 214, 23);
		contentPane.add(profilbouton);
		profilbouton.addActionListener(new ActionListener() {
			/**
			 * If we click in the profil button we are redirect to the window 'ProfilClient'
			 * where we have the list of users
			 */
			public void actionPerformed(ActionEvent e) {
				try {
					ListEmployee frame = new ListEmployee();
					frame.setVisible(true);
					setVisible(false);
				} catch (Exception e1) {
					logger.log(Level.INFO,
							"Impossible to access at window 'ListEmployee' " + e1.getClass().getCanonicalName());
				}
			}
		});

		// Creation of a list common area button
		// And display on the contentPane
		JButton listCommonAreabouton = new JButton("Liste des Parties Communes");
		listCommonAreabouton.setBounds(147, 30, 214, 23);
		contentPane.add(listCommonAreabouton);
		listCommonAreabouton.addActionListener(new ActionListener() {
			/**
			 * If we click in the listCommonAreabouton we are redirect to the window
			 * 'CommonArea' where we have the list of users
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ListCommonArea frame = new ListCommonArea();
					frame.setVisible(true);
					setVisible(false);
				} catch (Exception e1) {
					logger.log(Level.INFO,
							"Impossible to access at window 'ListCommonArea' " + e1.getClass().getCanonicalName());
				}
			}
		});
		
		
	}
}
