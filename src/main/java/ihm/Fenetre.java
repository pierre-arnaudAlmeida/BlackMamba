package ihm;

import java.awt.FlowLayout;

import javax.swing.*;

public class Fenetre extends JFrame  {

	public Fenetre() {

	JFrame  Fenetre = new JFrame("JFrame Example");
	
    Fenetre.setTitle("Ma première fenêtre Java");

    Fenetre.setSize(400, 100);
    //Nous demandons maintenant à notre objet de se positionner au centre
    Fenetre.setLocationRelativeTo(null);
    //Termine le processus lorsqu'on clique sur la croix rouge
    Fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //Et enfin, la rendre visible        
    Fenetre.setVisible(true);
	}
}
