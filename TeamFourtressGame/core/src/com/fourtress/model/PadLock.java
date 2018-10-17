package com.fourtress.model;

import java.util.HashSet;

import com.fourtress.model.interfaces.Lock;

public class PadLock implements Lock {

	private HashSet<Key> validKeys;

	public PadLock(Key key) {
		validKeys.add(key);
		lock();
	}

	public void tryLock(Key key) {
		if (validKeys.contains(key)) {
			unlock();
			System.out.println("Key Supplied was correct! The lock is unlocked");
		} else {
			System.out.println("Key Supplied was incorrect! Please try another key");

		}

	}

	public void addKey(Key key) {
		validKeys.add(key);
	}

}
