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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.boostme.adapter.QuestionDetailListAdapter;
import com.boostme.bean.AnswerEntity;
import com.boostme.bean.QuestionEntity;
import com.boostme.bean.ResponseInfoEntity;
import com.boostme.util.BmAsyncHttpResponseHandler;
import com.boostme.util.BmHttpClientUtil;
import com.boostme.util.Logs;
import com.boostme.util.StringUtil;
import com.boostme.util.UIUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

public class QuestionDetailActivity extends  Activity implements OnItemClickListener
{
	private PinnedHeaderListView mListview;
	private QuestionDetailListAdapter mAdapter;
	private List<QuestionEntity> sectionList;
	private List<AnswerEntity> itemList;
	
	private ImageButton sendBtn;
	private EditText ansEditText;
	
	private int mQid = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_detail);
		initialActionBar();
		
		sendBtn = (ImageButton) this.findViewById(R.id.send_btn);
		ansEditText = (EditText) this.findViewById(R.id.ans_edit_box);
		mListview = (PinnedHeaderListView) this.findViewById(R.id.list_view);
		mQid = getIntent().getIntExtra("qid", -1);
		
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
		mListview.setOnItemClickListener(this);
		
		this.getQuestionDetail(mQid);
		this.getAnswerList(mQid);
		//UIUtil.hideSoftInput(this, this.findViewById(R.id.edit_box));
		
		sendBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				String ans = ansEditText.getText().toString();
				if (StringUtil.isBlank(ans)) {
					UIUtil.showToast(QuestionDetailActivity.this, "回复不能为空！");
				} else {
					RequestParams params = new RequestParams();
					params.put("qid", mQid);
					params.put("content", ans);
					BmHttpClientUtil.getInstance(QuestionDetailActivity.this).post("question/ajax_answer", params, new BmAsyncHttpResponseHandler(QuestionDetailActivity.this)
					{
						@Override
						public void onSuccessOper(int statusCode, Header[] headers, byte[] response)
						{
							try {
								String result = new String(response, "utf-8");
								JSONObject json = new JSONObject(result);
								ResponseInfoEntity responseInfo = ResponseInfoEntity.parse(json);
								if (responseInfo.isSuccess()) {
									UIUtil.showToast(QuestionDetailActivity.this, json.getString("aid"));
								} else {
									UIUtil.showToast(QuestionDetailActivity.this, "error:" + responseInfo.getError());
								}
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					});
					UIUtil.hideSoftInput(QuestionDetailActivity.this, QuestionDetailActivity.this.findViewById(R.id.ans_edit_box));
					UIUtil.showToast(QuestionDetailActivity.this, ans);
				}
			}
		});
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
						for (AnswerEntity ans: answerList) { Logs.logd("entity = " + ans);}
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		String ans = ((AnswerEntity)parent.getItemAtPosition(position)).getContent() + ", " +
				((AnswerEntity)parent.getItemAtPosition(position)).getQid();
		//((AnswerEntity)parent.getItemAtPosition(position)).getQid(); == id
		UIUtil.showToast(this, "id = " + id + "----" + ans);
	}
}
