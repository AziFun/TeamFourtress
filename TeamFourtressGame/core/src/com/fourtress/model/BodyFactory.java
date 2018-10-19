package com.fourtress.model;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
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
	
	static public FixtureDef makeFixture(Material material, Shape shape){
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
			
		switch(material){
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
		default:
			fixtureDef.density = 7f;
			fixtureDef.friction = 0.5f;
			fixtureDef.restitution = 0.3f;
		}
		return fixtureDef;
	}
	
	public Body makeCirclePolyBody(float posx, float posy, float radius, Material material, BodyType bodyType, boolean fixedRotation){
		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = bodyType;
		boxBodyDef.position.x = posx;
		boxBodyDef.position.y = posy;
		boxBodyDef.fixedRotation = fixedRotation;
			
		Body boxBody = world.createBody(boxBodyDef);
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(radius /2);
		boxBody.createFixture(makeFixture(material,circleShape));
		circleShape.dispose();
		return boxBody;
	}
	
	public Body makeCirclePolyBody(float posx, float posy, float radius, Material material, BodyType bodyType){
		return makeCirclePolyBody( posx,  posy,  radius,  material,  bodyType,  false);
	}
	
	public Body makeBoxPolyBody(float posx, float posy, float width, float height,Material material, BodyType bodyType){
		return makeBoxPolyBody(posx, posy, width, height, material, bodyType, false);
	}
		
	public Body makeBoxPolyBody(float posx, float posy, float width, float height,Material material, BodyType bodyType, boolean fixedRotation){
		// create a definition
		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = bodyType;
		boxBodyDef.position.x = posx;
		boxBodyDef.position.y = posy;
		boxBodyDef.fixedRotation = fixedRotation;
			
		//create the body to attach said definition
		Body boxBody = world.createBody(boxBodyDef);
		PolygonShape poly = new PolygonShape();
		poly.setAsBox(width/2, height/2);
		boxBody.createFixture(makeFixture(material,poly));
		poly.dispose();

		return boxBody;
	}
}
