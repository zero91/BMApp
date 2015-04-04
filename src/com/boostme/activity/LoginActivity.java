package com.boostme.activity;

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.boostme.bean.ResponseInfoEntity;
import com.boostme.util.BmAsyncHttpResponseHandler;
import com.boostme.util.BmHttpClientUtil;
import com.boostme.util.DialogUtil;
import com.boostme.util.Logs;
import com.boostme.util.SharedPreferencesUtil;
import com.boostme.util.UIUtil;
import com.boostme.view.EnhanceEditText;
import com.loopj.android.http.RequestParams;

public class LoginActivity extends BMActivity implements OnClickListener
{
	private Button mLoginButton;
	private Button mFirstLoginButton;
	private EnhanceEditText mUsernameEditText, mPassEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		mLoginButton = (Button) findViewById(R.id.login_bt);
		mLoginButton.setOnClickListener(this);
		
		//添加第一次登陆功能
		mFirstLoginButton=(Button) findViewById(R.id.login_first);
		mFirstLoginButton.setOnClickListener(this);

		mUsernameEditText = (EnhanceEditText) findViewById(R.id.login_username);
		mPassEditText = (EnhanceEditText) findViewById(R.id.login_password);

		mUsernameEditText.setText("admin");
		mPassEditText.setText("8050894");

		System.err.println(System.currentTimeMillis());
	}

	private void doLogin(String name, String pass) 
	{
		RequestParams params = new RequestParams();
		params.put("username", name);
		params.put("password", pass);
		BmHttpClientUtil.getInstance(this).post("user/ajax_login", params, new BmAsyncHttpResponseHandler(this)
		{
			@Override
			public void onSuccessOper(int statusCode, Header[] headers, byte[] response)
			{
				/*for (Header header: headers) {
					Logs.logd(header.getName() + " = " + header.getValue());
				}
				Logs.logd(new String(response));*/
				Logs.logd(new String(response));
				try {
					String result = new String(response, "utf-8");
					JSONObject json = new JSONObject(result);
					ResponseInfoEntity responseInfo = ResponseInfoEntity.parse(json);
					if (responseInfo.isSuccess()) {
						LoginActivity.this.loginSuccess();
					} else {
						int errorno = responseInfo.getError();
						if (errorno == 104) {
							LoginActivity.this.loginSuccess();
						} else {
							UIUtil.showToast(LoginActivity.this, "登陆失败，error:" + responseInfo.getError());
						}
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void loginSuccess()
	{
		BmHttpClientUtil.setCookie();
		SharedPreferencesUtil.save(this, SharedPreferencesUtil.IS_LOGIN, true);
		
		//UIUtil.getApplication(LoginActivity.this).setUser(user);
		Intent intent = new Intent(LoginActivity.this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

	@Override
	protected String getActivitiTitle()
	{
		return "退出";
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//TODO
//			if (TextUtils.isEmpty(SPUtils.getString(this, SPUtils.SPKEY_SESSIONID))) {
//				System.exit(0);
//			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View view) 
	{
        int id = view.getId();
        switch(id) {
        case R.id.login_bt:
        	String name = mUsernameEditText.getRawText();
    		String pass = mPassEditText.getRawText();

    		if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pass)) {
    			UIUtil.showToast(this, "用户名和密码不能为空！");
    			return;
    		}
    		System.err.println("name: " + name + "   pass: " + pass);
    		
    		doLogin(name, pass);
    		break;
        case R.id.login_first:
        	//可以添加其他内容，或者另外的方式
        	DialogUtil.showDialog(LoginActivity.this, "亲，请先在PC端注册~", false);  
        default:
        	break;
        }
	}
}
