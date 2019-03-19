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

public class CommonArea extends JFrame {
	private JPanel contentPane;
	private int idCommonArea;
	private String nameCommonArea;
	private int etageCommonArea;
	static Logger logger = Logger.getLogger("logger");

	public CommonArea() {
		setTitle("Liste Parties Communes");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Creation of label name
		// And display on the contentPane
		JLabel name = new JLabel("Nom Partie Commune " + nameCommonArea);
		name.setBounds(50, 0, 200, 14);
		contentPane.add(name);

		// Creation of label id employee
		// And display on the contentPane
		JLabel idemployee = new JLabel("Id Partie Commune " + idCommonArea);
		idemployee.setBounds(50, 20, 200, 14);
		contentPane.add(idemployee);

		// Creation of label lastname
		// And display on the contentPane
		JLabel lastname = new JLabel("Etage Partie Commune " + etageCommonArea);
		lastname.setBounds(50, 40, 200, 14);
		contentPane.add(lastname);

		// Creation of a back button
		// And display on the contentPane
		JButton backButton = new JButton("Retour");
		backButton.setBounds(0, 100, 100, 23);
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
							"Impossible to redirect to window 'InsertionClient' " + e1.getClass().getCanonicalName());
				}
			}
		});
	}
}
