package com.pwners.darts;

import java.util.ArrayList;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Creates specified game and control high level game processing logic.
 * 
 * @author halbachk
 * 
 */
public class GameActivity extends Activity {

    static final int DIALOG_ENTER_SCORE = 0;
    private int scoreboardId = 0;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	Intent intent = getIntent();
	int gameType = intent.getIntExtra(GAME_NAME_KEY, 0);
	m_players = intent.getParcelableArrayListExtra(PLAYERS_KEY);

	// Set current player
	m_currPlayer = m_players.get(0);

	// Create specified gameboard
	switch (gameType) {
	case MainActivity.CUTTHROAT_CRICKET_KEY:
	    m_game = new CutthroatCricket(this, m_players);
	    scoreboardId = R.layout.cutthroat_cricket;
	    break;
	    // TODO: add more games
	}

	setContentView(scoreboardId);
	Button enterScoreBtn = (Button) findViewById(R.id.enterScore);
	enterScoreBtn.setOnClickListener(enterScoreButtonHandler);
	m_game.createBoard(scoreboardId);

    }

    // Handler for the start game button
    View.OnClickListener enterScoreButtonHandler = new View.OnClickListener() {
	public void onClick(View v) {
	    // DialogFragment.show() will take care of adding the fragment
	    // in a transaction. We also want to remove any currently showing
	    // dialog, so make our own transaction and take care of that here.
	    FragmentTransaction ft = getFragmentManager().beginTransaction();
	    Fragment prev = getFragmentManager().findFragmentByTag("dialog");
	    if (prev != null) {
		ft.remove(prev);
	    }
	    ft.addToBackStack(null);

	    // Create and show the dialog.
	    DialogFragment newFragment = EnterScoreDialog.newInstance(0);
	    newFragment.show(ft, "dialog");
	}
    };

    public static void updateBoard(DartThrow dartThrow) {
	// TODO: cycle through darts or pass full array
	m_game.processDartThrow(m_currPlayer, dartThrow);
    }

    /**
     * 
     * @return game name
     */
    public String get_gameName() {
	return m_game.getGameName();
    }

    public static final String GAME_NAME_KEY = "1";
    public static final String PLAYERS_KEY = "2";

    private static IDartsGame m_game;
    private static Player m_currPlayer;
    private ArrayList<Player> m_players = new ArrayList<Player>();
}