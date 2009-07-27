package com.bu.cs683.activity;

import android.app.Activity;
import android.app.NotificationManager;
///import android.net.http.EventHandler;
//import android.net.http.Headers;
import android.net.http.SslCertificate;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.Iterator;
//used for passing data 
import android.os.Handler;
import android.os.Message;
// used for connectivity
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Handler to get the http status when we post a new tweet
 */

public class MyEventHandler2 ////implements EventHandler 
{
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    Activity activity;

    MyEventHandler2(Activity activity) {
        this.activity = activity;
    }

    public void status(int i, int i1, int i2, String s) {
        Log.d("http", "status [" + s + "]");
    }

    public void headers(Iterator iterator) {
    }

   //// public void headers(Headers headers) {
   //// }

    public void data(byte[] bytes, int len) {
        baos.write(bytes, 0, len);
    }

    public void endData() {
        String text = new String(baos.toByteArray());
        Log.d("http", "load data [" + text + "]");
    }

    public void error(int i, String s) {
        Log.d("http", "error [" + s + "]");
        NotificationManager nm = (NotificationManager)
                activity.getSystemService(Activity.NOTIFICATION_SERVICE);

     ////   nm.notifyWithText(100,
     ////           "error [" + s + "]",
     ////           NotificationManager.LENGTH_SHORT, null);
    }

    public void handleSslErrorRequest(int i, String s, SslCertificate sslCertificate) {
        Log.d("http", "ssl error [" + s + "]");
        NotificationManager nm = (NotificationManager)
                activity.getSystemService(Activity.NOTIFICATION_SERVICE);

        ////nm.notifyWithText(100,
        ////        "error [" + s + "]",
        ////        NotificationManager.LENGTH_SHORT, null);
    }

}
