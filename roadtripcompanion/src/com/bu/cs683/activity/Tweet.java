package com.bu.cs683.activity;
//package org.apache.twitter;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.bu.cs683.persistence.RoadTripCompanion;
import com.bu.cs683.persistence.RoadTripCompanion.Trip;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.webkit.WebView;
import android.widget.EditText;
//import org.apache.commons.codec.binary.Base64;
//import android.net.http.RequestQueue;
import android.net.*;



public class Tweet extends Activity
{
//	static final int GET_LOGIN_INFORMATION = 1;
	private static final String TAG = "Tweet";

//    WebView webView;
//    //RequestQueue requestQueue;
//    String authInfo;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Log.v(TAG, "#### onCreate()");
		setContentView(R.layout.tweet);
	}
}
	



