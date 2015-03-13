package org.TheGame.sound;

import java.io.File;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import org.TheGame.main.GameCore;
import org.TheGame.main.resourcemanagement.Configuration;

public class MusicThread extends Thread {

	protected static MusicThread instance;
	private static String current = "";
	
	public static MusicThread getInstance() {
		if (MusicThread.instance == null) {
			MusicThread.instance = new MusicThread();
		}
		return MusicThread.instance;
	}
	
	protected Sequence sequence;
	protected Sequencer sequencer;
	protected static boolean muted;
	protected static boolean disabled;
	
	public MusicThread() {
		MusicThread.muted = false;
		MusicThread.disabled = false;
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.setLoopCount(-1);
		}
		catch(Exception e){System.out.println(e);}
	}
	
	public void run() {
		/*
		try {
			if(sequence == null) {
				Configuration configuration = Configuration.getInstance();
				String soundDirectoryPath = configuration.getValue("applicationPath")
						+ configuration.getValue("soundDirectory");
				sequence = MidiSystem.getSequence(new File(soundDirectoryPath + "music/test.mid"));
				//System.out.println(soundDirectoryPath + "music/test.mid");
			}
			
			if(!sequencer.isOpen()){
				sequencer.open();
				sequencer.setSequence(sequence);
				sequencer.setLoopCount(-1);
				sequencer.start();
			}
		}
		catch(Exception e){System.out.println(e);}
		*/
	}
	
	public void exit() {
		sequencer.stop();
		sequencer.close();
	}
	
	public void pause() {
		if(sequencer.isRunning())
			sequencer.stop();
	}
	
	public void unpause() {
		if(!sequencer.isRunning() && !MusicThread.disabled)
			sequencer.start();
	}
	
	public void switchState() {
		if(sequencer.isRunning()) {
			pause();
			MusicThread.disabled = true;
		}
		else {
			MusicThread.disabled = false;
			unpause();
		}
	}
	
	public void setMusic(String name) {
		
		try {
			Configuration configuration = Configuration.getInstance();
			String soundDirectoryPath = configuration.getValue("applicationPath")
					+ configuration.getValue("soundDirectory");
			Sequence s = MidiSystem.getSequence(new File(soundDirectoryPath + "music/" + name));
			
			if(sequencer.getSequence() == null || !current.equals(name))
			{
				current = name;
				sequence = s;
				if(sequencer.isRunning())
					sequencer.stop();
				if(sequencer.isOpen())
					sequencer.close();
				/*
				Configuration configuration = Configuration.getInstance();
				String soundDirectoryPath = configuration.getValue("applicationPath")
						+ configuration.getValue("soundDirectory");
				sequence = MidiSystem.getSequence(new File(soundDirectoryPath + "music/" + name));
				*/
				
				
				sequencer.setSequence(sequence);
				sequencer.setLoopCount(-1);
				
				if(!sequencer.isOpen()){
					sequencer.open();
				}
				
				if(!MusicThread.disabled) {	
					if(!sequencer.isRunning())
						sequencer.start();
				}
			}
		}
		catch(Exception e){System.out.println(e);}
		
	}
}
