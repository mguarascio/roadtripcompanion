package com.bu.cs683.activity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.webkit.WebView;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Settings extends Activity
{
	private static final String TAG = "Settings";
	//private final String strUN;
	private SharedPreferences app_preferences;
	
	TextView mUserName;
	TextView mPassword;
	
	Button mSaveSettingsButton;
	Button mCancelSettingsButton;
	
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Log.v(TAG, "#### onCreate()");
		setContentView(R.layout.settings);
		
//		editor.("twitter_username", "MJurkoic");
//		editor.putString("twiiter_password", "kielbasa");
//		// Set up preferences here
		
		app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
		// Get twitter_username and twitter_password from preferences
		String mUN = app_preferences.getString("twitter_username", "ABC");
		String mPw = app_preferences.getString("twitter_password", "123");
		
		mSaveSettingsButton = (Button) findViewById(R.id.saveSettingsButton);
		mCancelSettingsButton = (Button) findViewById(R.id.cancelSettingsButton);
		
		// This section of code gets the Twitter UserName and Twitter Password from the strings resource
		// A more persistent way, such as SharedPrefs needs to be implemented ultimately
		// Get Twitter UserName and Password from strings
		mUserName = (TextView) findViewById(R.id.twitterUserNameText);
		mPassword = (TextView) findViewById(R.id.twitterPasswordTdxt);
		// Load Twitter UserName and Password to Settings activity		
		//mUserName.setText(getResources().getString(R.string.gUserName));
		//mPassword.setText(getResources().getString(R.string.gPassword));
		mUserName.setText(mUN);
		mPassword.setText(mPw);
		// Add a click listener for the Save Settings button
		mSaveSettingsButton.setOnClickListener(new View.OnClickListener() 
		{
		    @Override
		    public void onClick(View v) 
		    {
		    	SharedPreferences.Editor editor = app_preferences.edit();
		    	// The preferences need to be set here 
		    	String strUN = mUserName.getText().toString();
		    	String strPW = mPassword.getText().toString();
		    	editor.putString("twitter_username", mUserName.getText().toString());
		    	editor.putString("twitter_password", mPassword.getText().toString());
		    	editor.commit();
		    	
		    	finish();
		    }
		    
		});		
		
		// Add a click listener for the Cancel Settings button
	    mCancelSettingsButton.setOnClickListener(new View.OnClickListener() 
		{
		    @Override
		    public void onClick(View v) 
		    {
		    	// Do nothing and close activity
		    	
		    	finish();
		    }
		});	
		
	}

}
