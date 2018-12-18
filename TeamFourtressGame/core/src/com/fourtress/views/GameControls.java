package com.fourtress.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.fourtress.ScreenType;
import com.fourtress.TeamFourtressGame;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import com.fourtress.model.AssetManager;

public class GameControls implements Screen {

	private TeamFourtressGame parent;
	private Stage stage;
	
	private AssetManager assets;
	private Skin skin;
	private TextButtonStyle style;
	private Label.LabelStyle lblStyle;
	
	private Label titleLabel;
	private Label movementLabel;
	private Label interactionLabel;
	private TextButton backButton;
	
	// Keyboard Icons
	private Image keyW;
	private Image keyA;
	private Image keyS;
	private Image keyD;
	private Image arrowUp;
	private Image arrowLeft;
	private Image arrowDown;
	private Image arrowRight;
	private Image keyE;
	private Image shift;
	private Image enter;
	
	// Keyboard Labels
	private Label keyWLabel;
	private Label keyALabel;
	private Label keySLabel;
	private Label keyDLabel;
	private Label keyELabel;
	private Label shiftLabel;
	
	//Keyboard Textures
	private Texture texKeyW;
	private Texture texKeyA;
	private Texture texKeyS;
	private Texture texKeyD;
	private Texture texArrowUp;
	private Texture texArrowLeft;
	private Texture texArrowDown;
	private Texture texArrowRight;
	private Texture texShift;
	private Texture texKeyE;
	private Texture texEnter;
	
	private Table selectionContainer;
	private ScrollPane scrollPane;
	
	public GameControls(TeamFourtressGame tfg) {
		parent = tfg;
		assets = AssetManager.getInstance();
		stage = new Stage(new ScreenViewport());
		
		// UI Setup
		skin = assets.getSkin();
		style = assets.getTextButtonStyle();
		lblStyle = assets.getLabelStyle();
		
		titleLabel = new Label("GAME CONTROLS", lblStyle);
		//titleLabel.setFontScale(0.5f);
		movementLabel = new Label("PLAYER MOVEMENT CONTROLS", skin);
		interactionLabel = new Label("OBJECT INTERACTION CONTROLS", skin);
		
		// Keyboard Icons/Label setup
		texKeyW = new Texture("images/KeyBoardMouseIcons/Keyboard_White_W.png");
		texKeyA = new Texture("images/KeyBoardMouseIcons/Keyboard_White_A.png");
		texKeyS = new Texture("images/KeyBoardMouseIcons/Keyboard_White_S.png");
		texKeyD = new Texture("images/KeyBoardMouseIcons/Keyboard_White_D.png");
		texArrowUp = new Texture("images/KeyBoardMouseIcons/Keyboard_White_Arrow_Up.png");
		texArrowLeft = new Texture("images/KeyBoardMouseIcons/Keyboard_White_Arrow_Left.png");
		texArrowDown = new Texture("images/KeyBoardMouseIcons/Keyboard_White_Arrow_Down.png");
		texArrowRight = new Texture("images/KeyBoardMouseIcons/Keyboard_White_Arrow_Right.png");
		texShift = new Texture("images/KeyBoardMouseIcons/Keyboard_White_Shift.png");
		texKeyE = new Texture("images/KeyBoardMouseIcons/Keyboard_White_E.png");
		texEnter = new Texture("images/KeyBoardMouseIcons/Keyboard_White_Enter.png");
	
		keyW = new Image(texKeyW);
		keyA = new Image(texKeyA);
		keyS = new Image(texKeyS);
		keyD = new Image(texKeyD);
		arrowUp = new Image(texArrowUp);
		arrowLeft = new Image(texArrowLeft);
		arrowDown = new Image(texArrowDown);
		arrowRight = new Image(texArrowRight);
		shift = new Image(texShift);
		keyE = new Image(texKeyE); 
		enter = new Image(texEnter); 
		
		keyWLabel = new Label("Up", skin);
		keyALabel = new Label("Left", skin);
		keySLabel = new Label("Down", skin);
		keyDLabel = new Label("Right", skin);
		keyELabel = new Label("Use", skin);
		shiftLabel = new Label("Run", skin);	
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);

		selectionContainer = new Table();
		scrollPane = new ScrollPane(selectionContainer, skin);
		backButton = new TextButton("Back", style);
		addListeners();
		
		stage.addActor(scrollPane);
				
		// Up Movement
		selectionContainer.add(titleLabel).padBottom(40).colspan(4);
		selectionContainer.row();
		selectionContainer.add(movementLabel).colspan(4).padBottom(20);
		selectionContainer.row();
		selectionContainer.add(keyWLabel);
		selectionContainer.add(keyW).pad(0,0,0,20);
		selectionContainer.add(arrowUp);
		// Left Movement
		selectionContainer.row();
		selectionContainer.add(keyALabel);
		selectionContainer.add(keyA).pad(0,0,0,20);
		selectionContainer.add(arrowLeft);
		// Down Movement
		selectionContainer.row();
		selectionContainer.add(keySLabel);
		selectionContainer.add(keyS).pad(0,0,0,20);
		selectionContainer.add(arrowDown);
		// Right Movement
		selectionContainer.row();
		selectionContainer.add(keyDLabel);
		selectionContainer.add(keyD).pad(0,0,0,20);
		selectionContainer.add(arrowRight);
		// Sprint
		selectionContainer.row();
		selectionContainer.add(shiftLabel);
		selectionContainer.add(shift).pad(0,0,0,20);
		selectionContainer.row();
		// Interaction Keys
		selectionContainer.row();
		selectionContainer.add(interactionLabel).padTop(50).padBottom(20).colspan(4);
		selectionContainer.row();
		selectionContainer.add(keyELabel);
		selectionContainer.add(keyE).pad(0,0,0,20);
		selectionContainer.add(enter);
		selectionContainer.row();   
		selectionContainer.pack();
        selectionContainer.setTransform(false);
        
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setFadeScrollBars(false);
        scrollPane.validate();
        scrollPane.setX(25f);
        scrollPane.setY(25f);
        scrollPane.setSize(Gdx.graphics.getWidth() - 50, Gdx.graphics.getHeight() - 50);
        stage.addActor(backButton);        
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
		
		float h = Gdx.graphics.getHeight();
		float w = Gdx.graphics.getWidth();
        
        scrollPane.setX(25f);
        scrollPane.setY(25f);
        scrollPane.setSize(w - 50, h - 50);        
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
		backButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(ScreenType.MENU);
			}
		});
	}
}
