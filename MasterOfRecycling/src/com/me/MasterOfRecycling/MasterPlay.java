package com.me.MasterOfRecycling;

import java.util.Iterator;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Align;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.badlogic.gdx.utils.Array;
import com.me.MasterOfRecycling.MasterOfRecycling.ScreenType;


public class MasterPlay implements Screen
{
	private enum GameStatus {
		Playing, PauseScreen, GameOver
	}
	private enum PackList {
		Plastic,Eco, Glass, Rock, Wrong, Life, Bonus
	}
	class PackType
	{
		public final Texture PackTexture;
		public final int pointsPlus;
		public final int pointsMinus;
		public final PackList TypeID;
		public int dropped;
		public PackType(PackList Type, Texture Image, int pp, int pm)
		{
			TypeID=Type;
			PackTexture=Image;
			pointsPlus=pp;
			pointsMinus=pm;
			dropped=0;
		}
	}
	class Package
	{
		public PackType pType;
		public Rectangle pRect;	
		public Package(PackType pt, Rectangle pr)
		{
			pType=pt;
			pRect=pr;
		}
	}
	
	class Truck
	{
		private final static float TRUCK_POS_Y=10;
		private final static float COL_OFF_X_L=15;
		private final static float COL_OFF_X_R=30;
		private final static float COL_OFF_TOP=20;
		private final static float COL_OFF_BOT=20;
		public TextureRegion texture;
		public float x;		// position
		public float x_;	// prev position
		public float dx;	// velocity
		public float dxd;	// desired velocity
		public Rectangle rect;
		public Rectangle colRect;
		
		public Truck(TextureRegion im)
		{
			texture=im;
			x=800/2-texture.getRegionWidth()/2;
			x_=x; dx=0f; dxd=0f;
			rect=			new Rectangle();
			rect.height=	texture.getRegionHeight();
			rect.width=		texture.getRegionWidth();
			rect.x=x;
			rect.y=			TRUCK_POS_Y;
			
			colRect=new Rectangle(rect);
			colRect.width -=COL_OFF_X_L+COL_OFF_X_R;
			colRect.height-=COL_OFF_TOP+COL_OFF_BOT;
			colRect.y=		TRUCK_POS_Y+COL_OFF_BOT;
		}
		
		public float Move(float vel, float dt)
		{
			dxd=vel;
			x+=dxd;
			if (x<0) x=0;
			if (x>800-texture.getRegionWidth()) x=800-texture.getRegionWidth();
			dx=(x-x_)/dt;
			x_=x;
			rect.x=x;
			colRect.x=x+COL_OFF_X_L;
			//colRect.y=30;
			return dx;
		}
		
		public void ResetTruck()
		{
			x=800/2-texture.getRegionWidth()/2;
			x_=x; dx=0f; dxd=0f;
			this.Move(0f, 1e-10f);
		}
	}
	
	MasterOfRecycling game;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private Texture BackgroundIm;
	private Texture MenuMask;
	private Texture fuelIm;
	private Texture fuelEIm;
	private TextureRegion fuelLeft;
	private Sound dropSound;
	private Array<PackType> PackageTypes;
	private Array<Package> Packages;
	//private final PackType[] PackTypes;
	private float time;
	private final float maxTime=60;
	private float lastDropTime;
	private float lastRandomization;
	private int points;
	private float fuel;	
	private float Kf=0.0007f*0.8f;
	private GameStatus status; 
		
	private BitmapFont fontTime;
	private BitmapFont fontPts;
	private BitmapFont fontPtsGreen;
	private BitmapFont fontSmall;
	
	private PackType targetPkg;		
	private Truck kamjon;
	public Stage stage;
	private OrthographicCamera stageCam;
	private ImageButton buttonPause;

	private Table tableC;
	private Table tableLL;
	private Table tableEnd;

	public MasterPlay(MasterOfRecycling Game)
	{
		game=Game;
		Texture.setEnforcePotImages(false);
		Gdx.graphics.getWidth();
		Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera();
	    camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		batch.enableBlending();
		
		loadAssets();
		kamjon=new Truck(new TextureRegion( new Texture(Gdx.files.internal("Game/truck.png"))));
		Packages = new Array<Package>();				
	        
	    fontTime = new BitmapFont(Gdx.files.internal("font/arial128.fnt"),
	            Gdx.files.internal("font/arial128.png"), false);
	    fontTime.setColor(Color.ORANGE);
	    fontTime.setScale(0.56f);
	    
	    fontPts = new BitmapFont(Gdx.files.internal("font/arial128.fnt"),
	            Gdx.files.internal("font/arial128.png"), false);
	    fontPts.setColor(Color.WHITE);
	    fontPts.setScale(0.75f);
	    
	    fontSmall = new BitmapFont(Gdx.files.internal("font/arial128.fnt"),
	            Gdx.files.internal("font/arial128.png"), false);
	    fontSmall.setColor(Color.WHITE);
	    fontSmall.setScale(0.35f);
	    
	    
	    stage= new Stage(800f, 480f, false);
	    stageCam = new OrthographicCamera();
		stageCam.setToOrtho(false, 800, 480);
		//stageCam.translate(-400,-240,100);
		//stageCam.translate(-150,-140,100);
		stage.setCamera(stageCam);
	    tableC=new Table();
	    tableC.setFillParent(true);
	    tableLL=new Table();
	    tableLL.setFillParent(true);    
	    tableEnd=new Table();
	    tableEnd.setFillParent(true);    
	    stage.addActor(tableLL);
	    stage.addActor(tableC);
	    stage.addActor(tableEnd);
	    
	    
		
	    restartGame();
	}

	
	
	private void loadAssets() 
	{
		final Texture pBonusIm = new Texture(Gdx.files.internal("Game/pbonus.png"));
		final Texture pEcoIm   = new Texture(Gdx.files.internal("Game/peco.png"));
		final Texture pGlassIm = new Texture(Gdx.files.internal("Game/pglass.png"));
		final Texture pLifeIm  = new Texture(Gdx.files.internal("Game/plife.png"));
		final Texture pPlastIm = new Texture(Gdx.files.internal("Game/pplastic.png"));
		final Texture pRockIm  = new Texture(Gdx.files.internal("Game/prock.png"));
		final Texture pWrongIm = new Texture(Gdx.files.internal("Game/pwrong.png"));
		
		PackageTypes=new Array<PackType>();
		PackageTypes.add(new PackType(PackList.Eco, 	pEcoIm,		20,10));
		PackageTypes.add(new PackType(PackList.Glass, 	pGlassIm,	20,10));
		PackageTypes.add(new PackType(PackList.Plastic, pPlastIm,	20,10));
		PackageTypes.add(new PackType(PackList.Bonus, 	pBonusIm,	20,0));
		PackageTypes.add(new PackType(PackList.Life, 	pLifeIm,	0,0));
		PackageTypes.add(new PackType(PackList.Rock, 	pRockIm,	0,20));
		PackageTypes.add(new PackType(PackList.Wrong, 	pWrongIm,	0,20));	
		
		BackgroundIm = new Texture(Gdx.files.internal("Game/background.png"));
		MenuMask = new Texture(Gdx.files.internal("Pause/background.png"));
		
		
		fuelIm = new Texture(Gdx.files.internal("Game/fuel.png"));
		fuelEIm = new Texture(Gdx.files.internal("Game/fuel-e.png"));
		fuelLeft = new TextureRegion(fuelIm);
		dropSound = Gdx.audio.newSound(Gdx.files.internal("Game/drop.wav"));
	}

	public void restartGame()
	{
		status=GameStatus.Playing;
	    points=0;
	    fuel=100f;
	    time=maxTime;
	    lastDropTime=time;
	    lastRandomization=time;
	    randomTargetPackage();
	    Packages.clear();
	    kamjon.ResetTruck();
	    constructStage(GameStatus.Playing);
	}
	
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		if (status==GameStatus.Playing)
		{
			time-=delta;
			
			if (time<=0 || fuel<=0)
			{
				//game.ChangeScreen(ScreenType.GameOver);
				endGame();
			}
			float accelY = Gdx.input.getAccelerometerY();
			//Gdx.app.log("acc", accelX + " | " + accelY + " | " + accelZ);	
			float Ka=game.sensitivity;
			
			
			if (fuel>0f)
				fuel-=Math.abs(Kf*kamjon.Move(Ka*(accelY), delta));
			
			 
			if (time - lastDropTime < -1) 
				spawnRaindrop();
			if (time - lastRandomization < -MathUtils.random(5, 15))
				randomTargetPackage();
			
			Iterator<Package> iter = Packages.iterator();
			
			
			
		    while(iter.hasNext()) 
		    {
		    	Package p = iter.next();
		        p.pRect.y -= 200 * Gdx.graphics.getDeltaTime();
		        if(p.pRect.y + 48 < 0)
		        {
		    	   iter.remove();
		        }
		        if(p.pRect.overlaps(kamjon.colRect)) {
	        		if (targetPkg.TypeID==p.pType.TypeID ||
	        				p.pType.TypeID==PackList.Bonus)
	        		{
	        			points+=p.pType.pointsPlus;
	    			}
		        	else if (p.pType.TypeID==PackList.Life)
		        		{	
		        			fuel+=20;
		        			if (fuel>100) fuel=100;
		        		}
		        	else points-=p.pType.pointsMinus;
	        		iter.remove();
	        		if (game.soundOn) dropSound.play();
		        }
		    }
		}
	    //DRAWING PART
	    Gdx.gl.glClearColor(0, 0, 0.2f, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
	    camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.disableBlending();
		batch.draw(BackgroundIm, 0, 0,800,480);
		batch.enableBlending();
		batch.draw(kamjon.texture, kamjon.x, 20);
		batch.draw(targetPkg.PackTexture, kamjon.x+70, 20+58);
		
		for(Package pack: Packages) 
		{
		    batch.draw(pack.pType.PackTexture, pack.pRect.x, pack.pRect.y);
		}
		
		fontTime.drawWrapped(batch, ""+(int)(time+.5), 560.f, 340.f, 200.f, HAlignment.CENTER);
		fontPts.drawWrapped(batch, ""+points, 560.f, 450.f, 200.f, HAlignment.CENTER);
		
		batch.draw(fuelEIm, 300, 420);

		fuelLeft.setRegion(.0f,.0f,214f/100f*fuel,1f);
		fuelLeft.setRegion(0f, 0f, fuel/100.f, 1f);
		batch.draw(fuelLeft, 300, 420);
		if (status==GameStatus.GameOver || status==GameStatus.PauseScreen)
		{
			batch.draw(MenuMask, 0, 0,800,480);
			if (status==GameStatus.GameOver)
				fontPts.drawWrapped(batch, ""+points, 560.f, 450.f, 200.f, HAlignment.CENTER);
				
		}
		batch.end();
		
		stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
	}

	private void endGame() 
	{
		String stats="";
		for (int i=0; i<PackageTypes.size; i++)
		{		
			stats+=" "+PackageTypes.get(i).dropped;
			PackageTypes.get(i).dropped=0;
		}
		Gdx.app.log("stats", stats);
		status=GameStatus.GameOver;
		constructStage(GameStatus.GameOver);
	}

	private void constructStage(GameStatus s)
	{
		switch (s)
		{
		case Playing:
			final ImageButtonStyle imbtnsty= new ImageButtonStyle();
		    imbtnsty.regionDown= new TextureRegion(
		    		new Texture(Gdx.files.internal("Pause/pause.png")));
		    imbtnsty.regionUp= imbtnsty.regionDown;//new TextureRegion(btnPlayIm);
		    buttonPause = new ImageButton(imbtnsty);
		    buttonPause.setClickListener(new ClickListener() {
				@Override
				public void click(Actor actor, float x, float y) {
					Gdx.app.log("gumb", "pause");
					status=GameStatus.PauseScreen;
					constructStage(GameStatus.PauseScreen);
				}
			});			
		    tableEnd.clear();// = new Table();
		    tableC.clear();
			tableLL.clear();// = new Table();
			tableLL.left().bottom();
			tableLL.add(buttonPause).pad(0, 10, 10, 0);//.left();
			fontPts.setColor(Color.WHITE);
			break;		
		case GameOver:
			//back button
			boolean better=game.updateScore(points);
		    final ImageButtonStyle imbtnsty1= new ImageButtonStyle();
		    imbtnsty1.regionDown= new TextureRegion(
		    		new Texture(Gdx.files.internal("common/back.png")));
		    imbtnsty1.regionUp= imbtnsty1.regionDown;//new TextureRegion(btnPlayIm);
		    ImageButton buttonBack = new ImageButton(imbtnsty1);
			buttonBack.setClickListener(new ClickListener() {
				@Override
				public void click(Actor actor, float x, float y) {
					game.ChangeScreen(ScreenType.Menu);
			}});
			
			//restart
			final ImageButtonStyle imbtnsty2= new ImageButtonStyle();
			imbtnsty2.regionDown= new TextureRegion(
					new Texture(Gdx.files.internal("EndGame/restart.png")));
			imbtnsty2.regionUp= imbtnsty2.regionDown;
		    ImageButton buttonRestart = new ImageButton(imbtnsty2);
		    buttonRestart.setClickListener(new ClickListener() {
				@Override
				public void click(Actor actor, float x, float y) {
					restartGame();
					status=GameStatus.Playing;
					constructStage(GameStatus.Playing);
			}});
			
		    //next
			final ImageButtonStyle imbtnsty3= new ImageButtonStyle();
			imbtnsty3.regionDown= new TextureRegion(
					new Texture(Gdx.files.internal("EndGame/next.png")));
			imbtnsty3.regionUp= imbtnsty3.regionDown;
			ImageButton buttonNext = new ImageButton(imbtnsty3);
			buttonNext.setClickListener(new ClickListener() {
				@Override
				public void click(Actor actor, float x, float y) {
			}});	
			final Color green= new Color(0.173f, 0.698f, 0.122f,1f);
			final LabelStyle styNo=new LabelStyle(fontTime,green);
			final LabelStyle styTitle=new LabelStyle(fontSmall, green);
			
			tableEnd.clear();// = new Table();
			tableEnd.center().top();
			String bestTitle = better ?
					"New best score!" : "Best score";
			tableEnd.add(new Label(bestTitle, styTitle)).align(Align.CENTER);;
			tableEnd.row();
			
			if (!better)
				tableEnd.add(new Label(""+game.scoreBest, styNo)).align(Align.CENTER);
			//tableEnd.add(new Label(""+game.scoreLast, styNo)).pad(0, 20, 0, 0);
			//tableEnd.row();
			//tableEnd.add(new Label("Score", styTitle)).pad(0, 20, 0, 0);
			
		    tableC.clear();// = new Table();
		    tableC.center();
		    tableC.add(buttonRestart).pad(0, 0, 0, 0);
			//table.add(buttonNext);//.pad(10, 10, 10, 10);
			tableLL.clear();// = new Table();
		    tableLL.left().bottom();
			tableLL.add(buttonBack);//.pad(10, 10, 10, 10);
			fontPts.setColor(green);
			break;
			
		case PauseScreen:
			//back button
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
			
			//restart
			final ImageButtonStyle imbtnsty21= new ImageButtonStyle();
			imbtnsty21.regionDown= new TextureRegion(
					new Texture(Gdx.files.internal("EndGame/restart.png")));
			imbtnsty21.regionUp= imbtnsty21.regionDown;
		    final ImageButton buttonRestart1 = new ImageButton(imbtnsty21);
		    buttonRestart1.setClickListener(new ClickListener() {
				@Override
				public void click(Actor actor, float x, float y) {
					//Gdx.input.setInputProcessor(null);
					restartGame();
					status=GameStatus.Playing;
					constructStage(GameStatus.Playing);				
				}
			});
			
		    //next
			final ImageButtonStyle imbtnsty31= new ImageButtonStyle();
			imbtnsty31.regionDown= new TextureRegion(
					new Texture(Gdx.files.internal("EndGame/resume.png")));
			imbtnsty31.regionUp= imbtnsty31.regionDown;
			final ImageButton buttonResume1 = new ImageButton(imbtnsty31);
			buttonResume1.setClickListener(new ClickListener() {
				@Override
				public void click(Actor actor, float x, float y) {
					//Gdx.input.setInputProcessor(null);
					status=GameStatus.Playing;
					constructStage(GameStatus.Playing);
					
				}
			});	
			tableEnd.clear();// = new Table();
			
			tableLL.clear();// = new Table();
		    tableLL.left().bottom();
			tableLL.add(buttonBack1);//.pad(10, 10, 10, 10);
			
		    tableC.clear();// = new Table();
		    tableC.reset();
		    
			tableC.add(buttonRestart1).pad(0, 0, 0, 20);//.width(400).right();
			tableC.add(buttonResume1).pad(0, 20, 0, 0);//.width(400).left();
			
			//stage.addActor(table);
		}
	}

	private void spawnRaindrop() 
	{
		//randomize position
		Rectangle prect = new Rectangle();
		prect.x = MathUtils.random(0, 800-48);
		prect.y = 480;
		prect.width = 48;
		prect.height = 48;
		
		int randType=MathUtils.random(0, 6);
		PackageTypes.get(randType).dropped++;
		Packages.add(new Package(PackageTypes.get(randType),prect));
		lastDropTime = time;
   }
	
	private void randomTargetPackage() 
	{
		int randType=MathUtils.random(0, 2);
		lastRandomization = time;
		targetPkg=PackageTypes.get(randType);
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
