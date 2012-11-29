package com.me.MasterOfRecycling;


import java.util.Iterator;

import sun.rmi.runtime.Log;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.sun.org.apache.regexp.internal.RECompiler;


public class MasterOfRecycling implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Sprite sprite;
		
	Texture pBonusIm;
	Texture pEcoIm;
	Texture pGlassIm;
	Texture pLifeIm;
	Texture pPlastIm;
	Texture pRockIm;
	Texture pWrongIm;

	Texture TruckIm;
	Texture BackgroundIm;
	
	Vector3 touchPos;
	Rectangle TruckRect;
	Rectangle TruckRefPos;
	
	boolean accelExists;
	
	class PackType
	{
		String PackName;
		Texture PackTexture;
		
		PackType(String Name, Texture Image)
		{
			PackName=Name;
			PackTexture=Image;
		}
	}
	Array<PackType> PackageTypes;
	
	class Package
	{
		PackType pType;
		Rectangle pRect;
		
		Package(PackType pt, Rectangle pr)
		{
			pType=pt;
			pRect=pr;
		}
	}
	Array<Package> Packages;
	
	
	long lastDropTime;
	
	@Override
	public void create() {		
		Texture.setEnforcePotImages(false);
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera();
	    camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		
		
		pBonusIm = new Texture(Gdx.files.internal("pbonus.png"));
		pEcoIm   = new Texture(Gdx.files.internal("peco.png"));
		pGlassIm = new Texture(Gdx.files.internal("pglass.png"));
		pLifeIm  = new Texture(Gdx.files.internal("plife.png"));
		pPlastIm = new Texture(Gdx.files.internal("pplastic.png"));
		pRockIm  = new Texture(Gdx.files.internal("prock.png"));
		pWrongIm = new Texture(Gdx.files.internal("pwrong.png"));
		
		PackageTypes=new Array<PackType>();
		PackageTypes.add(new PackType("Bonus", 	pBonusIm));
		PackageTypes.add(new PackType("Eco", 	pEcoIm));
		PackageTypes.add(new PackType("Glass", 	pGlassIm));
		PackageTypes.add(new PackType("Life", 	pLifeIm));
		PackageTypes.add(new PackType("Plastic",pPlastIm));
		PackageTypes.add(new PackType("Rock", 	pRockIm));
		PackageTypes.add(new PackType("Wrong", 	pWrongIm));
		
		
		BackgroundIm = new Texture(Gdx.files.internal("background.png"));
		//BackgroundIm = new Texture(Gdx.files.internal("data/libgdx.png"));
		BackgroundIm.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		
		TruckIm = new Texture(Gdx.files.internal("truck.png"));
		TruckRect = new Rectangle();
		TruckRect.x = 800 / 2 - 48 / 2;
		TruckRect.y = 20;
		TruckRect.width = TruckIm.getWidth();
		TruckRect.height = TruckIm.getHeight();
		TruckRefPos=new Rectangle();
		
		Packages = new Array<Package>();
		
		accelExists = Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer);
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
		
		Packages.add(new Package(PackageTypes.get(randType),prect));
		lastDropTime = TimeUtils.nanoTime();
   }

	@Override
	public void dispose() {
		batch.dispose();
		//texture.dispose();
	}

	@Override
	public void render() {		
		
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
	    camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(BackgroundIm, 0, 0,800,480);
		
		batch.draw(TruckIm, TruckRect.x, TruckRect.y);
		
		for(Package pack: Packages) 
		{
		    batch.draw(pack.pType.PackTexture, pack.pRect.x, pack.pRect.y);
		}
		
		batch.end();
		
		
		// accel
		float accelX = Gdx.input.getAccelerometerX();
		float accelY = Gdx.input.getAccelerometerY();
		float accelZ = Gdx.input.getAccelerometerZ();	
		Gdx.app.log("acc", accelX + " | " + accelY + " | " + accelZ);	
		float Ka=2f;
		
		TruckRect.x+=Ka*accelY;
		
		if (TruckRect.x<0) TruckRect.x=0;
		if (TruckRect.x>800-TruckRect.width) TruckRect.x=800-TruckRect.width;
		
		 
		if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();
		
		Iterator<Package> iter = Packages.iterator();
	    while(iter.hasNext()) 
	    {
	    	Package p = iter.next();
	        p.pRect.y -= 200 * Gdx.graphics.getDeltaTime();
	        if(p.pRect.y + 48 < 0) 
	    	   iter.remove();
	        if(p.pRect.overlaps(TruckRect)) {
	           //dropSound.play();
	           iter.remove();
	        }
	    }
	}

	private void menu()
	{
		
	}
	
	private void play()
	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
