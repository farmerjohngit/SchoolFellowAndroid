package com.csuft.zzc.schoolfellow.base.utils;

import android.app.Application;

/**
 * Created by wangzhi on 16/3/10.
 */
public class AppContextGetter {

    private static volatile AppContextGetter sInstance;

    private static Application sApplication;

    public static synchronized AppContextGetter instance() {
        if (sInstance == null) {
            sInstance = new AppContextGetter();
        }

        return sInstance;
    }

    public void setApplicationContext(Application application) {
        sApplication = application;
    }

    public Application get() {
        return sApplication;
    }

}
