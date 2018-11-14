package com.fourtress.model;

public class StorageBoxLock extends StorageBox {
	
	private Item correctItem;

	public StorageBoxLock(String message, Item correctItem) {
		super(message);
		this.correctItem = correctItem;
	}
	
	public boolean checkLock() {
		if (item == correctItem) {
			return true;
		} else {
			return false;
		}
	}
}
