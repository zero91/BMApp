package com.boostme.util;

import java.io.File;

import org.xml.sax.XMLReader;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.Editable;
import android.text.Html.TagHandler;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.View;

public class BMTagHandler implements TagHandler 
{
	private Context mContext;
	
	public BMTagHandler(Context context) 
	{
		this.mContext = context;
	}
	
	@Override
	public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) 
	{
		// 处理标签<img>
		if (tag.toLowerCase().equals("img")) {
			// 获取长度
			int len = output.length();
			// 获取图片地址
			ImageSpan[] images = output.getSpans(len-1, len, ImageSpan.class);
			String imgURL = images[0].getSource();
			
			// 使图片可点击并监听点击事件
			output.setSpan(new ImageClick(mContext, imgURL), len-1, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
	}
	
	private class ImageClick extends ClickableSpan 
	{
		private String mUrl;
		private Context mContext;
		
		public ImageClick(Context context, String url) 
		{
			this.mContext = context;
			this.mUrl = url;
		}
		
		@Override
		public void onClick(View widget) 
		{
			Logs.loge("-------------------------------onclick -- BMTagHandler");
			// 将图片URL转化为本地路径，可以将图片处理类里的图片处理过程写为一个方法，方便调用
			String imageName = StringUtil.md5(mUrl);
			String sdcardPath = Environment.getExternalStorageDirectory().toString(); // 获取SDCARD的路径
			//获取图片后缀名
			String[] ss = mUrl.split("\\.");
			String ext = ss[ss.length - 1];
			
			// 最终图片保持的地址
			String savePath = sdcardPath + "/" + mContext.getPackageName() + "/" + imageName + "." + ext;
			File file = new File(savePath);
			if (file.exists()) {
				// 处理点击事件，开启一个新的activity来处理显示图片
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(file), "image/*");
				mContext.startActivity(intent);
			}
		}
	}
}
