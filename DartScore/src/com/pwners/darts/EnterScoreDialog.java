package com.pwners.darts;

import java.util.ArrayList;
import java.util.Stack;

import com.pwners.darts.DartThrow.Multiplier;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

public class EnterScoreDialog extends DialogFragment{

    private static Activity m_activity;
    private TableRow summary;
    ArrayList<Integer> dartThrows = new ArrayList<Integer>();

    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    static EnterScoreDialog newInstance(int num) {
    	EnterScoreDialog f = new EnterScoreDialog();

    	Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int style = DialogFragment.STYLE_NO_TITLE;
        int theme = android.R.style.Theme_Holo_Light_Dialog;

        setStyle(style, theme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog, container, false);
        
        Button button = (Button)v.findViewById(R.id.twentyx1);
        summary = (TableRow)v.findViewById(R.id.summaryLine);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	Button clicked = (Button) v.findViewById(v.getId());
                // When button is clicked, call up to owning activity.
            	TextView tv = new TextView(v.getContext());
            	tv.setText(clicked.getText());
            	summary.addView(tv);
            	dartThrows.add((Integer)clicked.getId());
            }
        });
        
        Button enterBtn = (Button)v.findViewById(R.id.updateScore);
        enterBtn.setOnClickListener(new OnClickListener() {
        	
            public void onClick(View v) {
            	GameActivity.updateBoard(new DartThrow(20, Multiplier.DOUBLE));
            	dismiss();
            }
        });
        
        return v;
    }
}