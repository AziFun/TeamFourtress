package com.fourtress.controller;

import com.badlogic.gdx.Input.TextInputListener;
import com.fourtress.model.Box2dModel;

public class MyTextInputListener implements TextInputListener {

	private Box2dModel model;
	
	public MyTextInputListener(Box2dModel model) {
		this.model = model;
	}

	@Override
	public void input(String text) {
		model.inputText = text;
	}

	@Override
	public void canceled() {
	}
}