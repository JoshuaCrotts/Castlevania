package castlevania;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JComponent;

public class Player extends JComponent {

	private static final long serialVersionUID = -5471880502989465049L;
	private static int counter = 0;
	private BufferedImage currentImage, oldImage;
	private int xSprite = 0, ySprite = 0, x, y, velx = 0, vely, velyInit = 15, accel = -20, t = 0, health, direction = 1,
			attackTime = 5;
	private final int SPRITEROWS = 4, SPRITECOLS = 6, WIDTH = 128, HEIGHT = 128, RUNSPEED = 10;
	private SpriteSheet sheet;
	private ArrayList<Item> items;
	public boolean isRunning = false, isAttacking = false, isStanding = true, isJumping = false;
	public int attackRange = 1;
	
	public Player(int x, int y) {
		super();
		sheet = new SpriteSheet("spritesheets/belmont_sprite_sheet_with_fixed_whip.png", WIDTH, HEIGHT, SPRITEROWS, SPRITECOLS);
		sheet.modifyRow(1, 2, 2*WIDTH, HEIGHT);
		// currentImage = sheet.getImage(0, 0);
		this.x = x;
		this.y = y;
		health = 10;
		items = new ArrayList<Item>(); // for items gathered in game
		setFocusable(true);
	}

	public BufferedImage changeImages() {
		oldImage = currentImage;
		counter++;
/*		if (isSpacePressed) {
			currentImage = sheet.getImage(0, 1);
			if (direction == 1)
				currentImage = (sheet.getImage(1, 1));
			else
				currentImage = sheet.getFlippedImage(1, 1);

			isSpacePressed = false;
			return currentImage;
		}*/
		if (isJumping) { // This probably needs to go in the counter.
			if (clearBelow() && clearAbove()) {
				jump();
			} else {
				vely = 0;
				isJumping = false;
				xSprite = 0;
				ySprite = 0;
			}
		}

		// Just sets Sprites and running Speeds.
		if (counter >= 5) { // Should be 5
			if (isAttacking) // This sprite is for when he's attacking.
			{
				//System.out.println(attackTime);
				xSprite = 1;
				if (attackTime > 4)
				{
					ySprite = 0;
					attackTime--;
				}
				else if (attackTime > 0) {
					ySprite = 1;
					attackTime--;
				}
				else
				{
					isAttacking = false;
					attackTime = 5;
				}
			}
			else if (isJumping) {
				xSprite = 0;
				ySprite = 5;
			} else if (isStanding) {
				setVelx(0);
				xSprite = 0;
				ySprite = 0;
			} else if (isRunning) // I'm going to have images be returned
									// differently
			// depending on what the player is doing. This part
			// is if it's running.
			{
				xSprite = 0;
				setVelx(RUNSPEED * direction);
				if (ySprite != SPRITECOLS - 2 && velx != 0) {
					ySprite++;
				} else {
					ySprite = 0;
				}

			}
			// System.out.println(direction);
			if (direction == 1) {
				// System.out.println("Going right");
				currentImage = sheet.getImage(xSprite, ySprite);
			} else // if (direction == -1)
			{
				// System.out.println("This should print");
				currentImage = sheet.getFlippedImage(xSprite, ySprite);
			}
			counter = 0;
		}
		this.x += velx;
		this.y += vely;
		return currentImage;

	}

	public boolean checkRepaint() {
		if (currentImage == oldImage)
			return false;
		if (velx != 0 || vely != 0)
			return false;
		return true;
	}

	// public void paint(Graphics g)
	// {
	// dbImage = createImage(getWIDTH(), getHEIGHT());
	// dbg = dbImage.getGraphics();
	// paintComponent(dbg);
	// g.drawImage(dbImage, 0, 0, this);
	// }
	// @Override
	// public void paintComponent(Graphics g) {
	// //super.paintComponent(g);
	// Graphics2D g2 = (Graphics2D) g;
	// g2.drawImage(this.currentImage,x,y,this);
	// repaint();
	// }

	public boolean clearBelow() // Modify later to make it check below for
								// platforms.
	{
		if (this.y + HEIGHT > 600) {
			this.y = 600 - HEIGHT;
			return false;
		}
		return true;
	}

	public int getDirection() {
		return direction;
	}

	public boolean clearAbove() {
		return true;
	}

	public void resetTime() {
		this.t = 0;
	}

	public void jump() {
		// v = vi + at ---- Physics ----
		// System.out.println("IS JUMPING");
		isJumping = true;
		t++;
		if (t % 4 == 0) {
			vely = (int) ((velyInit + accel * ((double) (t / 30.0))) * -1);
			// System.out.println(vely);
		}
	}

	public void setDirection(int dir) {
		direction = dir;
	}

	public void setImage(int x, int y) {
		currentImage = sheet.getImage(x, y);
	}

	public BufferedImage getImageWithXAndY(int x, int y) {
		return sheet.getImage(x, y);
	}

	public boolean onGround() {
		return true;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWIDTH() {
		return WIDTH;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}

	public void setHealth(int x) {
		this.health = x;
	}

	public int getHealth() {
		return health;
	}

	public void addItem(Item item) {
		items.add(item);
	}

	public void removeItem(Item item) {
		items.remove(item);
	}

	public static int getCounter() {
		return counter;
	}

	public static void setCounter(int counter) {
		Player.counter = counter;
	}

	public int getxSprite() {
		return xSprite;
	}

	public void setxSprite(int xSprite) {
		this.xSprite = xSprite;
	}

	public int getySprite() {
		return ySprite;
	}

	public void setySprite(int ySprite) {
		this.ySprite = ySprite;
	}

	public int getSpriteRows() {
		return SPRITEROWS;
	}

	public int getSpriteCols() {
		return SPRITECOLS;
	}

	public SpriteSheet getSheet() {
		return sheet;
	}

	public void setSheet(SpriteSheet sheet) {
		this.sheet = sheet;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setVelx(int x) {
		this.velx = x;
	}

	public int getVelx() {
		return this.velx;
	}

	public BufferedImage getImage() {
		return currentImage;
	}
	
	public void setBImage(BufferedImage image){
		this.currentImage = image;
	}

	public boolean intersects(int enemyX){
		if(getX() == enemyX)return true;
		return false;
	}
	public void lose() {
		// TODO Auto-generated method stub
		GUI.removeRect();
		

	}
}