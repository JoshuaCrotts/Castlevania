package castlevania;

import java.awt.image.BufferedImage;

public abstract class Item extends Entity {

	private static final long serialVersionUID = -1265818783182436592L;
	private int itemNum;
	private String itemType;
	private SpriteSheet sheet;
	private BufferedImage image;
	
	public Item(String itemType, SpriteSheet sheet, BufferedImage image)
	{
		super(sheet);
		this.itemType = itemType;
		this.image = image;
	}
	
	public Item(String itemType, SpriteSheet sheet)
	{
		this.itemType = itemType;
		this.sheet = sheet;
	}
	public Item(String itemType, BufferedImage image)
	{
		this.itemType = itemType;
		this.image = image;
	}

	public int getItemNum() {
		return itemNum;
	}

	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public SpriteSheet getSheet() {
		return sheet;
	}

	public void setSheet(SpriteSheet sheet) {
		this.sheet = sheet;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	
	
}