package com.zyqhi.applock.services;

import java.util.Timer;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class AppLockService extends Service {
	private Timer mTimer;
	public static final int FOREGROUND_ID = 0;

	private void startTimer() {
		if (mTimer == null) {
			mTimer = new Timer();
			AppLockTimerTask lockTask = new AppLockTimerTask(this);
			mTimer.schedule(lockTask, 0L, 1000L);
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void onCreate() {
		super.onCreate();
		Toast.makeText(getApplicationContext(), "service start", Toast.LENGTH_SHORT).show();
		startForeground(FOREGROUND_ID, new Notification());
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		startTimer();
		return super.onStartCommand(intent, flags, startId);
	}

	public void onDestroy() {
		stopForeground(true);
		mTimer.cancel();
		mTimer.purge();
		mTimer = null;
		super.onDestroy();
	}
}
