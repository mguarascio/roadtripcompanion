package com.bu.cs683.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.bu.cs683.persistence.RoadTripCompanion.Trip;
import com.bu.cs683.utilities.OptionsMenuHandler;

public class CreateTrip extends Activity
{
	private long dateLong;
	private TextView mDateDisplay;
	private TextView mName;
	private TextView mStartAddr;
	private TextView mDestAddr;
	private Button mPickDate;
	private Button mCreateTrip;

	private int mDay;
	private int mYear;
	private int mMonth;

	static final int DATE_DIALOG_ID = 0;

	private static final String TAG = "CreateTrip";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Log.v(TAG, "#### onCreate()");
		setContentView(R.layout.createtrip);

		mDateDisplay = (TextView) findViewById(R.id.dateDisplay);
		mName = (TextView) findViewById(R.id.tripNameTxt);
		mStartAddr = (TextView) findViewById(R.id.startAddressTxt);
		mDestAddr = (TextView) findViewById(R.id.destAddressTxt);
		mPickDate = (Button) findViewById(R.id.startDate);
		mCreateTrip = (Button) findViewById(R.id.previewTripBtn);
		
		// add a click listener for pickDate button
		mPickDate.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				showDialog(DATE_DIALOG_ID);
			}
		});

		// add a click listener for createTrip button
		mCreateTrip.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{				
				// validate values
				//TODO: validation and whatnot
	
				Intent previewIntent = new Intent(getApplicationContext(), PreviewTrip.class);
				previewIntent.putExtra(Trip.NAME, mName.getText().toString());
				previewIntent.putExtra(Trip.START_DATE, dateLong);
				previewIntent.putExtra(Trip.SOURCE, mStartAddr.getText().toString());
				previewIntent.putExtra(Trip.DESTINATION, mDestAddr.getText().toString());
				
				startActivity(previewIntent);
				
			}
		});

		// get the current date
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		// display the current date
		updateDisplay();
		
		// get the intent and load the data if present
		Intent incomingIntent = getIntent();
		if(null != incomingIntent.getExtras())
		{
			mName.setText(incomingIntent.getStringExtra(Trip.NAME));
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			dateLong = incomingIntent.getLongExtra(Trip.START_DATE, new GregorianCalendar().getTimeInMillis());
			mDateDisplay.setText(sdf.format(new Date(dateLong)));
			
			mStartAddr.setText(incomingIntent.getStringExtra(Trip.SOURCE));
			mDestAddr.setText(incomingIntent.getStringExtra(Trip.DESTINATION));
		}
	}

	@Override
	protected Dialog onCreateDialog(int id)
	{
		switch (id)
		{
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
		}
		return null;
	}

	// updates the date we display in the TextView
	private void updateDisplay()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		dateLong = new Date(mYear-1900,mMonth,mDay).getTime();
		mDateDisplay.setText(sdf.format(new Date(mYear-1900,mMonth,mDay)));
	}

	// the call back received when the user "sets" the date in the dialog
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener()
	{
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
		{
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay();
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		boolean val = super.onCreateOptionsMenu(menu);
		menu.add(0, OptionsMenuHandler.MENU_HOME_ID, 0, R.string.menu_home);
		menu.add(0, OptionsMenuHandler.MENU_LIST_ID, 1, R.string.menu_list);
		menu.add(0, OptionsMenuHandler.MENU_TWEET_ID, 2, R.string.menu_tweet);
		menu.add(0, OptionsMenuHandler.MENU_PHOTO_ID, 3, R.string.menu_photo);
		return val;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = OptionsMenuHandler.getIntentForOptionsItem(item, getApplicationContext());
        if(null != intent)
        {
        	startActivity(intent);
        	return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
