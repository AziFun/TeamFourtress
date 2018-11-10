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
	
	public Level makeLevel(int levelNum, Box2dModel model) {
		switch (levelNum) {
		case 1:
			return new Level(new TmxMapLoader().load("Maps/HistoryMap.tmx"), model);
		}
		return null;
	}
}
