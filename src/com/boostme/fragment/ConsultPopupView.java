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
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ToggleButton;

import com.boostme.activity.R;

public class ConsultPopupView implements OnDismissListener, OnItemClickListener {

	Context context;
	View rootView;
	Resources res;

	private ToggleButton areaSelector, schoolSelector, facultySelector,
			majorSelector, targetSelector;

	private PopupWindow popupWindow;

	private ListView listViewSelector;

	private ArrayAdapter<String> listAdapterSelector;
	private ArrayList<String> listDatas = new ArrayList<String>();

	private String selectedArea = "", selectedSchool = "",
			selectedFaculty = "", selectedMajor = "";

	private String[] areaDatas, schoolDatas, facultyDatas, majorDatas;

	private int displayWidth, displayHeight;

	public ConsultPopupView(Context context, View rootView) {

		this.context = context;
		this.rootView = rootView;
		displayWidth = context.getResources().getDisplayMetrics().widthPixels;
		displayHeight = context.getResources().getDisplayMetrics().heightPixels;
		res = context.getResources();
		init();

	}

	public void init() {

		LayoutInflater inflater = LayoutInflater.from(context);

		areaSelector = (ToggleButton) rootView.findViewById(R.id.area_selector);
		schoolSelector = (ToggleButton) rootView
				.findViewById(R.id.school_selector);
		facultySelector = (ToggleButton) rootView
				.findViewById(R.id.faculty_selector);
		majorSelector = (ToggleButton) rootView
				.findViewById(R.id.major_selector);

		View popupView = inflater.inflate(R.layout.popup_list, null);
		popupWindow = new PopupWindow(popupView, displayWidth,
				displayHeight / 2, true);
		popupWindow.setOutsideTouchable(true);
		popupWindow
				.setBackgroundDrawable(new BitmapDrawable(res, (Bitmap) null));
		popupWindow.setOnDismissListener(this);

		listViewSelector = (ListView) popupView.findViewById(R.id.popup_list);
		listAdapterSelector = new ArrayAdapter<String>(context,
				R.layout.popup_item, listDatas);

		targetSelector = areaSelector;
		areaDatas = getAreaDatas();
		listDatas.addAll(Arrays.asList(areaDatas));

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

	}

	public void changeListDatas() {

		listDatas.clear();
		switch (targetSelector.getId()) {
		case R.id.area_selector:
			listDatas.addAll(Arrays.asList(areaDatas));
			break;
		case R.id.school_selector:
			listDatas.addAll(Arrays.asList(schoolDatas));
			break;
		case R.id.faculty_selector:
			listDatas.addAll(Arrays.asList(facultyDatas));
			break;
		case R.id.major_selector:
			listDatas.addAll(Arrays.asList(majorDatas));
			break;
		}

	}

	public class mToggleBtnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			if (targetSelector == v) {
				popupWindow.showAsDropDown(targetSelector);
			} else {
				targetSelector = (ToggleButton) v;
				changeListDatas();
				popupWindow.showAsDropDown(targetSelector);
			}

		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

		String s = listDatas.get(position);
		targetSelector.setText(s);

		switch (targetSelector.getId()) {
		case R.id.area_selector:
			changeSchoolStatus(s);
			break;
		case R.id.school_selector:
			changeFacultyStatus(s);
			break;
		case R.id.faculty_selector:
			changeMajorStatus(s);
			break;
		case R.id.major_selector:
			selectedMajor = s;
			break;
		}
		popupWindow.dismiss();
	}

	public void changeSchoolStatus(String s) {
		if (selectedArea != s) {
			selectedArea = s;
			schoolDatas = getSchoolDatas(s);

			schoolSelector.setText("学校");
			schoolSelector.setEnabled(true);
			facultySelector.setText("学院");
			facultySelector.setEnabled(false);
			majorSelector.setText("专业");
			majorSelector.setEnabled(false);
		}

	}

	public void changeFacultyStatus(String s) {

		if (selectedSchool != s) {
			selectedSchool = s;
			facultyDatas = getFacultyDatas(s);

			facultySelector.setText("学院");
			facultySelector.setEnabled(true);
			majorSelector.setText("专业");
			majorSelector.setEnabled(false);
		}

	}

	public void changeMajorStatus(String s) {
		if (selectedFaculty != s) {
			selectedFaculty = s;
			majorDatas = getMajorDatas(selectedSchool, s);

			majorSelector.setText("专业");
			majorSelector.setEnabled(true);
		}

	}

	public String[] getAreaDatas() {
		String[] s = res.getStringArray(R.array.province_item);
		return s;
	}

	public String[] getSchoolDatas(String area) {
		String[] s = res.getStringArray(R.array.school_item);
		return s;
	}

	public String[] getFacultyDatas(String school) {
		String[] s = res.getStringArray(R.array.faculty_item);
		return s;
	}

	public String[] getMajorDatas(String school, String faculty) {
		String[] s = res.getStringArray(R.array.major_item);
		return s;
	}

}
