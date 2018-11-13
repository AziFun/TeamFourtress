package com.fourtress.model;

import java.util.LinkedList;

public class ItemPile<T extends Item> extends Item {
	
	private LinkedList<T> contents;

	public ItemPile(String name, String type, String imageName) {
		super(name, type, imageName);
		// TODO Auto-generated constructor stub
	}
	
	public void setContents(LinkedList<T> contents) {
		this.contents = contents;
	}
	
	public LinkedList<T> getContents() {
		return contents;
	}
}
