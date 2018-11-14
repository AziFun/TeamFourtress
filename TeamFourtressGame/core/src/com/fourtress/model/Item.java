package com.fourtress.model;

/**
 * An Item is held by an inventory slot and can be one of:
 * - key
 */
public abstract class Item {
	
	public String name;
	public String type;
	public String imageName;
	
	public Item(String name, String type, String imageName) {
		this.name = name;
		this.type = type;
		this.imageName = imageName;
	}
	
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public String getImageName() {
		return imageName;
	}
	
	public String toString() {
		return("Name of Key: " + name + " | Type of Item: " + type + " | Image Name is: " + imageName);
	}

}
