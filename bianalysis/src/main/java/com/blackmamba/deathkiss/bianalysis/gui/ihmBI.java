package com.blackmamba.deathkiss.bianalysis.gui;

import static java.awt.BorderLayout.CENTER;
import static org.jfree.chart.ChartFactory.createBarChart;
import static org.jfree.chart.ChartFactory.createPieChart;
import static org.jfree.chart.plot.PlotOrientation.VERTICAL;
import org.jfree.chart.*; 
import org.jfree.chart.plot.*; 
import org.jfree.data.*;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JDialog;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import com.blackmamba.deathkiss.bianalysis.entity.Sensor;
import com.blackmamba.deathkiss.bianalysis.entity.SensorType;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

/**
 * 
 * @author Slayde
 *
 */

public class ihmBI extends JFrame {
	
	String[] periode = {"Année", "Mois","Jour"};
	String[] area = {"RC", "Etage 1","Etage 2"};
	String[] sensorType = {	"SMOKE", "MOVE","TEMPERATURE", "WINDOW" ,"DOOR", "ELEVATOR","LIGHT","FIRE","BADGE","ROUTER" };
	private JList<String> list;		
	private Font policeLabel;
	private JLabel labelNumber;
	private List<Sensor> listSensor = new ArrayList<Sensor>();
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
	private JScrollPane sc;
	
	private JButton btnRecherche;
	private JButton btnDeconnexion;
	private JButton btnTemperature;
	private JButton btnDate; 
	private JButton btnGraphique;
	private static final Logger logger = LogManager.getLogger(TabSensor.class);
	private JLabel lblResultat;
	private JLabel lbl;
	private JTextField tfNombreCapteurTemp;
	private JDateChooser dateChooser;
	private JDateChooser dateChooser_1;
	private JTextField tfDate;
	private JTextField tfDate1;
	private JDialog ratioHommeFemmeJdialog;
	private ChartPanel cPanel;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ihmBI frame = new ihmBI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ihmBI() {
		requestSensor();
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
		
		JLabel lblTempratureMoyenne = new JLabel("Température moyenne");
		lblTempratureMoyenne.setBounds(402, 111, 139, 16);
		contentPane.add(lblTempratureMoyenne);
		
		JLabel lblNombreDePannes = new JLabel("Nombre de pannes");
		lblNombreDePannes.setBounds(402, 154, 139, 16);
		contentPane.add(lblNombreDePannes);
		
		JLabel lblNombreDalertes = new JLabel("Nombre d'alertes");
		lblNombreDalertes.setBounds(402, 194, 147, 22);
		contentPane.add(lblNombreDalertes);
		
		
		JLabel lblStock = new JLabel("Nombre de capteurs en stock");
		lblStock.setBounds(402, 246, 176, 16);
		contentPane.add(lblStock);
		
		lblResultat = new JLabel("Nombre de capteurs Température");
		lblResultat.setBounds(404, 301, 186, 16);
		lblResultat.setFont(policeLabel);
		contentPane.add(lblResultat);
		
		// Combobox
		
		JComboBox cbPeriode = new JComboBox(periode);
		cbPeriode.setBounds(384, 30, 100, 35);
		contentPane.add(cbPeriode);
		
		JComboBox cbArea = new JComboBox(area);
		cbArea.setBounds(496, 30, 100, 35);
		contentPane.add(cbArea);
		
		JComboBox cbCapteur = new JComboBox(sensorType);
		cbCapteur.setBounds(608, 30, 100, 35);
		contentPane.add(cbCapteur);
		
		// Bouton
		
		btnRecherche = new JButton("Recherche");
		btnRecherche.setBounds(715, 33, 139, 28);
		contentPane.add(btnRecherche);
		
		
		btnTemperature = new JButton("Calculer");
		btnTemperature.setBounds(730, 105, 124, 28);
		contentPane.add(btnTemperature);
		
		btnDeconnexion = new JButton("Deconnexion");
		btnDeconnexion.setBounds(854, 6, 124, 28);
		contentPane.add(btnDeconnexion);

		// Textfield 
		nbPanne = new JTextField();
		nbPanne.setBounds(583, 148, 112, 28);
		contentPane.add(nbPanne);
		nbPanne.setColumns(10);
		

		
		tfTemperature = new JTextField();
		tfTemperature.setBounds(583, 105, 112, 28);
		contentPane.add(tfTemperature);
		tfTemperature.setColumns(10);
		
		tfAlertes = new JTextField();
		tfAlertes.setBounds(583, 194, 112, 28);
		tfAlertes.setText(returnNumber().toString());
		contentPane.add(tfAlertes);
		tfAlertes.setColumns(10);
		

		
		tfStock = new JTextField();
		tfStock.setBounds(583, 240, 112, 28);
		contentPane.add(tfStock);
		tfStock.setColumns(10);
		

		tfRecherche = new JTextField();
		tfRecherche.setBounds(45, 33, 327, 28);
		contentPane.add(tfRecherche);
		tfRecherche.setColumns(10);
		
		tfNombreCapteurTemp = new JTextField();
		tfNombreCapteurTemp.setBounds(583, 295, 112, 28);
		tfNombreCapteurTemp.setText(returnNumber().toString());
		contentPane.add(tfNombreCapteurTemp);
		tfNombreCapteurTemp.setColumns(10);


		// List 
		listM = new DefaultListModel<String>();
		list = new JList<String>(listM);
//		JList list = new JList();
//		list.setBounds(45, 109, 327, 488);
		sc = new JScrollPane(list);
		sc.setBounds(30, 120, 300, 300);
		
		listM.addElement("Tout les capteurs");
		for (Sensor sensors : listSensor) {
			listM.addElement(sensors.getIdSensor() + "# " + sensors.getTypeSensor() + " ,"
					+ sensors.getSensorState() + " ," + sensors.getIdCommonArea());
		}
		contentPane.add(sc);
		
	    dateChooser = new JDateChooser();
		dateChooser.setBounds(542, 346, 106, 28);
		contentPane.add(dateChooser);
		
		dateChooser_1 = new JDateChooser();
		dateChooser_1.setBounds(542, 386, 106, 28);
		contentPane.add(dateChooser_1);
		
		JLabel lblNewLabel = new JLabel("Data Range from");
		lblNewLabel.setBounds(337, 358, 124, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblTo = new JLabel("to");
		lblTo.setBounds(377, 386, 53, 16);
		contentPane.add(lblTo);
		
		tfDate = new JTextField();
		tfDate.setBounds(429, 346, 112, 28);
		contentPane.add(tfDate);
		tfDate.setColumns(10);
		
		btnDate = new JButton("GetDate");
		btnDate.setBounds(698, 346, 89, 28);
		contentPane.add(btnDate);
		
		tfDate1 = new JTextField();
		tfDate1.setBounds(429, 386, 112, 28);
		contentPane.add(tfDate1);
		tfDate1.setColumns(10);
		//contentPane.add(list);
		

		//
		ratioHommeFemmeJdialog = new JDialog();
		
		DefaultPieDataset pieDataset = new DefaultPieDataset(); 
	    pieDataset.setValue("Valeur1", new Integer(27)); 
	    pieDataset.setValue("Valeur2", new Integer(10)); 
	    pieDataset.setValue("Valeur3", new Integer(50)); 
	    pieDataset.setValue("Valeur4", new Integer(5)); 

	    JFreeChart pieChart = ChartFactory.createPieChart("Test camembert", 
	      pieDataset, true, true, true); 
	    ChartPanel cPanel = new ChartPanel(pieChart); 
	    contentPane.add(cPanel); 
	    
	    
	    
	    JButton btnGraphique = new JButton("VisualisationGraphique");
	    btnGraphique.setBounds(730, 194, 186, 28);
	    contentPane.add(btnGraphique);


			
	}
	
	private void createEvents() {
		
		btnDeconnexion.addActionListener ( new ActionListener() {
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
			}
		});
		
	    btnGraphique.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		
				ratioHommeFemmeJdialog.getContentPane().add(cPanel, CENTER);

				ratioHommeFemmeJdialog.pack();
				ratioHommeFemmeJdialog.setVisible(true);
	    		
	    		
	    	}
	    });

}
	
private void requestSensor() {
	
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

public String returnNumber() {
	int nb = 0;
	if (!listSensor.isEmpty()) {
		for (Sensor count : listSensor) {

			
			if (count.getTypeSensor() == SensorType.TEMPERATURE) {
				nb++;
			}
		}

	}

	return String.valueOf(nb);

}
}

