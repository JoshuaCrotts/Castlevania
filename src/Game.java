package castlevania;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Game extends JFrame implements Runnable, KeyListener {

	private final static int WIDTH = 800;
	private final static int HEIGHT = 600;
	
	private boolean running = false;
	private Player p;
	private Graphics g;
	private Thread t;
	private GUI gui;
	private boolean aIsDown = false, wIsDown = false, sIsDown = false,
			dIsDown = false;
	
	private Image dbImage;
	private Graphics dbg;
	private boolean stillGoing = true;
	public static boolean shouldShow = false;
	//private LevelList levels = new LevelList();
	
	private Level[] levels = {new Level("levels/level1bg.png", new Audio("music/vampirekiller.wav"))};
	private int oldHealth, loop = 0;
	private TitleFrame tFrame;
	
	
	public Game() {
		tFrame = new TitleFrame();
		tFrame.show();
		while(shouldShow == false){
			
		};
		shouldShow = true;
		p = new Player(0, HEIGHT - 128);
		oldHealth = p.getHealth();
		gui = new GUI();
		setSize(new Dimension(800, 600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setMaximumSize(new Dimension(WIDTH, HEIGHT));
		add(p);
		add(gui);
		setLocationRelativeTo(null);
		setFocusable(true);
		setResizable(false);
		pack();
		t = new Thread(this);
		
		setVisible(shouldShow);
		addKeyListener(this);
	}

	public void start() {
		run();
	}

	public void run() {
		running = true;

		while (running) {
			try {
				repaint();
				Thread.sleep(17L);
				//repaint();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//repaint();
		}
	}

	public void stop() {
		running = false;
	}

	public void keyPressed(KeyEvent e) {
		p.isRunning = false;
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_W:
			wIsDown = true;
			if(p.clearBelow() && !p.isJumping)
				p.resetTime(); //Using v = v_i + a*t for velocity, so need to reset time.
				p.jump();
			break;
		/*case KeyEvent.VK_S:
			sIsDown = true;
			p.setY(p.getY() + 10);
			break;
			*
			* This is commented out because we don't need 'S' to do anything yet.
			*/
		case KeyEvent.VK_A:
			aIsDown = true;
			p.isStanding = false;
			p.setDirection(-1);
			break;
		case KeyEvent.VK_D:
			dIsDown = true;
			p.isStanding = false;
			p.setDirection(1);
			break;
		case KeyEvent.VK_SPACE:
			Audio whip1 = new Audio("soundeffects/whip1.wav");
			whip1.play();
			//g.drawImage(p.getSheet().getImage(0,1),p.getX(),p.getY(), null);
			p.isSpacePressed = true;
			break;
		}
		if (aIsDown || dIsDown) {
			p.isRunning = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int i = e.getKeyCode();
		switch (i) {
		case KeyEvent.VK_A:
			aIsDown = false;
			break;
		case KeyEvent.VK_D:
			dIsDown = false;
			break;
		case KeyEvent.VK_W:
			wIsDown = false;
			break;
		case KeyEvent.VK_S:
			sIsDown = false;
			break;
		}
		if (!aIsDown && !dIsDown) //This is where I have to fix things. Along with the below if statement.
		{
			if (!p.isJumping)
			{
				p.isRunning = false;
				p.isStanding = true;
			}
		}
		
		if ((aIsDown || dIsDown) && !p.isJumping)
		{
			System.out.println("This is happening");
			p.isRunning = true;
			//p.setVelx(p.);
			p.isStanding = false; //Changed this.
		}

	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void update(Graphics g)
	{
		super.paint(g);
	}
	
	@Override
	public void paint(Graphics g) {
		dbImage = createImage(getWIDTH(), getHEIGHT());
		dbg = dbImage.getGraphics();
		
		paintComponent(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}
	public void paintComponent(Graphics g){
		levels[0].play();
		if(p.getX() <= 0 && stillGoing) 
			p.setX(0);

		if(p.getX() > 330 && stillGoing)
		{
			p.setX(330);
			levels[0].scrollImage();
			levels[0].paintComponent(g);
		}
		
		if(Math.abs(levels[0].getX()) > 7170){
			this.dispose();
			stillGoing = false;
			p.setX(getX());
			levels[0].stopScroll();
			//levels[0].setX();
			//levels[0].setX(getX());
			JOptionPane.showMessageDialog(null, "YOU BEAT LEVEL ONE.");
			
			//System.exit(0);
		}
		
		//else{
		levels[0].paintComponent(g);
		
		p.changeImages();
		g.drawImage(p.getImage(), p.getX(), p.getY(), this);
		gui.paintComponent(g);
		//}
		
	}
	/*@Override
	public void paint(Graphics g) {
		int newHealth = p.getHealth();
		try {
			Thread.sleep(17);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(p.getX() + ", " + p.getY());
		if (loop == 0)
		{
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			p.paintComponent(g);
			gui.paintComponent(g);
		}
		
		if (newHealth != oldHealth)
		{
			gui.setPlayerHealth(newHealth);
			gui.paintComponent(g);
		}
		p.paintComponent(g);
		loop++;
		repaint();
	}*/

	public static int getWIDTH() {
		return WIDTH;
	}

	public static int getHEIGHT() {
		return HEIGHT;
	}
}