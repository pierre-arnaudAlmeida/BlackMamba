package com.blackmamba.deathkiss.gui;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
	private List<String> listNameRectangle = new ArrayList<String>();
	private Map<Integer, String> listRectangleCommonArea = new HashMap<Integer, String>();

	private ResourceBundle rsParameters = ResourceBundle.getBundle("parameters");
	private List<CommonArea> listCommonArea = new ArrayList<CommonArea>();
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LogManager.getLogger(TabMapSensor.class);

	private static int x0;
	private static int y0;
	private static int x1;
	private static int y1;

	private static Rectangle kitchenE0;
	private static Rectangle dinningRoomE0;
	private static Rectangle staffRoomE0;
	private static Rectangle relaxationRoomE0;
	private static Rectangle corridorE0D;
	private static Rectangle corridorE0C;
	private static Rectangle corridorE0E;
	private static Rectangle infirmaryE0;
	private static Rectangle corridorE0A;
	private static Rectangle corridorE0B;
	private static Rectangle sittingRoomE0;
	private static Rectangle livingRoomE0;
	private static Rectangle entranceHall;
	private static Rectangle elevatorE0A;
	private static Rectangle elevatorE0B;

	private static Rectangle sittingRoomE1;
	private static Rectangle dinningRoomE1;
	private static Rectangle kitchenE1;
	private static Rectangle corridorE1A;
	private static Rectangle corridorE1B;
	private static Rectangle corridorE1C;
	private static Rectangle corridorE1D;
	private static Rectangle corridorE1E;
	private static Rectangle corridorE1F;
	private static Rectangle relaxationRoomE1;
	private static Rectangle livingRoomE1A;
	private static Rectangle staffRoomE1;
	private static Rectangle livingRoomE1B;
	private static Rectangle infirmaryE1;
	private static Rectangle elevatorE1A;
	private static Rectangle elevatorE1B;

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
					updateListAreas();
					updateListSensor();
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

		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		add(panel);

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
		add(textFloor);
		textFloor.addItem("-");
		for (Floor listFloor : Floor.values()) {
			textFloor.addItem(listFloor.name());
		}
		textFloor.addItemListener(new ItemListener() {
			private BufferedImage buffer;
			private BufferedImage buffer1;

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					textFloor.getSelectedItem();
					
				}
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

		///////////////////////// JScrollPane///////////////////////////////////////////

//		JScrollPane scroll = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//		scroll.setLocation(1000,1000);
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

		JLabel image = new JLabel(new ImageIcon(buffer));
		JLabel image1 = new JLabel(new ImageIcon(buffer1));

		x0 = (int) getToolkit().getScreenSize().getWidth() * 6 / 20;
		y0 = (int) getToolkit().getScreenSize().getHeight() * 3 / 20;

		x1 = (int) getToolkit().getScreenSize().getWidth() * 6 / 20 + 5;
		y1 = (int) getToolkit().getScreenSize().getHeight() * 3 / 20 + 3;

		entranceHall = new Rectangle(x0 + 720, y0 + 85, 176, 407);
		kitchenE0 = new Rectangle(x0, y0 + 119, 147, 66);
		dinningRoomE0 = new Rectangle(x0, y0 + 187, 147, 202);
		staffRoomE0 = new Rectangle(x0, y0 + 391, 147, 64);
		relaxationRoomE0 = new Rectangle(x0 + 143, y0, 430, 83);
		infirmaryE0 = new Rectangle(x0 + 147, y0 + 492, 428, 84);
		corridorE0A = new Rectangle(x0 + 216, y0 + 203, 502, 43);
		corridorE0B = new Rectangle(x0 + 216, y0 + 324, 502, 46);
		corridorE0C = new Rectangle(x0 + 149, y0 + 120, 64, 334);
		corridorE0D = new Rectangle(x0, y0 + 85, 718, 33);
		corridorE0E = new Rectangle(x0, y0 + 456, 718, 35);
		sittingRoomE0 = new Rectangle(x0 + 505, y0 + 120, 213, 80);
		livingRoomE0 = new Rectangle(x0 + 505, y0 + 373, 213, 81);
		elevatorE0A = new Rectangle(x0 + 216, y0 + 246, 48, 29);
		elevatorE0B = new Rectangle(x0 + 216, y0 + 295, 48, 27);

		sittingRoomE1 = new Rectangle(x1, y1, 168, 116);
		dinningRoomE1 = new Rectangle(x1, y1 + 202, 168, 164);
		kitchenE1 = new Rectangle(x1, y1 + 451, 168, 125);
		corridorE1A = new Rectangle(x1 + 170, y1 + 82, 721, 34);
		corridorE1B = new Rectangle(x1 + 248, y1 + 202, 578, 39);
		corridorE1C = new Rectangle(x1 + 248, y1 + 321, 578, 45);
		corridorE1D = new Rectangle(x1 + 170, y1 + 451, 721, 39);
		corridorE1E = new Rectangle(x1 + 172, y1 + 118, 74, 332);
		corridorE1F = new Rectangle(x1 + 829, y1 + 118, 62, 330);
		relaxationRoomE1 = new Rectangle(x1 + 337, y1, 389, 80);
		livingRoomE1A = new Rectangle(x1 + 414, y1 + 118, 247, 82);
		staffRoomE1 = new Rectangle(x1 + 639, y1 + 243, 188, 76);
		livingRoomE1B = new Rectangle(x1 + 413, y1 + 368, 248, 81);
		infirmaryE1 = new Rectangle(x1 + 337, y1 + 492, 389, 84);
		elevatorE1A = new Rectangle(x1 + 248, y1 + 244, 52, 28);
		elevatorE1B = new Rectangle(x1 + 248, y1 + 290, 52, 29);

		listNameRectangle.add("salon");
		listNameRectangle.add("entrancehall");
		listNameRectangle.add(getKitchenE0().toString());
		listNameRectangle.add("dinningroome0");
		listNameRectangle.add("staffroome0");
		listNameRectangle.add("relaxationroome0");
		listNameRectangle.add("infirmarye0");
		listNameRectangle.add("corridore0a");
		listNameRectangle.add("corridore0b");
		listNameRectangle.add("corridore0c");
		listNameRectangle.add("corridore0d");
		listNameRectangle.add("corridore0e");
		listNameRectangle.add("sittingroome0");
		listNameRectangle.add("livingRoome0");
		listNameRectangle.add("elevatore0a");
		listNameRectangle.add("elevatore0b");
		listNameRectangle.add("sittingroome1");
		listNameRectangle.add("dinningroome1");
		listNameRectangle.add("kitchene1");
		listNameRectangle.add("corridore1a");
		listNameRectangle.add("corridore1b");
		listNameRectangle.add("corridore1c");
		listNameRectangle.add("corridore1d");
		listNameRectangle.add("corridore1e");
		listNameRectangle.add("corridore1f");
		listNameRectangle.add("relaxationroome1");
		listNameRectangle.add("livingRoome1a");
		listNameRectangle.add("staffroome1");
		listNameRectangle.add("livingRoome1b");
		listNameRectangle.add("infirmarye1");
		listNameRectangle.add("elevatore1a");
		listNameRectangle.add("elevatore1b");

//		panel.setLayout(new BorderLayout());
//		image.setBounds(x0, y0, 900, 580);
//		panel.add(image);

	}

	public static Rectangle getKitchenE0() {
		return kitchenE0;
	}

	public static void setKitchenE0(Rectangle kitchenE0) {
		TabMapSensor.kitchenE0 = kitchenE0;
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		if (textFloor.getSelectedItem().equals("GROUND_FLOOR")) {
			try {
				img = ImageIO.read(getClass().getClassLoader().getResource("Floor0.jpg"));
				this.buffer = img;
				g2.drawImage(buffer, x0, y0, 900, 580, this);
				
				g2.setColor(Color.GREEN);
				
				//Draw rectangle
				g2.drawRect(kitchenE0.x, kitchenE0.y, kitchenE0.width, kitchenE0.height);
				g2.drawRect(dinningRoomE0.x, dinningRoomE0.y, dinningRoomE0.width, dinningRoomE0.height);
				g2.drawRect(staffRoomE0.x, staffRoomE0.y, staffRoomE0.width, staffRoomE0.height);
				g2.drawRect(relaxationRoomE0.x, relaxationRoomE0.y, relaxationRoomE0.width, relaxationRoomE0.height);
				g2.drawRect(infirmaryE0.x, infirmaryE0.y, infirmaryE0.width, infirmaryE0.height);
				g2.drawRect(corridorE0A.x, corridorE0A.y, corridorE0A.width, corridorE0A.height);
				g2.drawRect(corridorE0B.x, corridorE0B.y, corridorE0B.width, corridorE0B.height);
				g2.drawRect(corridorE0C.x, corridorE0C.y, corridorE0C.width, corridorE0C.height);
				g2.drawRect(corridorE0D.x, corridorE0D.y, corridorE0D.width, corridorE0D.height);
				g2.drawRect(corridorE0E.x, corridorE0E.y, corridorE0E.width, corridorE0E.height);
				g2.drawRect(sittingRoomE0.x, sittingRoomE0.y, sittingRoomE0.width, sittingRoomE0.height);
				g2.drawRect(livingRoomE0.x, livingRoomE0.y, livingRoomE0.width, livingRoomE0.height);
				g2.drawRect(entranceHall.x, entranceHall.y, entranceHall.width, entranceHall.height);
				g2.drawRect(elevatorE0A.x, elevatorE0A.y, elevatorE0A.width, elevatorE0A.height);
				g2.drawRect(elevatorE0B.x, elevatorE0B.y, elevatorE0B.width, elevatorE0B.height);
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if (textFloor.getSelectedItem().equals("FIRST_FLOOR")) {
			try {
				img1 = ImageIO.read(getClass().getClassLoader().getResource("Floor1.jpg"));
				g2.drawImage(buffer1, x0, y0, 900, 580, this);
				
				g2.setColor(Color.GREEN);
				
				g2.drawRect(sittingRoomE1.x, sittingRoomE1.y, sittingRoomE1.width, sittingRoomE1.height);
				g2.drawRect(dinningRoomE1.x, dinningRoomE1.y, dinningRoomE1.width, dinningRoomE1.height);
				g2.drawRect(kitchenE1.x, kitchenE1.y, kitchenE1.width, kitchenE1.height);
				g2.drawRect(corridorE1A.x, corridorE1A.y, corridorE1A.width, corridorE1A.height);
				g2.drawRect(corridorE1B.x, corridorE1B.y, corridorE1B.width, corridorE1B.height);
				g2.drawRect(corridorE1C.x, corridorE1C.y, corridorE1C.width, corridorE1C.height);
				g2.drawRect(corridorE1D.x, corridorE1D.y, corridorE1D.width, corridorE1D.height);
				g2.drawRect(corridorE1E.x, corridorE1E.y, corridorE1E.width, corridorE1E.height);
				g2.drawRect(corridorE1F.x, corridorE1F.y, corridorE1F.width, corridorE1F.height);
				g2.drawRect(relaxationRoomE1.x, relaxationRoomE1.y, relaxationRoomE1.width, relaxationRoomE1.height);
				g2.drawRect(livingRoomE1A.x, livingRoomE1A.y, livingRoomE1A.width, livingRoomE1A.height);
				g2.drawRect(staffRoomE1.x, staffRoomE1.y, staffRoomE1.width, staffRoomE1.height);
				g2.drawRect(livingRoomE1B.x, livingRoomE1B.y, livingRoomE1B.width, livingRoomE1B.height);
				g2.drawRect(infirmaryE1.x, infirmaryE1.y, infirmaryE1.width, infirmaryE1.height);
				g2.drawRect(elevatorE1A.x, elevatorE1A.y, elevatorE1A.width, elevatorE1A.height);
				g2.drawRect(elevatorE1B.x, elevatorE1B.y, elevatorE1B.width, elevatorE1B.height);
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			this.buffer1 = img1;
		}
		
		
		// Draw image
//		g2.drawImage(buffer, x0, y0, 900, 580, this);
//		g2.drawImage(buffer1, x0, y0, 900, 580, this);
//
//		g2.setColor(Color.GREEN);

		// Draw rectangle
//		g2.drawRect(kitchenE0.x, kitchenE0.y, kitchenE0.width, kitchenE0.height);
//		g2.drawRect(dinningRoomE0.x, dinningRoomE0.y, dinningRoomE0.width, dinningRoomE0.height);
//		g2.drawRect(staffRoomE0.x, staffRoomE0.y, staffRoomE0.width, staffRoomE0.height);
//		g2.drawRect(relaxationRoomE0.x, relaxationRoomE0.y, relaxationRoomE0.width, relaxationRoomE0.height);
//		g2.drawRect(infirmaryE0.x, infirmaryE0.y, infirmaryE0.width, infirmaryE0.height);
//		g2.drawRect(corridorE0A.x, corridorE0A.y, corridorE0A.width, corridorE0A.height);
//		g2.drawRect(corridorE0B.x, corridorE0B.y, corridorE0B.width, corridorE0B.height);
//		g2.drawRect(corridorE0C.x, corridorE0C.y, corridorE0C.width, corridorE0C.height);
//		g2.drawRect(corridorE0D.x, corridorE0D.y, corridorE0D.width, corridorE0D.height);
//		g2.drawRect(corridorE0E.x, corridorE0E.y, corridorE0E.width, corridorE0E.height);
//		g2.drawRect(sittingRoomE0.x, sittingRoomE0.y, sittingRoomE0.width, sittingRoomE0.height);
//		g2.drawRect(livingRoomE0.x, livingRoomE0.y, livingRoomE0.width, livingRoomE0.height);
//		g2.drawRect(entranceHall.x, entranceHall.y, entranceHall.width, entranceHall.height);
//		g2.drawRect(elevatorE0A.x, elevatorE0A.y, elevatorE0A.width, elevatorE0A.height);
//		g2.drawRect(elevatorE0B.x, elevatorE0B.y, elevatorE0B.width, elevatorE0B.height);

//		g2.drawRect(sittingRoomE1.x, sittingRoomE1.y, sittingRoomE1.width, sittingRoomE1.height);
//		g2.drawRect(dinningRoomE1.x, dinningRoomE1.y, dinningRoomE1.width, dinningRoomE1.height);
//		g2.drawRect(kitchenE1.x, kitchenE1.y, kitchenE1.width, kitchenE1.height);
//		g2.drawRect(corridorE1A.x, corridorE1A.y, corridorE1A.width, corridorE1A.height);
//		g2.drawRect(corridorE1B.x, corridorE1B.y, corridorE1B.width, corridorE1B.height);
//		g2.drawRect(corridorE1C.x, corridorE1C.y, corridorE1C.width, corridorE1C.height);
//		g2.drawRect(corridorE1D.x, corridorE1D.y, corridorE1D.width, corridorE1D.height);
//		g2.drawRect(corridorE1E.x, corridorE1E.y, corridorE1E.width, corridorE1E.height);
//		g2.drawRect(corridorE1F.x, corridorE1F.y, corridorE1F.width, corridorE1F.height);
//		g2.drawRect(relaxationRoomE1.x, relaxationRoomE1.y, relaxationRoomE1.width, relaxationRoomE1.height);
//		g2.drawRect(livingRoomE1A.x, livingRoomE1A.y, livingRoomE1A.width, livingRoomE1A.height);
//		g2.drawRect(staffRoomE1.x, staffRoomE1.y, staffRoomE1.width, staffRoomE1.height);
//		g2.drawRect(livingRoomE1B.x, livingRoomE1B.y, livingRoomE1B.width, livingRoomE1B.height);
//		g2.drawRect(infirmaryE1.x, infirmaryE1.y, infirmaryE1.width, infirmaryE1.height);
//		g2.drawRect(elevatorE1A.x, elevatorE1A.y, elevatorE1A.width, elevatorE1A.height);
//		g2.drawRect(elevatorE1B.x, elevatorE1B.y, elevatorE1B.width, elevatorE1B.height);

		repaint();
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
		testLocation(p, dinningRoomE1, "mouseClicked - data 1");
		if (nameRectangle(dinningRoomE1) == true) {
			System.out.println("Cool");
		}
	}

	/**
	 * Mouse Entered
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		p = e.getPoint();
	}

	// TODO RK
	// Récupére toute partie commune updateListAreas()
	// faire une liste de toutes les nom des rectangles en les inserant deja en
	// lowercase listNameRectangle()
	// boucle inbriquer
	// premiere boucle sur la list avec les noms des rectangles displayRectangle()
	// Fais boucle sur la liste partie commune
	// Dedans faire if et compare
	// commonArea.getName().lowercas.suppEspace.equals(lisNom[i])
	// alors on ajoute dans une liste d'object ou tableau bidimentionnel, le nom du
	// rectangle et l'id de la partie commune listRectangleCommonArea

	public void displayRectangle() {
		updateListAreas();
		for (int i = 0; i < listNameRectangle.size(); i++) {
			for (CommonArea areas : listCommonArea) {
				if (areas.getNameCommonArea().toLowerCase().trim().equals(listNameRectangle.get(i))) {
					listRectangleCommonArea.put(areas.getIdCommonArea(), listNameRectangle.get(i));
				}
			}
		}
	}

	public boolean nameRectangle(Rectangle commonArea) {
		Boolean bol = null;
		for (int i = 0; i < listRectangleCommonArea.size(); i++) {
			if (listRectangleCommonArea.containsValue(commonArea.toString().toLowerCase())) {
				bol = true;
			}
		}
		return bol;

	}

	public void updateListAreas() {
		requestType = "READ ALL";
		table = "CommonArea";
		objectMapper = new ObjectMapper();
		try {
			jsonString = "READ ALL";
			new ClientSocket(requestType, jsonString, table);
			jsonString = ClientSocket.getJson();
			CommonArea[] commonAreas = objectMapper.readValue(jsonString, CommonArea[].class);
			listCommonArea = Arrays.asList(commonAreas);
			logger.log(Level.DEBUG, "Recuperation of all Common Areas available succed");
		} catch (Exception e1) {
			logger.log(Level.WARN, "Impossible to parse in JSON Common Area datas " + e1.getClass().getCanonicalName());
		}
		String areasAdd = "";
		textInputNameCommonArea.removeAllItems();
		for (CommonArea commonAreas : listCommonArea) {
			if (!areasAdd.contains(commonAreas.getNameCommonArea() + " #" + commonAreas.getIdCommonArea()))
				textInputNameCommonArea.addItem(commonAreas.getNameCommonArea() + " #" + commonAreas.getIdCommonArea());
			areasAdd = areasAdd + commonAreas.getNameCommonArea() + " #" + commonAreas.getIdCommonArea() + ",";
		}
		logger.log(Level.DEBUG, "Convertion of all Common Areas available in a list succed");
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

	/**
	 * 
	 * @return
	 */
	public List<String> getListNameRectangle() {
		return listNameRectangle;
	}

	/**
	 * 
	 * @param listNameRectangle
	 */
	public void setListNameRectangle(List<String> listNameRectangle) {
		this.listNameRectangle = listNameRectangle;
	}

	/**
	 * 
	 * @return
	 */
	public Map<Integer, String> getListRectangleCommonArea() {
		return listRectangleCommonArea;
	}

	/**
	 * 
	 * @param listRectangleCommonArea
	 */
	public void setListRectangleCommonArea(Map<Integer, String> listRectangleCommonArea) {
		this.listRectangleCommonArea = listRectangleCommonArea;
	}

	/**
	 * 
	 * @param sensor
	 */
	public void addSensor(Sensor sensor) {
		this.listSensor.add(sensor);
	}

	/**
	 * 
	 * @param sensor
	 */
	public void removeSensor(Sensor sensor) {
		this.listSensor.remove(sensor);
	}

	/**
	 * Mouse Pressed
	 */
	@Override
	public void mousePressed(MouseEvent e) {
	}

	/**
	 * Mouse Released
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
	}

	/**
	 * Mouse Exited
	 */
	@Override
	public void mouseExited(MouseEvent e) {
	}

}
