package com.me.MasterOfRecycling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.me.MasterOfRecycling.MasterOfRecycling.ScreenType;

public class ButtonProcessor implements InputProcessor {

	private MasterOfRecycling game;
	private ScreenType back2Screen;
	private ScreenType menu2Screen;
	
	
	public ButtonProcessor(MasterOfRecycling GameRoot){
		this.game=GameRoot;	
		float dispWidth = (float)Gdx.graphics.getWidth();
		float dispHeigth = (float)Gdx.graphics.getHeight();
	}
		
	public void SetBack2Screen(ScreenType screen){
		this.back2Screen=screen;
	}
	
	public void SetMenu2Screen(ScreenType screen){
		this.menu2Screen=screen;
	}
	
	
	
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.BACK || keycode==Keys.ESCAPE){
						
			if (back2Screen==ScreenType.Exit)
				Gdx.app.exit();
			else if (back2Screen!=ScreenType.None)
				game.ChangeScreen(back2Screen);
			
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
