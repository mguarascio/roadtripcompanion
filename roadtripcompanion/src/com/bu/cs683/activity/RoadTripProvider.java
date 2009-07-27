package com.bu.cs683.activity;

import java.util.GregorianCalendar;
import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.bu.cs683.persistence.RoadTripCompanion;
import com.bu.cs683.persistence.RoadTripCompanion.Trip;

/**
 * Provides access to a database of road trips. Each trip has a title, a start date, a start
 * and a destination.
 */
public class RoadTripProvider extends ContentProvider
{
	private static final String TAG = "RoadTripProvider";
	
	public static final String DATABASE_NAME = "roadtrip_companion.db";
	public static final int DATABASE_VERSION = 1;
	public static final String TRIP_TABLE_NAME = "trip";
	
	private static final int TRIPS = 1;
    private static final int TRIP_ID = 2;

    private static final UriMatcher sUriMatcher;
    
    private static HashMap<String, String> sTripProjectionMap;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(RoadTripCompanion.AUTHORITY, "trips", TRIPS);
        sUriMatcher.addURI(RoadTripCompanion.AUTHORITY, "trips/#", TRIP_ID);

        sTripProjectionMap = new HashMap<String, String>();
        sTripProjectionMap.put(Trip._ID, Trip._ID);
        sTripProjectionMap.put(Trip.NAME, Trip.NAME);
        sTripProjectionMap.put(Trip.START_DATE, Trip.START_DATE);
        sTripProjectionMap.put(Trip.SOURCE, Trip.SOURCE);
        sTripProjectionMap.put(Trip.DESTINATION, Trip.DESTINATION);
    }
    
    /**
     * Helper class to open, create, and upgrade the database file.
     */
    private class DBHelper extends SQLiteOpenHelper
    {
    	public static final String CREATE_TABLES = "CREATE TABLE " + TRIP_TABLE_NAME + " ("
    												+ Trip._ID + " INTEGER PRIMARY KEY,"
    												+ Trip.NAME + " TEXT,"
    												+ Trip.START_DATE + " INTEGER,"
    												+ Trip.SOURCE + " TEXT,"
    												+ Trip.DESTINATION + " TEXT"
    												+ ");";
    	
    	public DBHelper(Context context)
    	{
    		super(context, DATABASE_NAME, null, DATABASE_VERSION);
    	}

    	@Override
    	public void onCreate(SQLiteDatabase db)
    	{
    		db.execSQL(CREATE_TABLES);
    	}

    	@Override
    	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    	{
    		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TRIP_TABLE_NAME);
            onCreate(db);
    	}

    }

    private DBHelper mOpenHelper;

    @Override
    public boolean onCreate() {
        mOpenHelper = new DBHelper(getContext());
        return true;
    }
    
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues initialValues)
	{
		// Validate the requested uri
		Log.v(TAG, "uri: " + uri);
        if (sUriMatcher.match(uri) != TRIPS) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        
		ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }
		
        // Make sure that the fields are all set
        if (values.containsKey(Trip.NAME) == false) {
            values.put(Trip.NAME, "new trip");
        }
        
        if (values.containsKey(Trip.START_DATE) == false) {
        	values.put(Trip.START_DATE, new GregorianCalendar().getTimeInMillis());
        }
        
        if (values.containsKey(Trip.SOURCE) == false) {
            values.put(Trip.SOURCE, "");
            //TODO: throw error
        }
        
        if (values.containsKey(Trip.DESTINATION) == false) {
        	values.put(Trip.DESTINATION, "");
        	//TODO: throw error
        }
        
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long rowId = db.insert(TRIP_TABLE_NAME, Trip.NAME, values);
        if (rowId > 0) {
            Uri noteUri = ContentUris.withAppendedId(Trip.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(noteUri, null);
            return noteUri;
        }

        throw new SQLException("Failed to insert row into " + uri);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
	{
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        
        switch (sUriMatcher.match(uri)) {
        case TRIPS:
            qb.setTables(TRIP_TABLE_NAME);
            qb.setProjectionMap(sTripProjectionMap);
            break;

        case TRIP_ID:
            qb.setTables(TRIP_TABLE_NAME);
            qb.setProjectionMap(sTripProjectionMap);
            qb.appendWhere(Trip._ID + "=" + uri.getPathSegments().get(1));
            break;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        
        // If no sort order is specified use the default
        String orderBy;
        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = Trip.DEFAULT_SORT_ORDER; 
        } else {
            orderBy = sortOrder;
        }
        
        // Get the database and run the query
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
        
        // Tell the cursor what uri to watch, so it knows when its source data changes
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
