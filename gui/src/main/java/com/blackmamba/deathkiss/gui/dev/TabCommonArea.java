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

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.blackmamba.deathkiss.entity.CommonArea;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class TabCommonArea extends JPanel {
	private String message;
	private int idemployee;
	private JPanel bar;
	private JLabel labelIdEmployee;
	private JLabel idEmployee;
	private JLabel labelNameCommonArea;
	private JLabel labelStageCommonArea;
	private JLabel labelIdCommonArea;
	private JTextField textInputNameCommonArea;
	private JTextField textInputStageCommonArea;
	private JTextField textInputIdCommonArea;
	private Font policeBar;
	private Font policeLabel;
	private JButton disconnection;
	private JButton addCommonArea;
	private JButton save;
	private JButton delete;
	private JButton restaure;
	private JButton listSensor;
	private CommonArea commonArea;
	private static final Logger logger = LogManager.getLogger(TabProfile.class);
	private JList list;

	public TabCommonArea() {
	}

	public TabCommonArea(Color color, String title, int idemployee) {
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
		policeBar = new Font("Arial", Font.BOLD, 16);
		labelIdEmployee.setForeground(Color.WHITE);
		labelIdEmployee.setFont(policeBar);
		bar.add(labelIdEmployee, BorderLayout.WEST);

		/**
		 * Definition of the label idEmployee on header bar
		 */
		idEmployee = new JLabel();
		idEmployee.setText("" + this.idemployee + "");
		idEmployee.setFont(policeBar);
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

		///////////////////////// LIST EMPLOYEE////////////////////////////////////////
		
		JScrollPane sc;
		
		int x = 0;
		
		DefaultListModel listM;
		// TODO mettre une barre de recherche et on affiche les résultat dans le Jlist
		listM = new DefaultListModel();
		while (x < 100) {
			listM.addElement(""+x+","+x);
			x++;
		}
		
		list = new JList(listM);
		sc = new JScrollPane(list);

		sc.setBounds(30, 120, 300, ((int) getToolkit().getScreenSize().getHeight() - 300));
		this.add(sc);
		
		MouseListener mouseListener = new MouseAdapter() {
		     public void mouseClicked(MouseEvent e) {
		         int index = list.locationToIndex(e.getPoint());
		         System.out.println("clicked on Item " + index);
		         String z= listM.getElementAt(index).toString();
		         System.out.println(z);
		         int q = z.indexOf(",");
		         String i = z.substring(0,q);
		         System.out.println(i);
		         
		     }
		 };
		 list.addMouseListener(mouseListener);

		///////////////////////// LABEL///////////////////////////////////////////////
		/**
		 * Definition of label IdCommonArea
		 */
		policeLabel = new Font("Arial", Font.BOLD, (int) getToolkit().getScreenSize().getWidth() / 80);
		labelIdCommonArea = new JLabel("Id : ");
		labelIdCommonArea.setBounds((int) getToolkit().getScreenSize().getWidth() * 1 / 4, (int) getToolkit().getScreenSize().getHeight() * 2 / 10, 100, 30);
		// labelIdCommonArea.setBounds(770, 350, 100, 30);
		labelIdCommonArea.setFont(policeLabel);
		this.add(labelIdCommonArea);

		/**
		 * Definition of label NameCommonArea
		 */
		labelNameCommonArea = new JLabel("Nom : ");
		labelNameCommonArea.setBounds((int) getToolkit().getScreenSize().getWidth() * 1 / 4, (int) getToolkit().getScreenSize().getHeight() * 4 / 10, 200, 30);
		// labelNameCommonArea.setBounds(330, 200, 100, 30);
		labelNameCommonArea.setFont(policeLabel);
		this.add(labelNameCommonArea);

		/**
		 * Definition of label StageCommonArea
		 */
		labelStageCommonArea = new JLabel("Etage : ");
		labelStageCommonArea.setBounds((int) getToolkit().getScreenSize().getWidth() * 1 / 4, (int) getToolkit().getScreenSize().getHeight() * 6 / 10, 200, 30);
		// labelStageCommonArea.setBounds(770, 200, 100, 30);
		labelStageCommonArea.setFont(policeLabel);
		this.add(labelStageCommonArea);

		//////////////////// TEXT AREA////////////////////////////////////////////////
		/**
		 * Definition of textArea IdCommonArea
		 */
		textInputIdCommonArea = new JTextField();
		textInputIdCommonArea.setBounds((int) getToolkit().getScreenSize().getWidth() * 1 / 4, (int) getToolkit().getScreenSize().getHeight() * 5 / 20, 300, 40);
		// textInputIdCommonArea.setBounds(330, 250, 300, 40);
		textInputIdCommonArea.setFont(policeLabel);
		// textInputIdCommonArea.setText(Integer.toString(commonArea.getIdCommonArea()));
		this.add(textInputIdCommonArea);

		/**
		 * Definition of textArea NameCommonArea
		 */
		textInputNameCommonArea = new JTextField();
		textInputNameCommonArea.setBounds((int) getToolkit().getScreenSize().getWidth() * 1 / 4, (int) getToolkit().getScreenSize().getHeight() * 9 / 20, 300, 40);
		// textInputNameCommonArea.setBounds(770, 250, 300, 40);
		textInputNameCommonArea.setFont(policeLabel);
		// textInputNameCommonArea.setText(commonArea.getNameCommonArea());
		this.add(textInputNameCommonArea);

		/**
		 * Definition of textArea Stage
		 */
		textInputStageCommonArea = new JTextField();
		textInputStageCommonArea.setBounds((int) getToolkit().getScreenSize().getWidth() * 1 / 4, (int) getToolkit().getScreenSize().getHeight() * 13 / 20, 300, 40);
		// textInputStageCommonArea.setBounds(770, 400, 300, 40);
		textInputStageCommonArea.setFont(policeLabel);
		// textInputStageCommonArea.setText(Integer.toString(commonArea.getEtageCommonArea()));
		this.add(textInputStageCommonArea);

		//////////////////// BUTTON////////////////////////////////////////////////
		/**
		 * Definition of Button AddEmployee
		 */
		addCommonArea = new JButton("Ajouter");
		addCommonArea.setBounds(30, (int) getToolkit().getScreenSize().getHeight() - 150, 300, 40);
		// addCommonArea.setBounds(147, 300, 100, 23);
		this.add(addCommonArea);
		addCommonArea.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// Quand on clique on recupere le id de la commonArea que l'on vient de cliquer
				// et
				// on fait une recherche dans la bdd avec un read, si ensuite les information
				// des textinput sont equal
				// au info recu de la bdd alors on fait une popup sinon on fait un create dans
				// la base avec les infos des text input
			}
		});

		/**
		 * Definition of Button Save
		 */
		save = new JButton("Sauvegarder");
		save.setBounds(((int) getToolkit().getScreenSize().getWidth() * 2 / 4) + 250, (int) getToolkit().getScreenSize().getHeight() * 15 / 20, 200, 40);
		// save.setBounds(147, 300, 100, 23);
		this.add(save);
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				while(!listM.isEmpty()){
					listM.removeElementAt(listM.size()-1);
				}
				int x=0;
				while(x<40) {
					//TODO nouvelle liste avec les infos de la recherche
					listM.addElement("A"+x);
					x++;
				}
				TabCommonArea a = new TabCommonArea();
				a.setVisible(true);
				setVisible(false);
			}
		});

		/**
		 * Definition of Button Delete
		 */
		delete = new JButton("Supprimer");
		delete.setBounds(((int) getToolkit().getScreenSize().getWidth() * 2 / 4) - 250, (int) getToolkit().getScreenSize().getHeight() * 15 / 20, 200, 40);
		// delete.setBounds(147, 400, 100, 23);
		this.add(delete);
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			
			}
		});

		/**
		 * Definition of Button Restaure
		 */
		restaure = new JButton("Annuler");
		restaure.setBounds(((int) getToolkit().getScreenSize().getWidth() * 2 / 4), (int) getToolkit().getScreenSize().getHeight() * 15 / 20, 200, 40);
		// restaure.setBounds(147, 400, 100, 23);
		this.add(restaure);
		restaure.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO reprendre les infos quand on clique sur un employee
				textInputIdCommonArea.setText(Integer.toString(commonArea.getIdCommonArea()));
				textInputNameCommonArea.setText(commonArea.getNameCommonArea());
				textInputStageCommonArea.setText(Integer.toString(commonArea.getEtageCommonArea()));
			}
		});

		listSensor = new JButton("Liste Capteur");
		listSensor.setBounds(((int) getToolkit().getScreenSize().getWidth() * 1 / 4), (int) getToolkit().getScreenSize().getHeight() * 15 / 20, 200, 40);
		// listSensor.setBounds(147, 400, 100, 23);
		this.add(listSensor);
		listSensor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		/**
		 * Diferent parameter of the window
		 */
		this.setLayout(new BorderLayout());
		this.add(bar, BorderLayout.NORTH);
		this.message = title;
		this.setBackground(color);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
