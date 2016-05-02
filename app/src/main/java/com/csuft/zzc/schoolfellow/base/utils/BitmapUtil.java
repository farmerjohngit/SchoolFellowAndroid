package com.csuft.zzc.schoolfellow.base.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

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
}
