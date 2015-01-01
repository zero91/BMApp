package com.boostme.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.boostme.activity.R;

public class TestFragment extends Fragment{
	
	
	private String hello;
	private String defaulHello = "default value";
	
	public static TestFragment newInstance(String s){
		TestFragment newFragment = new TestFragment();
		
		Bundle bundle  = new Bundle();
		bundle.putString("hello", s);
		newFragment.setArguments(bundle);
		
		return newFragment;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)	{
		Log.d("TEST", "TestTragment ====== onCreateView");
		
		Bundle args = getArguments();
		hello = args != null ? args.getString("hello") : defaulHello;
		View view = inflater.inflate(R.layout.guide_2, null);
		
		TextView viewHello = (TextView)view.findViewById(R.id.tv);
		viewHello.setText(hello);
		return view;
	}
	

}
