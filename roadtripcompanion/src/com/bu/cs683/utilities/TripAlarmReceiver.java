package com.bu.cs683.utilities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bu.cs683.activity.R;

public class TripAlarmReceiver extends BroadcastReceiver
{
    
	@Override
	public void onReceive(Context context, Intent intent)
	{
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);
		
		int icon = R.drawable.car;
		CharSequence tickerText = "Upcoming RoadTrip";
		
		Notification notification = new Notification(icon, tickerText, System.currentTimeMillis());
		
		CharSequence contentTitle = "Upcoming RoadTrip";
		CharSequence contentText = "[Check your RoadTripCompanion]";
		
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
		notification.setLatestEventInfo(context, contentTitle, contentText, pendingIntent);
		
		final int HELLO_ID = 1;

		mNotificationManager.notify(HELLO_ID, notification);
	}

}
