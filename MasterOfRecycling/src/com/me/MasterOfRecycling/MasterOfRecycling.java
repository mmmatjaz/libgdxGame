package com.me.MasterOfRecycling;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;

public class MasterOfRecycling extends Game {
	public enum ScreenType {
		Menu, GameType, Play, About, Settings,Pause, Exit,None,GameOver
	}
	
	public MasterMenu MenuScreen;
	
	public MasterPlay PlayScreen;
	public MasterAbout AboutScreen;
	public MasterSettings SettingsScreen;
	public MasterEndGame GameOverScreen;
	private ButtonProcessor ButtonProc;
	
	public int scoreLast;
	public int scoreBest;
	
	public boolean soundOn;
	public float accSens;
	
	
	public Preferences prefs;

	public float sensitivity;

	public boolean mute;

	private InputMultiplexer inputMultiplexer;
		
	@Override
	public void create() {
		// general setup
		Texture.setEnforcePotImages(false);
		prefs = Gdx.app.getPreferences( "profile" );
		Gdx.input.setCatchBackKey(true);
		ReadPrefs();
		// input event handling
		ButtonProc= new ButtonProcessor(this);
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(ButtonProc);
		// construct screens
		MenuScreen=new MasterMenu(this);
		ChangeScreen(ScreenType.Menu);
		
		PlayScreen=new MasterPlay(this);
		AboutScreen=new MasterAbout(this);
		SettingsScreen=new MasterSettings(this);
		GameOverScreen=new MasterEndGame(this);
		
		Gdx.input.setInputProcessor(inputMultiplexer);
		
	}

	
	
	public void ChangeScreen(ScreenType Scr)
	{
		
		inputMultiplexer.clear();
		inputMultiplexer.addProcessor(ButtonProc);
		switch (Scr)
		{
		case Menu:
			this.setScreen(MenuScreen);
			inputMultiplexer.addProcessor(MenuScreen.stage);
			ButtonProc.SetCurrentScreen(ScreenType.Menu);
			ButtonProc.SetBack2Screen(ScreenType.Exit);
			ButtonProc.SetMenu2Screen(ScreenType.None);
				
			break;
		case GameType:
			this.setScreen(PlayScreen);
			break;
		case Play:
			this.setScreen(PlayScreen);
			inputMultiplexer.addProcessor(PlayScreen.stage);
			ButtonProc.SetCurrentScreen(ScreenType.Play);
			ButtonProc.SetBack2Screen(ScreenType.Menu);
			ButtonProc.SetMenu2Screen(ScreenType.None);
			PlayScreen.restartGame();
			break;
		case About:
			this.setScreen(AboutScreen);
			ButtonProc.SetCurrentScreen(ScreenType.About);
			ButtonProc.SetBack2Screen(ScreenType.Menu);
			ButtonProc.SetMenu2Screen(ScreenType.None);		
			break;
		case Settings:
			this.setScreen(SettingsScreen);
			inputMultiplexer.addProcessor(SettingsScreen.stage);
			ButtonProc.SetCurrentScreen(ScreenType.Settings);
			ButtonProc.SetBack2Screen(ScreenType.Menu);
			ButtonProc.SetMenu2Screen(ScreenType.None);
			break;
		case GameOver:
			this.setScreen(GameOverScreen);
			inputMultiplexer.addProcessor(GameOverScreen.stage);
			ButtonProc.SetCurrentScreen(ScreenType.GameOver);
			ButtonProc.SetBack2Screen(ScreenType.Menu);
			ButtonProc.SetMenu2Screen(ScreenType.None);
			break;
		case Pause:
			this.setScreen(PlayScreen);
			break;
		}
	}
	

	private void ReadPrefs() {
		if (prefs.contains("score"))
			scoreBest=prefs.getInteger("score",-1);
		else scoreBest=0;
		
		if (prefs.contains("soundOn"))
			soundOn=prefs.getBoolean("soundOn");
		else soundOn=true;
		
		if (prefs.contains("sensitivity"))
			sensitivity=prefs.getFloat("sensitivity");
		else sensitivity=3f;
		Gdx.app.log("sound", "loaded "+soundOn);
		prefs.flush();
	}
	
	public void writeSoundPref(){
		prefs.putBoolean("soundOn", soundOn);
		prefs.flush();
		Gdx.app.log("sound", "written "+soundOn);
	}
	public boolean updateScore(int pts)
	{
		scoreLast=pts;
		boolean better=scoreLast>scoreBest;
		if (better)
		{
			scoreBest=scoreLast;
			prefs.putInteger("score", scoreBest);
			prefs.flush();
		}
		return better;
	}
	public void updateSens()
	{
		prefs.putFloat("sensitivity", sensitivity);
		prefs.flush();
		Gdx.app.log("sensitivity", "written "+sensitivity);
	}
	@Override
	public void dispose() {
		//batch.dispose();
		//texture.dispose();
	}
	
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
