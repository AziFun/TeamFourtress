package com.fourtress.model;

public class DeliverItem extends InteractableEntity{

	private Key correctKey;
	public String name;
	private String successMessage;
	
	public DeliverItem(String message, String successMessage,Key key, String name) {
		super(message);
		correctKey = key;
		this.name = name;
		this.successMessage = successMessage;
		
		System.out.println("Message " + message);
		System.out.println("S Message " +successMessage);
		System.out.println("Name " + name);
		System.out.println("Key Name " + key.name);
		System.out.println("Key " + key);

	}

	@Override
	public Item getItem() {
		return null;
	}
	
	public boolean attemptUnlock(Inventory inventory) {
		System.out.println("Requires: " + correctKey);
		System.out.println("Inventory Contains: " + inventory.toString());
		if (inventory.contains(correctKey)) {
			message = successMessage;
			return true;
		} else {
			return false;
		}
	}

	public String getName() {
		return name;
	}
}
