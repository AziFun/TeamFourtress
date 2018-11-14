package com.fourtress.model;

public class StorageBoxLock extends StorageBox {
	
	private Item correctItem;

	public StorageBoxLock(String message, Item contents, Item correctItem) {
		super(message, contents);
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
