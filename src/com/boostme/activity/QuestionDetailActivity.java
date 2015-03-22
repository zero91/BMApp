package com.boostme.activity;

import java.util.ArrayList;

import za.co.immedia.pinnedheaderlistview.PinnedHeaderListView;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.boostme.adapter.QuestionDetailListAdapter;
import com.boostme.bean.QuestionEntity;
import com.boostme.fragment.TestDatas;

public class QuestionDetailActivity extends  Activity
{
	private PinnedHeaderListView mListview;
	private QuestionDetailListAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_detail);
		initialActionBar();
		
		mListview = (PinnedHeaderListView) this.findViewById(R.id.list_view);
		String qid = getIntent().getStringExtra("qid");
		
		ArrayList<QuestionEntity> list = TestDatas.getCommDatas(0, 10);
		ArrayList<QuestionEntity> sectionList = new ArrayList<QuestionEntity>();
		sectionList.add(list.get(0));
		//sectionList.add(list.get(1));
		
		ArrayList<QuestionEntity> itemList = list;
		itemList.remove(0);
		
		mAdapter = new QuestionDetailListAdapter(this, sectionList, itemList);
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
