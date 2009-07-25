package com.bu.cs683.activity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

import com.bu.cs683.persistence.RoadTripCompanion.Trip;
import com.bu.cs683.utilities.MappingUtility;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

/**
 * Not extending TabActivity here, because I wanted to use a 
 *   Map in one of my tabs, which requires that I extend MapActivity. 
 * 
 * @author mg
 *
 */
public class ViewTrip extends MapActivity
{
	TabHost mTabHost;
	MapView mMapView;
	
	public void onStart() 
    {
        super.onStart();
		setTheme(android.R.style.Theme_Light_NoTitleBar); //TODO: This is not working, but I don't know why!
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewtrip);
		
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		
		mMapView = (MapView) findViewById(R.id.mapview);
		mMapView.setBuiltInZoomControls(true);
		
	    mTabHost.addTab(mTabHost.newTabSpec("tab_test1").setIndicator("Map").setContent(mMapView.getId()));
	    mTabHost.addTab(mTabHost.newTabSpec("tab_test2").setIndicator("Directions").setContent(R.id.textview3));
	    mTabHost.addTab(mTabHost.newTabSpec("tab_test3").setIndicator("Details").setContent(R.id.tripDetailsLayout));
	    
	    mTabHost.setCurrentTab(0);
	    
	    initializeMap();
	    initializeDetails();
	}

	private void initializeDetails()
	{
		Intent intent = getIntent();
		
		TextView tripNameTxt = (TextView) findViewById(R.id.tripNameTxt);
		tripNameTxt.setText(intent.getStringExtra(Trip.NAME));
		
		TextView startDateTxt = (TextView) findViewById(R.id.startDate);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long dateLong = intent.getLongExtra(Trip.START_DATE, new GregorianCalendar().getTimeInMillis());
		startDateTxt.setText(sdf.format(new Date(dateLong)));
		
		TextView mStartAddress = (TextView) findViewById(R.id.startAddressTxt);
		mStartAddress.setText(intent.getStringExtra(Trip.SOURCE));
		
		TextView mDestAddress = (TextView) findViewById(R.id.destAddressTxt);
		mDestAddress.setText(intent.getStringExtra(Trip.DESTINATION));
		
	}

	private void initializeMap()
	{
		try
		{
			GeoPoint src = MappingUtility.getMappingUtility().getGeoPointByLocationName("Boston, MA", getApplicationContext());
			GeoPoint dest = MappingUtility.getMappingUtility().getGeoPointByLocationName("New York, NY", getApplicationContext());
			
			MappingUtility.getMappingUtility().drawPath(src, dest, Color.BLUE, mMapView);
			
			mMapView.getController().zoomToSpan(src.getLatitudeE6() - dest.getLatitudeE6(), src.getLongitudeE6() - dest.getLongitudeE6()); 
			mMapView.getController().animateTo(src); //TODO: update this to be current location
			mMapView.invalidate();
			
			mMapView.setTraffic(false);
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
