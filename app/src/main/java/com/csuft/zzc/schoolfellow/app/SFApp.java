package com.csuft.zzc.schoolfellow.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.csuft.zzc.schoolfellow.BuildConfig;
import com.csuft.zzc.schoolfellow.base.utils.AppContextGetter;
import com.mogujie.layoutcast.LayoutCast;

import java.io.File;

/**
 * Created by wangzhi on 16/3/10.
 */
public class SFApp extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        AppContextGetter.instance().setApplicationContext(this);
        if (BuildConfig.DEBUG) {
            LayoutCast.init(this);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        try {
//            ApplicationInfo appInfo = getApplicationInfo(this);
//            File dexDir = new File(appInfo.dataDir, "secondary-dexes");
//            File[] fIles = dexDir.listFiles();
//            for (File file : fIles) {
//                Log.i("wangzhi", file.getAbsolutePath());
//            }
//        } catch (PackageManager.NameNotFoundException e1) {
//            e1.printStackTrace();
//        }

    }

    private static ApplicationInfo getApplicationInfo(Context context) throws PackageManager.NameNotFoundException {
        PackageManager pm;
        String packageName;
        try {
            pm = context.getPackageManager();
            packageName = context.getPackageName();
        } catch (RuntimeException var4) {
            Log.w("MultiDex", "Failure while trying to obtain ApplicationInfo from Context. Must be running in test mode. Skip patching.", var4);
            return null;
        }

        if (pm != null && packageName != null) {
            ApplicationInfo applicationInfo = pm.getApplicationInfo(packageName, 128);
            return applicationInfo;
        } else {
            return null;
        }
    }

}
