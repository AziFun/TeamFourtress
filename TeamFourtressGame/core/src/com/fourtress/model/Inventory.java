package com.fourtress.model;

import java.util.HashMap;
import java.util.Map;

import com.fourtress.exceptions.InventoryFullException;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Inventory implements ObservableValue<Map<Integer, Item>> {

	private HashMap<Integer, Item> items;
	private ChangeListener<? super Map<Integer, Item>> listener;

	public Inventory() {
		reset();
	}

	public int getInventorySize() {
		return items.size();
	}

	public Item remove(Integer slotNumber) {
		Item removed;
		if (listener != null) {
			@SuppressWarnings("unchecked")
			HashMap<Integer, Item> oldValue = (HashMap<Integer, Item>) items.clone();
			removed = items.remove(slotNumber);
			listener.changed(this, oldValue, items);
		} else {
			removed = items.remove(slotNumber);
		}
		return removed;
	}

	public void addItem(Item item) throws InventoryFullException {
		if (listener != null) {
			@SuppressWarnings("unchecked")
			HashMap<Integer, Item> oldValue = (HashMap<Integer, Item>) items.clone();
			addItemLogic(item);
			listener.changed(this, oldValue, items);
		} else {
			addItemLogic(item);
		}
	}

	@SuppressWarnings("unchecked")
	private void addItemLogic(Item item) throws InventoryFullException {
		if (item != null) {
			if (item instanceof ItemPile) {
				for (Item i : ((ItemPile<? extends Item>) item).getContents()) {
					addItemLogic(i);
				}
			} else {
				int slotNum = findSpace();
				if (slotNum == -1) {
					throw new InventoryFullException("InventoryFull");
				} else {
					items.put(slotNum, item);
				}
			}
		}
	}
	
	public int findSpace() {
		for (int i = 1; i < 11; i++) {
			if (!items.containsKey(i)) return i;
		}
		return -1;
	}

	public String toString() {
		String string = "";
		for (Integer i : items.keySet()) {
			string += i + ":";
			string += items.get(i);
			string += ".\n";
		}
		return string;
	}

	public boolean contains(Item item) {
		return items.values().contains(item);
	}

	// Return all of the Items currently stored in the inventory
	public HashMap<Integer, Item> getInventory() {
		return items;
	}

	// Reset the HashMap, removing all Items
	private void reset() {
		items = new HashMap<Integer, Item>();
	}
	

	public void clearItems() {
		if (listener != null) {
			@SuppressWarnings("unchecked")
			HashMap<Integer, Item> oldValue = (HashMap<Integer, Item>) items.clone();
			items.clear();
			listener.changed(this, oldValue, items);
		} else {
			items.clear();
		}
	}

	// print method for testing purposes
	public void print() {
		System.out.println("*** Inventory ***");
		for (int i = 0; i < items.size(); i++) {
			Item e = items.get(i);
			System.out.println("* [" + i + "] | " + e.toString());
		}
		System.out.println("*****************");
	}

	@Override
	public void addListener(InvalidationListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeListener(InvalidationListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addListener(ChangeListener<? super Map<Integer, Item>> listener) {
		this.listener = listener;
	}

	@Override
	public Map<Integer, Item> getValue() {
		return items;
	}

	@Override
	public void removeListener(ChangeListener<? super Map<Integer, Item>> listener) {
		this.listener = null;
	}
}
