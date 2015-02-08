package com.boostme.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.boostme.activity.R;
import com.squareup.picasso.Picasso;

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

	public static void setImageFromNet(Context context, String url, ImageView iv) 
	{
		try {
			Picasso.with(context).load(url).placeholder(R.drawable.portrait_holder).error(R.drawable.error_portrait).into(iv);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
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
