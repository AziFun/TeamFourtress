package com.fourtress.model;

import java.util.HashMap;
import java.util.LinkedList;

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
		HashMap<String, Item> levelItems;
		switch (levelNum) {
		case 1:
			levelItems = getLevel1Items();
			return new Level(new TmxMapLoader().load("Maps/HistoryMap.tmx"), model, levelItems);
		}
		return null;
	}
	
	private HashMap<String, Item> getLevel1Items() {
		HashMap<String, Item> items = new HashMap<String, Item>();
		LinkedList<Book> books = new LinkedList<Book>();
		books.add(new Book("A", "Book", null));
		books.add(new Book("B", "Book", null));
		books.add(new Book("C", "Book", null));
		books.add(new Book("D", "Book", null));
		books.add(new Book("E", "Book", null));
		books.add(new Book("F", "Book", null));
		books.add(new Book("G", "Book", null));
		ItemPile<Book> pileOfBooks = new ItemPile<Book>("Pile of books", "Pile", null);
		pileOfBooks.setContents(books);
		items.put("Books",  pileOfBooks);
		items.put("LibraryKey", new Key("LibraryKey", "Key", null, "red"));
		items.put("OfficeKey", new Key("OfficeKey", "Key", null, "blue"));
		items.put("EndKey", new Key("EndKey", "Key", null, "green"));
		return items;
	}
}
