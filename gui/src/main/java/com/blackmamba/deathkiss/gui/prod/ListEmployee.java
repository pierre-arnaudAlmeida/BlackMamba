package com.blackmamba.deathkiss.gui.prod;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ListEmployee extends JFrame {

	// Definition of differents fields
	private JPanel contentPane;
	private static Logger logger = Logger.getLogger("logger");

	public ListEmployee() {

		setTitle("Liste Employés");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Creation of a back button
		// And display on the contentPane
		JButton backButton = new JButton("Insertion");
		backButton.setBounds(0, 0, 100, 23);
		contentPane.add(backButton);

		backButton.addActionListener(new ActionListener() {
			/**
			 * If they click in backButton c'est will redirect to InsertionClient
			 */
			public void actionPerformed(ActionEvent e) {
				try {
					InsertionClient frame = new InsertionClient();
					frame.setVisible(true);
					setVisible(false);
					dispose();
				} catch (Exception e1) {
					logger.log(Level.INFO,
							"Impossible redirect to window 'InsertionClient' " + e1.getClass().getCanonicalName());
				}
			}
		});
		int x = 30;
		// Creation of label id employee
		// And display on the contentPane
		JLabel idemployee = new JLabel("Id :");
		idemployee.setBounds(10, x, 100, 14);
		contentPane.add(idemployee);

		// Creation of label lastname
		// And display on the contentPane
		JLabel lastname = new JLabel("Nom :");
		lastname.setBounds(100, x, 200, 14);
		contentPane.add(lastname);

		// Creation of label name
		// And display on the contentPane
		JLabel name = new JLabel("Pr\u00E9nom :");
		name.setBounds(240, x, 200, 14);
		contentPane.add(name);

		// Creation of a access button
		// And display on the contentPane
		JButton accessButton = new JButton("Voir");
		accessButton.setBounds(400, x, 100, 23);
		contentPane.add(accessButton);

		accessButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ProfilEmployee frame = new ProfilEmployee();
					frame.setVisible(true);
					setVisible(false);
					dispose();
				} catch (Exception e1) {
					logger.log(Level.INFO,
							"Impossible redirect to window 'ProfilClient' " + e1.getClass().getCanonicalName());
				}
			}
		});
	}
}
