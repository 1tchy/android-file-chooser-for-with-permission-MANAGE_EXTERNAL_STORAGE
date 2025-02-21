package com.obsez.android.lib.filechooser.demo.tool;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;

import timber.log.Timber;

/**
 * Created by coco on 6/8/15.
 */
public class ImageUtil {

    private static final String TAG = "ImageUtil";

    public static Bitmap decodeFile(String imgPath) {
        File imgFile = new File(imgPath);
        return decodeFile(imgFile);
    }

    public static Bitmap decodeFile(File imgFile) {
        Bitmap bm = null;

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;

        FileInputStream fis;
        try {
            fis = new FileInputStream(imgFile);
            BitmapFactory.decodeStream(fis, null, o);
            fis.close();

            int IMAGE_MAX_SIZE = 1024; // maximum dimension limit
            int scale = 1;
            if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
                scale = (int) Math.pow(2, (int) Math.round(
                    Math.log(IMAGE_MAX_SIZE / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;

            fis = new FileInputStream(imgFile);
            bm = BitmapFactory.decodeStream(fis, null, o2);
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bm;
    }

    public static Bitmap showBitmapFromFile(String imgPath) {
        File imgFile = new File(imgPath);
        return showBitmapFromFile(imgFile);
    }

    public static Bitmap showBitmapFromFile(File imgFile) {
        try {
            if (imgFile.exists()) {

                return decodeFile(imgFile.getAbsolutePath());

            }
        } catch (Exception e) {
            Timber.e("Exception showBitmapFromFile");
            return null;
        }
        return null;
    }

}
