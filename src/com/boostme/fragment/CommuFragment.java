package com.boostme.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.boostme.activity.R;
import com.boostme.adapter.CommuListAdapter;
import com.boostme.bean.CommuEntity;

public class CommuFragment extends Fragment {

	ListView mListView;
	ArrayList<CommuEntity> commuList;
	CommuListAdapter commuListAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		Log.d("Test1", "CommuFragment on create");
		
		// TODO Auto-generated method stub
		commuList = TestDatas.getCommDatas();

		super.onCreate(savedInstanceState);
	}


	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Log.d("Test1", "CommuFragment onCreateView");
		
		View rootView = inflater.inflate(R.layout.fragment_commu, container, false);
		mListView = (ListView) rootView.findViewById(R.id.commuListView);
		
		if (commuList != null && commuList.size() != 0) {
			commuListAdapter = new CommuListAdapter(getActivity(),
					commuList);
			mListView.setAdapter(commuListAdapter);
		}
		return rootView;
	}

}
