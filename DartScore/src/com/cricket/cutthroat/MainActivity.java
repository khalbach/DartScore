package com.cricket.cutthroat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Number of Players dropdown configuration
        Spinner spinner = (Spinner) findViewById(R.id.numPlayers);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.numPlayers_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new MyDropdownListener());
        
        Button startBtn = (Button)findViewById(R.id.startGame);
        startBtn.setOnClickListener(myhandler);
        
    }
    
    View.OnClickListener myhandler = new View.OnClickListener() {
        public void onClick(View v) {
        	
        	TableLayout playerNamesLayout = (TableLayout)findViewById(R.id.playerNames);
        	
        	int numPlayers = playerNamesLayout.getChildCount();
        	
        	String[] playerNames = new String[numPlayers];
        	
        	for(int i = 0; i < numPlayers; i++){
        		playerNames[i] = ((TextView)playerNamesLayout.getChildAt(i)).getText().toString();
        	}
        	
        	// Start game activity
        	Intent myGame = new Intent(getApplicationContext(), GameActivity.class);
        	myGame.putExtra(GameActivity.PLAYER_NAMES_KEY, playerNames);
        	myGame.putExtra(GameActivity.NUM_PLAYERS_KEY, numPlayers);
        	// TODO: add game type once we make this into a boss app
            startActivity(myGame);

        }
    };
    
    public class MyDropdownListener implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
            View view, int pos, long id) {
        	
        	int numPlayers = Integer.parseInt(parent.getItemAtPosition(pos).toString());
        	
        	TableLayout playerNamesLayout = (TableLayout)findViewById(R.id.playerNames);
        	final TextView[] playerNames = new TextView[numPlayers];
        	playerNamesLayout.removeAllViews();
        	
        	// Generate list for player Names
        	for (int i = 0; i < numPlayers; i++) {
        	    
        	    final EditText rowTextView = new EditText(parent.getContext());
        	    rowTextView.setText("Player " + (i+1));
        	    playerNamesLayout.addView(rowTextView);
        	    playerNames[i] = rowTextView;
        	    
        	}
        }

        public void onNothingSelected(AdapterView parent) {
          // Do nothing.
        }
    }       
}