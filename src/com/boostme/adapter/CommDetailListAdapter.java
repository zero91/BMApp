package com.boostme.adapter;

import java.util.List;

import za.co.immedia.pinnedheaderlistview.SectionedBaseAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boostme.activity.R;
import com.boostme.bean.CommuEntity;
import com.boostme.util.Logs;

public class CommDetailListAdapter extends SectionedBaseAdapter
{

	private List<CommuEntity> mSectionList;
	private List<CommuEntity> mItemList;
	private Context mContext;

	public CommDetailListAdapter(Context context, List<CommuEntity> sectionList, List<CommuEntity> itemList)
	{
		super();
		this.mSectionList = sectionList;
		this.mItemList = itemList;
		this.mContext = context;
	}

	@Override
	public Object getItem(int section, int position)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int section, int position)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSectionCount()
	{
		return mSectionList.size();
	}

	@Override
	public int getCountForSection(int section)
	{
		return mItemList.size();
	}

	@Override
	public View getItemView(int section, int position, View convertView, ViewGroup parent)
	{
		ViewHold hold=null;
		if (convertView==null) {
			hold=new ViewHold();
			convertView=LinearLayout.inflate(mContext, R.layout.comm_detail_reply_item, null);
			hold.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
			hold.tvPublishTime = (TextView) convertView.findViewById(R.id.tv_publish_time);
			hold.tvLikeNum = (TextView) convertView.findViewById(R.id.tv_likenum);
			convertView.setTag(hold);
		} else {
			hold=(ViewHold) convertView.getTag();
		}
		
		CommuEntity entity = mItemList.get(position);
		Logs.loge("position = " + position + ", mItemList.size() = " + mItemList.size() + " mItemList.get(position) = " + entity.toString());
		Logs.loge("hold = " + hold);
		hold.tvTitle.setText(entity.getPostContent());
		hold.tvPublishTime.setText(entity.getPostName() + "·" + entity.getPostTime() + "·");
		hold.tvLikeNum.setText(entity.getFavourNum() + "个赞");
		return convertView;
	}

	@Override
	public View getSectionHeaderView(int section, View convertView, ViewGroup parent)
	{
		ViewHold hold = null;
		if (convertView == null) {
			hold = new ViewHold();
			convertView = LinearLayout.inflate(mContext, R.layout.comm_detail_main_item, null);
			hold.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
			hold.tvPublishTime = (TextView) convertView.findViewById(R.id.tv_publish_time);
			hold.tvReplyNum = (TextView) convertView.findViewById(R.id.tv_reply_num);
			hold.tvLikeNum = (TextView) convertView.findViewById(R.id.tv_likenum);
			convertView.setTag(hold);
		} else {
			hold = (ViewHold) convertView.getTag();
		}
		
		CommuEntity entity = mSectionList.get(section);
		hold.tvTitle.setText(entity.getPostContent());
		hold.tvPublishTime.setText(entity.getPostName() + "·" + entity.getPostTime());
		hold.tvReplyNum.setText(entity.getReplyNum());
		hold.tvLikeNum.setText(entity.getFavourNum());
		return convertView;
	}
	
	class ViewHold
	{
		TextView tvPublishTime;
		TextView tvTitle;

		TextView tvReplyNum;
		TextView tvLikeNum;
	}
}
