package com.bu.cs683.activity;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

import com.bu.cs683.persistence.RoadTripCompanion;
import com.bu.cs683.persistence.RoadTripCompanion.Trip;

public class ViewTrips extends ListActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewtrips);
		
		 // Perform a managed query. The Activity will handle closing and requerying the cursor when needed.
		Cursor mCursor = managedQuery(Trip.CONTENT_URI, RoadTripCompanion.TRIP_PROJECTION, null, null, null);
		
		// Used to map notes entries from the database to views
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.triptextview, mCursor, new String[] { Trip.NAME }, new int[] { R.id.tripText });
        setListAdapter(adapter);
	}	
}
