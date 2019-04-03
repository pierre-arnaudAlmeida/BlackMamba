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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.blackmamba.deathkiss.entity.CommonArea;
import com.blackmamba.deathkiss.entity.Sensor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class TabCommonArea extends JPanel {

	private static final long serialVersionUID = 1L;
	private int idemployee;
	private int index;
	private String requestType;
	private String table;
	private String jsonString;
	private JPanel bar;
	private JPanel search;
	private JLabel labelIdEmployee;
	private JLabel labelNameCommonArea;
	private JLabel labelStageCommonArea;
	private JLabel labelIdCommonArea;
	private JLabel labelSearch;
	private JTextField textInputNameCommonArea;
	private JTextField textInputStageCommonArea;
	private JTextField textInputIdCommonArea;
	private JTextField searchBar;
	private Font policeBar;
	private Font policeLabel;
	private JButton disconnection;
	private JButton addCommonArea;
	private JButton save;
	private JButton delete;
	private JButton restaure;
	private JButton listSensor;
	private JButton validButton;
	private CommonArea commonArea;
	private CommonArea commonArea2;
	private Sensor sensor;
	private JScrollPane sc;
	private JTabbedPane tab;
	private TabListSensor tabListSensor;
	private ObjectMapper objectMapper;
	private List<CommonArea> listCommonArea = new ArrayList<CommonArea>();
	private List<CommonArea> listSearchCommonArea = new ArrayList<CommonArea>();
	private List<Sensor> listSensorUsed = new ArrayList<Sensor>();
	private static final Logger logger = LogManager.getLogger(TabProfile.class);
	private JList<String> list;
	private DefaultListModel<String> listM;

	public TabCommonArea() {
	}

	public TabCommonArea(Color color, int idemployee, String title) {
		this.idemployee = idemployee;

		/**
		 * Definition of the structure of this tab
		 */
		bar = new JPanel();
		bar.setBackground(Color.DARK_GRAY);
		bar.setPreferredSize(new Dimension((int) getToolkit().getScreenSize().getWidth(), 80));
		bar.setLayout(new BorderLayout());
		bar.setBorder(BorderFactory.createMatteBorder(20, 100, 20, 100, bar.getBackground()));

		///////////////////////// BAR/////////////////////////////////////////////////
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
			/**
			 * Close the application
			 */
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
			/**
			 * Verify the content of the search if they match with just numerics they will
			 * send a request to search the CommonArea with the id written in the research
			 * or the stage of the CommonArea. And add all the results on a list to display
			 * But if there is letter and numerics they will send a request to return all
			 * the CommonArea when the name contains the research
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				commonArea = new CommonArea();
				String searchReceived = searchBar.getText().trim();
				if (!searchReceived.equals("")) {
					/**
					 * If the research is just numerics they find first the IdCommonArea
					 */
					if (searchReceived.matches("[0-9]+[0-9]*")) {
						requestType = "READ";
						commonArea2 = new CommonArea();
						table = "CommonArea";
						commonArea2.setIdCommonArea(Integer.parseInt(searchReceived));
						try {
							jsonString = objectMapper.writeValueAsString(commonArea2);
							new ClientSocket(requestType, jsonString, table);
							jsonString = ClientSocket.getJson();
							commonArea2 = objectMapper.readValue(jsonString, CommonArea.class);
							logger.log(Level.INFO, "Find CommonArea data succed");
						} catch (Exception e1) {
							logger.log(Level.INFO,
									"Impossible to parse in JSON CommonArea datas" + e1.getClass().getCanonicalName());
						}
						listM.removeAllElements();
						if (!commonArea2.getNameCommonArea().equals("")) {
							listM.addElement("Résultat pour une partie communne avec l'id : " + searchReceived);
							listM.addElement(commonArea2.getIdCommonArea() + "# " + commonArea2.getNameCommonArea()
									+ " " + commonArea2.getEtageCommonArea());
						}
						/**
						 * Find CommonArea with the stage
						 */
						commonArea2 = new CommonArea();
						commonArea2.setEtageCommonArea(Integer.parseInt(searchReceived));
						requestType = "FIND ALL";
						table = "CommonArea";
						objectMapper = new ObjectMapper();
						try {
							jsonString = objectMapper.writeValueAsString(commonArea2);
							new ClientSocket(requestType, jsonString, table);
							jsonString = ClientSocket.getJson();
							CommonArea[] commonAreas = objectMapper.readValue(jsonString, CommonArea[].class);
							listSearchCommonArea = Arrays.asList(commonAreas);
							logger.log(Level.INFO, "Find CommonArea data succed");
						} catch (Exception e1) {
							logger.log(Level.INFO,
									"Impossible to parse in JSON CommonArea datas" + e1.getClass().getCanonicalName());
						}
						if (listSearchCommonArea.size() > 0)
							listM.addElement("Résultat pour les parties communes à l'étage : " + searchReceived);
						for (CommonArea commonAreas : listSearchCommonArea) {
							listM.addElement(commonAreas.getIdCommonArea() + "# " + commonAreas.getNameCommonArea()
									+ " " + commonAreas.getEtageCommonArea());
						}
					} else {
						/**
						 * If the research is letter and numerics
						 */
						searchReceived = Normalizer.normalize(searchReceived, Normalizer.Form.NFD)
								.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
						commonArea2 = new CommonArea();
						commonArea2.setNameCommonArea(searchReceived);
						requestType = "FIND ALL";
						table = "CommonArea";
						objectMapper = new ObjectMapper();
						try {
							jsonString = objectMapper.writeValueAsString(commonArea2);
							new ClientSocket(requestType, jsonString, table);
							jsonString = ClientSocket.getJson();
							CommonArea[] commonAreas = objectMapper.readValue(jsonString, CommonArea[].class);
							listSearchCommonArea = Arrays.asList(commonAreas);
							logger.log(Level.INFO, "Find CommonArea data succed");
						} catch (Exception e1) {
							logger.log(Level.INFO,
									"Impossible to parse in JSON CommonArea datas" + e1.getClass().getCanonicalName());
						}
						listM.removeAllElements();
						if (listSearchCommonArea.size() > 0)
							listM.addElement("Résultat pour une partie commune avec : " + searchReceived);
						for (CommonArea commonAreas : listSearchCommonArea) {
							listM.addElement(commonAreas.getIdCommonArea() + "# " + commonAreas.getNameCommonArea()
									+ " " + commonAreas.getEtageCommonArea());
						}
					}
				} else {
					/**
					 * If the research is empty they display all the CommonAreas
					 */
					requestType = "READ ALL";
					table = "CommonArea";
					objectMapper = new ObjectMapper();
					try {
						jsonString = "READ ALL";
						new ClientSocket(requestType, jsonString, table);
						jsonString = ClientSocket.getJson();
						CommonArea[] commonAreas = objectMapper.readValue(jsonString, CommonArea[].class);
						listCommonArea = Arrays.asList(commonAreas);
						logger.log(Level.INFO, "Find CommonArea data succed");
					} catch (Exception e1) {
						logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
					}
					listM.removeAllElements();
					if (listCommonArea.size() > 0)
						listM.addElement("Toutes les parties communes");
					for (CommonArea commonAreas : listCommonArea) {
						listM.addElement(commonAreas.getIdCommonArea() + "# " + commonAreas.getNameCommonArea() + " "
								+ commonAreas.getEtageCommonArea());
					}
				}
				searchBar.setText("");
			}
		});

		///////////////////////// LIST COMMONAREA////////////////////////////////////
		// TODO faire une synchro
		commonArea = new CommonArea();
		commonArea.setIdCommonArea(0);
		commonArea.setNameCommonArea("");
		commonArea.setEtageCommonArea(99);

		/**
		 * Find all the CommonArea in the data base and add on list to be displayed
		 */
		requestType = "READ ALL";
		table = "CommonArea";
		objectMapper = new ObjectMapper();
		try {
			jsonString = "READ ALL";
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			CommonArea[] commonAreas = objectMapper.readValue(jsonString, CommonArea[].class);
			listCommonArea = Arrays.asList(commonAreas);
			logger.log(Level.INFO, "Find CommonArea data succed");
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON CommonArea datas" + e1.getClass().getCanonicalName());
		}

		listM = new DefaultListModel<String>();
		for (CommonArea commonAreas : listCommonArea) {
			listM.addElement(commonAreas.getIdCommonArea() + "# " + commonAreas.getNameCommonArea() + " "
					+ commonAreas.getEtageCommonArea());
		}

		list = new JList<String>(listM);
		sc = new JScrollPane(list);
		sc.setBounds(30, 120, 300, ((int) getToolkit().getScreenSize().getHeight() - 300));
		this.add(sc);

		/**
		 * when we pressed a line in the list they will send a request to get all the
		 * information about the CommonArea to be displayed on the textField
		 */
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				index = list.locationToIndex(e.getPoint());
				String substring = listM.getElementAt(index).toString();
				int position = substring.indexOf("#");
				String id = substring.substring(0, position);

				/**
				 * Find the CommonArea by the id get on list
				 */
				requestType = "READ";
				commonArea = new CommonArea();
				table = "CommonArea";
				ObjectMapper readMapper = new ObjectMapper();
				commonArea.setIdCommonArea(Integer.parseInt(id));
				try {
					jsonString = readMapper.writeValueAsString(commonArea);
					new ClientSocket(requestType, jsonString, table);
					jsonString = ClientSocket.getJson();
					commonArea = readMapper.readValue(jsonString, CommonArea.class);
					logger.log(Level.INFO, "Find CommonArea data succed");
				} catch (Exception e1) {
					logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
				}
				textInputIdCommonArea.setText(Integer.toString(commonArea.getIdCommonArea()));
				textInputNameCommonArea.setText(commonArea.getNameCommonArea());
				textInputStageCommonArea.setText(Integer.toString(commonArea.getEtageCommonArea()));
			}
		};
		list.addMouseListener(mouseListener);

		///////////////////////// LABEL///////////////////////////////////////////////
		/**
		 * Definition of label IdCommonArea
		 */
		policeLabel = new Font("Arial", Font.BOLD, (int) getToolkit().getScreenSize().getWidth() / 80);
		labelIdCommonArea = new JLabel("Id : ");
		labelIdCommonArea.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 2 / 10, 100, 30);
		labelIdCommonArea.setFont(policeLabel);
		this.add(labelIdCommonArea);

		/**
		 * Definition of label NameCommonArea
		 */
		labelNameCommonArea = new JLabel("Nom : ");
		labelNameCommonArea.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 4 / 10, 200, 30);
		labelNameCommonArea.setFont(policeLabel);
		this.add(labelNameCommonArea);

		/**
		 * Definition of label StageCommonArea
		 */
		labelStageCommonArea = new JLabel("Etage : ");
		labelStageCommonArea.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 6 / 10, 200, 30);
		labelStageCommonArea.setFont(policeLabel);
		this.add(labelStageCommonArea);

		//////////////////// TEXT AREA////////////////////////////////////////////////
		/**
		 * Definition of textArea IdCommonArea
		 */
		textInputIdCommonArea = new JTextField();
		textInputIdCommonArea.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 5 / 20, 300, 40);
		textInputIdCommonArea.setFont(policeLabel);
		textInputIdCommonArea.setEditable(false);
		if (commonArea.getIdCommonArea() == 0)
			textInputIdCommonArea.setText("");
		else
			textInputIdCommonArea.setText(Integer.toString(commonArea.getIdCommonArea()));
		this.add(textInputIdCommonArea);

		/**
		 * Definition of textArea NameCommonArea
		 */
		textInputNameCommonArea = new JTextField();
		textInputNameCommonArea.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 9 / 20, 300, 40);
		textInputNameCommonArea.setFont(policeLabel);
		textInputNameCommonArea.setText(commonArea.getNameCommonArea());
		this.add(textInputNameCommonArea);

		/**
		 * Definition of textArea StageCommonArea
		 */
		textInputStageCommonArea = new JTextField();
		textInputStageCommonArea.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 13 / 20, 300, 40);
		textInputStageCommonArea.setFont(policeLabel);
		if (commonArea.getEtageCommonArea() == 99)
			textInputStageCommonArea.setText("");
		else
			textInputStageCommonArea.setText(Integer.toString(commonArea.getEtageCommonArea()));
		this.add(textInputStageCommonArea);

		//////////////////// BUTTON////////////////////////////////////////////////
		/**
		 * Definition of Button AddCommonArea
		 */
		addCommonArea = new JButton("Ajouter");
		addCommonArea.setBounds(30, (int) getToolkit().getScreenSize().getHeight() - 150, 300, 40);
		this.add(addCommonArea);
		addCommonArea.addActionListener(new ActionListener() {
			/**
			 * When we pressed the button addCommonArea we supress the space and we get out
			 * the special caraters and verify if the textField are empty or not If one of
			 * them is empty they send a message to user else they send the request to
			 * server
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				requestType = "CREATE";
				table = "CommonArea";

				String newNameCommonArea = textInputNameCommonArea.getText().trim();
				newNameCommonArea = Normalizer.normalize(newNameCommonArea, Normalizer.Form.NFD)
						.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
				String newStageCommonArea = textInputStageCommonArea.getText().trim();

				if (newNameCommonArea.equals("") || newStageCommonArea.equals("")) {
					JOptionPane.showMessageDialog(null, "Vous n'avez pas remplis au moins l'un des deux champs requis",
							"Erreur", JOptionPane.ERROR_MESSAGE);
					logger.log(Level.INFO, "Attempt of insertion without characters");
				} else if (!(newStageCommonArea.matches("[0-9]+[0-9]*"))) {
					JOptionPane.showMessageDialog(null, "L'etage ne contient pas de lettre uniquement un chiffre",
							"Erreur", JOptionPane.ERROR_MESSAGE);
				} else {
					commonArea.setNameCommonArea(newNameCommonArea.toUpperCase());
					commonArea.setEtageCommonArea(Integer.parseInt(newStageCommonArea));
					ObjectMapper insertMapper = new ObjectMapper();
					try {
						jsonString = insertMapper.writeValueAsString(commonArea);
						new ClientSocket(requestType, jsonString, table);
						jsonString = ClientSocket.getJson();
						if (!jsonString.equals("INSERTED")) {
							JOptionPane.showMessageDialog(null, "L'insertion a échoué", "Erreur",
									JOptionPane.ERROR_MESSAGE);
							logger.log(Level.INFO, "Impossible to insert commonArea");
						} else {
							/**
							 * Get the information about the CommoNArea came to be inserted in data base and
							 * at the information to list
							 */
							logger.log(Level.INFO, "Insertion Succeded");
							requestType = "READ ALL";
							table = "CommonArea";
							jsonString = "READ ALL";
							new ClientSocket(requestType, jsonString, table);
							jsonString = ClientSocket.getJson();
							CommonArea[] commonAreas = objectMapper.readValue(jsonString, CommonArea[].class);
							listCommonArea = Arrays.asList(commonAreas);
							logger.log(Level.INFO, "Find CommonArea data succed");

							int x = listCommonArea.size() - 1;
							commonArea = listCommonArea.get(x);
							listM.addElement(commonArea.getIdCommonArea() + "# " + commonArea.getNameCommonArea() + " "
									+ commonArea.getEtageCommonArea());
							JOptionPane.showMessageDialog(null, "L'insertion a été éffectué", "Infos",
									JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (Exception e1) {
						logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
					}
				}
			}
		});

		/**
		 * Definition of Button Save
		 */
		save = new JButton("Sauvegarder");
		save.setBounds(((int) getToolkit().getScreenSize().getWidth() * 5 / 7),
				(int) getToolkit().getScreenSize().getHeight() * 15 / 20, 150, 40);
		this.add(save);
		save.addActionListener(new ActionListener() {
			/**
			 * When we pressed the button save we update the commonArea datas we check if
			 * the informations are corect, if the stage is only numerics and if the name of
			 * the commonArea is not empty
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				requestType = "UPDATE";
				table = "CommonArea";

				commonArea.setIdCommonArea(Integer.parseInt(textInputIdCommonArea.getText()));
				String newNameCommonArea = textInputNameCommonArea.getText().trim();
				newNameCommonArea = Normalizer.normalize(newNameCommonArea, Normalizer.Form.NFD)
						.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
				String newStageCommonArea = textInputStageCommonArea.getText().trim();

				if (newNameCommonArea.equals("") || newStageCommonArea.equals("")) {
					JOptionPane.showMessageDialog(null, "Champs vide", "Erreur", JOptionPane.ERROR_MESSAGE);
				} else if (!(newStageCommonArea.matches("[0-9]+[0-9]*"))) {
					JOptionPane.showMessageDialog(null, "L'etage ne contient pas de lettre uniquement un chiffre",
							"Erreur", JOptionPane.ERROR_MESSAGE);
				} else {
					commonArea.setNameCommonArea(newNameCommonArea.toUpperCase());
					commonArea.setEtageCommonArea(Integer.parseInt(newStageCommonArea));
					ObjectMapper insertMapper = new ObjectMapper();
					try {
						jsonString = insertMapper.writeValueAsString(commonArea);
						new ClientSocket(requestType, jsonString, table);
						jsonString = ClientSocket.getJson();
						if (!jsonString.equals("UPDATED")) {
							JOptionPane.showMessageDialog(null, "La Mise a jour a échoué", "Erreur",
									JOptionPane.ERROR_MESSAGE);
							logger.log(Level.INFO, "Impossible to update commonArea");
						} else {
							logger.log(Level.INFO, "Update Succeded");
							listM.set(index, commonArea.getIdCommonArea() + "# " + commonArea.getNameCommonArea() + " "
									+ commonArea.getEtageCommonArea());
							JOptionPane.showMessageDialog(null, "Données Mises à jours", "Infos",
									JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (Exception e1) {
						logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
					}
				}
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
			 * When we pressed the button delete we get the id of the CommonArea and we send
			 * it to server, to be deleted by him
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!textInputIdCommonArea.getText().toString().equals("")) {
					/**
					 * we get all the Sensor wo have the commonArea id and we set to null the
					 * idCommonArea in Sensor table and them we can delete the CommonArea
					 */
					requestType = "FIND ALL";
					table = "Sensor";
					objectMapper = new ObjectMapper();
					sensor = new Sensor();
					try {
						sensor.setIdCommonArea(commonArea.getIdCommonArea());
						jsonString = objectMapper.writeValueAsString(sensor);
						new ClientSocket(requestType, jsonString, table);
						jsonString = ClientSocket.getJson();
						Sensor[] sensors = objectMapper.readValue(jsonString, Sensor[].class);
						listSensorUsed = Arrays.asList(sensors);
						logger.log(Level.INFO, "Find Sensor data succed");
					} catch (Exception e1) {
						logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
					}
					if (!listSensorUsed.isEmpty()) {
						for (Sensor sens : listSensorUsed) {
							requestType = "UPDATE";
							table = "Sensor";
							sens.setIdCommonArea(0);
							ObjectMapper insertMapper = new ObjectMapper();
							try {
								jsonString = insertMapper.writeValueAsString(sens);
								new ClientSocket(requestType, jsonString, table);
								jsonString = ClientSocket.getJson();
								if (!jsonString.equals("UPDATED")) {
									logger.log(Level.INFO, "Impossible to update sensor");
								} else {
									logger.log(Level.INFO, "Update Succeded");
								}
							} catch (Exception e1) {
								logger.log(Level.INFO,
										"Impossible to parse in JSON sensor datas" + e1.getClass().getCanonicalName());
							}
						}
					}

					requestType = "DELETE";
					table = "CommonArea";
					ObjectMapper connectionMapper = new ObjectMapper();
					try {
						jsonString = connectionMapper.writeValueAsString(commonArea);
						new ClientSocket(requestType, jsonString, table);
						jsonString = ClientSocket.getJson();
						if (!jsonString.equals("DELETED")) {
							JOptionPane.showMessageDialog(null, "La suppression a échoué", "Erreur",
									JOptionPane.ERROR_MESSAGE);
							logger.log(Level.INFO, "Impossible to delete this commonArea");
						} else {
							JOptionPane.showMessageDialog(null, "Suppression de la partie commune", "Infos",
									JOptionPane.INFORMATION_MESSAGE);
							logger.log(Level.INFO, "Deletion of CommonArea succed");
						}
					} catch (Exception e1) {
						logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
					}
					listM.removeElementAt(index);
					commonArea.setIdCommonArea(0);
					commonArea.setEtageCommonArea(0);
					commonArea.setListSensor(null);
					commonArea.setNameCommonArea("");

					textInputIdCommonArea.setText("");
					textInputNameCommonArea.setText("");
					textInputStageCommonArea.setText("");
				} else {
					JOptionPane.showMessageDialog(null, "Veuillez selectionner une partie commune à supprimer",
							"Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		/**
		 * Definition of Button Restaure
		 */
		restaure = new JButton("Annuler");
		restaure.setBounds(((int)

		getToolkit().getScreenSize().getWidth() * 4 / 7), (int) getToolkit().getScreenSize().getHeight() * 15 / 20, 150,
				40);
		this.add(restaure);
		restaure.addActionListener(new ActionListener() {
			/**
			 * Set the textField at the last commonArea selected by the user and if they
			 * will be deleted, they put nothing
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				if (commonArea.getIdCommonArea() == 0) {
					textInputIdCommonArea.setText("");
				} else {
					textInputIdCommonArea.setText(Integer.toString(commonArea.getIdCommonArea()));
				}
				textInputNameCommonArea.setText(commonArea.getNameCommonArea());
				if (commonArea.getEtageCommonArea() == 0) {
					textInputStageCommonArea.setText("");
				} else {
					textInputStageCommonArea.setText(Integer.toString(commonArea.getEtageCommonArea()));
				}
			}
		});

		/**
		 * Definition of Button ListSensor
		 */
		listSensor = new JButton("Liste Capteur");
		listSensor.setBounds(((int) getToolkit().getScreenSize().getWidth() * 2 / 7),
				(int) getToolkit().getScreenSize().getHeight() * 15 / 20, 150, 40);
		this.add(listSensor);
		listSensor.addActionListener(new ActionListener() {
			/**
			 * When we pressed the buttonListSensor they find all the sensor in the
			 * commonArea and display them on a new tab
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				if (commonArea.getIdCommonArea() == 0) {
					JOptionPane.showMessageDialog(null, "Veuillez selectionner une partie commune", "Erreur",
							JOptionPane.ERROR_MESSAGE);
				} else {
					tab = new JTabbedPane();
					tab = Frame.getTab();
					try {
						if (tab.isEnabledAt(6) == false) {
						} else {
							tab.remove(6);
							tabListSensor = new TabListSensor(commonArea, idemployee, "Onglet Liste des Capteurs");
							tab.add("Onglet Liste des Capteurs", tabListSensor);
							Frame.goToTab(6);
						}
					} catch (IndexOutOfBoundsException e1) {
						tabListSensor = new TabListSensor(commonArea, idemployee, "Onglet Liste des Capteurs");
						tab.add("Onglet Liste des Capteurs", tabListSensor);
						Frame.goToTab(6);
					}
				}
			}
		});

		/**
		 * Diferent parameter of the window
		 */
		this.setLayout(new BorderLayout());
		this.add(bar, BorderLayout.NORTH);
		this.setBackground(color);
	}
}