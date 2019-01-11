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

	private final String glassyUiPath = "skins/glassy/skin/glassy-ui.json";
	private final String fontPath = "skins/glassy/skin/font-big-export.fnt";
	private final String visUiPath = "skins/visui/assets/uiskin.json";
	private final String actionIndicatorPath = "img/hand-icon.png";
	private final String playerSpritePath = "img/sprite/witek.png";
	private final String musicPath = "audio/music/musicbox.mp3";
	private final String playerSpriteSetPath = "img/player/MainGuySpriteSheet.png";
	private final String sfxTypewriter = "audio/sfx/typewriter-key-1.mp3";
	private final String sfxTrombone = "audio/sfx/sadtrombone.mp3";
	private final String sfxTick = "audio/sfx/clock-ticking-2.mp3";

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

	public void loadLevel1() {
		assMan.load(actionIndicatorPath, Texture.class);
		assMan.load(playerSpritePath, Texture.class);
		assMan.load(musicPath, Music.class);
		assMan.load(playerSpriteSetPath, Texture.class);
		assMan.load(sfxTypewriter, Sound.class);
		assMan.load(sfxTrombone, Sound.class);
		assMan.load(sfxTick, Sound.class);

		assMan.finishLoading();
	}

	private void disposeLevel1() {
		assMan.unload(musicPath);
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
		}
	}

	public void disposeLevel(int levelNo) {
		switch (levelNo) {
		case 1:
			disposeLevel1();
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

	public void loadLevel2() {

	}

	public void loadLevel3() {

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
		default:
			fileName = null;
		}
		return assMan.get(fileName);
	}
}
