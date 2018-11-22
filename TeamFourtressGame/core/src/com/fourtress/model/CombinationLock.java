package com.fourtress.model;

public class CombinationLock extends InteractableEntity {

	private String combination;
	private String name;
	
	public CombinationLock(String message, String combination, String name) {
		super(message);
		this.name = name;
		this.combination = combination;
	}
	
	@Override
	public Item getItem() {
		return null;
	}
	
	public boolean attemptUnlock(String combination) {
		if (this.combination.equals(combination)) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getName() {
		return name;
	}
}
