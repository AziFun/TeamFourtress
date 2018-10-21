package com.fourtress.model;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.fourtress.controller.KeyboardController;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Box2dModel {

	public World world;
	private OrthographicCamera cam;
	public Body player;
	private BodyFactory bodyFactory;
	private Body nWall;
	private Body eWall;
	private Body sWall;
	private Body wWall;
	private Body door;
	private Body sensorSwitch;
	private Room room;
	public KeyboardController controller;
	private ContactListener listener;

	public Box2dModel(OrthographicCamera cam, KeyboardController controller) {
		this.cam = cam;
		this.controller = controller;
		this.bodyFactory = bodyFactory;
		this.world = new World(new Vector2(), true);
		listener = new ContactListener(this);
		world.setContactListener(listener);
		bodyFactory = BodyFactory.getInstance(world);
		createPlayer();
		createRoom();
		createSwitch();
	}

	private void createSwitch() {
		sensorSwitch = bodyFactory.makeCirclePolyBody(5, 5, 2, Material.Stone, BodyType.StaticBody);
		bodyFactory.makeBodySensor(sensorSwitch);
	}

	private void createRoom() {
		nWall = bodyFactory.makeBoxPolyBody(-2, 10, 18, 1, Material.Stone, BodyType.StaticBody);
		eWall = bodyFactory.makeBoxPolyBody(10, 0, 1, 20, Material.Stone, BodyType.StaticBody);
		sWall = bodyFactory.makeBoxPolyBody(0, -10, 20, 1, Material.Stone, BodyType.StaticBody);
		wWall = bodyFactory.makeBoxPolyBody(-10, 0, 1, 20, Material.Stone, BodyType.StaticBody);
		door = bodyFactory.makeBoxPolyBody(9, 10, 2, 1, Material.Stone, BodyType.StaticBody);
	}

	private void createPlayer() {
		player = bodyFactory.makeBoxPolyBody(1, 1, 1, 1, Material.Rubber, BodyType.DynamicBody, false);
	}

	public void logicStep(float delta) {
		
		if (controller.enter) {
			world.destroyBody(door);
		}
		if (controller.left) {
			player.applyForceToCenter(-10, 0, true);
		}
		if (controller.right) {
			player.applyForceToCenter(10, 0, true);
		}
		if (controller.up) {
			player.applyForceToCenter(0, 10, true);
		}
		if (controller.down) {
			player.applyForceToCenter(0, -10, true);
		}
		
		world.step(delta, 3, 3);
	}

	private void createObject() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(0, 0);
		Body body = world.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(1, 1);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1f;
		body.createFixture(fixtureDef);
		shape.dispose();
	}
}
