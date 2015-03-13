package org.TheGame.sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.TheGame.main.resourcemanagement.Configuration;

public class SFXThread extends Thread {
	
	private File toPlay;
	
	public SFXThread(String name) {
		toPlay = null;
		try {
			Configuration configuration = Configuration.getInstance();
			String soundDirectoryPath = configuration.getValue("applicationPath")
					+ configuration.getValue("soundDirectory");
			toPlay = new File(soundDirectoryPath + name);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		if(toPlay != null) {
			try {
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(toPlay);
				Clip clip = AudioSystem.getClip();
			    clip.open(audioIn);
			    clip.start();
			 } catch (UnsupportedAudioFileException e) {
			    e.printStackTrace();
			 } catch (IOException e) {
			    e.printStackTrace();
			 } catch (LineUnavailableException e) {
			    e.printStackTrace();
			 }
		}
	}

}
