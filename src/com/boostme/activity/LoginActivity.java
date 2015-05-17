package com.boostme.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.boostme.util.DialogUtil;
import com.boostme.util.LoginUtil;
import com.boostme.util.UIUtil;
import com.boostme.view.EnhanceEditText;

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

		mUsernameEditText.setText("test002");
		mPassEditText.setText("test002");

		System.err.println(System.currentTimeMillis());
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
    		
    		LoginUtil.doLogin(name, pass, LoginActivity.this, MainActivity.class);
    		break;
        case R.id.login_first:
        	//可以添加其他内容，或者另外的方式
        	DialogUtil.showDialog(LoginActivity.this, "亲，请先在PC端注册~", false);  
        default:
        	break;
        }
	}
}
