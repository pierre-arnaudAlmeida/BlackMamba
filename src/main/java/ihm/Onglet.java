package ihm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Onglet extends JPanel {
	private Color color = Color.white;
	private static int COUNT = 0;
	private String message = "";

	public Onglet() {
	}

	public Onglet(Color color, String title) {
		this.color = color;
		this.message = title;
		this.COUNT = ++COUNT;
	}

	public void paintComponent(Graphics g) {
		g.setColor(this.color);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.drawString(this.message, 10, 20);
	}
}
