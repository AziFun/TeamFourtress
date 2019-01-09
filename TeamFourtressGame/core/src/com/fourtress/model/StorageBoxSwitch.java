package com.fourtress.model;

public class StorageBoxSwitch extends StorageBox {
	
	public Item correctItem;
	private String lockName;

	public StorageBoxSwitch(String message, Item correctItem, String lockName) {
		super(message);
		this.hasSlot = true;
		this.correctItem = correctItem;
		this.lockName = lockName;
	}
	
	public boolean checkSwitch() {
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
