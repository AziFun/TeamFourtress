package com.fourtress.model;

public class StorageBox extends InteractableEntity {

	public StorageBox(String message) {
		super(message);
		hasSlot = true;
	}
	
	public StorageBox(String message, Item contents) {
		super(message, contents);
	}

	public boolean insertItem(Item item) {
		if (!isEmpty) {
			return false;
		} else {
			this.item = item;
			return true;
		}
	}
	
	public String getMessage() {
		if (isEmpty) {
			return message;
		} else {
			return "There is already something here";
		}
	}
}
