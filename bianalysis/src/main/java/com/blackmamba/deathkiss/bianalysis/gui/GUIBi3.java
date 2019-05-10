package com.blackmamba.deathkiss.bianalysis.gui;

import static java.awt.BorderLayout.CENTER;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import com.blackmamba.deathkiss.bianalysis.entity.AlertState;
import com.blackmamba.deathkiss.bianalysis.entity.CommonArea;
import com.blackmamba.deathkiss.bianalysis.entity.Message;
import com.blackmamba.deathkiss.bianalysis.entity.Sensor;
import com.blackmamba.deathkiss.bianalysis.entity.SensorHistorical;
import com.blackmamba.deathkiss.bianalysis.entity.SensorType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toedter.calendar.JDateChooser;
import javax.swing.JTabbedPane;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField;

/**
 * 
 * @author Slayde
 *
 */

public class GUIBi3 extends JFrame {
//TODO SL les trois ligne tu peux les mettre chacune juste avant de les utiliser 

	// pour celle la j'ai changer, tu ira voir dans la classe ou tu l'a prise et tu
	// prendra la forme que j'ai pcq on a pas le droit de les mettre en dur comme ca
	// faut utiliser l'enum

	private JList<String> list;
	private JList<String> list1;
	private static List<Sensor> listSensor = new ArrayList<Sensor>();
	private static List<SensorHistorical> listSensorHistorical = new ArrayList<SensorHistorical>();
	private static List<CommonArea> listCommonAreas = new ArrayList<CommonArea>();
	private static List<Message> listAllMessage = new ArrayList<Message>();
	private ObjectMapper objectMapper;
	private String requestType;
	private String table;
	private String jsonString;
	private JPanel contentPane;
	private JTextField tfDown;
	private JTextField tfAlertesReceived;
	private JTextField tfStock;
	private JTextField searchBar;
	private DefaultListModel<String> listM;
	private DefaultListModel<String> ListModel;
	private JScrollPane sc;
	private JScrollPane sc1;

	private JComboBox cbArea;
	private JComboBox cbCapteur;
	private JButton btnDeconnexion;
	private JButton btnTemperature;
	private JButton btnDate;
	private JButton btnGraphique;
	private static final Logger logger = LogManager.getLogger(TabSensor.class);
	private JLabel lbl;
	private JDateChooser dateChooser;
	private JDateChooser dateChooser_1;
	private JTextField tfDate;
	private JTextField tfDate1;
	private JDialog ratioSensors;
	private ChartPanel cPanel;
	private JTextField tfNbOver;
	private JLabel lblTotalAlertAttente;
	private JTextField tfPendingAlert;
	private int nbCommonArea = 0;
	private int nbTotalAlert = 0;
	private int indice = 0;
	private int nbAlertSensor = 0;
	private JTabbedPane tabbedPane;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JLabel lblNumberOfCommon;
	private JTextField tfCommonArea;
	private JTextField tfSensor;
	private JTextField tfAlert;
	private JTextField tfNbSensorStage;
	private JTextField tfNbSensorStage1;
	private JTextField tfNbSensorStage2;
	private JTextField tfMeanTemperatureRc;
	private JTextField tfMeanTemperature1;
	private JTextField tfMeanTemperature2;

//TODO SL tu met la methode main dans une classe qui s'appelle MainBianalysisGUI
	//////////////////////////////////////////////////////////////////////
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// TODO SL change le nom de ta classe ihmBI c'est du français on fait l'appli en
					// anglais meme le texte sur l'ihm
					// ihm == GUI
					// donc appelle la Frame c'est simple et comprehensible de toute facon y aura
					// pas deux frame dans ton jar donc pas de conflit
					// copie pas les classes que j'ai faite dans ton projet tu les ouvres tu copie
					// ok mais les integre pas pcq apres tu ne sais pas ou t'en es et apres au
					// moment de l'execution t'as des conflit pour rien
					GUIBi frame = new GUIBi();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
////////////////////////////////////////////////////////////////////////

	/**
	 * Create the frame.
	 */
	public GUIBi3() {
		GetAllSensor();
		GetAllSensorHistorical();
		getAllCommonArea();
		getAllMessage();
		initComponents();
		createEvents();

	}

	// Methods contains all
	private void initComponents() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 998, 636);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

/////////////////////////////////////////////
		//// JCombobox

		String[] periode = { "Année", "Mois", "Jour" };
		String[] area = { "ALL", "RC", "Etage 1", "Etage 2" };

		String[] sensorType = { "ALL", "SMOKE", "MOVE", "TEMPERATURE", "WINDOW", "DOOR", "ELEVATOR", "LIGHT", "FIRE",
				"BADGE", "ROUTER" };

		btnDeconnexion = new JButton("Deconnexion");
		btnDeconnexion.setBounds(860, 0, 124, 28);
		contentPane.add(btnDeconnexion);

		Object[] monObj1 = returnNumberMessage();

		Object[] monObj = returns();

///////////////////////////////////////////////////
		// List
		listM = new DefaultListModel<String>();

		listM.addElement("Tout les capteurs");
		for (Sensor sensors : listSensor) {
			listM.addElement(sensors.getIdSensor() + "# " + sensors.getTypeSensor() + " ," + sensors.getSensorState()
					+ " ," + sensors.getIdCommonArea());
		}

		ListModel = new DefaultListModel<String>();

		ListModel.addElement("Toute les alertes");
		for (SensorHistorical sensorHistorical : listSensorHistorical) {
			ListModel.addElement(sensorHistorical.getAlertState() + "# " + sensorHistorical.getIdHistorical() + " ,"
					+ sensorHistorical.getIdSensor() + " ," + sensorHistorical.getDate());
		}

/////////////////////////////////////
		// Dialog
		ratioSensors = new JDialog();

////////////////////////////////////
		// Graphic All sensor
		DefaultPieDataset pieDataset = new DefaultPieDataset();
		pieDataset.setValue("Number of Unused Sensors", (Number) monObj[0]);
		pieDataset.setValue("Number of used Sensors", (Number) monObj[1]);

		JFreeChart pieChart = ChartFactory.createPieChart("Ratio of unused sensors and used", pieDataset, true, true,
				true);
		ChartPanel cPanel = new ChartPanel(pieChart);
		contentPane.add(cPanel);

		// Graphic Historical alert

		DefaultPieDataset HistoricalAlert = new DefaultPieDataset();

		HistoricalAlert.setValue("Number of DOWN Sensors", (Number) monObj1[2]);
		HistoricalAlert.setValue("Number of OVER Sensors", (Number) monObj1[3]);
		HistoricalAlert.setValue("Number of NORMAL Sensors", (Number) monObj1[1]);

		JFreeChart pieChart1 = ChartFactory.createPieChart(" Ratio of number of alert", HistoricalAlert, true, true,
				true);
		ChartPanel cPanel1 = new ChartPanel(pieChart1);
		contentPane.add(cPanel1);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(22, 11, 824, 577);
		contentPane.add(tabbedPane);

		btnDate = new JButton("GetDate");
		btnDate.setBounds(386, 39, 97, 22);
		panel_1.add(btnDate);

		JLabel lblTo = new JLabel("to");
		lblTo.setBounds(52, 66, 28, 16);
		panel_1.add(lblTo);

		JLabel lblNewLabel = new JLabel("Data Range from");
		lblNewLabel.setBounds(10, 23, 97, 16);
		panel_1.add(lblNewLabel);

		JLabel lblSensors = new JLabel("Sensors");
		lblSensors.setBounds(57, 11, 55, 39);
		panel_2.add(lblSensors);

		Object[] monObj2 = returnNbSensorType();

		tfSensor = new JTextField();
		tfSensor.setText(String.valueOf(nbAlertSensor));
		tfSensor.setColumns(10);
		tfSensor.setBackground(Color.GREEN);
		tfSensor.setBounds(132, 456, 97, 65);
		panel_2.add(tfSensor);

		tfAlert = new JTextField();
		tfAlert.setBounds(330, 262, 119, 67);
		panel_1.add(tfAlert);
		tfAlert.setColumns(10);

		////////////////////////////////////////////////
		// Panel

		panel_1 = new JPanel();
		tabbedPane.addTab("Alert", null, panel_1, null);
		panel_1.setLayout(null);
		cbArea = new JComboBox(area);
		cbArea.setBounds(291, 39, 61, 35);
		panel_1.add(cbArea);

		cbArea.setEditable(true);
		cbArea.addItem(area);

		dateChooser_1 = new JDateChooser();
		dateChooser_1.setBounds(189, 55, 47, 28);
		panel_1.add(dateChooser_1);
		/////////////////////////////////////////////////////////////
		// Date
		dateChooser = new JDateChooser();
		dateChooser.setBounds(189, 11, 47, 28);
		panel_1.add(dateChooser);

		tfDate = new JTextField();
		tfDate.setBounds(90, 11, 47, 28);
		panel_1.add(tfDate);
		tfDate.setColumns(10);

		tfDate1 = new JTextField();
		tfDate1.setBounds(90, 55, 47, 28);
		panel_1.add(tfDate1);
		tfDate1.setColumns(10);

		sc1 = new JScrollPane();
		sc1.setBounds(31, 145, 276, 354);
		panel_1.add(sc1);
		list1 = new JList<String>(ListModel);
		sc1.setViewportView(list1);

		panel_2 = new JPanel();
		tabbedPane.addTab("Sensor", null, panel_2, null);
		panel_2.setLayout(null);
		sc = new JScrollPane();
		sc.setBounds(38, 64, 313, 381);
		panel_2.add(sc);
		list = new JList<String>(listM);
		sc.setViewportView(list);

		cbCapteur = new JComboBox(sensorType);
		cbCapteur.setBounds(111, 13, 100, 35);
		panel_2.add(cbCapteur);

		panel = new JPanel();
		tabbedPane.addTab("DASHBOARD", null, panel, null);
		panel.setLayout(null);
		ChartPanel cpSensors = new ChartPanel(pieChart);
		cpSensors.setBounds(198, 33, 307, 159);
		panel.add(cpSensors);
		cpSensors.setMouseWheelEnabled(true);

		JLabel lblStock = new JLabel("Number of sensors in stock");
		lblStock.setBounds(198, 222, 147, 16);
		panel.add(lblStock);
		tfStock = new JTextField();
		tfStock.setBounds(346, 216, 41, 28);
		panel.add(tfStock);
		tfStock.setText(monObj[2].toString());
		tfStock.setColumns(10);
		ChartPanel cpAlerts = new ChartPanel(pieChart1);
		cpAlerts.setBounds(515, 33, 281, 159);
		panel.add(cpAlerts);

		JLabel lblNombreDalertes = new JLabel("Total Alerts received");
		lblNombreDalertes.setBounds(594, 492, 112, 22);
		panel.add(lblNombreDalertes);
		tfAlertesReceived = new JTextField();
		tfAlertesReceived.setBounds(612, 428, 69, 55);
		tfAlertesReceived.setText(String.valueOf(nbTotalAlert));
		tfAlertesReceived.setColumns(10);
		panel.add(tfAlertesReceived);

		tfPendingAlert = new JTextField();
		tfPendingAlert.setBackground(Color.GREEN);
		tfPendingAlert.setBounds(591, 321, 97, 65);
		tfPendingAlert.setColumns(10);
		tfPendingAlert.setText(monObj1[0].toString());
		panel.add(tfPendingAlert);

		/////////////////////////////////////////////////////
		// Button

		btnTemperature = new JButton("Calculer");
		btnTemperature.setBounds(132, 489, 124, 28);
		panel.add(btnTemperature);

		JButton btnDetails = new JButton("Details");
		btnDetails.setBounds(691, 444, 89, 23);
		panel.add(btnDetails);

		JLabel lblTotalOver = new JLabel("Total OVER");
		lblTotalOver.setBounds(680, 265, 139, 22);
		panel.add(lblTotalOver);

		lblTotalAlertAttente = new JLabel("Total Pending Alert");
		lblTotalAlertAttente.setBounds(591, 395, 97, 22);
		panel.add(lblTotalAlertAttente);

		lblNumberOfCommon = new JLabel("number of common area");
		lblNumberOfCommon.setBounds(10, 33, 123, 28);
		panel.add(lblNumberOfCommon);

		JLabel lblNombreDePannes = new JLabel("Total DOWN");
		lblNombreDePannes.setBounds(535, 268, 89, 16);
		panel.add(lblNombreDePannes);

		tfCommonArea = new JTextField();
		tfCommonArea.setBounds(10, 72, 112, 55);
		tfCommonArea.setText(String.valueOf(nbCommonArea));
		panel.add(tfCommonArea);
		tfCommonArea.setColumns(10);

		tfDown = new JTextField();
		tfDown.setBackground(Color.ORANGE);
		tfDown.setBounds(525, 203, 97, 55);
		tfDown.setText(monObj1[2].toString());
		tfDown.setColumns(10);
		panel.add(tfDown);

		tfNbOver = new JTextField();
		tfNbOver.setBackground(Color.RED);
		tfNbOver.setBounds(663, 203, 97, 55);
		tfNbOver.setText(monObj1[3].toString());
		tfNbOver.setColumns(10);
		panel.add(tfNbOver);
		
		JTextPane tfNbSensorMaj = new JTextPane();
		tfNbSensorMaj.setBounds(10, 150, 112, 55);
		panel.add(tfNbSensorMaj);
		
		JLabel lblNbSensorMaj = new JLabel("Total UPDATE SENSORS");
		lblNbSensorMaj.setBounds(10, 210, 123, 28);
		panel.add(lblNbSensorMaj);
		
		JDateChooser dateChooser_2 = new JDateChooser();
		dateChooser_2.setBounds(132, 150, 69, 20);
		panel.add(dateChooser_2);
		
		JDateChooser dateChooser_3 = new JDateChooser();
		dateChooser_3.setBounds(132, 181, 69, 20);
		panel.add(dateChooser_3);
		
		tfNbSensorStage = new JTextField();
		tfNbSensorStage.setHorizontalAlignment(SwingConstants.TRAILING);
		tfNbSensorStage.setBounds(20, 290, 57, 44);
		panel.add(tfNbSensorStage);
		tfNbSensorStage.setColumns(10);
		
		JLabel lblTota = new JLabel("Total  Sensor Stage RC");
		lblTota.setBounds(10, 341, 124, 22);
		panel.add(lblTota);
		
		tfNbSensorStage1 = new JTextField();
		tfNbSensorStage1.setHorizontalAlignment(SwingConstants.TRAILING);
		tfNbSensorStage1.setColumns(10);
		tfNbSensorStage1.setBounds(190, 290, 57, 44);
		panel.add(tfNbSensorStage1);
		
		JLabel lblTotalSensorStage = new JLabel("Total  Sensor Stage 1");
		lblTotalSensorStage.setBounds(174, 341, 124, 22);
		panel.add(lblTotalSensorStage);
		
		tfNbSensorStage2 = new JTextField();
		tfNbSensorStage2.setHorizontalAlignment(SwingConstants.TRAILING);
		tfNbSensorStage2.setColumns(10);
		tfNbSensorStage2.setBounds(335, 290, 57, 44);
		panel.add(tfNbSensorStage2);
		
		JLabel lblTotalSensorStage_1 = new JLabel("Total  Sensor Stage 2");
		lblTotalSensorStage_1.setBounds(317, 341, 124, 22);
		panel.add(lblTotalSensorStage_1);
		
		tfMeanTemperatureRc = new JTextField();
		tfMeanTemperatureRc.setHorizontalAlignment(SwingConstants.TRAILING);
		tfMeanTemperatureRc.setColumns(10);
		tfMeanTemperatureRc.setBounds(20, 395, 57, 44);
		panel.add(tfMeanTemperatureRc);
		
		tfMeanTemperature1 = new JTextField();
		tfMeanTemperature1.setHorizontalAlignment(SwingConstants.TRAILING);
		tfMeanTemperature1.setColumns(10);
		tfMeanTemperature1.setBounds(190, 395, 57, 44);
		panel.add(tfMeanTemperature1);
		
		tfMeanTemperature2 = new JTextField();
		tfMeanTemperature2.setHorizontalAlignment(SwingConstants.TRAILING);
		tfMeanTemperature2.setColumns(10);
		tfMeanTemperature2.setBounds(337, 395, 57, 44);
		panel.add(tfMeanTemperature2);
		
		JLabel lblMeanTemperatureStage_2 = new JLabel("Mean temperature Stage 2");
		lblMeanTemperatureStage_2.setBounds(319, 446, 147, 22);
		panel.add(lblMeanTemperatureStage_2);
		
		JLabel lblMeanTemperatureStage_1 = new JLabel("Mean temperature Stage 1");
		lblMeanTemperatureStage_1.setBounds(174, 446, 135, 22);
		panel.add(lblMeanTemperatureStage_1);
		
		JLabel lblMeanTemperatureStage = new JLabel("Mean temperature Stage RC");
		lblMeanTemperatureStage.setBounds(0, 446, 147, 22);
		panel.add(lblMeanTemperatureStage);
		
		String[] CommonArea = { "ALL", "RC", "Etage 1", "Etage 2" };
		
		JComboBox cbStage = new JComboBox(CommonArea);
		cbStage.setBounds(129, 88, 59, 28);
		panel.add(cbStage);

		cbCapteur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JComboBox cbCapteur = (JComboBox) e.getSource();
				String selectedBook = (String) cbCapteur.getSelectedItem();

				if (selectedBook.equals("ALL")) {
					listM.clear();
					for (Sensor str : listSensor) {

						listM.addElement(str.getIdSensor() + "# " + str.getTypeSensor() + " ," + str.getSensorState()
								+ " ," + str.getIdCommonArea());

						indice = listM.size();
						System.out.println("okay " + indice);

					}

				} else if (selectedBook.equals("SMOKE")) {
					listM.clear();
					for (Sensor str : listSensor) {
						if (str.getTypeSensor().equals(SensorType.SMOKE)) {
							listM.addElement(str.getIdSensor() + "# " + str.getTypeSensor() + " ,"
									+ str.getSensorState() + " ," + str.getIdCommonArea());

							System.out.println("okay " + indice);
						}
					}

				} else if (selectedBook.equals("ROUTER")) {
					listM.clear();
					for (Sensor str : listSensor) {
						if (str.getTypeSensor().equals(SensorType.ROUTER)) {
							listM.addElement(str.getIdSensor() + "# " + str.getTypeSensor() + " ,"
									+ str.getSensorState() + " ," + str.getIdCommonArea());
							indice = listM.size();
						}
					}

				} else if (selectedBook.equals("ELEVATOR")) {
					listM.clear();
					for (Sensor str : listSensor) {
						if (str.getTypeSensor().equals(SensorType.ELEVATOR))
							listM.addElement(str.getIdSensor() + "# " + str.getTypeSensor() + " ,"
									+ str.getSensorState() + " ," + str.getIdCommonArea());
						indice = listM.size();

					}

				} else if (selectedBook.equals("DOOR")) {
					listM.clear();
					for (Sensor str : listSensor) {
						if (str.getTypeSensor().equals(SensorType.DOOR)) {
							listM.addElement(str.getIdSensor() + "# " + str.getTypeSensor() + " ,"
									+ str.getSensorState() + " ," + str.getIdCommonArea());
							indice = listM.size();
						}
					}
				} else if (selectedBook.equals("LIGHT")) {
					listM.clear();

					for (Sensor str : listSensor) {
						if (str.getTypeSensor().equals(SensorType.LIGHT))
							listM.addElement(str.getIdSensor() + "# " + str.getTypeSensor() + " ,"
									+ str.getSensorState() + " ," + str.getIdCommonArea());
						indice = listM.size();

					}

				} else if (selectedBook.equals("MOVE")) {
					listM.clear();
					for (Sensor str : listSensor) {
						if (str.getTypeSensor().equals(SensorType.MOVE)) {
							listM.addElement(str.getIdSensor() + "# " + str.getTypeSensor() + " ,"
									+ str.getSensorState() + " ," + str.getIdCommonArea());
							indice = listM.size();
						}
					}
				} else if (selectedBook.equals("WINDOW")) {
					listM.clear();
					for (Sensor str : listSensor) {
						if (str.getTypeSensor().equals(SensorType.WINDOW))
							listM.addElement(str.getIdSensor() + "# " + str.getTypeSensor() + " ,"
									+ str.getSensorState() + " ," + str.getIdCommonArea());
						indice = listM.size();
					}

				}
			}
		});

////////////////////////////////////////////////////////////////////
		// Bouton Graphic
		JButton btnGraphic = new JButton("VisualisationGraphique");
		btnGraphic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ratioSensors.getContentPane().add(cPanel, CENTER);

				ratioSensors.pack();
				ratioSensors.setVisible(true);

			}
		});

	}

	private void createEvents() {

		btnDeconnexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.log(Level.INFO, "Application closed, after disconnection");
				System.exit(ABORT);
			}
		});

		btnDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				DateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");

				tfDate.setText(df.format(dateChooser.getDate()));
				tfDate1.setText(df1.format(dateChooser_1.getDate()));

///////////////////////////////////////////////////////				
				ListModel.removeAllElements();
				for (SensorHistorical sensorHistorical1 : listSensorHistorical) {

					if (sensorHistorical1.getDate().after(dateChooser.getDate())
							&& sensorHistorical1.getDate().before((dateChooser_1.getDate()))) {
						ListModel.addElement(Integer.toString(sensorHistorical1.getIdHistorical()) + " Date : "
								+ sensorHistorical1.getDate() + " ID Sensor : " + sensorHistorical1.getIdSensor()
								+ " State : " + sensorHistorical1.getSensorState() + " Alert State :"
								+ sensorHistorical1.getAlertState());
						nbAlertSensor = ListModel.getSize();
						System.out.println("okay " + nbAlertSensor);
					}
				}
			}
		});

		cbArea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JComboBox cbArea = (JComboBox) e.getSource();
				String selectedBook = (String) cbArea.getSelectedItem();

				if (selectedBook.equals("ALL")) {
					ListModel.clear();
					for (CommonArea area : listCommonAreas) {
						for (Sensor str : listSensor) {
							for (SensorHistorical hist : listSensorHistorical) {
								if (hist.getIdSensor() == str.getIdSensor()) {
									if (str.getIdCommonArea() == area.getIdCommonArea()) {// A MODIFIER PLUS TARD

										ListModel.addElement(str.getIdSensor() + "# " + str.getTypeSensor() + " ,"
												+ str.getSensorState() + " ," + area.getNameCommonArea()
												+ str.getAlertState() + "#" + area.getFloorCommonArea());

									}
								}
							}
						}
					}
					nbTotalAlert = ListModel.getSize();
					nbAlertSensor = ListModel.getSize();
					tfAlertesReceived.setText(String.valueOf(nbTotalAlert));
					tfAlert.setText(String.valueOf(nbAlertSensor));

				} else if (selectedBook.equals("RC")) {
					ListModel.clear();
					for (CommonArea area : listCommonAreas) {
						if (area.getFloorCommonArea() == 0) {
							for (Sensor str : listSensor) {
								for (SensorHistorical hist : listSensorHistorical) {
									if (hist.getIdSensor() == str.getIdSensor()) {
										if (str.getIdCommonArea() == area.getIdCommonArea()) {// A MODIFIER PLUS TARD

											ListModel.addElement(str.getIdSensor() + "# " + str.getTypeSensor() + " ,"
													+ str.getSensorState() + " ," + area.getNameCommonArea()
													+ str.getAlertState() + "#" + area.getFloorCommonArea());

										}
									}
								}
							}
						}
					}

					nbAlertSensor = ListModel.getSize();
					tfAlert.setText(String.valueOf(nbAlertSensor));
				}

				else if (selectedBook.equals("Etage 1")) {
					ListModel.clear();
					for (CommonArea area : listCommonAreas) {
						if (area.getFloorCommonArea() == 1) {
							for (Sensor str : listSensor) {
								for (SensorHistorical hist : listSensorHistorical) {
									if (hist.getIdSensor() == str.getIdSensor()) {
										if (str.getIdCommonArea() == area.getIdCommonArea()) {// A MODIFIER PLUS TARD

											ListModel.addElement(str.getIdSensor() + "# " + str.getTypeSensor() + " ,"
													+ str.getSensorState() + " ," + area.getNameCommonArea()
													+ str.getAlertState() + "#" + area.getFloorCommonArea());
										}
									}
								}
							}
						}
					}
					nbAlertSensor = ListModel.getSize();
					tfAlert.setText(String.valueOf(nbAlertSensor));
				} else if (selectedBook.equals("Etage 2")) {
					ListModel.clear();
					System.out.println("Nous somme à l'étage 2");

					for (CommonArea area : listCommonAreas) {
						if (area.getFloorCommonArea() == 2) {
							for (Sensor str : listSensor) {
								for (SensorHistorical hist : listSensorHistorical) {
									if (hist.getIdSensor() == str.getIdSensor()) {
										if (str.getIdCommonArea() == area.getIdCommonArea()) {// A MODIFIER PLUS TARD

											ListModel.addElement(str.getIdSensor() + "# " + str.getTypeSensor() + " ,"
													+ str.getSensorState() + " ," + area.getNameCommonArea()
													+ str.getAlertState() + "#" + area.getFloorCommonArea());
										}
									}
								}
							}
						}
					}
				}
				nbAlertSensor = ListModel.getSize();
				tfAlert.setText(String.valueOf(nbAlertSensor));
			}
		});

	}

////////////////////////////////////
	// Method Get all Sensor contained in the Sensor database
	private void GetAllSensor() {

		requestType = "READ ALL";
		table = "Sensor";
		objectMapper = new ObjectMapper();

		try {
			jsonString = "READ ALL";
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			Sensor[] sensors = objectMapper.readValue(jsonString, Sensor[].class);
			listSensor = Arrays.asList(sensors);
			logger.log(Level.INFO, "Find Sensor data succed");

		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON Sensor data " + e1.getClass().getCanonicalName());
		}

	}

///////////////////////////////////////////
	// Method Get all Alert contained in the Historical database
	private void GetAllSensorHistorical() {

		requestType = "READ ALL";
		table = "SensorHistorical";
		objectMapper = new ObjectMapper();
		try {
			jsonString = "READ ALL";
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			System.out.println(jsonString);
			SensorHistorical[] sensorHistorical = objectMapper.readValue(jsonString, SensorHistorical[].class);
			listSensorHistorical = Arrays.asList(sensorHistorical);
			logger.log(Level.INFO, "Find SensorHistorical data succed");
			nbTotalAlert = listSensorHistorical.size();
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON Sensor data " + e1.getClass().getCanonicalName());
		}

	}
	////////////////////////////////////
	// Method GetAllComonArea

	public int getAllCommonArea() {
		requestType = "READ ALL";
		table = "CommonArea";
		objectMapper = new ObjectMapper();
		try {
			jsonString = "READ ALL";
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			CommonArea[] commonAreas = objectMapper.readValue(jsonString, CommonArea[].class);
			logger.log(Level.INFO, "Find CommonArea data succed");
			listCommonAreas = Arrays.asList(commonAreas);
			nbCommonArea = listCommonAreas.size();

		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON CommonArea datas " + e1.getClass().getCanonicalName());

		}
		return nbCommonArea;
	}
	//////////////////////////////////////
	// Method GetAllMessage

	public void getAllMessage() {
		requestType = "READ ALL";
		table = "Message";
		objectMapper = new ObjectMapper();
		try {
			jsonString = "READ ALL";
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			Message[] Message = objectMapper.readValue(jsonString, Message[].class);
			logger.log(Level.INFO, "Find CommonArea data succed");
			listAllMessage = Arrays.asList(Message);
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON CommonArea datas " + e1.getClass().getCanonicalName());

		}
	}

//////////////////////////////////////
	// Method calculate all alerts
	// TODO MODIFIER POUR LIRE LES ALERTES
	public static Object[] returnNumberMessage() {
		int numberAlerts = 0;
		int nbAlNormal = 0;
		int nbAlDown = 0;
		int nbAlOver = 0;
		int nbAlert = 0;

		if (!listAllMessage.isEmpty()) {
			for (Message allMessage : listAllMessage) {
				numberAlerts++;

				if (allMessage.getThreshold() != 0) {
					nbAlNormal++;
				} else {
					if (allMessage.getIdSensor() != 0) {

						if (allMessage.getIdSensor() != 9999) {
							nbAlDown++;
						} else {
							nbAlOver++;
						}
					}
				}

			}

		}
		return new Object[] { numberAlerts, nbAlNormal, nbAlDown, nbAlOver };

	}

///////////////////////////////////////////////////////////
	// Method to calculate the number of Sensor for each type
	public static Object[] returnNbSensorType() {
		int nbSensorType = 0;
		int nbSensorTemperature = 0;
		int nbSensorSmoke = 0;
		int nbSensorWindow = 0;
		int nbSensorDoor = 0;
		int nbSensorMove = 0;
		int nbSensorElevator = 0;
		int nbSensorLight = 0;
		int nbSensorFire = 0;
		int nbSensorBadge = 0;
		int nbSensorRouter = 0;
		double resultatTemperature = 0;

		if (!listSensor.isEmpty()) {
			for (Sensor type : listSensor) {
				nbSensorType++;

				if (type.getTypeSensor().equals(SensorType.TEMPERATURE)) {
					nbSensorTemperature++;
				} else if (type.getTypeSensor().equals(SensorType.SMOKE)) {
					nbSensorSmoke++;
				} else if (type.getTypeSensor().equals(SensorType.WINDOW)) {
					nbSensorWindow++;
				} else if (type.getTypeSensor().equals(SensorType.DOOR)) {
					nbSensorDoor++;
				} else if (type.getTypeSensor().equals(SensorType.MOVE)) {
					nbSensorMove++;
				} else if (type.getTypeSensor().equals(SensorType.ELEVATOR)) {
					nbSensorElevator++;
				} else if (type.getTypeSensor().equals(SensorType.LIGHT)) {
					nbSensorLight++;
				} else if (type.getTypeSensor().equals(SensorType.FIRE)) {
					nbSensorFire++;
				} else if (type.getTypeSensor().equals(SensorType.BADGE)) {
					nbSensorBadge++;
				} else if (type.getTypeSensor().equals(SensorType.ROUTER)) {
					nbSensorRouter++;
				}
			}

		}

//		BigDecimal resultatTemperature1 = ((new BigDecimal(nbSensorLight).divide(new BigDecimal(nbSensorType)).multiply(new BigDecimal(100))));

		resultatTemperature = (nbSensorLight / nbSensorType) * 100;

		DecimalFormat df2 = new DecimalFormat("###.##");

		return new Object[] { df2.format(resultatTemperature) + "%", nbSensorType, nbSensorSmoke, nbSensorWindow, };

	}

///////////////////////////////////////////////////////////
	// Method to calculate the number of Sensor
	public static Object[] returns() {

		int nbUnusedSensor = 0;
		int nbUsedSensor = 0;
		int nbTotalSensor = 0;
//////////////////////////////////////////////////////////
		for (Sensor sensor : listSensor) {
			nbTotalSensor++;

			if (sensor.getIdSensor() == 0)
				nbUnusedSensor++;
			else
				nbUsedSensor++;
		}
		return new Object[] { nbUsedSensor, nbUnusedSensor, nbTotalSensor };

	}

	public String returnAlert() {
		int numberAlerts = 0;
		if (!listSensorHistorical.isEmpty()) {
			for (SensorHistorical sensorH : listSensorHistorical) {

			}

		}

		return String.valueOf(numberAlerts);

	}
}

///////////////
