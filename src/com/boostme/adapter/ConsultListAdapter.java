package com.boostme.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.boostme.activity.R;
import com.boostme.bean.ConsultEntity;
import com.boostme.constants.Constants;
import com.boostme.util.UIUtil;

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
			iHolder.itemPublisherName = (TextView) view
					.findViewById(R.id.zx_publisher_name);
			iHolder.itemPublishTime = (TextView) view
					.findViewById(R.id.zx_publish_time);
			iHolder.itemDesc = (TextView) view
					.findViewById(R.id.zx_description);
			iHolder.itemPrice = (TextView) view.findViewById(R.id.zx_price);
			iHolder.leftImage = (ImageView) view
					.findViewById(R.id.zx_left_image);

			view.setTag(iHolder);
		} else {
			iHolder = (ViewHolder) view.getTag();
		}
		ConsultEntity entity = getItem(position);
		iHolder.itemPublisherName.setText(entity.getPublisherName());
		iHolder.itemPublishTime.setText(entity.getFormatTime());
		iHolder.itemDesc.setText("服务内容: " + entity.getDescription());
		iHolder.itemService.setText("服务分类: " + entity.getServiceCategoty());
		iHolder.itemPrice.setText("价格: " + entity.getPrice());
		//设置头像
		UIUtil.setImageFromNet(activity, Constants.BASE_URL + entity.getHeadImageUrl(),
				iHolder.leftImage);

		return view;
	}

	static public class ViewHolder {

		TextView itemPublisherName;
		TextView itemPublishTime;
		TextView itemDesc;
		TextView itemService;
		TextView itemPrice;
		ImageView leftImage;
	}

}
