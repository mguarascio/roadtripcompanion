package com.bu.cs683.activity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.bu.cs683.persistence.RoadTripCompanion.Trip;
import com.bu.cs683.utilities.MappingUtility;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

//TODO: set point of interest as source & destination addresses, instead of displaying as text?
//TODO: traffic view of map!

public class PreviewTrip extends MapActivity
{
	TextView mTripName;
	TextView mTripDate;
	long dateLong;
	String mStartAddress;
	String mDestAddress;

	Button mReviseTrip;
	Button mCreateTrip;
	
	MapView mapView;
	ZoomControls mZoom;
	MapController mc;
	GeoPoint srcGeoPt;
	GeoPoint destGeoPt;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.previewtrip);

		Intent intent = getIntent();

		mTripName = (TextView) findViewById(R.id.tripNameTxt);
		mTripName.setText(intent.getStringExtra(Trip.NAME));

		mTripDate = (TextView) findViewById(R.id.dateDisplay);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		dateLong = intent.getLongExtra(Trip.START_DATE, new GregorianCalendar().getTimeInMillis());
		mTripDate.setText(sdf.format(new Date(dateLong)));

		mStartAddress = intent.getStringExtra(Trip.SOURCE);
		mDestAddress = intent.getStringExtra(Trip.DESTINATION);
		
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mc = mapView.getController();

		mCreateTrip = (Button) findViewById(R.id.createTripBtn);
		mCreateTrip.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v)
			{
				// validate and save to DB
				ContentValues contentValues = new ContentValues();
				contentValues.put(Trip.NAME, mTripName.getText().toString());
				contentValues.put(Trip.START_DATE, dateLong);
				contentValues.put(Trip.SOURCE, mStartAddress);
				contentValues.put(Trip.DESTINATION, mDestAddress);
				
				getContentResolver().insert(Trip.CONTENT_URI, contentValues);
				
				Intent viewTrips = new Intent(getApplicationContext(), ListTrips.class);
				
				startActivity(viewTrips);
			}
			
		});
		
		mReviseTrip = (Button) findViewById(R.id.reviseTripBtn);
		mReviseTrip.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				Intent createTrip = new Intent(getApplicationContext(), CreateTrip.class);
				createTrip.putExtra(Trip.NAME, mTripName.getText().toString());
				createTrip.putExtra(Trip.START_DATE, dateLong);
				createTrip.putExtra(Trip.SOURCE, mStartAddress);
				createTrip.putExtra(Trip.DESTINATION, mDestAddress);
				startActivity(createTrip);
			}
		});
		
		try
		{
			srcGeoPt = MappingUtility.getMappingUtility().getGeoPointByLocationName(mStartAddress, this);
			destGeoPt = MappingUtility.getMappingUtility().getGeoPointByLocationName(mDestAddress, this);
			MappingUtility.getMappingUtility().drawPath(srcGeoPt, destGeoPt, Color.BLUE, mapView);
			
			mapView.getController().animateTo(srcGeoPt); 
	        mapView.getController().setZoom(15); 
			mapView.invalidate();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected boolean isRouteDisplayed()
	{
		return true;
	}
}
