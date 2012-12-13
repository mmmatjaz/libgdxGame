package com.me.MasterOfRecycling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.badlogic.gdx.utils.Array;
import com.me.MasterOfRecycling.MasterOfRecycling.ScreenType;

public class MasterEndGame implements Screen 
{
	MasterOfRecycling game;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	Texture btnPlayIm;
	Texture btnAboutIm;
	Texture btnSettingsIm;
	Texture BackgroundIm;
	
	Rectangle btnPlayRect;
	Rectangle btnAboutRect;
	Rectangle btnSettingsRect;
	Rectangle BackgroundRect;
	
	Rectangle touchPos;
	Color c;
	
	
	boolean buttonHeld[];

	public Stage stage;
	private Table table;
	private OrthographicCamera stageCam;
	private Skin skin;

	private Texture btnOnIm;

	private Texture btnOffIm;

	private Texture btnSensIm;
	
	public MasterEndGame(MasterOfRecycling GameRoot)
	{
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		Texture.setEnforcePotImages(false);
		game=GameRoot;

		BackgroundIm  = new Texture(Gdx.files.internal("common/background.png"));
		
		btnOnIm = new Texture(Gdx.files.internal("Settings/on.png"));
		btnOffIm = new Texture(Gdx.files.internal("Settings/off.png"));
		btnSensIm = new Texture(Gdx.files.internal("Settings/off.png"));
		BackgroundIm.setFilter(TextureFilter.Linear, TextureFilter.Linear);	
		
		
		camera = new OrthographicCamera();
	    camera.setToOrtho(false, w, h);
	    batch = new SpriteBatch();
	    
	    skin = new Skin(Gdx.files.internal("skin/buttons.json"),
	    		Gdx.files.internal("skin/button.png"));
		stage= new Stage(800f, 480f, false);
		stageCam=new OrthographicCamera(800,480);
		stageCam.setToOrtho(false,800,480);
		stageCam.translate(-150,-140,100);
		stage.setCamera(stageCam);
	    table = new Table();
		
	    //back button
	    final ImageButtonStyle imbtnsty= new ImageButtonStyle();
	    imbtnsty.regionDown= new TextureRegion(
	    		new Texture(Gdx.files.internal("common/back.png")));
	    imbtnsty.regionUp= imbtnsty.regionDown;//new TextureRegion(btnPlayIm);
	    ImageButton buttonBack = new ImageButton(imbtnsty);
		buttonBack.setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				//Gdx.app.log("gumb", "knof play");
				game.ChangeScreen(ScreenType.Menu);
			}
		});
		
		//restart
		final ImageButtonStyle imbtnsty2= new ImageButtonStyle();
		imbtnsty2.regionDown= new TextureRegion(
				new Texture(Gdx.files.internal("EndGame/restart.png")));
		imbtnsty2.regionUp= imbtnsty2.regionDown;
	    ImageButton buttonRestart = new ImageButton(imbtnsty2);
	    buttonRestart.setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				//Gdx.input.setInputProcessor(null);
				game.ChangeScreen(ScreenType.Play);
				
			}
		});
		
	    //next
		final ImageButtonStyle imbtnsty3= new ImageButtonStyle();
		imbtnsty3.regionDown= new TextureRegion(
				new Texture(Gdx.files.internal("EndGame/next.png")));
		imbtnsty3.regionUp= imbtnsty3.regionDown;
		ImageButton buttonNext = new ImageButton(imbtnsty3);
		buttonNext.setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				//Gdx.input.setInputProcessor(null);
				//game.ChangeScreen(ScreenType.About);
				
			}
		});	
		
		table.add(buttonRestart).pad(0, 500, 0, 100);
		table.add(buttonNext);//.pad(10, 10, 10, 10);
		table.row();
		table.add(buttonBack);//.pad(10, 10, 10, 10);
		stage.addActor(table);
	    
	    //Gdx.input.setInputProcessor(inProcListener);
	    
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
