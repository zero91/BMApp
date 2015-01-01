package com.boostme.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.view.View;

public class DialogUtil
{
	// 定义一个显示消息的对话框
	public static AlertDialog getDialog(final Context ctx, String msg, OnClickListener okListener)
	{
		// 创建一个AlertDialog.Builder对象
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx).setMessage(msg).setCancelable(false);
		builder.setPositiveButton("确定", okListener);
		return builder.create();
	}	
	
	/**
	 * 定义一个对话框
	 * @param ctx
	 * @param msg
	 * @param okListener
	 * @param negativeListener
	 * @return
	 */
	public static AlertDialog getDialog(final Context ctx, String msg, String ok, OnClickListener okListener, 
			String negative, OnClickListener negativeListener)
	{
		// 创建一个AlertDialog.Builder对象
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx).setMessage(msg).setCancelable(false);
		builder.setPositiveButton(ok, okListener)
			   .setNegativeButton(negative, negativeListener);
		return builder.create();
	}	
	
	/**
	 * 定义一个有 确定，取消 按钮的对话框
	 * @param ctx
	 * @param msg
	 * @param okListener
	 * @param negativeListener
	 * @return
	 */
	public static AlertDialog getDialog(final Context ctx, String msg, OnClickListener okListener, OnClickListener negativeListener)
	{
		return getDialog(ctx, msg, "确定", okListener, "取消", negativeListener);
	}	

	// 定义一个显示指定组件的对话框
	public static void showDialog(Context ctx, View view)
	{
		new AlertDialog.Builder(ctx)
					   .setView(view).setCancelable(false)
					   .setPositiveButton("确定", null).create().show();
	}
}
