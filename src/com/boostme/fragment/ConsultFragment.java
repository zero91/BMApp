package com.boostme.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.boostme.activity.R;
import com.boostme.adapter.ConsultListAdapter;
import com.boostme.bean.ConsultEntity;

public class ConsultFragment extends Fragment{
	
	ListView mListView;
	ArrayList<ConsultEntity> consultList;
	ConsultListAdapter consultListAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub

		consultList = TestDatas.getConsultDatas(getActivity());

		super.onCreate(savedInstanceState);
	}


	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		
		View rootView = inflater.inflate(R.layout.fragment_consult, container, false);
		mListView = (ListView) rootView.findViewById(R.id.consultListView);
		
		if (consultList != null && consultList.size() != 0) {
			consultListAdapter = new ConsultListAdapter(getActivity(),
					consultList);
			mListView.setAdapter(consultListAdapter);
		}
		return rootView;
	}

}
