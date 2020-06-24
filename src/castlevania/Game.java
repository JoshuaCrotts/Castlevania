package castlevania;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Game extends JFrame implements Runnable, KeyListener {

    private static final long serialVersionUID = 6853357271943590808L;

    public final static int WIDTH = 800, HEIGHT = 600, SCROLLSPOT = 330;

    private boolean running = false;
    public static Player p;
    private Graphics g;
    private GUI gui;
    private boolean aIsDown = false, dIsDown = false;
    public static boolean isDead = false;

    public static int difficulty = 0;

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
    public boolean isLevelFourDone = false;

    public static int levelNumber = 0;

    public static int isDeadCounter = 0;

    private TitleFrame tFrame = new TitleFrame();

    private Audio complete = new Audio("music/stageclear.wav");
    private boolean isStart = true;

    private static Level[] levels = {
        new Level("levels/level1bg.png", new Audio(
        "music/vampirekiller.wav")),
        new Level("levels/level2bg.png",
        new Audio("music/monsterdance.wav")),
        new Level("levels/level3bg.png", new Audio("music/wickedchild.wav")),
        new Level("levels/boss1.png", new Audio("music/heartoffire.wav"))};

    /**
     * Just the Game constructor. Adds the enemies to the frames, sets sizes,
     * adds the GUI, the player, etc.
     */
    public Game() {
        tFrame.show();
        while (shouldShow == false) {
            System.out.println(); // WHYYYYYYYYYYYYYYYYYYYYYYYYYYYY?
        }
        ;
        shouldShow = true;
        p = new Player(0, HEIGHT - 128);
        p.getHealth();

        final int LEVEL_COUNT = 2;

        for (int i = 0; i <= LEVEL_COUNT; i++) {
            addEnemies(i);
            addEnemiesToFrame(i);
        }

        addBoss(LEVEL_COUNT + 1);

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
        new Thread(this);

        setVisible(shouldShow);
        addKeyListener(this);
    }

    /**
     * starts thread
     */
    public void start() {
        run();
    }

    /**
     * Does the game loop, runs at ~60 FPS
     */
    public void run() {
        running = true;

        while (running) {
            try {
                repaint();
                Thread.sleep(17L); // 17L
                // repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // repaint();
        }
    }

    public void stop() {
        running = false;
    }

    /**
     * Method to tell when a certain key is pressed. Used for the help function
     * and direction and attacks
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (!isLevelDone) {
            p.isRunning = false;
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_W:
                    if (p.clearBelow() && !p.isJumping) {
                        p.resetTime(); // Using v = v_i + a*t for velocity, so need
                    }

                    p.jump();
                    break;
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
                case KeyEvent.VK_H:
                    JOptionPane.showMessageDialog(null, "ALL YOU HAVE TO DO IS SURVIVE.\nPRESS W to Jump\nPress A and D to move forward and backwards\nPress SPACE to Whip/Attack.");
                    break;
            }
            if (aIsDown || dIsDown) {
                p.isRunning = true;
            }

        }
    }

    /**
     * Does stuff when certain keys are released. Stops movement mainly for
     * player
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int i = e.getKeyCode();
        switch (i) {
            case KeyEvent.VK_A:
                aIsDown = false;
                break;
            case KeyEvent.VK_D:
                dIsDown = false;
                break;
            case KeyEvent.VK_W:
                break;
            case KeyEvent.VK_S:
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

    @Override
    public void keyTyped(KeyEvent arg0) {
    }

    @Override
    public void update(Graphics g) {
        super.paint(g);
    }

    /**
     * Double buffers the frame
     */
    @Override
    public void paint(Graphics g) {
        dbImage = createImage(getWIDTH(), getHEIGHT());
        dbg = dbImage.getGraphics();

        try {
            paintComponent(dbg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(dbImage, 0, 0, this);
    }

    /**
     * I don't really know why this method is here; it just kind of repaints
     * everything for some reason.
     *
     * @param g
     */
    public void paintLevel(Graphics g) {
        //if (!isLevelOneDone) {// Basically, if the first level isn't done it's
        // gonna do this if statement only.
        levels[levelNumber].play();// plays the music for the first level
        if (isDead) {
            levels[0].getMusic().stop();
            Audio ad = new Audio("soundeffects/lostlife.wav");
            ad.play();
            Game.isDeadCounter = 0;
            Game.isDead = true;
            try {
                g.drawImage(p.changeImages(), p.getX(), p.getY(), this);
                System.out.println(Game.isDeadCounter);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
        //Player can't go back
        if ((p.getX() - levels[levelNumber].getX() <= 0) && stillGoing) {
            p.setX(levels[levelNumber].getX());
        }

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
            if (reply == JOptionPane.NO_OPTION) {
                System.exit(0);
            } else {
                try {
                    Thread.sleep(4500);
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
                    default:
                        break;
                }
                isLevelOneDone = true;
                isLevelDone = true;
                levelNumber++;
                isStart = true;
            }
        }

        // else{
        levels[levelNumber].paintComponent(g);
        // this loop can be used for changing all of the enemies images then
        // drawing them.
        // checks if aggressive or naw
        for (int i = 0; i < levels[levelNumber].getEnemyAmount(); i++) {
            levels[levelNumber].getEnemyArrayList().get(i);
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
        if (stillGoing) {
            gui.paintComponent(g);
        }
    }

    /**
     * This is the main method of the whole game. This adds the Player to the
     * frame, displays the correct frame Draws the appropriate backgrounds
     * Starts and ends appropriate music Does collision detection
     *
     * @param g
     * @throws IOException
     */
    public void paintComponent(Graphics g) throws IOException {
        if (!isLevelOneDone) {
            initLevelOne(g);
        }
        if (isLevelOneDone && !isLevelTwoDone) {
            initLevelTwo(g);
        }
        if (isLevelOneDone && isLevelTwoDone && !isLevelThreeDone) {
            initLevelThree(g);
        }
        if (isLevelOneDone && isLevelTwoDone && isLevelThreeDone && !isLevelFourDone) {
            initLevelFour(g);
        }
    }

    private void initLevelOne(Graphics g) {
        levels[0].play();// plays the music for the first level
        if (isDead) {
            levels[0].getMusic().stop();
            Audio ad = new Audio("soundeffects/lostlife.wav");
            ad.play();
            Game.isDead = true;
            Game.isDeadCounter = 0;
            try {
                g.drawImage(p.changeImages(), p.getX() - levels[0].getX(), p.getY(), this);
                Game.isDeadCounter++;
                Thread.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.dispose();
            JOptionPane.showMessageDialog(null, "YOU DIED");
            System.exit(0);
        }

        if ((p.getX() - levels[0].getX() <= 0) && stillGoing) {
            p.setX(levels[0].getX());
        }

        if ((p.getX() - levels[0].getX()) >= 330 && stillGoing) {
            levels[0].scrollImage(p.getX());
            levels[0].paintComponent(g); // repaints the Level object.
        }

        if (Math.abs(levels[0].getX()) > 7170 && stillGoing) {
            setVisible(false);
            levels[0].setX(7170);
            levels[0].paintComponent(g);// repaints level frame
            stillGoing = false;
            levels[0].getMusic().stop();// stops the music for the Level.
            complete.play();// plays the victory theme

            // this should be simple enough
            int reply = JOptionPane.showConfirmDialog(null,
                    "You beat Level " + (getLevelNumber() + 1)
                    + ", continue?\nGame Will Load For 3 Seconds.",
                    "Continue", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.NO_OPTION) {
                System.exit(0);
            } else {
                try {
                    Thread.sleep(4500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isLevelOneDone = true;
                isLevelDone = true;
                levelNumber++;
                isStart = true;
            }
        }

        // else{
        levels[0].paintComponent(g);

        //DEDUCTS HEALTH FROM ENEMIES
        for (int i = 0; i < levels[levelNumber].getEnemyAmount(); i++) {
            if ((Math.abs((levels[levelNumber].getEnemyArrayList().get(i).getX() - p.getX())) <= 250) && p.isAttacking && (Math.abs(levels[levelNumber].getEnemyArrayList().get(i).getY() - p.getY()) <= 50)) {
                if (levels[levelNumber].getEnemyArrayList().get(i).getHealth() != 0) {
                    levels[levelNumber].getEnemyArrayList().get(i).setHealth(levels[levelNumber].getEnemyArrayList().get(i).getHealth() - 1);
                } else {
                    levels[levelNumber].getEnemyArrayList().get(i).setX(levels[levelNumber].getEnemyArrayList().get(i).getX());
                    levels[levelNumber].getEnemyArrayList().get(i).isDead = true;
                    g.drawImage(levels[levelNumber].getEnemyArrayList().get(i).changeImages(), levels[levelNumber].getEnemyArrayList().get(i).getX(), levels[levelNumber].getEnemyArrayList().get(i).getY(), null);
                    if (levels[levelNumber].getEnemyArrayList().get(i).isDeadCounter == 7) {
                        levels[levelNumber].getEnemyArrayList().set(i, null);
                        levels[levelNumber].getEnemyArrayList().remove(i);
                    }
                }
            }
        }

        // checks if aggressive or naw
        for (int i = 0; i < levels[levelNumber].getEnemyAmount(); i++) {
            levels[levelNumber].getEnemyArrayList().get(i);
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

        p.changeImages();// changes the players images dependent on what
        g.drawImage(p.getImage(), p.getX() - levels[0].getX(), p.getY(),
                this);
        if (stillGoing) {
            gui.paintComponent(g);
        }
    }

    private void initLevelTwo(Graphics g) {
        // gonna do this if statement only.
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
            gui = new GUI();
            p.setHealth(1000);
            gui.setPlayerHealth(1000);
        }
        levels[levelNumber].play();// plays the music for the first level

        if (isDead) {
            levels[1].getMusic().stop();
            Audio ad = new Audio("soundeffects/lostlife.wav");
            ad.play();
            Game.isDead = true;
            Game.isDeadCounter = 0;
            try {
                g.drawImage(p.changeImages(), p.getX() - levels[levelNumber].getX(), p.getY(), this);
                Game.isDeadCounter++;
                Thread.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.dispose();
            JOptionPane.showMessageDialog(null, "YOU DIED");
            System.exit(0);
        }

        if ((p.getX() - levels[levelNumber].getX() <= 0) && stillGoing) {
            p.setX(levels[levelNumber].getX());
        }

        if ((p.getX() - levels[levelNumber].getX()) >= 330 && stillGoing) {
            levels[levelNumber].scrollImage(p.getX());
            levels[levelNumber].paintComponent(g); // repaints the Level object.
        }

        if (Math.abs(levels[levelNumber].getX()) > 7170 && stillGoing) {
            setVisible(false);
            levels[levelNumber].setX(7170);
            levels[levelNumber].paintComponent(g);
            stillGoing = false;
            levels[levelNumber].getMusic().stop();
            Audio a2 = new Audio("music/stageclear.wav");
            a2.play();

            int reply = JOptionPane.showConfirmDialog(null,
                    "You beat Level " + (getLevelNumber() + 1)
                    + ", continue?", "Continue",
                    JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.NO_OPTION) {
                System.exit(0);
            } else {
                try {
                    Thread.sleep(4500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isLevelTwoDone = true;
                isLevelDone = true;
                levelNumber++;
                isStart = true;
            }
        }

        // else{
        levels[levelNumber].paintComponent(g);
        //DEDUCTS HEALTH FROM ENEMIES
        for (int i = 0; i < levels[levelNumber].getEnemyAmount(); i++) {
            if ((Math.abs((levels[levelNumber].getEnemyArrayList().get(i).getX() - p.getX())) <= 250) && p.isAttacking && (Math.abs(levels[levelNumber].getEnemyArrayList().get(i).getY() - p.getY()) <= 100)) {
                if (levels[levelNumber].getEnemyArrayList().get(i).getHealth() == 0) {
                    levels[levelNumber].getEnemyArrayList().get(i).setX(levels[levelNumber].getEnemyArrayList().get(i).getX());
                    levels[levelNumber].getEnemyArrayList().get(i).isDead = true;
                    g.drawImage(levels[levelNumber].getEnemyArrayList().get(i).changeImages(), levels[levelNumber].getEnemyArrayList().get(i).getX(), levels[levelNumber].getEnemyArrayList().get(i).getY(), null);
                    if (levels[levelNumber].getEnemyArrayList().get(i).isDeadCounter == 7) {
                        levels[levelNumber].getEnemyArrayList().remove(i);
                    }
                } else {
                    levels[levelNumber].getEnemyArrayList().get(i).setHealth(levels[levelNumber].getEnemyArrayList().get(i).getHealth() - 1);
                }
            }
        }

        // checks if aggressive or naw
        for (int i = 0; i < levels[levelNumber].getEnemyAmount(); i++) {
            levels[levelNumber].getEnemyArrayList().get(i);
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

        p.changeImages();// changes the players images dependent on what
        g.drawImage(p.getImage(), p.getX() - levels[levelNumber].getX(), p.getY(),
                this);
        if (stillGoing) {
            gui.paintComponent(g);// if the game is still going, it repaints
        }			// the gui
    }

    private void initLevelThree(Graphics g) {
        setVisible(true);// resets the frames visibility to true
        shouldShow = true;// essentially a control variable for the
        // statement above
        if (isStart) {// this will only execute on the first pass.
            p = new Player(0, HEIGHT - 128);// resets the player's position.
            p.setX(0);
            stillGoing = true;
            p.setVelx(0);
            p.isRunning = false;
            p.isStanding = true;
            isStart = false;
            isLevelDone = false;
            gui = new GUI();
            p.setHealth(1000);
            gui.setPlayerHealth(1000);
        }
        levels[2].play();

        if (isDead) {
            levels[2].getMusic().stop();
            Audio ad = new Audio("soundeffects/lostlife.wav");
            ad.play();
            Game.isDeadCounter = 0;
            Game.isDead = true;
            try {
                g.drawImage(p.changeImages(), p.getX(), p.getY(), this);
                Game.isDeadCounter++;
                Thread.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.dispose();
            JOptionPane.showMessageDialog(null, "YOU DIED");
            System.exit(0);
        }

        if (p.getX() <= 0 && stillGoing) {
            p.setX(0);
        }

        if ((p.getX() - levels[2].getX()) >= 330 && stillGoing) {
            levels[2].scrollImage(p.getX());
            levels[2].paintComponent(g); // repaints the Level object.
        }

        if (Math.abs(levels[2].getX()) > 7170 && stillGoing) {

            setVisible(false);
            levels[2].setX(7170);
            levels[2].paintComponent(g);
            stillGoing = false;
            levels[2].getMusic().stop();
            Audio a2 = new Audio("music/stageclear.wav");
            a2.play();

            int reply = JOptionPane.showConfirmDialog(null,
                    "You beat Level " + (getLevelNumber() + 1)
                    + ", continue?", "Continue",
                    JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.NO_OPTION) {
                System.exit(0);
            } else {
                try {
                    Thread.sleep(4500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isLevelThreeDone = true;
                isLevelDone = true;
                levelNumber++;
                isStart = true;
            }
        }

        levels[levelNumber].paintComponent(g);
        //DEDUCTS HEALTH FROM ENEMIES
        for (int i = 0; i < levels[levelNumber].getEnemyAmount(); i++) {
            if ((Math.abs((levels[levelNumber].getEnemyArrayList().get(i).getX() - p.getX())) <= 250) && p.isAttacking && (Math.abs(levels[levelNumber].getEnemyArrayList().get(i).getY() - p.getY()) <= 100)) {
                if (levels[levelNumber].getEnemyArrayList().get(i).getHealth() != 0) {
                    levels[levelNumber].getEnemyArrayList().get(i).setHealth(levels[levelNumber].getEnemyArrayList().get(i).getHealth() - 1);
                } else {
                    levels[levelNumber].getEnemyArrayList().get(i).setX(levels[levelNumber].getEnemyArrayList().get(i).getX());
                    levels[levelNumber].getEnemyArrayList().get(i).isDead = true;
                    g.drawImage(levels[levelNumber].getEnemyArrayList().get(i).changeImages(), levels[levelNumber].getEnemyArrayList().get(i).getX(), levels[levelNumber].getEnemyArrayList().get(i).getY(), null);
                    if (levels[levelNumber].getEnemyArrayList().get(i).isDeadCounter == 7) {
                        levels[levelNumber].getEnemyArrayList().remove(i);
                    }
                }
            }
        }

        // checks if aggressive or naw
        for (int i = 0; i < levels[levelNumber].getEnemyAmount(); i++) {
            levels[levelNumber].getEnemyArrayList().get(i);
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

        p.changeImages();// changes the players images dependent on what
        g.drawImage(p.getImage(), p.getX() - levels[levelNumber].getX(), p.getY(),
                this);// draws
        if (stillGoing) {
            gui.paintComponent(g);// if the game is still going, it repaints
        }			// the gui
    }

    private void initLevelFour(Graphics g) {
        setVisible(true);// resets the frames visibility to true
        shouldShow = true;
        if (isStart) {// this will only execute on the first pass.
            p = new Player(0, HEIGHT - 128);// resets the player's position.
            p.setX(0);
            stillGoing = true;
            p.setVelx(0);
            p.setVisible(true);
            p.isRunning = false;
            p.isStanding = true;
            isStart = false;
            isLevelDone = false;
            gui = new GUI();
            p.setHealth(1000);
            gui.setPlayerHealth(1000);
        }
        levels[levelNumber].play();// plays the music for the first level

        if (isDead) {
            levels[levelNumber].getMusic().stop();
            Audio ad = new Audio("soundeffects/lostlife.wav");
            ad.play();
            Game.isDead = true;
            Game.isDeadCounter = 0;
            try {
                g.drawImage(p.changeImages(), p.getX() - levels[levelNumber].getX(), p.getY(), this);
                Game.isDeadCounter++;
                Thread.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.dispose();
            JOptionPane.showMessageDialog(null, "YOU DIED");
            System.exit(0);
        }

        if ((p.getX() - levels[levelNumber].getX() <= 0) && stillGoing) {
            p.setX(levels[levelNumber].getX());
        }

        if (p.getX() >= 600 + 128) {
            p.setX(600);
        }

        if (levels[levelNumber].getEnemyAmount() == 0 && stillGoing) {
            setVisible(false);

            levels[levelNumber].paintComponent(g);// repaints level frame
            stillGoing = false;// sets the stillGoing variable to false,
            // trying to control stuff.
            levels[levelNumber].getMusic().stop();// stops the music for the Level.
            Audio a = new Audio("music/c&c.wav");
            a.play();
            JOptionPane.showMessageDialog(null, "YOU WIN! YOU BEAT THE EVIL RED SKELETON!");
            System.exit(0);
        }

        // else{
        levels[levelNumber].paintComponent(g);

        //DEDUCTS HEALTH FROM ENEMIES
        for (int i = 0; i < levels[levelNumber].getEnemyAmount(); i++) {
            if ((Math.abs((levels[levelNumber].getEnemyArrayList().get(i).getX() - p.getX())) <= 100) && p.isAttacking && (Math.abs(levels[levelNumber].getEnemyArrayList().get(i).getY() - p.getY()) <= 100)) {
                if (levels[levelNumber].getEnemyArrayList().get(i).getHealth() != 0) {
                    levels[levelNumber].getEnemyArrayList().get(i).setHealth(levels[levelNumber].getEnemyArrayList().get(i).getHealth() - 1);
                } else {
                    levels[levelNumber].getEnemyArrayList().remove(i);
                }
            }
        }

        // checks if aggressive or naw
        for (int i = 0; i < levels[levelNumber].getEnemyAmount(); i++) {
            levels[levelNumber].getEnemyArrayList().get(i);
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

        p.changeImages();
        g.drawImage(p.getImage(), p.getX() - levels[levelNumber].getX(), p.getY(),
                this);
        if (stillGoing) {
            gui.paintComponent(g);// if the game is still going, it repaints
        }			// the gui
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public void addBoss(int level) {
        addE(level, new RedSkeleton(500, HEIGHT - 128));
    }

    public static Enemy getRedSkeleton() {
        return levels[3].getEnemyArrayList().get(0);
    }

    public void addBossesToFrame(int level) {
        this.getContentPane().add(levels[level].getEnemyArrayList().get(0));
    }

    /**
     * Adds the enemies to @param level, dependent on difficulty.
     *
     * @param level
     */
    public void addEnemies(int level) {
        Random randomInt = new Random();
        int enemyChooser = 1 + randomInt.nextInt(3);
        if (difficulty == 1) {
            for (int i = 0; i < 5; i++) {
                switch (enemyChooser) {

                    case 1:
                        addE(level, new Ghoul(300 + randomInt.nextInt(7170), HEIGHT - 128));
                    case 2:
                        addE(level, new Skeleton(300 + randomInt.nextInt(7170), HEIGHT - 128));
                    case 3:
                        addE(level, new Knight(300 + randomInt.nextInt(7170), HEIGHT - 128));
                    default:
                        break;
                }
                enemyChooser = 1 + randomInt.nextInt(3);
            }
        }

        if (difficulty == 2) {
            for (int i = 0; i < 10; i++) {
                switch (enemyChooser) {

                    case 1:
                        addE(level, new Ghoul(300 + randomInt.nextInt(7170), HEIGHT - 128));
                    case 2:
                        addE(level, new Skeleton(300 + randomInt.nextInt(7170), HEIGHT - 128));
                    case 3:
                        addE(level, new Knight(300 + randomInt.nextInt(7170), HEIGHT - 128));
                    default:
                        break;
                }
                enemyChooser = 1 + randomInt.nextInt(3);
            }
        }
        if (difficulty == 3) {
            for (int i = 0; i < 15; i++) {
                switch (enemyChooser) {

                    case 1:
                        addE(level, new Ghoul(300 + randomInt.nextInt(7170), HEIGHT - 128));
                    case 2:
                        addE(level, new Skeleton(300 + randomInt.nextInt(7170), HEIGHT - 128));
                    case 3:
                        addE(level, new Knight(300 + randomInt.nextInt(7170), HEIGHT - 128));
                    default:
                        break;
                }
                enemyChooser = 1 + randomInt.nextInt(3);
            }
        }
        if (difficulty == 4) {
            for (int i = 0; i < 100; i++) {
                switch (enemyChooser) {

                    case 1:
                        addE(level, new Ghoul(300 + randomInt.nextInt(7170), HEIGHT - 128));
                    case 2:
                        addE(level, new Skeleton(300 + randomInt.nextInt(7170), HEIGHT - 128));
                    case 3:
                        addE(level, new Knight(300 + randomInt.nextInt(7170), HEIGHT - 128));
                    default:
                        break;
                }
                enemyChooser = 1 + randomInt.nextInt(3);
            }
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

    public static boolean getIsDead() {
        return isDead;
    }

    public static void setIsDead(boolean b) {
        isDead = b;
    }
    
    public static int getLevelNumber() {
        return levelNumber;
    }

}
