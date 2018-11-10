package com.fourtress.model;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;

public class Level {
	
	private TiledMap tiledMap;
	private Box2dModel model;
	
	public Level(TiledMap tiledMap, Box2dModel model) {
		this.tiledMap = tiledMap;
		this.model = model;
		createWalls();
	}

	private void createWalls() {
		 MapObjects objects = tiledMap.getLayers().get("Object Layer").getObjects();
		 Array<Body> bodies = new Array<Body>();
		 for(MapObject object : objects) {

	            if (object instanceof TextureMapObject) {
	                continue;
	            }

	            Shape shape;

	            if (object instanceof RectangleMapObject) {
	                shape = BodyFactory.getInstance(model.world).getRectangle((RectangleMapObject)object);
	            } else {
	            	continue;
	            }
	            
	            BodyDef bd = new BodyDef();
	            bd.type = BodyType.StaticBody;
	            Body body = model.world.createBody(bd);
	            body.createFixture(shape, 1);

	            bodies.add(body);

	            shape.dispose();
		 }
	}
	
	public TiledMap getTiledMap() {
		return tiledMap;
	}
}
