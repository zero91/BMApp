package com.boostme.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

/**
 * http异步访问工具类
 * @author aeolus
 *
 */
public class BmHttpClientUtil
{
	public static final String HOST_URL = "http://www.boostme.cn:9507/";
	public static final String BASE_URL = HOST_URL;
	
	private static BmHttpClientUtil mClientUtil;
	
	private AsyncHttpClient mClient;
	private Context mContext;

	private BmHttpClientUtil(Context paramContext)
	{
		mContext = paramContext;
		mClient = new AsyncHttpClient();
		PersistentCookieStore myCookieStore = new PersistentCookieStore(paramContext);
		mClient.setCookieStore(myCookieStore);

		/*List<Cookie> cookies = myCookieStore.getCookies();
		String phpCookie = "";
		if (cookies.isEmpty()) {
			Logs.logd("None");
		} else {
			for (int i = 0; i < cookies.size(); i++) {
				phpCookie += cookies.get(i).getName() + "=" + cookies.get(i).getValue() + ";";
			}
			Logs.logd(phpCookie);
		}
		if (phpCookie.length() > 0) {
			mClient.addHeader("Cookie", phpCookie);
		}*/
	}
	
	/**
	 * 登陆成功后要调用这个函数
	 */
	public static void setCookie()
	{
		PersistentCookieStore myCookieStore = new PersistentCookieStore(mClientUtil.mContext);
		mClientUtil.mClient.setCookieStore(myCookieStore);

		/*List<Cookie> cookies = myCookieStore.getCookies();
		String phpCookie = "";
		if (cookies.isEmpty()) {
			Logs.logd("None");
		} else {
			for (int i = 0; i < cookies.size(); i++) {
				phpCookie += cookies.get(i).getName() + "=" + cookies.get(i).getValue() + ";";
			}
			Logs.logd(phpCookie);
		}
		if (phpCookie.length() > 0) {
			mClientUtil.mClient.addHeader("Cookie", phpCookie);
		}*/
	}
	
	public static void clearCookie()
	{
		PersistentCookieStore myCookieStore = new PersistentCookieStore(mClientUtil.mContext);
		myCookieStore.clear();
		mClientUtil.mClient.setCookieStore(myCookieStore);
		//mClientUtil.mClient.setCookieStore(null);
	}

	public static BmHttpClientUtil getInstance(Context paramContext)
	{
		if (mClientUtil == null) {
			mClientUtil = new BmHttpClientUtil(paramContext.getApplicationContext());
		}
		return mClientUtil;
	}

	public AsyncHttpClient getAsyncHttpClient()
	{
		assert this.mClient != null;
		return this.mClient;
	}

	public RequestHandle get(String url, Map<String, String> params, AsyncHttpResponseHandler responseHandler)
	{
		String getUrl = getAbsoluteUrl(url) + getGetParams(params);
		Logs.logd("url: " + getUrl);
		return mClient.get(getUrl, null, responseHandler);
	}

	public RequestHandle post(String url, String content, AsyncHttpResponseHandler responseHandler)
	{
		HttpEntity entity = null;
		try {
			entity = new StringEntity(content, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		if (entity == null) {
			return null;
		}
		try {
			Logs.logd(url + ", (post)entity = " + EntityUtils.toString(entity));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mClient.post(mContext, getAbsoluteUrl(url), entity, "text", responseHandler);
	}
	
	public RequestHandle post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler)
	{
		Logs.logd(getAbsoluteUrl(url) + ", " + params.toString());
		return mClient.post(getAbsoluteUrl(url), params, responseHandler); 
	}
	
	private String getGetParams(Map<String, String> params)
	{
		/*if (!params.containsKey(Constants.SESSIONID)) {
			params.put(Constants.SESSIONID, SharedPreferencesUtil.getString(mContext, Constants.SESSIONID));
		}*/

		StringBuffer sb = new StringBuffer();
		sb.append("?");
		for (Entry<String, String> entry : params.entrySet()) {
			sb.append(entry.getKey()).append("=").append(entry.getValue())
			  .append("&");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	private String getAbsoluteUrl(String relativeUrl)
	{
		if (relativeUrl.startsWith("http")) {
			return relativeUrl;
		} else {
			return BASE_URL + relativeUrl;
		}
	}
}
