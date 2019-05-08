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
import java.sql.Time;
import java.text.Normalizer;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;

import org.apache.logging.log4j.Level;

import com.blackmamba.deathkiss.entity.CommonArea;
import com.blackmamba.deathkiss.entity.Floor;
import com.blackmamba.deathkiss.entity.Sensitivity;
import com.blackmamba.deathkiss.entity.Alert;
import com.blackmamba.deathkiss.entity.Sensor;
import com.blackmamba.deathkiss.entity.SensorType;
import com.blackmamba.deathkiss.launcher.ClientSocket;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
	private JTextField textInputIdSensor;
	private Font police;
	private Font policeBar;
	private JButton disconnection;
	private JButton validButton;
	private JPanel bar;
	private JPanel search;
	private JPanel canvas;
	private JLabel labelIdEmployee;
	private JLabel labelHeadList;
	private JLabel labelSearch;
	private JTextField searchBar;
	private JTextField textInputAlertState;
	private JTextField textInputThresholdMin;
	private JTextField textInputThresholdMax;

	private List<SurfacePolygon> surfacePolygon = new ArrayList<SurfacePolygon>();
	private List<Sensor> listSensor = new ArrayList<Sensor>();
	private List<Alert> listAlert = new ArrayList<Alert>();
	private JButton switchButton;
	private JButton configureSensor;

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
	private JTabbedPane tab;

	private JList<String> list;
	private JComboBox<String> textInputNameCommonArea;
	private JComboBox<String> textInputTypeSensor;
	private JComboBox<String> textInputSensitivity;
	private JComboBox<String> textFloor;
	private JComboBox<Integer> textInputHourStartActivity;
	private JComboBox<Integer> textInputMinuteStartActivity;
	private JComboBox<Integer> textInputHourEndActivity;
	private JComboBox<Integer> textInputMinuteEndActivity;
	private DefaultListModel<String> listM;
	private List<Sensor> listSearchSensor = new ArrayList<Sensor>();
	private ResourceBundle rs = ResourceBundle.getBundle("parameters");

	private BufferedImage img = null;
	private BufferedImage img1 = null;
	private BufferedImage buffer = null;
	private BufferedImage buffer1 = null;
	private Point p = null;

	private static final Rectangle polygon1 = new Rectangle(7, 56, 108, 313);
	private static final Rectangle polygon2 = new Rectangle(129, 72, 105, 97);
	private static final Rectangle polygon3 = new Rectangle(240, 171, 346, 45);
	private static final Rectangle polygon4_1 = new Rectangle(591, 171, 282, 182);
	private static final Rectangle polygon4_2 = new Rectangle(733, 71, 140, 99);

	public TabMapSensor() {
	}

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

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setVgap(100);
		flowLayout.setHgap(200);
		panel.setBackground(Color.GRAY);
		add(panel);

		///////////////////////// JScrollPane///////////////////////////////////////////

		JScrollPane scroll = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(0, 0, 930, 610);
		this.add(scroll);

		///////////////////////// IMAGE/////////////////////////////////////////////////

		try {
			img = ImageIO.read(getClass().getClassLoader().getResource("etage0.jpg"));
			img1 = ImageIO.read(getClass().getClassLoader().getResource("etage1bis.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		this.buffer = img;
		this.buffer1 = img1;

		JLabel image = new JLabel(new ImageIcon(img));
		JLabel image1 = new JLabel(new ImageIcon(img1));

		panel.setLayout(new BorderLayout());
		panel.add(image, BorderLayout.EAST);

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
