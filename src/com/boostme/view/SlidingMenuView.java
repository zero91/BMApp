package com.boostme.view;

import android.app.Activity;
import android.content.Intent;
import android.sax.StartElementListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.boostme.activity.R;
import com.boostme.activity.TradeContentActivity;
import com.slidingmenu.lib.SlidingMenu;

public class SlidingMenuView implements OnClickListener {

	private final Activity activity;
	private SlidingMenu localSlidingMenu;

	private View myPurchaseView;

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
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.left_my_purchase:
			Intent intent = new Intent(activity, TradeContentActivity.class);
			activity.startActivity(intent);
			break;
		default:
			break;
		}
	}

}
