package com.boostme.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.boostme.activity.CommDetailActivity;
import com.boostme.activity.R;
import com.boostme.adapter.CommuListAdapter;
import com.boostme.bean.CommuEntity;
import com.boostme.view.XListView;
import com.boostme.view.XListView.IXListViewListener;

public class CommuFragment extends Fragment implements OnItemClickListener, IXListViewListener  {

	private XListView mListView;
	private Handler mHandler;
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
		mListView = (XListView) rootView.findViewById(R.id.commuListView);
		mHandler = new Handler();
		
		if (commuList != null && commuList.size() != 0) {
			commuListAdapter = new CommuListAdapter(getActivity(), commuList);
			
			mListView.setPullLoadEnable(true);
			mListView.setAdapter(commuListAdapter);
			mListView.setOnItemClickListener(this);
			mListView.setXListViewListener(this);
		}
		return rootView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		Intent intent = new Intent(this.getActivity(), CommDetailActivity.class);
		intent.putExtra("pubuser_id", ((CommuEntity) parent.getItemAtPosition(position)).getSerialversionuid());
		intent.putExtra("id", id);
		startActivity(intent);
	}
	
	private int start = 1;
	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				ArrayList<CommuEntity> list = TestDatas.getCommDatas(start, 10);
				start += 10;
				commuList.addAll(0, list);
				//commuListAdapter = new CommuListAdapter(getActivity(), commuList);
				//mListView.setAdapter(commuListAdapter);
				commuListAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				commuListAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}
	
	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");
	}
}
