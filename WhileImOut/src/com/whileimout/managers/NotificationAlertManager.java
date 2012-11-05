package com.whileimout.managers;


import zsmith.capstone.whileimout.R;
import zsmith.capstone.whileimout.WhileImOut;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;

public class NotificationAlertManager {

	public static void makeNotification(String contentTitle,
			String contentText, Context context, Intent intent) {
		PendingIntent activityToStartOnClick = PendingIntent.getActivity(
				context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		NotificationCompat.Builder notificationBuilder = new Builder(context);
		notificationBuilder
				.setContentIntent(activityToStartOnClick)
				.setContentTitle(contentTitle)
				.setContentText(contentText)
				.setWhen(System.currentTimeMillis())
				.setSmallIcon(R.drawable.ic_launcher)
				.setAutoCancel(true)
				.setDefaults(
						Notification.DEFAULT_LIGHTS
								| Notification.DEFAULT_SOUND
								| Notification.DEFAULT_VIBRATE);
		notificationManager.notify(WhileImOut.APP_ID,
				notificationBuilder.build());
	}

}
