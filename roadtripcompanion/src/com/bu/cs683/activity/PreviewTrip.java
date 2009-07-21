package com.bu.cs683.activity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.bu.cs683.persistence.RoadTripCompanion.Trip;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class PreviewTrip extends MapActivity
{
	private final String TAG = this.getClass().getName();
	TextView mTripName;
	TextView mTripDate;
	TextView mStartAddress;
	TextView mDestAddress;

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
		mTripDate.setText(String.valueOf(intent.getLongExtra(Trip.START_DATE, new GregorianCalendar().getTimeInMillis())));

		mStartAddress = (TextView) findViewById(R.id.startAddressTxt);
		mStartAddress.setText(intent.getStringExtra(Trip.SOURCE));

		mDestAddress = (TextView) findViewById(R.id.destAddressTxt);
		mDestAddress.setText(intent.getStringExtra(Trip.DESTINATION));

		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mc = mapView.getController();

		try
		{
			Geocoder geocoder = new Geocoder(this, Locale.getDefault());
			List<Address> srcAddresses = geocoder.getFromLocationName(mStartAddress.getText().toString(), 5);
			srcGeoPt = new GeoPoint((int) (srcAddresses.get(0).getLatitude() * 1E6), (int) (srcAddresses.get(0).getLongitude() * 1E6));

			List<Address> destAddresses = geocoder.getFromLocationName(mDestAddress.getText().toString(), 5);
			destGeoPt = new GeoPoint((int) (destAddresses.get(0).getLatitude() * 1E6), (int) (destAddresses.get(0).getLongitude() * 1E6));
			
			DrawPath(srcGeoPt, destGeoPt, BIND_AUTO_CREATE, mapView);
			
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
		return false;
	}

	private void DrawPath(GeoPoint src, GeoPoint dest, int color, MapView mMapView1)
	{
		// This is a hack since the google maps api included in android 1.5
		// doesn't include navigation features
		// We are using the maps.google.com URL to retrieve KML data to include
		// on our map.
		StringBuilder urlString = new StringBuilder();
		urlString.append("http://maps.google.com/maps?f=d&hl=en");
		urlString.append("&saddr="); // source address
		urlString.append(Double.toString(src.getLatitudeE6() / 1.0E6));
		urlString.append(",");
		urlString.append(Double.toString(src.getLongitudeE6() / 1.0E6));
		urlString.append("&daddr="); // destination address
		urlString.append(Double.toString(dest.getLatitudeE6() / 1.0E6));
		urlString.append(",");
		urlString.append(Double.toString(dest.getLongitudeE6() / 1.0E6));
		urlString.append("&ie=UTF8&0&om=0&output=kml"); // output=kml gives us
		// the kml document
		Log.d(TAG, "URL=" + urlString.toString());

		// get the KML doc and parse it to get the coordinates
		Document doc = null;
		HttpURLConnection urlConnection = null;
		URL url = null;
		try
		{
			url = new URL(urlString.toString());
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			urlConnection.connect();

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(urlConnection.getInputStream());

			if (doc.getElementsByTagName("GeometryCollection").getLength() > 0)
			{
				String path = doc.getElementsByTagName("GeometryCollection").item(0).getFirstChild().getFirstChild().getFirstChild().getNodeValue();
				Log.d(TAG, "path=" + path);
				String[] pairs = path.split(" ");
				String[] lngLat = pairs[0].split(","); // lngLat[0]=longitude
				// lngLat[1]=latitude
				// lngLat[2]=height
				// src
				GeoPoint startGP = new GeoPoint((int) (Double.parseDouble(lngLat[1]) * 1E6), (int) (Double.parseDouble(lngLat[0]) * 1E6));
				mMapView1.getOverlays().add(new MapDirectionsOverlay(startGP, startGP, 1));
				GeoPoint gp1;
				GeoPoint gp2 = startGP;
				for (int i = 1; i < pairs.length; i++) // the last one would be
				// crash
				{
					lngLat = pairs[i].split(",");
					gp1 = gp2;
					gp2 = new GeoPoint((int) (Double.parseDouble(lngLat[1]) * 1E6), (int) (Double.parseDouble(lngLat[0]) * 1E6));
					mMapView1.getOverlays().add(new MapDirectionsOverlay(gp1, gp2, 2, color));
					Log.d(TAG, "pair:" + pairs[i]);
				}
				mMapView1.getOverlays().add(new MapDirectionsOverlay(dest, dest, 3)); // use
				// the
				// default
				// color
			}
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ParserConfigurationException e)
		{
			e.printStackTrace();
		}
		catch (SAXException e)
		{
			e.printStackTrace();
		}
	}
}
