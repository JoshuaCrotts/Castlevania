package castlevania;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JComponent;

public class GUI extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5908728103257773279L;
	private ArrayList<Rectangle> playerHealthBar = new ArrayList<Rectangle>();
	private int playerHealth;

	public GUI() {
		for (int i = 0, x = 120; i < 10; i++, x += 18) {
			Rectangle rect = new Rectangle(x, 43, 12, 30);
			playerHealthBar.add(rect);
		}
		playerHealth = 10;
		//repaint();
	}

	public void setPlayerHealth(int h)
	{
		playerHealth = h;
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		Color black1 = new Color(139, 90, 0);
		Color black2 = new Color(139, 0, 0);

		GradientPaint grad = new GradientPaint(0, 0, black1, 1300, 400, black2);
		g2.setPaint(grad);
		g2.fillRect(0, 0, 800, 90);

		Font f = new Font("Georgia", Font.PLAIN, 22);
		g2.setColor(Color.BLACK);
		g2.setFont(f);
		g2.drawString("HEALTH:", 15, 68);
		for (int i = 0; i < playerHealth; i++) {
			g2.setColor(Color.RED);
			g2.fill(playerHealthBar.get(i));
		}

	}
}