package com.zyqhi.applock.helpers;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.zyqhi.applock.database.ProtectedApplicationsTable;

public class ApplicationItem {
	private String sTitle;
	private String sPackageName;
	private Drawable mImage;
	private Context mContext;
	private long mItemId;
	private boolean mShowCheckBox = false;
	private boolean mItemChecked = false;
	
	private final static String TAG = "ApplicationItem";
	
	public void setItemChecked(boolean itemChecked) {
		mItemChecked = itemChecked;
	}
	
	public boolean getItemChecked() {
		return mItemChecked;
	}
	
	public void setShowCheckBox(boolean showCheckBox) {
		mShowCheckBox = showCheckBox;
	}
	
	public boolean getShowCheckBox() {
		return mShowCheckBox;
	}
	
	public String getTitle() {
		return sTitle;
	}
	
	public void setTitle(String title) {
		sTitle = title;
	}
	
	public String getPackageName() {
		return sPackageName;
	}
	
	public void setPackageName(String packageName) {
		sPackageName = packageName;
	}
	
	public Drawable getImage() {
		return mImage;
	}
	
	public void setImage(Drawable image) {
		mImage = image;
	}
	
	public Context getContext() {
		return mContext;
	}
	
	public void setContext(Context c) {
		mContext = c;
	}
	
	public void addValueToDatabase() {
		ProtectedApplicationsTable mTable = new ProtectedApplicationsTable(mContext);
		mTable.open();
		mItemId = mTable.createRow(sTitle, sPackageName);
		mTable.close();
	}
	
	/**
	 * Default constructor
	 */
	public ApplicationItem() {
		
	}
	
	public ApplicationItem(Context context) {
		mContext = context;
	}
	
	public ApplicationItem(String title, String packageName, Drawable image, Context context) {
		sTitle = title;
		sPackageName = packageName;
		mImage = image;
		mContext = context;
	}
}
