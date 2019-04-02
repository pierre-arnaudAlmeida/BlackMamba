package com.blackmamba.deathkiss.gui.dev;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import com.blackmamba.deathkiss.gui.dev.ClientSocket;
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
	private JLabel labelIdEmployee;
	private JLabel idEmployee;
	private JLabel labelNameCommonArea;
	private JLabel labelStageCommonArea;
	private JLabel labelIdCommonArea;
	private JTextField textInputNameCommonArea;
	private JTextField textInputStageCommonArea;
	private JTextField textInputIdCommonArea;
	private Font policeBar;
	private Font policeLabel;
	private JButton disconnection;
	private JButton addCommonArea;
	private JButton save;
	private JButton delete;
	private JButton restaure;
	private JButton listSensor;
	private CommonArea commonArea;
	private Sensor sensor;
	private JScrollPane sc;
	private ObjectMapper objectMapper;
	private List<CommonArea> listCommonArea = new ArrayList<CommonArea>();
	private List<Sensor> listSensorUsed = new ArrayList<Sensor>();
	private static final Logger logger = LogManager.getLogger(TabProfile.class);
	private JList list;
	private DefaultListModel listM;

	public TabCommonArea() {
	}

	public TabCommonArea(Color color, int idemployee, String title) {
		this.idemployee = idemployee;

		/**
		 * Definition of the structure of this tab
		 */
		bar = new JPanel();
		bar.setBackground(Color.DARK_GRAY);
		bar.setPreferredSize(new Dimension((int) getToolkit().getScreenSize().getWidth(), 70));
		bar.setLayout(new BorderLayout());
		bar.setBorder(BorderFactory.createMatteBorder(20, 100, 20, 100, bar.getBackground()));

		///////////////////////// BAR/////////////////////////////////////////////////
		/**
		 * Definition of label Identifiant on header bar
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

		///////////////////////// LIST EMPLOYEE////////////////////////////////////////
		commonArea = new CommonArea();
		commonArea.setIdCommonArea(0);
		commonArea.setNameCommonArea("");
		commonArea.setEtageCommonArea(99);

		requestType = "READ ALL";
		table = "CommonArea";
		objectMapper = new ObjectMapper();
		try {
			jsonString = "READ ALL";
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			CommonArea[] commonAreas = objectMapper.readValue(jsonString, CommonArea[].class);
			listCommonArea = Arrays.asList(commonAreas);
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
		}

		listM = new DefaultListModel();
		for (CommonArea commonAreas : listCommonArea) {
			listM.addElement(commonAreas.getIdCommonArea() + "# " + commonAreas.getNameCommonArea() + " "
					+ commonAreas.getEtageCommonArea());
		}

		list = new JList(listM);
		sc = new JScrollPane(list);

		sc.setBounds(30, 120, 300, ((int) getToolkit().getScreenSize().getHeight() - 300));
		this.add(sc);

		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				index = list.locationToIndex(e.getPoint());
				String substring = listM.getElementAt(index).toString();
				int position = substring.indexOf("#");
				String id = substring.substring(0, position);

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

			@Override
			public void actionPerformed(ActionEvent e) {
				requestType = "CREATE";
				table = "CommonArea";

				String newNameCommonArea = textInputNameCommonArea.getText().trim();
				String newStageCommonArea = textInputStageCommonArea.getText().trim();
				String newIdCommonArea = textInputIdCommonArea.getText().trim();

				if (newNameCommonArea.equals("") || newStageCommonArea.equals("")) {
					JOptionPane.showMessageDialog(null, "Vous n'avez pas remplis au moins l'un des deux champs requis",
							"Erreur", JOptionPane.ERROR_MESSAGE);
					logger.log(Level.INFO, "Attempt of insertion without characters");
				} else if (!(newStageCommonArea.matches("[0-9]+[0-9]*"))) {
					JOptionPane.showMessageDialog(null, "L'etage ne contient pas de lettre uniquement un chiffre",
							"Erreur", JOptionPane.ERROR_MESSAGE);
				} else {
					commonArea.setNameCommonArea(newNameCommonArea);
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
							logger.log(Level.INFO, "Insertion Succeded");
							requestType = "READ ALL";
							table = "CommonArea";
							jsonString = "READ ALL";
							new ClientSocket(requestType, jsonString, table);
							jsonString = ClientSocket.getJson();
							CommonArea[] commonAreas = objectMapper.readValue(jsonString, CommonArea[].class);
							listCommonArea = Arrays.asList(commonAreas);

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

			@Override
			public void actionPerformed(ActionEvent e) {
				requestType = "UPDATE";
				table = "CommonArea";

				commonArea.setIdCommonArea(Integer.parseInt(textInputIdCommonArea.getText()));
				String newNameCommonArea = textInputNameCommonArea.getText().trim();
				String newStageCommonArea = textInputStageCommonArea.getText().trim();

				if (newNameCommonArea.equals("") || newStageCommonArea.equals("")) {
					JOptionPane.showMessageDialog(null, "Champs vide", "Erreur", JOptionPane.ERROR_MESSAGE);
				} else if (!(newStageCommonArea.matches("[0-9]+[0-9]*"))) {
					JOptionPane.showMessageDialog(null, "L'etage ne contient pas de lettre uniquement un chiffre",
							"Erreur", JOptionPane.ERROR_MESSAGE);
				} else {
					commonArea.setNameCommonArea(newNameCommonArea);
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
						}
					} catch (Exception e1) {
						logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
					}
					listM.removeElementAt(index);
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

			@Override
			public void actionPerformed(ActionEvent e) {
				if (commonArea.getIdCommonArea() == 0) {
					textInputIdCommonArea.setText("");
				} else {
					textInputIdCommonArea.setText(Integer.toString(commonArea.getIdCommonArea()));
				}
				textInputNameCommonArea.setText(commonArea.getNameCommonArea());
				textInputStageCommonArea.setText(Integer.toString(commonArea.getEtageCommonArea()));
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

			@Override
			public void actionPerformed(ActionEvent e) {
				if (commonArea.getIdCommonArea() == 0) {
					JOptionPane.showMessageDialog(null, "Veuillez selectionner une partie commune", "Erreur",
							JOptionPane.ERROR_MESSAGE);
				} else {
					JTabbedPane tab = new JTabbedPane();
					tab = Window.getTab();
					try {
						if (tab.isEnabledAt(6) == false) {
						} else {
							tab.remove(6);
							TabListSensor tabListSensor = new TabListSensor(commonArea, idemployee,
									"Onglet Liste des Capteurs");
							tab.add("Onglet Liste des Capteurs", tabListSensor);
							Window.goToTab(6);
						}
					} catch (IndexOutOfBoundsException e1) {
						TabListSensor tabListSensor = new TabListSensor(commonArea, idemployee,
								"Onglet Liste des Capteurs");
						tab.add("Onglet Liste des Capteurs", tabListSensor);
						Window.goToTab(6);
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

		// TODO mettre une barre de recherche et on affiche les résultat dans le Jlist
		// Si il fait une recherche vide soit on met une pop up soit on affiche tout les
		// commonAreas
		// on crée deux list une avec tout les commonArea et une apres la recherche et
		// comme ca on a pas besoin de refaire une demande!!
	}
}
