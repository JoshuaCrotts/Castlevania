package castlevania;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Game extends JFrame implements Runnable, KeyListener {

	public final static int WIDTH = 800, HEIGHT = 600, SCROLLSPOT = 330;

	private boolean running = false;
	public static Player p;
	private Graphics g;
	private Thread t;
	private GUI gui;
	private boolean aIsDown = false, wIsDown = false, sIsDown = false,
			dIsDown = false;
	static boolean isDead = false;
	
	public static boolean getIsDead(){
		return isDead;
	}
	
	public static void setIsDead(boolean b){
		isDead = b;
	}
	
	private Image dbImage;
	private Graphics dbg;
	private boolean stillGoing = true;
	public static boolean shouldShow = false; // controls visibility of tFrame
	public static boolean shouldShow2 = false; // controls visibility of
	// lDoneFrame

	public boolean isLevelDone = false;
	public boolean isLevelOneDone = false;
	public boolean isLevelTwoDone = false;
	public boolean isLevelThreeDone = false;

	public static int levelNumber = 0;

	public static int getLevelNumber() {
		return levelNumber;
	}

	// private LevelList levels = new LevelList();
	private int oldHealth, loop = 0;
	private TitleFrame tFrame = new TitleFrame();

	private Audio complete = new Audio("music/stageclear.wav");
	private boolean isStart = true;

	private Level[] levels = {
			new Level("levels/level1bg.png", new Audio(
					"music/vampirekiller.wav")),
			new Level("levels/level2bg.png",
					new Audio("music/monsterdance.wav")),
			new Level("levels/level3bg.png", new Audio("music/wickedchild.wav")) };

	public Game() {
		tFrame.show();
		while (shouldShow == false) {
			System.out.println(); // WHYYYYYYYYYYYYYYYYYYYYYYYYYYYY?
		}
		;
		shouldShow = true;
		p = new Player(0, HEIGHT - 128);
		oldHealth = p.getHealth();

		// levels[0].getEnemyArrayList().add(new Ghoul(0, HEIGHT - 128));
		// levels[0].getEnemyArrayList().add(new Ghoul(100, HEIGHT - 128));
		// levels[0].getEnemyArrayList().add(new Ghoul(200, HEIGHT - 128));
		// levels[0].getEnemyArrayList().add(new Ghoul(900, HEIGHT - 128));

		addEnemies(0);
		addEnemiesToFrame(0);

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
				Thread.sleep(17L); // 17L
				// repaint();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// repaint();
		}
	}

	public void stop() {
		running = false;
	}

	public void keyPressed(KeyEvent e) {
		if (!isLevelDone) {
			p.isRunning = false;
			int keyCode = e.getKeyCode();
			switch (keyCode) {
			case KeyEvent.VK_W:
				wIsDown = true;
				if (p.clearBelow() && !p.isJumping) {
					p.resetTime(); // Using v = v_i + a*t for velocity, so need
					// to reset time.
				}

				p.jump();
				break;
			/*
			 * case KeyEvent.VK_S: sIsDown = true; p.setY(p.getY() + 10); break;
			 * 
			 * This is commented out because we don't need 'S' to do anything
			 * yet.
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
				// g.drawImage(p.getSheet().getImage(0,1),p.getX(),p.getY(),
				// null);
				p.isAttacking = true;
				break;
			}
			if (aIsDown || dIsDown) {
				p.isRunning = true;
			}
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

		if (!aIsDown && !dIsDown) // This is where I have to fix things. Along
		// with the below if statement.
		{
			p.isStanding = true;
		}

		if ((aIsDown || dIsDown) && !p.isJumping) {
			p.isRunning = true;
			p.isStanding = false; // Changed this.
		}
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void update(Graphics g) {
		super.paint(g);
	}

	@Override
	public void paint(Graphics g) {
		dbImage = createImage(getWIDTH(), getHEIGHT());
		dbg = dbImage.getGraphics();

		paintComponent(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}

	public void paintLevel(Graphics g) {
		//if (!isLevelOneDone) {// Basically, if the first level isn't done it's
			// gonna do this if statement only.
		System.out.println("Level Number" + levelNumber);
		levels[levelNumber].play();// plays the music for the first level
		
		//Player can't go back
		if ((p.getX() - levels[levelNumber].getX() <= 0) && stillGoing)
			p.setX(levels[levelNumber].getX());
		
		//Player can't go more forward
		if ((p.getX() - levels[levelNumber].getX()) >= 330 && stillGoing) {
			levels[levelNumber].scrollImage(p.getX());
			levels[levelNumber].paintComponent(g); // repaints the Level object.
		}
		//This statement will be when the level should be done
		if (Math.abs(levels[levelNumber].getX()) > 7170 && stillGoing) {
			setVisible(false);
			//Stop the level from scrolling
			levels[levelNumber].setX(7170);
			//repaint level
			levels[levelNumber].paintComponent(g);
			stillGoing = false; //Level over
			levels[levelNumber].getMusic().stop();
			complete.play();// plays the victory theme
			// this should be simple enough
			int reply = JOptionPane.showConfirmDialog(null,
					"You beat Level " + (getLevelNumber() + 1)
							+ ", continue?\nGame Will Load For 3 Seconds.",
					"Continue", JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.NO_OPTION)
				System.exit(0);
			else {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				switch (levelNumber) {
				case 0:
					isLevelOneDone = true;
					break;
				case 1:
					isLevelTwoDone = true;
					break;
				case 2: 
					isLevelThreeDone = true;
					break;
				}
				isLevelOneDone = true;
				isLevelDone = true;
				levelNumber++;
				isStart = true;
			}
			// System.exit(0);
		}

		// else{
		levels[levelNumber].paintComponent(g);
		// this loop can be used for changing all of the enemies images then
		// drawing them.
		// for(int i = 0; i<levels[0].getEnemyArrayList().size(); i++){
		// levels[0].getEnemyArrayList().get(0).changeImages();
		// g.drawImage(levels[0].getEnemyArrayList().get(0).getImage(),levels[0].getEnemyArrayList().get(0).getX(),levels[0].getEnemyArrayList().get(0).getY(),null);
		// }

		// checks if aggressive or naw
		for (int i = 0; i < levels[levelNumber].getEnemyAmount(); i++) {
			Enemy temp = levels[levelNumber].getEnemyArrayList().get(i);
			// System.out.println("ABS: " + Math.abs(p.getX() -
			// temp.getX()));
			// System.out.println("IS ATTACKING: " + temp.isAttacking());
		}
		// changes all enemy graphics
		for (int i = 0; i < levels[levelNumber].getEnemyAmount(); i++) {
			levels[levelNumber].getEnemyArrayList().get(i).changeImages();
		}

		// draws the enemies
		for (int i = 0; i < levels[levelNumber].getEnemyAmount(); i++) {
			Enemy temp = levels[levelNumber].getEnemyArrayList().get(i);
			temp.changeImages();
		}
		p.changeImages();
		//Draws player
		g.drawImage(p.getImage(), p.getX() - levels[0].getX(), p.getY(),
				this);
		if (stillGoing)
			gui.paintComponent(g);
	}

	public void paintComponent(Graphics g) {
		
		//paintLevel(g);
		
		// ----------------------------LEVEL 1
		if (!isLevelOneDone) {// Basically, if the first level isn't done it's
			// gonna do this if statement only.
			levels[0].play();// plays the music for the first level
			
			//BRANDON FIX THIS. WHY IS IT NOT SWAPPING THE IMAGES???????????????????????????????????
			if(isDead){
				levels[0].getMusic().stop();
				Audio ad = new Audio("soundeffects/lostlife.wav");
				ad.play();
				Game.p.setBImage(Game.p.getSheet().getImage(0, 0));
				try {
					Thread.sleep(300);
					g.drawImage(p.getImage(), p.getX() - levels[0].getX(), p.getY(),
							this);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Game.p.setBImage(Game.p.getSheet().getImage(3, 1));
				try {
					Thread.sleep(300);
					g.drawImage(p.getImage(), p.getX() - levels[0].getX(), p.getY(),
							this);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Game.p.setBImage(Game.p.getSheet().getImage(3, 2));
				try {
					Thread.sleep(300);
					g.drawImage(p.getImage(), p.getX() - levels[0].getX(), p.getY(),
							this);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.exit(0);
			}
			if ((p.getX() - levels[0].getX() <= 0) && stillGoing) // this if
				// statement
				// says if
				// the
				// player is
				// heading
				// left and
				// trying to
				// exit the
				// screen,
				// they
				// can't.
				p.setX(levels[0].getX());

			if ((p.getX() - levels[0].getX()) >= 330 && stillGoing) {
				levels[0].scrollImage(p.getX());
				levels[0].paintComponent(g); // repaints the Level object.
			}

			if (Math.abs(levels[0].getX()) > 7170 && stillGoing) {// this if
				// statement
				// will be
				// when the
				// level
				// should be
				// done.

				setVisible(false);// sets the frame temporarily to false. You
				// can experiment if you wanna try and make
				// it look better.
				levels[0].setX(7170);// sets the x coordinate to 7170 so it
				// doesn't scroll, but doesn't really
				// work.
				levels[0].paintComponent(g);// repaints level frame
				stillGoing = false;// sets the stillGoing variable to false,
				// trying to control stuff.
				levels[0].getMusic().stop();// stops the music for the Level.
				complete.play();// plays the victory theme
				// levels[0].setX();
				// levels[0].setX(getX());
				// shouldShow = false;

				// this should be simple enough
				int reply = JOptionPane.showConfirmDialog(null,
						"You beat Level " + (getLevelNumber() + 1)
								+ ", continue?\nGame Will Load For 3 Seconds.",
						"Continue", JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.NO_OPTION)
					System.exit(0);
				else {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					isLevelOneDone = true;
					isLevelDone = true;
					levelNumber++;
					isStart = true;
				}
				// System.exit(0);
			}

			// else{
			levels[0].paintComponent(g);
			// this loop can be used for changing all of the enemies images then
			// drawing them.
			// for(int i = 0; i<levels[0].getEnemyArrayList().size(); i++){
			// levels[0].getEnemyArrayList().get(0).changeImages();
			// g.drawImage(levels[0].getEnemyArrayList().get(0).getImage(),levels[0].getEnemyArrayList().get(0).getX(),levels[0].getEnemyArrayList().get(0).getY(),null);
			// }

			//DEDUCTS HEALTH FROM ENEMIES
			for(int i = 0; i<levels[levelNumber].getEnemyAmount(); i++){
				if((Math.abs((levels[levelNumber].getEnemyArrayList().get(i).getX() - p.getX()+128)) <= 250) && p.isAttacking){
					if(levels[levelNumber].getEnemyArrayList().get(i).getHealth() == 0)
						//g.drawImage(IMAGEOFPOOF, levels[levelNumber].getEnemyArrayList().get(i).getX(), levels[levelNumber].getEnemyArrayList().get(i).getX(),this);
						levels[levelNumber].getEnemyArrayList().remove(i);
					else
						levels[levelNumber].getEnemyArrayList().get(i).setHealth(levels[levelNumber].getEnemyArrayList().get(i).getHealth()-1);
				}
			}
			
			// checks if aggressive or naw
			for (int i = 0; i < levels[levelNumber].getEnemyAmount(); i++) {
				Enemy temp = levels[levelNumber].getEnemyArrayList().get(i);
				// System.out.println("ABS: " + Math.abs(p.getX() -
				// temp.getX()));
				// System.out.println("IS ATTACKING: " + temp.isAttacking());
			}
			// changes all enemy graphics
			for (int i = 0; i < levels[levelNumber].getEnemyAmount(); i++) {
				levels[levelNumber].getEnemyArrayList().get(i).changeImages();
			}

			// draws the enemies
			for (int i = 0; i < levels[levelNumber].getEnemyAmount(); i++) {
				Enemy temp = levels[levelNumber].getEnemyArrayList().get(i);
				temp.changeImages();
				g.drawImage(temp.getImage(),
						temp.getX() - levels[levelNumber].getX(), HEIGHT - 128,
						null);
			}

			// System.out.println(levels[0].getEnemyArrayList().get(0).getX());
			p.changeImages();// changes the players images dependent on what
			// they're doing
			g.drawImage(p.getImage(), p.getX() - levels[0].getX(), p.getY(),
					this);// draws
							// the
							// player
							// to
							// screen
			if (stillGoing)
				gui.paintComponent(g);// if the game is still going, it repaints
			// the gui
			
			if(levels[levelNumber].getEnemyAmount() != 0)
			//System.out.println("Position: " +Math.abs(levels[levelNumber].getEnemyArrayList().get(0).getX() - (p.getX() + 128)));
			//System.out.println("Is attacking: "+p.isAttacking );
			System.out.println("HEALTH: "+levels[levelNumber].getEnemyArrayList().get(0).getHealth());

		}






		// -------------------------------------------------------------------------------------
		// LEVEL 2
		if (isLevelOneDone && !isLevelTwoDone) {
			setVisible(true);// resets the frames visibility to true
			shouldShow = true;// essentially a control variable for the
			// statement above
			if (isStart) {// this will only execute on the first pass.
				p = new Player(0, HEIGHT - 128);// resets the player's position.
				System.out.println("Does this even execute");// just a test
				p.setX(0);// below are statements trying to get the player to
				// not move on start.
				stillGoing = true;
				p.setVelx(0);
				p.isRunning = false;
				p.isStanding = true;
				isStart = false;
				isLevelDone = false;
			}
			levels[1].play();
			if (p.getX() <= 0 && stillGoing)
				p.setX(0);

			if (p.getX() > 330 && stillGoing) {
				p.setX(330);
				levels[1].scrollImage(p.getX());
				levels[1].paintComponent(g);
			}

			if (Math.abs(levels[1].getX()) > 7170 && stillGoing) {

				setVisible(false);
				levels[1].setX(7170);
				levels[1].paintComponent(g);
				stillGoing = false;
				levels[1].getMusic().stop();
				Audio a2 = new Audio("music/stageclear.wav");
				a2.play();
				// levels[0].setX();
				// levels[0].setX(getX());
				// shouldShow = false;

				int reply = JOptionPane.showConfirmDialog(null,
						"You beat Level " + (getLevelNumber() + 1)
								+ ", continue?", "Continue",
						JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.NO_OPTION)
					System.exit(0);
				else {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					isLevelTwoDone = true;
					isLevelDone = true;
					levelNumber++;
					isStart = true;
				}
			}

			// else{
			levels[1].paintComponent(g);

			p.changeImages();
			g.drawImage(p.getImage(), p.getX(), p.getY(), this);
			if (stillGoing)
				gui.paintComponent(g);
			// }
		}
		
		
		
		// ----------------------------------------------LEVEL THREE
		if (isLevelOneDone && isLevelTwoDone && !isLevelThreeDone) {
			setVisible(true);// resets the frames visibility to true
			shouldShow = true;// essentially a control variable for the
			// statement above
			if (isStart) {// this will only execute on the first pass.
				p = new Player(0, HEIGHT - 128);// resets the player's position.
				System.out.println("Does this even execute");// just a test
				p.setX(0);// below are statements trying to get the player to
				// not move on start.
				stillGoing = true;
				p.setVelx(0);
				p.isRunning = false;
				p.isStanding = true;
				isStart = false;
				isLevelDone = false;
			}
			levels[2].play();
			if (p.getX() <= 0 && stillGoing)
				p.setX(0);

			if (p.getX() > 330 && stillGoing) {
				p.setX(330);
				levels[2].scrollImage(p.getX());
				levels[2].paintComponent(g);
			}

			if (Math.abs(levels[2].getX()) > 7170 && stillGoing) {

				setVisible(false);
				levels[2].setX(7170);
				levels[2].paintComponent(g);
				stillGoing = false;
				levels[2].getMusic().stop();
				Audio a2 = new Audio("music/stageclear.wav");
				a2.play();
				// levels[0].setX();
				// levels[0].setX(getX());
				// shouldShow = false;

				int reply = JOptionPane.showConfirmDialog(null,
						"You beat Level " + (getLevelNumber() + 1)
								+ ", continue?", "Continue",
						JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.NO_OPTION)
					System.exit(0);
				else {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					isLevelThreeDone = true;
					isLevelDone = true;
					levelNumber++;
					isStart = true;
				}
			}

			// else{
			levels[2].paintComponent(g);

			p.changeImages();
			g.drawImage(p.getImage(), p.getX(), p.getY(), this);
			if (stillGoing)
				gui.paintComponent(g);
			// }
		}
	
	}

	public static int getWIDTH() {
		return WIDTH;
	}

	public static int getHEIGHT() {
		return HEIGHT;
	}

	public void addEnemies(int level) {
		// addE(level,new Ghoul(0, HEIGHT - 128));
		// addE(level,new Ghoul(100, HEIGHT - 128));
		// addE(level,new Ghoul(200, HEIGHT - 128));
		// addE(level,new Ghoul(900, HEIGHT - 128));
		 //  addE(level, new Knight(1250, HEIGHT - 128));
		// addE(level,new Skeleton(1250, HEIGHT-128));
		// addE(level,new Knight(2000,HEIGHT-128));
		
		Random randomInt = new Random();
		int enemyChooser = 1+randomInt.nextInt(3);
		
		for(int i = 0; i<15; i++){
			switch(enemyChooser){
			
			case 1: addE(level, new Ghoul(300+randomInt.nextInt(7170), HEIGHT-128));
			case 2: addE(level, new Skeleton(300+randomInt.nextInt(7170), HEIGHT-128));
			case 3: addE(level, new Knight(300+randomInt.nextInt(7170), HEIGHT-128));
			}
			enemyChooser = 1+randomInt.nextInt(3);
		}
	}

	public void addEnemiesToFrame(int level) {
		for (int i = 0; i < levels[level].getEnemyAmount(); i++) {
			this.getContentPane().add(levels[level].getEnemyArrayList().get(i));
		}
	}

	public void paintEnemies(int level) {
		for (int i = 0; i < levels[level].getEnemyAmount(); i++) {
			Enemy temp = levels[level].getEnemyArrayList().get(i);
			temp.changeImages();
			g.drawImage(temp.getImage(), (temp.getX() - levels[level].getX()),
					temp.getX() - levels[level].getY(), null);
		}
	}

	public void updateEnemyPosition(int level) {
		for (int i = 0; i < levels[level].getEnemyAmount(); i++) {
			levels[level].getEnemyArrayList().get(i).changeImages();
		}
	}

	public void addE(int level, Enemy e) {
		levels[level].getEnemyArrayList().add(e);
	}

	public static Player getPlayer() {
		return p;
	}
}