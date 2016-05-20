package castlevania;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Julius extends Boss{
	
	final static int WIDTH = 128, HEIGHT = 128, SPRITEROWS = 4, SPRITECOLS = 5, RANGE = 200;
	
	final static int ATTACKRANGE = 200;
	
	private int x, y, velx = 0, vely, velyInit = 15, accel = -20, t = 0;
	
	static SpriteSheet sheet = new SpriteSheet("spritesheets/julius_belmont_sheet.png", WIDTH, HEIGHT, SPRITEROWS, SPRITECOLS);
	
	private BufferedImage oldImage, currentImage = sheet.getImage(0, 0);
	
	private int counter = 5, xSprite = 0, ySprite = 0, turningPoint;
	
	private int health = 20;
	
	public boolean isPassive = true, isAttacking = true;
	
	private final int RUNSPEED = 5, MOVESPEED = 3;
	
	public int direction = 1, velX, velY = 0, xOrigin;
	
	private int attackDamage;
	
	public int attackTime = 5;
	
	public Random randomInt = new Random();
	
	public Julius(int x, int y){
		super(x,y,WIDTH,HEIGHT,sheet);
		sheet.modifyRow(1, 1, 2*WIDTH, HEIGHT);
		this.xOrigin = x;
		this.health = 200;
	}
	
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
	
	public BufferedImage changeImages() {
		counter++;

		// Just sets Sprites and running Speeds.
		if (counter >= 5) { // Should be 5
			testCollision();
			testAction();
			walk(counter);
		}
			else if (isAttacking) // This sprite is for when he's attacking.
			{
				if (isAttacking) // This sprite is for when he's attacking.
				{
					System.out.println(attackTime);
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
				
			}
			if (ySprite == 0) {
				ySprite++;
			} else {
				ySprite = 0;
			}
			// System.out.println(direction);
			if (direction == 1) {
				currentImage = sheet.getFlippedImage(xSprite, ySprite);
			} else {
				currentImage = sheet.getImage(xSprite, ySprite);
			}
			counter = 0;
		this.x += velX;
		this.y += velY;
		return currentImage;
	}

	/*private void testCollision() {
		// TODO Auto-generated method stub
		Player player = Game.getPlayer();
		int playerX = player.getX();
		int playerY = player.getY();
		
		if ((Math.abs(this.x - playerX) <= player.getWIDTH())
				&& (Math.abs(this.y - playerY) >= player.getHEIGHT()))
		{
			player.lose();
		}
	}*/

	private void testAction() {
		// If within attacking range of the player
		System.out.println(Math.abs(Game.getPlayer().getX() - this.x));
		if (Math.abs(Game.getPlayer().getX() - this.x) <= 800) {
			this.isPassive = false;
			this.isAttacking = true;
		} else {
			this.isPassive = true;
			this.isAttacking = false;
		}
	}

	private boolean changeDirectionOrNaw() {
		/*
		 * Tests if the difference between the enemy's x-Coordinate and Origin
		 * would be closer to the origin with or without the direction added.
		 */
		if (Math.abs(this.x - xOrigin) < (Math.abs((this.x + direction)
				- xOrigin))
				&& outsideRange()) {
			return true;
		}
		return false;
	}

	/*
	 * @Override public void paintComponent(Graphics g){
	 * super.paintComponent(g); g.drawImage(currentImage, x, y, null); }
	 */
	private boolean outsideRange() {
		if (Math.abs(this.x - xOrigin) > RANGE) {
			return true;
		}
		return false;
	}
	
	public BufferedImage walk(int yCoord){
		return sheet.getImage(0, yCoord)
	}

	private void setVelx(int vel) {
		this.velX = vel;
	}

	public BufferedImage getImage() {
		return currentImage;
	}

	@Override
	public void setIsAttacking(boolean b) {
		this.isAttacking = b;

	}

	public void setIsPassive(boolean p) {
		this.isPassive = p;
	}

	public boolean isPassive() {
		return isPassive;
	}

	public boolean isAttacking() {
		return isAttacking;
	}

	public int getDirection() {
		return direction;
	}
	
	public void setHealth(int health){
		this.health = health;
	}
	
	public int getHealth(){
		return health;
	}

}
