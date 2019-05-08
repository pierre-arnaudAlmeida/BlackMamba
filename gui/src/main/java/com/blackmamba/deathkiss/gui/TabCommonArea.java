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
import java.util.ResourceBundle;
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
import com.blackmamba.deathkiss.launcher.ClientSocket;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class TabCommonArea extends JPanel {

	/**
	 * Different parameters used
	 */
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
	private JLabel labelHeadList;
	private JLabel labelMaxSensor;
	private JLabel labelAreaCommonArea;
	private JTextField textInputNameCommonArea;
	private JTextField textInputStageCommonArea;
	private JTextField textInputAreaCommonArea;
	private JTextField textInputMaxSensor;
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
	private JScrollPane sc;
	private JTabbedPane tab;
	private CommonArea commonArea;
	private CommonArea commonArea2;
	private Sensor sensor;
	private TabListSensor tabListSensor;
	private ObjectMapper objectMapper;
	private Thread threadCommonArea;
	private JList<String> list;
	private DefaultListModel<String> listM;
	private List<CommonArea> listCommonArea = new ArrayList<CommonArea>();
	private List<CommonArea> listSearchCommonArea = new ArrayList<CommonArea>();
	private List<Sensor> listSensorUsed = new ArrayList<Sensor>();
	private static final Logger logger = LogManager.getLogger(TabCommonArea.class);
	private ResourceBundle rs = ResourceBundle.getBundle("parameters");

	/**
	 * Constructor
	 */
	public TabCommonArea() {
	}

	/**
	 * Constructor
	 * 
	 * @param color
	 * @param idemployee
	 * @param title
	 */
	public TabCommonArea(Color color, int idemployee, int idcommonArea, String title) {
		this.idemployee = idemployee;

		///////////////////////// Thread/////////////////////////////////////////////////
		setThreadCommonArea(new Thread(new Runnable() {
			/**
			 * Loop and update every 30 seconds the list of commonAreas Update first the
			 * commonArea selected by the user if they have selected one and update the list
			 * of commonArea existing in Data base And at end the thread wait 30
			 * seconds/time define in file properties in resources
			 */
			@Override
			public void run() {
				while (true) {
					updateCommonAreaSelected();
					updateListCommonArea();
					try {
						Thread.sleep(Integer.parseInt(rs.getString("time_threadSleep")));
					} catch (InterruptedException e) {
						logger.log(Level.WARN,
								"Impossible to sleep the thread CommonArea " + e.getClass().getCanonicalName());
					}
				}
			}
		}));

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
		 * Definition of label LOGIN on header bar
		 */
		labelIdEmployee = new JLabel("Login :   " + this.idemployee + "    ");
		policeBar = new Font("Arial", Font.BOLD, 16);
		labelIdEmployee.setForeground(Color.WHITE);
		labelIdEmployee.setFont(policeBar);
		bar.add(labelIdEmployee, BorderLayout.WEST);

		/**
		 * Definition of the button and the different action after pressed the button
		 */
		disconnection = new JButton("Disconnect");
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
		labelSearch.setText("Research : ");
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
		validButton.setText("Search");
		search.add(validButton);
		validButton.addActionListener(new ActionListener() {
			/**
			 * Verify the content of the search if they match with just numerics they will
			 * send a request to search the CommonArea with the id written in the research
			 * the idCommonArea or the stage of the CommonArea. And add all the results on a
			 * list to display But if there is letter and numerics they will send a request
			 * to return all the CommonArea when the name contains the research
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				commonArea = new CommonArea();
				String searchReceived = searchBar.getText().trim();
				if (!searchReceived.equals("")) {
					/**
					 * If the research is just numerics they find first the IdCommonArea. And
					 * display this list at left of screen
					 */
					if (searchReceived.matches("[0-9]+[0-9]*")) {
						commonArea2 = new CommonArea();
						commonArea2.setIdCommonArea(Integer.parseInt(searchReceived));
						requestType = "READ";
						table = "CommonArea";
						commonArea2 = getCommonArea(commonArea2, requestType, table);
						listM.removeAllElements();
						if (!commonArea2.getNameCommonArea().equals("")) {
							listM.addElement("Results for common area with id : " + searchReceived);
							listM.addElement(commonArea2.getIdCommonArea() + "# " + commonArea2.getNameCommonArea()
									+ " ," + commonArea2.getEtageCommonArea());
						}
						/**
						 * Find CommonArea with the stage inserted by user on search bar. And display
						 * this list at left of screen
						 */
						commonArea2 = new CommonArea();
						commonArea2.setEtageCommonArea(Integer.parseInt(searchReceived));
						requestType = "FIND ALL";
						table = "CommonArea";
						listSearchCommonArea = getAllCommonArea(commonArea2, requestType, table);
						if (listSearchCommonArea.size() > 0)
							listM.addElement("Results for common area at floor : " + searchReceived);
						for (CommonArea commonAreas : listSearchCommonArea) {
							listM.addElement(commonAreas.getIdCommonArea() + "# " + commonAreas.getNameCommonArea()
									+ " ," + commonAreas.getEtageCommonArea());
						}
					} else {
						/**
						 * If the research contains letter and numerics they get all commonArea who
						 * contains the characters wrote And display it in Scroll list at left of screen
						 */
						searchReceived = Normalizer.normalize(searchReceived, Normalizer.Form.NFD)
								.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
						commonArea2 = new CommonArea();
						commonArea2.setNameCommonArea(searchReceived);
						requestType = "FIND ALL";
						table = "CommonArea";
						listSearchCommonArea = getAllCommonArea(commonArea2, requestType, table);
						listM.removeAllElements();
						if (listSearchCommonArea.size() > 0)
							listM.addElement("Results for common area with : " + searchReceived);
						for (CommonArea commonAreas : listSearchCommonArea) {
							listM.addElement(commonAreas.getIdCommonArea() + "# " + commonAreas.getNameCommonArea()
									+ " ," + commonAreas.getEtageCommonArea());
						}
					}
				} else {
					/**
					 * If the research is empty they display all the CommonAreas at left of screen
					 */
					requestType = "READ ALL";
					table = "CommonArea";
					listCommonArea = getAllCommonArea(null, requestType, table);
					listM.removeAllElements();
					if (listCommonArea.size() > 0)
						listM.addElement("All commons areas");
					for (CommonArea commonAreas : listCommonArea) {
						listM.addElement(commonAreas.getIdCommonArea() + "# " + commonAreas.getNameCommonArea() + " ,"
								+ commonAreas.getEtageCommonArea());
					}
				}
				searchBar.setText("");
			}
		});

		///////////////////////// LIST COMMONAREA////////////////////////////////////
		listM = new DefaultListModel<String>();
		list = new JList<String>(listM);
		sc = new JScrollPane(list);

		updateListCommonArea();
		if (idcommonArea != 0) {
			commonArea = new CommonArea();
			commonArea.setIdCommonArea(idcommonArea);
			getCommonArea(commonArea, "READ", "CommonArea");
		}

		/**
		 * Add a scrollBar on list
		 */
		sc.setBounds(30, 120, 300, ((int) getToolkit().getScreenSize().getHeight() - 300));
		this.add(sc);

		/**
		 * when we pressed a line in the list they will send a request to get all the
		 * information about the CommonArea selected to be displayed on the textField
		 */
		// TODO PA COMMENTAIRE
		index = -9999;
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				index = list.locationToIndex(e.getPoint());
				String substring = listM.getElementAt(index).toString();
				int position = substring.indexOf("#");
				System.out.println(position);
				if (position > -1) {
					String id = substring.substring(0, position);
					/**
					 * Find the CommonArea by the id get on list
					 */
					commonArea = new CommonArea();
					commonArea.setIdCommonArea(Integer.parseInt(id));
					requestType = "READ";
					table = "CommonArea";
					commonArea = getCommonArea(commonArea, requestType, table);
					textInputIdCommonArea.setText(Integer.toString(commonArea.getIdCommonArea()));
					textInputNameCommonArea.setText(commonArea.getNameCommonArea());
					textInputStageCommonArea.setText(Integer.toString(commonArea.getEtageCommonArea()));
				}
			}
		};
		list.addMouseListener(mouseListener);

		///////////////////////// LABEL///////////////////////////////////////////////
		/**
		 * Definition of label IdCommonArea
		 */
		policeLabel = new Font("Arial", Font.BOLD, (int) getToolkit().getScreenSize().getWidth() / 80);
		labelIdCommonArea = new JLabel("ID : ");
		labelIdCommonArea.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 2 / 10, 100, 30);
		labelIdCommonArea.setFont(policeLabel);
		this.add(labelIdCommonArea);

		/**
		 * Definition of label NameCommonArea
		 */
		labelNameCommonArea = new JLabel("Name : ");
		labelNameCommonArea.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 4 / 10, 200, 30);
		labelNameCommonArea.setFont(policeLabel);
		this.add(labelNameCommonArea);

		/**
		 * Definition of label StageCommonArea
		 */
		labelStageCommonArea = new JLabel("Floor : ");
		labelStageCommonArea.setBounds((int) getToolkit().getScreenSize().getWidth() * 2 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 6 / 10, 200, 30);
		labelStageCommonArea.setFont(policeLabel);
		this.add(labelStageCommonArea);

		/**
		 * Definition of label Area CommonArea
		 */
		labelAreaCommonArea = new JLabel("Area : ");
		labelAreaCommonArea.setBounds((int) getToolkit().getScreenSize().getWidth() * 4 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 2 / 10, 200, 30);
		labelAreaCommonArea.setFont(policeLabel);
		this.add(labelAreaCommonArea);

		/**
		 * Definition of label Max Sensor
		 */
		labelMaxSensor = new JLabel("Max Sensors : ");
		labelMaxSensor.setBounds((int) getToolkit().getScreenSize().getWidth() * 4 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 4 / 10, 200, 30);
		labelMaxSensor.setFont(policeLabel);
		this.add(labelMaxSensor);

		/**
		 * Definition of label HeadList
		 */
		labelHeadList = new JLabel("ID /Name /Floor");
		labelHeadList.setBounds(40, 90, 200, 30);
		labelHeadList.setFont(policeBar);
		this.add(labelHeadList);

		// TODO PA donc verifier a chaque fois qu'on ajoute un capteur la taille de la
		// partie
		// commune
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

		/**
		 * Definition of textArea Area CommonArea
		 */
		textInputAreaCommonArea = new JTextField();
		textInputAreaCommonArea.setBounds((int) getToolkit().getScreenSize().getWidth() * 4 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 5 / 20, 300, 40);
		textInputAreaCommonArea.setFont(policeLabel);
		textInputAreaCommonArea.setText(commonArea.getNameCommonArea());
		this.add(textInputAreaCommonArea);

		/**
		 * Definition of textArea Mac Sensors
		 */
		textInputMaxSensor = new JTextField();
		textInputMaxSensor.setBounds((int) getToolkit().getScreenSize().getWidth() * 4 / 7,
				(int) getToolkit().getScreenSize().getHeight() * 9 / 20, 300, 40);
		textInputMaxSensor.setFont(policeLabel);
		textInputMaxSensor.setText(commonArea.getNameCommonArea());
		this.add(textInputMaxSensor);

		//////////////////// BUTTON////////////////////////////////////////////////
		/**
		 * Definition of Button AddCommonArea
		 */
		addCommonArea = new JButton("Add");
		addCommonArea.setBounds(30, (int) getToolkit().getScreenSize().getHeight() * 16 / 20, 300, 40);
		this.add(addCommonArea);
		addCommonArea.addActionListener(new ActionListener() {
			/**
			 * When we pressed the button addCommonArea we supress the space and we get out
			 * the special caracters and verify if the textField are empty or not If one of
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
				String newArea = textInputAreaCommonArea.getText().trim();
				String newMaxSensor = textInputMaxSensor.getText().trim();
				if (newNameCommonArea.equals("") || newStageCommonArea.equals("")) {
					JOptionPane.showMessageDialog(null, "Missing values", "Error", JOptionPane.INFORMATION_MESSAGE);
					logger.log(Level.INFO, "Attempt of insertion without characters");
				} else if (!(newStageCommonArea.matches("[0-9]+[0-9]*")) || !(newArea.matches("[0-9]+[0-9]*"))
						|| !(newMaxSensor.matches("[0-9]+[0-9]*"))) {
					JOptionPane.showMessageDialog(null, "The floor/the area/the max sensor are integer", "Error",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					commonArea.setNameCommonArea(newNameCommonArea.toUpperCase());
					commonArea.setEtageCommonArea(Integer.parseInt(newStageCommonArea));
					commonArea.setArea(Integer.parseInt(newArea));
					commonArea.setMaxSensor(Integer.parseInt(newMaxSensor));
					ObjectMapper insertMapper = new ObjectMapper();
					try {
						jsonString = insertMapper.writeValueAsString(commonArea);
						new ClientSocket(requestType, jsonString, table);
						jsonString = ClientSocket.getJson();
						if (!jsonString.equals("INSERTED")) {
							JOptionPane.showMessageDialog(null, "Insertion failed", "Error", JOptionPane.ERROR_MESSAGE);
							logger.log(Level.WARN, "Impossible to insert commonArea");
						} else {
							/**
							 * Get the information about the CommoNArea came to be inserted in data base and
							 * at the information to list
							 */
							logger.log(Level.DEBUG, "Insertion Succeeded");
							requestType = "READ ALL";
							table = "CommonArea";
							listCommonArea = getAllCommonArea(null, requestType, table);
							int x = listCommonArea.size() - 1;
							commonArea = listCommonArea.get(x);
							listM.addElement(commonArea.getIdCommonArea() + "# " + commonArea.getNameCommonArea() + " ,"
									+ commonArea.getEtageCommonArea());
							JOptionPane.showMessageDialog(null, "Insertion succeeded", "Information",
									JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (Exception e1) {
						logger.log(Level.WARN,
								"Impossible to parse in JSON CommonArea data " + e1.getClass().getCanonicalName());
					}
				}
			}
		});

		/**
		 * Definition of Button Save
		 */
		save = new JButton("Save");
		save.setBounds(((int) getToolkit().getScreenSize().getWidth() * 5 / 7),
				(int) getToolkit().getScreenSize().getHeight() * 15 / 20, 150, 40);
		this.add(save);
		save.addActionListener(new ActionListener() {
			/**
			 * When we pressed the button save we update the commonArea datas we check if
			 * the informations are correct, if the stage is only numerics and if the name
			 * of the commonArea is not empty
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
				String newArea = textInputAreaCommonArea.getText().trim();
				String newMaxSensor = textInputMaxSensor.getText().trim();
				if (newNameCommonArea.equals("") || newStageCommonArea.equals("")) {
					JOptionPane.showMessageDialog(null, "Fields Empty", "Error", JOptionPane.INFORMATION_MESSAGE);
					/**
					 * if text area do not contains numerics they open an pop-up
					 */
				} else if (!(newStageCommonArea.matches("[0-9]+[0-9]*")) || !(newArea.matches("[0-9]+[0-9]*"))
						|| !(newMaxSensor.matches("[0-9]+[0-9]*"))) {
					JOptionPane.showMessageDialog(null, "The floor/the area/the max sensor are integer", "Error",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					commonArea.setNameCommonArea(newNameCommonArea.toUpperCase());
					commonArea.setEtageCommonArea(Integer.parseInt(newStageCommonArea));
					commonArea.setArea(Integer.parseInt(newArea));
					commonArea.setMaxSensor(Integer.parseInt(newMaxSensor));
					ObjectMapper insertMapper = new ObjectMapper();
					try {
						jsonString = insertMapper.writeValueAsString(commonArea);
						new ClientSocket(requestType, jsonString, table);
						jsonString = ClientSocket.getJson();
						if (!jsonString.equals("UPDATED")) {
							JOptionPane.showMessageDialog(null, "Update failed", "Error", JOptionPane.ERROR_MESSAGE);
							logger.log(Level.WARN, "Impossible to update commonArea");
						} else {
							logger.log(Level.DEBUG, "Update Succeeded");
							listM.set(index, commonArea.getIdCommonArea() + "# " + commonArea.getNameCommonArea() + " "
									+ commonArea.getEtageCommonArea());
							JOptionPane.showMessageDialog(null, "Datas updated", "Information",
									JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (Exception e1) {
						logger.log(Level.WARN,
								"Impossible to parse in JSON CommonArea " + e1.getClass().getCanonicalName());
					}
					textInputIdCommonArea.setText("");
					textInputNameCommonArea.setText("");
					textInputStageCommonArea.setText("");
				}
			}
		});

		/**
		 * Definition of Button Delete
		 */
		delete = new JButton("Delete");
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
				if (index != -9999) {
					String substring = listM.getElementAt(index).toString();
					int position = substring.indexOf("#");
					if (position > -1) {
						/**
						 * we get all the Sensor who have the commonArea id and we set to null the
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
							logger.log(Level.DEBUG, "Find Sensor data succeeded");
						} catch (Exception e1) {
							logger.log(Level.WARN,
									"Impossible to parse in JSON Sensor data " + e1.getClass().getCanonicalName());
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
										logger.log(Level.WARN, "Impossible to update sensor");
									} else {
										logger.log(Level.DEBUG, "Update Succeeded");
									}
								} catch (Exception e1) {
									logger.log(Level.WARN, "Impossible to parse in JSON sensor datas "
											+ e1.getClass().getCanonicalName());
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
								JOptionPane.showMessageDialog(null, "Deletion failed", "Error",
										JOptionPane.ERROR_MESSAGE);
								logger.log(Level.WARN, "Impossible to delete this commonArea");
							} else {
								JOptionPane.showMessageDialog(null, "Deletion succeeded", "Information",
										JOptionPane.INFORMATION_MESSAGE);
								logger.log(Level.DEBUG, "Deletion of CommonArea succeeded");
							}
						} catch (Exception e1) {
							logger.log(Level.WARN,
									"Impossible to parse in JSON CommonArea " + e1.getClass().getCanonicalName());
						}
						listM.removeElementAt(index);
						index = -9999;
						commonArea.setIdCommonArea(0);
						commonArea.setEtageCommonArea(0);
						commonArea.setListSensor(null);
						commonArea.setNameCommonArea("");
						commonArea.setArea(0);
						commonArea.setMaxSensor(0);

						textInputIdCommonArea.setText("");
						textInputNameCommonArea.setText("");
						textInputStageCommonArea.setText("");
						textInputAreaCommonArea.setText("");
						textInputMaxSensor.setText("");
					} else {
						JOptionPane.showMessageDialog(null, "Please select an common area to be deleted", "Error",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Please select an common area to be deleted", "Error",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		/**
		 * Definition of Button Restore
		 */
		restaure = new JButton("Restore");
		restaure.setBounds(((int) getToolkit().getScreenSize().getWidth() * 4 / 7),
				(int) getToolkit().getScreenSize().getHeight() * 15 / 20, 150, 40);
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
				textInputAreaCommonArea.setText(Integer.toString(commonArea.getArea()));
				textInputMaxSensor.setText(Integer.toString(commonArea.getMaxSensor()));
			}
		});

		/**
		 * Definition of Button ListSensor
		 */
		listSensor = new JButton("Sensor List");
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
					JOptionPane.showMessageDialog(null, "Please select an common area", "Error",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					tab = new JTabbedPane();
					tab = Frame.getTab();
					try {
						if (tab.isEnabledAt(7)) {
						} else {
							tab.remove(7);
							tabListSensor = new TabListSensor(commonArea, idemployee, "Tab Sensor List");
							tab.add("Tab Sensor List", tabListSensor);
							Frame.goToTab(7);
						}
					} catch (IndexOutOfBoundsException e1) {
						tabListSensor = new TabListSensor(commonArea, idemployee, "Tab Sensor List");
						tab.add("Tab Sensor List", tabListSensor);
						Frame.goToTab(7);
					}
				}
			}
		});

		/**
		 * Launch thread
		 */
		threadCommonArea.start();

		///////////////////////// FRAME/////////////////////////////////////////////////
		/**
		 * Different parameter of the window
		 */
		this.setLayout(new BorderLayout());
		this.add(bar, BorderLayout.NORTH);
		this.setBackground(color);
	}

	/**
	 * Find all the CommonArea in the data base and add on list to be displayed
	 */
	public void updateListCommonArea() {
		commonArea = new CommonArea();

		requestType = "READ ALL";
		table = "CommonArea";
		listCommonArea = getAllCommonArea(null, requestType, table);
		listM.clear();
		listM = new DefaultListModel<>();
		listM.addElement("All commons areas");
		for (CommonArea commonAreas : listCommonArea) {
			listM.addElement(commonAreas.getIdCommonArea() + "# " + commonAreas.getNameCommonArea() + " ,"
					+ commonAreas.getEtageCommonArea());
		}
		list.setModel(listM);
	}

	/**
	 * Find the CommonArea by the id get on list
	 */
	public void updateCommonAreaSelected() {
		if (index != -9999) {
			String substring = listM.getElementAt(index).toString();
			int position = substring.indexOf("#");
			System.out.println(position);
			if (position > -1) {
				String id = substring.substring(0, position);

				requestType = "READ";
				table = "CommonArea";
				commonArea = new CommonArea();
				commonArea.setIdCommonArea(Integer.parseInt(id));
				commonArea = getCommonArea(commonArea, requestType, table);
				textInputIdCommonArea.setText(Integer.toString(commonArea.getIdCommonArea()));
				textInputNameCommonArea.setText(commonArea.getNameCommonArea());
				textInputStageCommonArea.setText(Integer.toString(commonArea.getEtageCommonArea()));
				textInputAreaCommonArea.setText(Integer.toString(commonArea.getArea()));
				textInputMaxSensor.setText(Integer.toString(commonArea.getMaxSensor()));
			}
		}
	}

	/**
	 * Get the commonArea for the request READ
	 * 
	 * @param commonArea
	 * @param requestType
	 * @param table
	 * @return
	 */
	public CommonArea getCommonArea(CommonArea area, String requestType, String table) {
		objectMapper = new ObjectMapper();
		try {
			jsonString = objectMapper.writeValueAsString(area);
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			commonArea = objectMapper.readValue(jsonString, CommonArea.class);
			logger.log(Level.DEBUG, "Find CommonArea data succed");
			return commonArea;
		} catch (Exception e1) {
			logger.log(Level.WARN, "Impossible to parse in JSON CommonArea datas " + e1.getClass().getCanonicalName());
			return null;
		}
	}

	/**
	 * Get all the commonArea for the requestType FIND ALL and READ ALL
	 * 
	 * @param commonArea
	 * @param requestType
	 * @param table
	 * @return
	 */
	public List<CommonArea> getAllCommonArea(CommonArea commonArea, String requestType, String table) {
		objectMapper = new ObjectMapper();
		try {
			jsonString = objectMapper.writeValueAsString(commonArea);
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			CommonArea[] commonAreas = objectMapper.readValue(jsonString, CommonArea[].class);
			logger.log(Level.DEBUG, "Find CommonArea data succed");
			return Arrays.asList(commonAreas);
		} catch (Exception e1) {
			logger.log(Level.WARN, "Impossible to parse in JSON CommonArea datas " + e1.getClass().getCanonicalName());
			return null;
		}
	}

	/**
	 * @return the threadCommonArea
	 */
	public Thread getThreadCommonArea() {
		return threadCommonArea;
	}

	/**
	 * @param threadCommonArea the threadCommonArea to set
	 */
	public void setThreadCommonArea(Thread threadCommonArea) {
		this.threadCommonArea = threadCommonArea;
	}
}