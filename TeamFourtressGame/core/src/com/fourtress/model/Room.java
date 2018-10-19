package com.fourtress.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Room implements Screen {

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	
	private Player player;
	
	public Room() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		renderer.setView(camera);
		renderer.render();
		// Render Player Sprite	
		renderer.getBatch().begin();
		player.draw(renderer.getBatch());
		renderer.getBatch().end();
		
	}
	
	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width + 1;
		camera.viewportHeight = height + 1;
		camera.setToOrtho(false,width / 2, height / 2);
		camera.update();

	}
	
	@Override
	public void pause() {
		
		
	}
	
	@Override
	public void resume() {
		
		
	}
	
	@Override
	public void show() {
		map = new TmxMapLoader().load("maps/map.tmx");	
		renderer = new OrthogonalTiledMapRenderer(map);
		camera = new OrthographicCamera();		
		player = new Player(new Sprite(new Texture("img/playerFrames/still1.png")));
	}
	
	@Override
	public void hide() {
		dispose();
		
	}
	
	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();
		
	}
	
	
}
