package com.example.navigation;

import model.Hotel;
import model.Monument;

import com.example.activities.ActivityHotel;
import com.example.activities.ActivityMonument;
import com.example.activities.pager.ActivityHotelPager;
import com.example.navigation.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;

public class MyAlarmService extends Service {

	private NotificationManager mManager;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@SuppressWarnings({ "static-access", "deprecation" })
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);

		mManager = (NotificationManager) this.getApplicationContext()
				.getSystemService(
						this.getApplicationContext().NOTIFICATION_SERVICE);
		Intent intent1 = new Intent(this.getApplicationContext(),
				ActivityMonument.class);
		
		
		

		Notification notification = new Notification(R.drawable.ic_launcher,
				"Monument Detected!", System.currentTimeMillis());
		intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
				| Intent.FLAG_ACTIVITY_CLEAR_TOP);

		PendingIntent pendingNotificationIntent = PendingIntent.getActivity(
				this.getApplicationContext(), 0, intent1,
				PendingIntent.FLAG_UPDATE_CURRENT);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.setLatestEventInfo(this.getApplicationContext(),
				"EPI", "Get all information!",
				pendingNotificationIntent);
		Uri alarmSound = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		notification.sound = alarmSound;
		notification.defaults = Notification.DEFAULT_LIGHTS
				| Notification.DEFAULT_VIBRATE;

		mManager.notify(0, notification);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}