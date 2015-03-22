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

import com.boostme.activity.QuestionDetailActivity;
import com.boostme.activity.R;
import com.boostme.adapter.QuestionListAdapter;
import com.boostme.bean.QuestionEntity;
import com.boostme.bean.ResponseInfoEntity;
import com.boostme.util.BmAsyncHttpResponseHandler;
import com.boostme.util.BmHttpClientUtil;
import com.boostme.util.Logs;
import com.boostme.view.XListView;
import com.boostme.view.XListView.IXListViewListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class QuestionFragment extends Fragment implements OnItemClickListener, IXListViewListener  
{
	private XListView mListView;
	private Handler mHandler;
	private ArrayList<QuestionEntity> questionList;
	private QuestionListAdapter questionListAdapter;
	
	private int pageNum = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		Log.d("Test1", "CommuFragment on create");
		//questionList = TestDatas.getCommDatas();
		super.onCreate(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		Log.d("Test1", "CommuFragment onCreateView");
		
		View rootView = inflater.inflate(R.layout.fragment_commu, container, false);
		mListView = (XListView) rootView.findViewById(R.id.questionListView);
		mHandler = new Handler();
		
		questionList = new ArrayList<QuestionEntity>();
		questionListAdapter = new QuestionListAdapter(getActivity(), questionList);
		mListView.setPullLoadEnable(true);
		mListView.setAdapter(questionListAdapter);
		mListView.setOnItemClickListener(this);
		mListView.setXListViewListener(this);
		
		this.getCommunicationList(pageNum, true);
		return rootView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		Intent intent = new Intent(this.getActivity(), QuestionDetailActivity.class);
		intent.putExtra("qid", ((QuestionEntity) parent.getItemAtPosition(position)).getQid());
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
						List<QuestionEntity> list = (new Gson()).fromJson(json.getString("question_list"), 
								new TypeToken<ArrayList<QuestionEntity>>() {}.getType());
						if (appendToFirst) {
							questionList.addAll(0, list);
						} else {
							questionList.addAll(list);
						}
						questionListAdapter.notifyDataSetChanged();
						
						for (QuestionEntity entity: list) {
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
				/*ArrayList<CommuEntity> list = TestDatas.getCommDatas(pageNum, 10);
				pageNum += 10;
				questionList.addAll(0, list);*/
				questionListAdapter.notifyDataSetChanged();
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
				QuestionFragment.this.getCommunicationList(pageNum++, false);
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