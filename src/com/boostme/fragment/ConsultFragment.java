package com.boostme.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.boostme.activity.ConsultDetailActivity;
import com.boostme.activity.R;
import com.boostme.adapter.ConsultListAdapter;
import com.boostme.bean.ConsultEntity;

public class ConsultFragment extends Fragment implements OnItemClickListener{

	private ListView mListView;
	private ArrayList<ConsultEntity> consultList;
	private ConsultListAdapter consultListAdapter;

	private ConsultPopupView popupViewHandler;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub

		consultList = TestDatas.getConsultDatas(getActivity());
		super.onCreate(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_consult, container,
				false);

		mListView = (ListView) rootView.findViewById(R.id.consultListView);

		if (consultList != null && consultList.size() != 0) {
			consultListAdapter = new ConsultListAdapter(getActivity(),
					consultList);
			mListView.setAdapter(consultListAdapter);
		}
		mListView.setOnItemClickListener(this);
		popupViewHandler = new ConsultPopupView(getActivity(), rootView);
		popupViewHandler.init();

		return rootView;
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this.getActivity(), ConsultDetailActivity.class);
		ConsultEntity e = (ConsultEntity)parent.getItemAtPosition(position);
		Bundle bundle = new Bundle();
		bundle.putSerializable("consult", e);
		intent.putExtras(bundle);
		startActivity(intent);
	}

}
