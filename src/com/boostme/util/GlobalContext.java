package com.boostme.util;

import com.boostme.bean.UserEntity;

import android.app.Activity;
import android.app.Application;
import android.util.DisplayMetrics;
import android.view.Display;

public final class GlobalContext extends Application
{
	// singleton
	private static GlobalContext globalContext = null;

	private DisplayMetrics displayMetrics = null;
	private Activity activity = null;
	private UserEntity user = null;

	@Override
	public void onCreate()
	{
		super.onCreate();
		globalContext = this;
	}

	public static GlobalContext getInstance()
	{
		return globalContext;
	}

	public DisplayMetrics getDisplayMetrics()
	{
		if (displayMetrics != null) {
			return displayMetrics;
		} else {
			Activity a = getActivity();
			if (a != null) {
				Display display = getActivity().getWindowManager().getDefaultDisplay();
				DisplayMetrics metrics = new DisplayMetrics();
				display.getMetrics(metrics);
				this.displayMetrics = metrics;
				return metrics;
			} else {
				// default screen is 800x480
				DisplayMetrics metrics = new DisplayMetrics();
				metrics.widthPixels = 480;
				metrics.heightPixels = 800;
				return metrics;
			}
		}
	}

	public Activity getActivity()
	{
		return activity;
	}

	public void setActivity(Activity activity)
	{
		this.activity = activity;
	}
	
	public void setUser(UserEntity user)
	{
		this.user = user;
	}
	
	public UserEntity getUser()
	{
		return user;
	}
}
