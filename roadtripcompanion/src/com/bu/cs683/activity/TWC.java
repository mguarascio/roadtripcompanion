package com.bu.cs683.activity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.webkit.WebView;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.net.URL; 

import javax.xml.parsers.SAXParser; 
import javax.xml.parsers.SAXParserFactory; 

import org.xml.sax.InputSource; 
import org.xml.sax.XMLReader; 

import android.app.Activity; 
import android.os.Bundle; 
import android.preference.PreferenceManager;
import android.util.Log; 
import android.widget.TextView;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

import org.xml.sax.SAXException;
import java.net.URL; 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser; 
import javax.xml.parsers.SAXParserFactory; 

import org.xml.sax.InputSource; 
import org.xml.sax.XMLReader; 

import com.bu.cs683.utilities.KmlDirectionsHandler;


public class TWC extends Activity 
{
	Button mButton;
	Button mUpdateButton;
	TextView mText;
	TextView mTweetText;
	WebView mWebView;
	TextView mUserName;
	TextView mPassword;
	String strUserName;
	String strPassword;
	
		
	static final int DATE_DIALOG_ID = 0;
	private static final String TAG = "Twitter Client";
	public SharedPreferences app_preferences;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twc);
        
               
        // Set UserName and Password textview widths
        mUserName = (TextView) findViewById(R.id.usernameTxt);
        mUserName.setWidth(150);
        mPassword = (TextView) findViewById(R.id.passwordTxt);
        mPassword.setWidth(150);
        // Set the text field and buttons
        mText = (TextView) findViewById(R.id.tweetTextViewView);
        mButton = (Button) findViewById(R.id.authenticateButton);
        mUpdateButton = (Button) findViewById(R.id.updateButton);
        
        //TextView mUserName;
		//TextView mPassword;
		//mUserName = (TextView) findViewById(R.id.usernameTxt);
		//mPassword = (TextView) findViewById(R.id.passwordTxt);
		
		// Set up preferences here
		app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
				
		// Using strings
		mUserName.setText(getResources().getString(R.string.gUserName));
		mPassword.setText(getResources().getString(R.string.gPassword));
		// Using SharedPreferences - Get twitter_username and twitter_password from preferences
		//mUserName.setText(app_preferences.getString("twitter_username", "ABC"));
		//mPassword.setText(app_preferences.getString("twitter_username", "123"));
        			
		// Add a click listener for the Authenticate button
		mButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//TextView mUserName;
				//TextView mPassword;
				
				//mUserName = (TextView) findViewById(R.id.usernameTxt);
				//mPassword = (TextView) findViewById(R.id.passwordTxt);
				
				//mUserName.setText(getResources().getString(R.string.gUserName));
				//mPassword.setText(getResources().getString(R.string.gPassword));
				
				//String strUserName = mUserName.getText().toString();
				//String strPassword = mPassword.getText().toString();			
				TwitterClient client = new TwitterClient();
				// Using strings
				client.setTwitteruser(mUserName.getText().toString());
				client.setTwitterpwd(mPassword.getText().toString());				
				
				// Receive XML response from Twitter API
				String result = client.getFriendsTimeline();
				
				// Output a success or failure message to the screen?
				//mText.setText(result);
				
				// Parse the returned XML file pulling out the name and text elements
				// and placing these data to the TextView
				
//				// Define a list to hold the Twitter updates
//				List<Map<String, String>> twitterUpdates = new ArrayList<Map<String, String>>();
//				
//				// Do the SAX parsing
//				try
//				{
//				/* Get a SAXParser from the SAXPArserFactory. */ 
//		        SAXParserFactory spf = SAXParserFactory.newInstance(); 
//		        SAXParser sp = spf.newSAXParser(); 
//
//		        /* Get the XMLReader of the SAXParser we created. */ 
//		        XMLReader xr = sp.getXMLReader(); 
//		        /* Create a new ContentHandler and apply it to the XML-Reader*/ 
//		        TwitterUpdatesHandler handler = new TwitterUpdatesHandler(); 
//		        xr.setContentHandler(handler); 
//		         
//		        /* Parse the xml-data from our URL. */ 
//		        xr.parse(result); 
//		        
//		        twitterUpdates = handler.getParsedData();
//		      
//		        /* Parsing has finished. */
//				}
//				catch (MalformedURLException e)
//				{
//					e.printStackTrace();
//				}
//				catch (IOException e)
//				{
//					e.printStackTrace();
//				}
//				catch (ParserConfigurationException e)
//				{
//					e.printStackTrace();
//				}
//				catch (SAXException e)
//				{
//					e.printStackTrace();
//				}
//		          
//				String result2 = twitterUpdates.toString();
				
				String strUpdate = "";
				String strName = "";
				String strFull = "";
				
				// Loop through and parse he XML document	
				//while (result.indexOf("</text>") > 0)
				for (int i = 0; i < 6;i++)
				{				
				// Get the next text element
				result = result.substring(result.indexOf("<text>"), result.length() - result.indexOf("<text>")-1);
				result = result.substring(6, result.length() - 6);
				strUpdate = result.substring(0, result.indexOf("</text>"));
				// Get the next name element
				result = result.substring(result.indexOf("<screen_name>"), result.length() - result.indexOf("<text>")-1);
				result = result.substring(13, result.length() - 14);
				strName = result.substring(0, result.indexOf("</screen_name>"));
     			//Remove the name element
				//result = result.substring(result.indexOf("</name>") + 7, result.length() - result.indexOf("</name>") - 8);
				// Build a string of name/text pairs
				strFull = strFull + strName + "   " + strUpdate + "  " + "\n\r\n\r";
				
				}
				// Display parsed screen_name and text elements
			   mText.setText(strFull);
				
				}
		});
			// Add a click listener for the Update button
			mUpdateButton.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{
					//TextView mUserName;
					//TextView mPassword;
					//TextView mTweetText;
					
					//mUserName = (TextView) findViewById(R.id.usernameTxt);
					//mPassword = (TextView) findViewById(R.id.passwordTxt);
					mTweetText = (TextView) findViewById(R.id.tweetTxt);
					
					TwitterClient client = new TwitterClient();
					client.setTwitteruser(mUserName.getText().toString());
					client.setTwitterpwd(mPassword.getText().toString());
					
					String result = client.postStateToTwitter(mTweetText.getText().toString());
					
					// If update succesful, display it
					//if (result == "Status:OK")
					//{
						result = "Tweet was successful.";
					//}
										
					//String result = client.getFriendsTimeline();
					
					// Send resulting XML to the TewtView on the screen
					mText.setText(result);
				}		
        	});
		
	}
    private class TwitterClient 
	{
		private String twitterhost = "www.twitter.com";
		private String twitteruser = null;
		private String twitterpwd = null;
		
		public String getTwitterpwd() {
			return twitterpwd;
		}

		public void setTwitterpwd(String twitterpwd) {
			this.twitterpwd = twitterpwd;
		}

		public String getTwitteruser() {
			return twitteruser;
		}

		public void setTwitteruser(String twitteruser) {
			this.twitteruser = twitteruser;
		}
		
		/**
		 * Executes an Authenticated HTTP method (Get or Post)
		 * @param method - the get or post to execute
		 * @throws NullPointerException
		 * @throws HttpException
		 * @throws IOException
		 */
		private void executeAuthenticatedMethod(HttpMethodBase method) throws NullPointerException, HttpException, IOException {
			if ((twitteruser == null)||(twitterpwd == null)) {
				throw new RuntimeException("User and/or password has not been initialized!");
			}		
			HttpClient client = new HttpClient();
			Credentials credentials = new UsernamePasswordCredentials(this.getTwitteruser(), this.getTwitterpwd());
			client.getState().setCredentials(new AuthScope(twitterhost, 80, AuthScope.ANY_REALM), credentials);
			HostConfiguration host = client.getHostConfiguration();
			//host.setHost("https://"+twitterhost);
			host.setHost(twitterhost);
			client.executeMethod(host, method);		
		}
		
		/**
		 * Gets the 20 most recent statuses by friends (equivalent to /home on the web)
		 * @return a String representing the XML response.
		 */
		private String getFriendsTimeline() {
			String message = "";		
			String url = "/statuses/friends_timeline.xml";
			GetMethod get = new GetMethod(url);
		    try {
		    	executeAuthenticatedMethod(get);
				message = message + get.getResponseBodyAsString();
			} catch (URIException e) {
				message = e.getMessage();			
			} catch (NullPointerException e) {
				message = e.getMessage();
			} catch (IOException e) {
				message = e.getMessage();
			} finally {
				get.releaseConnection();
			}
			return message;
		}
		
		/**
		 * Update your Twitter Status
		 * @param state
		 * @return
		 */
		private String postStateToTwitter(String status) {
			String url = "/statuses/update.xml";
			String message = "";
			PostMethod post = new PostMethod(url);
			NameValuePair params[] = new NameValuePair[1];
			params[0] = new NameValuePair("status", status);
			post.setRequestBody(params);		
		    try {
		    	executeAuthenticatedMethod(post);
		    	message = "Status:"+post.getStatusText() + " " + message;
			} catch (URIException e) {
				message = e.getMessage();			
			} catch (NullPointerException e) {
				message = e.getMessage();
			} catch (IOException e) {
				message = e.getMessage();
			} finally {
				post.releaseConnection();
			}
			
			return message;
		}
		
	}
}