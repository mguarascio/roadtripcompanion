package com.bu.cs683.activity;


import android.util.Log;
////import org.apache.commons.codec.binary.Base64;

import java.util.HashMap;
import java.util.Map;

/**
 * Thread to update the web view with tweets from friends
 */

public class MyRunnable implements Runnable 
{
    private TwitterClient twitterClient;

    public MyRunnable(TwitterClient twitterClient) {
        this.twitterClient = twitterClient;
    }

    public void run() {
        while (true) {
            if (twitterClient.authInfo != null) {
                Log.d("http", "Found authentication information");
                Map headers = new HashMap();
               //// headers.put("Authorization", "Basic " + new String(Base64.encodeBase64(twitterClient.authInfo.getBytes())));
                Log.d("http", "Authorization1: [" + twitterClient.authInfo + "]");
               //// Log.d("http", "Authorization2: " + "Basic " + new String(Base64.encodeBase64(twitterClient.authInfo.getBytes())));
               //// twitterClient.requestQueue.queueRequest(
               ////         "https://twitter.com/statuses/friends_timeline.xml",
               ////         "GET", headers, new MyEventHandler(twitterClient, twitterClient.webView), null, 0, false);
                Log.d("http", "Sent request. Waiting");
              ////  twitterClient.requestQueue.waitUntilComplete();
                Log.d("http", "Got response");
            }
            Log.d("http", "Going to sleep");
            try {
                Thread.sleep(60 * 1000);
            } catch (InterruptedException e) {
            }
            Log.d("http", "Awake now");
        }
    }
}


