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

public class MasterAbout implements Screen 
{
	MasterOfRecycling game;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Table tableLL;
	public Stage stage;
	private OrthographicCamera stageCam;

	private Texture BackgroundIm;
	private Texture MenuMask;
	private Texture InstructIm;
		
	private final float instFracX=.75f;
	private final float instFracY=.8f;
	
	public MasterAbout(MasterOfRecycling GameRoot)
	{
		Texture.setEnforcePotImages(false);
		game=GameRoot;
		
		BackgroundIm  = new Texture(Gdx.files.internal("common/background2.png"));
		InstructIm  = new Texture(Gdx.files.internal("About/instruct.png"));
		BackgroundIm.setFilter(TextureFilter.Linear, TextureFilter.Linear);	
		MenuMask = new Texture(Gdx.files.internal("Pause/background.png"));
		camera = new OrthographicCamera();
	    camera.setToOrtho(false, 800, 480);
	    batch = new SpriteBatch();
	    
	    final ImageButtonStyle imbtnsty11= new ImageButtonStyle();
	    imbtnsty11.regionDown= new TextureRegion(
	    		new Texture(Gdx.files.internal("common/back.png")));
	    imbtnsty11.regionUp= imbtnsty11.regionDown;//new TextureRegion(btnPlayIm);
	    final ImageButton buttonBack1 = new ImageButton(imbtnsty11);
		buttonBack1.setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				//Gdx.app.log("gumb", "knof play");
				game.ChangeScreen(ScreenType.Menu);
			}
		});
		
		stage= new Stage(800f, 480f, false);
		stageCam=new OrthographicCamera(800,480);
		stageCam.setToOrtho(false,800,480);
		//stageCam.translate(-150,-140,100);
		//stageCam.translate(-400,-240,100);
		stage.setCamera(stageCam);
		
		tableLL=new Table();
	    tableLL.setFillParent(true);    
		tableLL.clear();// = new Table();
	    tableLL.left().bottom();
		tableLL.add(buttonBack1);
		stage.addActor(tableLL);
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
	    camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(BackgroundIm,0, 0, game.appWidth, game.appHeigth);
		batch.draw(MenuMask, 	0, 0, game.appWidth, game.appHeigth);
		int offx=(int)((1f-instFracX)*(float)game.appWidth /2f);
		int offy=(int)((1f-instFracY)*(float)game.appHeigth/2f);
		batch.draw(InstructIm, 
				offx,offy,(instFracX*game.appWidth),(instFracY*game.appHeigth));
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
