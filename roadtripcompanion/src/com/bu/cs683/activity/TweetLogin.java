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
	private Button mCancelButton;
	private Button mLoginButton;
	private EditText mUserName;
			
	private static final String TAG = "TweetLogin";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Log.v(TAG, "#### onCreate()");
		setContentView(R.layout.login);
		
		mLoginButton = (Button) findViewById(R.id.loginbtn);
		mCancelButton = (Button) findViewById(R.id.cancelbtn);
		mUserName = (EditText) findViewById(R.id.username);
		
		Log.d("ABC", "Is null = " + (null == mLoginButton));
	
		// Add a click listener for the Login button
		mLoginButton.setOnClickListener(new View.OnClickListener() 
		{
		    @Override
		    public void onClick(View v) 
		    {
		        mUserName.setText("MJurkoic");
		    }
		});		
		
		// Add a click listener for the Cancel button
		mCancelButton.setOnClickListener(new View.OnClickListener() 
		{
		    @Override
		    public void onClick(View v) 
		    {
		        finish();
		    }
		});		
	}
}
