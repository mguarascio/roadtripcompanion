package com.bu.cs683.activity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.bu.cs683.persistence.RoadTripCompanion.Trip;
import com.bu.cs683.utilities.MappingUtility;
import com.bu.cs683.utilities.OptionsMenuHandler;
import com.bu.cs683.utilities.TransparentPanel;
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
	boolean trafficOn = false;
	TabHost mTabHost;
	MapView mMapView;
	String sourceAddress;
	String destAddress;
	GeoPoint src;
	GeoPoint dest;
	ProgressDialog progressDialog;
	List<MapDirectionsOverlay> overlays;
	Button mToggleTraffic;
	
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
		//mMapView.setBuiltInZoomControls(true);
		//mMapView.displayZoomControls(true);
		ZoomControls controls = (ZoomControls) mMapView.getZoomControls();
		TransparentPanel panel = (TransparentPanel)findViewById(R.id.transparent_panel);
		panel.addView(controls);
		
		mMapView.displayZoomControls(true);
		
	    mTabHost.addTab(mTabHost.newTabSpec("tab_test1").setIndicator("Map").setContent(R.id.mapContainer));
	    mTabHost.addTab(mTabHost.newTabSpec("tab_test2").setIndicator("Directions").setContent(R.id.directionsContainer));
	    mTabHost.addTab(mTabHost.newTabSpec("tab_test3").setIndicator("Details").setContent(R.id.tripDetailsLayout));
	    
	    mTabHost.setCurrentTab(0);
	    
	    progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Loading Trip Data...");
		progressDialog.setCancelable(false);
	    progressDialog.show();
	    
	    initializeDetails(getIntent());
	    try
		{
			src = MappingUtility.getMappingUtility().getGeoPointByLocationName(sourceAddress, getApplicationContext());
			dest = MappingUtility.getMappingUtility().getGeoPointByLocationName(destAddress, getApplicationContext());
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	    initializeDirections((ListView)findViewById(android.R.id.list));
	    
	    // takes a bit of time so we'll run it in a background thread and show the progress dialog
	    new Thread() {
	    	@Override
	    	public void run()
	    	{
	    		initializeMap();
	    		handler.sendEmptyMessage(0); //notify handler that thread is done
	    	}

	    }.start();
	
	    
	   mToggleTraffic = (Button) findViewById(R.id.toggleTraffic);
	   mToggleTraffic.setOnClickListener(new View.OnClickListener() {

		public void onClick(View v)
		{
			trafficOn = !trafficOn;
			mMapView.setTraffic(trafficOn);
		}
	  
	   });
	}

	private void initializeDetails(Intent intent)
	{
		sourceAddress = intent.getStringExtra(Trip.SOURCE);
		destAddress = intent.getStringExtra(Trip.DESTINATION);
		
		TextView tripNameTxt = (TextView) findViewById(R.id.tripNameTxt);
		tripNameTxt.setText(intent.getStringExtra(Trip.NAME));
		
		TextView startDateTxt = (TextView) findViewById(R.id.startDate);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long dateLong = intent.getLongExtra(Trip.START_DATE, new GregorianCalendar().getTimeInMillis());
		startDateTxt.setText(sdf.format(new Date(dateLong)));
		
		TextView mStartAddress = (TextView) findViewById(R.id.startAddressTxt);
		mStartAddress.setText(sourceAddress);
		
		TextView mDestAddress = (TextView) findViewById(R.id.destAddressTxt);
		mDestAddress.setText(destAddress);
		
	}

	private void initializeMap()
	{
			overlays = MappingUtility.getMappingUtility().drawPathOverlays(src, dest, Color.BLUE);

	}
	
	private void initializeDirections(ListView view)
	{
		List<Map<String,String>> directions = MappingUtility.getMappingUtility().getDirections(src, dest);
		SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), directions, R.layout.triptextview, new String[] {"direction"},new int[]{R.id.tripText});
		view.setAdapter(adapter);
	}
	
	private void refreshMapData(GeoPoint src, GeoPoint dest)
	{
		mMapView.getController().zoomToSpan(src.getLatitudeE6() - dest.getLatitudeE6(), src.getLongitudeE6() - dest.getLongitudeE6()); 
		mMapView.getController().animateTo(src); //TODO: update this to be current location
		mMapView.invalidate();
		
		mMapView.setTraffic(false);
	}
	
	@Override
	protected boolean isRouteDisplayed()
	{
		return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		boolean val = super.onCreateOptionsMenu(menu);
		menu.add(0, OptionsMenuHandler.MENU_HOME_ID, 0, R.string.menu_home);
		menu.add(0, OptionsMenuHandler.MENU_CREATE_ID, 1, R.string.menu_create);
		menu.add(0, OptionsMenuHandler.MENU_LIST_ID, 2, R.string.menu_list);
		menu.add(0, OptionsMenuHandler.MENU_TWEET_ID, 3, R.string.menu_tweet);
		menu.add(0, OptionsMenuHandler.MENU_PHOTO_ID, 4, R.string.menu_photo);
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
