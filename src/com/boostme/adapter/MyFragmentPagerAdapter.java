package com.boostme.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter{
	
	private ArrayList<Fragment>list;
	
	public MyFragmentPagerAdapter(FragmentManager fragmentManager, ArrayList<Fragment> fragmentList) {
		super(fragmentManager);
		// TODO Auto-generated constructor stub
		this.list = fragmentList;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		Log.d("TEST", "getItem: " + arg0);
		
		return list.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}
	
	

}
