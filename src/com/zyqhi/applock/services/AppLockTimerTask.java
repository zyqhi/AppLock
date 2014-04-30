package com.zyqhi.applock.services;

import java.util.TimerTask;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AppLockTimerTask extends TimerTask {
	public static final String TAG = "AppLockTimerTask";
	private Context mContext;
	String testPackageName = "com.android.contacts";
	String testClassName = "com.android.contacts.activities.PeopleActivity";

	private ActivityManager mActivityManager;

	public AppLockTimerTask(Context context) {
		mContext = context;
		mActivityManager = (ActivityManager) context
				.getSystemService("activity");
		Toast.makeText(mContext, "TimerTask", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//获取启动的程序（Activity）
		ComponentName topActivity = mActivityManager.getRunningTasks(1).get(0).topActivity;
		String packageName = topActivity.getPackageName();
		String className = topActivity.getClassName();
		//for debug
		//Log.v(TAG, "packageName: " + packageName);
		System.out.println("packageName is: "+ packageName);
		//Log.v(TAG, "\nclassName: " + className);
		System.out.println("className is: " + className);

		//如果当前获取的activity是加了锁的，那么少年，此处岂可袖手旁观
		if (testPackageName.equals(packageName)
				&& testClassName.equals(className)) {
			Intent intent = new Intent();
			intent.setClassName("com.zyqhi.applock",
					"com.zyqhi.applock.lockpattern.ChooseLockPattern");
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(intent);	
		}
	}
}
