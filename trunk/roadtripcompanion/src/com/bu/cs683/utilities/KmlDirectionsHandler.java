package com.bu.cs683.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class KmlDirectionsHandler extends DefaultHandler
{
	private List<Map<String, String>> directionsMap = new ArrayList<Map<String,String>>();
	private boolean isDirectionsNode = false;
	private static final String DIRECTIONS_NODE_NAME = "name";
	
	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException
	{
		super.startElement(uri, localName, name, attributes);
		if(localName.equals(DIRECTIONS_NODE_NAME))
		{
			System.out.println("directions node hit");
			isDirectionsNode = true;
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String name) throws SAXException
	{
		super.endElement(uri, localName, name);
		if(localName.equals(DIRECTIONS_NODE_NAME))
		{
			isDirectionsNode = false;
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException
	{
		super.characters(ch, start, length);
		if(isDirectionsNode)
		{ 
			Map<String, String> direction = new HashMap<String, String>();
			direction.put("direction", new String(ch, start, length));
	         directionsMap.add(direction);
		}
	}
	
	public List<Map<String,String>> getParsedData()
	{	
		return directionsMap;
	}
}
