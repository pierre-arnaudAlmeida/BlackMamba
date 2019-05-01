package com.blackmamba.deathkiss.bianalysis.gui;

import static java.awt.BorderLayout.CENTER;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.blackmamba.deathkiss.bianalysis.entity.Sensor;
import com.blackmamba.deathkiss.bianalysis.entity.SensorHistorical;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toedter.calendar.JDateChooser;

/**
 * 
 * @author Slayde
 *
 */

public class ihmBI extends JFrame {
//TODO SL les trois ligne tu peux les mettre chacune juste avant de les utiliser 
	String[] periode = { "Année", "Mois", "Jour" };
	String[] area = { "RC", "Etage 1", "Etage 2" };
	// pour celle la j'ai changer, tu ira voir dans la classe ou tu l'a prise et tu
	// prendra la forme que j'ai pcq on a pas le droit de les mettre en dur comme ca
	// faut utiliser l'enum
	String[] sensorType = { "SMOKE", "MOVE", "TEMPERATURE", "WINDOW", "DOOR", "ELEVATOR", "LIGHT", "FIRE", "BADGE",
			"ROUTER" };
	private JList<String> list;
	private JList<String> list1;
	private Font policeLabel;
	private JLabel labelNumber;
	private static List<Sensor> listSensor = new ArrayList<Sensor>();
	private static List<SensorHistorical> listSensorHistorical = new ArrayList<SensorHistorical>();
	private ObjectMapper objectMapper;
	private String requestType;
	private String table;
	private String jsonString;
	private JPanel contentPane;
	private JTextField nbPanne;
	private JTextField tfAlertes;
	private JTextField tfStock;
	private JTextField tfRecherche;
	private JTextField tfTemperature;
	private JTextField searchBar;
	private DefaultListModel<String> listM;
	private DefaultListModel<String> ListModel;
	private JScrollPane sc;
	private JScrollPane sc1;

	private JButton btnRecherche;
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
	private JDialog ratioHommeFemmeJdialog;
	private ChartPanel cPanel;

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
					ihmBI frame = new ihmBI();
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
	public ihmBI() {
		requestAllSensor();
		requestSensorHistorical();
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

		// Label
		// TODO SL les type comme JLabel et autre du les definits en haut de la classe
		// comme dans les autres classes
		JLabel lblTempratureMoyenne = new JLabel("Température moyenne");
		lblTempratureMoyenne.setBounds(54, 544, 139, 16);
		contentPane.add(lblTempratureMoyenne);

		JLabel lblNombreDePannes = new JLabel("Nombre de pannes");
		lblNombreDePannes.setBounds(632, 507, 139, 16);
		contentPane.add(lblNombreDePannes);

		JLabel lblNombreDalertes = new JLabel("Nombre d'alertes");
		lblNombreDalertes.setBounds(719, 426, 147, 22);
		contentPane.add(lblNombreDalertes);

		JLabel lblStock = new JLabel("Nombre de capteurs en stock");
		lblStock.setBounds(340, 277, 147, 16);
		contentPane.add(lblStock);

		JComboBox cbArea = new JComboBox(area);
		cbArea.setBounds(632, 52, 100, 35);
		contentPane.add(cbArea);

		JComboBox cbCapteur = new JComboBox(sensorType);
		cbCapteur.setBounds(632, 6, 100, 35);
		contentPane.add(cbCapteur);

		// Bouton

		btnRecherche = new JButton("Recherche");
		btnRecherche.setBounds(742, 33, 112, 28);
		contentPane.add(btnRecherche);

		btnTemperature = new JButton("Calculer");
		btnTemperature.setBounds(382, 538, 124, 28);
		contentPane.add(btnTemperature);

		btnDeconnexion = new JButton("Deconnexion");
		btnDeconnexion.setBounds(860, 0, 124, 28);
		contentPane.add(btnDeconnexion);

		btnDate = new JButton("GetDate");
		btnDate.setBounds(752, 72, 89, 28);
		contentPane.add(btnDate);

		// Textfield
		nbPanne = new JTextField();
		nbPanne.setBounds(813, 501, 112, 28);
		contentPane.add(nbPanne);
		nbPanne.setColumns(10);

		tfTemperature = new JTextField();
		tfTemperature.setBounds(235, 538, 112, 28);
		contentPane.add(tfTemperature);
		tfTemperature.setColumns(10);

		tfAlertes = new JTextField();
		tfAlertes.setBounds(862, 423, 112, 28);
		tfAlertes.setText(returnNumber().toString());
		contentPane.add(tfAlertes);
		tfAlertes.setColumns(10);

		Object[] monObj = returns();

		tfStock = new JTextField();
		tfStock.setBounds(495, 271, 112, 28);
		tfStock.setText(monObj[2].toString());
		contentPane.add(tfStock);
		tfStock.setColumns(10);

		tfRecherche = new JTextField();
		tfRecherche.setBounds(10, 33, 289, 28);
		contentPane.add(tfRecherche);
		tfRecherche.setColumns(10);

		// List
		listM = new DefaultListModel<String>();
		list = new JList<String>(listM);
//		JList list = new JList();
//		list.setBounds(45, 109, 327, 488);
		sc = new JScrollPane(list);
		sc.setBounds(30, 120, 300, 300);

		listM.addElement("Tout les capteurs");
		for (Sensor sensors : listSensor) {
			listM.addElement(sensors.getIdSensor() + "# " + sensors.getTypeSensor() + " ," + sensors.getSensorState()
					+ " ," + sensors.getIdCommonArea());
		}
		contentPane.add(sc);

		ListModel = new DefaultListModel<String>();
		list1 = new JList<String>(ListModel);
		sc1 = new JScrollPane(list1);
		sc1.setBounds(627, 111, 298, 298);

		ListModel.addElement("Toute les alertes");
		for (SensorHistorical sensorHistorical : listSensorHistorical) {
			ListModel.addElement(sensorHistorical.getAlertState() + "# " + sensorHistorical.getIdHistorical() + " ,"
					+ sensorHistorical.getIdSensor() + " ," + sensorHistorical.getDate());
		}

		contentPane.add(sc1);

		dateChooser = new JDateChooser();
		dateChooser.setBounds(495, 6, 112, 28);
		contentPane.add(dateChooser);

		dateChooser_1 = new JDateChooser();
		dateChooser_1.setBounds(495, 50, 112, 28);
		contentPane.add(dateChooser_1);

		JLabel lblNewLabel = new JLabel("Data Range from");
		lblNewLabel.setBounds(316, 18, 97, 16);
		contentPane.add(lblNewLabel);

		JLabel lblTo = new JLabel("to");
		lblTo.setBounds(309, 49, 28, 16);
		contentPane.add(lblTo);

		tfDate = new JTextField();
		tfDate.setBounds(396, 6, 89, 28);
		contentPane.add(tfDate);
		tfDate.setColumns(10);

		tfDate1 = new JTextField();
		tfDate1.setBounds(396, 50, 89, 28);
		contentPane.add(tfDate1);
		tfDate1.setColumns(10);
		// contentPane.add(list);

		//
		ratioHommeFemmeJdialog = new JDialog();

		DefaultPieDataset pieDataset = new DefaultPieDataset();
		pieDataset.setValue("Valeur1", (Number) monObj[1]);
		pieDataset.setValue("Valeur2", new Integer(10));
		pieDataset.setValue("Valeur3", new Integer(50));
		pieDataset.setValue("Valeur4", new Integer(5));

		JFreeChart pieChart = ChartFactory.createPieChart("Test camembert", pieDataset, true, true, true);
		ChartPanel cPanel = new ChartPanel(pieChart);
		contentPane.add(cPanel);

		JButton btnGraphique = new JButton("VisualisationGraphique");
		btnGraphique.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ratioHommeFemmeJdialog.getContentPane().add(cPanel, CENTER);

				ratioHommeFemmeJdialog.pack();
				ratioHommeFemmeJdialog.setVisible(true);

			}
		});
		btnGraphique.setBounds(386, 319, 186, 28);
		contentPane.add(btnGraphique);

		ChartPanel myChart = new ChartPanel(pieChart);
		myChart.setBounds(366, 89, 223, 182);
		contentPane.add(myChart);
		myChart.setMouseWheelEnabled(true);

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
				tfDate1.setText(df.format(dateChooser_1.getDate()));
				
				
///////////////////////////////////////////////////////				
				ListModel.removeAllElements();
				for (SensorHistorical sensorHistorical1 : listSensorHistorical) {
					
					
					
					if (tfDate.getText()!=null && tfDate1.getText()!=null) {
						
						if (sensorHistorical1.getDate().after(dateChooser.getDate())
							&& sensorHistorical1.getDate().before((dateChooser_1.getDate()))) {
						ListModel.addElement(
								sensorHistorical1.getAlertState() + "# " + sensorHistorical1.getIdHistorical() + " ,"
										+ sensorHistorical1.getIdSensor() + " ," + sensorHistorical1.getDate());

						}
					} else if (tfDate.getText()==null && tfDate1.getText()==null) {
						for (SensorHistorical sensorHistorical : listSensorHistorical) {
							ListModel.addElement(sensorHistorical.getAlertState() + "# " + sensorHistorical.getIdHistorical() + " ,"
									 + " ," + sensorHistorical.getDate());
						}
						}
					}
				}
			
		});

	}

	// TODO SL les noms des methodes met des nom explicite genre getAllSensor et
	// getAllSensorHistorical
	private void requestAllSensor() {

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

	private void requestSensorHistorical() {

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

		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON Sensor data " + e1.getClass().getCanonicalName());
		}

	}

	private void requestSensorHistoricalFIND() {

		requestType = "READ ALL";
		table = "SensorHistorical";
		objectMapper = new ObjectMapper();
		try {
			jsonString = "SELECT * FROM SensorHistorical WHERE idHistorical >= date_chooser AND date_chooser1";
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			SensorHistorical[] sensorHistorical = objectMapper.readValue(jsonString, SensorHistorical[].class);
			listSensorHistorical = Arrays.asList(sensorHistorical);
			logger.log(Level.INFO, "Find SensorHistorical data succed");

		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON Sensor data " + e1.getClass().getCanonicalName());
		}

	}

	public String returnNumber() {
		int nb = 0;
		if (!listSensorHistorical.isEmpty()) {
			for (SensorHistorical sensorHistorical : listSensorHistorical) {
				nb++;

			}

		}

		return String.valueOf(nb);

	}

//TODO SL donne des noms explicite pour les objets pcq nob et nunu est pas comprehensible a la rigueur x et y c'est plus explicite que ca
	public static Object[] returns() {

		int nob = 0;
		int nunu = 0;
		int total = 0;
//TODO SL supprime les espaces entre les truc tu perds au moment de la comprehension met plutot des 
		////////////////////////////////////////////////////////////////////////////////////////////
		//pour separer des methodes si il faut
		for (Sensor sensor : listSensor) {
			total++;

			if (sensor.getIdSensor() == 0)
				nob++;

			else
				nunu++;

		}

		return new Object[] { nunu, nob, total };

	}
}
