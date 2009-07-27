package com.bu.cs683.utilities;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import com.bu.cs683.activity.CreateTrip;
import com.bu.cs683.activity.ListTrips;

public class OptionsMenuHandler 
{
	public static final int MENU_HOME_ID = 1;
	public static final int MENU_CREATE_ID = 2;
	public static final int MENU_LIST_ID = 3;
	public static final int MENU_TWEET_ID = 4;
	public static final int MENU_PHOTO_ID = 5;
	
	public static Intent getIntentForOptionsItem(MenuItem item, Context appContext)
	{
		switch (item.getItemId()) {
        case MENU_HOME_ID:
            return browseToHome(appContext);
        case MENU_CREATE_ID:
        	return browseToCreateTrip(appContext);
        case MENU_LIST_ID:
        	return browseToListTrips(appContext);
        case MENU_TWEET_ID:
        	return browseToTweet(appContext);
        case MENU_PHOTO_ID:
        	return browseToPhoto(appContext);
        default:
        	return null;
        }
	}
	
	private static Intent browseToListTrips(Context appContext)
	{
		return new Intent(appContext, ListTrips.class);
	}

	private static Intent browseToPhoto(Context context)
	{
		// TODO Auto-generated method stub
		return null;
	}

	private static Intent browseToTweet(Context context)
	{
		// TODO Auto-generated method stub
		return null;
	}

	private static Intent browseToHome(Context context)
	{ //TODO: uncomment once MainScreen is created
	  //		Intent homeIntent = new Intent(getApplicationContext(), MainScreen.class);
	  //		startActivity(homeIntent);
		return null;
	}
	
	private static Intent browseToCreateTrip(Context context)
	{ 
		return new Intent(context, CreateTrip.class);
	}	
}
