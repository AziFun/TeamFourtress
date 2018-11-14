package com.fourtress.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.fourtress.controller.KeyboardController;
import com.fourtress.views.GameScreen;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;

public class Box2dModel {

	public World world;
	public GameScreen gameScreen;
	private OrthographicCamera cam;
	public Body player;
	public Inventory inventory;
	private BodyFactory bodyFactory;
	private Room room;
	public KeyboardController controller;
	private ContactListener listener;
	private boolean doorToOpen = false;
	private boolean grabKey = false;
	private String actionText;
	private Item actionItem;

	public Box2dModel(OrthographicCamera cam, KeyboardController controller, GameScreen gameScreen) {
		this.cam = cam;
		this.gameScreen = gameScreen;
		this.controller = controller;
		this.bodyFactory = bodyFactory;
		this.inventory = new Inventory();
		this.world = new World(new Vector2(), true);
		listener = new ContactListener(this);
		world.setContactListener(listener);
		bodyFactory = BodyFactory.getInstance(world);
	}

	public void logicStep(float delta) {

		if (controller.left) {
			player.setLinearVelocity(-5, player.getLinearVelocity().y);
		}
		if (controller.right) {
			player.setLinearVelocity(5, player.getLinearVelocity().y);
		}
		if (controller.up) {
			player.setLinearVelocity(player.getLinearVelocity().x, 5);
		}
		if (controller.down) {
			player.setLinearVelocity(player.getLinearVelocity().x, -5);
		}
		if (!controller.left && !controller.right && !controller.up && !controller.down) {
			player.setLinearVelocity(0, 0);
		}
		if (controller.playerAction) {
			controller.playerAction = false;
			playerAction();
		}

		world.step(delta, 3, 3);
	}

	public void setSpawn(Ellipse spawn) {
		player = bodyFactory.makeCirclePolyBody(spawn.x / 32, spawn.y / 32, 1, Material.Rubber, BodyType.DynamicBody,
				false);
	}

	public void setPlayerAction(InteractableEntity iEntity) {
		actionText = iEntity.getMessage();
		if (iEntity.isContainer()) {
			actionItem = iEntity.getItem();
		} else {
			actionItem = null;
		}
	}

	public void playerAction() {
		if (actionText != null) {
			System.out.println(actionText);
		}
		if (actionItem != null) {
			inventory.addItem(actionItem);
		}
	}

	public void endPlayerAction() {
		actionItem = null;
		actionText = null;
	}

}
