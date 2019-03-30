package com.blackmamba.deathkiss.gui.dev;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.blackmamba.deathkiss.entity.CommonArea;
import com.blackmamba.deathkiss.entity.Sensor;

public class TabListSensor extends JPanel {

	private static final long serialVersionUID = 1L;
	private int idemployee;
	private JPanel bar;
	private JLabel labelIdEmployee;
	private JLabel idEmployee;
	private Font police;
	private JButton disconnection;
	private CommonArea commonArea;
	private List<Sensor> listSensor = new ArrayList<Sensor>();
	private static final Logger logger = LogManager.getLogger(TabListSensor.class);
	private Object[][] listM;

	public TabListSensor() {
	}

	public TabListSensor(CommonArea area, int idemployee) {
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
		
		Object[][] data = {   
			      {"Cysboy", "6boy"},
			      {"BZHHydde", "BZH"},
			      {"IamBow", "BoW"},
			      {"FunMan", "Year"}
			    };
		
		String[] titre = { "a", "b", "c" };
		JTable tableau = new JTable(new DefaultTableModel(data, titre));
		tableau.setBounds(300, 300, 500, 500);
		this.add(tableau);
		/**
		 * Diferent parameters of the window
		 */
		this.setLayout(new BorderLayout());
		this.add(bar, BorderLayout.NORTH);
		this.setBackground(Color.GRAY);

	}
	// TODO un tableau avec une liste de tout les capteur de la common area, les
	// infos des capteurs
	// si on clique sur un capteur ya un bouton qui permet d'acceder au capteur
	// et un bouton qui permet de faire une nouvelle recherche de partie commune et
	// qui ferme donc le tabListsensor
	// et redirige vers la page common Area
	// ++ une barre de recherche dans la list des infos du tableau donc avoir deux
	// tableau un avec toutes les infos et un autre avec les infos de la recherche
}
