package com.boostme.adapter;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.boostme.activity.R;
import com.boostme.util.Logs;
import com.etsy.android.grid.util.DynamicHeightTextView;

public class MaterialGridAdapter extends BaseAdapter {

	private static final String TAG = "MaterialGridAdapter";

	private LayoutInflater mLayoutInflater;
	private Random mRandom;
	private ArrayList<Integer> mBackgroundColors;
	private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();
	private String[] datas;

	// private Context context;

	public MaterialGridAdapter(Context context, String[] types) {
		// this.context = context;
		mLayoutInflater = LayoutInflater.from(context);
		mRandom = new Random();
		mBackgroundColors = new ArrayList<Integer>();
		mBackgroundColors.add(R.color.orange_0);
		mBackgroundColors.add(R.color.green_0);
		mBackgroundColors.add(R.color.blue_0);
		mBackgroundColors.add(R.color.yellow_0);
		mBackgroundColors.add(R.color.grey_0);

		datas = types;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datas[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder vh;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.material_grid_item,
					parent, false);
			vh = new ViewHolder();
			vh.titleName = (DynamicHeightTextView) convertView
					.findViewById(R.id.zl_grid_item_name);

			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}

		double positionHeight = getPositionRatio(position);
		int backgroundIndex = position >= mBackgroundColors.size() ? position
				% mBackgroundColors.size() : position;

		convertView.setBackgroundResource(mBackgroundColors
				.get(backgroundIndex));

		Logs.logd("getView position = " + position + " h = " + positionHeight,
				TAG);

		vh.titleName.setHeightRatio(positionHeight);
		vh.titleName.setText(datas[position]);

		// convertView.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(final View v) {
		// Toast.makeText(context, "Button Clicked Position " + position +
		// datas[position], Toast.LENGTH_SHORT).show();
		// }
		// });

		return convertView;
	}

	private double getPositionRatio(int position) {
		double ratio = sPositionHeightRatios.get(position, 0.0);
		// if not yet done generate and stash the columns height
		// in our real world scenario this will be determined by
		// some match based on the known height and width of the image
		// and maybe a helpful way to get the column height!
		if (ratio == 0) {
			ratio = getRandomHeightRatio();
			sPositionHeightRatios.append(position, ratio);
		}
		return ratio;
	}

	private double getRandomHeightRatio() {
		return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5
													// the width
	}

	static class ViewHolder {
		DynamicHeightTextView titleName;
	}

}
