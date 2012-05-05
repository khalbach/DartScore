package com.cricket.cutthroat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class GameActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cricket);
        
        Intent i = getIntent();
        m_numPlayers = i.getIntExtra(NUM_PLAYERS_KEY,0);
        m_playerNames = i.getStringArrayExtra(PLAYER_NAMES_KEY);
        
        m_currMarks = new int[NUM_CRICKET_MARKS*m_numPlayers];
        this.createBoard((int)m_numPlayers);
        
    }

	private void createBoard(int numPlayers) {
		TableLayout sb = (TableLayout)findViewById(R.id.scoreboard);
		
		android.view.Display display = ((android.view.WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		int cellWidth = display.getWidth()/(numPlayers+1);
		int cellHeight = display.getHeight()/(NUM_CRICKET_BOARD_ROWS);
        // Create a TableRow and give it an ID
        TableRow trNames = new TableRow(getApplicationContext());
        
        // place holder
        TextView placeholder = new TextView(getApplicationContext());
        placeholder.setWidth(cellWidth);
        trNames.addView(placeholder);
        
		// Set each player's name on board
        for (int i = 1; i <= numPlayers; i++)
        {
            // Create a TextView to house the name of the province
            TextView playerName = new TextView(getApplicationContext());
            playerName.setGravity(Gravity.CENTER_HORIZONTAL);
            playerName.setWidth(cellWidth);
            playerName.setText(m_playerNames[i-1]);
            trNames.addView(playerName);
        }
        

        // Add the TableRow to the TableLayout
        sb.addView(trNames);
        
        String[] marks = {"20","19","18","17","16","15","B"};
        
        for(int j = 0; j < marks.length; j++){
        	TableRow trMark = new TableRow(getApplicationContext());
        	TextView markNum = new TextView(getApplicationContext());
        	markNum.setGravity(Gravity.CENTER_VERTICAL);
        	markNum.setClickable(true);
            markNum.setWidth(100);
            markNum.setHeight(100);
        	markNum.setText(marks[j]);
            trMark.addView(markNum);
            
         // Set each player's name on board
            for (int i = 0; i < numPlayers; i++)
            {
                // Create a TextView to house the name of the province
                ImageView markCell = new ImageView(getApplicationContext());
                markCell.setClickable(true);
                Bitmap bmp=BitmapFactory.decodeResource(getResources(), R.drawable.no_marks);
                int width=100;
                int height=100;
            	markCell.setId((numPlayers*j)+i);
                Bitmap resizedbitmap=Bitmap.createScaledBitmap(bmp, width, height, true);
                markCell.setImageBitmap(resizedbitmap);
                
                markCell.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                    	int vid = v.getId();
                    	Bitmap bmp=BitmapFactory.decodeResource(getResources(), marksImg[++m_currMarks[vid] % 4]);
                        int width=100;
                        int height=100;
                        Bitmap resizedbitmap=Bitmap.createScaledBitmap(bmp, width, height, true);
                        ((ImageView)v).setImageBitmap(resizedbitmap);
                    }
                });
                
                trMark.addView(markCell);
            }
            
            sb.addView(trMark);
        }
	}
    

	public static final String NUM_PLAYERS_KEY = "1";
	public static final String PLAYER_NAMES_KEY = "2";
	
	/*
	 * PRIVATE - DATA MEMEBERS
	 */	
    
	private String[] m_playerNames;
	private int m_numPlayers;
    private int[] m_currMarks;
	
    private final int NUM_CRICKET_MARKS = 7;
    private final int NUM_CRICKET_BOARD_ROWS = 9;
    
    private final int[] marksImg = {
    		R.drawable.no_marks,
    		R.drawable.one_mark,
    		R.drawable.two_marks,
    		R.drawable.three_marks,
    		};
       
}