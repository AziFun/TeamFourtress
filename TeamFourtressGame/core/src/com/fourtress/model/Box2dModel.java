package com.fourtress.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.fourtress.controller.KeyboardController;
import com.fourtress.exceptions.InventoryFullException;
import com.fourtress.utils.BodyFactory;
import com.fourtress.utils.ContactListener;
import com.fourtress.views.GameScreen;

public class Box2dModel {

	public final float maxV = 20;
	public final float minV = 10;
	public final float maxI = 10;
	public final float minI = 5;
	public World world;
	public GameScreen gameScreen;
	public Body player;
	public Inventory inventory;
	private BodyFactory bodyFactory;
	public KeyboardController controller;
	private ContactListener listener;
	private String actionText;
	private HashMap<String, Joint> lockJoints;
	private InteractableEntity currentInteractable;
	public boolean recievingCode = false;
	public String inputCode;
	public List<StorageBoxSwitch> multiLocks;
	public Joint jointToDestroy;
	private Body finishLine;
	public List<Body> physicsObjects;

	public Box2dModel(KeyboardController controller, GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		this.controller = controller;
		physicsObjects = new LinkedList<Body>();
		multiLocks = new LinkedList<StorageBoxSwitch>();
		this.inventory = new Inventory();
		this.world = new World(new Vector2(), true);
		listener = new ContactListener(this);
		world.setContactListener(listener);
		bodyFactory = BodyFactory.getInstance(world);
		lockJoints = new HashMap<String, Joint>();
	}

	public void logicStep(float delta) {
		if (jointToDestroy != null) {
			world.destroyJoint(jointToDestroy);
			jointToDestroy = null;
		}

		float playerV;
		float playerI;
		if (controller.shift) {
			playerV = maxV;
			playerI = maxI;
		} else {
			playerV = minV;
			playerI = minI;
			slowPlayerSprint();
		}
		if (controller.left) {
			if (player.getLinearVelocity().x >= -playerV) {
				player.applyLinearImpulse(new Vector2(-playerI, 0), player.getWorldCenter(), true);
			}
		}
		if (controller.right) {
			if (player.getLinearVelocity().x <= playerV) {
				player.applyLinearImpulse(new Vector2(playerI, 0), player.getWorldCenter(), true);
			}
		}
		if (!controller.left && !controller.right) {
			slowPlayerX();
		}
		if (controller.up) {
			if (player.getLinearVelocity().y <= playerV) {
				player.applyLinearImpulse(new Vector2(0, playerI), player.getWorldCenter(), true);
			}
		}
		if (controller.down) {
			if (player.getLinearVelocity().y >= -playerV) {
				player.applyLinearImpulse(new Vector2(0, -playerI), player.getWorldCenter(), true);
			}
		}
		if (!controller.up && !controller.down) {
			slowPlayerY();
		}
		if (recievingCode) {
			if (inputCode == null) {
				inputCode = "";
				gameScreen.write("> ");
			}
			if (controller.enter) {
				gameScreen.write("\n");
				inputCode();
				recievingCode = false;
				controller.enter = false;
			} else if (controller.num0) {
				inputCode += "0";
				controller.num0 = false;
				gameScreen.write("0");
			} else if (controller.num1) {
				inputCode += "1";
				controller.num1 = false;
				gameScreen.write("1");
			} else if (controller.num2) {
				inputCode += "2";
				controller.num2 = false;
				gameScreen.write("2");
			} else if (controller.num3) {
				inputCode += "3";
				controller.num3 = false;
				gameScreen.write("3");
			} else if (controller.num4) {
				inputCode += "4";
				controller.num4 = false;
				gameScreen.write("4");
			} else if (controller.num5) {
				inputCode += "5";
				controller.num5 = false;
				gameScreen.write("5");
			} else if (controller.num6) {
				inputCode += "6";
				controller.num6 = false;
				gameScreen.write("6");
			} else if (controller.num7) {
				inputCode += "7";
				controller.num7 = false;
				gameScreen.write("7");
			} else if (controller.num8) {
				inputCode += "8";
				controller.num8 = false;
				gameScreen.write("8");
			} else if (controller.num9) {
				inputCode += "9";
				controller.num9 = false;
				gameScreen.write("9");
			} else if (controller.backspace) {
				inputCode.substring(0, inputCode.length() - 2);
				controller.backspace = false;
				gameScreen.write(" X\n> " + inputCode);
			}
		} else if (isStorageAvailable()) {
			if (currentInteractable.item == null) {
				if (controller.num1) {
					currentInteractable.item = inventory.remove(1);
					controller.num1 = false;
				} else if (controller.num2) {
					currentInteractable.item = inventory.remove(2);
					controller.num2 = false;
				} else if (controller.num3) {
					currentInteractable.item = inventory.remove(3);
					controller.num3 = false;
				} else if (controller.num4) {
					currentInteractable.item = inventory.remove(4);
					controller.num4 = false;
				} else if (controller.num5) {
					currentInteractable.item = inventory.remove(5);
					controller.num5 = false;
				} else if (controller.num6) {
					currentInteractable.item = inventory.remove(6);
					controller.num6 = false;
				} else if (controller.num7) {
					currentInteractable.item = inventory.remove(7);
					controller.num7 = false;
				} else if (controller.num8) {
					currentInteractable.item = inventory.remove(8);
					controller.num8 = false;
				} else if (controller.num9) {
					currentInteractable.item = inventory.remove(9);
					controller.num9 = false;
				}
			} else if (controller.enter) {
				try {
					inventory.addItem(currentInteractable.item);
					currentInteractable.item = null;
				} catch (Exception e) {
					gameScreen.write("You are carrying too much\n");
				}
				controller.enter = false;
			}
		} else if (controller.enter) {
			controller.enter = false;
			interact();
		}

		world.step(delta, 3, 3);
	}

	private void inputCode() {
		if (currentInteractable instanceof CombinationLock) {
			if (((CombinationLock) currentInteractable).attemptUnlock(inputCode)) {
				if (lockJoints.containsKey(((CombinationLock) currentInteractable).getName())) {
					world.destroyJoint(lockJoints.remove(((CombinationLock) currentInteractable).getName()));
					gameScreen.write("Door unlocked\n");
				}
			}
			inputCode = null;
		}
	}

	public void slowPlayerX() {
		player.setLinearVelocity(player.getLinearVelocity().scl(0.75f, 1f));
	}

	public void slowPlayerY() {
		player.setLinearVelocity(player.getLinearVelocity().scl(1f, 0.75f));
	}

	public void slowPlayerSprint() {
		if (player.getLinearVelocity().y > minV) {
			player.applyForceToCenter(0, -50, true);
		}
		if (player.getLinearVelocity().y < -minV) {
			player.applyForceToCenter(0, +50, true);
		}
		if (player.getLinearVelocity().x > minV) {
			player.applyForceToCenter(-50, 0, true);
		}
		if (player.getLinearVelocity().x < -minV) {
			player.applyForceToCenter(+50, 0, true);
		}
	}

	public void addLockJoint(String name, Joint j) {
		lockJoints.put(name, j);
	}

	public void setSpawn(Ellipse spawn) {
		player = bodyFactory.makeCirclePolyBody(spawn.x / BodyFactory.ppt, spawn.y / BodyFactory.ppt, 1.5f, Material.Player, BodyType.DynamicBody, false);
		player.setUserData("Player");
	}

	public void setFinish(Ellipse finish) {
		finishLine = bodyFactory.makeCirclePolyBody((finish.x + finish.width / 2) / BodyFactory.ppt, (finish.y + finish.height / 2) / BodyFactory.ppt, finish.width / BodyFactory.ppt, Material.Rubber, BodyType.StaticBody, false);
		bodyFactory.makeBodySensor(finishLine, "finish");
		finishLine.setUserData("finish");
	}

	public void setInteractable(InteractableEntity iEntity) {
		actionText = iEntity.getMessage();
		currentInteractable = iEntity;

	}

	public void interact() {
		if (currentInteractable != null) {
			if (currentInteractable instanceof Lock) {
				if (((Lock) currentInteractable).attemptUnlock(inventory)) {
					actionText = "Door unlocked";
					if (lockJoints.containsKey(((Lock) currentInteractable).getName())) {
						world.destroyJoint(lockJoints.remove(((Lock) currentInteractable).getName()));
					} else if (((Lock) currentInteractable).getName().equals("End")) {
						world.destroyJoint(lockJoints.remove("End 1"));
						world.destroyJoint(lockJoints.remove("End 2"));
					}
				}
			} else if (currentInteractable instanceof CombinationLock) {
				recievingCode = true;
				gameScreen.write("Please enter the combination\n");
			}

			if (currentInteractable.item != null) {
				try {
					inventory.addItem(currentInteractable.getItem());
				} catch (InventoryFullException e) {
					gameScreen.write("You are carrying too much\n");
				}
			}
		}
		if (actionText != null && !(currentInteractable instanceof CombinationLock)) {
			// Text Area set for actions
			gameScreen.write(actionText + "\n");
			System.out.println(actionText);
			}
	}

	public void endInteraction() {
		if (currentInteractable != null && currentInteractable instanceof StorageBox) {
			boolean atLeastOneLocked = false;
			for (StorageBoxSwitch zwich : multiLocks) {
				if (!zwich.checkSwitch()) {
					atLeastOneLocked = true;
					break;
				}
			}
			if (atLeastOneLocked == false) {
				if (lockJoints.containsKey(((StorageBoxSwitch) currentInteractable).getLockName())) {
					jointToDestroy = lockJoints.remove(((StorageBoxSwitch) currentInteractable).getLockName());
				}
			}
		}
		if (recievingCode) {
			recievingCode = false;
			gameScreen.write("\n");
		}
		inputCode = null;
		currentInteractable = null;
		actionText = null;
	}

	public boolean isActionAvailable() {
		return actionText != null;
	}

	public boolean isStorageAvailable() {
		return currentInteractable instanceof StorageBoxSwitch;
	}
	
	public void dispose() {
		world.dispose();
	}
}