package com.bu.cs683.persistence;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Constants for RoadTripProvider
 * 
 * @author mg
 */
public final class RoadTripCompanion
{
	public static final String AUTHORITY = "com.bu.cs683.activity.roadtripprovider";

	/**
     * Standard projection for the columns of a trip.
     */
    public static final String[] TRIP_PROJECTION = new String[] {
            Trip._ID, // 0
            Trip.NAME, // 1
            Trip.START_DATE, //2
            Trip.SOURCE, //3
            Trip.DESTINATION //4
    };
	
	//class can't be instantiated
	private RoadTripCompanion() {};

	/**
	 * Trip table
	 * 
	 * @author mg
	 */
	public static final class Trip implements BaseColumns
	{
		private Trip() {};
		
		/**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/trips");
        
        /**
         * The default sort order for this table
         */
        public static final String DEFAULT_SORT_ORDER = "startDate DESC";
        
        /**
         * The name of the trip
         * 
         * <p>Type: TEXT</p>
         */
        public static final String NAME = "name";
        
        /**
         * The start date of the trip
         * 
         * <p>Type: INTEGER (long)</p>
         */
        public static final String START_DATE = "startDate";
        
        /**
         * The starting address of the trip
         * 
         * <p>Type: TEXT</p>
         */
        public static final String SOURCE = "source";
        
        /**
         * The destination address of the trip
         * 
         * <p>Type: TEXT</p>
         */
        public static final String DESTINATION = "destination";
	}
}
