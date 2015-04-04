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
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;

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

public class QuestionDetailActivity extends  BMActivity implements OnItemClickListener
{
	private PinnedHeaderListView mListview;
	private QuestionDetailListAdapter mAdapter;
	private List<QuestionEntity> mSectionList;
	private List<AnswerEntity> mItemList;
	
	private ImageButton mSendBtn;
	private EditText mAnsEditText;
	
	private int mQid = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_detail);
		initialActionBar();
		
		mSendBtn = (ImageButton) this.findViewById(R.id.send_btn);
		mAnsEditText = (EditText) this.findViewById(R.id.ans_edit_box);
		mListview = (PinnedHeaderListView) this.findViewById(R.id.list_view);
		mQid = getIntent().getIntExtra("qid", -1);
		
		/*ArrayList<QuestionEntity> list = TestDatas.getCommDatas(0, 10);
		ArrayList<QuestionEntity> mSectionList = new ArrayList<QuestionEntity>();
		mSectionList.add(list.get(0));
		//mSectionList.add(list.get(1));
		
		ArrayList<QuestionEntity> mItemList = list;
		mItemList.remove(0);*/
		
		mSectionList = new ArrayList<QuestionEntity>();
		mItemList = new ArrayList<AnswerEntity>();
		mAdapter = new QuestionDetailListAdapter(this, mSectionList, mItemList);
		mListview.setAdapter(mAdapter);
		mListview.setPinHeaders(false); //控制要不要把section pin起来
		mListview.setOnItemClickListener(this);
		
		this.getQuestionDetail(mQid);
		this.getAnswerList(mQid);
		//UIUtil.hideSoftInput(this, this.findViewById(R.id.edit_box));
		
		mSendBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				String ans = mAnsEditText.getText().toString();
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
									QuestionDetailActivity.this.getAnswerList(QuestionDetailActivity.this.mQid);
									QuestionDetailActivity.this.mAnsEditText.setText("");
									UIUtil.hideSoftInput(QuestionDetailActivity.this, QuestionDetailActivity.this.findViewById(R.id.ans_edit_box));
								} else {
									UIUtil.showToast(QuestionDetailActivity.this, "回复失败，error:" + responseInfo.getError());
								}
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					});
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
						mItemList.addAll(answerList);
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
						mSectionList.add(question);
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

	protected String getActivitiTitle()
	{
		return "详情";
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
