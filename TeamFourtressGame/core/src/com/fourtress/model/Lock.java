package com.fourtress.model;

public class Lock extends InteractableEntity{

	private Key correctKey;
	public String name;
	public String successMessage;
	
	public Lock(String message,String successMessage, Key key, String name) {
		super(message);
		correctKey = key;
		this.name = name;
		this.successMessage = successMessage;
	}

	@Override
	public Item getItem() {
		return null;
	}
	
	public boolean attemptUnlock(Inventory inventory) {
		if (inventory.contains(correctKey)) {
			if (successMessage != null) {
				message = successMessage;
						return true;}
			else {
			message = "You unlocked the door";}
			return true;
		} else {
			return false;
		}
	}

	public String getName() {
		return name;
	}
}
