package com.boostme.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.View;

import com.boostme.activity.MainActivity;

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
	
	public static void showDialog(final Context ctx, String msg, boolean goHome)
	{
		AlertDialog dialog = getDialog(ctx, msg, goHome);
		dialog.show();
	}
	
	// 定义一个显示消息的对话框
	public static AlertDialog getDialog(final Context ctx, String msg, boolean goHome)
	{
		// 创建一个AlertDialog.Builder对象
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx).setMessage(msg).setCancelable(false);
		if (goHome) {
			builder.setPositiveButton("确定", new OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					Intent i = new Intent(ctx, MainActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					ctx.startActivity(i);
				}
			});
		} else {
			builder.setPositiveButton("确定", null);
		}
		return builder.create();
	}
}
