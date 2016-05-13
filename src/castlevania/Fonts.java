package castlevania;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Fonts {

	private static ArrayList<Fonts> fonts = new ArrayList<Fonts>();
	
	private static String fontPath;
	
	public Fonts(String name)
	{
		Fonts.fontPath = name;
		registerFont();
	}
	
	private void registerFont()
	{
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try{
			g.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(fontPath)));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void addFont(Fonts font)
	{
		fonts.add(font);
	}
}
