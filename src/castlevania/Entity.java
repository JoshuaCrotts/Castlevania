package castlevania;

import javax.swing.JComponent;


public abstract class Entity extends JComponent{

	private static final long serialVersionUID = 9043644151166500825L;
	private int width;
	private int height;
	private SpriteSheet sheet;
	
	public Entity(int width, int height, SpriteSheet sheet)
	{
		this.width = width;
		this.height = height;
		this.sheet = sheet;
	}
	public Entity(SpriteSheet sheet)
	{
		this.sheet = sheet;
	}

	public Entity() {
		// TODO Auto-generated constructor stub
	}
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public SpriteSheet getSheet() {
		return sheet;
	}

	public void setSheet(SpriteSheet sheet) {
		this.sheet = sheet;
	}
}