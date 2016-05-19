package com.csuft.zzc.schoolfellow.base.view;

import android.widget.Toast;

import com.csuft.zzc.schoolfellow.base.utils.AppContextGetter;

/**
 * Created by wangzhi on 16/5/6.
 */
public class ScToast {
    public static void toast(String msg) {
        Toast.makeText(AppContextGetter.instance().get(), msg, Toast.LENGTH_SHORT).show();
    }
}
