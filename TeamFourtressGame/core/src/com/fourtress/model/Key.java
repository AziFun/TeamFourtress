package com.fourtress.model;

public class Key extends Item {
	
	private String keyColour;
	
	public Key(String name, String type, String imageName, String keyColour) {
		super(name, type, imageName);
		setKeyColor(keyColour);
	}
	
	
	public String getKeyColor() {
		return keyColour;
	}

	private void setKeyColor(String keyColour) {
		this.keyColour=keyColour;
	}
	
	@Override
	public String toString() {
		return super.toString() + " | Key Colour is: " + keyColour;
	}
	
}
