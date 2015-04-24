package com.boostme.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.boostme.activity.R;
import com.boostme.bean.ConsultCommentEntity;
import com.boostme.util.UIUtil;

public class ConsultCommentListAdapter extends BaseAdapter {

	private Activity activity;
	private LayoutInflater inflater;
	private ArrayList<ConsultCommentEntity> consultCommentList;

	public ConsultCommentListAdapter(Activity activity,
			ArrayList<ConsultCommentEntity> list) {

		this.activity = activity;
		consultCommentList = list;
		inflater = LayoutInflater.from(activity);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return consultCommentList == null ? 0 : consultCommentList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (consultCommentList != null && consultCommentList.size() != 0)
			return consultCommentList.get(position);

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
			view = inflater.inflate(R.layout.consult_commentlist_item, parent,
					false);
			iHolder = new ViewHolder();
			iHolder.image = (ImageView) view
					.findViewById(R.id.zx_commentlist_item_image);
			iHolder.name = (TextView) view
					.findViewById(R.id.zx_commentlist_item_name);
			iHolder.time = (TextView) view
					.findViewById(R.id.zx_commentlist_item_time);
			iHolder.content = (TextView) view
					.findViewById(R.id.zx_commentlist_item_content);
			iHolder.ratingStar = (RatingBar) view
					.findViewById(R.id.zx_commentlist_item_ratingbar);
			iHolder.ratingScore = (TextView) view
					.findViewById(R.id.zx_commentlist_itme_ratingscore);

			view.setTag(iHolder);
		} else
			iHolder = (ViewHolder) view.getTag();

		ConsultCommentEntity entity = consultCommentList.get(position);
		iHolder.name.setText(entity.getAuthor());
		iHolder.time.setText(entity.getFormatTime());
		iHolder.content.setText(entity.getContent());
		iHolder.ratingStar.setRating(Float.valueOf(entity.getScore()));
		iHolder.ratingScore.setText(entity.getScore() + "分");

		UIUtil.setImageFromNet(activity, entity.getAvatarUrl(), iHolder.image);

		return view;
	}

	static public class ViewHolder {

		TextView name;
		TextView time;
		TextView content;
		RatingBar ratingStar;
		TextView ratingScore;
		ImageView image;
	}

}
