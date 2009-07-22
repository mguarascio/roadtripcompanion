package com.bu.cs683.activity;

import android.os.Bundle;
import android.util.Log;
import java.util.GregorianCalendar;

import com.bu.cs683.persistence.RoadTripCompanion;
import com.bu.cs683.persistence.RoadTripCompanion.Trip;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.webkit.WebView;
import android.widget.EditText;
//import org.apache.commons.codec.binary.Base64;
//import android.net.http.RequestQueue;
import android.net.*;

public class TweetLogin extends Activity 
{
	private ImageButton mCancelButton;
	private ImageButton mLoginButton;
		
	private static final String TAG = "TweetLogin";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Log.v(TAG, "#### onCreate()");
		setContentView(R.layout.login);
		
		mLoginButton = (ImageButton) findViewById(R.id.loginbtn);
		mCancelButton = (ImageButton) findViewById(R.id.cancel);
		
		Log.d("ABC", "Is null = " + (null == mLoginButton));
		
		// Add a click listener for the Twitter Login button
		mLoginButton.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v)
			{
				// Create an intent to open the Tweet activity
				//Intent tweetIntent = new Intent(getApplicationContext(), TweetLogin.class);
				//startActivity(tweetIntent);
				//mCancelButton.setText("Cancel Clicked");
			}
		});
	}
}
