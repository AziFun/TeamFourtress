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
import com.fourtress.model.GameState;
import com.fourtress.model.Item;
import com.fourtress.model.Level;
import com.fourtress.utils.MyAssetManager;
import com.fourtress.utils.Sfx;
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
	private TextArea textArea;
	public String textAreaBuffer;
	public Label timerLabel;
	public Label levelLabel;
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
	private boolean typeSoundReady;
	private boolean debug = false;

	private GameState state;
	private int currentSeconds;
	private float w;
	private float h;

	public GameScreen(TeamFourtressGame parent) {
		this.parent = parent;
		this.parent = parent;
		w = 60;
		h = 34;

		assets = MyAssetManager.getInstance();
		loadAssets();

		// Camera setup
		gameCam = new OrthographicCamera(w, h);
		uiCam = new OrthographicCamera(1920, 1080);
		stage = new Stage(new FitViewport(1920, 1080, uiCam));
		gameCam.zoom = 0.7f;
		gameCam.update();

		// Controller setup
		controller = new KeyboardController();
		model = new Box2dModel(controller, this);

		// Debug for when required
		if (debug) {
			debugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);
		}

		// Sprite setup
		sb = new SpriteBatch();
		sb.setProjectionMatrix(uiCam.projection);
		skin = assets.getGameSkin();

		// Music setup
		SoundManager.playMusic(assets);
		typeSoundReady = true;

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

		if (debug) {
			table.debugAll();
		}

		textArea = new TextArea("Welcome to Escape the Fourtress!\n", skin);
		textArea.setColor(Color.BLACK);
		skin.getFont("default-font").getData().scale(0.2f);
		table.add(textArea).grow().pad(10);
		
		// Timer and Level Label Details
		timerLabel = new Label("", skin);
		timerLabel.setFontScale(2);
		levelLabel = new Label(level.levelName, skin);
		levelLabel.setFontScale(2);
		table.add(timerLabel).top();
		table.add(levelLabel).growX().top();
		table.row();
		table.add().grow();
		table.row();
		table.add().grow();
		table.row();
		
		// Inventory GUI Setup
		inventoryDisplay = new Label("", skin);
		guiSetup();
		table.add(inventoryDisplay).grow();
		table.add().grow();

		
		// Interaction Icon
		actionIndicator = new Image(assets.getActionIndicator());
		actionIndicator.setScaling(Scaling.fit);
		table.add(actionIndicator).bottom().right().size(100);
		stage.addActor(table);
		textAreaBuffer = "";
		write(level.getInitialMessage() + "\n");

		// Initial Game State
		state = GameState.READY;

	}

	public void resetLevel() {
		levelNo = 1;
    	model.inventory.clearItems();
    	model.dispose();
    	loadAssets();
    	setup(); 
    	guiSetup();
    	textAreaReset();
		levelLabel.setText(level.levelName);

	}
	
    public void guiSetup() {   	
    	model.inventory.addListener(new ChangeListener<Map<Integer, Item>>() {
			@Override
			public void changed(ObservableValue<? extends Map<Integer, Item>> observable, Map<Integer, Item> oldValue, Map<Integer, Item> newValue) {
				inventoryDisplay.setText(formatInventory(newValue));
			}
		});
    }
    
    public void textAreaReset() {
    	textArea.selectAll();
    	textArea.setText("");
    	write("Welcome to Escape the Fourtress!" + "\n");
    }
    
	public void write(String string) {
		textAreaBuffer += string;
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
    	write(level.getInitialMessage() + "\n");


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
			break;
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
		if (state == GameState.READY) {
			timer = new GameTimer();
			state = GameState.RUNNING;
		}
	}

	@Override
	public void render(float delta) {
		if (nextLevelReady) {
			nextLevel();
		}
		switch (state) {
		case RUNNING:
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
			if (debug) {
				debugRenderer.render(model.world, gameCam.combined);
			}
			// Player
			TextureRegion currentFrame = getFrame(delta);
			sb.begin();
			sb.draw(currentFrame, -50, -40, 100, 100); // (uiCam.viewportWidth / 2) - 50, (uiCam.viewportHeight / 2) - 40, 100, 100);
			sb.end();

			// Display the time remaining for the player to complete the level
			elapsed += delta;

			if (elapsed >= 0.99f) {
				timerLabel.setText(timer.getFormattedMinutes() + " : " + timer.getFormattedSeconds());
				if (timer.getFormattedMinutes() < 1 && timer.getSeconds() == 10) {
					SoundManager.playSFX(Sfx.Tick, assets);
				}
				elapsed = 0f;

				if (timer.getTimeUp() == true) {
					controller.gameOver();
					parent.changeScreen(ScreenType.GAMEOVER);
				}
			}

			shapeRenderer.setAutoShapeType(true);
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setProjectionMatrix(gameCam.combined);
			shapeRenderer.setColor(Color.BROWN);

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
			if (textAreaBuffer.length() > 0) {
				textArea.appendText(textAreaBuffer.substring(0, 1));
				textAreaBuffer = textAreaBuffer.substring(1);
				if (typeSoundReady) {
					SoundManager.playSFX(Sfx.Typewriter, assets);
					typeSoundReady = false;
				} else {
					typeSoundReady = true;
				}
			}
			stage.draw();
			Gdx.graphics.requestRendering();
			break;
		case PAUSED:
			pause();
			break;
		case RESUME:
			resume();
			break;
		case RESTART:
			// Reset Level Functionality
			this.state = GameState.READY;
			resetLevel();
			parent.changeScreen(ScreenType.GAME);
			break;
		case ENDGAME:
			// Reset Level and return to Menu
			this.state = GameState.READY;
			resetLevel();
			parent.changeScreen(ScreenType.MENU);
			break;
		default:
			state = GameState.RUNNING;
		}
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
		levelLabel.setText(level.levelName);
		nextLevelReady = false;
	}

	public void tearDown() {
		model.dispose();
		level.dispose();
		mapRenderer.dispose();
		assets.disposeLevel(levelNo);
		inventoryDisplay = null;
	}

	@Override
	public void pause() {
		currentSeconds = timer.getIntSeconds();
		timer.stopTimer();
		super.pause();
		parent.changeScreen(ScreenType.PAUSE);
	}

	@Override
	public void resume() {
		super.resume();
		timer.setIntSeconds(currentSeconds + 1);
		timer.startTimer();
		this.state = GameState.RUNNING;
	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

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

	public void setState(GameState state) {
		this.state = state;
	}

	public GameState getState() {
		return state;
	}
}
