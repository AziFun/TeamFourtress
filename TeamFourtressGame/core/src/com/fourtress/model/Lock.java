package com.fourtress.model;

public class Lock extends InteractableEntity{

	private Key correctKey;
	private String name;
	
	public Lock(String message, Key key, String name) {
		super(message);
		correctKey = key;
		this.name = name;
	}

	@Override
	public Item getItem() {
		return null;
	}
	
	public boolean attemptUnlock(Inventory inventory) {
		if (inventory.contains(correctKey)) {
			message = "You unlocked the door";
			return true;
		} else {
			return false;
		}
	}

	public String getName() {
		return name;
	}
}
