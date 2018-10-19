package com.fourtress.model;

public abstract class Lock {
	
	private boolean locked;
	
	
	public boolean isLocked() {
		return locked;
	}
	
	protected void lock() {
		this.locked = false;
	}
	
	protected void unlock() {
		this.locked = true;
	}
	

}
