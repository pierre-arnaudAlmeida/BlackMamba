package com.blackmamba.deathkiss.gui;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JPanel;
import org.apache.logging.log4j.Level;

import com.blackmamba.deathkiss.entity.CommonArea;
import com.blackmamba.deathkiss.entity.Alert;
import com.blackmamba.deathkiss.entity.Sensor;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Raymond
 *
 */

public class TabMapSensor extends JPanel implements MouseListener {

	private int idEmployee;
	private int index;
	private Font police;
	private Font policeBar;
	private JButton disconnection;
	private JButton switchButton;
	private JButton validButton;
	private JPanel bar;
	private JPanel search;
	private JLabel labelIdEmployee;
	private JLabel labelSearch;
	private JScrollPane sc;
	private JTextField searchBar;
	private JTextField textInputIdSensor;
	private JLabel imageNursingHome;
	private ImageIcon nursingHome;

	private List<SurfacePolygon> surfacePolygon = new ArrayList<SurfacePolygon>();
	private List<Sensor> listSensor = new ArrayList<Sensor>();
	private List<Alert> listAlert = new ArrayList<Alert>();

	private Sensor sensor;
	private TabSensor tabSensor;
	private String requestType;
	private String table;
	private String jsonString;
	private static final Logger logger = LogManager.getLogger(TabMapSensor.class);
	private static final long serialVersionUID = 7348020021300445245L;

	private CommonArea commonArea;
	private ObjectMapper objectMapper;
	private Thread threadMapSensor;

	private JList<String> list;
	private JComboBox<String> textInputNameCommonArea;
	private JComboBox<String> textInputTypeSensor;
	private DefaultListModel<String> listM;
	private ResourceBundle rs = ResourceBundle.getBundle("parameters");

	public TabMapSensor() {

	}

	public TabMapSensor(Color color, int idEmployee, String title) {
		this.idEmployee = idEmployee;

		setThreadMapSensor(new Thread(new Runnable() {
			/**
			 * Loop and update every 30 seconds the list of Sensor with their alert state
			 */
			@Override
			public void run() {
				while (true) {
					//tabSensor.actualizationListSensor();
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

		///////////////////////// FRAME/////////////////////////////////////////////////
		/**
		 * Different parameters of the window
		 */
		this.setLayout(new BorderLayout());
		this.add(bar, BorderLayout.NORTH);
		this.setBackground(color);

		///////////////////////// LIST SENSOR///////////////////////////////////////////
		listM = new DefaultListModel<String>();
		list = new JList<String>(listM);

		/**
		 * TODO RK Update sensors Every time an alert is declared, the list is updated
		 */
		//tabSensor.actualizationListSensor();

		sc = new JScrollPane(list);
		sc.setBounds(30, 120, 300, ((int) getToolkit().getScreenSize().getHeight() - 300));
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
						;
						new ClientSocket(requestType, jsonString, table);
						jsonString = ClientSocket.getJson();
						sensor = objectMapper.readValue(jsonString, Sensor.class);
					} catch (Exception e1) {
						logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
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
						;
						new ClientSocket(requestType, jsonString, table);
						jsonString = ClientSocket.getJson();
						commonArea = readMapper.readValue(jsonString, CommonArea.class);
					} catch (Exception e1) {
						logger.log(Level.INFO, "Impossible to parse in JSON " + e1.getClass().getCanonicalName());
					}

					textInputIdSensor.setText(Integer.toString(sensor.getIdSensor()));
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

		
		///////////////////////// IMAGE/////////////////////////////////////////////////

		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		/**
		 * TODO RK Lorsqu'on clique sur une partie commune, cela créera un popup d'une
		 * liste de capteur
		 */
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		/**
		 * TODO RK Lorsqu'on rentre dans une partie commune, cela modiifera sa couleur
		 */
	}

	@Override
	public void mouseExited(MouseEvent e) {
		/**
		 * TODO RK
		 */
	}

	@Override
	public void mousePressed(MouseEvent e) {
		/**
		 * TODO RK
		 */
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		/**
		 * TODO RK
		 */
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

}
