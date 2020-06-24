package castlevania;

import java.awt.image.BufferedImage;

public class Boss extends Enemy {

    private static final long serialVersionUID = -7424536528712509152L;

    public Boss(int x, int y, int WIDTH, int HEIGHT, SpriteSheet SHEET) {
        super(x, y, WIDTH, HEIGHT, SHEET);
    }

    @Override
    public BufferedImage getImage() {
        return null;
    }

    @Override
    public void setIsAttacking(boolean b) {
    }

    @Override
    public void setIsPassive(boolean p) {
    }

    @Override
    public int getDirection() {
        return 0;
    }

    @Override
    public boolean isAttacking() {
        return false;
    }

    @Override
    public boolean isPassive() {
        return false;
    }

    @Override
    public BufferedImage changeImages() {
        return null;
    }

}
