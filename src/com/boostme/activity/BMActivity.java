package com.boostme.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

public abstract class BMActivity extends Activity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		initialActionBar();
	}

	protected void initialActionBar()
	{
		ActionBar actionBar = getActionBar();
		//actionBar.setIcon(R.drawable.ic_back);
		actionBar.setTitle(getActivitiTitle());
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setHomeButtonEnabled(true);
		int padding = (int) getResources().getDimension(R.dimen.ab_title_padding);
		ImageView view = (ImageView) findViewById(android.R.id.home);
		view.setPadding(padding * 3, padding, padding * 2, padding);
		//view.setPadding(0, padding, padding * 2, padding);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	protected abstract String getActivitiTitle();
}
