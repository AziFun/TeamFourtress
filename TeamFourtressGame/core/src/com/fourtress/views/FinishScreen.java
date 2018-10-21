package com.fourtress.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.fourtress.TeamFourtressGame;


public class FinishScreen implements Screen {
	
	private TeamFourtressGame parent;
	private Stage stage;
	private Skin skin;
	private Label label;
	
	public FinishScreen(TeamFourtressGame parent) {
		this.parent = parent;
		stage = new Stage(new ScreenViewport());
		skin = new Skin(Gdx.files.internal("assets/vhs/skin/vhs-ui.json"));
		label = new Label("You Escaped!", skin);
	}

	@Override
	public void show() {
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		table.add(label).fillX().uniform();
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
		// TODO Auto-generated method stub
		
	}

}
