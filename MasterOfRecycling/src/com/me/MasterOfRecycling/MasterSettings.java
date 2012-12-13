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
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.ValueChangedListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.badlogic.gdx.utils.Array;
import com.me.MasterOfRecycling.MasterOfRecycling.ScreenType;

public class MasterSettings implements Screen 
{
	MasterOfRecycling game;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	public Stage stage;
	private OrthographicCamera stageCam;
	
	
	private Table tableC;
	private Table tableLL;
	private Table tableSlider;
	private Texture BackgroundIm;
	
	private ImageButtonStyle btnSndOnSty;
	private ImageButtonStyle btnSndOffSty;
	private ImageButton buttonSound;

	
	
	public MasterSettings(MasterOfRecycling GameRoot)
	{
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		Texture.setEnforcePotImages(false);
		game=GameRoot;

		BackgroundIm  = new Texture(Gdx.files.internal("Settings/background.png"));
		BackgroundIm.setFilter(TextureFilter.Linear, TextureFilter.Linear);	
		
		final Texture btnOnIm = new Texture(Gdx.files.internal("Settings/on.png"));
		final Texture btnOffIm = new Texture(Gdx.files.internal("Settings/off.png"));
		final Texture btnSensIm = new Texture(Gdx.files.internal("Settings/sens.png"));
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
	    batch = new SpriteBatch();
				
	    stage= new Stage(800f, 480f, false);
	    stageCam = new OrthographicCamera();
		stageCam.setToOrtho(false, 800, 480);
		stageCam.translate(-400,-240,100);
		//stage.setCamera(stageCam);
	    tableC=new Table();
	    tableC.setFillParent(true);
	    tableLL=new Table();
	    tableLL.setFillParent(true);    
	    tableSlider=new Table();
	    tableSlider.setFillParent(true);    
	    stage.addActor(tableLL);
	    stage.addActor(tableC);
	    //stage.addActor(tableSlider);
	    
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
				game.updateSens();
				game.ChangeScreen(ScreenType.Menu);
			}
		});
		
		// sound
		Gdx.app.log("sound", "btn inflate "+game.soundOn);
		btnSndOnSty= new ImageButtonStyle();
		btnSndOnSty.regionDown=new TextureRegion(btnOnIm);
		btnSndOnSty.regionUp=btnSndOnSty.regionDown;
	    btnSndOffSty= new ImageButtonStyle();
	    btnSndOffSty.regionDown= new TextureRegion(btnOffIm);
	    btnSndOffSty.regionUp= btnSndOffSty.regionDown;
		buttonSound = new ImageButton(
				game.soundOn ? btnSndOnSty:btnSndOffSty);
	    buttonSound.setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				Gdx.app.log("sound", "btn clicked "+game.soundOn);
				game.soundOn=!game.soundOn;
				Gdx.app.log("sound", "changed "+game.soundOn);
				buttonSound.setStyle(game.soundOn ? btnSndOnSty:btnSndOffSty);
				
			    game.writeSoundPref();
		}});
		
		final ImageButtonStyle btnSensSty= new ImageButtonStyle();
		btnSensSty.regionDown=new TextureRegion(
	    		new Texture(Gdx.files.internal("Settings/sens.png")));
		btnSensSty.regionUp= btnSensSty.regionDown;
		ImageButton buttonSens = new ImageButton(btnSensSty);
		buttonSens.setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				//Gdx.input.setInputProcessor(null);
				//game.ChangeScreen(ScreenType.About);			
		}});	
		
		//SliderStyle slsty=new SL
		Skin skin = new Skin(Gdx.files.internal("Settings/uiskin.json"), Gdx.files.internal("Settings/uiskin.png"));
		Slider slider=new Slider(1f, 5f, .1f, skin.getStyle(SliderStyle.class));
		slider.setValue(game.sensitivity);
		slider.setValueChangedListener(new ValueChangedListener() {		
			@Override
			public void changed(Slider slider, float value) {
				// TODO Auto-generated method stub	
				Gdx.app.log("slider", ""+value);
				game.sensitivity=value;
			}
		});

		tableC.clear();// = new Table();
	    tableC.center();
	    tableC.add(buttonSound).pad(0, 0, 0, 0);
	    //tableC.add(buttonSens).pad(0, 20, 0, 0);//.pad(10, 10, 10, 10);
	    tableC.add(slider).width(300).pad(0, 100, 0, 0);
		
	    tableLL.clear();// = new Table();
	    tableLL.left().bottom();
		tableLL.add(buttonBack);//.pad(10, 10, 10, 10);
		//tableLL.add(slider).width(300);
		
	    
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
