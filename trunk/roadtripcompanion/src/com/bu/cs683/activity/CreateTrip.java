package com.bu.cs683.activity;

import java.util.Calendar;
import java.util.GregorianCalendar;

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

import com.bu.cs683.persistence.RoadTripCompanion;
import com.bu.cs683.persistence.RoadTripCompanion.Trip;

public class CreateTrip extends Activity
{
	private TextView mDateDisplay;
	private TextView mName;
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
		mPickDate = (Button) findViewById(R.id.startDate);
		mCreateTrip = (Button) findViewById(R.id.createTripBtn);

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
				//final Intent intent = getIntent();
				
				// Get a cursor to access the trip
				Cursor mCursor = managedQuery(Trip.CONTENT_URI, RoadTripCompanion.TRIP_PROJECTION, null, null, null);
				Log.d(TAG,"Count of records: " + mCursor.getCount());
				
				// validate and save to DB
				ContentValues contentValues = new ContentValues();
				contentValues.put(Trip.NAME, mName.getText().toString());
				contentValues.put(Trip.START_DATE, new GregorianCalendar().getTimeInMillis());
				getContentResolver().insert(Trip.CONTENT_URI, contentValues);
				
				mCursor.requery();
				Log.d(TAG,"Count of records: " + mCursor.getCount());
				
			}
		});

		// get the current date
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		// display the current date
		updateDisplay();
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
		mDateDisplay.setText(new StringBuilder()
		// Month is 0 based so add 1
				.append(mMonth + 1).append("-").append(mDay).append("-").append(mYear).append(" "));
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
}
