package castlevania;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Enemy extends Entity {

	private static final long serialVersionUID = 2003297664785729009L;
	protected int x;
	protected int y;
	protected final int WIDTH;
	protected final int HEIGHT;
	protected SpriteSheet SHEET;
	
	public Enemy(int x, int y, int WIDTH, int HEIGHT, SpriteSheet sheet)
	{
		this.x = x;
		this.y = y;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
	}
	public Enemy(int WIDTH, int HEIGHT){
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	}

	public abstract BufferedImage getImage();
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWIDTH() {
		return WIDTH;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}

	public SpriteSheet getSHEET() {
		return SHEET;
	}
}