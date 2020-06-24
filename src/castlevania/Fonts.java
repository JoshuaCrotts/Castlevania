package castlevania;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.util.ArrayList;

/**
 * Basically, this class is to get a font and utilize it that is CUSTOM, NOT IN
 * FROM THE COMPUTER. i.e, it has to come with the Java game.
 *
 * @author - not all my code, but the basic idea is.
 *
 */
public class Fonts {

    private static ArrayList<Fonts> fonts = new ArrayList<Fonts>();

    private static String fontPath;

    public Fonts(String name) {
        Fonts.fontPath = name;
        registerFont();
    }

    private void registerFont() {
        GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            g.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(fontPath)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addFont(Fonts font) {
        fonts.add(font);
    }
}
