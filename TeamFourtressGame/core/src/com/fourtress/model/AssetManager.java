package com.fourtress.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class AssetManager {
	
	private BitmapFont font;
	private Skin skin;
	private TextButtonStyle style;
	private Label.LabelStyle lblStyle;
	
	private static AssetManager asset_instance;
	
	private AssetManager() {
		
		skin = new Skin(Gdx.files.internal("skins/glassy/skin/glassy-ui.json"));
		
		style = new TextButtonStyle(skin.get("small",TextButtonStyle.class));	
		
		font = new BitmapFont(Gdx.files.internal("skins/glassy/skin/font-big-export.fnt"),
		         Gdx.files.internal("skins/glassy/raw/font-big-export.png"), false);
		
		font.setColor(skin.get("white",Color.class));
		
		lblStyle = new Label.LabelStyle(font, null);
	
	}
	
	public static AssetManager getInstance() {
		if(asset_instance == null) {
			asset_instance = new AssetManager();
		}
		
		return asset_instance;
	}
	
	public Skin getSkin() {
		return skin;
	}
	
	public TextButtonStyle getTextButtonStyle() {
		return style;
	}
	
	public BitmapFont getFont() {
		return font;
	}
	
	public Label.LabelStyle getLabelStyle(){
		return lblStyle;
	}
}
