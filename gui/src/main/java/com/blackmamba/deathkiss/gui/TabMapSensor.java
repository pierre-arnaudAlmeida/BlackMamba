package com.blackmamba.deathkiss.gui;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import java.awt.Canvas;
import java.io.IOException;
import java.text.Normalizer;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.apache.logging.log4j.Level;

import com.blackmamba.deathkiss.entity.CommonArea;
import com.blackmamba.deathkiss.entity.Alert;
import com.blackmamba.deathkiss.entity.Sensor;
import com.blackmamba.deathkiss.entity.SensorType;
import com.blackmamba.deathkiss.launcher.ClientSocket;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Raymond
 *
 */

public class TabMapSensor extends JPanel implements MouseListener {

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

	public TabMapSensor() {
	}

	public TabMapSensor(Color color, int idEmployee, String title) {
		this.idEmployee = idEmployee;
		this.addMouseListener(this);

		setThreadMapSensor(new Thread(new Runnable() {
			/**
			 * Loop and update every 30 seconds the list of Sensor with their alert state
			 */
			@Override
			public void run() {
				while (true) {
					// tabSensor.actualizationListSensor();
					tabSensor.updateSensorSelected();
					try {
						Thread.sleep(Integer.parseInt(rs.getString("time_threadSleep")));
					} catch (InterruptedException e) {
						logger.log(Level.INFO, "Impossible to sleep the thread " + e.getClass().getCanonicalName());
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
		 * Definition of label Login on header bar
		 */
		labelIdEmployee = new JLabel("Login :   " + this.idEmployee + "    ");
		police = new Font("Arial", Font.BOLD, 16);
		labelIdEmployee.setForeground(Color.WHITE);
		labelIdEmployee.setFont(police);
		bar.add(labelIdEmployee, BorderLayout.WEST);

		/**
		 * Definition of the button and the different action after pressed the button
		 */
		disconnection = new JButton("Disconnect");
		bar.add(disconnection, BorderLayout.EAST);
		disconnection.addActionListener(new ActionListener() {
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
			 * send a request to search with the id written in the research the IdSensor or
			 * the IdCommonArea of the Sensor. And add all the results on a list to display
			 * But if there is letter and numerics they will send a request to return all
			 * the Sensor when the type of Sensor contains the research
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				sensor2 = new Sensor();
				String searchReceived = searchBar.getText().trim();
				if (!searchReceived.equals("")) {
					/**
					 * If the research is just numerics they find first the IdSensor
					 */
					if (searchReceived.matches("[0-9]+[0-9]*")) {
						requestType = "READ";
						sensor2 = new Sensor();
						table = "Sensor";
						sensor2.setIdSensor(Integer.parseInt(searchReceived));
						try {
							jsonString = objectMapper.writeValueAsString(sensor2);
							new ClientSocket(requestType, jsonString, table);
							jsonString = ClientSocket.getJson();
							sensor2 = objectMapper.readValue(jsonString, Sensor.class);
							logger.log(Level.INFO, "Find Sensor data succed");
						} catch (Exception e1) {
							logger.log(Level.INFO,
									"Impossible to parse in JSON Sensor datas " + e1.getClass().getCanonicalName());
						}
						listM.removeAllElements();
						if (sensor2.getTypeSensor() != null) {
							listM.addElement("Result for sensor with id : " + searchReceived);
							listM.addElement(sensor2.getIdSensor() + "# " + sensor2.getTypeSensor() + " ,"
									+ sensor2.getSensorState() + " ," + sensor2.getIdCommonArea());
						}
						/**
						 * Find Sensor with IdCommonArea
						 */
						searchReceived = Normalizer.normalize(searchReceived, Normalizer.Form.NFD)
								.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
						sensor2.setIdCommonArea(Integer.parseInt(searchReceived));
						findAllSensor(sensor2);
						if (listSearchSensor.size() > 0)
							listM.addElement("Results for sensor in common area : " + searchReceived);
						for (Sensor sensors : listSearchSensor) {
							listM.addElement(sensors.getIdSensor() + "# " + sensors.getTypeSensor() + " ,"
									+ sensors.getSensorState() + " ," + sensors.getIdCommonArea());
						}
					} else {
						/**
						 * If the research contains letter and numerics we match with the types of
						 * Sensor
						 */
						sensor2 = new Sensor();
						for (SensorType type : SensorType.values()) {
							if (type.name().startsWith(searchReceived.toUpperCase())) {
								sensor2.setTypeSensor(type);
							}
						}
						findAllSensor(sensor2);
						listM.removeAllElements();
						if (listSearchSensor.size() > 0)
							listM.addElement("Results for sensor type : " + searchReceived);
						for (Sensor sensors : listSearchSensor) {
							listM.addElement(sensors.getIdSensor() + "# " + sensors.getTypeSensor() + " ,"
									+ sensors.getSensorState() + " ," + sensors.getIdCommonArea());
						}
					}
				} else {
					/**
					 * If the research is empty they display all the Sensors
					 */
					updateListSensor();
				}
				searchBar.setText("");
			}
		});

		/**
		 * Definition of the List CommonArea
		 */
		textInputNameCommonArea = new JComboBox<String>();

		///////////////////////// FRAME/////////////////////////////////////////////////
		/**
		 * Different parameters of the window
		 */
		this.setLayout(new BorderLayout());
		this.add(bar, BorderLayout.NORTH);
		this.setBackground(color);
		
		
		///////////////////////// PANEL/////////////////////////////////////////////////

		
		
		
		///////////////////////// IMAGE/////////////////////////////////////////////////

        try {
			img = ImageIO.read(getClass().getClassLoader().getResource("etage0bis.jpg"));
			img1 = ImageIO.read(getClass().getClassLoader().getResource("etage1bis.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		this.buffer = img;
		this.buffer1 = img1;
        
		JLabel image = new JLabel(new ImageIcon(img));
		JLabel image1 = new JLabel(new ImageIcon(img1));

		
		///////////////////////// JScrollPane///////////////////////////////////////////

		JScrollPane scroll = new JScrollPane();
        scroll.setPreferredSize(new Dimension(40, 60));
        image.add(scroll);
		

		
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		int planWidth = getWidth();
		int panelWidth = this.getWidth();

		// Draw image
		g2.drawImage(buffer, 700, 50, buffer.getWidth(), buffer.getHeight(), this);
		g2.drawImage(buffer1, 700, 400, buffer1.getWidth(), buffer1.getHeight(), this);

		g.setColor(Color.GREEN);
		// Draw rectangle
		g.drawRect(polygon1.x, polygon1.y, polygon1.width, polygon1.height);
		g.drawRect(polygon2.x, polygon2.y, polygon2.width, polygon2.height);
		g.drawRect(polygon3.x, polygon3.y, polygon3.width, polygon3.height);
		g.drawRect(polygon4_1.x, polygon4_1.y, polygon4_1.width, polygon4_1.height);
		g.drawRect(polygon4_2.x, polygon4_2.y, polygon4_2.width, polygon4_2.height);
	}

	private void testLocation(Point mouse, Rectangle commonArea, String text) {
		// if the mouse if here
		if (commonArea.contains(mouse))
			System.out.println(text + " - image");
		else
			System.out.println(text + " - !image");
	}

	private boolean location(Point mouse, Rectangle commonArea) {
		if (commonArea.contains(mouse))
			return true;
		else
			return false;
	}

	public void mouseClicked(MouseEvent e) {
		// recovering the position of the mouse
		p = e.getPoint();
		testLocation(p, polygon1, "mouseClicked - data 1");
		testLocation(p, polygon2, "mouseClicked - data 2");
		testLocation(p, polygon3, "mouseClicked - data 3");
		testLocation(p, polygon4_1, "mouseClicked - data 4_1");
		testLocation(p, polygon4_2, "mouseClicked - data 4_2");

		if (location(p, polygon1) == true) {
			System.out.println("Polygon1");
		}
	}

	public void findAllSensor(Sensor sensor) {
		requestType = "FIND ALL";
		table = "Sensor";
		objectMapper = new ObjectMapper();
		try {
			jsonString = objectMapper.writeValueAsString(sensor);
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			Sensor[] sensors = objectMapper.readValue(jsonString, Sensor[].class);
			listSearchSensor = Arrays.asList(sensors);
			logger.log(Level.INFO, "Find Sensor data succed");
		} catch (Exception e1) {
			logger.log(Level.INFO, "Impossible to parse in JSON Sensor datas " + e1.getClass().getCanonicalName());
		}
	}

	public void updateListSensor() {
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
		listM.removeAllElements();
		listM.addElement("All sensors");
		for (Sensor sens : listSensor) {
			listM.addElement(sens.getIdSensor() + "# " + sens.getTypeSensor() + " ," + sens.getSensorState() + " ,"
					+ sens.getIdCommonArea());
		}
	}

	/**
	 * @return the threadMapSensor
	 */
	public Thread getThreadMapSensor() {
		return threadMapSensor;
	}

	/**
	 * @param threadMapSensor the threadMapSensor to set
	 */
	public void setThreadMapSensor(Thread threadMapSensor) {
		this.threadMapSensor = threadMapSensor;
	}

	/**
	 * @return the surfacePolygon
	 */
	public List<SurfacePolygon> getSurfacePolygon() {
		return surfacePolygon;
	}

	/**
	 * @param surfacePolygon the surfacePolygon to set
	 */
	public void setSurfacePolygon(List<SurfacePolygon> surfacePolygon) {
		this.surfacePolygon = surfacePolygon;
	}

	/**
	 * @return the listSensor
	 */
	public List<Sensor> getListSensor() {
		return listSensor;
	}

	/**
	 * @param listSensor the listSensor to set
	 */
	public void setListSensor(List<Sensor> listSensor) {
		this.listSensor = listSensor;
	}

	/**
	 * @return the listAlert
	 */
	public List<Alert> getListAlert() {
		return listAlert;
	}

	/**
	 * @param listAlert the listAlert to set
	 */
	public void setListAlert(List<Alert> listAlert) {
		this.listAlert = listAlert;
	}

	public void addSensor(Sensor sensor) {
		this.listSensor.add(sensor);
	}

	public void removeSensor(Sensor sensor) {
		this.listSensor.remove(sensor);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
