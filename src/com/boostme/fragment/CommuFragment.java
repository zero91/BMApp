package com.boostme.fragment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

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
import com.boostme.bean.ResponseInfoEntity;
import com.boostme.util.BmAsyncHttpResponseHandler;
import com.boostme.util.BmHttpClientUtil;
import com.boostme.util.Logs;
import com.boostme.view.XListView;
import com.boostme.view.XListView.IXListViewListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CommuFragment extends Fragment implements OnItemClickListener, IXListViewListener  
{
	private XListView mListView;
	private Handler mHandler;
	private ArrayList<CommuEntity> commuList;
	private CommuListAdapter commuListAdapter;
	
	private int pageNum = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		Log.d("Test1", "CommuFragment on create");
		//commuList = TestDatas.getCommDatas();
		super.onCreate(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		Log.d("Test1", "CommuFragment onCreateView");
		
		View rootView = inflater.inflate(R.layout.fragment_commu, container, false);
		mListView = (XListView) rootView.findViewById(R.id.commuListView);
		mHandler = new Handler();
		
		commuList = new ArrayList<CommuEntity>();
		commuListAdapter = new CommuListAdapter(getActivity(), commuList);
		mListView.setPullLoadEnable(true);
		mListView.setAdapter(commuListAdapter);
		mListView.setOnItemClickListener(this);
		mListView.setXListViewListener(this);
		
		this.getCommunicationList(pageNum, true);
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
	
	private void getCommunicationList(int pageNum, final boolean appendToFirst)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("page", "" + pageNum); // start from 1
		BmHttpClientUtil.getInstance(this.getActivity()).get("question/ajax_fetch_list", params, new BmAsyncHttpResponseHandler(this.getActivity())
		{
			@Override
			public void onSuccessOper(int statusCode, Header[] headers, byte[] response)
			{
				try {
					String result = new String(response, "utf-8");
					JSONObject json = new JSONObject(result);
					ResponseInfoEntity responseInfo = ResponseInfoEntity.parse(json);
					if (responseInfo.isSuccess()) {
						Logs.logd("question_num = " + json.getInt("question_num"));
						Logs.logd(json.getString("question_list"));
						List<CommuEntity> list = (new Gson()).fromJson(json.getString("question_list"), 
								new TypeToken<ArrayList<CommuEntity>>() {}.getType());
						if (appendToFirst) {
							commuList.addAll(0, list);
						} else {
							commuList.addAll(list);
						}
						commuListAdapter.notifyDataSetChanged();
						
						for (CommuEntity entity: list) {
							Logs.logd(entity.toString());
						}
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	@Override
	public void onRefresh()
	{
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				ArrayList<CommuEntity> list = TestDatas.getCommDatas(pageNum, 10);
				pageNum += 10;
				commuList.addAll(0, list);
				commuListAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() 
	{
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				CommuFragment.this.getCommunicationList(pageNum++, false);
				onLoad();
			}
		}, 2000);
	}
	
	private void onLoad() 
	{
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");
	}
}