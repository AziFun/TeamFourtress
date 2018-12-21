package com.fourtress.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.fourtress.controller.KeyboardController;
import com.fourtress.controller.MyTextInputListener;
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
	private String actionText;
	private Item actionItem;
	private HashMap<String, Joint> lockJoints;
	private InteractableEntity actionUnlock;
	private Dialog actionDialog;
	private Skin skin;
	public String inputText;
	public List<StorageBoxLock> multiLocks;
	public Joint jointToDestroy;
	private Body finishLine;
	public List<Body> physicsObjects;

	public Box2dModel(OrthographicCamera cam, KeyboardController controller, GameScreen gameScreen) {
		this.cam = cam;
		this.gameScreen = gameScreen;
		this.controller = controller;
		this.bodyFactory = bodyFactory;
		physicsObjects = new LinkedList<Body>();
		multiLocks = new LinkedList<StorageBoxLock>();
		this.inventory = new Inventory();
		this.world = new World(new Vector2(), true);
		listener = new ContactListener(this);
		world.setContactListener(listener);
		bodyFactory = BodyFactory.getInstance(world);
		lockJoints = new HashMap<String, Joint>();
		skin = new Skin(Gdx.files.internal("assets/visui/assets/uiskin.json"));
		actionDialog = new Dialog("", skin);

	}

	public void logicStep(float delta) {
		if (jointToDestroy != null) {
			world.destroyJoint(jointToDestroy);
			jointToDestroy = null;
		}
		
		if(controller.p == true) {			
			if(gameScreen.getState() == GameState.RUNNING) {
				// Game is currently running so pause the game
				controller.setPause(false);
				gameScreen.setState(GameState.PAUSED);
			}
		}
		
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
	
	public void setFinish(Ellipse finish) {
		finishLine = bodyFactory.makeCirclePolyBody((finish.x + finish.width/2)/ BodyFactory.ppt, (finish.y + finish.height/2)/ BodyFactory.ppt, finish.width / BodyFactory.ppt,
				Material.Rubber, BodyType.StaticBody, false);
		bodyFactory.makeBodySensor(finishLine,"finish");
		finishLine.setUserData("finish");
	}

	public void setPlayerAction(InteractableEntity iEntity) {
		actionText = iEntity.getMessage();
		if (iEntity.isContainer()) {
			actionItem = iEntity.getItem();
		} else {
			actionItem = null;
		}
		if (iEntity instanceof CombinationLock) {
			actionUnlock = iEntity;
		}
		if (iEntity instanceof Lock) {
			actionUnlock = iEntity;
		}
		if (iEntity instanceof StorageBoxLock) {
			actionUnlock = iEntity;
		}
	}

	public void playerAction() {
		if (actionUnlock != null) {
			if (actionUnlock instanceof Lock) {
				if (((Lock) actionUnlock).attemptUnlock(inventory)) {
					if (lockJoints.containsKey(((Lock) actionUnlock).getName())) {
						world.destroyJoint(lockJoints.remove(((Lock) actionUnlock).getName()));
					} else if (((Lock) actionUnlock).getName().equals("End")) {
						world.destroyJoint(lockJoints.remove("End 1"));
						world.destroyJoint(lockJoints.remove("End 2"));
					}
				}
			} else if (actionUnlock instanceof CombinationLock) {
				if (inputText == null) {
					MyTextInputListener listener = new MyTextInputListener(this);
					Gdx.input.getTextInput(listener, "Please Enter Combo Code", "", "Combo Code");
				} else if (((CombinationLock) actionUnlock).attemptUnlock(inputText)) {
					if (lockJoints.containsKey(((CombinationLock) actionUnlock).getName())) {
						world.destroyJoint(lockJoints.remove(((CombinationLock) actionUnlock).getName()));
					}
				}
			} else if (actionUnlock instanceof StorageBoxLock) {
				if (inputText == null) {
					actionText = "Please enter the number of the item you want to insert. ";
					actionText += inventory.toString();
					MyTextInputListener listener = new MyTextInputListener(this);
					Gdx.input.getTextInput(listener, "Please Enter Slot Number or R to remove item", "", "Slot Number");
				} else {
					if (inputText.equals("R")) {
						inventory.addItem(actionUnlock.getItem());
					} else {
						actionUnlock.item = inventory.remove(inputText);
					}
				}
			}
		}
		if (actionText != null) {
			// Text Area set for actions
			gameScreen.textArea.appendText(actionText + "\n");
			System.out.println(actionText);
		}
		if (actionItem != null) {
			inventory.addItem(actionItem);
		}
	}

	public void endPlayerAction() {
		if (actionUnlock != null && actionUnlock instanceof StorageBoxLock) {
			boolean atLeastOneLocked = false;
			for (StorageBoxLock lock : multiLocks) {
				if (!lock.checkLock()) {
					atLeastOneLocked = true;
					break;
				}
			}
			if (atLeastOneLocked == false) {
				if (lockJoints.containsKey(((StorageBoxLock) actionUnlock).getLockName())) {
					jointToDestroy = lockJoints.remove(((StorageBoxLock) actionUnlock).getLockName());
				}
			}
		}
		
		inputText = null;
		actionUnlock = null;
	}

}