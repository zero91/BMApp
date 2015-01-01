package com.boostme.view;

import android.app.Activity;

import com.boostme.activity.R;
import com.slidingmenu.lib.SlidingMenu;

public class SlidingMenuView {
	
	private final Activity activity;
	private SlidingMenu localSlidingMenu;
	
	
	public SlidingMenuView(Activity activity){
		this.activity = activity;
	}
	
	public SlidingMenu initSlidingMenu(){
		localSlidingMenu = new SlidingMenu(activity);
		localSlidingMenu.setMode(SlidingMenu.LEFT);
		localSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		localSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);
		localSlidingMenu.setShadowDrawable(R.drawable.shadow);
		localSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		localSlidingMenu.setFadeDegree(0.35F);
		localSlidingMenu.attachToActivity(activity, SlidingMenu.SLIDING_CONTENT);
		localSlidingMenu.setMenu(R.layout.left_slidingmenu);
		//localSlidingMenu.toggle();
		
		return localSlidingMenu;
	}

}





























