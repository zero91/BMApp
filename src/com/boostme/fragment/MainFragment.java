package com.boostme.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.boostme.activity.EditQuestionActivity;
import com.boostme.activity.MainActivity;
import com.boostme.activity.R;
import com.boostme.adapter.MyFragmentPagerAdapter;
import com.boostme.view.SlidingMenuView;
import com.slidingmenu.lib.SlidingMenu;

public class MainFragment extends Fragment implements OnClickListener {

	public static final int TAB_COMMUNICATION = 0;
	public static final int TAB_CONSULTING = 1;
	public static final int TAB_TUTOR = 2;
	public static final int TAB_MATERIAL = 3;

	private ViewPager viewPager;
	private ArrayList<Fragment> fragmentList;
	private RadioButton questionBtn, consultBtn, tutorBtn, materialBtn;

	public SlidingMenu localSlidingMenu;
	private ImageButton actionbarMenuBtn;
	private ImageButton newItemBtn;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);

		initButtonView(rootView);
		initViewPager(rootView);
		initSlidingMenu();

		Log.d("TEST", "main fragment on create view.");
		return rootView;
	}

	protected void initSlidingMenu() {
		localSlidingMenu = new SlidingMenuView((MainActivity) getActivity())
				.initSlidingMenu();
	}

	public void initButtonView(View view) {

		questionBtn = (RadioButton) view.findViewById(R.id.main_tab_question);
		consultBtn = (RadioButton) view.findViewById(R.id.main_tab_consulting);
		tutorBtn = (RadioButton) view.findViewById(R.id.main_tab_tutor);
		materialBtn = (RadioButton) view.findViewById(R.id.main_tab_material);
		actionbarMenuBtn = (ImageButton) view
				.findViewById(R.id.btn_actionbar_menu);
		newItemBtn = (ImageButton) view.findViewById(R.id.btn_actionbar_new);
		
		questionBtn.setOnClickListener(this);
		consultBtn.setOnClickListener(this);
		tutorBtn.setOnClickListener(this);
		materialBtn.setOnClickListener(this);
		
		actionbarMenuBtn.setOnClickListener(this);
		newItemBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.main_tab_question:
			viewPager.setCurrentItem(TAB_COMMUNICATION);
			newItemBtn.setVisibility(View.VISIBLE);
			break;
		case R.id.main_tab_consulting:
			viewPager.setCurrentItem(TAB_CONSULTING);
			newItemBtn.setVisibility(View.GONE);
			break;
		case R.id.main_tab_tutor:
			viewPager.setCurrentItem(TAB_TUTOR);
			newItemBtn.setVisibility(View.GONE);
			break;
		case R.id.main_tab_material:
			viewPager.setCurrentItem(TAB_MATERIAL);
			newItemBtn.setVisibility(View.GONE);
			break;
		case R.id.btn_actionbar_menu:
			localSlidingMenu.toggle();
			break;
		case R.id.btn_actionbar_new:
			Intent intent = new Intent(this.getActivity(), EditQuestionActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}

	}

	public void initViewPager(View view) {
		viewPager = (ViewPager) view.findViewById(R.id.viewpager);
		fragmentList = new ArrayList<Fragment>();

		fragmentList = new ArrayList<Fragment>();

		Fragment commuFragment = new QuestionFragment();
		Fragment consultFragment = new ConsultFragment();

		Fragment thirdFragment = TestFragment
				.newInstance("this is third fragment");
		Fragment fourthFragment = TestFragment
				.newInstance("this is fourth fragment");

		fragmentList.add(commuFragment);

		fragmentList.add(consultFragment);
		fragmentList.add(thirdFragment);
		fragmentList.add(fourthFragment);

		viewPager.setAdapter(new MyFragmentPagerAdapter(getActivity()
				.getSupportFragmentManager(), fragmentList));
		viewPager.setCurrentItem(0);
		viewPager.setOffscreenPageLimit(3);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			switch (arg0) {
			case TAB_COMMUNICATION:
				questionBtn.setChecked(true);
				newItemBtn.setVisibility(View.VISIBLE);
				break;
			case TAB_CONSULTING:
				consultBtn.setChecked(true);
				newItemBtn.setVisibility(View.GONE);
				break;
			case TAB_TUTOR:
				tutorBtn.setChecked(true);
				newItemBtn.setVisibility(View.GONE);
				break;
			case TAB_MATERIAL:
				materialBtn.setChecked(true);
				newItemBtn.setVisibility(View.GONE);
				break;
			default:
				break;
			}
		}
	}

}
