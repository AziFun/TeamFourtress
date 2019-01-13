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
import com.fourtress.model.GameState;
import com.fourtress.utils.SoundManager;
import com.fourtress.utils.MyAssetManager;

public class PauseMenu implements Screen {
	
	private TeamFourtressGame parent;
	private Stage stage;
	private MyAssetManager assets;
	private TextButtonStyle style;
	private TextButton restartGame;
	private TextButton continueGame;
	private TextButton returnToMenu;
	private TextButton exit;
	private GameScreen screen;
	private Label titleLabel;
	private Label.LabelStyle lblStyle;

	
	public PauseMenu(TeamFourtressGame tfg, GameScreen screen) {
		parent = tfg;
		stage = new Stage(new ScreenViewport());
		this.screen = screen;
		assets = MyAssetManager.getInstance();
		lblStyle = assets.getLabelStyle();		
		style = assets.getTextButtonStyle();
		restartGame = new TextButton("Restart Game", style);
		continueGame = new TextButton("Continue Game", style);
		returnToMenu = new TextButton("Back to Menu", style);
		titleLabel = new Label("GAME PAUSED", lblStyle);
		exit = new TextButton("Exit", style);
		stage.act(Gdx.graphics.getDeltaTime());
		addListeners();
	
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		table.add(titleLabel).padBottom(40);
		table.row();
		table.add(continueGame).fillX().uniformX().pad(10, 0, 0, 0);
		table.row();
		table.add(restartGame).fillX().uniformX().pad(10, 0, 0, 0);
		table.row();
		table.add(returnToMenu).fillX().uniformX().pad(10, 0, 0, 0);
		table.row();
		table.add(exit).fillX().uniformX().pad(10, 0, 10, 0);
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
		continueGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				screen.setState(GameState.RESUME);
				parent.changeScreen(ScreenType.GAME);
			}
		});
		
		restartGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				screen.setState(GameState.RESTART);
				parent.changeScreen(ScreenType.GAME);
			}
		});
		
		returnToMenu.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				screen.setState(GameState.ENDGAME);
				parent.changeScreen(ScreenType.GAME);
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
