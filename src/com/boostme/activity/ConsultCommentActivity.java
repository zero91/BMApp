package com.boostme.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.boostme.adapter.ConsultCommentListAdapter;
import com.boostme.bean.ConsultCommentEntity;
import com.boostme.bean.ResponseInfoEntity;
import com.boostme.util.BmAsyncHttpResponseHandler;
import com.boostme.util.BmHttpClientUtil;
import com.boostme.util.Logs;
import com.boostme.view.EnhanceEditText;
import com.boostme.view.XListView;
import com.boostme.view.XListView.IXListViewListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ConsultCommentActivity extends BMActivity implements
		IXListViewListener {

	private XListView commentListView;
	private ConsultCommentListAdapter listAdapter;
	private ArrayList<ConsultCommentEntity> list;

	private Handler mHandler;

	private int pageNum = 1;
	private String serviceId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.consult_comment);

		initViews();
	}

	public void initViews() {


		commentListView = (XListView) findViewById(R.id.consult_comment_list);


		mHandler = new Handler();

		list = new ArrayList<ConsultCommentEntity>();
		listAdapter = new ConsultCommentListAdapter(this, list);
		commentListView.setAdapter(listAdapter);
		commentListView.setPullLoadEnable(true);
		commentListView.setXListViewListener(this);

		serviceId = getIntent().getStringExtra("serviceId");

		getConsultCommentList(false);
	}

	public void getConsultCommentList(final boolean appendToFirst) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("service_id", serviceId);
		params.put("page", pageNum + "");

		BmHttpClientUtil.getInstance(this).get("/service/ajax_fetch_comment",
				params, new BmAsyncHttpResponseHandler(this) {

					@Override
					public void onSuccessOper(int statusCode, Header[] headers,
							byte[] response) {
						// TODO Auto-generated method stub
						try {

							String result = new String(response, "utf-8");
							Logs.logd(result, "Bm Consult Comment");
							JSONObject json = new JSONObject(result);
							ResponseInfoEntity responseInfo = ResponseInfoEntity
									.parse(json);
							if (responseInfo.isSuccess()) {
								List<ConsultCommentEntity> tempList = (new Gson()).fromJson(
										json.getString("comment_list"),
										new TypeToken<ArrayList<ConsultCommentEntity>>() {
										}.getType());

								if (appendToFirst) {
									list.clear();
									list.addAll(tempList);
								} else
									list.addAll(tempList);

								listAdapter.notifyDataSetChanged();
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
	}


	@Override
	protected String getActivitiTitle() {
		// TODO Auto-generated method stub
		return "返回";
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				pageNum = 1;
				getConsultCommentList(true);
				onLoad();
			}

		}, 2000);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				pageNum++;
				getConsultCommentList(false);
				onLoad();
			}
		}, 2000);

	}

	private void onLoad() {
		commentListView.stopRefresh();
		commentListView.stopLoadMore();
		commentListView.setRefreshTime("刚刚");
	}

}
