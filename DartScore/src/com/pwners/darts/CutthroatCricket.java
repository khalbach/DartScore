package com.pwners.darts;

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

public class CutthroatCricket implements IDartsGame {
	
	private final int NUM_CRICKET_MARKS = 7;
    
	/* number of players */
    private int m_numPlayers;
    
    /* names of the players */
    private String[] m_playersNames;
    
    /* array representing current status of board */
    private int[] m_currMarks;
    
    /* this activity */
    private Activity m_activity;
    
    /* images to rotate between */
    private final int[] marksImg = {
    		R.drawable.no_marks,
    		R.drawable.one_mark,
    		R.drawable.two_marks,
    		R.drawable.three_marks,
    		};
    
    /**
     * Set data member variables and create board
     * 
     * @param activity current GameActivity
     * @param playersNames names of the players
     */
    public CutthroatCricket(Activity activity, String[] playersNames) {
    	m_numPlayers = playersNames.length;
    	m_playersNames = playersNames;
    	m_currMarks = new int[NUM_CRICKET_MARKS*m_numPlayers];
    	m_activity = activity;
    	createBoard();
    }

	public void createBoard() {

		// Update the current view
		m_activity.setContentView(R.layout.cutthroat_cricket);
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
        for (int i = 1; i <= m_numPlayers; i++)
        {
            // Create a TextView for each of the players names
            TextView playerNameCell = new TextView(c);
            playerNameCell.setGravity(Gravity.CENTER_HORIZONTAL);
            playerNameCell.setWidth(CELL_WIDTH);
            playerNameCell.setText(m_playersNames[i-1]);
            
            // Add the TextView to TableRow
            namesTableRow.addView(playerNameCell);
        }
        

        // Add the TableRow to the TableLayout
        sb.addView(namesTableRow);
        
        final String[] MARKS = {"20","19","18","17","16","15","B"};
        
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

	public void processDart(Player player, int dartScore) {
		// TODO Auto-generated method stub
		
	}

	public void updateScore(Player player, int points) {
		// TODO Auto-generated method stub
		
	}

	public boolean isGameOver(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getGameName() {
		// TODO Auto-generated method stub
		return null;
	}

}
