package com.fourtress.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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
	private HashMap<String, Joint> lockJoints;
	private Lock actionUnlock;
	private Dialog actionDialog;
	private Skin skin;

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
		lockJoints = new HashMap<String, Joint>();
        skin = new Skin(Gdx.files.internal("assets/visui/assets/uiskin.json"));
		actionDialog = new Dialog("",skin);

	}

	public void logicStep(float delta) {

		if (controller.left) {
			if (controller.shift) {
				if (player.getLinearVelocity().x >= -20) {
					player.applyLinearImpulse(new Vector2(-10, 0), player.getWorldCenter(), true);
				}
			} else {
				if (player.getLinearVelocity().x >= -10) {
					player.applyLinearImpulse(new Vector2(-5, 0), player.getWorldCenter(), true);
				}
			}
		}
		if (controller.right) {
			if (controller.shift) {
				// player.setLinearVelocity(10, player.getLinearVelocity().y);
				if (player.getLinearVelocity().x <= 20) {
					player.applyLinearImpulse(new Vector2(10, 0), player.getWorldCenter(), true);
				}
			} else {
				// player.setLinearVelocity(5, player.getLinearVelocity().y);
				if (player.getLinearVelocity().x <= 10) {
					player.applyLinearImpulse(new Vector2(5, 0), player.getWorldCenter(), true);
				}
			}

		}
		if (controller.up) {
			if (controller.shift) {
				if (player.getLinearVelocity().y <= 20) {
					player.applyLinearImpulse(new Vector2(0, 10), player.getWorldCenter(), true);
				}
			} else {
				if (player.getLinearVelocity().y <= 10) {
					player.applyLinearImpulse(new Vector2(0, 5), player.getWorldCenter(), true);
				}
			}
		}
		if (controller.down) {
			if (controller.shift) {
				if (player.getLinearVelocity().y >= -20) {
					player.applyLinearImpulse(new Vector2(0, -10), player.getWorldCenter(), true);
				}
			} else {
				if (player.getLinearVelocity().y >= -10) {
					player.applyLinearImpulse(new Vector2(0, -5), player.getWorldCenter(), true);
				}
			}

		}
		if (!controller.shift) {
			if (player.getLinearVelocity().y > 10) {
				player.applyForceToCenter(0, -50, true);
			}
			if (player.getLinearVelocity().y < -10) {
				player.applyForceToCenter(0, +50, true);
			}
			if (player.getLinearVelocity().x > 10) {
				player.applyForceToCenter(-50, 0, true);
			}
			if (player.getLinearVelocity().x < -10) {
				player.applyForceToCenter(+50, 0, true);
			}
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

	public void addLockJoint(String name, Joint j) {
		lockJoints.put(name, j);
	}

	public void setSpawn(Ellipse spawn) {
		player = bodyFactory.makeCirclePolyBody(spawn.x / BodyFactory.ppt, spawn.y / BodyFactory.ppt, 1,
				Material.Player, BodyType.DynamicBody, false);
		player.setUserData("Player");
	}

	public void setPlayerAction(InteractableEntity iEntity) {
		actionText = iEntity.getMessage();
		if (iEntity.isContainer()) {
			actionItem = iEntity.getItem();
		} else {
			actionItem = null;
		}
		if (iEntity instanceof Lock) {
			actionUnlock = (Lock) iEntity;
		}
	}

	public void playerAction() {
		if (actionUnlock != null) {
			if (actionUnlock.attemptUnlock(inventory)) {
				if (lockJoints.containsKey(actionUnlock.getName())) {
					world.destroyJoint(lockJoints.remove(actionUnlock.getName()));
				}
			}
		}
		if (actionText != null) {
			Stage stage = gameScreen.getStage();
			actionDialog.text(actionText);
			actionDialog.setVisible(true);

	        actionDialog.show(stage);
			System.out.println(actionText);
		}
		if (actionItem != null) {
			inventory.addItem(actionItem);
		}
	}

	public void endPlayerAction() {
		actionDialog.setVisible(false);
		actionDialog.getContentTable().clear();
		actionItem = null;
		actionText = null;
		com.fourtress.controller.MyTextInputListener listener = new com.fourtress.controller.MyTextInputListener();
		Gdx.input.getTextInput(listener, "Please Enter Combo Code", "", "Combo Code");
	}

}
