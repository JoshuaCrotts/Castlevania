package castlevania;

public class Ghoul extends Enemy{
	
	final static int WIDTH = 5;
	final static int HEIGHT = 10;
	final static int SPRITEROWS = 1;
	final static int SPRITECOLS = 2;
	static SpriteSheet sheet = new SpriteSheet("spritesheets/enemy_one_sheet.png", WIDTH, HEIGHT, SPRITEROWS, SPRITECOLS);
	public Ghoul(int x, int y)
	{
		super(x, y, WIDTH, HEIGHT, sheet);
	}
}
