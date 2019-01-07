package com.fourtress.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.fourtress.model.BodyFactory;
import com.fourtress.model.Box2dModel;
import com.fourtress.model.DoorData;
import com.fourtress.model.GameTimer;
import com.fourtress.model.Level;
import com.fourtress.model.LevelFactory;
import com.fourtress.model.SoundManager;


public class GameScreen extends ScreenAdapter {

	private Box2dModel model;
	private OrthographicCamera gameCam;
	private OrthographicCamera uiCam;
	private Box2DDebugRenderer debugRenderer;
	public TeamFourtressGame parent;
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
	

	public GameScreen(TeamFourtressGame parent) {
		this.parent = parent;
		float w = 60;
		float h = 34;
		
		// Camera setup
		gameCam = new OrthographicCamera(w, h);
		uiCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), uiCam));
		gameCam.zoom = 0.7f;
		gameCam.update();
    
		// Controller setup
		controller = new KeyboardController();
		model = new Box2dModel(gameCam, controller, this);
		debugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);

		// Sprite setup
		sb = new SpriteBatch();
		sb.setProjectionMatrix(uiCam.combined);
		skin = new Skin(Gdx.files.internal("assets/visui/assets/uiskin.json"));

		// Music setup
		SoundManager.playMusic("audio/music/musicbox.mp3");

		// Map setup
		LevelFactory levelGen = LevelFactory.getInstance();
		level = levelGen.makeLevel(1, model);
		mapRenderer = new OrthogonalTiledMapRenderer(level.getTiledMap(), 1/BodyFactory.ppt);
		shapeRenderer = new ShapeRenderer();	
        	
		//Text Area Setup
		Table table = new Table();
		table.setFillParent(true);
		table.left().top();
		//table.debugAll();
		//table.setFillParent(true);
		//FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Gdx.files.internal("assets/slkscreb.ttf"));
		//FreeTypeFontParameter param =new FreeTypeFontParameter();
		//param.size = 56;
		//BitmapFont font = ftfg.generateFont(param);
		//font.getData().setScale(1/16f, 1/16f);
		textArea = new TextArea("Welcome to TeamFourtress!\n", skin);
        textArea.setColor(Color.BLACK);
        skin.getFont("default-font").getData().scale(0.2f);
        table.add(textArea).grow().pad(10);
        timerLabel = new Label("", skin);
        timerLabel.setFontScale(2);;
        table.add(timerLabel).top().expandX();
        table.add().grow();
        table.row();
        table.add().grow();
        table.row();
        table.add().grow();
        table.row();
        inventoryDisplay = new Label("", skin);
        table.add(inventoryDisplay).grow();
        table.add().grow();
        actionIndicator = new Image(new Texture(Gdx.files.internal("hand-icon.png")));
        actionIndicator.setScaling(Scaling.fit);
        table.add(actionIndicator).bottom().right().size(100);
        table.debug();
        //textArea.setPosition(0, 0);
        //table.row();
        //table.add(textArea).expand();
        stage.addActor(table);
       
        //textArea.appendText(level.getInitialMessage() + "\n");
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(controller);

		//Gdx.input.setInputProcessor(stage);
		
		// Music setup
		SoundManager.playMusic("audio/music/musicbox.mp3");	
		
		// Timer setup
		timer = new GameTimer();
	}

	@Override
	public void render(float delta) {		
		model.logicStep(delta);
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gameCam.position.set(model.player.getPosition(), gameCam.position.z);
		gameCam.update();
		mapRenderer.setView(gameCam);
		mapRenderer.render();
		debugRenderer.render(model.world, gameCam.combined);
		sb.begin();
		Texture playerSprite = new Texture(Gdx.files.internal("witek.png"));
		Texture keySprite = new Texture(Gdx.files.internal("assets/key.png"));
		sb.draw(playerSprite, (uiCam.viewportWidth/2)-50,(uiCam.viewportHeight/2)-50, 100, 100);
		sb.end();
		
		// Display the time remaining for the player to complete the level
		elapsed += delta;
	
		if (elapsed >= 0.99f) {
		   timerLabel.setText(timer.getFormattedMinutes() + " : " + timer.getFormattedSeconds());
		   elapsed = 0f;
		   
		   if(timer.getTimeUp() == true) {
			   parent.changeScreen(ScreenType.GAMEOVER);
		   }
		}
		
		shapeRenderer.setAutoShapeType(true);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setProjectionMatrix(gameCam.combined);
		shapeRenderer.setColor(Color.CYAN);
		
		for (Body door : model.physicsObjects) {
			DoorData doorData = (DoorData) door.getUserData();
			shapeRenderer.rect(doorData.doorBody.getRectangle().x / BodyFactory.ppt, doorData.doorBody.getRectangle().y / BodyFactory.ppt,
					doorData.hingeCentre.x / BodyFactory.ppt, doorData.hingeCentre.y / BodyFactory.ppt,
					doorData.doorBody.getRectangle().width / BodyFactory.ppt, doorData.doorBody.getRectangle().height / BodyFactory.ppt, 1, 1,
					door.getAngle() * MathUtils.radiansToDegrees);
		}
		
		shapeRenderer.end();
		if (model.isActionAvailable()) {
			actionIndicator.setVisible(true);
		} else {
			actionIndicator.setVisible(false);
		}
		stage.draw();
	    playerSprite.dispose();
	    keySprite.dispose();	
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		uiCam.update();
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
		SoundManager.dispose();

	}

	public Stage getStage() {
		return stage;
	}


	public Skin getSkin() {
		return skin;
	}

}

