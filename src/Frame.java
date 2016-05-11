package castlevania;

import javax.swing.JPanel;

public class Frame extends JPanel{

	private Player p;
	
	public Frame()
	{
		p = new Player(0, p.getHEIGHT()-128);
	}
}
