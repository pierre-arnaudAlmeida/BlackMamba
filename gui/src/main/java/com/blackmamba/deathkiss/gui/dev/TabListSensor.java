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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.blackmamba.deathkiss.entity.CommonArea;
import com.blackmamba.deathkiss.entity.Sensor;
import com.blackmamba.deathkiss.gui.dev.ClientSocket;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TabListSensor extends JPanel {

	private static final long serialVersionUID = 1L;
	private int idemployee;
	private int index;
	private int idSensor;
	private String requestType;
	private String table;
	private String jsonString;
	private JPanel bar;
	private JLabel labelIdEmployee;
	private JLabel idEmployee;
	private Font police;
	private JButton disconnection;
	private JButton checkSensor;
	private JButton newCommonArea;
	private CommonArea commonArea;
	private JTable tableau;
	private DefaultTableModel tableModel;
	private List<Sensor> listSensor = new ArrayList<Sensor>();
	private static final Logger logger = LogManager.getLogger(TabListSensor.class);
	private Object[][] listM;

	public TabListSensor() {
	}

	public TabListSensor(CommonArea area, int idemployee, String title) {
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
		police = new Font("Arial", Font.BOLD, 16);
		labelIdEmployee.setForeground(Color.WHITE);
		labelIdEmployee.setFont(police);
		bar.add(labelIdEmployee, BorderLayout.WEST);

		/**
		 * Definition of the label idEmployee on header bar
		 */
		idEmployee = new JLabel();
		idEmployee.setText("" + this.idemployee + "");
		idEmployee.setFont(police);
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

		///////////////////////// TABLE/////////////////////////////////////////////////
		requestType = "FIND ALL";
		commonArea = new CommonArea();
		Sensor sensor = new Sensor();
		table = "Sensor";
		ObjectMapper readMapper = new ObjectMapper();
		sensor.setIdCommonArea(area.getIdCommonArea());
		try {
			jsonString = readMapper.writeValueAsString(sensor);
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			Sensor[] listSensors = readMapper.readValue(jsonString, Sensor[].class);
			listSensor = Arrays.asList(listSensors);
			commonArea.setListSensor(listSensor);
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
		}

		Object[] titles = { "Identifiant Capteur", "Type", "Etat", "Nom Partie Commune" };
		tableModel = new DefaultTableModel(titles, 0);
		tableau = new JTable(tableModel);

		tableModel.addRow(titles);

		for (Sensor sensors : listSensor) {
			Object[] newRow = { Integer.toString(sensors.getIdSensor()), sensors.getTypeSensor(),
					sensors.getSensorState(), area.getNameCommonArea() };
			tableModel.addRow(newRow);
		}
		// TODO ScrollPane au tableau
		//faire en premier tabSensor pour en ajouter plein

		tableau.setBounds((int) getToolkit().getScreenSize().getWidth() * 3 / 10,
				(int) getToolkit().getScreenSize().getHeight() * 2 / 10,
				(int) getToolkit().getScreenSize().getWidth() * 1 / 2,
				(int) getToolkit().getScreenSize().getHeight() * 1 / 2);

		this.add(tableau);

		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (index != 0) {
					tableau.setValueAt(index, index, 0);
				}
				index = tableau.getSelectedRow();
				if (index != 0) {
					idSensor = Integer.parseInt(tableau.getValueAt(index, 0).toString());
					System.out.println(idSensor);
				}
			}
		};
		tableau.addMouseListener(mouseListener);

		///////////////////////// BUTTON/////////////////////////////////////////////////
		/**
		 * Definition of Button CheckSensor
		 */
		checkSensor = new JButton("Afficher le capteur");
		checkSensor.setBounds(((int) getToolkit().getScreenSize().getWidth() * 5 / 10),
				(int) getToolkit().getScreenSize().getHeight() * 15 / 20, 150, 40);
		this.add(checkSensor);
		checkSensor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (index == 0) {
					JOptionPane.showMessageDialog(null, "Veuillez selectionner un capteur", "Erreur",
							JOptionPane.ERROR_MESSAGE);
				} else {
					JTabbedPane tab = new JTabbedPane();
					tab = Window.getTab();

					tab.remove(2);
					TabSensor tabSensor = new TabSensor(Color.GRAY, idemployee, "Onglet Capteurs", index);
					tab.add(tabSensor, 2);
					tab.setTitleAt(2, "Onglet Capteurs");
					Window.goToTab(2);
				}
			}
		});

		/**
		 * Definition of Button newCommonArea
		 */
		newCommonArea = new JButton("Nouvelle Partie Commune");
		newCommonArea.setBounds(((int) getToolkit().getScreenSize().getWidth() * 3 / 10),
				(int) getToolkit().getScreenSize().getHeight() * 15 / 20, 200, 40);
		this.add(newCommonArea);
		newCommonArea.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JTabbedPane tab = new JTabbedPane();
				tab = Window.getTab();
				tab.remove(6);
				Window.goToTab(1);
			}
		});

		/**
		 * Diferent parameters of the window
		 */
		this.setLayout(new BorderLayout());
		this.add(bar, BorderLayout.NORTH);
		this.setBackground(Color.GRAY);

	}

	// TODO
	//mettre une image
	// ++ une barre de recherche dans la list des infos du tableau donc avoir deux
	// tableau un avec toutes les infos et un autre avec les infos de la recherche
}