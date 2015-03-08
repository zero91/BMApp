package com.boostme.activity;

import java.util.ArrayList;

import za.co.immedia.pinnedheaderlistview.PinnedHeaderListView;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.boostme.adapter.CommDetailListAdapter;
import com.boostme.bean.CommuEntity;
import com.boostme.fragment.TestDatas;

public class CommDetailActivity extends  Activity
{
	private PinnedHeaderListView mListview;
	private CommDetailListAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comm_detail);
		initialActionBar();
		
		mListview = (PinnedHeaderListView) this.findViewById(R.id.list_view);
		String qid = getIntent().getStringExtra("qid");
		
		ArrayList<CommuEntity> list = TestDatas.getCommDatas(0, 10);
		ArrayList<CommuEntity> sectionList = new ArrayList<CommuEntity>();
		sectionList.add(list.get(0));
		//sectionList.add(list.get(1));
		
		ArrayList<CommuEntity> itemList = list;
		itemList.remove(0);
		
		mAdapter = new CommDetailListAdapter(this, sectionList, itemList);
		mListview.setAdapter(mAdapter);
		mListview.setPinHeaders(false); //控制要不要把section pin起来
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
