package com.fourtress.model;

import com.badlogic.gdx.physics.box2d.World;

public class BodyFactory {

	private World world;
	private static BodyFactory thisInstance;
	
	private BodyFactory(World world) {
		this.world = world;
	}
	
	public static BodyFactory getInstance(World world) {
		if (thisInstance == null) {
			thisInstance = new BodyFactory(world);
		}
		return thisInstance;
	}
}
