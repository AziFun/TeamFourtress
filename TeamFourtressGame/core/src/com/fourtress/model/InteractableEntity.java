package com.fourtress.model;

public class InteractableEntity {

	protected String message;
	protected boolean hasSlot;
	protected Item item;
	protected boolean isEmpty;

	public InteractableEntity(String message) {
		this.message = message;
		isEmpty = true;
		hasSlot = false;
	}

	public InteractableEntity(String message, Item item) {
		this.message = message;
		this.item = item;
		hasSlot = true;
		isEmpty = false;
	}

	public Item getItem() {
		if (hasSlot && !isEmpty) {
			Item removedItem = item;
			item = null;
			isEmpty = true;
			return removedItem;
		} else {
			return null;
		}
	}

	public String getMessage() {
		if (hasSlot && isEmpty) {
			return "There is nothing here";
		} else {
			return message;
		}
	}
	
	public boolean isContainer() {
		return hasSlot;
	}
}
