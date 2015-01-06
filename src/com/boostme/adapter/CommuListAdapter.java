package com.boostme.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.boostme.activity.R;
import com.boostme.bean.CommuEntity;

public class CommuListAdapter extends BaseAdapter {

	private Activity activity;
	private LayoutInflater inflater;
	private ArrayList<CommuEntity> commuList;

	public CommuListAdapter(Activity activity, ArrayList<CommuEntity> list) {

		this.activity = activity;
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
		if(view == null){
			view = inflater.inflate(R.layout.consult_list_item, null);
			iHolder = new ViewHolder();
			iHolder.itemTitle = (TextView)view.findViewById(R.id.item_title);
			iHolder.itemInfo = (TextView)view.findViewById(R.id.item_info);
			
			view.setTag(iHolder);
		}
		else{
			iHolder = (ViewHolder)view.getTag();
		}
		CommuEntity entity = getItem(position);
		iHolder.itemInfo.setText(entity.getInfo());
		iHolder.itemTitle.setText(entity.getTitle());
		
		
		return view;
	}

	static public class ViewHolder {
		
		TextView itemTitle;
		TextView itemInfo;
	}

}
