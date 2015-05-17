package com.boostme.activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.boostme.adapter.TradeDetailListAdapter;
import com.boostme.adapter.TradeDetailListAdapter.OnMyDetailClickListener;
import com.boostme.bean.ResponseInfoEntity;
import com.boostme.bean.TradeInfoEntity;
import com.boostme.constants.Constants;
import com.boostme.util.BmAsyncHttpResponseHandler;
import com.boostme.util.BmHttpClientUtil;
import com.boostme.util.Logs;
import com.boostme.view.SwipeListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TradeDetailActivity extends BMActivity implements
		OnMyDetailClickListener {

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
		mAdapter.setOnMyDetailClickListener(this);

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

		allPriceTextView.setText(preMoney + allPrice);
	}

	public void updateAllPriceByChangeBuyNum(TradeInfoEntity entity,
			int newQuantity) {
		int preNum = entity.getBuyNum();
		double price = entity.getTargetPrice();
		allPrice = allPrice + (newQuantity - preNum) * price;

		BigDecimal bd = new BigDecimal(allPrice).setScale(2,
				BigDecimal.ROUND_HALF_UP);
		allPrice = bd.doubleValue();

		allPriceTextView.setText(preMoney + allPrice);
	}

	public void updateAllPriceByRemove(TradeInfoEntity entity) {
		double d = entity.getTargetPrices();
		allPrice -= d;

		BigDecimal bd = new BigDecimal(allPrice).setScale(2,
				BigDecimal.ROUND_HALF_UP);
		allPrice = bd.doubleValue();

		allPriceTextView.setText(preMoney + allPrice);
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

	public void updateTradeQuantity(int position, final int newQuantity,
			final View root) {

		Map<String, String> params = new HashMap<String, String>();
		final TradeInfoEntity entity = list.get(position);

		params.put("trade_no", tradeNo);
		params.put("target_id", entity.getTargetId());
		params.put("type", entity.getType() + "");
		params.put("quantity", newQuantity + "");

		BmHttpClientUtil.getInstance(this).get("trade/ajax_update_quantity",
				params, new BmAsyncHttpResponseHandler(this) {

					@Override
					public void onSuccessOper(int statusCode, Header[] headers,
							byte[] response) {
						// TODO Auto-generated method stub
						try {

							String result = new String(response, "utf-8");
							Logs.logd(result, "Bm trade updateQuantity");
							JSONObject json = new JSONObject(result);
							ResponseInfoEntity responseInfo = ResponseInfoEntity
									.parse(json);
							if (responseInfo.isSuccess()) {

								updateAllPriceByChangeBuyNum(entity,
										newQuantity);
								entity.setBuyNum(newQuantity);

								TradeDetailListAdapter.ViewHolder iHolder = (TradeDetailListAdapter.ViewHolder) root
										.getTag();

								iHolder.numView.setText(newQuantity + "");
								iHolder.totalPriceView.setText(preMoney
										+ entity.getTargetPrices());

							} else {
								String s = "";
								switch (responseInfo.getError()) {
								case 101:
									s = "用户尚未登录";
									break;
								case 102:
									s = "无效参数";
									break;
								case 103:
									s = "用户无权操作";
									break;
								case 104:
									s = "更新失败,请稍后再试";
									break;
								}
								Toast.makeText(TradeDetailActivity.this, s,
										Toast.LENGTH_SHORT).show();
							}

						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				});
	}

	@Override
	public void onNumChangeClickListener(View v, int position, View root) {
		// TODO Auto-generated method stub
		int preNum = list.get(position).getBuyNum();
		switch (v.getId()) {
		case R.id.trade_detail_item_num_add:
			int newQuantity = preNum + 1;
			updateTradeQuantity(position, newQuantity, root);

			break;
		case R.id.trade_detail_item_num_reduce:
			int newQuantityD = preNum - 1;
			if (newQuantityD <= 0)
				Toast.makeText(TradeDetailActivity.this, "订单数量不能小于1",
						Toast.LENGTH_SHORT).show();
			else
				updateTradeQuantity(position, newQuantityD, root);

			break;

		default:
			break;

		}
	}

	public void removeTradeItem(final int position, final View v) {

		Map<String, String> params = new HashMap<String, String>();
		final TradeInfoEntity entity = list.get(position);

		params.put("trade_no", tradeNo);
		params.put("target_id", entity.getTargetId());
		params.put("type", entity.getType() + "");

		BmHttpClientUtil.getInstance(this).get("trade/ajax_remove_item",
				params, new BmAsyncHttpResponseHandler(this) {

					@Override
					public void onSuccessOper(int statusCode, Header[] headers,
							byte[] response) {
						// TODO Auto-generated method stub
						try {

							String result = new String(response, "utf-8");
							Logs.logd(result, "Bm trade remove item");
							JSONObject json = new JSONObject(result);
							ResponseInfoEntity responseInfo = ResponseInfoEntity
									.parse(json);
							if (responseInfo.isSuccess()) {

								mListView.hiddenRight((View) v.getParent(),
										true);
								TradeInfoEntity entity = list.remove(position);
								mAdapter.notifyDataSetChanged();

								updateAllPriceByRemove(entity);

							} else {
								String s = "";
								switch (responseInfo.getError()) {
								case 101:
									s = "用户尚未登录";
									break;
								case 102:
									s = "无效参数";
									break;
								case 103:
									s = "用户无权删除该订单物品";
									break;
								case 104:
									s = "删除失败,请稍后再试";
									break;
								}
								Toast.makeText(TradeDetailActivity.this, s,
										Toast.LENGTH_SHORT).show();
							}

						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				});
	}

	public void forwardAnotherActivity(int position, View v) {

		TradeInfoEntity entity = list.get(position);
		int type = entity.getType();
		String targetId = entity.getTargetId();
		
		mListView.hiddenRight((View) v.getParent(),
				true);
		
		if (type == 2) {
			Intent intent = new Intent(TradeDetailActivity.this,
					ConsultDetailActivity.class);
			intent.putExtra("serviceId", targetId);
			startActivity(intent);
		}

	}

	@Override
	public void onRightItemClickListener(View v, int position) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.sp_item_right_del:
			View parent = (View) v.getParent();
			removeTradeItem(position, parent);
			break;
		case R.id.sp_item_right_view:
			forwardAnotherActivity(position, (View)v.getParent());
			break;
		}

	}
}
