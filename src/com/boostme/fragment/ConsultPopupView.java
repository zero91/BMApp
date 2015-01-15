package com.boostme.fragment;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ToggleButton;

import com.boostme.activity.R;

public class ConsultPopupView implements OnDismissListener, OnItemClickListener {

	private ToggleButton areaSelector, schoolSelector, facultySelector,
			majorSelector, targetSelector;

	private PopupWindow popupWindow;

	private ListView listViewSelector;

	private ArrayAdapter<String> listAdapterSelector;
	private ArrayList<String> listDatas = new ArrayList<String>();

	Context context;
	View rootView;

	public ConsultPopupView(Context context, View rootView) {

		this.context = context;
		this.rootView = rootView;
		init();

	}

	public void init() {

		LayoutInflater inflater = LayoutInflater.from(context);
		Resources res = context.getResources();
		areaSelector = (ToggleButton) rootView.findViewById(R.id.area_selector);
		schoolSelector = (ToggleButton) rootView
				.findViewById(R.id.school_selector);
		facultySelector = (ToggleButton) rootView
				.findViewById(R.id.faculty_selector);
		majorSelector = (ToggleButton) rootView
				.findViewById(R.id.major_selector);

		View popupView = inflater.inflate(R.layout.popup_list, null);
		popupWindow = new PopupWindow(popupView, 400, 400, true);
		popupWindow.setOutsideTouchable(true);
		popupWindow
				.setBackgroundDrawable(new BitmapDrawable(res, (Bitmap) null));
		popupWindow.setOnDismissListener(this);

		listViewSelector = (ListView) popupView.findViewById(R.id.popup_list);
		listAdapterSelector = new ArrayAdapter<String>(context,
				R.layout.popup_item, listDatas);

		String[] s = res.getStringArray(R.array.province_item);
		listDatas.addAll(Arrays.asList(s));

		listViewSelector.setAdapter(listAdapterSelector);
		listViewSelector.setOnItemClickListener(this);

		mToggleBtnClickListener mclick = new mToggleBtnClickListener();
		areaSelector.setOnClickListener(mclick);
		schoolSelector.setOnClickListener(mclick);
		facultySelector.setOnClickListener(mclick);
		majorSelector.setOnClickListener(mclick);

	}

	@Override
	public void onDismiss() {
		// TODO Auto-generated method stub
		targetSelector.setChecked(false);
		System.out.println("uncheck " + targetSelector.isChecked());
	}

	public class mToggleBtnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			if (targetSelector != null && targetSelector != v) {
				targetSelector.setChecked(false);
			}

			targetSelector = (ToggleButton) v;
			System.out.println("click " + targetSelector.isChecked());

			if (targetSelector.isChecked()) {
				System.out.println("check " + targetSelector.isChecked());
				popupWindow.showAsDropDown(targetSelector);

			} else if (!targetSelector.isChecked()) {
				System.out.println("uncheck!! " + targetSelector.isChecked());
				if (popupWindow.isShowing())
					popupWindow.dismiss();
			}
		}

	}
	
	public boolean onPressBack() {
		if (popupWindow != null && popupWindow.isShowing()) {
			popupWindow.dismiss();
			//hideView();
			if (targetSelector != null) {
				targetSelector.setChecked(false);
			}
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

		String s = listDatas.get(position);
		targetSelector.setText(s);
		popupWindow.dismiss();
	}

}
