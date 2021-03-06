package com.pwners.darts;

import java.util.ArrayList;
import java.util.Stack;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Cutthroat Cricket Darts Game
 * @author halbachk
 *
 */
public class CutthroatCricket implements IDartsGame {

    /* Number of cricket marks */
    private final int NUM_CRICKET_MARKS = 7;

    /* number of players */
    private final int m_numPlayers;

    /* the players */
    private final ArrayList<Player> m_players;

    /* array representing current status of board */
    private final int[] m_currMarks;

    /* this activity */
    private final Activity m_activity;

    /* Array of all dart throws */
    Stack<DartThrow> allDartThrows = new Stack<DartThrow>();

    /* images to rotate between */
    private final int[] marksImg = {
	    R.drawable.no_marks,
	    R.drawable.one_mark,
	    R.drawable.two_marks,
	    R.drawable.three_marks,
    };

    private final String[] MARKS = {"20","19","18","17","16","15","B"};

    /**
     * Set data member variables and create board
     * 
     * @param activity current GameActivity
     * @param playersNames names of the players
     */
    public CutthroatCricket(Activity activity, ArrayList<Player> players) {
	m_numPlayers = players.size();
	m_players = players;
	m_currMarks = new int[NUM_CRICKET_MARKS * m_numPlayers];
	m_activity = activity;
    }

    /*
     * (non-Javadoc)
     * @see com.pwners.darts.IDartsGame#createBoard()
     */
    public void createBoard(int scoreboard) {

	// Update the current view
	Context c = m_activity.getApplicationContext();
	final Resources res = m_activity.getResources();

	TableLayout sb = (TableLayout)m_activity.findViewById(R.id.scoreboard);

	final int CELL_WIDTH = 100;
	final int CELL_HEIGHT = 100;
	// Create a TableRow for the player names
	TableRow namesTableRow = new TableRow(c);

	// place holder for top left corner of the board
	TextView placeholder = new TextView(c);
	placeholder.setWidth(CELL_WIDTH);
	namesTableRow.addView(placeholder);

	// Set each player's name on board
	for (Player player : m_players)
	{
	    // Create a TextView for each of the players names
	    TextView playerNameCell = new TextView(c);
	    playerNameCell.setGravity(Gravity.CENTER_HORIZONTAL);
	    playerNameCell.setWidth(CELL_WIDTH);
	    playerNameCell.setText(player.getName());

	    // Add the TextView to TableRow
	    namesTableRow.addView(playerNameCell);
	}


	// Add the TableRow to the TableLayout
	sb.addView(namesTableRow);

	// Add each row of marks to board
	for(int j = 0; j < MARKS.length; j++){

	    // Create table row for current mark
	    TableRow marksTableRow = new TableRow(c);

	    // Create TextView for mark
	    TextView markNumCell = new TextView(c);
	    markNumCell.setGravity(Gravity.CENTER_VERTICAL);
	    markNumCell.setWidth(CELL_WIDTH);
	    markNumCell.setHeight(CELL_HEIGHT);
	    markNumCell.setText(MARKS[j]);

	    // Add TextView to TableRow
	    marksTableRow.addView(markNumCell);

	    // Create blank cells to represent each players' marks
	    for (int i = 0; i < m_numPlayers; i++)
	    {
		// Create a TextView and initialize it to "NO" marks image
		ImageView markCell = new ImageView(c);
		markCell.setClickable(true);
		Bitmap bmp=BitmapFactory.decodeResource(res, R.drawable.no_marks);
		Bitmap resizedbitmap=Bitmap.createScaledBitmap(bmp, CELL_WIDTH, CELL_HEIGHT, true);
		markCell.setImageBitmap(resizedbitmap);

		// id is used as index into MARKS array
		markCell.setId((m_numPlayers*j)+i);

		markCell.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
			int id = v.getId();
			Bitmap bmp=BitmapFactory.decodeResource(res, marksImg[++m_currMarks[id] % 4]);
			Bitmap resizedbitmap=Bitmap.createScaledBitmap(bmp, CELL_WIDTH, CELL_HEIGHT, true);
			((ImageView)v).setImageBitmap(resizedbitmap);
		    }
		});

		marksTableRow.addView(markCell);
	    }

	    sb.addView(marksTableRow);
	}

    }

    /*
     * (non-Javadoc)
     * @see com.pwners.darts.IDartsGame#processDart(com.pwners.darts.Player, com.pwners.darts.DartThrow)
     */
    public void processDartThrow(Player player, DartThrow dartThrow) {
	final Resources res = m_activity.getResources();
	int id = 1;
	Bitmap bmp=BitmapFactory.decodeResource(res, marksImg[++m_currMarks[id] % 4]);
	Bitmap resizedbitmap=Bitmap.createScaledBitmap(bmp, 100, 100, true);
	ImageView v = (ImageView)m_activity.findViewById(1);
	v.setImageBitmap(resizedbitmap);
	allDartThrows.push(dartThrow);
    }

    /*
     * (non-Javadoc)
     * @see com.pwners.darts.IDartsGame#undoDartThrow()
     */
    public void undoDartThrow() {

	if(allDartThrows.size() == 0) {
	    return;
	}

	DartThrow lastThrow = allDartThrows.pop();
    }

    /*
     * (non-Javadoc)
     * @see com.pwners.darts.IDartsGame#isGameOver(com.pwners.darts.Player)
     */
    public boolean isGameOver(Player player) {
	// TODO Auto-generated method stub
	return false;
    }

    /*
     * (non-Javadoc)
     * @see com.pwners.darts.IDartsGame#getGameName()
     */
    public String getGameName() {
	// TODO Auto-generated method stub
	return "Cutthroat Cricket";
    }

}
