package castlevania;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Enemy extends Entity {

    private static final long serialVersionUID = 2003297664785729009L;
    protected int x;
    protected int y;
    protected final int WIDTH;
    protected final int HEIGHT;
    protected SpriteSheet SHEET;
    private int health;
    public boolean isDead = false;
    public int isDeadCounter = 0;
    //private int attackDamage;

    public Enemy(int x, int y, int WIDTH, int HEIGHT, SpriteSheet sheet) {
        this.x = x;
        this.y = y;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }

    public Enemy(int WIDTH, int HEIGHT) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }

    /**
     * This method checks whether or not the player's X and the enemies X are
     * within a "range"
     */
    protected void testCollision() {
        Player player = Game.getPlayer();
        int playerX = player.getX();
        int playerY = player.getY();

        if ((Math.abs(this.x - playerX) <= player.getWIDTH())
                && (Math.abs(this.y - playerY) <= player.getHEIGHT())) {
            player.lose();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public abstract BufferedImage getImage();

    public int getHealth() {
        return health;
    }

    public void setHealth(int h) {
        this.health = h;
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

    public abstract void setIsAttacking(boolean b);

    public abstract void setIsPassive(boolean p);

    public abstract int getDirection();

    public abstract boolean isAttacking();

    public abstract boolean isPassive();
}
