package com.bu.cs683.utilities;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.bu.cs683.activity.MapDirectionsOverlay;
import com.google.android.maps.GeoPoint;

/**
 * Helper class to handle common Mappping functionality
 * 
 * @author mg
 *
 */
public class MappingUtility
{
	private static final MappingUtility SINGLETON = new MappingUtility();
	private static String TAG = "MappingUtility";
	
	private MappingUtility()
	{
	}
	
	public static MappingUtility getMappingUtility()
	{
		return SINGLETON;
	}
	
	/**
	 * Return a GeoPoint based on the LocationName passed in.  Takes the first result returned by geocoder. 
	 * @param locName
	 * @return a GeoPoint, if found
	 */
	public GeoPoint getGeoPointByLocationName(String locName, Context context) throws IOException
	{
		Geocoder geocoder = new Geocoder(context, Locale.getDefault());
		List<Address> addresses = geocoder.getFromLocationName(locName, 5);
		return new GeoPoint((int) (addresses.get(0).getLatitude() * 1E6), (int) (addresses.get(0).getLongitude() * 1E6));
	}
	
	public List<MapDirectionsOverlay> drawPathOverlays(GeoPoint src, GeoPoint dest, int color)
	{
		List<MapDirectionsOverlay> overlays = new ArrayList<MapDirectionsOverlay>();
		
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
				overlays.add(new MapDirectionsOverlay(startGP, startGP, 1));
				GeoPoint gp1;
				GeoPoint gp2 = startGP;
				for (int i = 1; i < pairs.length; i++)
				{
					lngLat = pairs[i].split(",");
					gp1 = gp2;
					gp2 = new GeoPoint((int) (Double.parseDouble(lngLat[1]) * 1E6), (int) (Double.parseDouble(lngLat[0]) * 1E6));
					overlays.add(new MapDirectionsOverlay(gp1, gp2, 2, color));
					Log.d(TAG, "pair:" + pairs[i]);
				}
				overlays.add(new MapDirectionsOverlay(dest, dest, 3));
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

		return overlays;
	}
	
	public List<Map<String, String>> getDirections(GeoPoint src, GeoPoint dest)
	{
		
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
		List<Map<String, String>> directions = new ArrayList<Map<String, String>>();
		
		try
		{
			url = new URL(urlString.toString());
			
		// Parse the directions
		/* Get a SAXParser from the SAXPArserFactory. */ 
        SAXParserFactory spf = SAXParserFactory.newInstance(); 
        SAXParser sp = spf.newSAXParser(); 

        /* Get the XMLReader of the SAXParser we created. */ 
        XMLReader xr = sp.getXMLReader(); 
        /* Create a new ContentHandler and apply it to the XML-Reader*/ 
        KmlDirectionsHandler handler = new KmlDirectionsHandler(); 
        xr.setContentHandler(handler); 
         
        /* Parse the xml-data from our URL. */ 
        xr.parse(new InputSource(url.openStream())); 
                
        directions = handler.getParsedData();
      
        /* Parsing has finished. */
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
		
		return directions;
	      
	}
}
