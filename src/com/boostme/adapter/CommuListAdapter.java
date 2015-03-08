package com.boostme.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.boostme.activity.R;
import com.boostme.bean.CommuEntity;
import com.boostme.util.TimeUtils;
import com.boostme.util.UIUtil;

public class CommuListAdapter extends BaseAdapter {

	// private Activity activity;
	private LayoutInflater inflater;
	private ArrayList<CommuEntity> commuList;
	private Context mContext;

	public CommuListAdapter(Activity activity, ArrayList<CommuEntity> list) {

		// this.activity = activity;
		mContext = activity.getApplicationContext();
		commuList = list;
		inflater = LayoutInflater.from(activity);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return commuList == null ? 0 : commuList.size();
	}

	@Override
	public CommuEntity getItem(int position) {
		// TODO Auto-generated method stub

		if (commuList != null && commuList.size() != 0)
			return commuList.get(position);

		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder iHolder;
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.commu_list_item, null);

			iHolder = new ViewHolder();
			iHolder.headIcon = (ImageView) view.findViewById(R.id.jl_head_icon);
			iHolder.postName = (TextView) view.findViewById(R.id.jl_post_name);
			iHolder.postTime = (TextView) view.findViewById(R.id.jl_post_time);
			iHolder.title = (TextView) view.findViewById(R.id.jl_title);
			iHolder.postContent = (TextView) view
					.findViewById(R.id.jl_post_content);
			iHolder.favourIcon = (ImageView) view
					.findViewById(R.id.jl_favour_icon);
			iHolder.favourNum = (TextView) view
					.findViewById(R.id.jl_favour_num);
			iHolder.replyIcon = (ImageView) view
					.findViewById(R.id.jl_reply_icon);
			iHolder.replyNum = (TextView) view.findViewById(R.id.jl_reply_num);
			iHolder.headIcon.setImageResource(R.drawable.konglang);

			view.setTag(iHolder);
		} else {
			iHolder = (ViewHolder) view.getTag();
		}
		CommuEntity entity = getItem(position);
		iHolder.postName.setText(entity.getPostName());
		iHolder.postTime.setText(TimeUtils.getDateDistanceToNowBefore(entity.getPostTime() * 1000, TimeUtils.MMDD_HHMM));
		iHolder.postContent.setText(entity.getPostContent());
		iHolder.favourNum.setText(entity.getFavourNum());
		iHolder.replyNum.setText(entity.getReplyNum());
		iHolder.title.setText(entity.getTitle().length() <= 12 ? entity.getTitle(): entity.getTitle().substring(0, 12));
		
		UIUtil.setImageFromNet(mContext, entity.getHeadIcon(), iHolder.headIcon);

		return view;
	}

	static public class ViewHolder {

		ImageView headIcon;
		TextView postName;
		TextView postTime;
		TextView title;

		TextView postContent;

		ImageView favourIcon;
		TextView favourNum;
		ImageView replyIcon;
		TextView replyNum;

	}

}
