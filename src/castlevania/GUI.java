package castlevania;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JComponent;

public class GUI extends JComponent{

	private static final long serialVersionUID = -5908728103257773279L;
	private static ArrayList<Rectangle> playerHealthBar = new ArrayList<Rectangle>();
	private static ArrayList<Rectangle> bossHealthBar = new ArrayList<Rectangle>();
	public static int playerHealth;
	private static int k = 0;
	public GUI() {
		for (int i = 0, x = 220; i < 10; i++, x += 18) {
			Rectangle rect = new Rectangle(x, 43, 12, 30);
			playerHealthBar.add(rect);
		}
		playerHealth = 1000;
	}

	public void setPlayerHealth(int h)
	{
		playerHealth = h;
	}
	/**
	 * This will actually DRAW the GUI to the top of the frame.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		Color black1 = new Color(139, 90, 0);
		//Color black2 = new Color(139, 0, 0);

		Color red2 = new Color(140, 0+20, 0+20);
		GradientPaint grad = new GradientPaint(0, 0, black1, 700, 200, red2);
		g2.setPaint(grad);
		g2.fillRect(0, 0, 800, 90);

		Font customFont = null;
		try {
		    customFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/oldschool.ttf")).deriveFont(24f);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/oldschool.ttf")));
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}
		
		g2.setColor(Color.BLACK);
		g2.setFont(customFont);
		g2.drawString("HEALTH:", 15, 68);
		int x = 255;
		int y = 0;
		int z = 0;
		for (int i = 0; i < playerHealthBar.size(); i++) {
			g2.setColor(Color.RED);
			g2.fill(playerHealthBar.get(i));
		}
		for(int i = 0; i<playerHealthBar.size(); i++){
			if(playerHealthBar.size() > 10){
				playerHealthBar.remove(i);
				i--;}
		}
	}
	
	/**
	 * This method removes a rectangle dependent on the player's health, and has a text
	 * version of the health running in the console, and if that is divisible by 100 evenly,
	 * it removes a health bar.
	 */
	public static void removeRect(){
		if(playerHealthBar.size() == 0 || playerHealth == 0){
			Game.setIsDead(true);
		}
		else
		{
			
			playerHealth -= 10;
			System.out.println("Player health:" + playerHealth+" Bar Size: "+playerHealthBar.size());
			if(playerHealth % 100 == 0){
				playerHealthBar.remove(playerHealthBar.size()-1);}
		}
	}
	
	public void addBossBar(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		for (int i = 0, x = 440; i < 10; i++, x += 18) {
			Rectangle rect = new Rectangle(x, 43, 12, 30);
			bossHealthBar.add(rect);
		}
		for(int i = 0; i<bossHealthBar.size(); i++){
			g2.fill(bossHealthBar.get(i));
		}
		Game.getRedSkeleton().setHealth(2000);
		}
	
	public static void removeEnemyRect(){
		if(playerHealthBar.size() == 0 || playerHealth == 0){
			System.out.println("YOU WIN");
			System.exit(0);
		}
		else
		{
			
			Game.getRedSkeleton().setHealth(Game.getRedSkeleton().getHealth()-10);
			System.out.println("Enemy health:" + playerHealth+" Bar Size: "+playerHealthBar.size());
			if(Game.getRedSkeleton().getHealth() % 200 == 0){
				playerHealthBar.remove(k);
				k++;}
		}
	}
	
}