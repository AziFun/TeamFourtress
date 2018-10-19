package com.fourtress;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fourtress.model.Room;
import com.fourtress.views.LoadingScreen;
import com.fourtress.views.MenuScreen;
import com.fourtress.views.PreferencesScreen;

public class TeamFourtressGame extends Game {

	private LoadingScreen loadingScreen;
	private MenuScreen menuScreen;
	private PreferencesScreen preferencesScreen;
	private ApplicationPreferences preferences;
	
	@Override
	public void create () {
		preferences = new ApplicationPreferences();
		menuScreen = new MenuScreen(this);
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
		default: 
		}
	}
	
	public ApplicationPreferences getPreferences() {
		return preferences;
	}
}
