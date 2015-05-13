package com.boostme.view;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.boostme.activity.R;
import com.boostme.activity.TradeContentActivity;
import com.boostme.util.BmHttpClientUtil;
import com.boostme.util.SharedPreferencesUtil;
import com.slidingmenu.lib.SlidingMenu;

public class SlidingMenuView implements OnClickListener {

	private final Activity activity;
	private SlidingMenu localSlidingMenu;

	private View myPurchaseView;
	private View myLogin;
	private TextView myLoginText;
	

	public SlidingMenuView(Activity activity) {
		this.activity = activity;
	}

	public SlidingMenu initSlidingMenu() {
		localSlidingMenu = new SlidingMenu(activity);
		localSlidingMenu.setMode(SlidingMenu.LEFT);
		localSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		localSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);
		localSlidingMenu.setShadowDrawable(R.drawable.shadow);
		localSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		localSlidingMenu.setFadeDegree(0.35F);
		localSlidingMenu
				.attachToActivity(activity, SlidingMenu.SLIDING_CONTENT);
		localSlidingMenu.setMenu(R.layout.left_slidingmenu);
		// localSlidingMenu.toggle();
		
		initView();
		
		return localSlidingMenu;
	}

	public void initView() {
		myPurchaseView = localSlidingMenu
				.findViewById(R.id.left_my_purchase);
		myPurchaseView.setOnClickListener(this);
		
		myLogin = localSlidingMenu.findViewById(R.id.account_login);
		myLogin.setOnClickListener(this);
		myLoginText = (TextView)localSlidingMenu.findViewById(R.id.account_logintext);
		
		if(SharedPreferencesUtil.getBoolean(activity, SharedPreferencesUtil.IS_LOGIN) == true){
			myLoginText.setText("退出登录");
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.left_my_purchase:
			Intent intent = new Intent(activity, TradeContentActivity.class);
			activity.startActivity(intent);
			break;
		case R.id.account_login:
			if(myLoginText.getText().toString().equals("退出登录")){
				SharedPreferencesUtil.save(activity, SharedPreferencesUtil.IS_LOGIN, false);
				BmHttpClientUtil.clearCookie();
				myLoginText.setText("登录帐号");
			}
			break;
		default:
			break;
		}
	}

}
