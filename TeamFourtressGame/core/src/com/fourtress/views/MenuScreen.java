package com.fourtress.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.fourtress.ScreenType;
import com.fourtress.TeamFourtressGame;
import com.fourtress.utils.MyAssetManager;
import com.fourtress.utils.SoundManager;

public class MenuScreen implements Screen {
	
	private TeamFourtressGame parent;
	private MyAssetManager assets;
	private Stage stage;
	
	private Label.LabelStyle lblStyle;
	private TextButtonStyle style;
	
	private TextButton newGame;
	private TextButton preferences;
	private TextButton gameControls;
	private TextButton exit;
	private Label titleLabel;
	
	
	public MenuScreen(TeamFourtressGame tfg) {
		parent = tfg;
		assets = MyAssetManager.getInstance();
		stage = new Stage(new ScreenViewport());
		
		// UI Setup
		style = assets.getTextButtonStyle();		
		lblStyle = assets.getLabelStyle();
		
		titleLabel = new Label("FOURTRESS ESCAPE!", lblStyle);
		newGame = new TextButton("New Game", style);
		preferences = new TextButton("Preferences", style);
		gameControls = new TextButton("Game Controls", style);
		exit = new TextButton("Exit", style);
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		addListeners();
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		table.add(titleLabel).padBottom(50);
		table.row();
		table.add(newGame).fillX().uniformX().padBottom(10);
		table.row();
		table.add(preferences).fillX().uniformX().padBottom(10);;
		table.row();
		table.add(gameControls).fillX().uniformX().padBottom(10);;
		table.row();
		table.add(exit).fillX().uniformX();
		
		// Music setup
		//SoundManager.playMusic("audio/music/CatMouse.mp3");
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
	}
	
	private void addListeners() {
		newGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				SoundManager.dispose();
				parent.changeScreen(ScreenType.GAME);
			}
		});
		
		preferences.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(ScreenType.PREFERENCES);
			}
		});
		
		gameControls.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(ScreenType.CONTROLS);
			}
		});
		
		exit.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				SoundManager.dispose();
				Gdx.app.exit();				
			}
		});
	}

}
