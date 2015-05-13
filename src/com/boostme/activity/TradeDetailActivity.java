package com.boostme.activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.boostme.adapter.TradeDetailListAdapter;
import com.boostme.bean.ResponseInfoEntity;
import com.boostme.bean.TradeInfoEntity;
import com.boostme.constants.Constants;
import com.boostme.util.BmAsyncHttpResponseHandler;
import com.boostme.util.BmHttpClientUtil;
import com.boostme.util.Logs;
import com.boostme.view.SwipeListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TradeDetailActivity extends BMActivity {

	private SwipeListView mListView;
	private TextView allPriceTextView;
	private TextView buyTextView;
	private TextView tradeNoView;
	private TextView tradeStatusView;

	private ArrayList<TradeInfoEntity> list;
	private TradeDetailListAdapter mAdapter;

	private String tradeNo;
	private double allPrice = 0;
	private String preMoney = "¥";
	private int status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.trade_detail);
		tradeNo = getIntent().getStringExtra("tradeNo");

		mListView = (SwipeListView) findViewById(R.id.trade_detail_list);
		allPriceTextView = (TextView) findViewById(R.id.trade_detail_allprice);
		buyTextView = (TextView) findViewById(R.id.trade_detail_buy);

		buyTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(TradeDetailActivity.this, "pay pay",
						Toast.LENGTH_SHORT).show();
			}
		});

		tradeNoView = (TextView) findViewById(R.id.trade_detail_no);
		tradeStatusView = (TextView) findViewById(R.id.trade_detail_status);

		tradeNoView.setText(tradeNo);

		list = new ArrayList<TradeInfoEntity>();
		mAdapter = new TradeDetailListAdapter(this, list);
		mListView.setAdapter(mAdapter);
		getTradeDetailData();
	}

	@Override
	protected String getActivitiTitle() {
		// TODO Auto-generated method stub
		return "订单详情";
	}

	public void countAllPrice(List<TradeInfoEntity> tempList) {
		for (TradeInfoEntity item : tempList) {
			allPrice += item.getTargetPrices();
		}
		BigDecimal bd = new BigDecimal(allPrice).setScale(2,
				BigDecimal.ROUND_HALF_UP);
		allPrice = bd.doubleValue();

		allPriceTextView.setText(preMoney + allPrice + "");
	}

	public void setTradeStatus() {
		tradeStatusView.setText(Constants.TRADE_STATUS_LIST[status - 1]);
	}

	public void getTradeDetailData() {

		Map<String, String> params = new HashMap<String, String>();

		params.put("trade_no", tradeNo);

		BmHttpClientUtil.getInstance(this).get("trade/ajax_fetch_info", params,
				new BmAsyncHttpResponseHandler(this) {

					@Override
					public void onSuccessOper(int statusCode, Header[] headers,
							byte[] response) {
						// TODO Auto-generated method stub
						try {

							String result = new String(response, "utf-8");
							Logs.logd(result, "Bm trade info");
							JSONObject json = new JSONObject(result);
							ResponseInfoEntity responseInfo = ResponseInfoEntity
									.parse(json);
							if (responseInfo.isSuccess()) {

								status = json.getInt("trade_status");

								List<TradeInfoEntity> tempList = (new Gson()).fromJson(
										json.getString("trade_info_list"),
										new TypeToken<ArrayList<TradeInfoEntity>>() {
										}.getType());

								countAllPrice(tempList);
								setTradeStatus();
								
								list.clear();
								list.addAll(tempList);
								mAdapter.notifyDataSetChanged();

							}

						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				});
	}

}
