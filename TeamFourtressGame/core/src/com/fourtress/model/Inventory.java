package com.fourtress.model;

import java.util.HashMap;

public class Inventory {

	    HashMap<Integer, Item> items;
	    
	    public Inventory(){
	        reset();
	    }
	    
	    public int getInventorySize(){
	        return items.size();
	    }

	    public void addItem(Item item) {
	    	// Retrieve the current inventory size
	    	int invSize = getInventorySize();
	    		
	    	// Check in the inventory is full (Maximum slots is 10)
	    	if(invSize >= 10) {
	    		
	    	} else {
	    		// Inventory is not full, so add the item to the inventory and use invSize as the ID
	    		items.put(invSize,item);
	    	}
	    }
	    
	    // Return all of the Items currently stored in the inventory
	    public HashMap<Integer, Item> getInventory(){
	        return items;
	    }
	    
	    // Reset the HashMap, removing all Items
	    private void reset() {
	        items = new HashMap<Integer, Item>();
	    }
	    
	    
	    
	    
	    // print method for testing purposes
	    public void print() {
	        System.out.println("*** Inventory ***");
	        for(int i = 0 ; i < items.size(); i++){
	            Item e = items.get(i);
	            System.out.println("* ["+i+"] | " + e.toString());
	        }    
	        System.out.println("*****************");
	    }	    
	    

	    // Main Method for Testing Hashmap Structure
		public static void main (String[] arg) {
			Inventory inv = new Inventory();
			Key key = new Key("Room 0 Key","Key","key.png","Yellow");
			Key key1 = new Key("Room 1 Key","Key1","key1.png","Yellow");
			Key key2 = new Key("Room 2 Key","Key2","key2.png","Brown");
			Key key3 = new Key("Room 3 Key","Key3","key3.png","Yellow");
			Key key4 = new Key("Room 4 Key","Key4","key4.png","Pink");
			Key key5 = new Key("Room 5 Key","Key5","key5.png","Yellow");
			Key key6 = new Key("Room 6 Key","Key6","key6.png","Yellow");
			Key key7 = new Key("Room 7 Key","Key7","key7.png","Blue");
			Key key8 = new Key("Room 8 Key","Key8","key8.png","Red");
			Key key9 = new Key("Room 9 Key","Key9","key9.png","Green");
			//Key key10 = new Key("Room 10 Key","Key","key.png","Green");
			inv.addItem(key);
			inv.addItem(key1);
			inv.addItem(key2);
			inv.addItem(key3);
			inv.addItem(key4);
			inv.addItem(key5);
			inv.addItem(key6);
			inv.addItem(key7);
			inv.addItem(key8);
			inv.addItem(key9);
			//inv.addItem(key10);
			inv.print();
			inv.reset();
			inv.print();
		}
}