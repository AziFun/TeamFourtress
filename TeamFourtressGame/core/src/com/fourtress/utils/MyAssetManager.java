package com.fourtress.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class MyAssetManager {

	private final String glassyUiPath = "assets/skins/glassy/skin/glassy-ui.json";
	private final String fontPath = "assets/skins/glassy/skin/font-big-export.fnt";
	private final String visUiPath = "assets/skins/visui/assets/uiskin.json";
	private final String actionIndicatorPath = "assets/img/KeyboardMouseIcons/Keyboard_White_Enter.png";
	private final String playerSpritePath = "assets/img/sprite/witek.png";
	private final String musicPath = "assets/audio/musicbox.mp3";
	private final String playerSpriteSetPath = "assets/img/player/MainGuySpriteSheet.png";
	private final String sfxTypewriter = "assets/audio/sfx/typewriter-key-1.mp3";
	private final String sfxTrombone = "assets/audio/sfx/sadtrombone.mp3";
	private final String sfxTick = "assets/audio/sfx/clock-ticking-2.mp3";
	private final String texKeyW = "assets/img/KeyboardMouseIcons/Keyboard_White_W.png";
	private final String texKeyA = "assets/img/KeyboardMouseIcons/Keyboard_White_A.png";
	private final String texKeyS = "assets/img/KeyboardMouseIcons/Keyboard_White_S.png";
	private final String texKeyD = "assets/img/KeyboardMouseIcons/Keyboard_White_D.png";
	private final String texArrowUp = "assets/img/KeyboardMouseIcons/Keyboard_White_Arrow_Up.png";
	private final String texArrowLeft = "assets/img/KeyboardMouseIcons/Keyboard_White_Arrow_Left.png";
	private final String texArrowDown = "assets/img/KeyboardMouseIcons/Keyboard_White_Arrow_Down.png";
	private final String texArrowRight = "assets/img/KeyboardMouseIcons/Keyboard_White_Arrow_Right.png";
	private final String texShift = "assets/img/KeyboardMouseIcons/Keyboard_White_Shift.png";
	private final String texKeyE = "assets/img/KeyboardMouseIcons/Keyboard_White_E.png";
	private final String texEnter = "assets/img/KeyboardMouseIcons/Keyboard_White_Enter.png";
	private final String texKeyP = "assets/img/KeyboardMouseIcons/Keyboard_White_P.png";
	private final String sfxDoor = "assets/audio/sfx/creaky_door_4.mp3";
	private TextButtonStyle style;
	private Label.LabelStyle lblStyle;

	private final AssetManager assMan = new AssetManager();
	private static MyAssetManager myAssetManager;

	private MyAssetManager() {
		assMan.load(glassyUiPath, Skin.class);
		assMan.load(fontPath, BitmapFont.class);
		assMan.load(visUiPath, Skin.class);
		assMan.finishLoading();

		style = new TextButtonStyle(getSkin().get("small", TextButtonStyle.class));

		getFont().setColor(getSkin().get("white", Color.class));

		lblStyle = new Label.LabelStyle(getFont(), null);
	}

	public static MyAssetManager getInstance() {
		if (myAssetManager == null) {
			myAssetManager = new MyAssetManager();
		}

		return myAssetManager;
	}

	public Skin getSkin() {
		return assMan.get(glassyUiPath);
	}

	public Skin getGameSkin() {
		return assMan.get(visUiPath);
	}

	public TextButtonStyle getTextButtonStyle() {
		return style;
	}

	public BitmapFont getFont() {
		return assMan.get(fontPath);
	}

	public Label.LabelStyle getLabelStyle() {
		return lblStyle;
	}

	
	private void disposeLevel1() {
		assMan.unload(musicPath);
	}
	
	private void disposeLevel2() {
		assMan.unload(musicPath);
	}
	
	private void disposeLevel3() {
		assMan.unload(musicPath);
	}
	
	public void loadControls() {
		assMan.load(texKeyW, Texture.class);
		assMan.load(texKeyA, Texture.class);
		assMan.load(texKeyS, Texture.class);
		assMan.load(texKeyD, Texture.class);
		assMan.load(texKeyE, Texture.class);
		assMan.load(texKeyP, Texture.class);
		assMan.load(texArrowDown, Texture.class);
		assMan.load(texArrowUp, Texture.class);
		assMan.load(texArrowLeft, Texture.class);
		assMan.load(texArrowRight, Texture.class);
		assMan.load(texEnter, Texture.class);
		assMan.load(texShift, Texture.class);
		
		assMan.finishLoading();
	}
	
	public void disposeControls() {
		assMan.unload(texKeyW);
		assMan.unload(texKeyA);
		assMan.unload(texKeyS);
		assMan.unload(texKeyD);
		assMan.unload(texKeyE);
		assMan.unload(texKeyP);
		assMan.unload(texArrowDown);
		assMan.unload(texArrowUp);
		assMan.unload(texArrowLeft);
		assMan.unload(texArrowRight);
		assMan.unload(texEnter);
		assMan.unload(texShift);
	}          

	public Object get(String filePath) {
		return assMan.get(filePath);
	}
	
	public void loadLevel(int levelNo) {
		switch (levelNo) {
		case 1:
			loadLevel1();
			break;
		case 2:
			loadLevel2();
			break;
		case 3:
			loadLevel3();
			break;
		}
	}

	public void disposeLevel(int levelNo) {
		switch (levelNo) {
		case 1:
			disposeLevel1();
			break;
		case 2:
			disposeLevel2();
			break;
		case 3:
			disposeLevel3();
			break;
		}
	}

	public void dispose() {
		assMan.dispose();
	}

	public Texture getActionIndicator() {
		return assMan.get(actionIndicatorPath);
	}

	public Texture getPlayerSprite() {
		return assMan.get(playerSpritePath);
	}
	
	
	public void loadLevel1() {
		levelSetup();
	}


	public void loadLevel2() {
		levelSetup();
	}

	public void loadLevel3() {
		levelSetup();
	}
	
	private void levelSetup() {
		assMan.load(actionIndicatorPath, Texture.class);
		assMan.load(playerSpritePath, Texture.class);
		assMan.load(musicPath, Music.class);
		assMan.load(playerSpriteSetPath, Texture.class);
		assMan.load(sfxTypewriter, Sound.class);
		assMan.load(sfxTrombone, Sound.class);
		assMan.load(sfxTick, Sound.class);
		assMan.load(sfxDoor, Sound.class);

		assMan.finishLoading();
	}

	public Music getMusic() {
		return assMan.get(musicPath);
	}

	public Texture getPlayerSpriteSet() {
		return assMan.get(playerSpriteSetPath);
	}

	public Sound getSfx(Sfx sound) {
		String fileName;
		switch (sound) {
		case Trombone:
			fileName = sfxTrombone;
			break;
		case Typewriter:
			fileName = sfxTypewriter;
			break;
		case Tick:
			fileName = sfxTick;
			break;
		case DoorCreak:
			fileName = sfxDoor;
			break;
		default:
			fileName = null;
		}
		return assMan.get(fileName);
	}
}
