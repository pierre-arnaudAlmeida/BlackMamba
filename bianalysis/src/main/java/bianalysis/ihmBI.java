package bianalysis;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * 
 * @author Slayde
 *
 */

public class ihmBI extends JFrame {
	
	String[] periode = {"Année", "Mois","Jour"};
	String[] area = {"RC", "Etage 1","Etage 2"};
	
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
	private JButton btnRecherche;
	private JButton btnDeconnexion;
	private JButton btnTemperature;
	private static final Logger logger = LogManager.getLogger(TabSensor.class);
	private JLabel lblResultat;
	private JLabel lbl;
	private JTextField tfNombreCapteurTemp;
	
	

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
		request();
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
		cbPeriode.setBounds(404, 30, 100, 35);
		contentPane.add(cbPeriode);
		
		JComboBox cbArea = new JComboBox(area);
		cbArea.setBounds(516, 30, 100, 35);
		contentPane.add(cbArea);
		
		// Bouton
		
		btnRecherche = new JButton("Recherche");
		btnRecherche.setBounds(643, 33, 139, 28);
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
		contentPane.add(tfAlertes);
		tfAlertes.setColumns(10);
		

		
		tfStock = new JTextField();
		tfStock.setBounds(583, 240, 112, 28);
		contentPane.add(tfStock);
		tfStock.setColumns(10);
		

		tfRecherche = new JTextField();
		tfRecherche.setBounds(68, 33, 327, 28);
		contentPane.add(tfRecherche);
		tfRecherche.setColumns(10);
		
		tfNombreCapteurTemp = new JTextField();
		tfNombreCapteurTemp.setBounds(583, 295, 112, 28);
		tfNombreCapteurTemp.setText(returnNumber().toString());
		contentPane.add(tfNombreCapteurTemp);
		tfNombreCapteurTemp.setColumns(10);


		// List 
		
		JList list = new JList();
		list.setBounds(68, 105, 327, 488);
		contentPane.add(list);
		

			
	}
	
	private void createEvents() {
		
		btnDeconnexion.addActionListener ( new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			logger.log(Level.INFO, "Application closed, after disconnection");
			System.exit(ABORT);
		}
	});
	

}
	
private void request() {
	
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


