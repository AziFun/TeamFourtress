package com.fourtress.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player extends Sprite {
	
	public Player(Sprite sprite){
	    super(sprite);   
	}
	
	@Override
	public void draw(Batch batch) {
		update(Gdx.graphics.getDeltaTime());
		super.draw(batch);
	}
	
	
	public void update(float delta) {
		// Set player position
		setX(150);
		setY(150);

	}		
}	