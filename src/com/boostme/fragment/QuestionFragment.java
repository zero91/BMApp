package com.boostme.fragment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	public static final int MAX_REQ_NUM = 10;
	public static final String OLD_REQ_TYPE = "old";
	public static final String NEW_REQ_TYPE = "new";
	
	private Handler mHandler;
	private XListView mQuestionListView;
	private ArrayList<QuestionEntity> mQuestionList; //保证question按照发表时间，从大到小排序
	private QuestionListAdapter mQuestionListAdapter;
	
	private int mPageNum = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		Log.d("Test1", "CommuFragment on create");
		//mQuestionList = TestDatas.getCommDatas();
		super.onCreate(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		Log.d("Test1", "CommuFragment onCreateView");
		
		View rootView = inflater.inflate(R.layout.fragment_question, container, false);
		mQuestionListView = (XListView) rootView.findViewById(R.id.question_list_view);
		mHandler = new Handler();
		
		mQuestionList = new ArrayList<QuestionEntity>();
		mQuestionListAdapter = new QuestionListAdapter(getActivity(), mQuestionList);
		mQuestionListView.setPullLoadEnable(true);
		mQuestionListView.setAdapter(mQuestionListAdapter);
		mQuestionListView.setOnItemClickListener(this);
		mQuestionListView.setXListViewListener(this);
		
		this.getQuestionListByPage(mPageNum, true);
		return rootView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		Intent intent = new Intent(this.getActivity(), QuestionDetailActivity.class);
		//intent.putExtra("qid", ((QuestionEntity) parent.getItemAtPosition(position)).getQid());
		intent.putExtra("qid", (int)id);
		startActivity(intent);
	}
	
	private void getQuestionListByPage(int pageNum, final boolean appendToFirst)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("page", String.valueOf(pageNum)); // start from 1
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
						//Logs.logd("question_num = " + json.getInt("question_num"));
						//Logs.logd(json.getString("question_list"));
						List<QuestionEntity> list = (new Gson()).fromJson(json.getString("question_list"), 
								new TypeToken<ArrayList<QuestionEntity>>() {}.getType());
						
						//sortQuestionList(list, false);//按时间逆序
						if (appendToFirst) {
							mQuestionList.addAll(0, list);
						} else {
							mQuestionList.addAll(list);
						}
						mQuestionListAdapter.notifyDataSetChanged();
						//for (QuestionEntity entity: list) { Logs.logd(entity.toString()); }
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void getQuestionListByRefreshType(int reqNum, final String reqType, long updateTime)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("req_num", String.valueOf(reqNum));
		params.put("req_type", reqType);
		params.put("update_time", String.valueOf(updateTime));
		
		BmHttpClientUtil.getInstance(this.getActivity()).get("question/ajax_fetch_list_by_update_time", params, new BmAsyncHttpResponseHandler(this.getActivity())
		{
			@Override
			public void onSuccessOper(int statusCode, Header[] headers, byte[] response)
			{
				try {
					String result = new String(response, "utf-8");
					JSONObject json = new JSONObject(result);
					ResponseInfoEntity responseInfo = ResponseInfoEntity.parse(json);
					if (responseInfo.isSuccess()) {
						//Logs.logd("question_num = " + json.getInt("question_num"));
						//Logs.logd(json.getString("question_list"));
						List<QuestionEntity> list = (new Gson()).fromJson(json.getString("question_list"), 
								new TypeToken<ArrayList<QuestionEntity>>() {}.getType());
						
						if (reqType.equals(NEW_REQ_TYPE)) {
							mQuestionList.addAll(0, list);
						} else {
							mQuestionList.addAll(list);
						}
						mQuestionListAdapter.notifyDataSetChanged();
						//for (QuestionEntity entity: list) { Logs.logd(entity.toString()); }
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
				int size = QuestionFragment.this.mQuestionList.size();
				if (size > 0) {
					long updateTime = QuestionFragment.this.mQuestionList.get(0).getUpdateTime(); //最新的时间
					QuestionFragment.this.getQuestionListByRefreshType(MAX_REQ_NUM, NEW_REQ_TYPE, updateTime);
					Logs.logd("new, updateTime = " + updateTime);
				} else {
					mPageNum = 0;
					QuestionFragment.this.getQuestionListByPage(++mPageNum, false);
				}
				onLoad();
			}
		}, 2000);
	}

	@Override
	/**
	 * 加载更多
	 */
	public void onLoadMore() 
	{
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() 
			{
				int size = QuestionFragment.this.mQuestionList.size();
				if (size > 0) {
					long updateTime = QuestionFragment.this.mQuestionList.get(size-1).getUpdateTime(); //最旧的时间
					QuestionFragment.this.getQuestionListByRefreshType(MAX_REQ_NUM, OLD_REQ_TYPE, updateTime);
					Logs.logd("old, updateTime = " + updateTime);
				} else {
					mPageNum = 0;
					QuestionFragment.this.getQuestionListByPage(++mPageNum, false);
				}
				onLoad();
			}
		}, 2000);
	}
	
	private void onLoad() 
	{
		mQuestionListView.stopRefresh();
		mQuestionListView.stopLoadMore();
		mQuestionListView.setRefreshTime("刚刚");
	}
	
	private void sortQuestionList(List<QuestionEntity> list, boolean isAscSort)
	{
		final int type = isAscSort ? 1 : -1;
		
		Collections.sort(list, new Comparator<QuestionEntity>()
		{
			@Override
			public int compare(QuestionEntity lhs, QuestionEntity rhs)
			{
				if (lhs.getTime() < rhs.getTime()) {
					return -1 * type;
				} else if (lhs.getTime() == rhs.getTime()) {
					return 0;
				} else {
					return 1 * type;
				}
			}
		});
	}
}