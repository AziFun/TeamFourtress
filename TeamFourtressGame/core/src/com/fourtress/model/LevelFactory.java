package com.fourtress.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class LevelFactory {

	private static LevelFactory thisInstance;
	
	private LevelFactory() {
		
	}
	
	public static LevelFactory getInstance() {
		if (thisInstance == null) {
			thisInstance = new LevelFactory();
		}
		return thisInstance;
	}
	
	public TiledMap makeLevel(int levelNum) {
		switch (levelNum) {
		case 1:
			return createLevel1();
		}
		return null;
	}

	private TiledMap createLevel1() {
		return new TmxMapLoader().load("Maps/HistoryMap.tmx");
	}
}
