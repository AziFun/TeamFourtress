package com.fourtress.views;


import org.omg.CORBA.DomainManagerOperations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.fourtress.TeamFourtressGame;
import com.fourtress.controller.KeyboardController;
import com.fourtress.model.Box2dModel;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.fourtress.model.Level;
import com.fourtress.model.LevelFactory;
import com.fourtress.model.SoundManager;


public class GameScreen extends ScreenAdapter {

	private Box2dModel model;
	private OrthographicCamera cam;
	private Box2DDebugRenderer debugRenderer;
	public TeamFourtressGame parent;
	private KeyboardController controller;
	private SpriteBatch sb;
	private Stage stage;
	private Skin skin;
	PopUpDialog test;
	BitmapFont font;
	Dialog welcome;
	private OrthogonalTiledMapRenderer mapRenderer;
	private ShapeRenderer shapeRenderer;
	private Level level;

	public GameScreen(TeamFourtressGame parent) {
		this.parent = parent;
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		// Camera setup
		cam = new OrthographicCamera(w/10, h/10);
		stage = new Stage(new ScreenViewport());
		cam.position.set(cam.viewportWidth / 2.2f, cam.viewportHeight / 2.2f, 0);
		cam.update();
		
		// Controller setup
		controller = new KeyboardController();
		model = new Box2dModel(cam, controller, this);
		debugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);
		
		// Sprite setup
		sb = new SpriteBatch();
		sb.setProjectionMatrix(cam.combined);
        skin = new Skin(Gdx.files.internal("assets/visui/assets/uiskin.json"));
		
		// Music setup
		SoundManager.playMusic("audio/music/musicbox.mp3");
		
		// Map setup
		LevelFactory levelGen = LevelFactory.getInstance();
		level = levelGen.makeLevel(1, model);
		mapRenderer = new OrthogonalTiledMapRenderer(level.getTiledMap(), 1 / 32f);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(controller);
		//Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {		
		model.logicStep(delta);
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		cam.update();
		mapRenderer.setView(cam);
		mapRenderer.render();
		debugRenderer.render(model.world, cam.combined);
		sb.begin();
		Texture playerSprite = new Texture(Gdx.files.internal("witek.png"));
		Texture keySprite = new Texture(Gdx.files.internal("assets/key.png"));		
		sb.draw(playerSprite, model.player.getPosition().x - 1,
		model.player.getPosition().y - 1, 2, 2);
		sb.end();
        stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
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
		SoundManager.dispose();

	}
	
	public Stage getStage() {
		return stage;
	}
	
	public Skin getSkin() {
		return skin;
	}

}
