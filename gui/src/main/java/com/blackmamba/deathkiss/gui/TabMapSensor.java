package com.blackmamba.deathkiss.gui;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.sql.Time;

import javax.swing.JPanel;

import org.apache.logging.log4j.Level;

import com.blackmamba.deathkiss.entity.CommonArea;
import com.blackmamba.deathkiss.entity.Floor;
import com.blackmamba.deathkiss.entity.Alert;
import com.blackmamba.deathkiss.entity.Sensor;
import com.blackmamba.deathkiss.launcher.ClientSocket;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Raymond
 *
 */

public class TabMapSensor extends JPanel implements MouseListener {

	private int index;
	private int idEmployee;
	private Sensor sensor;
	private JTextField textInputIdSensor;
	private Font police;
	private JButton disconnection;
	private JButton configureSensor;
	private JTabbedPane tab;
	private JPanel bar;
	private JLabel labelIdEmployee;
	private JLabel labelHeadList;
	private JTextField textInputAlertState;
	private JTextField textInputThresholdMin;
	private JTextField textInputThresholdMax;

	private JButton switchButton;

	private TabSensor tabSensor;
	private String requestType;
	private String table;
	private String jsonString;
	private CommonArea commonArea;
	private ObjectMapper objectMapper;
	private Thread threadMapSensor;
	private JScrollPane sc;

	private BufferedImage img = null;
	private BufferedImage img1 = null;
	private BufferedImage buffer = null;
	private BufferedImage buffer1 = null;
	private Point p = null;

	private JList<String> list;
	private JComboBox<String> textFloor;
	private JComboBox<String> textInputNameCommonArea;
	private JComboBox<String> textInputTypeSensor;
	private JComboBox<String> textInputSensitivity;
	private JComboBox<Integer> textInputHourStartActivity;
	private JComboBox<Integer> textInputMinuteStartActivity;
	private JComboBox<Integer> textInputHourEndActivity;
	private JComboBox<Integer> textInputMinuteEndActivity;

	private DefaultListModel<String> listM;
	private List<Sensor> listSearchSensor = new ArrayList<Sensor>();
	private List<Sensor> listSensor = new ArrayList<Sensor>();
	private List<Alert> listAlert = new ArrayList<Alert>();
	private List<SurfacePolygon> surfacePolygon = new ArrayList<SurfacePolygon>();

	private ResourceBundle rsParameters = ResourceBundle.getBundle("parameters");
	private List<CommonArea> listCommonArea = new ArrayList<CommonArea>();
	private static final long serialVersionUID = 7348020021300445245L;

	private static final Logger logger = LogManager.getLogger(TabMapSensor.class);
	private static final Rectangle rectangle1 = new Rectangle(400, 204, 147, 66);
	private static final Rectangle rectangle2 = new Rectangle(400, 272, 147, 202);
	private static final Rectangle rectangle3 = new Rectangle(400, 476, 147, 64);
	private static final Rectangle rectangle4 = new Rectangle(543, 85, 430, 83);
	private static final Rectangle rectangle5 = new Rectangle(400, 170, 718, 33);
	private static final Rectangle rectangle6 = new Rectangle(549, 205, 64, 334);
	private static final Rectangle rectangle7 = new Rectangle(400, 541, 718, 35);
	private static final Rectangle rectangle8 = new Rectangle(547, 577, 428, 84);
	private static final Rectangle rectangle9 = new Rectangle(616, 288, 502, 43);
	private static final Rectangle rectangle10 = new Rectangle(616, 409, 502, 46);
	private static final Rectangle rectangle11 = new Rectangle(905, 205, 213, 80);
	private static final Rectangle rectangle12 = new Rectangle(905, 458, 213, 81);
	private static final Rectangle rectangle13 = new Rectangle(1120, 170, 176, 407);
	private static final Rectangle elevatorA = new Rectangle(616, 331, 48, 29);
	private static final Rectangle elevatorB = new Rectangle(616, 380, 48, 27);

	/**
	 * Constructor
	 */
	public TabMapSensor() {
	}

	/**
	 * Constructor
	 * 
	 * @param color
	 * @param idEmployee
	 * @param title
	 * @param idSensor
	 */
	public TabMapSensor(Color color, int idEmployee, String title, int idSensor) {
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
						Thread.sleep(Integer.parseInt(rsParameters.getString("time_threadSleep")));
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
		 * Definition of the List CommonArea
		 */
		textInputNameCommonArea = new JComboBox<String>();

		///////////////////////// FROM LIST SENSOR//////////////////////////////////////
		/**
		 * if the is before on TabListSensor they send a idSensor and if they are
		 * different to 0 they send a request to get informations about this Sensor
		 */
		if (idSensor != 0) {
			requestType = "READ";
			sensor = new Sensor();
			table = "Sensor";
			ObjectMapper readMapper = new ObjectMapper();
			sensor.setIdSensor(idSensor);
			try {
				jsonString = readMapper.writeValueAsString(sensor);
				new ClientSocket(requestType, jsonString, table);
				jsonString = ClientSocket.getJson();
				sensor = readMapper.readValue(jsonString, Sensor.class);
				logger.log(Level.DEBUG, "Find Sensor datas succed");
			} catch (Exception e1) {
				logger.log(Level.WARN, "Impossible to parse in JSON Sensor datas " + e1.getClass().getCanonicalName());
			}
		}

		///////////////////////// LIST SENSOR///////////////////////////////////////////
		listM = new DefaultListModel<String>();
		list = new JList<String>(listM);
		updateListSensor();

		/**
		 * Add a scrollBar on list
		 */
		sc = new JScrollPane(list);
		sc.setBounds(30, 170, 300, ((int) getToolkit().getScreenSize().getHeight() - 350));
		this.add(sc);

		/**
		 * when we pressed a line in the list they will send a request to get all the
		 * information about the Sensor selected to be displayed on the textField
		 */
		index = -9999;
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				index = list.locationToIndex(e.getPoint());
				String substring = listM.getElementAt(index).toString();
				int position = substring.indexOf("#");
				if (position > -1) {
					String id = substring.substring(0, position);
					/**
					 * Find the Sensor by the id get on list
					 */
					requestType = "READ";
					sensor = new Sensor();
					table = "Sensor";
					sensor.setIdSensor(Integer.parseInt(id));
					try {
						jsonString = objectMapper.writeValueAsString(sensor);
						new ClientSocket(requestType, jsonString, table);
						jsonString = ClientSocket.getJson();
						sensor = objectMapper.readValue(jsonString, Sensor.class);
					} catch (IOException e1) {
						logger.log(Level.WARN, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
					}
					textInputIdSensor.setText(Integer.toString(sensor.getIdSensor()));
					textInputAlertState.setText(sensor.getAlertState().name());
					textInputSensitivity.setSelectedItem(sensor.getSensitivity().name());
					textInputTypeSensor.setSelectedItem(sensor.getTypeSensor().name());
					textInputThresholdMin.setText(Integer.toString(sensor.getThresholdMin()));
					textInputThresholdMax.setText(Integer.toString(sensor.getThresholdMax()));
					convertActivityTime(sensor.getStartActivity(), sensor.getEndActivity());
					if (sensor.getSensorState()) {
						switchButton.setText("ON");
						switchButton.setBackground(Color.GREEN);
					} else {
						switchButton.setText("OFF");
						switchButton.setBackground(Color.RED);
					}

					/**
					 * Find the CommonAreaName by the idCommonArea get on list
					 */
					requestType = "READ";
					commonArea = new CommonArea();
					table = "CommonArea";
					ObjectMapper readMapper = new ObjectMapper();
					commonArea.setIdCommonArea(sensor.getIdCommonArea());
					try {
						jsonString = readMapper.writeValueAsString(commonArea);
						new ClientSocket(requestType, jsonString, table);
						jsonString = ClientSocket.getJson();
						commonArea = readMapper.readValue(jsonString, CommonArea.class);
					} catch (IOException e1) {
						logger.log(Level.WARN, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
					}
					String str = commonArea.getNameCommonArea() + " #" + sensor.getIdCommonArea();
					for (int i = 0; i < textInputNameCommonArea.getItemCount(); i++) {
						if (textInputNameCommonArea.getItemAt(i).toString().contains(str)) {
							textInputNameCommonArea.setSelectedIndex(i);
						}
					}
				}
			}
		};
		list.addMouseListener(mouseListener);

		/**
		 * Definition of label HeadList
		 */
		labelHeadList = new JLabel("ID /Type /State /ID common area /  :");
		labelHeadList.setBounds(40, 140, 300, 30);
		labelHeadList.setFont(police);
		this.add(labelHeadList);

		///////////////////////// JComboBox/////////////////////////////////////////////
		/**
		 * List of floor
		 */
		textFloor = new JComboBox<String>();
		textFloor.setBounds(40, 90, 140, 30);
		this.add(textFloor);
		textFloor.addItem("-");
		for (Floor listFloor : Floor.values()) {
			textFloor.addItem(listFloor.name());
		}
		textFloor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				if () {
//					
//				}
			}
		});

		///////////////////////// FRAME/////////////////////////////////////////////////
		/**
		 * Different parameters of the window
		 */
		this.setLayout(new BorderLayout());
		this.add(bar, BorderLayout.NORTH);
		this.setBackground(color);

		///////////////////////// BUTTON////////////////////////////////////////////////
		/**
		 * Definition of Button CheckSensor
		 */
//		configureSensor = new JButton("Configure Sensor");
//		configureSensor.setBounds(30, (int) getToolkit().getScreenSize().getHeight() * 16 / 20, 150, 40);
//		this.add(configureSensor);
//		configureSensor.addActionListener(new ActionListener() {
//			/**
//			 * If we pressed the Configure Sensor Button we go to the TabSensor to watch the
//			 * informations about this sensor
//			 */
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				
//				if (index == 0 && index==-9999)  {
//					JOptionPane.showMessageDialog(null, "Please select an sensor", "Information",
//							JOptionPane.INFORMATION_MESSAGE);
//				} 
//				else {
//					tab = new JTabbedPane();
//					tab = Frame.getTab();
//
//					tab.remove(2);
//					tabSensor = new TabSensor(Color.GRAY, idEmployee, "Tab Sensors", index);
//					tab.add(tabSensor, 2);
//					tab.setTitleAt(2, "Tab Sensors");
//					Frame.goToTab(2);
//				}
//			}
//		});

		///////////////////////// PANEL/////////////////////////////////////////////////

//		JPanel panel = new JPanel();
//		panel.setBackground(Color.GRAY);
//		add(panel);

		///////////////////////// JScrollPane///////////////////////////////////////////

//		JScrollPane scroll = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//		scroll.setBounds(0, 0, 930, 610);
//		this.add(scroll);

		///////////////////////// IMAGE/////////////////////////////////////////////////

		try {
			img = ImageIO.read(getClass().getClassLoader().getResource("Floor0.jpg"));
			img1 = ImageIO.read(getClass().getClassLoader().getResource("Floor1.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		this.buffer = img;
		this.buffer1 = img1;

		JLabel image = new JLabel(new ImageIcon(img));
		JLabel image1 = new JLabel(new ImageIcon(img1));

//		panel.setLayout(new BorderLayout());
//		panel.add(image1, BorderLayout.EAST);

	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		// Draw image
		g2.drawImage(buffer, 400, 85, 900, 580, this);
		// g2.drawImage(buffer1, 700, 400, buffer1.getWidth(), buffer1.getHeight(),
		// this);

		g2.setColor(Color.GREEN);
		// Draw rectangle
		g2.drawRect(rectangle1.x, rectangle1.y, rectangle1.width, rectangle1.height);
		g2.drawRect(rectangle2.x, rectangle2.y, rectangle2.width, rectangle2.height);
		g2.drawRect(rectangle3.x, rectangle3.y, rectangle3.width, rectangle3.height);
		g2.drawRect(rectangle4.x, rectangle4.y, rectangle4.width, rectangle4.height);
		g2.drawRect(rectangle5.x, rectangle5.y, rectangle5.width, rectangle5.height);
		g2.drawRect(rectangle6.x, rectangle6.y, rectangle6.width, rectangle6.height);
		g2.drawRect(rectangle7.x, rectangle7.y, rectangle7.width, rectangle7.height);
		g2.drawRect(rectangle8.x, rectangle8.y, rectangle8.width, rectangle8.height);
		g2.drawRect(rectangle9.x, rectangle9.y, rectangle9.width, rectangle9.height);
		g2.drawRect(rectangle10.x, rectangle10.y, rectangle10.width, rectangle10.height);
		g2.drawRect(rectangle11.x, rectangle11.y, rectangle11.width, rectangle11.height);
		g2.drawRect(rectangle12.x, rectangle12.y, rectangle12.width, rectangle12.height);
		g2.drawRect(rectangle13.x, rectangle13.y, rectangle13.width, rectangle13.height);
		g2.drawRect(elevatorA.x, elevatorA.y, elevatorA.width, elevatorA.height);
		g2.drawRect(elevatorB.x, elevatorB.y, elevatorB.width, elevatorB.height);
	}

	private void testLocation(Point mouse, Rectangle commonArea, String text) {
		// if the mouse if here
		if (commonArea.contains(mouse))
			System.out.println(text + " - image");
		else
			System.out.println(text + " - !image");
	}

	public void mouseClicked(MouseEvent e) {
		// recovering the position of the mouse
		p = e.getPoint();
	}

	/**
	 * 
	 * @param sensor
	 */
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
			logger.log(Level.DEBUG, "Find Sensor data succed");
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
			logger.log(Level.DEBUG, "Find Sensor data succed");
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
	 * 
	 * @param startActivity
	 * @param endActivity
	 */
	public void convertActivityTime(Time startActivity, Time endActivity) {
		String sA = startActivity.toString();
		String eA = endActivity.toString();

		int position = sA.indexOf(":");
		textInputHourStartActivity.setSelectedIndex(Integer.parseInt(sA.substring(0, position).trim()));
		textInputMinuteStartActivity
				.setSelectedIndex(Integer.parseInt(sA.substring(position + 1, position + 3).trim()));
		position = eA.indexOf(":");
		textInputHourEndActivity.setSelectedIndex(Integer.parseInt(eA.substring(0, position).trim()));
		textInputMinuteEndActivity.setSelectedIndex(Integer.parseInt(eA.substring(position + 1, position + 3).trim()));
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
