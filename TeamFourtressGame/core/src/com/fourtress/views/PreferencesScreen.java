package com.fourtress.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.fourtress.ScreenType;
import com.fourtress.TeamFourtressGame;
import com.fourtress.model.SoundManager;

public class PreferencesScreen implements Screen {

	private TeamFourtressGame parent;
	private Stage stage;
	private Skin skin;
	private Slider musicSlider;
	private CheckBox musicCheckBox;
	private Slider effectsSlider;
	private CheckBox effectsCheckBox;
	private Label titleLabel;
	private Label volumeMusicLabel;
	private Label volumeEffectsLabel;
	private Label musicOnOffLabel;
	private Label effectsOnOffLabel;
	private TextButton backButton;
	private TextButtonStyle style;

	public PreferencesScreen(TeamFourtressGame tfg) {
		parent = tfg;
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		
		// UI Setup
		skin = new Skin(Gdx.files.internal("skins/glassy/skin/glassy-ui.json"));
		style = new TextButtonStyle(skin.get("small",TextButtonStyle.class));
		
		titleLabel = new Label("PREFERENCES", skin);
		
		// Music
		volumeMusicLabel = new Label("Music Volume", skin);
		musicOnOffLabel = new Label("Toggle Music", skin);
		musicSlider = new Slider(0f, 1f, 0.1f, false, skin);
		musicSlider.setValue(parent.getPreferences().getMusicVolume());
		musicCheckBox = new CheckBox(null, skin);
		musicCheckBox.setChecked(parent.getPreferences().isMusicEnabled());
		// SFX
		volumeEffectsLabel = new Label("Sound Effects Volume", skin);
		effectsSlider = new Slider(0f, 1f, 0.1f, false, skin);
		effectsSlider.setValue(parent.getPreferences().getSoundVolume());
		effectsCheckBox = new CheckBox(null, skin);
		effectsCheckBox.setChecked(parent.getPreferences().isSoundEffectsEnabled());		
		effectsOnOffLabel = new Label("Toggle Sound Effects", skin);
		
		backButton = new TextButton("Back", style);
		addListeners();
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		table.add(titleLabel).width(100).height(50);
		table.row().pad(10, 0, 10, 0);
		table.add(volumeMusicLabel);
		table.add(musicSlider);
		table.row().pad(10, 0, 10, 0);
		table.add(musicOnOffLabel);
		table.add(musicCheckBox);
		table.row().pad(10, 0, 10, 0);
		table.add(volumeEffectsLabel).pad(0,0,0,20);
		table.add(effectsSlider);
		table.row().pad(10, 0, 10, 0);
		table.add(effectsOnOffLabel).pad(0,0,0,20);
		table.add(effectsCheckBox);
		table.row().pad(10, 0, 10, 0);
		table.add(backButton);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	private void addListeners() {
		musicSlider.addListener(new EventListener() {

			@Override
			public boolean handle(Event event) {
				parent.getPreferences().setMusicVolume(musicSlider.getValue());
				SoundManager.setMusicVolume(musicSlider.getValue());
				return false;
			}
		});

		musicCheckBox.addListener(new EventListener() {

			@Override
			public boolean handle(Event event) {
				parent.getPreferences().setMusicEnabled(musicCheckBox.isChecked());
				if(musicCheckBox.isChecked()) {
					SoundManager.toggleMusic(true);
				} else {
					SoundManager.toggleMusic(false);
				}
				return false;
			}
		});

		effectsSlider.addListener(new EventListener() {

			@Override
			public boolean handle(Event event) {
				parent.getPreferences().setSoundVolume(effectsSlider.getValue());
				SoundManager.setSFXVolume(effectsSlider.getValue());
				return false;
			}
		});

		effectsCheckBox.addListener(new EventListener() {

			@Override
			public boolean handle(Event event) {
				parent.getPreferences().setSoundEffectsEnabled(effectsCheckBox.isChecked());
				if(effectsCheckBox.isChecked()) {
					SoundManager.toggleSFX(true);
				} else {
					SoundManager.toggleSFX(false);
				}
				return false;
			}
		});

		backButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(ScreenType.MENU);
			}
		});
	}

}
