package com.blackmamba.deathkiss.bianalysis.gui;

import static java.awt.BorderLayout.CENTER;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Statement;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.JsonStringEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toedter.calendar.JDateChooser;
import javax.swing.JTabbedPane;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

/**
 * 
 * @author Slayde
 *
 */

public class GUIBi extends JFrame {

	private JList<String> list;
	private JList<String> list1;
	private static List<Sensor> listSensor = new ArrayList<Sensor>();
	private static List<SensorHistorical> listSensorHistorical = new ArrayList<SensorHistorical>();
	private static List<CommonArea> listCommonAreas = new ArrayList<CommonArea>();
	private static List<CommonArea> listNumberCommonAreas = new ArrayList<CommonArea>();
	private static List<Message> listAllMessage = new ArrayList<Message>();

	private ObjectMapper objectMapper;
	private String requestType;
	private String table;
	private String jsonString;
	private String request;

	private JPanel contentPane;
	private JTextField tfDown;
	private JTextField tfAlertesReceived;
	private JTextField tfStock;
	private JTextField searchBar;
	private DefaultListModel<String> listM;
	private DefaultListModel<String> ListModel;
	private DefaultListModel<String> listCount;
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
	private JTextField tfNbCommonArea;
	private JTextField tfSensor;
	private JTextField tfAlert;
	private JTextField tfPassiveSensor;
	private JTextField tfActiveSensor;
	private JTextField tfMeanTemperatureStageRC;
	private JTextField tfMeanTemperatureStage1;
	private JTextField tfTotalStockRc;
	private JTextField tfTotalStockEtage1;
	private JLabel lbTotalStockRc;
	private JLabel lbTotalStockStage1;
	private JComboBox cbStockTypeSensor;

	//////////////////////////////////////////////////////////////////////
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

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
	public GUIBi() {
		GetAllSensor();
		GetAllSensorHistorical();
//		getNumberDownOverSensor();
		GetNumberAlertReceived();
		getNumberUpdate();
		getNumberStageSensor();
//		getAllCommonArea();
//		getNumberPassiveActiveSensor();
		getAllMessage();
//		GetNumberUsedSensor();
		getNumberCommonArea();
		getAverageTemperature();
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

		/////////////////////////////////////////////
		/// Object

		Object[] NumberMessage = returnNumberMessage();

		Object[] NumberUsedSensor = returns();

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
		pieDataset.setValue("Number of Unused Sensors", (Number) NumberUsedSensor[0]);
		pieDataset.setValue("Number of used Sensors", (Number) NumberUsedSensor[1]);

		JFreeChart pieChart = ChartFactory.createPieChart("Ratio of unused sensors and used", pieDataset, true, true,
				true);
		ChartPanel cPanel = new ChartPanel(pieChart);
		contentPane.add(cPanel);

		// Graphic Historical alert

		DefaultPieDataset HistoricalAlert = new DefaultPieDataset();

		HistoricalAlert.setValue("Number of DOWN Sensors", (Number) NumberMessage[2]);
		HistoricalAlert.setValue("Number of OVER Sensors", (Number) NumberMessage[3]);
		HistoricalAlert.setValue("Number of NORMAL Sensors", (Number) NumberMessage[1]);

		JFreeChart pieChart1 = ChartFactory.createPieChart(" Ratio of number of alert", HistoricalAlert, true, true,
				true);
		ChartPanel cPanel1 = new ChartPanel(pieChart1);
		contentPane.add(cPanel1);

		//////////////////////////////////////////////
		// TabbedPane - DASH BOARD
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(22, 11, 824, 577);
		contentPane.add(tabbedPane);

		panel = new JPanel();
		tabbedPane.addTab("DASHBOARD", null, panel, null);
		panel.setLayout(null);
		ChartPanel cpSensors = new ChartPanel(pieChart);
		cpSensors.setBounds(198, 33, 307, 159);
		panel.add(cpSensors);
		cpSensors.setMouseWheelEnabled(true);

		///////////////////////////////////////////////////////
		// DATE
		
		JDateChooser dateChooser_2 = new JDateChooser();
		dateChooser_2.setBounds(129, 139, 69, 20);
		panel.add(dateChooser_2);

		JDateChooser dateChooser_3 = new JDateChooser();
		dateChooser_3.setBounds(129, 170, 69, 20);
		panel.add(dateChooser_3);
		
		// label - DASHBOARD
		JLabel lblStock = new JLabel("Number of sensors in stock");
		lblStock.setBounds(210, 233, 147, 16);
		panel.add(lblStock);

		JLabel lblNombreDalertes = new JLabel("Total Alerts received");
		lblNombreDalertes.setBounds(594, 492, 112, 22);
		panel.add(lblNombreDalertes);

		JLabel lblTotalOver = new JLabel("Total OVER");
		lblTotalOver.setBounds(680, 265, 139, 22);
		panel.add(lblTotalOver);

		lblTotalAlertAttente = new JLabel("Total Pending Alert");
		lblTotalAlertAttente.setBounds(591, 395, 97, 22);
		panel.add(lblTotalAlertAttente);

		JLabel lblNombreDePannes = new JLabel("Total DOWN");
		lblNombreDePannes.setBounds(535, 268, 89, 16);
		panel.add(lblNombreDePannes);

		lblNumberOfCommon = new JLabel("number of common area");
		lblNumberOfCommon.setBounds(10, 33, 123, 28);
		panel.add(lblNumberOfCommon);

		JLabel lblNbSensorMaj = new JLabel("Total UPDATE SENSORS");
		lblNbSensorMaj.setBounds(20, 195, 123, 28);
		panel.add(lblNbSensorMaj);

		JLabel lblTotalActive = new JLabel("Total Active");
		lblTotalActive.setBounds(80, 234, 63, 14);
		panel.add(lblTotalActive);

		JLabel lblTotalPassive = new JLabel("Total Passive");
		lblTotalPassive.setBounds(80, 278, 78, 14);
		panel.add(lblTotalPassive);

		JLabel lbltfMeanTemperatureStageRC = new JLabel("Mean temperature Stage RC");
		lbltfMeanTemperatureStageRC.setBounds(20, 477, 147, 22);
		panel.add(lbltfMeanTemperatureStageRC);

		JLabel lblMeanTemperatureStage = new JLabel("Mean temperature Stage 1");
		lblMeanTemperatureStage.setBounds(194, 477, 135, 22);
		panel.add(lblMeanTemperatureStage);

		lbTotalStockRc = new JLabel("Total Stock Sensor RC");
		lbTotalStockRc.setBounds(10, 395, 147, 22);
		panel.add(lbTotalStockRc);

		lbTotalStockStage1 = new JLabel("Total Stock Sensor Etage 1");
		lbTotalStockStage1.setBounds(181, 399, 135, 22);
		panel.add(lbTotalStockStage1);

//////////////////////////////////////////////////////////////////
		// Textfield - DASHBOARD
		tfStock = new JTextField();
		tfStock.setBounds(367, 227, 41, 28);
		panel.add(tfStock);
		tfStock.setColumns(10);

		ChartPanel cpAlerts = new ChartPanel(pieChart1);
		cpAlerts.setBounds(515, 33, 281, 159);
		panel.add(cpAlerts);

		tfAlertesReceived = new JTextField();
		tfAlertesReceived.setBounds(612, 428, 69, 55);
		// String numberAlertReceived = GetNumberAlertReceived();
		// tfAlertesReceived.setText(numberAlertReceived.toString().replaceAll("\"",
		// ""));
		// tfAlertesReceived.setText(String.valueOf(nbTotalAlert));
		tfAlertesReceived.setColumns(10);
		panel.add(tfAlertesReceived);

		tfDown = new JTextField();
		tfDown.setBackground(Color.ORANGE);
		tfDown.setBounds(525, 203, 97, 55);
		tfDown.setColumns(10);
		panel.add(tfDown);

		tfNbOver = new JTextField();
		tfNbOver.setBackground(Color.RED);
		tfNbOver.setBounds(663, 203, 97, 55);
		tfNbOver.setColumns(10);
		panel.add(tfNbOver);

		tfPendingAlert = new JTextField();
		tfPendingAlert.setBackground(Color.GREEN);
		tfPendingAlert.setBounds(591, 321, 97, 65);
		tfPendingAlert.setColumns(10);
		tfPendingAlert.setText(NumberMessage[0].toString());
		panel.add(tfPendingAlert);

		tfNbCommonArea = new JTextField();
		tfNbCommonArea.setBounds(20, 56, 112, 55);
		panel.add(tfNbCommonArea);
		tfNbCommonArea.setColumns(10);

		JTextField tfNbSensorMaj = new JTextField();
		tfNbSensorMaj.setBounds(10, 139, 112, 55);
		panel.add(tfNbSensorMaj);

		tfPassiveSensor = new JTextField();
		tfPassiveSensor.setBounds(20, 268, 50, 35);
		panel.add(tfPassiveSensor);
		tfPassiveSensor.setColumns(10);

		tfActiveSensor = new JTextField();
		tfActiveSensor.setColumns(10);
		tfActiveSensor.setBounds(20, 222, 50, 35);
		panel.add(tfActiveSensor);

		tfMeanTemperatureStageRC = new JTextField();
		tfMeanTemperatureStageRC.setHorizontalAlignment(SwingConstants.TRAILING);
		tfMeanTemperatureStageRC.setColumns(10);
		tfMeanTemperatureStageRC.setBounds(40, 426, 57, 44);
		panel.add(tfMeanTemperatureStageRC);

		tfMeanTemperatureStage1 = new JTextField();
		tfMeanTemperatureStage1.setHorizontalAlignment(SwingConstants.TRAILING);
		tfMeanTemperatureStage1.setColumns(10);
		tfMeanTemperatureStage1.setBounds(210, 426, 57, 44);
		panel.add(tfMeanTemperatureStage1);

		tfTotalStockRc = new JTextField();
		tfTotalStockRc.setHorizontalAlignment(SwingConstants.TRAILING);
		tfTotalStockRc.setColumns(10);
		tfTotalStockRc.setBounds(40, 343, 57, 44);
		panel.add(tfTotalStockRc);

		tfTotalStockEtage1 = new JTextField();
		tfTotalStockEtage1.setHorizontalAlignment(SwingConstants.TRAILING);
		tfTotalStockEtage1.setColumns(10);
		tfTotalStockEtage1.setBounds(210, 343, 57, 44);
		panel.add(tfTotalStockEtage1);
		///////////////////////////////////////////////////////////
		// Button - DASHBOARD
		btnTemperature = new JButton("Calculer");
		btnTemperature.setBounds(94, 510, 124, 28);
		panel.add(btnTemperature);

		JComboBox cbTotalTypeSensorStock = new JComboBox();
		cbTotalTypeSensorStock.setBounds(418, 230, 87, 22);
		panel.add(cbTotalTypeSensorStock);
		
		cbStockTypeSensor = new JComboBox();
		cbStockTypeSensor.setBounds(107, 362, 73, 22);
		panel.add(cbStockTypeSensor);



////////////////////////////////////////////////////////////
		// Combobox in Tabbed Alert
		panel_1 = new JPanel();
		tabbedPane.addTab("Alert", null, panel_1, null);
		panel_1.setLayout(null);
		cbArea = new JComboBox(area);
		cbArea.setBounds(291, 39, 61, 35);
		panel_1.add(cbArea);

		cbArea.setEditable(true);
		cbArea.addItem(area);

		/////////////////////////////////////////////////////////////
		// Date
		dateChooser_1 = new JDateChooser();
		dateChooser_1.setBounds(189, 55, 47, 28);
		panel_1.add(dateChooser_1);

		dateChooser = new JDateChooser();
		dateChooser.setBounds(189, 11, 47, 28);
		panel_1.add(dateChooser);
		// Textfield - Alert
		tfDate = new JTextField();
		tfDate.setBounds(90, 11, 47, 28);
		panel_1.add(tfDate);
		tfDate.setColumns(10);

		tfDate1 = new JTextField();
		tfDate1.setBounds(90, 55, 47, 28);
		panel_1.add(tfDate1);
		tfDate1.setColumns(10);
		// label - Alert
		JLabel lblTo = new JLabel("to");
		lblTo.setBounds(52, 66, 28, 16);
		panel_1.add(lblTo);

		JLabel lblNewLabel = new JLabel("Data Range from");
		lblNewLabel.setBounds(10, 23, 97, 16);
		panel_1.add(lblNewLabel);
		sc1 = new JScrollPane();
		sc1.setBounds(31, 145, 276, 354);
		panel_1.add(sc1);
		list1 = new JList<String>(ListModel);
		sc1.setViewportView(list1);

		// Button - Alert
		btnDate = new JButton("GetDate");
		btnDate.setBounds(386, 39, 97, 22);
		panel_1.add(btnDate);

		tfAlert = new JTextField();
		tfAlert.setBounds(330, 262, 119, 67);
		panel_1.add(tfAlert);
		tfAlert.setColumns(10);

		////////////////////////////////////////////////
		// tabbed Sensor
		panel_2 = new JPanel();
		tabbedPane.addTab("Sensor", null, panel_2, null);
		panel_2.setLayout(null);
		sc = new JScrollPane();
		sc.setBounds(38, 64, 313, 381);
		panel_2.add(sc);
		list = new JList<String>(listM);
		sc.setViewportView(list);

		// label Sensor
		JLabel lblSensors = new JLabel("Sensors");
		lblSensors.setBounds(57, 11, 55, 39);
		panel_2.add(lblSensors);

		cbCapteur = new JComboBox(sensorType);
		cbCapteur.setBounds(111, 13, 100, 35);
		panel_2.add(cbCapteur);
		// Textfield in Sensor
		tfSensor = new JTextField();
		tfSensor.setText(String.valueOf(nbAlertSensor));
		tfSensor.setColumns(10);
		tfSensor.setBackground(Color.GREEN);
		tfSensor.setBounds(132, 456, 97, 65);
		panel_2.add(tfSensor);

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

//	public int getAllCommonArea() {
//		requestType = "READ ALL";
//		table = "CommonArea";
//		objectMapper = new ObjectMapper();
//		try {
//			jsonString = "READ ALL";
//			new ClientSocket(requestType, jsonString, table);
//			jsonString = ClientSocket.getJson();
//			CommonArea[] commonAreas = objectMapper.readValue(jsonString, CommonArea[].class);
//			logger.log(Level.INFO, "Find CommonArea data succed");
//			listCommonAreas = Arrays.asList(commonAreas);
//			nbCommonArea = listCommonAreas.size();
//
//		} catch (Exception e1) {
//			logger.log(Level.INFO, "Impossible to parse in JSON CommonArea datas " + e1.getClass().getCanonicalName());
//
//		}
//		return nbCommonArea;
//	}
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

///////////////////////////////////////////
///////////////////////////////////////

	///////////////////////////////////////////

///////////////////////////////////////
// Method of calculating the number of CommonArea

	// Method of calculating the number of DOWN Sensors and OVER Sensors for a
	/////////////////////////////////////// graphic visualization

	// Method of calculating of Number active Sensor and passive Sensor
	/////////////////////////////////////// graphic visualization
//	private Object[] getNumberPassiveActiveSensor() {
//		String s2 = "";
//		String s3 = "";
//		requestType = "COUNTING";
//		table = "Sensor";
//		objectMapper = new ObjectMapper();
//		try {
//			jsonString = "COUNTING";
//			new ClientSocket(requestType, jsonString, table);
//			jsonString = ClientSocket.getJson();
//			System.out.println(jsonString);
//
//			String jsonString_list[] = jsonString.replaceAll("\"", "").split(",");
//			s2 = jsonString_list[0];
//			s3 = jsonString_list[1];
//			System.out.println("resultat Active" + s2);
//			System.out.println("resultat Passive" + s3);
//
////				jsonString.toString();
////				System.out.println(jsonString.replaceAll("\"","" ));
////				Object[] msg = objectMapper.readValue(jsonString, Object[].class);
//
//		} catch (Exception e1) {
//			logger.log(Level.INFO, "Impossible to parse in JSON Sensor data " + e1.getClass().getCanonicalName());
//		}
//		return new Object[] { s2, s3 };
//	}
//	
//	
//	public String DateFromDateTo() {
//		
//		requestType = "COUNT";
//		table = "SensorHistorical";
//		ObjectMapper objWriter = new ObjectMapper();
//		String jsonString = "";
//		
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
//		
//		String DateFrom = df.format(dateChooser.getDate());
//		String DateTo=df1.format(dateChooser_1.getDate());
//		
//		
//		try {
//			
//			jsonString = DateFrom + "," + DateTo;
//			jsonString = objWriter.writeValueAsString(jsonString);
//			new ClientSocket(requestType, jsonString, table);
//			jsonString = ClientSocket.getJson();
//			logger.log(Level.INFO, "Find UPDATE SENSOR");
//		} catch (JsonProcessingException e) {
//			logger.log(Level.WARN,
//					"Impossible to get SensorHistorical datas from BDD " + e.getClass().getCanonicalName());
//		}
//		return jsonString;
//	}

	public String getNumberCommonArea() {

		requestType = "COUNT";
		table = "CommonArea";
		ObjectMapper objWriter = new ObjectMapper();
		String jsonString1 = "";

		try {

			jsonString = "SELECT COUNT(*) FROM partie_commune;";
			jsonString = objWriter.writeValueAsString(jsonString);
			new ClientSocket(requestType, jsonString, table);
			jsonString1 = ClientSocket.getJson();
			System.out.println(jsonString1);
			logger.log(Level.INFO, "Find NUMBER COMMON AREA");
			System.out.println("resultat" + jsonString);
		} catch (JsonProcessingException e) {
			logger.log(Level.WARN,
					"Impossible to get SensorHistorical datas from BDD " + e.getClass().getCanonicalName());
		}
		return jsonString;
	}

	public String getNumberDownOverSensor() {
		String s2 = "";
		String s3 = "";
		requestType = "COUNT";
		table = "Message";
		ObjectMapper objWriter = new ObjectMapper();
		String jsonString = "";

		try {

			jsonString = "SELECT sum(case when seuil = '0' AND (id_capteur !=0 AND id_capteur != 9999) then 1 else 0 end ) As nbSensorDown,sum(case when seuil = '0' AND (id_capteur !=0 AND id_capteur = 9999) then 1 else 0 end ) As nbSensorOver FROM message;";
			jsonString = objWriter.writeValueAsString(jsonString);
			new ClientSocket(requestType, jsonString, table);
			System.out.println("okay");
			jsonString = ClientSocket.getJson();
			logger.log(Level.INFO, "Find DOWN OVER SENSOR");
			System.out.println("resultat2 " + jsonString);

			String jsonString_list[] = jsonString.replaceAll("\"", "").split(",");
			s2 = jsonString_list[0];
			s3 = jsonString_list[1];
			System.out.println("resultat2 Down " + s2);
			System.out.println("resultat2 Over " + s3);
		} catch (JsonProcessingException e) {
			logger.log(Level.WARN,
					"Impossible to get SensorHistorical datas from BDD " + e.getClass().getCanonicalName());
		}
		return jsonString;
	}

	public String GetNumberAlertReceived() {
		requestType = "COUNT";
		table = "SensorHistorical";
		ObjectMapper objWriter = new ObjectMapper();
		String jsonString = "";

		try {

			jsonString = "SELECT COUNT(*) FROM historique;";
			jsonString = objWriter.writeValueAsString(jsonString);
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			logger.log(Level.INFO, "Find Number Alert Received");
			System.out.println("resultat alert received " + jsonString);
		} catch (JsonProcessingException e) {
			logger.log(Level.WARN,
					"Impossible to get SensorHistorical datas from BDD " + e.getClass().getCanonicalName());
		}
		return jsonString;
	}

	public String GetNumberUsedSensor() {
		String s2 = "";
		String s3 = "";
		requestType = "COUNT";
		table = "Sensor";
		ObjectMapper objWriter = new ObjectMapper();
		String jsonString = "";

		try {

			jsonString = "SELECT sum(case when id_capteur = 0 then 1 else 0 end ) As nbUnusedSensor, sum(case when id_capteur != 0 then 1 else 0 end ) As nbUsedSensor FROM capteur;";
			jsonString = objWriter.writeValueAsString(jsonString);
			new ClientSocket(requestType, jsonString, table);
			System.out.println("okay");
			jsonString = ClientSocket.getJson();
			logger.log(Level.INFO, "Find UNUSED USED SENSOR");
			System.out.println("resultat2 " + jsonString);

			String jsonString_list[] = jsonString.replaceAll("\"", "").split(",");
			s2 = jsonString_list[0];
			s3 = jsonString_list[1];
			System.out.println("resultat2 Unused " + s2);
			System.out.println("resultat2 used " + s3);
		} catch (JsonProcessingException e) {
			logger.log(Level.WARN,
					"Impossible to get SensorHistorical datas from BDD " + e.getClass().getCanonicalName());
		}
		return jsonString;
	}

	public void getNumberStageSensor() {

//		String s2 = "";
//		String s3 = "";
		requestType = "COUNT";
		table = "Sensor";
		ObjectMapper objWriter = new ObjectMapper();
		String jsonString = "";

		try {

			jsonString = "SELECT sum(case when ( id_partie_commune = 1 AND id_capteur != 0) then 1 else 0 end ) As nbSensorEtage1, sum(case when ( id_partie_commune = 0 AND id_capteur != 0) then 1 else 0 end ) As nbSensorRC FROM capteur;";
			jsonString = objWriter.writeValueAsString(jsonString);
			new ClientSocket(requestType, jsonString, table);
			System.out.println("okay");
			jsonString = ClientSocket.getJson();
			logger.log(Level.INFO, "Find NUMBER SENSOR FOR EACH STAGE");
			System.out.println("resultat " + jsonString);

			String jsonString_list[] = jsonString.replaceAll("\"", "").split(",");
//			s2 = jsonString_list[0];
//			s3 = jsonString_list[1];
//			System.out.println("resultat2 Etage1 " + s2);
//			System.out.println("resultat2 RC" + s3);
		} catch (JsonProcessingException e) {
			logger.log(Level.WARN,
					"Impossible to get SensorHistorical datas from BDD " + e.getClass().getCanonicalName());
		}

	}

	public String getNumberUpdate() {
		String s2 = "";
		String s3 = "";
		requestType = "COUNT";
		table = "SensorHistorical";
		ObjectMapper objWriter = new ObjectMapper();
//		String jsonString = "";
//		DateFormat df = new SimpleDateFormat("'yyyy-MM-dd'");
//		DateFormat df1 = new SimpleDateFormat("'yyyy-MM-dd'");
//		
//		String DateFrom = df.format(dateChooser.getDate());
//		String DateTo=df1.format(dateChooser_1.getDate());

		String DateFrom = "'2000-10-01'";
		String DateTo = "'2006-10-01'";

		try {

			jsonString = "SELECT COUNT(*) FROM historique WHERE date_historique BETWEEN " + DateFrom + "AND " + DateTo
					+ "AND ( type_alerte = 'NORMAL')";
//			jsonString = "SELECT COUNT(*) FROM capteur";
			jsonString = objWriter.writeValueAsString(jsonString);
			System.out.println(jsonString);
			new ClientSocket(requestType, jsonString, table);
			System.out.println("okay date");
			jsonString = ClientSocket.getJson();
			logger.log(Level.INFO, "Find NUMBER SENSOR UPDATE PER PERIOD");
			System.out.println("resultat " + jsonString);
			System.out.println("test2");
			String jsonString_list[] = jsonString.replaceAll("\"", "").split(",");
//			s2 = jsonString_list[0];
//			s3 = jsonString_list[1];
//			System.out.println("resultat2 Etage1 " + s2);
//			System.out.println("resultat2 RC" + s3);
		} catch (JsonProcessingException e) {
			logger.log(Level.WARN,
					"Impossible to get SensorHistorical datas from BDD " + e.getClass().getCanonicalName());
		}
		return jsonString;
	}

	public String getAverageTemperature() {

		String s2 = "";
		String s3 = "";
		requestType = "COUNT";
		table = "Sensor";
		ObjectMapper objWriter = new ObjectMapper();
//		String jsonString = "";

		try {

			jsonString = "SELECT capteur.id_capteur, capteur.type_capteur, id_partie_commune, ROUND(AVG(message.seuil),2) FROM capteur INNER JOIN message ON capteur.id_capteur = message.id_capteur GROUP BY capteur.id_capteur, capteur.id_partie_commune;";
			jsonString = objWriter.writeValueAsString(jsonString);
			System.out.println(jsonString);
			new ClientSocket(requestType, jsonString, table);
			System.out.println("okay");
			jsonString = ClientSocket.getJson();
			System.out.println(jsonString);
			logger.log(Level.INFO, "Find AVERAGE TEMPERATURE FOR EACH STAGE");
			System.out.println("resultat temperature " + jsonString);

			String jsonString_list[] = jsonString.replaceAll("\"", "").split(",");
//			s2 = jsonString_list[0];
//			s3 = jsonString_list[1];
//			System.out.println("resultat2 Etage1 " + s2);
//			System.out.println("resultat2 RC" + s3);
		} catch (JsonProcessingException e) {
			logger.log(Level.WARN,
					"Impossible to get SensorHistorical datas from BDD " + e.getClass().getCanonicalName());
		}
		return jsonString;

	}
}

///////////////
//TODO CALCULER LE NOMBRE DE MODIFICATIONS 
