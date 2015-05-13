package com.boostme.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.boostme.activity.R;
import com.boostme.bean.TradeInfoEntity;

public class TradeDetailListAdapter extends BaseAdapter implements
		OnClickListener {

	private LayoutInflater inflater;
	private ArrayList<TradeInfoEntity> list;
	private Context context;
	private String preMoney = "¥";

	public TradeDetailListAdapter(Context activity,
			ArrayList<TradeInfoEntity> list) {

		context = activity;
		inflater = LayoutInflater.from(activity);
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder iHolder;
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.trade_detail_item, parent, false);
			iHolder = new ViewHolder();
			iHolder.headerView = (ImageView) view
					.findViewById(R.id.trade_detail_item_image);
			iHolder.sellerNameView = (TextView) view
					.findViewById(R.id.trade_detail_item_name);
			iHolder.priceView = (TextView) view
					.findViewById(R.id.trade_detail_item_price);
			iHolder.contentView = (TextView) view
					.findViewById(R.id.trade_detail_item_content);
			iHolder.numView = (TextView) view
					.findViewById(R.id.trade_detail_item_num);
			iHolder.numAddView = (TextView) view
					.findViewById(R.id.trade_detail_item_num_add);
			iHolder.numReduceView = (TextView) view
					.findViewById(R.id.trade_detail_item_num_reduce);
			iHolder.totalPriceView = (TextView) view
					.findViewById(R.id.trade_detail_item_totalprice);

			iHolder.item_reight_del = (TextView) view
					.findViewById(R.id.sp_item_right_del);
			iHolder.item_reight_view = (TextView) view
					.findViewById(R.id.sp_item_right_view);

			iHolder.item_left = (LinearLayout) view
					.findViewById(R.id.trade_detail_item_left);
			iHolder.item_right = (LinearLayout) view
					.findViewById(R.id.sp_item_right);

			LinearLayout.LayoutParams lp1 = new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			iHolder.item_left.setLayoutParams(lp1);

			LinearLayout.LayoutParams lp2 = new LayoutParams(360,
					LayoutParams.MATCH_PARENT);
			iHolder.item_right.setLayoutParams(lp2);

			iHolder.numAddView.setOnClickListener(this);

			view.setTag(iHolder);
		} else {
			iHolder = (ViewHolder) view.getTag();
		}

		// 填数据
		TradeInfoEntity entity = list.get(position);
		iHolder.sellerNameView.setText(entity.getTargetName());
		iHolder.priceView.setText(preMoney + entity.getTargetPrice() + "");
		iHolder.contentView.setText(entity.getTargetContent());

		iHolder.numView.setText(entity.getBuyNum() + "");
		iHolder.totalPriceView
				.setText(preMoney + entity.getTargetPrices() + "");

		return view;
	}

	static public class ViewHolder {

		LinearLayout item_right;
		LinearLayout item_left;

		ImageView headerView;
		TextView sellerNameView;
		TextView priceView;
		TextView contentView;
		TextView numReduceView;
		TextView numView;
		TextView numAddView;
		TextView totalPriceView;

		TextView item_reight_del;
		TextView item_reight_view;

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.trade_detail_item_num_add:
			Toast.makeText(context, "add", Toast.LENGTH_SHORT).show();
			break;
		case R.id.trade_detail_item_num_reduce:
			Toast.makeText(context, "reduce", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;

		}

	}
}
