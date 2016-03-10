package com.csuft.zzc.schoolfellow.app;

import android.app.Application;
import android.content.Context;

import com.csuft.zzc.schoolfellow.base.utils.AppContextGetter;

/**
 * Created by wangzhi on 16/3/10.
 */
public class SFApp extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        AppContextGetter.instance().setApplicationContext(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }


}
