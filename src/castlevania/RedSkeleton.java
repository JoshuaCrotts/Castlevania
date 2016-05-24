package castlevania;

import java.awt.image.BufferedImage;

public class RedSkeleton extends Enemy {

	private static final long serialVersionUID = -7197389649495032529L;

	final static int WIDTH = 128, HEIGHT = 128, SPRITEROWS = 1, SPRITECOLS = 2,
			RANGE = 300, ATTACKRANGE = 300;

	static SpriteSheet sheet = new SpriteSheet(
			"spritesheets/red_skeleton_sheet.png", WIDTH, HEIGHT,
			SPRITEROWS, SPRITECOLS);

	private BufferedImage currentImage = sheet.getImage(0, 0);

	private int health = 1000;
	
	private int counter = 0, xSprite = 0, ySprite = 0;

	public boolean isPassive = true, isAttacking = false;

	private final int RUNSPEED = 3, MOVESPEED = 2; //Might change AD

	public int direction = 1, velX, velY = 0, xOrigin;

	/**Basically this is a boss.
	 * 
	 * @param x coord
	 * @param y coord
	 */
	public RedSkeleton(int x, int y) {
		super(x, y, WIDTH, HEIGHT, sheet);
		this.xOrigin = x;
		this.health = 20;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	/**
	 * This will change the image every five times
	 * the counter is incremented so it won't swap so fast.
	 * Changes depend on the distance between the player's X and Y, etc.
	 */
	public BufferedImage changeImages() {
		counter++;

		// Just sets Sprites and running Speeds.
		if (counter >= 5) { // Should be 5
			testCollision();
			testAction();

			if (isPassive) {
				if (changeDirectionOrNaw())
					direction *= -1;
				
				setVelx(MOVESPEED * direction);
			}

			else if (isAttacking) // This sprite is for when he's attacking.
			{
				if (Game.getPlayer().getX() < this.x) {
					direction = -1;
				} else {
					direction = 1;
				}
				setVelx(RUNSPEED * direction);
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
		}
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
		if (Math.abs(Game.getPlayer().getX() - this.x) <= ATTACKRANGE) {
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