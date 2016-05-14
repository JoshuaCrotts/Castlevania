package castlevania;

public abstract class Enemy extends Entity {

	private static final long serialVersionUID = 2003297664785729009L;
	protected int x;
	protected int y;
	protected final int WIDTH;
	protected final int HEIGHT;
	protected SpriteSheet SHEET;
	
	public Enemy(int x, int y, int WIDTH, int HEIGHT, SpriteSheet sheet)
	{
		super(x, y, sheet);
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
	}

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