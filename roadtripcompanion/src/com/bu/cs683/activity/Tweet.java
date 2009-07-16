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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class Tweet extends Activity
{
	static final int DATE_DIALOG_ID = 0;

	private static final String TAG = "MainScreen";	

	public Tweet() {
		// TODO Auto-generated constructor stub
	}
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Log.v(TAG, "#### onCreate()");
		setContentView(R.layout.tweet);
	}
}
	



