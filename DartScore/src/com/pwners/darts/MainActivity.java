package com.pwners.darts;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.FilterQueryProvider;
import android.widget.Filterable;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    /**
     * Fields to contain the current position and display contents of the spinner
     */
    protected int mPos;
    protected String mSelection;

    /**
     * ArrayAdapter connects the spinner widget to array-based data.
     */
    protected ArrayAdapter<CharSequence> mAdapter;

    /**
     * ContactListAdapter connects the AutoCompleteTextView to the contact data
     */
    protected ContactListAdapter cAdapter;

    /**
     *  The initial position of the spinner when it is first installed.
     */
    public static final int DEFAULT_POSITION = 1;

    /**
     * The name of a properties file that stores the position and
     * selection when the activity is not loaded.
     */
    public static final String PREFERENCES_FILE = "PlayerCountSpinnerPrefs";

    /**
     * These values are used to read and write the properties file.
     * PROPERTY_DELIMITER delimits the key and value in a Java properties file.
     * The "marker" strings are used to write the properties into the file
     */
    public static final String PROPERTY_DELIMITER = "=";

    /**
     * The key or label for "position" in the preferences file
     */
    public static final String POSITION_KEY = "Position";

    /**
     * The key or label for "selection" in the preferences file
     */
    public static final String SELECTION_KEY = "Selection";

    public static final String POSITION_MARKER =
	    POSITION_KEY + PROPERTY_DELIMITER;

    public static final String SELECTION_MARKER =
	    SELECTION_KEY + PROPERTY_DELIMITER;

    public static final int CUTTHROAT_CRICKET_KEY = 1;

    /**
     * Initializes the application and the activity.
     * 1) Sets the view
     * 2) Reads the spinner's backing data from the string resources file
     * 3) Instantiates a callback listener for handling selection from the
     *    spinner
     * Notice that this method includes code that can be uncommented to force
     * tests to fail.
     *
     * This method overrides the default onCreate() method for an Activity.
     *
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	/**
	 * derived classes that use onCreate() overrides must always call the super constructor
	 */
	super.onCreate(savedInstanceState);

	setContentView(R.layout.main);

	Spinner spinner = (Spinner) findViewById(R.id.PlayerCountSpinner);

	/*
	 * Create a backing mLocalAdapter for the Spinner from a list of the
	 * player counts. The list is defined by XML in the strings.xml file.
	 */
	mAdapter = ArrayAdapter.createFromResource(this, R.array.PlayerCounts,
		android.R.layout.simple_dropdown_item_1line);

	/*
	 * Attach the mLocalAdapter to the spinner.
	 */
	spinner.setAdapter(mAdapter);

	/*
	 * Create a listener that is triggered when Android detects the
	 * user has selected an item in the Spinner.
	 */
	OnItemSelectedListener spinnerListener = new myOnItemSelectedListener(this,mAdapter);

	/*
	 * Attach the listener to the Spinner.
	 */
	spinner.setOnItemSelectedListener(spinnerListener);

	/*
	 * Set up the ContactListAdapter
	 */
	ContentResolver content = getContentResolver();
	Cursor cursor = content.query(Phone.CONTENT_FILTER_URI,
		PHONE_PROJECTION, null, null, null);

	cAdapter = new ContactListAdapter(this, cursor);

	Button startBtn = (Button)findViewById(R.id.StartGameButton);
	startBtn.setOnClickListener(startGameButtonHandler);
    }

    // Handler for the start game button
    View.OnClickListener startGameButtonHandler = new View.OnClickListener() {
	public void onClick(View v) {
	    TableLayout playerNamesTableLayout = (TableLayout)findViewById(R.id.PlayerNamesTableLayout);

	    int numPlayers = playerNamesTableLayout.getChildCount();

	    ArrayList<Player> players = new ArrayList<Player>();
	    for (int i = 0; i < numPlayers; i++) {
		Player player = (Player) ((TextView) playerNamesTableLayout
			.getChildAt(i)).getTag();
		players.add(player);
	    }

	    // Start game activity
	    Intent myGame = new Intent(getApplicationContext(), GameActivity.class);
	    myGame.putExtra(GameActivity.GAME_NAME_KEY, CUTTHROAT_CRICKET_KEY);
	    myGame.putParcelableArrayListExtra(GameActivity.PLAYERS_KEY, players);
	    // TODO: add game type once we make this into a boss app
	    startActivity(myGame);
	}
    };

    /**
     *  A callback listener that implements the
     *  {@link android.widget.AdapterView.OnItemSelectedListener} interface
     *  For views based on adapters, this interface defines the methods available
     *  when the user selects an item from the View.
     *
     */
    public class myOnItemSelectedListener implements OnItemSelectedListener {
	/*
	 * provide local instances of the mLocalAdapter and the mLocalContext
	 */
	ArrayAdapter<CharSequence> mLocalAdapter;
	Activity mLocalContext;

	/**
	 *  Constructor
	 *  @param c - The activity that displays the Spinner.
	 *  @param ad - The Adapter view that
	 *    controls the Spinner.
	 *  Instantiate a new listener object.
	 */
	public myOnItemSelectedListener(Activity c, ArrayAdapter<CharSequence> ad) {
	    mLocalContext = c;
	    mLocalAdapter = ad;
	}

	/**
	 * When the user selects an item in the spinner, this method is invoked by the callback
	 * chain. Android calls the item selected listener for the spinner, which invokes the
	 * onItemSelected method.
	 *
	 * @see android.widget.AdapterView.OnItemSelectedListener#onItemSelected(
	 *  android.widget.AdapterView, android.view.View, int, long)
	 * @param parent - the AdapterView for this listener
	 * @param v - the View for this listener
	 * @param pos - the 0-based position of the selection in the mLocalAdapter
	 * @param row - the 0-based row number of the selection in the View
	 */
	public void onItemSelected(AdapterView<?> parent, View v, int pos, long row) {
	    int numPlayers = Integer.parseInt(parent.getItemAtPosition(pos).toString());

	    TableLayout playerNamesTableLayout = (TableLayout)findViewById(R.id.PlayerNamesTableLayout);
	    AutoCompleteTextView[] playerNames = new AutoCompleteTextView[numPlayers];
	    playerNamesTableLayout.removeAllViews();

	    // Generate list for player Names
	    for (int i = 0; i < numPlayers; i++) {
		final AutoCompleteTextView rowTextView = new AutoCompleteTextView(
			parent.getContext());
		String playerText = "Player " + (i+1);
		rowTextView.setText(playerText);
		rowTextView.setAdapter(cAdapter);
		rowTextView.setSelectAllOnFocus(true);

		Player player = new Player(playerText, null);
		rowTextView.setTag(player);

		// TODO: there has got to be a better way to do this
		rowTextView.setOnItemClickListener(new OnItemClickListener()
		{
		    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
		    {
			rowTextView.setTag(arg1.getTag());
		    }
		});

		playerNamesTableLayout.addView(rowTextView);
		playerNames[i] = rowTextView;
	    }
	}

	/**
	 * The definition of OnItemSelectedListener requires an override
	 * of onNothingSelected(), even though this implementation does not use it.
	 * @param parent - The View for this Listener
	 */
	public void onNothingSelected(AdapterView<?> parent) {
	    // do nothing
	}
    }

    // XXX compiler bug in javac 1.5.0_07-164, we need to implement Filterable
    // to make compilation work
    public static class ContactListAdapter extends CursorAdapter implements Filterable {
	public ContactListAdapter(Context context, Cursor c) {
	    super(context, c);
	    mContent = context.getContentResolver();
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
	    final LayoutInflater inflater = LayoutInflater.from(context);
	    final TextView view = (TextView) inflater.inflate(
		    android.R.layout.simple_dropdown_item_1line, parent, false);
	    view.setText(cursor.getString(COLUMN_DISPLAY_NAME));

	    // TODO: probably shouldn't keep creating new Players.. but i'm
	    // tired and it works so okay for now
	    Player player = new Player(cursor.getString(COLUMN_DISPLAY_NAME),
		    cursor.getString(COLUMN_NUMBER));
	    view.setTag(player);

	    return view;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
	    ((TextView) view).setText(cursor.getString(COLUMN_DISPLAY_NAME));

	    // TODO: probably shouldn't keep creating new Players.. but i'm
	    // tired and it works so okay for now
	    Player player = new Player(cursor.getString(COLUMN_DISPLAY_NAME),
		    cursor.getString(COLUMN_NUMBER));
	    ((TextView) view).setTag(player);
	}

	@Override
	public String convertToString(Cursor cursor) {
	    return cursor.getString(COLUMN_DISPLAY_NAME);
	}

	@Override
	public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
	    FilterQueryProvider filter = getFilterQueryProvider();
	    if (filter != null) {
		return filter.runQuery(constraint);
	    }

	    Uri uri = Uri.withAppendedPath(
		    Phone.CONTENT_FILTER_URI,
		    Uri.encode(constraint.toString()));
	    return mContent.query(uri, PHONE_PROJECTION, null, null, null);
	}

	private final ContentResolver mContent;
    }

    private static final String[] PHONE_PROJECTION = new String[] {
	Phone._ID,
	Phone.DISPLAY_NAME,
	Phone.NUMBER
    };

    private static final int COLUMN_ID = 0;
    private static final int COLUMN_DISPLAY_NAME = 1;
    private static final int COLUMN_NUMBER = 2;

    /**
     * Restores the current state of the spinner (which item is selected, and the value
     * of that item).
     * Since onResume() is always called when an Activity is starting, even if it is re-displaying
     * after being hidden, it is the best place to restore state.
     *
     * Attempts to read the state from a preferences file. If this read fails,
     * assume it was just installed, so do an initialization. Regardless, change the
     * state of the spinner to be the previous position.
     *
     * @see android.app.Activity#onResume()
     */
    @Override
    public void onResume() {

	/*
	 * an override to onResume() must call the super constructor first.
	 */

	super.onResume();

	/*
	 * Try to read the preferences file. If not found, set the state to the desired initial
	 * values.
	 */

	if (!readInstanceState(this)) {
	    setInitialState();
	}

	/*
	 * Set the spinner to the current state.
	 */

	Spinner restoreSpinner = (Spinner)findViewById(R.id.PlayerCountSpinner);
	restoreSpinner.setSelection(getSpinnerPosition());
    }

    /**
     * Store the current state of the spinner (which item is selected, and the value of that item).
     * Since onPause() is always called when an Activity is about to be hidden, even if it is about
     * to be destroyed, it is the best place to save state.
     *
     * Attempt to write the state to the preferences file. If this fails, notify the user.
     *
     * @see android.app.Activity#onPause()
     */
    @Override
    public void onPause() {

	/*
	 * an override to onPause() must call the super constructor first.
	 */
	super.onPause();

	/*
	 * Save the state to the preferences file. If it fails, display a Toast, noting the failure.
	 */
	if (!writeInstanceState(this)) {
	    Toast.makeText(this,
		    "Failed to write state!", Toast.LENGTH_LONG).show();
	}
    }

    /**
     * Sets the initial state of the spinner when the application is first run.
     */
    public void setInitialState() {
	mPos = DEFAULT_POSITION;
    }

    /**
     * Read the previous state of the spinner from the preferences file
     * @param c - The Activity's Context
     */
    public boolean readInstanceState(Context c) {
	/*
	 * The preferences are stored in a SharedPreferences file. The abstract implementation of
	 * SharedPreferences is a "file" containing a hashmap. All instances of an application
	 * share the same instance of this file, which means that all instances of an application
	 * share the same preference settings.
	 */

	/*
	 * Get the SharedPreferences object for this application
	 */
	SharedPreferences p = c.getSharedPreferences(PREFERENCES_FILE, MODE_WORLD_READABLE);

	/*
	 * Get the position and value of the spinner from the file, or a default value if the
	 * key-value pair does not exist.
	 */
	mPos = p.getInt(POSITION_KEY, MainActivity.DEFAULT_POSITION);
	mSelection = p.getString(SELECTION_KEY, "");

	/*
	 * SharedPreferences doesn't fail if the code tries to get a non-existent key. The
	 * most straightforward way to indicate success is to return the results of a test that
	 * SharedPreferences contained the position key.
	 */
	return (p.contains(POSITION_KEY));
    }

    /**
     * Write the application's current state to a properties repository.
     * @param c - The Activity's Context
     *
     */
    public boolean writeInstanceState(Context c) {
	/*
	 * Get the SharedPreferences object for this application
	 */
	SharedPreferences p =
		c.getSharedPreferences(MainActivity.PREFERENCES_FILE, MODE_WORLD_READABLE);

	/*
	 * Get the editor for this object. The editor interface abstracts the implementation of
	 * updating the SharedPreferences object.
	 */
	SharedPreferences.Editor e = p.edit();

	/*
	 * Write the keys and values to the Editor
	 */
	e.putInt(POSITION_KEY, mPos);
	e.putString(SELECTION_KEY, mSelection);

	/*
	 * Commit the changes. Return the result of the commit. The commit fails if Android
	 * failed to commit the changes to persistent storage.
	 */
	return (e.commit());
    }

    public int getSpinnerPosition() {
	return mPos;
    }

    public void setSpinnerPosition(int pos) {
	mPos = pos;
    }

    public String getSpinnerSelection() {
	return mSelection;
    }

    public void setSpinnerSelection(String selection) {
	mSelection = selection;
    }
}