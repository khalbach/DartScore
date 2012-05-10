package com.pwners.darts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Creates specified game and control high level game
 * processing logic.
 * 
 * @author halbachk
 *
 */
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
        		m_game = new CutthroatCricket(this, playerNames);
        		break;
        		//TODO: add more games
        }
        
        // create board for specified game
        m_game.createBoard();
    }

    /**
     * 
     * @return game name
     */
	public String get_gameName() {
		return m_game.getGameName();
	}

	public static final String GAME_NAME_KEY = "1";
	public static final String PLAYER_NAMES_KEY = "2";
	
	private IDartsGame m_game;
    
	
	
    
       
}