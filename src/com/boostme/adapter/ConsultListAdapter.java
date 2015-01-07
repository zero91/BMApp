package com.boostme.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.boostme.activity.R;
import com.boostme.bean.ConsultEntity;

public class ConsultListAdapter extends BaseAdapter {

	private Activity activity;
	private LayoutInflater inflater;
	private ArrayList<ConsultEntity> consultList;

	public ConsultListAdapter(Activity activity, ArrayList<ConsultEntity> list) {

		this.activity = activity;
		consultList = list;
		inflater = LayoutInflater.from(this.activity);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return consultList == null ? 0 : consultList.size();
	}

	@Override
	public ConsultEntity getItem(int position) {
		// TODO Auto-generated method stub

		if (consultList != null && consultList.size() != 0)
			return consultList.get(position);

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
			view = inflater.inflate(R.layout.consult_list_item, parent, false);
			iHolder = new ViewHolder();
			iHolder.itemTitle = (TextView) view.findViewById(R.id.zx_title);
			iHolder.itemInfo = (TextView) view.findViewById(R.id.zx_info);
			iHolder.leftImage = (ImageView) view
					.findViewById(R.id.zx_left_image);

			view.setTag(iHolder);
		} else {
			iHolder = (ViewHolder) view.getTag();
		}
		ConsultEntity entity = getItem(position);
		iHolder.itemInfo.setText(entity.getInfo());
		iHolder.itemTitle.setText(entity.getTitle());
		iHolder.leftImage.setImageResource(R.drawable.konglang);
		return view;
	}

	static public class ViewHolder {

		TextView itemTitle;
		TextView itemInfo;
		ImageView leftImage;
	}

}
