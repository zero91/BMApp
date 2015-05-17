package com.boostme.util;

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;

import com.boostme.activity.LoginActivity;
import com.boostme.bean.ResponseInfoEntity;
import com.boostme.bean.UserEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

public class LoginUtil
{
	public static void doLogin(final String name, final String pass, final Activity activity, final Class<? extends Activity> objActivity)
	{
		RequestParams params = new RequestParams();
		params.put("username", name);
		params.put("password", pass);
		BmHttpClientUtil.getInstance(activity).post("user/ajax_login", params, new BmAsyncHttpResponseHandler(activity)
		{
			@Override
			public void onSuccessOper(int statusCode, Header[] headers, byte[] response)
			{
				/*for (Header header: headers) {
					Logs.logd(header.getName() + " = " + header.getValue());
				}
				Logs.logd(new String(response));*/
				Logs.logd(new String("login:  " + response));
				try {
					String result = new String(response, "utf-8");
					JSONObject json = new JSONObject(result);
					ResponseInfoEntity responseInfo = ResponseInfoEntity.parse(json);
					//UIUtil.showToast(activity, result, 100000);
					if (responseInfo.isSuccess()) {
						UserEntity user = (new Gson()).fromJson(json.getString("user"),  new TypeToken<UserEntity>() {}.getType());
						loginSuccess(user, activity, objActivity);
					} else {
						//UIUtil.showToast(activity, "errorno:" + responseInfo.getError());
						int errorno = responseInfo.getError();
						if (errorno == 104) {
							UserEntity user = new UserEntity();
							user.setUsername(name);
							user.setPassword(pass);
							loginSuccess(user, activity, objActivity);
						} else {
							UIUtil.showToast(activity, "登陆失败，error:" + responseInfo.getError());
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
	
	public static void loginSuccess(UserEntity user, Activity activity, Class<? extends Activity> objActivity)
	{
		BmHttpClientUtil.setCookie();
		SharedPreferencesUtil.save(activity, SharedPreferencesUtil.IS_LOGIN, true);
		SharedPreferencesUtil.save(activity, SharedPreferencesUtil.USERNAME, user.getUsername());
		SharedPreferencesUtil.save(activity, SharedPreferencesUtil.PASSWORD, user.getPassword());
		
		GlobalContext.getInstance().setUser(user);
		
		//UIUtil.getApplication(LoginActivity.this).setUser(user);
		Intent intent = new Intent(activity, objActivity);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		activity.startActivity(intent);
		activity.finish();
		Logs.logd("登陆成功");
	}
	
	public static void doLogout(final Activity activity, final Class<? extends Activity> objActivity)
	{
		RequestParams params = new RequestParams();
		BmHttpClientUtil.getInstance(activity).post("user/ajax_logout", params, new BmAsyncHttpResponseHandler(activity)
		{
			@Override
			public void onSuccessOper(int statusCode, Header[] headers, byte[] response)
			{
				Logs.logd(new String(response));
				try {
					String result = new String(response, "utf-8");
					JSONObject json = new JSONObject(result);
					ResponseInfoEntity responseInfo = ResponseInfoEntity.parse(json);
					if (responseInfo.isSuccess()) {
						BmHttpClientUtil.clearCookie();
						SharedPreferencesUtil.save(activity, SharedPreferencesUtil.IS_LOGIN, false);
						SharedPreferencesUtil.save(activity, SharedPreferencesUtil.USERNAME, null);
						SharedPreferencesUtil.save(activity, SharedPreferencesUtil.PASSWORD, null);
						GlobalContext.getInstance().setUser(null);
						
						Intent intent = new Intent(activity, objActivity);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						activity.startActivity(intent);
						activity.finish();
					} else {
						UIUtil.showToast(activity, "退出失败，error:" + responseInfo.getError());
						
						Intent intent = new Intent(activity, objActivity);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						activity.startActivity(intent);
						activity.finish();
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void forwardLoginActivity(Activity source) {
		source.startActivity(new Intent(source, LoginActivity.class));
	}
}
