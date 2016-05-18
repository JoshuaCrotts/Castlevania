package castlevania;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Knight extends Enemy{
	
	final static int WIDTH = 128, HEIGHT = 128, SPRITEROWS = 1, SPRITECOLS = 3, RANGE = 200;
	
	final static int ATTACKRANGE = 200;
	
	static SpriteSheet sheet = new SpriteSheet("spritesheets/enemy_three_spritesheet.png", WIDTH, HEIGHT, SPRITEROWS, SPRITECOLS);
	
	private BufferedImage oldImage, currentImage = sheet.getImage(0, 0);
	
	private int counter = 0, xSprite = 0, ySprite = 0, turningPoint;
	
	public boolean isPassive = true, isAttacking = true;
	
	private final int RUNSPEED = 8, MOVESPEED = 5;
	
	public int direction = 1, velX, velY = 0, xOrigin;
	
	private int attackDamage;
	
	public Knight(int x, int y)
	{
		super(x, y, WIDTH, HEIGHT, sheet);
		this.xOrigin = x;
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
	public BufferedImage changeImages()
	{
		oldImage = currentImage;
		counter++;
		
		//Just sets Sprites and running Speeds.
		if (counter >= 5) { //Should be 5
			testCollision();
			testAction();
			
			if(changeDirectionOrNaw())
				direction *= -1;
			if (isPassive) 
			{
				setVelx(MOVESPEED * direction);
				if (ySprite == 0) {
					ySprite++;
				} else {
					ySprite = 0;
				}
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
			//System.out.println(direction);
			if (direction == 1)
			{
				currentImage = sheet.getImage(xSprite, ySprite);
			}
			else
			{
				currentImage = sheet.getFlippedImage(xSprite, ySprite);
			}
			counter = 0;
		}
		this.x += velX;
		this.y += velY;
		return currentImage;	
	}
	private boolean changeDirectionOrNaw() {
		/*
		 * Tests if the difference between the enemy's
		 * x-Coordinate and Origin would be closer to the origin
		 * with or without the direction added.
		 */
		if (Math.abs(this.x - xOrigin) < (Math.abs((this.x + direction) - xOrigin)) && outsideRange())
		{
			return true;
		}
		return false;
	}
	/*@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(currentImage, x, y, null);
	}*/
	private boolean outsideRange() {
		if (Math.abs(this.x - xOrigin) > RANGE)
		{
			return true;
		}
		return false;
	}
	private void setVelx(int vel) {
		this.velX = vel;
	}	
	public BufferedImage getImage(){
		return currentImage;
	}
	@Override
	public void setIsAttacking(boolean b) {
		this.isAttacking = b;
		
	}
	
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
	
	public void setIsPassive(boolean p){
		this.isPassive = p;
	}
	
	public boolean isPassive(){
		return isPassive;
	}
	public boolean isAttacking(){
		return isAttacking;
	}
	
	public int getDirection(){
		return direction;
	}

}