package com.blackmamba.deathkiss.gui.prod;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.blackmamba.deathkiss.entity.CommonArea;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class ListCommonArea extends JFrame {

	// Definition of differents fields
	private JPanel contentPane;
	private String requestType;
	private String table;
	private String jsonString;
	private CommonArea commonArea;
	private List<CommonArea> listCommonArea = new ArrayList();
	private static Logger logger = Logger.getLogger("logger");

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
		JButton backButton = new JButton("Ajouter un employé");
		backButton.setBounds(147, 0, 214, 23);
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

		// Creation of a insert button
		// And display on the contentPane
		JButton insertButton = new JButton("Ajouter une partie commune");
		insertButton.setBounds(147, 30, 214, 23);
		contentPane.add(insertButton);

		insertButton.addActionListener(new ActionListener() {
			/**
			 * If they click in backButton c'est will redirect to InsertionClient
			 */
			public void actionPerformed(ActionEvent e) {
				try {
					InsertionCommonArea frame = new InsertionCommonArea();
					frame.setVisible(true);
					setVisible(false);
					logger.log(Level.INFO, "Go to Insertion CommonArea");
					dispose();
				} catch (Exception e1) {
					logger.log(Level.INFO,
							"Impossible redirect to window 'InsertionCommonArea' " + e1.getClass().getCanonicalName());
				}
			}
		});

		requestType = "READ ALL";
		commonArea = new CommonArea();
		table = "CommonArea";
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			jsonString = "READ ALL";
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			CommonArea[] commonAreas = objectMapper.readValue(jsonString, CommonArea[].class);
			listCommonArea = Arrays.asList(commonAreas);
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
		}
		int x = 60;
		for (CommonArea commonAreas : listCommonArea) {
			x = x + 30;
			// Creation of label idCommonArea
			// And display on the contentPane
			JLabel idCommonArea = new JLabel("Id : " + commonAreas.getIdCommonArea());
			idCommonArea.setBounds(10, x, 100, 14);
			contentPane.add(idCommonArea);

			// Creation of label name
			// And display on the contentPane
			JLabel name = new JLabel("Nom : " + commonAreas.getNameCommonArea());
			name.setBounds(100, x, 200, 14);
			contentPane.add(name);

			// Creation of label stage
			// And display on the contentPane
			JLabel stage = new JLabel("\u00E9tage : " + commonAreas.getEtageCommonArea());
			stage.setBounds(240, x, 200, 14);
			contentPane.add(stage);

			// Creation of a access button
			// And display on the contentPane
			JButton accessButton = new JButton("Voir");
			accessButton.setBounds(400, x, 100, 23);
			contentPane.add(accessButton);

			accessButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						ProfilCommonArea frame = new ProfilCommonArea(commonAreas.getIdCommonArea());
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
