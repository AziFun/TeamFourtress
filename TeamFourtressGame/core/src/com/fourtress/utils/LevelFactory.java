package com.fourtress.utils;

import java.util.HashMap;
import java.util.LinkedList;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.fourtress.model.Book;
import com.fourtress.model.Box2dModel;
import com.fourtress.model.Item;
import com.fourtress.model.ItemPile;
import com.fourtress.model.Key;
import com.fourtress.model.Level;

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
		case 2:
			levelItems = getLevel2Items();
			return new Level(new TmxMapLoader().load("Maps/Computing.tmx"), model, levelItems);
		case 3:
			levelItems = getLevel3Items();
			return new Level(new TmxMapLoader().load("Maps/EnglishCanteenMap.tmx"), model, levelItems);
		}
		return null;
	}

	private HashMap<String, Item> getLevel1Items() {
		HashMap<String, Item> items = new HashMap<String, Item>();
		LinkedList<Book> books = new LinkedList<Book>();
		items.put("A", new Book("A brief history of time", "Book", null));
		items.put("B", new Book("Diary of a young girl", "Book", null));
		items.put("C", new Book("Horrible histories", "Book", null));
		items.put("D", new Book("Just one more war sir?", "Book", null));
		items.put("E", new Book("Memories from the front", "Book", null));
		items.put("F", new Book("Sapiens: a brief history", "Book", null));
		items.put("G", new Book("Sun Tzu on the art of war", "Book", null));
		books.add((Book) items.get("D"));
		books.add((Book) items.get("F"));
		books.add((Book) items.get("B"));
		books.add((Book) items.get("G"));
		books.add((Book) items.get("A"));
		books.add((Book) items.get("C"));
		books.add((Book) items.get("E"));
		ItemPile<Book> pileOfBooks = new ItemPile<Book>("Pile of books", "Pile", null);
		pileOfBooks.setContents(books);
		items.put("Books", pileOfBooks);
		items.put("LibraryKey", new Key("LibraryKey", "Key", null, "red"));
		items.put("OfficeKey", new Key("OfficeKey", "Key", null, "blue"));
		items.put("EndKey", new Key("EndKey", "Key", null, "green"));
		return items;
	}
	
	private HashMap<String, Item> getLevel2Items() {
		HashMap<String, Item> items = new HashMap<String, Item>();
		items.put("ServerClosetKey", new Key("ServerClosetKey", "Key", null, "red"));
		items.put("EndKey", new Key("EndKey", "Key", null, "green"));
		return items;
	}
	
	private HashMap<String, Item> getLevel3Items() {
		HashMap<String, Item> items = new HashMap<String, Item>();
		items.put("LibraryKey", new Key("LibraryKey", "Key", null, "red"));
		items.put("VegetableKey", new Key("VegetableKey", "Key", null, "blue"));
		items.put("SecretKey", new Key("SecretKey", "Key", null, "green"));
		items.put("EndKey", new Key("EndKey", "Key", null, "green"));

		return items;
	}
}

