package com.fourtress.model;

import java.util.HashMap;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.fourtress.utils.BodyFactory;

public class Level {

	private TiledMap tiledMap;
	private Box2dModel model;
	private HashMap<String, Item> requiredItems;
	private Boolean lastLevel;
	public String levelName;

	public Level(TiledMap tiledMap, Box2dModel model, HashMap<String, Item> requiredItems) {
		this.tiledMap = tiledMap;
		this.model = model;
		this.requiredItems = requiredItems;
		createWalls();
		createInteractionPoints();
		setPlayerSpawn();
		setPlayerFinish();
		createDoors();
		setLastLevel();
		setLevelName();
	}

	private void createWalls() {
		MapObjects objects = tiledMap.getLayers().get("Object Layer").getObjects();
		for (MapObject object : objects) {

			if (object instanceof TextureMapObject) {
				continue;
			}

			Shape shape;

			if (object instanceof RectangleMapObject) {
				shape = BodyFactory.getInstance(model.world).getRectangle((RectangleMapObject) object);
			} else {
				continue;
			}

			BodyDef bd = new BodyDef();
			bd.type = BodyType.StaticBody;
			Body body = model.world.createBody(bd);
			body.createFixture(shape, 1);

			shape.dispose();
		}
	}

	private void createInteractionPoints() {
		MapObjects objects = tiledMap.getLayers().get("Interaction Layer").getObjects();
		for (MapObject object : objects) {
			if (object instanceof TextureMapObject) {
				continue;
			}

			Shape shape;

			if (object instanceof EllipseMapObject) {
				shape = BodyFactory.getInstance(model.world).getCircle((EllipseMapObject) object);
			} else {
				continue;
			}
			BodyDef bd = new BodyDef();
			bd.type = BodyType.StaticBody;
			Body body = model.world.createBody(bd);
			body.createFixture(shape, 1);
			if (object.getProperties().get("type") != null) {
				if (object.getProperties().get("type").equals("Item")) {
					BodyFactory.getInstance(model.world).makeBodyItemSensor(body,
							(String) object.getProperties().get("Message"),
							requiredItems.get(object.getProperties().get("Item")));
				} else if (object.getProperties().get("type").equals("MultiLock")) {
					model.multiLocks.add(BodyFactory.getInstance(model.world).makeBodyMultiLockSensor(body,
							(String) object.getProperties().get("Message"),
							requiredItems.get((String) object.getProperties().get("Required Item")),
							(String) object.getProperties().get("LockName")));
				} else if (object.getProperties().get("type").equals("Lock")) {
					BodyFactory.getInstance(model.world).makeBodyLockSensor(body,
							(String) object.getProperties().get("Message"),
							(String) object.getProperties().get("Success Message"),
							(Key) requiredItems.get(object.getProperties().get("KeyID")), object.getName());
				} else if (object.getProperties().get("type").equals("ComboLock")) {
					BodyFactory.getInstance(model.world).makeBodyComboLockSensor(body,
							(String) object.getProperties().get("Message"),
							(String) object.getProperties().get("Combination"), object.getName());
				} else {
					BodyFactory.getInstance(model.world).makeBodySensor(body,
							(String) object.getProperties().get("Message"));
				}
			}
			shape.dispose();
		}
	}

	private void createDoors() {
		MapObjects doorObjects = tiledMap.getLayers().get("Door Layer").getObjects();
		for (MapObject door : doorObjects) {
			Shape doorShape;
			if (door instanceof RectangleMapObject) {
				doorShape = BodyFactory.getInstance(model.world).getRectangle((RectangleMapObject) door);
			} else {
				continue;
			}

			BodyDef bdoorBd = new BodyDef();
			bdoorBd.type = BodyType.DynamicBody;
			Body doorBody = model.world.createBody(bdoorBd);
			doorBody.createFixture(doorShape, 10);
			DoorData doorData = new DoorData();
			doorData.doorBody = (RectangleMapObject) door;
			doorBody.setUserData(doorData);
			model.physicsObjects.add(doorBody);
			doorShape.dispose();
			String doorName = door.getName();

			MapObjects hingeObjects = tiledMap.getLayers().get("Hinge Layer").getObjects();
			for (MapObject hinge : hingeObjects) {
				Shape hingeShape;
				if (hinge instanceof EllipseMapObject && hinge.getName().equals(doorName)) {
					hingeShape = BodyFactory.getInstance(model.world).getCircle((EllipseMapObject) hinge);
					
				} else {
					continue;
				}

				BodyDef hingeBd = new BodyDef();
				hingeBd.type = BodyType.StaticBody;
				Body hingeBody = model.world.createBody(hingeBd);
				hingeBody.createFixture(hingeShape, 1);
				doorData.hingeCentre = new Vector2(((EllipseMapObject) hinge).getEllipse().x, ((EllipseMapObject) hinge).getEllipse().y).sub(((RectangleMapObject) door).getRectangle().getPosition(new Vector2()));
						doorBody.getLocalPoint(((CircleShape) hingeShape).getPosition());
				hingeShape.dispose();

				RevoluteJointDef rDef = new RevoluteJointDef();
				rDef.initialize(doorBody, hingeBody,
						new Vector2(((EllipseMapObject) hinge).getEllipse().x / BodyFactory.ppt,
								((EllipseMapObject) hinge).getEllipse().y / BodyFactory.ppt));
				rDef.collideConnected = false;
				model.world.createJoint(rDef);
				break;
			}

			MapObjects lockObjects = tiledMap.getLayers().get("Lock Layer").getObjects();
			for (MapObject lock : lockObjects) {
				Shape lockShape;
				if (lock instanceof EllipseMapObject && lock.getName().equals(doorName)) {
					lockShape = BodyFactory.getInstance(model.world).getCircle((EllipseMapObject) lock);
				} else {
					continue;
				}

				BodyDef lockBd = new BodyDef();
				lockBd.type = BodyType.StaticBody;
				Body lockBody = model.world.createBody(lockBd);
				lockBody.createFixture(lockShape, 1);

				lockShape.dispose();

				DistanceJointDef dDef = new DistanceJointDef();
				dDef.initialize(doorBody, lockBody, doorBody.getWorldCenter(), lockBody.getWorldCenter());
				dDef.collideConnected = false;
				Joint j = model.world.createJoint(dDef);
				model.addLockJoint(lock.getName(), lockBody);
				break;
			}
		}
	}

	private void setPlayerSpawn() {
		MapObjects objects = tiledMap.getLayers().get("Spawn Layer").getObjects();
		MapObject spawn = objects.get("Player Spawn");
		if (spawn instanceof EllipseMapObject) {
			model.setSpawn(((EllipseMapObject) spawn).getEllipse());
		}
	}
	

	private void setPlayerFinish() {
		MapObjects objects = tiledMap.getLayers().get("End Layer").getObjects();
		MapObject finish = objects.get("Player End");
		if (finish instanceof EllipseMapObject) {
			model.setFinish(((EllipseMapObject) finish).getEllipse());
		}
	}
	
	private void setLastLevel() {
		lastLevel = (Boolean) tiledMap.getProperties().get("Last Level");
	}
	
	private void setLevelName() {
		levelName =  (String) tiledMap.getProperties().get("Name");
	}
	
	public Boolean isLastLevel() {
		return lastLevel;
	}
	
	public String getInitialMessage() {
		return (String) tiledMap.getProperties().get("Initial Message");
	}
	

	public TiledMap getTiledMap() {
		return tiledMap;
	}
	
	public void dispose() {
		tiledMap.dispose();
	}
}
