package com.bu.cs683.activity;

import java.io.IOException;
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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

import org.xml.sax.SAXException;
import java.net.URL; 

import javax.xml.parsers.SAXParser; 
import javax.xml.parsers.SAXParserFactory; 

import org.xml.sax.InputSource; 
import org.xml.sax.XMLReader; 


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
        
        //TextView mUserName;
		//TextView mPassword;
		
		mUserName = (TextView) findViewById(R.id.usernameTxt);
		mPassword = (TextView) findViewById(R.id.passwordTxt);
		
		mUserName.setText(getResources().getString(R.string.gUserName));
		mPassword.setText(getResources().getString(R.string.gPassword));
        
              
        mText = (TextView) findViewById(R.id.tweetTextViewView);
        mButton = (Button) findViewById(R.id.authenticateButton);
        mUpdateButton = (Button) findViewById(R.id.updateButton);
						
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
				
							
				TwitterClient client = new TwitterClient();
				client.setTwitteruser(mUserName.getText().toString());
				client.setTwitterpwd(mPassword.getText().toString());
				
								
				String result = client.getFriendsTimeline();
				
				// Output a success or failure message to the screen?
				mText.setText(result);
				
				// Parse the returned XML file pulling out the name and text elements
				// and placing these data to the TextView
				//String parsedResult;
				//parsedResult = ResultParser(result);
				//mText.setText(parsedResult);
				
//				Document doc = null;
//				try 
//				{
//				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//				DocumentBuilder db = dbf.newDocumentBuilder();
//				doc = db.parse(result);
//				}
//				 catch (IOException ioe) 
//				 {
//					 mText.setText("AAAA" + result);
//					 //nm.notifyWithText(0, �Invalid XML format!!�,
//					 //NotificationManager.LENGTH_SHORT, null);
//				 } 
//				 catch (ParserConfigurationException pce) 
//				 {
//					 mText.setText("BBBB" + result);
//					 //nm.notifyWithText(0, �Could not parse XML!�,
//					 //NotificationManager.LENGTH_SHORT, null);
//				 }
//				 catch (SAXException se) 
//				 {
//					 mText.setText("CCCC" + result);
//					 //nm.notifyWithText(0, �Could not parse XML!�,
//					 //NotificationManager.LENGTH_SHORT, null);
//				 }
//					 
//				String s1 = doc.getElementsByTagName("text").toString();
//				String s2 = doc.getElementsByTagName("name").toString();
//				String s3 = s1 + s2;
				
//				/* Create a new TextView to display the parsingresult later. */ 
//		          TextView tv = new TextView(this); 
//		          try { 
//		               /* Create a URL we want to load some xml-data from. */ 
//		               URL url = new URL("http://www.anddev.org/images/tut/basic/parsingxml/example.xml"); 
//
//		               /* Get a SAXParser from the SAXPArserFactory. */ 
//		               SAXParserFactory spf = SAXParserFactory.newInstance(); 
//		               SAXParser sp = spf.newSAXParser(); 
//
//		               /* Get the XMLReader of the SAXParser we created. */ 
//		               XMLReader xr = sp.getXMLReader(); 
//		               /* Create a new ContentHandler and apply it to the XML-Reader*/ 
//		               ExampleHandler myExampleHandler = new ExampleHandler(); 
//		               xr.setContentHandler(myExampleHandler); 
//		                
//		               /* Parse the xml-data from our URL. */ 
//		               xr.parse(new InputSource(url.openStream())); 
//		               /* Parsing has finished. */ 
//
//		               /* Our ExampleHandler now provides the parsed data to us. */ 
//		               ParsedExampleDataSet parsedExampleDataSet = 
//		                                             myExampleHandler.getParsedData(); 
//
//		               /* Set the result to be displayed in our GUI. */ 
//		               tv.setText(parsedExampleDataSet.toString()); 
//		                
//		          } catch (Exception e) { 
//		               /* Display any Error to the GUI. */ 
//		               tv.setText("Error: " + e.getMessage()); 
//		               //Log.e(MY_DEBUG_TAG, "WeatherQueryError", e); 
//		          } 
//
//				
				mText.setText(result);
				



			
							
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
					// Ultimately I want to parse out the updates
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