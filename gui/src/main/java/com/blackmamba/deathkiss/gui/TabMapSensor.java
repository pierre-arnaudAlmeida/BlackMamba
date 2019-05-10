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
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LogManager.getLogger(TabMapSensor.class);

	private static int x0;
	private static int y0;
	private static int x1;
	private static int y1;
	
	private static Rectangle rectangleE0_1;
	private static Rectangle rectangleE0_2;
	private static Rectangle rectangleE0_3;
	private static Rectangle rectangleE0_4;
	private static Rectangle rectangleE0_5;
	private static Rectangle rectangleE0_6;
	private static Rectangle rectangleE0_7;
	private static Rectangle rectangleE0_8;
	private static Rectangle rectangleE0_9;
	private static Rectangle rectangleE0_10;
	private static Rectangle rectangleE0_11;
	private static Rectangle rectangleE0_12;
	private static Rectangle rectangleE0_13;
	private static Rectangle elevatorE0_A;
	private static Rectangle elevatorE0_B;
	
	private static Rectangle rectangleE1_1;
	private static Rectangle rectangleE1_2;
	private static Rectangle rectangleE1_3;
	private static Rectangle corridorE1_A;
	private static Rectangle corridorE1_B;
	private static Rectangle corridorE1_C;
	private static Rectangle corridorE1_D;
	private static Rectangle corridorE1_E;
	private static Rectangle corridorE1_F;
	private static Rectangle rectangleE1_10;
	private static Rectangle rectangleE1_11;
	private static Rectangle rectangleE1_12;
	private static Rectangle rectangleE1_13;
	private static Rectangle rectangleE1_14;
	private static Rectangle elevatorE1_A;
	private static Rectangle elevatorE1_B;

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
	
		x0 = (int) getToolkit().getScreenSize().getWidth() * 6/20;
		y0 = (int) getToolkit().getScreenSize().getHeight() * 3/20;
		
		x1 = (int) getToolkit().getScreenSize().getWidth() * 6/20 + 5;
		y1 = (int) getToolkit().getScreenSize().getHeight() * 3/20 + 3;
		
		rectangleE0_1 = new Rectangle(x0, y0+119, 147, 66);
		rectangleE0_2 = new Rectangle(x0,  y0+187, 147, 202);
		rectangleE0_3 = new Rectangle(x0,  y0+391, 147, 64);
		rectangleE0_4 = new Rectangle(x0+143, y0, 430, 83);
		rectangleE0_5 = new Rectangle(x0,  y0+85, 718, 33);
		rectangleE0_6 = new Rectangle(x0+149,  y0+120, 64, 334);
		rectangleE0_7 = new Rectangle(x0,  y0+456, 718, 35);
		rectangleE0_8 = new Rectangle(x0+147,  y0+492, 428, 84);
		rectangleE0_9 = new Rectangle(x0+216,  y0+203, 502, 43);
		rectangleE0_10 = new Rectangle(x0+216,  y0+324, 502, 46);
		rectangleE0_11 = new Rectangle(x0+505,  y0+120, 213, 80);
		rectangleE0_12 = new Rectangle(x0+505,  y0+373, 213, 81);
		rectangleE0_13 = new Rectangle(x0+720,  y0+85, 176, 407);
		elevatorE0_A = new Rectangle(x0+216,  y0+246, 48, 29);
		elevatorE0_B = new Rectangle(x0+216,  y0+295, 48, 27);
		
		rectangleE1_1 = new Rectangle(x1,y1, 168, 116);
		rectangleE1_2 = new Rectangle(x1,y1+202, 168, 164);
		rectangleE1_3 = new Rectangle(x1,y1+451, 168, 125);
		corridorE1_A = new Rectangle(x1+170,y1+82, 721, 34);
		corridorE1_B = new Rectangle(x1+248,y1+202, 578, 39);
		corridorE1_C = new Rectangle(x1+248,y1+321, 578, 45);
		corridorE1_D = new Rectangle(x1+170,y1+451, 721, 39);
		corridorE1_E = new Rectangle(x1+170,y1+118, 74, 332);
		corridorE1_F = new Rectangle(x1+829,y1+118, 62, 330);
		rectangleE1_10 = new Rectangle(x1+337,y1, 389, 80);
		rectangleE1_11 = new Rectangle(x1+414,y1+118, 247, 82);
		rectangleE1_12 = new Rectangle(x1+639,y1+243, 188, 76);
		rectangleE1_13 = new Rectangle(x1+413,y1+368, 248, 81);
		rectangleE1_14 = new Rectangle(x1+337,y1+492, 389, 84);
		elevatorE1_A = new Rectangle(x1+248,y1+244, 52, 28);
		elevatorE1_B = new Rectangle(x1+248,y1+290, 52, 29);
		
//		panel.setLayout(new BorderLayout());
//		image.setBounds(x0, y0, 900, 580);
//		panel.add(image);

	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		// Draw image
//		g2.drawImage(buffer, 400, 85, 900, 580, this);
//		g2.drawImage(buffer, x0, y0, 900, 580, this);
		g2.drawImage(buffer1, x1, y1 , 900, 580, this);

		g2.setColor(Color.GREEN);
		// Draw rectangle
		
//		g2.drawRect(rectangleE0_1.x, rectangleE0_1.y, rectangleE0_1.width, rectangleE0_1.height);
//		g2.drawRect(rectangleE0_2.x, rectangleE0_2.y, rectangleE0_2.width, rectangleE0_2.height);
//		g2.drawRect(rectangleE0_3.x, rectangleE0_3.y, rectangleE0_3.width, rectangleE0_3.height);
//		g2.drawRect(rectangleE0_4.x, rectangleE0_4.y, rectangleE0_4.width, rectangleE0_4.height);
//		g2.drawRect(rectangleE0_5.x, rectangleE0_5.y, rectangleE0_5.width, rectangleE0_5.height);
//		g2.drawRect(rectangleE0_6.x, rectangleE0_6.y, rectangleE0_6.width, rectangleE0_6.height);
//		g2.drawRect(rectangleE0_7.x, rectangleE0_7.y, rectangleE0_7.width, rectangleE0_7.height);
//		g2.drawRect(rectangleE0_8.x, rectangleE0_8.y, rectangleE0_8.width, rectangleE0_8.height);
//		g2.drawRect(rectangleE0_9.x, rectangleE0_9.y, rectangleE0_9.width, rectangleE0_9.height);
//		g2.drawRect(rectangleE0_10.x, rectangleE0_10.y, rectangleE0_10.width, rectangleE0_10.height);
//		g2.drawRect(rectangleE0_11.x, rectangleE0_11.y, rectangleE0_11.width, rectangleE0_11.height);
//		g2.drawRect(rectangleE0_12.x, rectangleE0_12.y, rectangleE0_12.width, rectangleE0_12.height);
//		g2.drawRect(rectangleE0_13.x, rectangleE0_13.y, rectangleE0_13.width, rectangleE0_13.height);
//		g2.drawRect(elevatorE0_A.x, elevatorE0_A.y, elevatorE0_A.width, elevatorE0_A.height);
//		g2.drawRect(elevatorE0_B.x, elevatorE0_B.y, elevatorE0_B.width, elevatorE0_B.height);
		
		g2.drawRect(rectangleE1_1.x, rectangleE1_1.y, rectangleE1_1.width, rectangleE1_1.height);
		g2.drawRect(rectangleE1_2.x, rectangleE1_2.y, rectangleE1_2.width, rectangleE1_2.height);
		g2.drawRect(rectangleE1_3.x, rectangleE1_3.y, rectangleE1_3.width, rectangleE1_3.height);
		g2.drawRect(corridorE1_A.x, corridorE1_A.y, corridorE1_A.width, corridorE1_A.height);
		g2.drawRect(corridorE1_B.x, corridorE1_B.y, corridorE1_B.width, corridorE1_B.height);
		g2.drawRect(corridorE1_C.x, corridorE1_C.y, corridorE1_C.width, corridorE1_C.height);
		g2.drawRect(corridorE1_D.x, corridorE1_D.y, corridorE1_D.width, corridorE1_D.height);
		g2.drawRect(corridorE1_E.x, corridorE1_E.y, corridorE1_E.width, corridorE1_E.height);
		g2.drawRect(corridorE1_F.x, corridorE1_F.y, corridorE1_F.width, corridorE1_F.height);
		g2.drawRect(rectangleE1_10.x, rectangleE1_10.y, rectangleE1_10.width, rectangleE1_10.height);
		g2.drawRect(rectangleE1_11.x, rectangleE1_11.y, rectangleE1_11.width, rectangleE1_11.height);
		g2.drawRect(rectangleE1_12.x, rectangleE1_12.y, rectangleE1_12.width, rectangleE1_12.height);
		g2.drawRect(rectangleE1_13.x, rectangleE1_13.y, rectangleE1_13.width, rectangleE1_13.height);
		g2.drawRect(rectangleE1_14.x, rectangleE1_14.y, rectangleE1_14.width, rectangleE1_14.height);
		g2.drawRect(elevatorE1_A.x, elevatorE1_A.y, elevatorE1_A.width, elevatorE1_A.height);
		g2.drawRect(elevatorE1_B.x, elevatorE1_B.y, elevatorE1_B.width, elevatorE1_B.height);
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
	 * Mouse Entered
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
	}

	/**
	 * Mouse Exited
	 */
	@Override
	public void mouseExited(MouseEvent e) {
	}

}
