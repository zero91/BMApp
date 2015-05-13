package org.qii.weiciyuan.support.imageutility;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.boostme.util.Utility;

/**
 * User: Jiang Qi
 * Date: 12-8-3
 */
public class ImageUtility 
{
	public static Bitmap decodeBitmapFromSDCard(String path,
            int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }
	
	private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (height > reqHeight && reqHeight != 0) {
                inSampleSize = (int) Math.floor((double) height / (double) reqHeight);
            }

            int tmp = 0;

            if (width > reqWidth && reqWidth != 0) {
                tmp = (int) Math.floor((double) width / (double) reqWidth);
            }

            inSampleSize = Math.max(inSampleSize, tmp);
        }
        int roundedSize;
        if (inSampleSize <= 8) {
            roundedSize = 1;
            while (roundedSize < inSampleSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (inSampleSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

	/*public static Bitmap getWriteWeiboPictureThumblr(String filePath) {
        try {
            //actionbar button image width and height is 32 dip
            int reqWidth = Utility.dip2px(32);
            int reqHeight = Utility.dip2px(32);

            if (!FileManager.isExternalStorageMounted()) {
                return null;
            }

            boolean fileExist = new File(filePath).exists();

            if (!fileExist) {
                return null;
            }

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);

            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;
            options.inPurgeable = true;
            options.inInputShareable = true;

            Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

            if (bitmap == null) {
                //this picture is broken,so delete it
                new File(filePath).delete();
                return null;
            }

            int[] size = calcResize(bitmap.getWidth(), bitmap.getHeight(), reqWidth, reqHeight);
            if (size[0] > 0 && size[1] > 0) {
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, size[0], size[1], true);
                if (scaledBitmap != bitmap) {
                    bitmap.recycle();
                    bitmap = scaledBitmap;
                }
            }

            Bitmap roundedBitmap = ImageEditUtility.getRoundedCornerBitmap(bitmap);
            if (roundedBitmap != bitmap) {
                bitmap.recycle();
                bitmap = roundedBitmap;
            }

            int exifRotation = ImageUtility.getFileExifRotation(filePath);
            if (exifRotation != 0) {
                Matrix mtx = new Matrix();
                mtx.postRotate(exifRotation);
                Bitmap adjustedBitmap = Bitmap.createBitmap(roundedBitmap, 0, 0,
                        bitmap.getWidth(), bitmap.getHeight(), mtx, true);
                if (adjustedBitmap != bitmap) {
                    bitmap.recycle();
                    bitmap = adjustedBitmap;
                }
            }

            return bitmap;
        } catch (OutOfMemoryError ignored) {
            ignored.printStackTrace();
            return null;
        }
    }*/
}

