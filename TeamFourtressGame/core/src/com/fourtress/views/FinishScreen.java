package com.fourtress.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.fourtress.ScreenType;
import com.fourtress.TeamFourtressGame;
import com.fourtress.model.SoundManager;


public class FinishScreen implements Screen {
	
	private TeamFourtressGame parent;
	private Stage stage;
	private Skin skin;
	private Label escapeMessage;
	private TextButton returnToMenu;
	private TextButtonStyle style;
	
	public FinishScreen(TeamFourtressGame parent) {
		this.parent = parent;
		stage = new Stage(new ScreenViewport());
		
		// UI Setup
		skin = new Skin(Gdx.files.internal("skins/glassy/skin/glassy-ui.json"));
		style = new TextButtonStyle(skin.get("small",TextButtonStyle.class));
		escapeMessage = new Label("YOU ESCAPED!", skin);
		returnToMenu = new TextButton("Return to Main Menu", style);
		addListeners();		

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		table.add(escapeMessage).fillX().uniform();
		table.row().pad(10, 0, 10, 0);
		table.add(returnToMenu);
		
		// Music setup
		SoundManager.playMusic("audio/music/FunkCity.mp3");
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
		SoundManager.dispose();
	}
	
	private void addListeners() {
		returnToMenu.addListener(new ChangeListener() {
	
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				SoundManager.dispose();
				parent.changeScreen(ScreenType.MENU);
			}
		});
	}

}
