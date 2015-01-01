package com.boostme.util;

import org.apache.http.Header;

import android.content.Context;

import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * 封装了默认错误处理的AsyncHttpResponseHandler
 * @author aeolus
 *
 */
public abstract class BmAsyncHttpResponseHandler extends AsyncHttpResponseHandler
{
	private Context mContext;
	
	public BmAsyncHttpResponseHandler(Context context)
	{
		mContext = context;
	}
	
	public abstract void onSuccessOper(int statusCode, Header[] headers, byte[] response);
	
	@Override
	public void onSuccess(int statusCode, Header[] headers, byte[] response)
	{
		try {
			this.onSuccessOper(statusCode, headers, response);
		} catch (JsonSyntaxException jsonExp) {
			UIUtil.showToast(mContext, "服务端数据Json解析出错！");
			jsonExp.printStackTrace();
		} catch (Exception exp) {
			UIUtil.showToast(mContext, "服务端出现了未知错误！");
			exp.printStackTrace();
		}
	}

	@Override
	public void onFailure(int statusCode, Header[] headers, byte[] response, Throwable throwable)
	{
		if (!NetUtil.isNetConnected(mContext)) {
			UIUtil.showToast(mContext, "网络连接出错，请检查网络");
		} else {
			UIUtil.showToast(mContext, "出错了，请稍后重试，或者检查网络问题");
		}
		throwable.printStackTrace();
	}
	
	public void showErrorInfo(String msg)
	{
		UIUtil.showToast(mContext, msg);
	}
}
