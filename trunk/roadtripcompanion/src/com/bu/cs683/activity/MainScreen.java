package com.bu.cs683.activity;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.bu.cs683.persistence.RoadTripCompanion;
import com.bu.cs683.persistence.RoadTripCompanion.Trip;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.DatePicker;
import android.widget.TextView;

public class MainScreen extends Activity
{
	ImageButton mTweet;
	TextView mText;
	
	static final int DATE_DIALOG_ID = 0;
	private static final String TAG = "MainScreen";
	
	/** Called when the activity is first created. */
	//savedInstanceState
	@Override
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		Log.v(TAG, "#### onCreate()");
		setContentView(R.layout.mainscreen);
		
		
		mTweet = (ImageButton) findViewById(R.id.tweetButton);
		mText = (TextView)findViewById(R.id.TextView01);
		
		mTweet.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//showDialog(2);
				mText.setText("SAUSAGE");
			}
		});
	}
}

