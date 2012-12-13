package com.me.MasterOfRecycling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class MasterPrefs{

	private boolean soundOn;
	private float sensitivity;
	private int bestScore;
	private Preferences prefs;
	private MasterOfRecycling game;
	
	public MasterPrefs()
	{
		prefs = Gdx.app.getPreferences( "profile" );
	}
	
}
