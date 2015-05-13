package com.boostme.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.boostme.adapter.TradeListAdapter;
import com.boostme.bean.ResponseInfoEntity;
import com.boostme.util.BmAsyncHttpResponseHandler;
import com.boostme.util.BmHttpClientUtil;
import com.boostme.util.Logs;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TradeContentActivity extends BMActivity implements
		OnItemClickListener {

	private ListView mListView;
	private TradeListAdapter mAdapter;
	private ArrayList<Map<String, Object>> list;
	private int pageNum = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.trade_content);
		mListView = (ListView) findViewById(R.id.trade_content_list);

		list = new ArrayList<Map<String, Object>>();
		mAdapter = new TradeListAdapter(this, list);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
		getTradeListData(false);
	}

	public void getTradeListData(final boolean appendToFirst) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("page", pageNum + "");

		BmHttpClientUtil.getInstance(this).get("trade/ajax_fetch_list", params,
				new BmAsyncHttpResponseHandler(this) {

					@Override
					public void onSuccessOper(int statusCode, Header[] headers,
							byte[] response) {
						// TODO Auto-generated method stub
						try {

							String result = new String(response, "utf-8");
							Logs.logd(result, "Bm trade list");
							JSONObject json = new JSONObject(result);
							ResponseInfoEntity responseInfo = ResponseInfoEntity
									.parse(json);
							if (responseInfo.isSuccess()) {
								List<Map<String, Object>> tempList = (new Gson()).fromJson(
										json.getString("trade_list"),
										new TypeToken<ArrayList<Map<String, Object>>>() {
										}.getType());

								if (appendToFirst) {
									list.clear();
									list.addAll(tempList);
								} else
									list.addAll(tempList);

								mAdapter.notifyDataSetChanged();
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
		return "购买历史";
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

		String no = (String) ((Map) parent.getItemAtPosition(position))
				.get("trade_no");
		// Toast.makeText(this, no, Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, TradeDetailActivity.class);
		intent.putExtra("tradeNo", no);
		startActivity(intent);
	}

}
