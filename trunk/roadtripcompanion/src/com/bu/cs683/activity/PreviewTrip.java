package com.bu.cs683.activity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.bu.cs683.persistence.RoadTripCompanion.Trip;
import com.bu.cs683.utilities.MappingUtility;
import com.bu.cs683.utilities.TripAlarmReceiver;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

//TODO: set point of interest as source & destination addresses, instead of displaying as text?
public class PreviewTrip extends MapActivity
{
	TextView mTripName;
	TextView mTripDate;
	long dateLong;
	String mStartAddress;
	String mDestAddress;

	Button mReviseTrip;
	Button mCreateTrip;
	
	MapView mMapView;
	List<MapDirectionsOverlay> overlays;
	ZoomControls mZoom;
	MapController mc;
	GeoPoint src;
	GeoPoint dest;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	ProgressDialog progressDialog;

	private final Handler handler = new Handler() {

        @Override

        public void handleMessage(final Message msg) {
            Log.v(getClass().getName(), "worker thread done");

            for (MapDirectionsOverlay overlay : overlays)
    		{
    		    mMapView.getOverlays().add(overlay);
    		}
            refreshMapData(src, dest);
            progressDialog.dismiss();
               
        }

    };  
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.previewtrip);

		Intent intent = getIntent();

		mTripName = (TextView) findViewById(R.id.tripNameTxt);
		mTripName.setText(intent.getStringExtra(Trip.NAME));

		mTripDate = (TextView) findViewById(R.id.dateDisplay);
		dateLong = intent.getLongExtra(Trip.START_DATE, new GregorianCalendar().getTimeInMillis());
		mTripDate.setText(sdf.format(new Date(dateLong)));

		mStartAddress = intent.getStringExtra(Trip.SOURCE);
		mDestAddress = intent.getStringExtra(Trip.DESTINATION);
		
		mMapView = (MapView) findViewById(R.id.mapview);
		mMapView.setBuiltInZoomControls(true);
		mc = mMapView.getController();

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
				
				Uri newTripUri = getContentResolver().insert(Trip.CONTENT_URI, contentValues);
				String idString = newTripUri.getLastPathSegment();
				// Create Notification
				createAlarm(Integer.valueOf(idString).intValue());
				
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
		
		// perform map work in a background thread
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Calculating Trip Route...");
		progressDialog.setCancelable(false);
	    progressDialog.show();
	    
	    // takes a bit of time so we'll run it in a background thread and show the progress dialog
	    new Thread() {
	    	@Override
	    	public void run()
	    	{
	    		initializeMap();
	    		handler.sendEmptyMessage(0); //notify handler that thread is done
	    	}
	    }.start();
	}

	@Override
	protected boolean isRouteDisplayed()
	{
		return true;
	}
	
	private void initializeMap()
	{
		try
		{
			src = MappingUtility.getMappingUtility().getGeoPointByLocationName(mStartAddress, getApplicationContext());
			dest = MappingUtility.getMappingUtility().getGeoPointByLocationName(mDestAddress, getApplicationContext());
			
			overlays = MappingUtility.getMappingUtility().drawPathOverlays(src, dest, Color.BLUE);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void refreshMapData(GeoPoint src, GeoPoint dest)
	{
		mMapView.getController().zoomToSpan(src.getLatitudeE6() - dest.getLatitudeE6(), src.getLongitudeE6() - dest.getLongitudeE6()); 
		mMapView.getController().animateTo(src); //TODO: update this to be current location
		mMapView.invalidate();
		
		mMapView.setTraffic(false);
	}

	private void createAlarm(int newTripId)
	{
		String as = ALARM_SERVICE;
		
		AlarmManager alarmMgr = (AlarmManager) getSystemService(as);
		
		long when = dateLong - AlarmManager.INTERVAL_DAY; //trigger the notification one day before the date of the trip 
		
		Intent broadcastIntent = new Intent(TripAlarmReceiver.class.getCanonicalName());
		PendingIntent contentIntent = PendingIntent.getBroadcast(getApplicationContext(), newTripId, broadcastIntent, 0);

		System.out.println(sdf.format(new Date(when)));
		alarmMgr.set(AlarmManager.RTC_WAKEUP, when, contentIntent);
		
		Toast toasted = Toast.makeText(this, "Trip Created!", Toast.LENGTH_LONG);
		toasted.setDuration(5000);
		toasted.show();
	}
}
