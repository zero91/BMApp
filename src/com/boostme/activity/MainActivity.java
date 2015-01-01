package com.boostme.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.boostme.adapter.MyFragmentPagerAdapter;
import com.boostme.fragment.ButtonFragment;
import com.boostme.fragment.TestFragment;
import com.boostme.view.SlidingMenuView;
import com.slidingmenu.lib.SlidingMenu;

public class MainActivity extends FragmentActivity implements OnClickListener {

	public static final int TAB_COMMUNICATION = 0;
	public static final int TAB_CONSULTING = 1;
	public static final int TAB_TUTOR = 2;
	public static final int TAB_MATERIAL = 3;

	private ViewPager viewPager;
	private ArrayList<Fragment> fragmentList;
	private RadioButton commBtn, consultBtn, tutorBtn, materialBtn;

	private SlidingMenu localSlidingMenu;
	private ImageButton actionbarMenuBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initTextView();
		initViewPager();
		initSlidingMenu();
	}

	public void initTextView() {

		commBtn = (RadioButton) findViewById(R.id.main_tab_communication);
		consultBtn = (RadioButton) findViewById(R.id.main_tab_consulting);
		tutorBtn = (RadioButton) findViewById(R.id.main_tab_tutor);
		materialBtn = (RadioButton) findViewById(R.id.main_tab_material);
		actionbarMenuBtn = (ImageButton) findViewById(R.id.btn_actionbar_menu);

		commBtn.setOnClickListener(this);
		consultBtn.setOnClickListener(this);
		tutorBtn.setOnClickListener(this);
		materialBtn.setOnClickListener(this);
		actionbarMenuBtn.setOnClickListener(this);

	}

	protected void initSlidingMenu() {
		localSlidingMenu = new SlidingMenuView(this).initSlidingMenu();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.main_tab_communication:
			viewPager.setCurrentItem(TAB_COMMUNICATION);
			break;
		case R.id.main_tab_consulting:
			viewPager.setCurrentItem(TAB_CONSULTING);
			break;
		case R.id.main_tab_tutor:
			viewPager.setCurrentItem(TAB_TUTOR);
			break;
		case R.id.main_tab_material:
			viewPager.setCurrentItem(TAB_MATERIAL);
			break;
		case R.id.btn_actionbar_menu:
			localSlidingMenu.toggle();
			break;
		default:
			break;
		}

	}

	public void initViewPager() {
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		fragmentList = new ArrayList<Fragment>();

		fragmentList = new ArrayList<Fragment>();
		Fragment btFragment = new ButtonFragment();
		Fragment secondFragment = TestFragment
				.newInstance("this is second fragment");
		Fragment thirdFragment = TestFragment
				.newInstance("this is third fragment");
		Fragment fourthFragment = TestFragment
				.newInstance("this is fourth fragment");

		fragmentList.add(btFragment);
		fragmentList.add(secondFragment);
		fragmentList.add(thirdFragment);
		fragmentList.add(fourthFragment);

		viewPager.setAdapter(new MyFragmentPagerAdapter(
				getSupportFragmentManager(), fragmentList));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	private long mExitTime;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (localSlidingMenu.isMenuShowing()) {
				localSlidingMenu.showContent();
			} else {
				if ((System.currentTimeMillis() - mExitTime) > 2000) {
					Toast.makeText(this, "在按一次退出", Toast.LENGTH_SHORT).show();
					mExitTime = System.currentTimeMillis();
				} else {
					finish();
				}
			}
			return true;
		}
		// 拦截MENU按钮点击事件，让他无任何操作
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
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
				commBtn.setChecked(true);
				break;
			case TAB_CONSULTING:
				consultBtn.setChecked(true);
				break;
			case TAB_TUTOR:
				tutorBtn.setChecked(true);
				break;
			case TAB_MATERIAL:
				materialBtn.setChecked(true);
				break;
			default:
				break;
			}
		}
	}

}
