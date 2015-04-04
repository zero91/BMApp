package com.boostme.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;

import com.boostme.util.SharedPreferencesUtil;

public class WelcomeActivity extends Activity 
{
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				checkLogin();
			}
			super.handleMessage(msg);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wel);
		mHandler.sendEmptyMessageDelayed(1, 1000);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.welcome, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void checkLogin() 
	{
		if (SharedPreferencesUtil.getBoolean(this, SharedPreferencesUtil.IS_LOGIN) == false) {
			startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
		} else {
			startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
		}
		finish();
		return;
	}
}
