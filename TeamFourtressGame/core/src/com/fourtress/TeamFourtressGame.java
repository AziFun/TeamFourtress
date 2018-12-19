package com.fourtress;

import com.badlogic.gdx.Game;
import com.fourtress.model.SoundManager;
import com.fourtress.views.FinishScreen;
import com.fourtress.views.GameControls;
import com.fourtress.views.GameOverScreen;
import com.fourtress.views.GameScreen;
import com.fourtress.views.LoadingScreen;
import com.fourtress.views.MenuScreen;
import com.fourtress.views.PauseMenu;
import com.fourtress.views.PreferencesScreen;

public class TeamFourtressGame extends Game {

	private GameScreen gameScreen;
	private LoadingScreen loadingScreen;
	private MenuScreen menuScreen;
	private PauseMenu pauseMenu;
	private PreferencesScreen preferencesScreen;
	private ApplicationPreferences preferences;
	private FinishScreen finishScreen;
	private GameOverScreen gameOverScreen;
	private GameControls gameControlsScreen;
	
	@Override
	public void create () {
		preferences = new ApplicationPreferences();
		menuScreen = new MenuScreen(this);
		if(preferences.isMusicEnabled() == true) {
			SoundManager.toggleMusic(true);
			SoundManager.setMusicVolume(preferences.getMusicVolume());
		} else {
			SoundManager.toggleMusic(false);
			SoundManager.setMusicVolume(preferences.getMusicVolume());
		}		
		if(preferences.isSoundEffectsEnabled() == true) {
			SoundManager.toggleSFX(true);
			SoundManager.setSFXVolume(preferences.getSoundVolume());
		} else {
			SoundManager.toggleSFX(false);
			SoundManager.setSFXVolume(preferences.getSoundVolume());
		}		
		this.setScreen(menuScreen);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}
	
	public void changeScreen(ScreenType s) {
		switch (s) {
		case FINISH:
			if (finishScreen == null) finishScreen = new FinishScreen(this);
			this.setScreen(finishScreen);
			break;
		case GAME:
			if (gameScreen == null) gameScreen = new GameScreen(this);
			this.setScreen(gameScreen);
			menuScreen.dispose();
			break;
		case PAUSE: 
			if (pauseMenu == null) pauseMenu = new PauseMenu(this);
			this.setScreen(pauseMenu);
			break;	
		case LOADING: 
			if (loadingScreen == null) loadingScreen = new LoadingScreen(this);
			this.setScreen(loadingScreen);
			break;
		case MENU:
			if (menuScreen == null) menuScreen = new MenuScreen(this);
			this.setScreen(menuScreen);
			break;
		case PREFERENCES:
			if (preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
			this.setScreen(preferencesScreen);
			break;
		case GAMEOVER:
			if (gameOverScreen == null) gameOverScreen = new GameOverScreen(this);
			this.setScreen(gameOverScreen);
			break;
		case CONTROLS:
			if (gameControlsScreen == null) gameControlsScreen = new GameControls(this);
			this.setScreen(gameControlsScreen);
			break;
		default: 
		}
	}
	
	public ApplicationPreferences getPreferences() {
		return preferences;
	}
}
