package com.boostme.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences存储访问工具类
 * @author aeolus
 *
 */
public class SharedPreferencesUtil
{
	private static final String SPNAME = "boostme_sp";
	public static boolean IS_ENCRY_VALUE = true;
	
	public static final String IS_LOGIN = "is_login";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";

	public static void save(Context context, String key, String value)
	{
		SharedPreferences sp = context.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
		try {
			if (IS_ENCRY_VALUE) value = Base64Util.encode(value);
		} catch (Exception ex) {
			Logs.logd("Encryped Error~");
		}
		sp.edit().putString(key, value).commit();
	}

	public static String getString(Context context, String key)
	{
		SharedPreferences sp = context.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
		String value = sp.getString(key, "");
		if (value == "") return value;
		else {
			try {
				if (IS_ENCRY_VALUE) value = Base64Util.decode(value);
			} catch (Exception ex) {
				Logs.logd("Decrypted Error~");
			}
			return value;
		}
	}

	public static void save(Context context, String key, int value)
	{
		SharedPreferences sp = context.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
		sp.edit().putInt(key, value).commit();
	}

	public static void save(Context context, String key, boolean value)
	{
		SharedPreferences sp = context.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
		sp.edit().putBoolean(key, value).commit();
	}

	public static boolean getBoolean(Context context, String key)
	{
		SharedPreferences sp = context.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
		return sp.getBoolean(key, false);
	}

	public static int getInt(Context context, String key)
	{
		SharedPreferences sp = context.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
		return sp.getInt(key, 0);
	}

	public static int getInt(Context context, String key, int defaultValue)
	{
		SharedPreferences sp = context.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
		return sp.getInt(key, defaultValue);
	}
}
