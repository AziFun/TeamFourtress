package com.fourtress.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
	
	private static Music music;
	private static Sound sfx;
	private static float masterVolume = 1.0f;

	public static void playMusic(String filePath, float volume) {
		// Audio for Music
		if(music != null) {
			music.stop();
			music.dispose();
		}				
		music = Gdx.audio.newMusic(Gdx.files.internal(filePath));
		music.setLooping(true);
		music.setVolume(volume * masterVolume);
		music.play();
	}
	
	public static void playSoundEffects(String filePath, float volume) {
		// Audio for Sound Effects
		sfx = Gdx.audio.newSound(Gdx.files.internal(filePath));
		sfx.loop(volume * masterVolume);
	}

	public static void dispose() {
		if(music != null) {
			music.dispose();
		}
		
		if(sfx != null) {
			sfx.dispose();
		}
	}
}
