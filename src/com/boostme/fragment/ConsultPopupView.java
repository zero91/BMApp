package com.boostme.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ProgressBar;
import android.widget.ToggleButton;

import com.boostme.activity.R;

public class ConsultPopupView implements OnDismissListener, OnItemClickListener {

	private static String School_List = "school_list", Dept_List = "dept_list",
			Major_List = "major_list";

	private Context context;
	private View rootView;
	private Resources res;
	private ConsultFragment container;

	private ProgressBar progressBar;

	private ToggleButton regionSelector, schoolSelector, facultySelector,
			majorSelector, targetSelector;

	private PopupWindow popupWindow;

	private ListView listViewSelector;

	private ArrayAdapter<AreaObject> listAdapterSelector;
	private List<AreaObject> listDatas = new ArrayList<AreaObject>();

	public String selectedRegion = "", selectedSchool = "",
			selectedFaculty = "", selectedMajor = "";

	private List<AreaObject> regionList, schoolList, facultyList, majorList;

	private int displayWidth, displayHeight;

	private ConsultPopupAreasHandle areasHandle;

	Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				regionList = getRegionsList(ConsultPopupAreasHandle.regionsMap);
				listDatas.addAll(regionList);
				break;
			case 1:
				schoolList = getAreas(ConsultPopupAreasHandle.regionsMap,
						ConsultPopupAreasHandle.schoolsMap, selectedRegion,
						School_List);
				schoolSelector.setEnabled(true);
				
				container.getConsultList(1, true);
				//progressBar.setVisibility(View.GONE);
				break;
			case 2:
				facultyList = getAreas(ConsultPopupAreasHandle.schoolsMap,
						ConsultPopupAreasHandle.deptsMap, selectedSchool,
						Dept_List);
				facultySelector.setEnabled(true);
				
				container.getConsultList(1, true);
				//progressBar.setVisibility(View.GONE);
				break;
			case 3:
				majorList = getAreas(ConsultPopupAreasHandle.deptsMap,
						ConsultPopupAreasHandle.majorsMap, selectedFaculty,
						Major_List);
				majorSelector.setEnabled(true);
				
				container.getConsultList(1, true);
				//progressBar.setVisibility(View.GONE);
				break;
			default:
				// do something
				break;
			}
		}
	};

	public ConsultPopupView(Context context, View rootView, ConsultFragment container) {

		this.context = context;
		this.rootView = rootView;
		this.container = container;
		
		displayWidth = context.getResources().getDisplayMetrics().widthPixels;
		displayHeight = context.getResources().getDisplayMetrics().heightPixels / 2;
		res = context.getResources();

		init();

	}

	public void init() {

		LayoutInflater inflater = LayoutInflater.from(context);

		regionSelector = (ToggleButton) rootView
				.findViewById(R.id.region_selector);
		schoolSelector = (ToggleButton) rootView
				.findViewById(R.id.school_selector);
		facultySelector = (ToggleButton) rootView
				.findViewById(R.id.faculty_selector);
		majorSelector = (ToggleButton) rootView
				.findViewById(R.id.major_selector);

		View popupView = inflater.inflate(R.layout.popup_list, null);
		popupWindow = new PopupWindow(popupView, displayWidth, displayHeight,
				true);
		popupWindow.setOutsideTouchable(true);
		popupWindow
				.setBackgroundDrawable(new BitmapDrawable(res, (Bitmap) null));
		popupWindow.setOnDismissListener(this);

		listViewSelector = (ListView) popupView.findViewById(R.id.popup_list);
		listAdapterSelector = new ArrayAdapter<AreaObject>(context,
				R.layout.popup_item, listDatas);

		targetSelector = regionSelector;

		listViewSelector.setAdapter(listAdapterSelector);
		listViewSelector.setOnItemClickListener(this);

		mToggleBtnClickListener mclick = new mToggleBtnClickListener();

		regionSelector.setOnClickListener(mclick);
		schoolSelector.setOnClickListener(mclick);
		facultySelector.setOnClickListener(mclick);
		majorSelector.setOnClickListener(mclick);

		progressBar = (ProgressBar) rootView
				.findViewById(R.id.zx_list_progressbar);

		areasHandle = new ConsultPopupAreasHandle(context, handler);
		areasHandle.getRegionsMap();// 加载地区数据Map
	}

	public int getHeight(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return 0;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			totalHeight += listItem.getMeasuredHeight();
		}

		totalHeight = totalHeight
				+ (listView.getDividerHeight() * listAdapter.getCount());

		if (totalHeight > displayHeight)
			return displayHeight;
		else
			return totalHeight;
	}

	public List<AreaObject> getRegionsList(Map<String, Object> regionMap) {

		List<AreaObject> regionList = new ArrayList<AreaObject>();
		Iterator iterator = regionMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			AreaObject a = new AreaObject();
			a.key = (String) entry.getKey();
			a.name = (String) ((Map) entry.getValue()).get("name");
			a.index = ((Double) ((Map) entry.getValue()).get("index"))
					.intValue();
			regionList.add(a);
		}
		Collections.sort(regionList);
		return regionList;
	}

	public List<AreaObject> getAreas(Map bigAreaMap, Map smallAreaMap,
			String bigAreaKey, String listName) {
		List smallAreaKeyList = (List) (((Map) bigAreaMap.get(bigAreaKey))
				.get(listName));

		List<AreaObject> smallAreaList = new ArrayList<AreaObject>();
		for (Object key : smallAreaKeyList) {
			AreaObject a = new AreaObject();
			a.key = (String) key;
			a.name = (String) ((Map) smallAreaMap.get(key)).get("name");
			a.index = ((Double) ((Map) smallAreaMap.get(key)).get("index"))
					.intValue();
			smallAreaList.add(a);
		}
		Collections.sort(smallAreaList);
		return smallAreaList;
	}

	@Override
	public void onDismiss() {
		// TODO Auto-generated method stub
		targetSelector.setChecked(false);

	}

	public void changeListDatas() {

		listDatas.clear();
		switch (targetSelector.getId()) {
		case R.id.region_selector:
			listDatas.addAll(regionList);
			break;
		case R.id.school_selector:
			listDatas.addAll(schoolList);
			break;
		case R.id.faculty_selector:
			listDatas.addAll(facultyList);
			break;
		case R.id.major_selector:
			listDatas.addAll(majorList);
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

		AreaObject s = listDatas.get(position);
		targetSelector.setText(s.name);

		switch (targetSelector.getId()) {
		case R.id.region_selector:
			changeSchoolStatus(s);
			break;
		case R.id.school_selector:
			changeFacultyStatus(s);
			break;
		case R.id.faculty_selector:
			changeMajorStatus(s);
			break;
		case R.id.major_selector:
			changeLastStatus(s);
			break;
		}
		popupWindow.dismiss();
	}

	public void changeSchoolStatus(AreaObject s) {

		if (selectedRegion != s.key) {
			selectedRegion = s.key;
			container.setRegionId(selectedRegion);
			
			progressBar.setVisibility(View.VISIBLE);
			
			if (ConsultPopupAreasHandle.schoolsMap == null) {
				areasHandle.getSchoolsMap();
			} else {
				schoolList = getAreas(ConsultPopupAreasHandle.regionsMap,
						ConsultPopupAreasHandle.schoolsMap, selectedRegion,
						School_List);
				schoolSelector.setEnabled(true);
				
				container.getConsultList(1, true);
			}

			schoolSelector.setText("学校");
			facultySelector.setText("学院");
			facultySelector.setEnabled(false);
			majorSelector.setText("专业");
			majorSelector.setEnabled(false);
			
			
		}

	}

	public void changeFacultyStatus(AreaObject s) {

		if (selectedSchool != s.key) {
			selectedSchool = s.key;
			container.setSchoolId(selectedSchool);
			
			progressBar.setVisibility(View.VISIBLE);
			
			if (ConsultPopupAreasHandle.deptsMap == null) {
				areasHandle.getDeptsMap();
			} else {
				facultyList = getAreas(ConsultPopupAreasHandle.schoolsMap,
						ConsultPopupAreasHandle.deptsMap, selectedSchool,
						Dept_List);
				facultySelector.setEnabled(true);
				
				container.getConsultList(1, true);
			}

			facultySelector.setText("学院");
			majorSelector.setText("专业");
			majorSelector.setEnabled(false);
		}

	}

	public void changeMajorStatus(AreaObject s) {
		
		if (selectedFaculty != s.key) {
			selectedFaculty = s.key;
			container.setDeptId(selectedFaculty);
			
			progressBar.setVisibility(View.VISIBLE);
			
			if (ConsultPopupAreasHandle.majorsMap == null) {
				areasHandle.getMajorsMap();
			} else {
				majorList = getAreas(ConsultPopupAreasHandle.deptsMap,
						ConsultPopupAreasHandle.majorsMap, selectedFaculty,
						Major_List);
				majorSelector.setEnabled(true);
				container.getConsultList(1, true);
			}

			majorSelector.setText("专业");
		}

	}
	
	public void changeLastStatus(AreaObject s) {
		
		if(selectedMajor != s.key){
			selectedMajor = s.key;
			container.setMajorId(selectedMajor);
			
			progressBar.setVisibility(View.VISIBLE);
			
			container.getConsultList(1, true);
		}
		
	}

	static class AreaObject implements Comparable<AreaObject> {
		public String key;
		public String name;
		public int index;

		@Override
		public int compareTo(AreaObject o) {
			// TODO Auto-generated method stub
			int diff = this.index - o.index;
			return diff;
		}

		public String toString1() {
			return "key: " + key + ", name: " + name + ", index: " + index;
		}

		public String toString() {
			return name;
		}
	}

}
