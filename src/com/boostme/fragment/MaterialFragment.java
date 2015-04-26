package com.boostme.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.boostme.activity.MaterialContentActivity;
import com.boostme.activity.R;
import com.boostme.adapter.MaterialGridAdapter;
import com.etsy.android.grid.StaggeredGridView;

public class MaterialFragment extends Fragment implements OnItemClickListener {

	private StaggeredGridView mGridView;
	private MaterialGridAdapter mAdapter;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_material, null);

		View header = inflater
				.inflate(R.layout.material_grid_item_header, null);

		TextView headerTitle = (TextView) header
				.findViewById(R.id.zl_grid_header);
		headerTitle.setText("选择你感兴趣的资料区");

		mGridView = (StaggeredGridView) rootView
				.findViewById(R.id.zl_staggeredgridview);
		mGridView.addHeaderView(header);

		String[] types = this.getActivity().getResources()
				.getStringArray(R.array.material_types);
		mAdapter = new MaterialGridAdapter(this.getActivity(), types);
		mGridView.setAdapter(mAdapter);

		mGridView.setOnItemClickListener(this);
		return rootView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

		Intent intent = new Intent(this.getActivity(), MaterialContentActivity.class);
		intent.putExtra("type", (String)parent.getItemAtPosition(position));
		startActivity(intent);
	}

}
