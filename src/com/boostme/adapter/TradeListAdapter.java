package com.boostme.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.boostme.activity.R;
import com.boostme.constants.Constants;

public class TradeListAdapter extends BaseAdapter {

	private Context activity;
	private LayoutInflater inflater;
	private ArrayList<Map<String, Object>> list;
	private String preMoney = "¥";
	private String preContent[] = { "[资料]", "[服务]" };

	public TradeListAdapter(Context activity,
			ArrayList<Map<String, Object>> list) {

		this.activity = activity;
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
		// TODO Auto-generated method stub
		ViewHolder iHolder;
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.trade_content_list_item, parent,
					false);
			iHolder = new ViewHolder();
			iHolder.tradeNoView = (TextView) view.findViewById(R.id.trade_no);
			iHolder.tradeContentView = (TextView) view
					.findViewById(R.id.trade_contents);
			iHolder.tradePriceView = (TextView) view
					.findViewById(R.id.trade_price);
			iHolder.tradeStatusView = (TextView) view
					.findViewById(R.id.trade_status);
			iHolder.tradeTimeView = (TextView) view
					.findViewById(R.id.trade_time);

			iHolder.tradePayBtn = (Button) view
					.findViewById(R.id.trade_paybutton);

			iHolder.tradePayBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Toast.makeText(activity, "Pay", Toast.LENGTH_SHORT).show();

				}
			});

			view.setTag(iHolder);
		} else {
			iHolder = (ViewHolder) view.getTag();
		}

		// /填数据
		setDatas(list.get(position), iHolder);

		return view;
	}

	public void setDatas(Map<String, Object> item, ViewHolder iHolder) {

		iHolder.tradeNoView.setText((String) item.get("trade_no"));
		iHolder.tradePriceView.setText(preMoney + item.get("tot_price"));

		int index = Integer.parseInt((String) item.get("status"));
		if (index == 1)
			iHolder.tradePayBtn.setVisibility(View.VISIBLE);
		else
			iHolder.tradePayBtn.setVisibility(View.GONE);

		iHolder.tradeStatusView.setText(Constants.TRADE_STATUS_LIST[index - 1]);
		iHolder.tradeTimeView.setText((String) item.get("format_time"));
		StringBuilder content = new StringBuilder();
		List<Map<String, Object>> info = (ArrayList) item.get("trade_info");
		for (Map<String, Object> map : info) {
			if (map.get("type").equals("2")) {
				content.append(preContent[1]);
				content.append(((Map) map.get("target_info"))
						.get("service_content") + "\n");
			} else {
				content.append(preContent[0]);
				content.append(((Map) map.get("target_info")).get("title")
						+ "\n");
			}
		}
		if (content.length() > 0) {
			String s = content.toString();
			iHolder.tradeContentView.setText(s.substring(0, s.length() - 2));
		}

	}

	static public class ViewHolder {

		TextView tradeNoView;
		TextView tradeContentView;
		TextView tradePriceView;
		TextView tradeStatusView;
		Button tradePayBtn;
		TextView tradeTimeView;
	}

}
