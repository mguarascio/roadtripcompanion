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
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.*;

public class MainScreen extends Activity
{
	ImageButton mCreateTrip;
	ImageButton mListTrips;
	ImageButton mTweet;
	ImageButton mTakePhoto;
	//ImageButton mAttributes;
	TextView mText;
	
	//String strUserName = "MJurkoic";
	//String strPassword = "kielbasa";
		
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
		
		mCreateTrip = (ImageButton) findViewById(R.id.createTripButton);
		mListTrips = (ImageButton) findViewById(R.id.listTripsButton);
		mTweet = (ImageButton) findViewById(R.id.tweetButton);
		mTakePhoto = (ImageButton) findViewById(R.id.takePhotoButton);
		mText = (TextView)findViewById(R.id.TextView01);
		
		// Add a click listener for Create Trip button
		mCreateTrip.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				Intent createTripIntent = new Intent(getApplicationContext(), CreateTrip.class);
				startActivity(createTripIntent);
			}
		});
		
		// Add a click listener for List Trips button
		mListTrips.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				Intent viewTripIntent = new Intent(getApplicationContext(), ListTrips.class);
				startActivity(viewTripIntent);
			}
		});
		
		// Add a click listener for the Tweet button
		mTweet.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				// Create an intent to open the TwitterClient activity
				//Intent tweetIntent = new Intent(getApplicationContext(), TweetLogin.class);
				//Intent tweetIntent = new Intent(getApplicationContext(), TwitterClient.class);
				Intent tweetIntent = new Intent(getApplicationContext(), TWC.class);
				startActivity(tweetIntent);
			}
		});
		
		// Add a click listener for Take Photo button
		mTakePhoto.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//showDialog(2);
				mText.setText("Take Photo");
			}
		});
		

	}
}

