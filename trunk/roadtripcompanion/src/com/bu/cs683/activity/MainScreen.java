package com.bu.cs683.activity;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.bu.cs683.persistence.RoadTripCompanion;
import com.bu.cs683.persistence.RoadTripCompanion.Trip;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.DatePicker;
import android.widget.TextView;
import org.xmlpull.v1.*;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MainScreen extends Activity
{
	ImageButton mCreateTrip;
	ImageButton mListTrips;
	ImageButton mTweet;
	ImageButton mTakePhoto;
	ImageButton mSettings;
	TextView mText;
	
	static final int DATE_DIALOG_ID = 0;
	private static final String TAG = "MainScreen";
	private SharedPreferences app_preferences;
	
	// Public strings holding values used over multiple activities
	public String TwitterUserName = "";
	public String TwitterPassword = "";
	
	/** Called when the activity is first created. */
	//savedInstanceState
	@Override
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		Log.v(TAG, "#### onCreate()");
		setContentView(R.layout.mainscreen);
		
		mCreateTrip = (ImageButton) findViewById(R.id.createTripButton);
		mListTrips = (ImageButton) findViewById(R.id.listTripsButton);
		mTweet = (ImageButton) findViewById(R.id.tweetButton);
		mTakePhoto = (ImageButton) findViewById(R.id.takePhotoButton);
		mSettings = (ImageButton) findViewById(R.id.handleSettingsButton);
		mText = (TextView)findViewById(R.id.TextView01);
		
		// Sets up application preferences
		app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
		// Default Twitter UserName and Password preferences to the user's
		SharedPreferences.Editor editor = app_preferences.edit();
		editor.putString("twitter_username", "MJurkoic");
		editor.putString("twitter_password", "kielbasa");
		editor.commit();
				
		// Add a click listener for Create Trip button
		mCreateTrip.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				// Create an intent to open the Create Trip activity
				Intent createTripIntent = new Intent(getApplicationContext(), CreateTrip.class);
				startActivity(createTripIntent);
			}
		});
		
		// Add a click listener for List Trips button
		mListTrips.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				// Create an intent to open the View Trips activity
				Intent viewTripIntent = new Intent(getApplicationContext(), ListTrips.class);
				startActivity(viewTripIntent);
			}
		});
		
		// Add a click listener for the Tweet button
		mTweet.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				// Create an intent to open the TWC activity
				Intent tweetIntent = new Intent(getApplicationContext(), TWC.class);
				startActivity(tweetIntent);
			}
		});
		
		// Add a click listener for Take Photo button
		mTakePhoto.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				// Create an intent to open the Take Photo activity
				Intent takePhotoIntent = new Intent(getApplicationContext(), TakePhoto.class);
				startActivity(takePhotoIntent);
			}
		});
		
		// Add a click listener for Take Photo button
		mSettings.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				// Create an intent to open the Settings activity
				Intent settingsIntent = new Intent(getApplicationContext(), Settings.class);
				startActivity(settingsIntent);
			}
		});
		

	}
}

