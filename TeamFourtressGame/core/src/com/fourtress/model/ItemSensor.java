package com.fourtress.model;

public class ItemSensor extends Sensor {

	private Item item;
	private boolean isEmpty = false;

	public ItemSensor(String message, Item item) {
		super(message);
		this.item = item;
	}

	public Item getItem() {
		if (!isEmpty) {
			Item removedItem = item;
			item = null;
			isEmpty = true;
			return removedItem;
		} else {
			return null;
		}
	}
	
	@Override
	public String getMessage() {
		if (!isEmpty) {
			return super.getMessage();
		} else {
			return "There is nothing here";
		}
	}
}
