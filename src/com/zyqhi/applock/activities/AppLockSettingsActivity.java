package com.zyqhi.applock.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.widget.Toast;

import com.zyqhi.applock.R;
import com.zyqhi.applock.lockpattern.ChooseLockPattern;
import com.zyqhi.applock.lockpattern.LockPatternUtils;
import com.zyqhi.applock.services.AppLockService;

public class AppLockSettingsActivity extends PreferenceActivity implements 
	OnPreferenceClickListener,
	OnPreferenceChangeListener {
	private final static String TAG = "AppLockSettingsActivity";
	private final static int DISABLE_LOCKPATTERN = 1;
	private final static int ENABLE_LOCKPATTERN = 2;
	private final static int CHANGE_LOCKPATTERN = 3;
	private LockPatternUtils mLockPatternUtils;
	
	private CheckBoxPreference mToogleLockPref;
	private Preference mSetPatternPref; 
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        
        mSetPatternPref = findPreference("set_pattern");
        mSetPatternPref.setOnPreferenceClickListener(this);
        mSetPatternPref.setOnPreferenceChangeListener(this);
        mToogleLockPref = (CheckBoxPreference) findPreference("lock_status");
        mToogleLockPref.setOnPreferenceClickListener(this);
        mToogleLockPref.setOnPreferenceChangeListener(this);
    }

	@Override
	public boolean onPreferenceClick(Preference preference) {
		// TODO Auto-generated method stub
		//Intent i = new Intent(this, ChooseLockPattern.class);
		//startActivity(i);
		
		/*
		startService(new Intent(this, AppLockService.class));
		return false;
		*/
		return true;
	}
	
	public boolean onPreferenceChange(Preference preference, Object objValue) {
		if (preference == mToogleLockPref) {
			//mToogleLockPref.getKey();
			if (objValue.equals(true)) {
				//Toast.makeText(getApplicationContext(), "Chage:Toogle " + "true", Toast.LENGTH_SHORT).show();
				//开启锁服务
				startService(new Intent(this, AppLockService.class));
			} else {
				//Toast.makeText(getApplicationContext(), "Chage:Toogle " + "false", Toast.LENGTH_SHORT).show();
				//关闭锁服务
				stopService(new Intent(this, AppLockService.class));
			}
			
		} else if (preference == mSetPatternPref) {
			Toast.makeText(getApplicationContext(), "Change:set pattern", Toast.LENGTH_SHORT).show();
		}
		return true;
	}
}
