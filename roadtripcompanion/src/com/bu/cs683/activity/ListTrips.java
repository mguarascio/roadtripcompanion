package com.bu.cs683.activity;

import android.app.ListActivity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.bu.cs683.persistence.RoadTripCompanion;
import com.bu.cs683.persistence.RoadTripCompanion.Trip;
import com.bu.cs683.utilities.OptionsMenuHandler;

public class ListTrips extends ListActivity
{
	Cursor mCursor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// allow the user to filter
		getListView().setTextFilterEnabled(true);
		
		// Perform a managed query. The Activity will handle closing and requerying the cursor when needed.
		mCursor = managedQuery(Trip.CONTENT_URI, RoadTripCompanion.TRIP_PROJECTION, null, null, null);
		
		// Used to map notes entries from the database to views
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.triptextview, mCursor, new String[] { Trip.NAME }, new int[] { R.id.tripText });
        setListAdapter(adapter);
	}	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);
		
		// Normally I wouldn't put Cursor nonsense in the activity, but I'm lacking on time!  Ideally this would be in a DBHelper class.
		Uri uri = ContentUris.withAppendedId(Trip.CONTENT_URI, id);
		
		Cursor c = getContentResolver().query(uri, RoadTripCompanion.TRIP_PROJECTION, null, null, null);
		c.moveToFirst();
		
		String name = c.getString(c.getColumnIndex(Trip.NAME));
		long startDate = c.getLong(c.getColumnIndex(Trip.START_DATE));
		String startAddr = c.getString(c.getColumnIndex(Trip.SOURCE));
		String destAddr = c.getString(c.getColumnIndex(Trip.DESTINATION));
		
		Intent viewTrip = new Intent(getApplicationContext(), ViewTrip.class);
		viewTrip.putExtra(Trip.NAME, name);
		viewTrip.putExtra(Trip.START_DATE, startDate);
		viewTrip.putExtra(Trip.SOURCE, startAddr);
		viewTrip.putExtra(Trip.DESTINATION, destAddr);
		
		c.close();
		
		Log.d(getClass().getName(),"data: " + name + " " + startDate + " " + startAddr + " " + destAddr);
		
		startActivity(viewTrip);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		boolean val = super.onCreateOptionsMenu(menu);
		menu.add(0, OptionsMenuHandler.MENU_HOME_ID, 0, R.string.menu_home);
		menu.add(0, OptionsMenuHandler.MENU_CREATE_ID, 1, R.string.menu_create);
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
