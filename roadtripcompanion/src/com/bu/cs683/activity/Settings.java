package com.bu.cs683.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.webkit.WebView;

public class Settings extends Activity
{
	private static final String TAG = "Settings";
	
	TextView mUserName;
	TextView mPassword;
	
	Button mSaveSettingsButton;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Log.v(TAG, "#### onCreate()");
		setContentView(R.layout.settings);
		
		mSaveSettingsButton = (Button) findViewById(R.id.saveSettingsButton);
		// This section of code gets the Twitter UserName and Twitter Password from the strings resource
		// A more persistent way, such as SharedPrefs needs to be implemented ultimately
		// Get Twitter UserName and Password from strings
		mUserName = (TextView) findViewById(R.id.twitterUserNameText);
		mPassword = (TextView) findViewById(R.id.twitterPasswordTdxt);
		// Load Twitter UserName and Password to Settings activity		
		mUserName.setText(getResources().getString(R.string.gUserName));
		mPassword.setText(getResources().getString(R.string.gPassword));
	
		// Add a click listener for the Save Settings button
		mSaveSettingsButton.setOnClickListener(new View.OnClickListener() 
		{
		    @Override
		    public void onClick(View v) 
		    {
		    	// This currently does nothing but in the future needs to persist the data somehow 
		    	finish();
		    }
		});		
		
		
	}

}
