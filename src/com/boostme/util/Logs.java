package com.boostme.util;

import com.boostme.constants.Constants;

import android.util.Log;

public class Logs
{

	public static void logd(Object from, String msg)
	{
		if (isDebug()) {
			Log.d(from.getClass().getName(), msg);
		}
	}

	public static void logi(Object from, String msg)
	{
		if (isDebug()) {
			Log.i(from.getClass().getName(), msg);
		}
	}

	public static void loge(Object from, String msg)
	{
		if (isDebug()) {
			Log.e(from.getClass().getName(), msg);
		}
	}

	public static void logd(String msg)
	{
		if (isDebug()) {
			Log.d("BoostMe", msg);
		}
	}

	public static void logi(String msg)
	{
		if (isDebug()) {
			Log.i("BoostMe", msg);
		}
	}

	public static void loge(String msg)
	{
		if (isDebug()) {
			Log.e("BoostMe", msg);
		}
	}

	private static boolean isDebug()
	{
		return Constants.IS_DEBUG;
	}
}
