package com.boostme.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.boostme.activity.ConsultDetailActivity;
import com.boostme.activity.R;
import com.boostme.adapter.ConsultListAdapter;
import com.boostme.bean.ConsultEntity;
import com.boostme.bean.ResponseInfoEntity;
import com.boostme.util.BmAsyncHttpResponseHandler;
import com.boostme.util.BmHttpClientUtil;
import com.boostme.util.Logs;
import com.boostme.view.XListView;
import com.boostme.view.XListView.IXListViewListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ConsultFragment extends Fragment implements OnItemClickListener,
		IXListViewListener {

	private XListView mListView;
	private ArrayList<ConsultEntity> consultList;
	private ConsultListAdapter consultListAdapter;

	private ConsultPopupView popupViewHandler;
	private Handler mHandler;
	private int pageNum = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub

		// consultList = TestDatas.getConsultDatas(getActivity());
		super.onCreate(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_consult, container,
				false);
		mHandler = new Handler();
		consultList = new ArrayList<ConsultEntity>();

		mListView = (XListView) rootView.findViewById(R.id.consultListView);
		mListView.setPullLoadEnable(true);

		consultListAdapter = new ConsultListAdapter(getActivity(), consultList);
		mListView.setAdapter(consultListAdapter);

		mListView.setOnItemClickListener(this);
		mListView.setXListViewListener(this);

		popupViewHandler = new ConsultPopupView(getActivity(), rootView);
		popupViewHandler.init();

		getConsultList(pageNum++, true);
		return rootView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this.getActivity(),
				ConsultDetailActivity.class);
		ConsultEntity e = (ConsultEntity) parent.getItemAtPosition(position);
		Bundle bundle = new Bundle();
		bundle.putSerializable("consult", e);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	public void getConsultList(int pageNum, final boolean appendToFirst) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("page", pageNum + "");

		BmHttpClientUtil.getInstance(getActivity()).get(
				"/service/ajax_fetch_list", params,
				new BmAsyncHttpResponseHandler(getActivity()) {

					@Override
					public void onSuccessOper(int statusCode, Header[] headers,
							byte[] response) {
						// TODO Auto-generated method stub
						try {

							String result = new String(response, "utf-8");
							Logs.logd(result, "Bm Consult");
							JSONObject json = new JSONObject(result);
							ResponseInfoEntity responseInfo = ResponseInfoEntity
									.parse(json);
							if (responseInfo.isSuccess()) {
								List<ConsultEntity> list = (new Gson()).fromJson(
										json.getString("service_list"),
										new TypeToken<ArrayList<ConsultEntity>>() {
										}.getType());

								if (appendToFirst) {
									consultList.clear();
									consultList.addAll(list);
								} else
									consultList.addAll(list);

								consultListAdapter.notifyDataSetChanged();

							}

						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				});

	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub

		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				getConsultList(1, true);
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
				getConsultList(pageNum++, false);
				onLoad();
			}
		}, 2000);

	}

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");
	}

}
