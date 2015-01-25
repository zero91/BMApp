package com.boostme.activity;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.boostme.fragment.MainFragment;
import com.boostme.util.BmAsyncHttpResponseHandler;
import com.boostme.util.BmHttpClientUtil;
import com.boostme.util.Logs;
import com.loopj.android.http.RequestParams;

public class MainActivity extends FragmentActivity {

	private MainFragment mainFragment;
	private long mExitTime;  

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {

			mainFragment = new MainFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.fragment_container, mainFragment).commit();

		}
		
		testget();// only for test
		//testpost();
		testpost2();// only for test
	}
	
	private void testget()
	{
		Logs.logd("testget...");
		Map<String, String> params = new HashMap<String, String>();
		params.put("userid", "123");
		BmHttpClientUtil.getInstance(this).get("main/test", params, new BmAsyncHttpResponseHandler(this)
		{
			@Override
			public void onSuccessOper(int statusCode, Header[] headers, byte[] response)
			{
				for (Header header: headers) {
					Logs.logd(header.getName() + " = " + header.getValue());
				}
				Logs.logd(new String(response));
			}
		});
	}
	
	private void testpost()
	{
		String str = "username=admin&password=8050894&submit=";
		BmHttpClientUtil.getInstance(this).post("main/login", str, new BmAsyncHttpResponseHandler(this)
		{
			@Override
			public void onSuccessOper(int statusCode, Header[] headers, byte[] response)
			{
				for (Header header: headers) {
					Logs.logd(header.getName() + " = " + header.getValue());
				}
				Logs.logd(new String(response));
			}
		});
	}
	
	private void testpost2()
	{
		Logs.logd("testpost2...");
		RequestParams params = new RequestParams();
		params.put("username", "admin");
		params.put("password", "8050894");
		params.put("submit", "");
		BmHttpClientUtil.getInstance(this).post("user/login", params, new BmAsyncHttpResponseHandler(this)
		{
			@Override
			public void onSuccessOper(int statusCode, Header[] headers, byte[] response)
			{
				for (Header header: headers) {
					Logs.logd(header.getName() + " = " + header.getValue());
				}
				Logs.logd(new String(response));
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mainFragment.localSlidingMenu.isMenuShowing()) {
				mainFragment.localSlidingMenu.showContent();
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
}
