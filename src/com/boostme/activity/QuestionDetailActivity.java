package com.boostme.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import za.co.immedia.pinnedheaderlistview.PinnedHeaderListView;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.boostme.adapter.QuestionDetailListAdapter;
import com.boostme.bean.AnswerEntity;
import com.boostme.bean.QuestionEntity;
import com.boostme.bean.ResponseInfoEntity;
import com.boostme.fragment.TestDatas;
import com.boostme.util.BmAsyncHttpResponseHandler;
import com.boostme.util.BmHttpClientUtil;
import com.boostme.util.Logs;
import com.boostme.util.UIUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class QuestionDetailActivity extends  Activity
{
	private PinnedHeaderListView mListview;
	private QuestionDetailListAdapter mAdapter;
	List<QuestionEntity> sectionList;
	List<AnswerEntity> itemList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_detail);
		initialActionBar();
		
		mListview = (PinnedHeaderListView) this.findViewById(R.id.list_view);
		int qid = getIntent().getIntExtra("qid", -1);
		
		/*ArrayList<QuestionEntity> list = TestDatas.getCommDatas(0, 10);
		ArrayList<QuestionEntity> sectionList = new ArrayList<QuestionEntity>();
		sectionList.add(list.get(0));
		//sectionList.add(list.get(1));
		
		ArrayList<QuestionEntity> itemList = list;
		itemList.remove(0);*/
		
		sectionList = new ArrayList<QuestionEntity>();
		itemList = new ArrayList<AnswerEntity>();
		mAdapter = new QuestionDetailListAdapter(this, sectionList, itemList);
		mListview.setAdapter(mAdapter);
		mListview.setPinHeaders(false); //控制要不要把section pin起来
		
		this.getQuestionDetail(qid);
		this.getAnswerList(qid);
		UIUtil.hideSoftInput(this, this.findViewById(R.id.edit_box));
	}
	
	private void getAnswerList(final int qid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("qid", "" + qid); 
		BmHttpClientUtil.getInstance(this).get("answer/ajax_fetch_list", params, new BmAsyncHttpResponseHandler(this)
		{
			@Override
			public void onSuccessOper(int statusCode, Header[] headers, byte[] response)
			{
				try {
					String result = new String(response, "utf-8");
					JSONObject json = new JSONObject(result);
					ResponseInfoEntity responseInfo = ResponseInfoEntity.parse(json);
					if (responseInfo.isSuccess()) {
						List<AnswerEntity> answerList = (new Gson()).fromJson(json.getString("answer_list"), 
								new TypeToken<List<AnswerEntity>>() {}.getType());
						itemList.addAll(answerList);
						mAdapter.notifyDataSetChanged();
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void getQuestionDetail(final int qid)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("qid", "" + qid); 
		BmHttpClientUtil.getInstance(this).get("question/ajax_fetch_info", params, new BmAsyncHttpResponseHandler(this)
		{
			@Override
			public void onSuccessOper(int statusCode, Header[] headers, byte[] response)
			{
				try {
					String result = new String(response, "utf-8");
					JSONObject json = new JSONObject(result);
					ResponseInfoEntity responseInfo = ResponseInfoEntity.parse(json);
					if (responseInfo.isSuccess()) {
						QuestionEntity question = (new Gson()).fromJson(json.getString("question"), 
								new TypeToken<QuestionEntity>() {}.getType());
						Logs.logd("qid = " + qid + ", entity = " + question);
						sectionList.add(question);
						mAdapter.notifyDataSetChanged();
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void initialActionBar()
	{
		ActionBar actionBar = getActionBar();
		actionBar.setIcon(R.drawable.ic_back);
		actionBar.setTitle("详情");
		//actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		int padding = (int) getResources().getDimension(R.dimen.ab_title_padding);
		ImageView view = (ImageView) findViewById(android.R.id.home);
		view.setPadding(padding * 3, padding, padding * 2, padding);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
