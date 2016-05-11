package castlevania;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class Level extends JComponent{

	private static final long serialVersionUID = 1737878072499692441L;
	private BufferedImage image;
	private boolean loading;
	private boolean won;
	private Audio music;
	private int x = 0;
	private int y = 0;
	private String levelName;
	private Thread t = new Thread();
	private ArrayList<Entity> obstacles;

	public Level(String level, Audio music)
	{
		this.levelName = level;
		try {
			this.setImage(ImageIO.read(new File(level)));
		} catch (IOException e) {

			System.out.println("Error Opening Level File.");
		}

		this.obstacles = new ArrayList<Entity>();
		this.music = music;
		music.play();
		t.start();

	}
	//	public void loadLevel(int index)
	//	{
	//		
	//	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(image,x,y,null);
		repaint();
	}
	
	public void scrollImage()
	{
		this.x -= 10;
	}
	public void stopScroll()
	{
		this.x = getX();
	}
	public String getLevelName(){
		return levelName;
	}
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public boolean isLoading()
	{
		return loading;
	}
	public void setLoading(boolean loading)
	{
		this.loading = loading;
	}

	public boolean isWon() {
		return won;
	}

	public void setWon(boolean won) {
		this.won = won;
	}

	public Audio getMusic() {
		return music;
	}

	public void setMusic(Audio music) {
		this.music = music;
	}

	public void addObstacle(Entity obj)
	{
		obstacles.add(obj);
	}

	public void removeObstacle(Entity obj)
	{
		obstacles.remove(obj);
	}

	public String toString()
	{
		return "Level: " + levelName +"\nMusic: "+ music.getFileName();
	}
	
	public int getX()
	{
		System.out.println(x);
		return x;
	}
	public void setX()
	{
		this.x = 0;
	}
	public void setX(int x)
	{
		this.x = x;
	}

}