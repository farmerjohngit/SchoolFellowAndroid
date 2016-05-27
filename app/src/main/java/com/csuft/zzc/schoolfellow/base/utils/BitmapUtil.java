package com.csuft.zzc.schoolfellow.base.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by wangzhi on 16/5/3.
 */
public class BitmapUtil {


    public static Drawable getDrawable(Context context, int id) {
        if (Build.VERSION.SDK_INT > 21) {
            return context.getDrawable(id);
        } else {
            return context.getResources().getDrawable(id, context.getTheme());
        }
    }

    public static String saveBiteMapToSdTemp(String fileName, Bitmap bitmap) {
        File f = new File(FileUtil.getImgDir(), fileName + ".png");
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
            f.createNewFile();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            return f.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fOut != null) {
                    fOut.flush();
                    fOut.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}
