package com.csuft.zzc.schoolfellow.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.csuft.zzc.schoolfellow.BuildConfig;
import com.csuft.zzc.schoolfellow.base.utils.AppContextGetter;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.csuft.zzc.schoolfellow.base.view.ScToast;
import com.csuft.zzc.schoolfellow.host.SplashAct;
import com.csuft.zzc.schoolfellow.im.SCMessageHandler;
import com.mogujie.layoutcast.LayoutCast;

/**
 * Created by wangzhi on 16/3/10.
 */
public class SFApp extends Application {
    private static final String TAG = "SFApp";

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
        AVOSCloud.initialize(this, "TRvKz7nrfnFhTJv5F8E3Xds5-gzGzoHsz", "XE9oYGAAL8VV31kID07iFHqB");
        AVIMMessageManager.registerDefaultMessageHandler(SCMessageHandler.getInstance());
        AVInstallation.getCurrentInstallation().saveInBackground();
        AVOSCloud.setDebugLogEnabled(BuildConfig.DEBUG);
        PushService.setDefaultPushCallback(this, SplashAct.class);
    }


//    private static ApplicationInfo getApplicationInfo(Context context) throws PackageManager.NameNotFoundException {
//        PackageManager pm;
//        String packageName;
//        try {
//            pm = context.getPackageManager();
//            packageName = context.getPackageName();
//        } catch (RuntimeException var4) {
//            Log.w("MultiDex", "Failure while trying to obtain ApplicationInfo from Context. Must be running in test mode. Skip patching.", var4);
//            return null;
//        }
//
//        if (pm != null && packageName != null) {
//            ApplicationInfo applicationInfo = pm.getApplicationInfo(packageName, 128);
//            return applicationInfo;
//        } else {
//            return null;
//        }
//    }

}
