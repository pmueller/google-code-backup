package org.TheGame.sound;

public class SFXManager {

	public static void playSound(String name) {
		SFXThread sfx = new SFXThread(name + ".wav");
		sfx.start();
	}
	
}
