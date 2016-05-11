package castlevania;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

/**Not my source code*/
public class Audio {

	private String fileName;
	private Clip audioClip;
	public Audio(String fileName)
	{
		this.fileName = fileName;
		try{
			File audioFile = new File(fileName);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			AudioFormat format = audioStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			audioClip = (Clip) AudioSystem.getLine(info);
			audioClip.open(audioStream);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void play()
	{
		audioClip.start();
	}
	
	public void stop()
	{
		audioClip.close();
	}
	public String getFileName()
	{
		return fileName;
	}
	
}