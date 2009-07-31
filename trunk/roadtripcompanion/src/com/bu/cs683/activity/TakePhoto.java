package com.bu.cs683.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.webkit.WebView;

public class TakePhoto extends Activity
{
private static final String TAG = "TakePhoto";
	
	Button mClose;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Log.v(TAG, "#### onCreate()");
		setContentView(R.layout.takephoto);
		
		mClose = (Button) findViewById(R.id.takePhotoCloseButton);

		// Add a click listener for the Save Settings button
		mClose.setOnClickListener(new View.OnClickListener() 
		{
		    @Override
		    public void onClick(View v) 
		    {
		    	// Close the activity 
		    	finish();
		    }
		});		
		
	}
		

}
