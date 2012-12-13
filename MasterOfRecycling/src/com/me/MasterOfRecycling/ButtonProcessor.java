package com.me.MasterOfRecycling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.me.MasterOfRecycling.MasterOfRecycling.ScreenType;

public class ButtonProcessor implements InputProcessor {

	private float dispWidth;
	private float dispHeigth;
	
	private MasterOfRecycling game;
	private ScreenType theScreen;
	private ScreenType back2Screen;
	private ScreenType menu2Screen;
	
	
	public ButtonProcessor(MasterOfRecycling GameRoot){
		this.game=GameRoot;	
		dispWidth=(float)Gdx.graphics.getWidth();
		dispHeigth=(float)Gdx.graphics.getHeight();
		Gdx.app.log("menu", "size "+dispWidth+" x "+dispHeigth);
	}
	
	public void SetCurrentScreen(ScreenType screen){
		this.theScreen=screen;
	}
	
	public void SetBack2Screen(ScreenType screen){
		this.back2Screen=screen;
	}
	
	public void SetMenu2Screen(ScreenType screen){
		this.back2Screen=screen;
	}
	
	
	
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.BACK || keycode==Keys.ESCAPE){
			Gdx.app.log("menu", "back pressed ");
		    switch (theScreen)
		    {
		    case Play:
		    	Gdx.app.log("menu", "back pressed in play");
		    	game.ChangeScreen(ScreenType.Menu);
		    	break;
			case About:
				Gdx.app.log("menu", "back pressed in about");
		    	game.ChangeScreen(ScreenType.Menu);
		    	break;
			case Settings:
				Gdx.app.log("menu", "back pressed in Settings");
		    	game.ChangeScreen(ScreenType.Menu);
		    	break;
			case Menu:
				Gdx.app.exit();
		    }
		    
	    }
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub	
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
