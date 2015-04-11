package com.boostme.util;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.boostme.activity.R;
import com.squareup.picasso.Picasso;

public class UIUtil
{
	public static void displaySoftInput(Context context, View view)
	{
		if (context == null || view == null) {
			return;
		}
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, 0);
	}

	public static void hideSoftInput(Context context, View view)
	{
		if (context == null || view == null) {
			return;
		}
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	public static void setImageFromNet(Context context, String url, ImageView iv) 
	{
		setImageFromNet(context, url, iv, R.drawable.portrait_holder);
	}
	
	public static void setImageFromNet(Context context, String url, ImageView iv, int default_image) 
	{
		try {
			Picasso.with(context).load(url).placeholder(default_image).error(R.drawable.error_portrait).into(iv);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}
	
	public static void clearFocus(Activity activity)
	{
		try {
			View view = activity.getCurrentFocus();
			if (view != null) {
				view.clearFocus();
				hideSoftInput(activity, view);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void showToast(Activity activity, String msg)
	{
		Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
	}

	public static void showToast(Context context, String msg)
	{
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	public static void showToast(Context context, String msg, int duration)
	{
		Toast.makeText(context, msg, duration).show();
	}
	
	/**
	 * 强制显示 三个点 的菜单
	 * 
	 * @param context
	 */
	public static void getOverflowMenu(Context context) {
		try {
			ViewConfiguration config = ViewConfiguration.get(context);
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//过滤表情输入
	private static Set<String> blacksEmojiSet = new HashSet();
	public static void filterEmoji(EditText paramEditText)
	{
		try {
			if (blacksEmojiSet.isEmpty()) {
				generate(paramEditText.getResources());
			}
			InputFilter[] filters = new InputFilter[1];
			filters[0] = new UIUtil.EmojiFilter(paramEditText.getContext());
			paramEditText.setFilters(filters);
			return;
		} finally {
			// localObject = finally;
			// throw localObject;
		}
	}
	
	public static InputFilter getEmojiFilter(Context context)
	{
		if (blacksEmojiSet.isEmpty()) {
			generate(context.getResources());
		}
		InputFilter filter =  new UIUtil.EmojiFilter(context);
		return filter;
	}

	public static void generate(Resources paramResources)
	{
		String[] arrayOfString = paramResources.getStringArray(R.array.emoji_black);
		for (String str: arrayOfString) {
			String [] arr = str.split("-");
			String emoji = "";
			for (int m = 0; m < arr.length; m++) {
				emoji = emoji + new String(Character.toChars(Integer.parseInt(arr[m], 16)));
			}
			blacksEmojiSet.add(emoji);
		}
	}
	
	
	static class EmojiFilter implements InputFilter
	{
		private Context mContext;
		public EmojiFilter(Context mContext)
		{
			this.mContext = mContext;
		}

		public CharSequence filter(CharSequence paramCharSequence, int paramInt1, int paramInt2, Spanned paramSpanned, int paramInt3, int paramInt4)
		{
			//System.out.println("filter: " + paramCharSequence);
			if (UIUtil.blacksEmojiSet.contains(paramCharSequence)) {
				UIUtil.showToast(mContext, "暂不支持表情输入");
				paramCharSequence = "";
			}
			return paramCharSequence;
		}
	}
}
