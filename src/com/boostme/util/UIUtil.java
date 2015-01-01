package com.boostme.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class UIUtil
{
	public static void displaySoftInput(Context context, View view)
	{
		if (context == null || view == null) {
			return;
		}
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, 0);
	}

	public static void hideSoftInput(Context context, View view)
	{
		if (context == null || view == null) {
			return;
		}
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	public static void clearFocus(Activity activity)
	{
		try {
			View view = activity.getCurrentFocus();
			if (view != null) {
				view.clearFocus();
				hideSoftInput(activity, view);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void showToast(Activity activity, String msg)
	{
		Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
	}

	public static void showToast(Context context, String msg)
	{
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	public static void showToast(Context context, String msg, int duration)
	{
		Toast.makeText(context, msg, duration).show();
	}
}
