package com.fourtress.views;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.fourtress.ScreenType;
import com.fourtress.TeamFourtressGame;
import com.fourtress.controller.KeyboardController;
import com.fourtress.model.Box2dModel;
import com.fourtress.model.DoorData;
import com.fourtress.model.Item;
import com.fourtress.model.Level;
import com.fourtress.utils.MyAssetManager;
import com.fourtress.utils.SoundManager;
import com.fourtress.utils.BodyFactory;
import com.fourtress.utils.GameTimer;
import com.fourtress.utils.LevelFactory;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class GameScreen extends ScreenAdapter {

	private Box2dModel model;
	private OrthographicCamera gameCam;
	private OrthographicCamera uiCam;
	private Box2DDebugRenderer debugRenderer;
	public TeamFourtressGame parent;
	private MyAssetManager assets;
	private KeyboardController controller;
	private SpriteBatch sb;
	private Stage stage;
	private Skin skin;
	BitmapFont font;
	Dialog welcome;
	private OrthogonalTiledMapRenderer mapRenderer;
	private ShapeRenderer shapeRenderer;
	private Level level;
	public TextArea textArea;
	public Label timerLabel;
	public Label inventoryDisplay;
	public Image actionIndicator;
	public GameTimer timer;
	private float elapsed;
	private int levelNo = 1;
	private boolean nextLevelReady = false;
	private Animation<TextureRegion> playerUpAnimation;
	private Animation<TextureRegion> playerDownAnimation;
	private Animation<TextureRegion> playerLeftAnimation;
	private Animation<TextureRegion> playerRightAnimation;
	private float animationTime = 0;

	public GameScreen(TeamFourtressGame parent) {
		this.parent = parent;
		float w = 60;
		float h = 34;

		assets = MyAssetManager.getInstance();
		loadAssets();

		// Camera setup
		gameCam = new OrthographicCamera(w, h);
		uiCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), uiCam));
		gameCam.zoom = 0.7f;
		gameCam.update();

		// Controller setup
		controller = new KeyboardController();
		model = new Box2dModel(controller, this);

		// Debug for when required
		debugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);

		// Sprite setup
		sb = new SpriteBatch();
		sb.setProjectionMatrix(uiCam.combined);
		skin = assets.getGameSkin();

		// Music setup
		SoundManager.playMusic(assets);

		// Map setup
		LevelFactory levelGen = LevelFactory.getInstance();
		level = levelGen.makeLevel(levelNo, model);
		mapRenderer = new OrthogonalTiledMapRenderer(level.getTiledMap(), 1 / BodyFactory.ppt);
		shapeRenderer = new ShapeRenderer();

		// Text Area Setup
		Table table = new Table();
		table.setFillParent(true);
		table.left().top();
		// Table debug for when required
		// table.debugAll();
		textArea = new TextArea("Welcome to TeamFourtress!\n", skin);
		textArea.setColor(Color.BLACK);
		skin.getFont("default-font").getData().scale(0.2f);
		table.add(textArea).grow().pad(10);
		timerLabel = new Label("", skin);
		timerLabel.setFontScale(2);
		;
		table.add(timerLabel).top().expandX();
		table.add().grow();
		table.row();
		table.add().grow();
		table.row();
		table.add().grow();
		table.row();
		inventoryDisplay = new Label("", skin);
		model.inventory.addListener(new ChangeListener<Map<Integer, Item>>() {
			@Override
			public void changed(ObservableValue<? extends Map<Integer, Item>> observable, Map<Integer, Item> oldValue, Map<Integer, Item> newValue) {
				inventoryDisplay.setText(formatInventory(newValue));
			}
		});
		table.add(inventoryDisplay).grow();
		table.add().grow();
		actionIndicator = new Image(assets.getActionIndicator());
		actionIndicator.setScaling(Scaling.fit);
		table.add(actionIndicator).bottom().right().size(100);

		// Table debug for when required
		// table.debug();
		stage.addActor(table);

		textArea.appendText(level.getInitialMessage() + "\n");
	}

	/*
	 * Things here are reloaded each level
	 */
	public void setup() {
		assets.loadLevel(levelNo);
		model = new Box2dModel(controller, this);
		LevelFactory levelGen = LevelFactory.getInstance();
		level = levelGen.makeLevel(levelNo, model);
		mapRenderer = new OrthogonalTiledMapRenderer(level.getTiledMap(), 1 / BodyFactory.ppt);
	}

	private void loadAssets() {
		switch (levelNo) {
		case 1:
			assets.loadLevel1();
			break;
		case 2:
			assets.loadLevel2();
			break;
		case 3:
			assets.loadLevel3();
		}
		Texture walkSheet = assets.getPlayerSpriteSet();
		TextureRegion[][] region = TextureRegion.split(walkSheet, walkSheet.getWidth() / 3, walkSheet.getHeight() / 4);
		TextureRegion[] upFrames = new TextureRegion[3];
		upFrames[0] = region[2][1];
		upFrames[1] = region[2][0];
		upFrames[2] = region[2][2];
		TextureRegion[] downFrames = new TextureRegion[3];
		downFrames[0] = region[0][1];
		downFrames[1] = region[0][0];
		downFrames[2] = region[0][2];
		TextureRegion[] leftFrames = new TextureRegion[3];
		leftFrames[0] = region[3][1];
		leftFrames[1] = region[3][0];
		leftFrames[2] = region[3][2];
		TextureRegion[] rightFrames = new TextureRegion[3];
		rightFrames[0] = region[1][1];
		rightFrames[1] = region[1][0];
		rightFrames[2] = region[1][2];

		playerUpAnimation = new Animation<TextureRegion>(0.25f, upFrames);
		playerDownAnimation = new Animation<TextureRegion>(0.25f, downFrames);
		playerLeftAnimation = new Animation<TextureRegion>(0.25f, leftFrames);
		playerRightAnimation = new Animation<TextureRegion>(0.25f, rightFrames);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(controller);

		// Music setup
		SoundManager.playMusic(assets);

		// Timer setup
		timer = new GameTimer();
	}

	@Override
	public void render(float delta) {
		if (nextLevelReady) {
			nextLevel();
		}
		model.logicStep(delta);
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gameCam.position.set(model.player.getPosition(), gameCam.position.z);
		gameCam.update();
		mapRenderer.setView(gameCam);
		mapRenderer.render();
		// Debug Renderer for when required
		 debugRenderer.render(model.world, gameCam.combined);
		TextureRegion currentFrame = getFrame(delta);
		sb.begin();
		sb.draw(currentFrame, (uiCam.viewportWidth / 2) - 50, (uiCam.viewportHeight / 2) - 40, 100, 100);
		sb.end();

		// Display the time remaining for the player to complete the level
		elapsed += delta;

		if (elapsed >= 0.99f) {
			timerLabel.setText(timer.getFormattedMinutes() + " : " + timer.getFormattedSeconds());
			elapsed = 0f;

			if (timer.getTimeUp() == true) {
				parent.changeScreen(ScreenType.GAMEOVER);
			}
		}

		shapeRenderer.setAutoShapeType(true);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setProjectionMatrix(gameCam.combined);
		shapeRenderer.setColor(Color.CYAN);

		for (Body door : model.physicsObjects) {
			DoorData doorData = (DoorData) door.getUserData();
			shapeRenderer.rect(doorData.doorBody.getRectangle().x / BodyFactory.ppt, doorData.doorBody.getRectangle().y / BodyFactory.ppt, doorData.hingeCentre.x / BodyFactory.ppt, doorData.hingeCentre.y / BodyFactory.ppt,
					doorData.doorBody.getRectangle().width / BodyFactory.ppt, doorData.doorBody.getRectangle().height / BodyFactory.ppt, 1, 1, door.getAngle() * MathUtils.radiansToDegrees);
		}

		shapeRenderer.end();
		if (model.isActionAvailable()) {
			actionIndicator.setVisible(true);
			if (model.isStorageAvailable()) {
				// do something to highlight
			} else {
				// stop doing the thing
			}
		} else {
			actionIndicator.setVisible(false);
		}
		stage.draw();
	}

	public TextureRegion getFrame(float delta) {
		animationTime += delta;
		if (controller.left) {
			return playerLeftAnimation.getKeyFrame(animationTime, true);
		} else if (controller.right) {
			return playerRightAnimation.getKeyFrame(animationTime, true);
		} else if (controller.up) {
			return playerUpAnimation.getKeyFrame(animationTime, true);
		} else if (controller.down) {
			return playerDownAnimation.getKeyFrame(animationTime, true);
		}
		return playerDownAnimation.getKeyFrame(0);
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		uiCam.update();
	}

	public void readyNextLevel() {
		nextLevelReady = true;
	}

	public void nextLevel() {
		tearDown();
		levelNo++;
		setup();
		nextLevelReady = false;
	}

	public void tearDown() {
		model.dispose();
		level.dispose();
		mapRenderer.dispose();
		assets.disposeLevel(levelNo);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		// SoundManager.dispose();

	}

	public Stage getStage() {
		return stage;
	}

	public Level getLevel() {
		return level;
	}

	public Skin getSkin() {
		return skin;
	}

	private String formatInventory(Map<Integer, Item> inventory) {
		String formatted = "";
		for (int i = 1; i < 11; i++) {
			formatted += i + ": " + (inventory.get(i) != null ? inventory.get(i).name : "") + "\n";
		}
		return formatted;
	}

}
