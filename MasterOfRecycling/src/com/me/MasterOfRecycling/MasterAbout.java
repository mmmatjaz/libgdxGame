package com.me.MasterOfRecycling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.me.MasterOfRecycling.MasterOfRecycling.ScreenType;

public class MasterAbout implements Screen 
{
	MasterOfRecycling game;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	Texture BackgroundIm;
	
		
	public MasterAbout(MasterOfRecycling GameRoot)
	{
		Texture.setEnforcePotImages(false);
		game=GameRoot;
		
		BackgroundIm  = new Texture(Gdx.files.internal("About/About.png"));
		BackgroundIm.setFilter(TextureFilter.Linear, TextureFilter.Linear);	
		
		camera = new OrthographicCamera();
	    camera.setToOrtho(false, 800, 480);
	    batch = new SpriteBatch();
	    
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
	    camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(BackgroundIm, 0, 0,800,480);
		
		batch.end();	
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
