package com.fourtress.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
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
	
	public GameScreen(TeamFourtressGame parent) {
		this.parent = parent;
		cam = new OrthographicCamera(32,24);
		stage = new Stage(new ScreenViewport());
		controller = new KeyboardController();
		model = new Box2dModel(cam, controller, this);
		debugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);
		sb = new SpriteBatch();
		sb.setProjectionMatrix(cam.combined);
		
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
		//debugRenderer.render(model.world, cam.combined);
		sb.begin();
		Texture playerSprite = new Texture(Gdx.files.internal("witek.png"));
		Texture keySprite = new Texture(Gdx.files.internal("assets/key.png"));
		Texture background = new Texture(Gdx.files.internal("background.png"));
		skin = new Skin(Gdx.files.internal("assets/visui/assets/uiskin.json"));
		sb.draw(background,-10,-10,20,20);
		sb.draw(playerSprite,model.player.getPosition().x-1,model.player.getPosition().y-1,2,2);
		System.out.println("Before");
		
		Dialog welcome = new Dialog("Welcome to Team Fourtress!",skin);
		welcome.show(stage);
		welcome.setPosition(220,50);
		Timer.schedule(new Task(){
			@Override
			public void run() {
				welcome.hide();
			}
		},5);
		
		System.out.println("After");


				
		Texture wallSprite = new Texture(Gdx.files.internal("wall.png"));
		Texture doorSprite = new Texture(Gdx.files.internal("door.png"));
		sb.draw(background, -10, -10, 20, 20);
		sb.draw(wallSprite, -10, -11, 20, 2);
		sb.draw(wallSprite, -10, 9, 8, 2);
		sb.draw(wallSprite, 2, 9, 8, 2);
		sb.draw(wallSprite, -11, -11, 2, 20);
		sb.draw(wallSprite, 9, -11, 2, 20);
		welcome.hide();

		if (model.door.isActive()) {
			sb.draw(doorSprite, model.door.getPosition().x - 2, model.door.getPosition().y - 1, 4, 4);
		}
		sb.draw(playerSprite, model.player.getPosition().x - 1, model.player.getPosition().y - 1, 2, 2);
		if (!controller.switchAvailable) {
			sb.draw(keySprite, model.key.getPosition().x - 1, model.key.getPosition().y - 1, 2, 2);
		}
		if (model.keyIndicator != null) {
			sb.draw(keySprite, model.keyIndicator.getPosition().x, model.keyIndicator.getPosition().y, 2, 2);
		}
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
		// TODO Auto-generated method stub

	}

}
