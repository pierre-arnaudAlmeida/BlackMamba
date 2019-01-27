package ihm;

import java.awt.Color;

import javax.swing.*;
import javax.swing.border.Border;

public class Fenetre extends JFrame {

	private JTabbedPane onglet;

	public Fenetre() {

		this.setTitle("BlackMamba");

		this.setSize((int) getToolkit().getScreenSize().getWidth(), (int) getToolkit().getScreenSize().getHeight());
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		Onglet[] tPan = { new Onglet(Color.RED, "Employés"), new Onglet(Color.GREEN, "Parties Communes"),
				new Onglet(Color.BLUE, "Capteurs"), new Onglet(Color.RED, "Résidants"),
				new Onglet(Color.RED, "Historiques") };

		onglet = new JTabbedPane();
		int i = -1;
		String tableauOnglet[] = { "Employés", "Parties Communes", "Capteurs", "Résidants", "Historiques" };
		for (Onglet pan : tPan) {
			onglet.add("Onglet " + tableauOnglet[++i], pan);
		}
		this.getContentPane().add(onglet);
		this.setVisible(true);
	}
}
