package com.fourtress.model.interfaces;

public interface Lock {
	
	private boolean locked;
	
	
	public default boolean isLocked() {
		return locked;
	}
	
	default void lock() {
		this.locked = false;
	}
	
	default void unlock() {
		this.locked = true;
	}
	

}
