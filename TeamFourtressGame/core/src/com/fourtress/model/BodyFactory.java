package com.fourtress.model;

import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class BodyFactory {

	private World world;
	private static BodyFactory thisInstance;
	public static float ppt = 32f;

	private BodyFactory(World world) {
		this.world = world;
	}

	public static BodyFactory getInstance(World world) {
		if (thisInstance == null) {
			thisInstance = new BodyFactory(world);
		}
		return thisInstance;
	}

	static public FixtureDef makeFixture(Material material, Shape shape) {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;

		switch (material) {
		case Steel:
			fixtureDef.density = 1f;
			fixtureDef.friction = 0.3f;
			fixtureDef.restitution = 0.1f;
			break;
		case Wood:
			fixtureDef.density = 0.5f;
			fixtureDef.friction = 0.7f;
			fixtureDef.restitution = 0.3f;
			break;
		case Rubber:
			fixtureDef.density = 1f;
			fixtureDef.friction = 0f;
			fixtureDef.restitution = 1f;
			break;
		case Stone:
			fixtureDef.density = 1f;
			fixtureDef.friction = 0.9f;
			fixtureDef.restitution = 0.01f;
		case Player:
			fixtureDef.density = 0.5f;
			fixtureDef.friction = 0.25f;
			fixtureDef.restitution = 0.01f;
		default:
			fixtureDef.density = 7f;
			fixtureDef.friction = 0.5f;
			fixtureDef.restitution = 0.3f;
		}
		return fixtureDef;
	}

	public Body makeCirclePolyBody(float posx, float posy, float radius, Material material, BodyType bodyType,
			boolean fixedRotation) {
		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = bodyType;
		boxBodyDef.position.x = posx;
		boxBodyDef.position.y = posy;
		boxBodyDef.fixedRotation = fixedRotation;

		Body boxBody = world.createBody(boxBodyDef);
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(radius / 2);
		boxBody.createFixture(makeFixture(material, circleShape));
		circleShape.dispose();
		return boxBody;
	}

	public Body makeCirclePolyBody(float posx, float posy, float radius, Material material, BodyType bodyType) {
		return makeCirclePolyBody(posx, posy, radius, material, bodyType, false);
	}

	public Body makeBoxPolyBody(float posx, float posy, float width, float height, Material material,
			BodyType bodyType) {
		return makeBoxPolyBody(posx, posy, width, height, material, bodyType, false);
	}

	public Body makeBoxPolyBody(float posx, float posy, float width, float height, Material material, BodyType bodyType,
			boolean fixedRotation) {
		// create a definition
		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = bodyType;
		boxBodyDef.position.x = posx;
		boxBodyDef.position.y = posy;
		boxBodyDef.fixedRotation = fixedRotation;

		// create the body to attach said definition
		Body boxBody = world.createBody(boxBodyDef);
		PolygonShape poly = new PolygonShape();
		poly.setAsBox(width / 2, height / 2);
		boxBody.createFixture(makeFixture(material, poly));
		poly.dispose();

		return boxBody;
	}

	public PolygonShape getRectangle(RectangleMapObject rectangleObject) {
		Rectangle rectangle = rectangleObject.getRectangle();
		PolygonShape polygon = new PolygonShape();
		Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) / ppt,
				(rectangle.y + rectangle.height * 0.5f) / ppt);
		polygon.setAsBox(rectangle.width * 0.5f / ppt, rectangle.height * 0.5f / ppt, size, 0.0f);
		return polygon;
	}

	public void makeBodySensor(Body body, String message) {
		for (Fixture f : body.getFixtureList()) {
			f.setSensor(true);
		}
		body.setUserData(new InteractableEntity(message));
	}

	public void makeBodyItemSensor(Body body, String message, Item item) {
		for (Fixture f : body.getFixtureList()) {
			f.setSensor(true);
		}
		body.setUserData(new InteractableEntity(message, item));
	}

	public StorageBoxLock makeBodyMultiLockSensor(Body body, String message, Item correctItem, String lockName) {
		for (Fixture f : body.getFixtureList()) {
			f.setSensor(true);
		}
		StorageBoxLock sbl =  new StorageBoxLock(message, correctItem, lockName);
		body.setUserData(sbl);
		return sbl;
	}
	public void makeBodyLockSensor(Body body, String message, Key key, String lockName) {
		for (Fixture f : body.getFixtureList()) {
			f.setSensor(true);
		}
		body.setUserData(new Lock(message, key, lockName));
	}
	public void makeBodyComboLockSensor(Body body, String message, String combination, String lockName) {
		for (Fixture f : body.getFixtureList()) {
			f.setSensor(true);
		}
		body.setUserData(new CombinationLock(message, combination, lockName));
	}

	public CircleShape getCircle(EllipseMapObject ellipseObject) {
		Ellipse ellipse = ellipseObject.getEllipse();
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(ellipse.width / (2 * ppt));
		circleShape.setPosition(
				new Vector2((ellipse.x + ellipse.width * 0.5f) / ppt, (ellipse.y + ellipse.height * 0.5f) / ppt));
		return circleShape;
	}
}
