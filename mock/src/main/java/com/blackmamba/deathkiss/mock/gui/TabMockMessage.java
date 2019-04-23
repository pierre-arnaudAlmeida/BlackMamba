package com.blackmamba.deathkiss.mock.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TabMockMessage extends JPanel {

	/**
	 * Different parameters used
	 */
	private static final long serialVersionUID = 1L;
	private Font policeLabel;
	private JLabel labelIdSensor;
	private JLabel labelThreshold;
	private JLabel labelSensorType;
	private JLabel labelRandomSensor;
	private JTextField textInputIdSensor;
	private JSlider sliderThreshold;
	private ButtonGroup buttonGroup;
	private JRadioButton positiveRadio;
	private JRadioButton negativeRadio;
	private JComboBox<String> textInputTypeSensor;

	public TabMockMessage() {

	}

	public TabMockMessage(Color color, String title) {
		///////////////////////// LABEL///////////////////////////////////////////////
		/**
		 * Definition of label IdSensor
		 */
		policeLabel = new Font("Arial", Font.BOLD, 20);
		labelIdSensor = new JLabel("Id Capteur : ");
		labelIdSensor.setBounds(100, 100, 200, 30);
		labelIdSensor.setFont(policeLabel);
		this.add(labelIdSensor);

		/**
		 * Definition of label Threshold
		 */
		labelThreshold = new JLabel("Seuil : ");
		labelThreshold.setBounds(100, 200, 200, 30);
		labelThreshold.setFont(policeLabel);
		this.add(labelThreshold);

		/**
		 * Definition of label SensorType
		 */
		labelSensorType = new JLabel("Type capteur : ");
		labelSensorType.setBounds(400, 200, 200, 30);
		labelSensorType.setFont(policeLabel);
		this.add(labelSensorType);

		/**
		 * Definition of label RandomSensor
		 */
		labelRandomSensor = new JLabel("Capteur Aleatoire : ");
		labelRandomSensor.setBounds(400, 100, 200, 30);
		labelRandomSensor.setFont(policeLabel);
		this.add(labelRandomSensor);
		//////////////////// TEXT AREA////////////////////////////////////////////////
		/**
		 * Definition of textArea IdSensor
		 */
		textInputIdSensor = new JTextField();
		textInputIdSensor.setBounds(100, 150, 250, 30);
		textInputIdSensor.setFont(policeLabel);
		textInputIdSensor.setText("");
		this.add(textInputIdSensor);

		/**
		 * Definition of Slider Threshold
		 */
		sliderThreshold = new JSlider();
		sliderThreshold.setPaintTicks(true);
		sliderThreshold.setPaintLabels(true);
		sliderThreshold.setMaximum(100);
		sliderThreshold.setMinimum(0);
		sliderThreshold.setValue(30);
		sliderThreshold.setMinorTickSpacing(5);
		sliderThreshold.setMajorTickSpacing(10);

		sliderThreshold.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				if (textInputTypeSensor.getSelectedItem().equals("ELEVATOR")) {
					labelThreshold.setText("Seuil : " + ((JSlider) event.getSource()).getValue() + "00kg");
				} else if (textInputTypeSensor.getSelectedItem().equals("SMOKE")) {
					labelThreshold.setText("Seuil : " + ((JSlider) event.getSource()).getValue() + "0ppm");
				} else {
					labelThreshold.setText("Seuil : " + ((JSlider) event.getSource()).getValue());
				}
			}
		});

		sliderThreshold.setBackground(color);
		sliderThreshold.setBounds(100, 250, 250, 50);
		this.add(sliderThreshold);

		/**
		 * Definition of ButtonGroup RandomSensor
		 */
		positiveRadio = new JRadioButton("Oui");
		positiveRadio.setBounds(400, 150, 100, 30);
		positiveRadio.setFont(policeLabel);
		positiveRadio.setBackground(color);

		negativeRadio = new JRadioButton("Non");
		negativeRadio.setBounds(500, 150, 100, 30);
		negativeRadio.setFont(policeLabel);
		negativeRadio.setBackground(color);

		buttonGroup = new ButtonGroup();
		buttonGroup.add(positiveRadio);
		buttonGroup.add(negativeRadio);

		this.add(positiveRadio);
		this.add(negativeRadio);

		/**
		 * Definition of ComboBox TypeSensor
		 */
		String[] types = { "SMOKE", "MOVE", "TEMPERATURE", "WINDOW", "DOOR", "ELEVATOR", "LIGHT", "FIRE", "BADGE", "ROUTER" };
		textInputTypeSensor = new JComboBox<String>(types);
		textInputTypeSensor.setBounds(400, 300, 300, 40);
		textInputTypeSensor.setFont(policeLabel);
		textInputTypeSensor.setSelectedItem(null);
		this.add(textInputTypeSensor);
		textInputTypeSensor.addItemListener(new ItemListener() {
			/**
			 * All type of Sensor
			 */
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getItem().toString().equals("SMOKE")) {
					sliderThreshold.setMaximum(50);
					sliderThreshold.setMinimum(0);
					sliderThreshold.setValue(10);
					sliderThreshold.setMinorTickSpacing(5);
					sliderThreshold.setMajorTickSpacing(10);
				} else if (e.getItem().toString().equals("MOVE") || e.getItem().toString().equals("WINDOW") || e.getItem().toString().equals("DOOR") || e.getItem().toString().equals("LIGHT") || e.getItem().toString().equals("FIRE") || e.getItem().toString().equals("BADGE")
						|| e.getItem().toString().equals("ROUTER")) {
					sliderThreshold.setMaximum(1);
					sliderThreshold.setMinimum(0);
					sliderThreshold.setValue(0);
				} else if (e.getItem().toString().equals("TEMPERATURE")) {
					sliderThreshold.setMaximum(50);
					sliderThreshold.setMinimum(-20);
					sliderThreshold.setValue(20);
					sliderThreshold.setMinorTickSpacing(5);
					sliderThreshold.setMajorTickSpacing(10);
				} else if (e.getItem().toString().equals("ELEVATOR")) {
					sliderThreshold.setMaximum(8);
					sliderThreshold.setMinimum(0);
					sliderThreshold.setValue(4);
					sliderThreshold.setMinorTickSpacing(1);
					sliderThreshold.setMajorTickSpacing(2);
				}
			}
		});

		///////////////////////// FRAME/////////////////////////////////////////////////
		/**
		 * Different parameters of the window
		 */
		this.setLayout(new BorderLayout());
		this.setBackground(color);
	}
}
