package com.fourtress.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class KeyboardController implements InputProcessor {

	private static final boolean KeyProcessed = false;
	public boolean left;
	public boolean right;
	public boolean up;
	public boolean down;
	public boolean shift;
	public boolean enter;
	public boolean num1;
	public boolean num2;
	public boolean num3;
	public boolean num4;
	public boolean num5;
	public boolean num6;
	public boolean num7;
	public boolean num8;
	public boolean num9;
	public boolean num0;
	public boolean backspace;

	public void KeyPressedCheck() {
		right = false;
		left = false;
		up = false;
		down = false;
	}

	@Override
	public boolean keyDown(int keycode) {
		boolean keyProcessed = false;
		switch (keycode) {
		case Keys.LEFT:
		case Keys.A:
			left = true;
			keyProcessed = true;
			break;
		case Keys.RIGHT:
		case Keys.D:
			right = true;
			keyProcessed = true;
			break;
		case Keys.UP:
		case Keys.W:
			up = true;
			keyProcessed = true;
			break;
		case Keys.DOWN:
		case Keys.S:
			down = true;
			keyProcessed = true;
			break;
		case Keys.ENTER:
		case Keys.E:
			enter = true;
			keyProcessed = true;
			break;
		case Keys.SHIFT_LEFT:
			shift = true;
			keyProcessed = true;
			break;
		case Keys.NUM_1:
		case Keys.NUMPAD_1:
			num1 = true;
			keyProcessed = true;
			break;
		case Keys.NUM_2:
		case Keys.NUMPAD_2:
			num2 = true;
			keyProcessed = true;
			break;
		case Keys.NUM_3:
		case Keys.NUMPAD_3:
			num3 = true;
			keyProcessed = true;
			break;
		case Keys.NUM_4:
		case Keys.NUMPAD_4:
			num4 = true;
			keyProcessed = true;
			break;
		case Keys.NUM_5:
		case Keys.NUMPAD_5:
			num5 = true;
			keyProcessed = true;
			break;
		case Keys.NUM_6:
		case Keys.NUMPAD_6:
			num6 = true;
			keyProcessed = true;
			break;
		case Keys.NUM_7:
		case Keys.NUMPAD_7:
			num7 = true;
			keyProcessed = true;
			break;
		case Keys.NUM_8:
		case Keys.NUMPAD_8:
			num8 = true;
			keyProcessed = true;
			break;
		case Keys.NUM_9:
		case Keys.NUMPAD_9:
			num9 = true;
			keyProcessed = true;
			break;
		case Keys.NUM_0:
		case Keys.NUMPAD_0:
			num0 = true;
			keyProcessed = true;
			break;
		case Keys.BACKSPACE:
			backspace = true;
			keyProcessed = true;
		}
		return keyProcessed;
	}

	@Override
	public boolean keyUp(int keycode) {
		boolean keyProcessed = false;
		switch (keycode) {
		case Keys.LEFT:
		case Keys.A:
			left = false;
			keyProcessed = true;
			break;
		case Keys.RIGHT:
		case Keys.D:
			right = false;
			keyProcessed = true;
			break;
		case Keys.UP:
		case Keys.W:
			up = false;
			keyProcessed = true;
			break;
		case Keys.DOWN:
		case Keys.S:
			down = false;
			keyProcessed = true;
			break;
		case Keys.ENTER:
		case Keys.E:
			enter = false;
			keyProcessed = true;
			break;
		case Keys.SHIFT_LEFT:
			shift = false;
			keyProcessed = true;
			break;
		case Keys.NUM_1:
		case Keys.NUMPAD_1:
			num1 = false;
			keyProcessed = true;
			break;
		case Keys.NUM_2:
		case Keys.NUMPAD_2:
			num2 = false;
			keyProcessed = true;
			break;
		case Keys.NUM_3:
		case Keys.NUMPAD_3:
			num3 = false;
			keyProcessed = true;
			break;
		case Keys.NUM_4:
		case Keys.NUMPAD_4:
			num4 = false;
			keyProcessed = true;
			break;
		case Keys.NUM_5:
		case Keys.NUMPAD_5:
			num5 = false;
			keyProcessed = true;
			break;
		case Keys.NUM_6:
		case Keys.NUMPAD_6:
			num6 = false;
			keyProcessed = true;
			break;
		case Keys.NUM_7:
		case Keys.NUMPAD_7:
			num7 = false;
			keyProcessed = true;
			break;
		case Keys.NUM_8:
		case Keys.NUMPAD_8:
			num8 = false;
			keyProcessed = true;
			break;
		case Keys.NUM_9:
		case Keys.NUMPAD_9:
			num9 = false;
			keyProcessed = true;
			break;
		case Keys.NUM_0:
		case Keys.NUMPAD_0:
			num0 = false;
			keyProcessed = true;
		}

		return keyProcessed;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
