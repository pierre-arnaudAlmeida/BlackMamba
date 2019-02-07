package ihm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;

public class IHMTest extends JFrame {

	private JPanel contentPane;
	private JTextField IHMTestprenom;
	private JTextField IHMTestNom;
	private JTextField IHMTestemployee;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IHMTest frame = new IHMTest();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public IHMTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel prenom = new JLabel("Votre Pr\u00E9nom");
		prenom.setBounds(147, 21, 72, 14);
		contentPane.add(prenom);
		
		final JLabel Bonjourlbl = new JLabel("Bonjour");
		Bonjourlbl.setHorizontalAlignment(SwingConstants.LEFT);
		Bonjourlbl.setBounds(43, 199, 111, 14);
		contentPane.add(Bonjourlbl);
		
		IHMTestprenom = new JTextField();
		IHMTestprenom.setBounds(147, 34, 96, 20);
		contentPane.add(IHMTestprenom);
		IHMTestprenom.setColumns(10);
		
		JButton validerbouton = new JButton("Valider");
		validerbouton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String resultat = IHMTestprenom.getText();
				Bonjourlbl.setText("bonjour " + resultat + "!");
			}
		});
		validerbouton.setBounds(300, 122, 89, 23);
		contentPane.add(validerbouton);
		
		IHMTestNom = new JTextField();
		IHMTestNom.setBounds(266, 34, 96, 20);
		contentPane.add(IHMTestNom);
		IHMTestNom.setColumns(10);
		
		JLabel nom = new JLabel("Votre nom");
		nom.setBounds(264, 21, 98, 14);
		contentPane.add(nom);
		
		JLabel id_employee = new JLabel("Id employee");
		id_employee.setBounds(10, 21, 81, 14);
		contentPane.add(id_employee);
		
		IHMTestemployee = new JTextField();
		IHMTestemployee.setBounds(10, 34, 96, 20);
		contentPane.add(IHMTestemployee);
		IHMTestemployee.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(147, 123, 121, 20);
		contentPane.add(passwordField);
		
		final JCheckBox Montrermdp = new JCheckBox("Montrer le mot de passe");
		Montrermdp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Montrermdp.isSelected())
				{
					passwordField.setEchoChar((char)0);
				}else 
				{
					passwordField.setEchoChar('*');
				}
					

			}
		});
		Montrermdp.setBounds(147, 150, 171, 23);
		contentPane.add(Montrermdp);
	}
}