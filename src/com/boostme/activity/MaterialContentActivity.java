package com.boostme.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.boostme.fragment.AreaHandleInterface;
import com.boostme.fragment.AreaPopupView;
import com.boostme.view.XListView;
import com.boostme.view.XListView.IXListViewListener;

public class MaterialContentActivity extends BMActivity implements
		AreaHandleInterface, IXListViewListener {

	private String type;
	private XListView mListView;
	private AreaPopupView myPopupView;
	private Handler mHandler;
	private ProgressBar progressBar;

	private int pageNum = 1;
	protected String regionId = "", schoolId = "", deptId = "", majorId = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		type = type != null ? type : getIntent().getStringExtra("type");
		setContentView(R.layout.material_content);

		initView();
	}

	public void initView() {

		mListView = (XListView) findViewById(R.id.content_listview);
		progressBar = (ProgressBar) findViewById(R.id.content_list_progressbar);
		mHandler = new Handler();
		myPopupView = new AreaPopupView(this, getRootView(this), this);
		myPopupView.init();
		
	}

	private View getRootView(Activity context) {
		return ((ViewGroup) context.findViewById(android.R.id.content))
				.getChildAt(0);
	}

	@Override
	protected String getActivitiTitle() {
		// TODO Auto-generated method stub
		type = getIntent().getStringExtra("type");
		return type;

	}

	@Override
	public void getDataList() {
		// TODO Auto-generated method stub

		if (progressBar.getVisibility() == View.VISIBLE)
			progressBar.setVisibility(View.GONE);
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
		this.schoolId = "";
		this.deptId = "";
		this.majorId = "";
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
		this.deptId = "";
		this.majorId = "";
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
		this.majorId = "";
	}

	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub

	}

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");
	}

}
