package castlevania;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class TitleFrame extends JFrame implements ActionListener{

	public boolean shouldBeVisible = false;
	private static final int WIDTH = Game.getWIDTH();
	private static final int HEIGHT = Game.getHEIGHT();
	public static boolean shouldDestroy = false;
	private Graphics g;
	private BufferedImage icon;
	public JFrame tFrame;
	public TitleFrame()
	{
		Audio m1 = new Audio("music/menumusic.wav");
		m1.play();
		Font customFont = null;
		try {
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/oldschool.ttf")).deriveFont(48f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/oldschool.ttf")));
		} catch (IOException e) {
			e.printStackTrace();
		} catch(FontFormatException e) {
			e.printStackTrace();
		}

		tFrame = new JFrame();
		tFrame.setFont(customFont);
		tFrame.setTitle("Castlevania Title Frame");
		tFrame.setSize(WIDTH, HEIGHT);
		tFrame.setFocusable(true);
		tFrame.setResizable(false);
		tFrame.setLocationRelativeTo(null);
//		Color c = new Color(64,66,66);
//		Color paint = new GradientPaint(0,0,c,600,200,Color.BLACK);
		tFrame.getContentPane().setBackground(Color.BLACK);
		//tFrame.setVisible(true);
		
		final JButton play = new JButton();
		play.setBackground(Color.WHITE);
		play.setText("PLAY GAME");
		play.setFont(customFont);
		play.setSize(467,100);
		play.setLocation(170, 250);
		play.setOpaque(false);
		play.setContentAreaFilled(false);
		play.setBorderPainted(false);
		
		try {
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/oldschool.ttf")).deriveFont(12f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/oldschool.ttf")));
		} catch (IOException e) {
			e.printStackTrace();
		} catch(FontFormatException e) {
			e.printStackTrace();
		}
		
		final JButton help = new JButton();
		help.setBackground(Color.WHITE);
		help.setText("<html>To Play The Game, All You Must Do Is Survive.<br /><br />Use The Space Bar to Attack, <br /><br>W to Jump, A and D To Move Left And Right.</html>");
		help.setFont(customFont);
		help.setSize(800,200);
		help.setLocation(30, 200); 
		help.setOpaque(false);
		help.setVisible(false);
		help.setContentAreaFilled(false);
		help.setBorderPainted(false);

		final JButton madeBy = new JButton();
		madeBy.setBackground(Color.WHITE);
		madeBy.setText("Made by Joshua Crotts And Brandon Willis");
		madeBy.setFont(customFont);
		madeBy.setSize(800,200);
		madeBy.setLocation(10, 440); 
		madeBy.setOpaque(false);
		madeBy.setVisible(true);
		madeBy.setContentAreaFilled(false);
		madeBy.setBorderPainted(false);
		
		try {
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/oldschool.ttf")).deriveFont(24f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/oldschool.ttf")));
		} catch (IOException e) {
			e.printStackTrace();
		} catch(FontFormatException e) {
			e.printStackTrace();
		}
		//easy
		final JButton easy = new JButton();
		easy.setBackground(Color.WHITE);
		easy.setText("EASY");
		easy.setFont(customFont);
		easy.setSize(300,200);
		easy.setLocation(80, 210); 
		easy.setOpaque(false);
		easy.setVisible(false);
		easy.setContentAreaFilled(false);
		easy.setBorderPainted(false);
		
		//med
		final JButton med = new JButton();
		med.setBackground(Color.WHITE);
		med.setText("MEDIUM");
		med.setFont(customFont);
		med.setSize(300,200);
		med.setLocation(240, 210); 
		med.setOpaque(false);
		med.setVisible(false);
		med.setContentAreaFilled(false);
		med.setBorderPainted(false);
		
		//hard
		final JButton hard = new JButton();
		hard.setBackground(Color.WHITE);
		hard.setText("HARD");
		hard.setFont(customFont);
		hard.setSize(300,200);
		hard.setLocation(400, 210); 
		hard.setOpaque(false);
		hard.setVisible(false);
		hard.setContentAreaFilled(false);
		hard.setBorderPainted(false);
		

		//qimpossible
		final JButton impos = new JButton();
		impos.setBackground(Color.WHITE);
		impos.setText("IMPOSSIBLE");
		impos.setFont(customFont);
		impos.setSize(300,200);
		impos.setLocation(250, 300);
		impos.setOpaque(false);
		impos.setVisible(false);
		impos.setContentAreaFilled(false);
		impos.setBorderPainted(false);
		
		
		final JButton exit = new JButton();
		exit.setBackground(Color.WHITE);
		exit.setText("QUIT");
		exit.setFont(customFont);
		exit.setSize(200,200);
		exit.setLocation(300, 270); 
		exit.setOpaque(false);
		exit.setVisible(true);
		exit.setContentAreaFilled(false);
		exit.setBorderPainted(false);

		tFrame.getContentPane().add(play);
		tFrame.getContentPane().add(help);
		tFrame.getContentPane().add(madeBy);
		tFrame.getContentPane().add(exit);
		tFrame.getContentPane().add(easy);
		tFrame.getContentPane().add(med);
		tFrame.getContentPane().add(hard);
		tFrame.getContentPane().add(impos);
		

	    JLabel background = new JLabel(new ImageIcon("images/Castlevania_logo_color.png"));
	    background.setLayout(new BorderLayout());
	    tFrame.getContentPane().add(background);
		
		
		impos.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Audio a = new Audio("soundeffects/menuselect.wav");
				a.play();
				Game.difficulty = 3;
				med.setVisible(false);
				hard.setVisible(false);
				easy.setVisible(false);
				impos.setVisible(false);
				help.setVisible(true);
				exit.setVisible(false);
			}
		});
	    
		easy.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Audio a = new Audio("soundeffects/menuselect.wav");
				a.play();
				Game.difficulty = 0;
				med.setVisible(false);
				hard.setVisible(false);
				easy.setVisible(false);
				help.setVisible(true);
				impos.setVisible(false);
				exit.setVisible(false);
				
			}
		});
		
		med.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Audio a = new Audio("soundeffects/menuselect.wav");
				a.play();
				Game.difficulty = 1;
				med.setVisible(false);
				hard.setVisible(false);
				easy.setVisible(false);
				help.setVisible(true);
				impos.setVisible(false);
				exit.setVisible(false);
				
			}
		});
		
		hard.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Audio a = new Audio("soundeffects/menuselect.wav");
				a.play();
				Game.difficulty = 2;
				med.setVisible(false);
				hard.setVisible(false);
				easy.setVisible(false);
				help.setVisible(true);
				impos.setVisible(false);
				exit.setVisible(false);
				
			}
		});
		


	    
		play.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Audio a = new Audio("soundeffects/menuselect.wav");
				a.play();
				play.setVisible(false);
				exit.setVisible(false);
				easy.setVisible(true);
				med.setVisible(true);
				hard.setVisible(true);
				impos.setVisible(true);
				
			}
		});

		
		help.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Audio a = new Audio("soundeffects/menuselect.wav");
				a.play();
				m1.stop();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				help.setVisible(false);
			//	try{
			//	a = new Audio("music/frontgates.wav");
			//	a.play();
			//	Thread.sleep(7500);
			//	}catch(Exception ex){ex.printStackTrace();}
				Game.shouldShow = true;
				tFrame.setVisible(false);
			}
		});
		
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				Audio a = new Audio("soundeffects/menuselect.wav");
				a.play();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});
		/*
		JButton go = new JButton();
		go.setBackground(Color.WHITE);
		go.setText("<html>You Beat Level "+(Game.getLevelNumber()+1)+"<br/>Click Here To Continue.</html>");
		go.setFont(customFont);
		go.setSize(467,100);
		go.setLocation(170, 250);
		go.setOpaque(false);
		go.setContentAreaFilled(false);
		go.setBorderPainted(false);
		
		go.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Audio a = new Audio("soundeffects/menuselect.wav");
				a.play();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				go.setVisible(false);
				try{
					Thread.sleep(5000);
				}catch(Exception ex){ex.printStackTrace();}
				Game.shouldShow = true;
				Game.shouldShow2 = false;
				shouldBeVisible = false;
				tFrame.setVisible(false);
			}
		});
	*/
		
		tFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	@Override
	public void show()
	{
		if(shouldBeVisible)return;
		tFrame.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}