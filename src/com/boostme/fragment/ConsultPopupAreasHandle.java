package com.boostme.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Handler;

import com.boostme.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ConsultPopupAreasHandle {

	private Gson gson = new Gson();
	private Type type = new TypeToken<Map<String, Object>>() {
	}.getType();

	public static Map<String, Object> regionsMap;
	public static Map<String, Object> schoolsMap;
	public static Map<String, Object> deptsMap;
	public static Map<String, Object> majorsMap;

	// public SoftReference<Map<String, Object>> schoolsMap;
	// public SoftReference<Map<String, Object>> deptsMap;
	// public SoftReference<Map<String, Object>> majorsMap;

	private Context context;
	private Handler handler;

	public ConsultPopupAreasHandle(Context context, Handler handler) {
		this.context = context;
		this.handler = handler;
	}

	public void getRegionsMap() {
		if (regionsMap == null) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {

					try {
						InputStream is = context.getResources()
								.openRawResource(R.raw.region);
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(is), 1 * 1024 * 1024);
						String s = reader.readLine();
						reader.close();
						Map<String, Object> map = new HashMap<String, Object>();

						regionsMap = gson.fromJson(s, type);

						handler.sendEmptyMessage(0);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			t.start();
		}
	}

	public void getSchoolsMap() {

		if (schoolsMap == null) {

			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {

					try {
						InputStream is = context.getResources()
								.openRawResource(R.raw.school);
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(is), 1 * 1024 * 1024);
						String s = reader.readLine();
						reader.close();
						Map<String, Object> map = new HashMap<String, Object>();
						schoolsMap = gson.fromJson(s, type);
						handler.sendEmptyMessage(1);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			t.start();
		}
	}

	public void getDeptsMap() {

		if (deptsMap == null) {

			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {

					try {
						InputStream is = context.getResources()
								.openRawResource(R.raw.department);
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(is), 1 * 1024 * 1024);
						String s = reader.readLine();
						reader.close();
						Map<String, Object> map = new HashMap<String, Object>();
						deptsMap = gson.fromJson(s, type);
						handler.sendEmptyMessage(2);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			t.start();
		}
	}

	public void getMajorsMap() {

		if (majorsMap == null) {

			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {

					try {
						InputStream is = context.getResources()
								.openRawResource(R.raw.major);
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(is), 3 * 1024 * 1024);
						String s = reader.readLine();
						reader.close();
						Map<String, Object> map = new HashMap<String, Object>();
						majorsMap = gson.fromJson(s, type);
						handler.sendEmptyMessage(3);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			t.start();
		}
	}

}
