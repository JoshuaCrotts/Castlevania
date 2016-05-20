package castlevania;

import java.awt.image.BufferedImage;

public class Boss extends Enemy{

	private static final long serialVersionUID = -7424536528712509152L;
	
	public Boss(int x, int y, int WIDTH, int HEIGHT, SpriteSheet SHEET) {
		super(x, y, WIDTH, HEIGHT, SHEET);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public BufferedImage getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setIsAttacking(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setIsPassive(boolean p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getDirection() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isAttacking() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPassive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BufferedImage changeImages() {
		// TODO Auto-generated method stub
		return null;
	}
	
	



}