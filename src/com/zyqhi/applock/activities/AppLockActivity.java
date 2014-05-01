package com.zyqhi.applock.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;

import com.zyqhi.applock.R;
import com.zyqhi.applock.adapters.ListViewCustomAdapter;
import com.zyqhi.applock.helpers.ApplicationItem;
import com.zyqhi.applock.lockpattern.ChooseLockPattern;
import com.zyqhi.applock.lockpattern.ChooseLockPatternExample;
import com.zyqhi.applock.lockpattern.ChooseLockPatternTutorial;
import com.zyqhi.applock.services.AppLockService;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.Toast;

public class AppLockActivity extends Activity implements OnClickListener {

	private ListView mApplicationList;
	private ListViewCustomAdapter mAdapter;
	private ArrayList<Object> mItemList;
	private ApplicationItem mApplicationItem;

	private ListViewCustomAdapter mLockedAdapter;
	private ArrayList<Object> mLockedItemList;
	
	private Button mCancelBtn;
	private Button mConfirmBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_lock);
		this.setTitle("Applications List");
		
		/*
		ProgressDialog mProgressDialog = ProgressDialog.show(
				AppLockActivity.this, "",
				getString(R.string.loading_application_list));
		*/
		mCancelBtn = (Button) findViewById(R.id.cancel_all_button);
		mConfirmBtn = (Button) findViewById(R.id.confirm_button);
		mCancelBtn.setOnClickListener(this);
		mConfirmBtn.setOnClickListener(this);
		
		prepareArrayLists();

		/*
		Collections.sort(mItemList, new Comparator<Object>() {

			@Override
			public int compare(Object appItem1, Object appItem2) {
				final ApplicationItem applicationItem1 = (ApplicationItem) appItem1;
				final ApplicationItem applicationItem2 = (ApplicationItem) appItem2;

				return applicationItem1.getTitle().compareToIgnoreCase(
						applicationItem2.getTitle());
			}

		});
		*/

		//remove apps appear more then once
		/*
		Set<Object> mItemSet = new LinkedHashSet<Object>(mItemList);

		mItemList.clear();
		mItemList.addAll(mItemSet);
		*/
		mApplicationList = (ListView) findViewById(R.id.application_list);
		mAdapter = new ListViewCustomAdapter(this, mItemList);
		// mAdapter.notifyDataSetChanged();
		mApplicationList.setAdapter(mAdapter);
		
		//mProgressDialog.dismiss();

	}

	private void prepareArrayLists() {
		mItemList = new ArrayList<Object>();

		PackageManager pm = this.getPackageManager();

		Intent i = new Intent(Intent.ACTION_MAIN);
		i.addCategory(Intent.CATEGORY_LAUNCHER);

		ArrayList<ResolveInfo> mList = (ArrayList<ResolveInfo>) pm
				.queryIntentActivities(i, PackageManager.PERMISSION_GRANTED);

		for (ResolveInfo resInfo : mList) {
			//add application item to list
			AddObjectToList(resInfo.activityInfo.applicationInfo.loadIcon(pm),
					resInfo.activityInfo.applicationInfo.loadLabel(pm)
							.toString(),
					resInfo.activityInfo.applicationInfo.packageName);
		}
	}

	private void AddObjectToList(Drawable image, String title,
			String packageName) {
		mApplicationItem = new ApplicationItem();
		mApplicationItem.setTitle(title);
		mApplicationItem.setPackageName(packageName);
		mApplicationItem.setImage(image);

		mItemList.add(mApplicationItem);
	}

	/**
	 * @Override public void onClick(View view) { switch (view.getId()) { case
	 *           R.id.button_settings:
	 * 
	 *           // Get all checked applications and send to next activity.
	 * 
	 *           Intent i = new Intent(this, AppLockSettingsActivity.class);
	 *           startActivity(i); } }
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.app_lock, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			//Intent i = new Intent(this, AppLockSettingsActivity.class);
			//startActivity(i);
			
			mLockedItemList = new ArrayList<Object>();
			
			int index;
			for (index = mItemList.size()/2; index < mItemList.size(); index++) {
				mLockedItemList.add(mItemList.get(index));
			}
			//mLockedItemList.add(mItemList.get(0));
			mLockedAdapter = new ListViewCustomAdapter(this, mLockedItemList);
			mApplicationList.setAdapter(mLockedAdapter);
			break;
		case R.id.action_lock_apps:
			mAdapter.setShowAllCheckBox(true);
			break;
		default:
			Toast.makeText(getApplicationContext(), "noting selected",
					Toast.LENGTH_SHORT).show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.confirm_button:
			mAdapter.removeCheckedItems();
			break;
		case R.id.cancel_all_button:
			mAdapter.setShowAllCheckBox(false);
			break;
		}
	}

}
