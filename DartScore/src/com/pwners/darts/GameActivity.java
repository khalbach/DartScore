package com.pwners.darts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class GameActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Intent i = getIntent();
        int gameType = i.getIntExtra(GAME_NAME_KEY,0);
        String[] playerNames = i.getStringArrayExtra(PLAYER_NAMES_KEY);        
       
        switch (gameType){
        	case MainActivity.CUTTHROAT_CRICKET_KEY: 
        		set_gameName(new CutthroatCricket(this, playerNames));
        		break;
        }
        
    }

	public IDartsGame get_gameName() {
		return m_game;
	}

	public void set_gameName(IDartsGame game) {
		m_game = game;
	}

	public static final String GAME_NAME_KEY = "1";
	public static final String PLAYER_NAMES_KEY = "2";
	
	private IDartsGame m_game;
    
	
	
    
       
}