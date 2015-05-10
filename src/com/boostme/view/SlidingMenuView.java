package com.boostme.view;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boostme.activity.LoginActivity;
import com.boostme.activity.R;
import com.boostme.util.GlobalContext;
import com.boostme.util.LoginUtil;
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
		
		if (GlobalContext.getInstance().getUser() != null) {
			((LinearLayout)localSlidingMenu.findViewById(R.id.unlogin_info)).setVisibility(View.GONE);
			((LinearLayout)localSlidingMenu.findViewById(R.id.login_info)).setVisibility(View.VISIBLE);
			((TextView)localSlidingMenu.findViewById(R.id.username_txt)).setText(GlobalContext.getInstance().getUser().getUsername());
			
			((TextView)localSlidingMenu.findViewById(R.id.logout_txt)).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v)
				{
					LoginUtil.doLogout(activity, LoginActivity.class);
				}
			});
		}
		return localSlidingMenu;
	}

}





























