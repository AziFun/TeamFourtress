package com.fourtress.model;

public class StorageBoxLock extends StorageBox {
	
	public Item correctItem;
	private String lockName;

	public StorageBoxLock(String message, Item correctItem, String lockName) {
		super(message);
		this.hasSlot = true;
		this.correctItem = correctItem;
		this.lockName = lockName;
	}
	
	public boolean checkLock() {
		if (item == correctItem) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getLockName() {
		return lockName;
	}
}
