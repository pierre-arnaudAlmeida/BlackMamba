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

import com.blackmamba.deathkiss.entity.CommonArea;

public class ListCommonArea extends JFrame {

	// Definition of differents fields
	private JPanel contentPane;
	private static Logger logger = Logger.getLogger("logger");
	private CommonArea commonArea;

	public ListCommonArea() {

		setTitle("Liste Partie Commune");
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
					InsertionEmployee frame = new InsertionEmployee();
					frame.setVisible(true);
					setVisible(false);
					logger.log(Level.INFO, "Back to Insertion Employee");
					dispose();
				} catch (Exception e1) {
					logger.log(Level.INFO,
							"Impossible redirect to window 'InsertionClient' " + e1.getClass().getCanonicalName());
				}
			}
		});
		int x = 0;
		
		for (int i = 0; i < 5; i++) {
			x = x+30;
			final int y =x+1;
			commonArea = new CommonArea();
			// Creation of label idCommonArea
			// And display on the contentPane
			JLabel idCommonArea = new JLabel("Id :");
			
			idCommonArea.setBounds(10, x, 100, 14);
			contentPane.add(idCommonArea);
			

			// Creation of label name
			// And display on the contentPane
			JLabel name = new JLabel("Nom :");
			name.setBounds(100, x, 200, 14);
			contentPane.add(name);
			commonArea.setNameCommonArea("nom");

			// Creation of label stage
			// And display on the contentPane
			JLabel stage = new JLabel("\u00E9tage :");
			stage.setBounds(240, x, 200, 14);
			contentPane.add(stage);
			commonArea.setEtageCommonArea(1);

			// Creation of a access button
			// And display on the contentPane
			JButton accessButton = new JButton("Voir");
			JLabel idbutton = new JLabel(""+y+"");
			accessButton.setBounds(400, x, 100, 23);
			contentPane.add(accessButton);

			accessButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						commonArea.setIdCommonArea(y);
						ProfilCommonArea frame = new ProfilCommonArea(commonArea.getIdCommonArea());
						frame.setVisible(true);
						setVisible(false);
						logger.log(Level.INFO, "Go to Profil Common Area");
						dispose();
					} catch (Exception e1) {
						logger.log(Level.INFO,
								"Impossible redirect to window 'CommonArea' " + e1.getClass().getCanonicalName());
					}
				}
			});
		}
	}
}
