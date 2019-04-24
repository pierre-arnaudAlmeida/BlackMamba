package com.blackmamba.deathkiss.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.blackmamba.deathkiss.pool.entity.Resident;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class TabResident extends JPanel {

	private static final long serialVersionUID = 1L;
	private int idemployee;
	private JPanel bar;
	private JPanel search;
	private JLabel labelIdEmployee;
	private JLabel labelLastnameResident;
	private JLabel labelNameResident;
	private JLabel labelIdResident;
	private JLabel labelSearch;
	private JTextField searchBar;
	private JTextField textInputLastnameResident;
	private JTextField textInputNameResident;
	private JTextField textInputIdResident;
	private Font policeBar;
	private Font policeLabel;
	private JButton disconnection;
	private JButton addResident;
	private JButton save;
	private JButton delete;
	private JButton restaure;
	private JButton validButton;
	private Resident resident;
	private Resident resident2;
	private String requestType;
	private String table;
	private String jsonString;
	private JScrollPane sc;
	private ObjectMapper objectMapper;
	private JList<String> list;
	private DefaultListModel<String> listM;
	private List<Resident> listResident = new ArrayList<Resident>();
	private List<Resident> listSearchResident = new ArrayList<Resident>();
	private static final Logger logger = LogManager.getLogger(TabProfile.class);

	public TabResident() {
	}

	public TabResident(Color color, int idemployee, String title) {
		this.idemployee = idemployee;

		/**
		 * Definition of the structure of this tab
		 */
		bar = new JPanel();
		bar.setBackground(Color.DARK_GRAY);
		bar.setPreferredSize(new Dimension((int) getToolkit().getScreenSize().getWidth(), 80));
		bar.setLayout(new BorderLayout());
		bar.setBorder(BorderFactory.createMatteBorder(20, 100, 20, 100, bar.getBackground()));

		/**
		 * Definition of label Identifiant on header bar
		 */
		labelIdEmployee = new JLabel("Identifiant :   " + this.idemployee + "    ");
		policeBar = new Font("Arial", Font.BOLD, 16);
		labelIdEmployee.setForeground(Color.WHITE);
		labelIdEmployee.setFont(policeBar);
		bar.add(labelIdEmployee, BorderLayout.WEST);

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
		 * Definition of the panel Search
		 */
		search = new JPanel();
		search.setBackground(Color.DARK_GRAY);
		search.setBorder(BorderFactory.createMatteBorder(0, 25, 0, 25, bar.getBackground()));
		bar.add(search);

		/**
		 * Definition of the label search and add on panel search
		 */
		labelSearch = new JLabel();
		labelSearch.setText("Recherche : ");
		labelSearch.setFont(policeBar);
		labelSearch.setForeground(Color.WHITE);
		search.add(labelSearch);

		/**
		 * Definition of the textField seachBar and add panel search
		 */
		searchBar = new JTextField();
		searchBar.setPreferredSize(new Dimension(350, 30));
		search.add(searchBar);

		/**
		 * Definition of the ValidButton
		 */
		validButton = new JButton();
		validButton.setText("Valider");
		search.add(validButton);
		validButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resident = new Resident();
				String searchReceived = searchBar.getText().trim();
				if (!searchReceived.equals("")) {
					/**
					 * If the research is just numerics they find first the IdResident
					 */
					if (searchReceived.matches("[0-9]+[0-9]*")) {
						requestType = "READ";
						resident2 = new Resident();
						table = "Resident";
						resident2.setIdResident(Integer.parseInt(searchReceived));
						try {
							jsonString = objectMapper.writeValueAsString(resident2);
							new ClientSocket(requestType, jsonString, table);
							jsonString = ClientSocket.getJson();
							resident2 = objectMapper.readValue(jsonString, Resident.class);
							logger.log(Level.INFO, "Find Resident data succed");
						} catch (Exception e1) {
							logger.log(Level.INFO,
									"Impossible to parse in JSON Resident datas" + e1.getClass().getCanonicalName());
						}
						listM.removeAllElements();
						if (!resident2.getLastnameResident().equals("")) {
							listM.addElement("Résultat pour un resident avec l'id : " + searchReceived);
							listM.addElement(resident2.getIdResident() + "# " + resident2.getLastnameResident() + " "
									+ resident2.getNameResident());
						}

					} else {
						/**
						 * If the research is letter and numerics
						 */
						searchReceived = Normalizer.normalize(searchReceived, Normalizer.Form.NFD)
								.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
						resident2 = new Resident();
						resident2.setLastnameResident(searchReceived);
						requestType = "FIND ALL";
						table = "Resident";
						objectMapper = new ObjectMapper();
						try {
							jsonString = objectMapper.writeValueAsString(resident2);
							new ClientSocket(requestType, jsonString, table);
							jsonString = ClientSocket.getJson();
							Resident[] residents = objectMapper.readValue(jsonString, Resident[].class);
							listSearchResident = Arrays.asList(residents);
							logger.log(Level.INFO, "Find Resident data succed");
						} catch (Exception e1) {
							logger.log(Level.INFO,
									"Impossible to parse in JSON Resident datas" + e1.getClass().getCanonicalName());
						}
						listM.removeAllElements();
						if (listSearchResident.size() > 0)
							listM.addElement("Résultat pour un resident avec : " + searchReceived);
						for (Resident residents : listSearchResident) {
							listM.addElement(residents.getIdResident() + "# " + residents.getLastnameResident() + " "
									+ residents.getNameResident());
						}

						resident2 = new Resident();
						resident2.setNameResident(searchReceived);
						requestType = "FIND ALL";
						table = "Resident";
						objectMapper = new ObjectMapper();
						try {
							jsonString = objectMapper.writeValueAsString(resident2);
							new ClientSocket(requestType, jsonString, table);
							jsonString = ClientSocket.getJson();
							Resident[] residents = objectMapper.readValue(jsonString, Resident[].class);
							listSearchResident = Arrays.asList(residents);
							logger.log(Level.INFO, "Find Resident data succed");
						} catch (Exception e1) {
							logger.log(Level.INFO,
									"Impossible to parse in JSON Resident datas" + e1.getClass().getCanonicalName());
						}
						listM.removeAllElements();
						if (listSearchResident.size() > 0)
							listM.addElement("Résultat pour un resident avec : " + searchReceived);
						for (Resident residents : listSearchResident) {
							listM.addElement(residents.getIdResident() + "# " + residents.getLastnameResident() + " "
									+ residents.getNameResident());
						}

					}

				} else {
					/**
					 * If the research is empty they display all the Resident
					 */
					requestType = "READ ALL";
					table = "Resident";
					objectMapper = new ObjectMapper();
					try {
						jsonString = "READ ALL";
						new ClientSocket(requestType, jsonString, table);
						jsonString = ClientSocket.getJson();
						Resident[] residents = objectMapper.readValue(jsonString, Resident[].class);
						listResident = Arrays.asList(residents);
						logger.log(Level.INFO, "Find Resident data succed");
					} catch (Exception e1) {
						logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
					}
					listM.removeAllElements();
					if (listResident.size() > 0)
						listM.addElement("Toutes les residents");
					for (Resident residents : listResident) {
						listM.addElement(residents.getIdResident() + "# " + residents.getLastnameResident() + " "
								+ residents.getNameResident());
					}
				}
				searchBar.setText("");
			}
		});

		///////////////////////// LIST RESIDENT ////////////////////////////////////

		resident = new Resident();
		resident.setIdResident(0);
		resident.setLastnameResident("");
		resident.setNameResident("");

		/**
		 * Find all the Resident in the data base and add on list to be displayed
		 */
		requestType = "READ ALL";
		table = "Resident";
		objectMapper = new ObjectMapper();
		try {
			jsonString = "READ ALL";
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			Resident[] residents = objectMapper.readValue(jsonString, Resident[].class);
			listResident = Arrays.asList(residents);
			logger.log(Level.INFO, "Find Resident data succed");
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON Resident datas" + e1.getClass().getCanonicalName());
		}

		listM = new DefaultListModel<String>();
		for (Resident residents : listResident) {
			listM.addElement(residents.getIdResident() + "# " + residents.getLastnameResident() + " "
					+ residents.getNameResident());
		}

		list = new JList<String>(listM);
		sc = new JScrollPane(list);
		sc.setBounds(30, 120, 300, ((int) getToolkit().getScreenSize().getHeight() - 300));
		this.add(sc);

		/**
		 * when we pressed a line in the list they will send a request to get all the
		 * information about the Resident to be displayed on the textField
		 */
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// TODO
			}
		};
		list.addMouseListener(mouseListener);

		///////////////////////// LABEL///////////////////////////////////////////////
		/**
		 * Definition of label LastnameResident
		 */
		policeLabel = new Font("Arial", Font.BOLD, (int) getToolkit().getScreenSize().getWidth() / 80);

		labelLastnameResident = new JLabel("Nom : ");
		labelLastnameResident.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 2 / 10, 200, 30);
		labelLastnameResident.setFont(policeLabel);
		this.add(labelLastnameResident);

		/**
		 * Definition of label NameResident
		 */
		labelNameResident = new JLabel("Prenom : ");
		labelNameResident.setBounds((int) getToolkit().getScreenSize().getWidth() * 4 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 2 / 10, 200, 30);
		labelNameResident.setFont(policeLabel);
		this.add(labelNameResident);

		/**
		 * Definition of label IdResident
		 */

		labelIdResident = new JLabel("Id : ");
		labelIdResident.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 4 / 10, 100, 30);
		labelIdResident.setFont(policeLabel);
		this.add(labelIdResident);

		//////////////////// TEXT AREA////////////////////////////////////////////////

		/**
		 * Definition of textArea LastnameResident
		 */
		textInputLastnameResident = new JTextField();
		textInputLastnameResident.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 5 / 20, 300, 40);
		textInputLastnameResident.setFont(policeLabel);
		textInputLastnameResident.setText(resident.getLastnameResident());
		this.add(textInputLastnameResident);

		/**
		 * Definition of textArea NameResident
		 */
		textInputNameResident = new JTextField();
		textInputNameResident.setBounds((int) getToolkit().getScreenSize().getWidth() * 4 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 5 / 20, 300, 40);
		textInputNameResident.setFont(policeLabel);
		textInputNameResident.setText(resident.getNameResident());
		this.add(textInputNameResident);

		/**
		 * Definition of textArea IdResident
		 */
		textInputIdResident = new JTextField();
		textInputIdResident.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 9 / 20, 300, 40);
		textInputIdResident.setFont(policeLabel);
		textInputIdResident.setEditable(false);
		if (resident.getIdResident() == 0)
			textInputIdResident.setText("");
		else
			textInputIdResident.setText(Integer.toString(resident.getIdResident()));
		this.add(textInputIdResident);

		//////////////////// BUTTON////////////////////////////////////////////////
		/**
		 * Definition of Button AddResident
		 */
		addResident = new JButton("Ajouter");
		addResident.setBounds(30, (int) getToolkit().getScreenSize().getHeight() - 150, 300, 40);
		this.add(addResident);
		addResident.addActionListener(new ActionListener() {
			/**
			 * When we pressed the button addResident we suppress the space and we get out
			 * the special characters and verify if the textField are empty or not If one of
			 * them is empty they send a message to user else they send the request to
			 * server
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
			}
			// TODO
		});

		/**
		 * Definition of Button Save
		 */
		save = new JButton("Sauvegarder");
		save.setBounds(((int) getToolkit().getScreenSize().getWidth() * 5 / 7),
				(int) getToolkit().getScreenSize().getHeight() * 15 / 20, 200, 40);
		this.add(save);
		save.addActionListener(new ActionListener() {
			/**
			 * When we pressed the button save we update the Resident datas we check if the
			 * informations are corect
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});

		/**
		 * Definition of Button Delete
		 */
		delete = new JButton("Supprimer");
		delete.setBounds(((int) getToolkit().getScreenSize().getWidth() * 3 / 7),
				(int) getToolkit().getScreenSize().getHeight() * 15 / 20, 150, 40);
		this.add(delete);
		delete.addActionListener(new ActionListener() {
			/**
			 * When we pressed the button delete we get the id of the Resident and we send
			 * it to server, to be deleted by him
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});

		/**
		 * Definition of Button Restore
		 */
		restaure = new JButton("Annuler");
		restaure.setBounds(((int)

		getToolkit().getScreenSize().getWidth() * 4 / 7), (int) getToolkit().getScreenSize().getHeight() * 15 / 20, 150,
				40);
		this.add(restaure);
		restaure.addActionListener(new ActionListener() {
			/**
			 * Set the textField at the last Resident selected by the user and if they will
			 * be deleted, they put nothing
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});

		/**
		 * Different parameters of the window
		 */
		this.setLayout(new BorderLayout());
		this.add(bar, BorderLayout.NORTH);
		this.setBackground(color);
	}
}
