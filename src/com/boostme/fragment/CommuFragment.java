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
		commuList = new ArrayList<CommuEntity>();
		CommuEntity e1 = new CommuEntity();
		CommuEntity e2 = new CommuEntity();
		e1.setInfo("asdfasdf fsdadf 哈哈哈哈哈哈哈.");
		e1.setTitle("hell哦  我老人的 吗.");

		e2.setInfo("222asdfasdf fsdadf 哈哈哈哈哈哈哈.");
		e2.setTitle("@@@hell哦  我老人的 吗.");

		commuList.add(e2);
		commuList.add(e1);

		super.onCreate(savedInstanceState);
	}

//	/** 此方法意思为fragment是否可见 ,可见时候加载数据 */
//	@Override
//	public void setUserVisibleHint(boolean isVisibleToUser) {
//		
//		Log.d("Test1", "CommuFragment setUserVisibleHint " + isVisibleToUser);
//		
//		if (isVisibleToUser) {
//			// fragment可见时加载数据
//			if (commuList != null && commuList.size() != 0) {
//				commuListAdapter = new CommuListAdapter(getActivity(),
//						commuList);
//				mListView.setAdapter(commuListAdapter);
//			} else {
//				//Toast.makeText(getActivity(), "no data", Toast.LENGTH_SHORT).show();;
//			}
//		} else {
//			// fragment不可见时不执行操作
//		}
//		super.setUserVisibleHint(isVisibleToUser);
//	}

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
