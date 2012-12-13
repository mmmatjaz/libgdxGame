package com.me.MasterOfRecycling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.me.MasterOfRecycling.MasterOfRecycling.ScreenType;

public class MasterMenu implements Screen 
{
	MasterOfRecycling game;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private final Texture btnPlayIm;
	private final Texture btnAboutIm;
	private final Texture btnSettingsIm;
	private final Texture BackgroundIm;
	
	
	
	public Stage stage;
	private Table table;
	private OrthographicCamera stageCam;
	
	public MasterMenu(MasterOfRecycling GameRoot)
	{
		//int w = Gdx.graphics.getWidth();
		//int h = Gdx.graphics.getHeight();
		Texture.setEnforcePotImages(false);
		game=GameRoot;
		btnPlayIm = new Texture(Gdx.files.internal("Menu/play-button.png"));
		btnAboutIm   = new Texture(Gdx.files.internal("Menu/about-button.png"));
		btnSettingsIm = new Texture(Gdx.files.internal("Menu/settings-button.png"));
		BackgroundIm  = new Texture(Gdx.files.internal("Menu/background2.png"));
		BackgroundIm.setFilter(TextureFilter.Linear, TextureFilter.Linear);		
		
		camera = new OrthographicCamera();
	    camera.setToOrtho(false, 800, 480);
	    batch = new SpriteBatch();
	   
		stage= new Stage(800f, 480f, false);
		stageCam=new OrthographicCamera(800,480);
		stageCam.setToOrtho(false,800,480);
		//stageCam.translate(-150,-140,100);
		//stageCam.translate(-400,-240,100);
		stage.setCamera(stageCam);
	    table = new Table();
		
	    
	    final ImageButtonStyle imbtnsty= new ImageButtonStyle();
	    imbtnsty.regionDown= new TextureRegion(btnPlayIm);
	    imbtnsty.regionUp= new TextureRegion(btnPlayIm);
	    ImageButton buttonPlay = new ImageButton(imbtnsty);
		buttonPlay.setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				Gdx.app.log("gumb", "knof play");
				game.ChangeScreen(ScreenType.Play);
			}
		});
		
		final ImageButtonStyle imbtnsty2= new ImageButtonStyle();
		imbtnsty2.regionDown= new TextureRegion(btnAboutIm);
		imbtnsty2.regionUp= new TextureRegion(btnAboutIm);
	    ImageButton buttonAbout = new ImageButton(imbtnsty2);
	    buttonAbout.setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				//Gdx.input.setInputProcessor(null);
				game.ChangeScreen(ScreenType.About);
				
			}
		});
		
		final ImageButtonStyle imbtnsty3= new ImageButtonStyle();
		imbtnsty3.regionDown= new TextureRegion(btnSettingsIm);
		imbtnsty3.regionUp= new TextureRegion(btnSettingsIm);
		ImageButton buttonSettings = new ImageButton(imbtnsty3);
		buttonSettings.setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				//Gdx.input.setInputProcessor(null);
				game.ChangeScreen(ScreenType.Settings);
				
			}
		});	
		table.left().bottom();
		table.add(buttonPlay);//.pad(10, 10, 10, 10);	
		table.row();
		table.add(buttonSettings);//.pad(10, 10, 10, 10);
		table.row();
		table.add(buttonAbout);//.pad(10, 10, 10, 10);
		stage.addActor(table);
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
		stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();		
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
