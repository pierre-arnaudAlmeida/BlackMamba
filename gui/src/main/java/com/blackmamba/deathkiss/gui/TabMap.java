package com.blackmamba.deathkiss.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.blackmamba.deathkiss.entity.Alert;
import com.blackmamba.deathkiss.entity.CommonArea;
import com.blackmamba.deathkiss.entity.Sensor;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.FlowLayout;
import javax.swing.ScrollPaneConstants;

public class TabMap extends JPanel {

	private int index;
	private int idEmployee;
	private JTextField textInputIdSensor;
	private Font police;
	private Font policeBar;
	private JButton disconnection;
	private JButton validButton;
	private JPanel bar;
	private JPanel search;
	private JPanel canvas;
	private JLabel labelIdEmployee;
	private JLabel labelSearch;
	private JTextField searchBar;

	private List<SurfacePolygon> surfacePolygon = new ArrayList<SurfacePolygon>();
	private List<Sensor> listSensor = new ArrayList<Sensor>();
	private List<Alert> listAlert = new ArrayList<Alert>();
	private JButton switchButton;

	private Sensor sensor;
	private Sensor sensor2;
	private TabSensor tabSensor;
	private String requestType;
	private String table;
	private String jsonString;
	private static final Logger logger = LogManager.getLogger(TabMapSensor.class);
	private static final long serialVersionUID = 7348020021300445245L;
	private CommonArea commonArea;
	private ObjectMapper objectMapper;
	private Thread threadMapSensor;
	private JScrollPane sc;

	private JList<String> list;
	private JComboBox<String> textInputNameCommonArea;
	private JComboBox<String> textInputTypeSensor;
	private DefaultListModel<String> listM;
	private List<Sensor> listSearchSensor = new ArrayList<Sensor>();
	private ResourceBundle rs = ResourceBundle.getBundle("parameters");

	private BufferedImage img = null;
	private BufferedImage img1 = null;
	private BufferedImage buffer = null;
	private BufferedImage buffer1 = null;
	private Point p = null;
	private Image planImage;

	private static final Rectangle polygon1 = new Rectangle(7, 56, 108, 313);
	private static final Rectangle polygon2 = new Rectangle(129, 72, 105, 97);
	private static final Rectangle polygon3 = new Rectangle(240, 171, 346, 45);
	private static final Rectangle polygon4_1 = new Rectangle(591, 171, 282, 182);
	private static final Rectangle polygon4_2 = new Rectangle(733, 71, 140, 99);

	/**
	 * Create the panel.
	 */
	public TabMap(Color color, int idEmployee, String title) {
	}

	public TabMap() {

		bar = new JPanel();
		bar.setBackground(Color.DARK_GRAY);
		bar.setPreferredSize(new Dimension((int) getToolkit().getScreenSize().getWidth(), 80));
		bar.setLayout(new BorderLayout());
		bar.setBorder(BorderFactory.createMatteBorder(20, 100, 20, 100, bar.getBackground()));
		add(bar);
		labelIdEmployee = new JLabel("Login :   " + this.idEmployee + "    ");
		bar.add(labelIdEmployee, BorderLayout.NORTH);
		labelIdEmployee.setForeground(Color.WHITE);
		labelIdEmployee.setFont(police);

///////////////////////// BAR/////////////////////////////////////////////////
		/**
		 * Definition of label Login on header bar
		 */
		police = new Font("Arial", Font.BOLD, 16);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setVgap(100);
		flowLayout.setHgap(200);
		add(panel);
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);

	}

}
