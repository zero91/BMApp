package com.boostme.util;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Display;

public class Utility
{
	public static Uri getLatestCameraPicture(Activity activity)
	{
		String[] projection = new String[] {
				MediaStore.Images.ImageColumns._ID,
				MediaStore.Images.ImageColumns.DATA,
				MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
				MediaStore.Images.ImageColumns.DATE_TAKEN,
				MediaStore.Images.ImageColumns.MIME_TYPE };
		final Cursor cursor = activity.getContentResolver().query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null,
				null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");
		if (cursor.moveToFirst()) {
			String path = cursor.getString(1);
			return Uri.fromFile(new File(path));
		}
		return null;
	}

	public static int dip2px(int dipValue) 
	{
        float reSize = GlobalContext.getInstance().getResources().getDisplayMetrics().density;
        return (int) ((dipValue * reSize) + 0.5);
    }
	
	public static int getScreenWidth() {
        Activity activity = GlobalContext.getInstance().getActivity();
        if (activity != null) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            return metrics.widthPixels;
        }

        return 480;
    }

    public static int getScreenHeight() {
        Activity activity = GlobalContext.getInstance().getActivity();
        if (activity != null) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            return metrics.heightPixels;
        }
        return 800;
    }

	public static int length(String paramString) {
        int i = 0;
        for (int j = 0; j < paramString.length(); j++) {
            if (paramString.substring(j, j + 1).matches("[Α-￥]")) {
                i += 2;
            } else {
                i++;
            }
        }

        if (i % 2 > 0) {
            i = 1 + i / 2;
        } else {
            i = i / 2;
        }

        return i;
    }

	public static boolean isIntentSafe(Activity activity, Intent intent) {
        PackageManager packageManager = activity.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        return activities.size() > 0;
    }

	public static boolean isKK() {
        return Build.VERSION.SDK_INT >= 19; //Build.VERSION_CODES.KITKAT;
    }
}
