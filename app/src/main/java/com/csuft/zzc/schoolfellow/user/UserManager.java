package com.csuft.zzc.schoolfellow.user;

import android.text.TextUtils;

import com.csuft.zzc.schoolfellow.base.im.ImUtil;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.csuft.zzc.schoolfellow.base.utils.ScSharedPreferences;
import com.csuft.zzc.schoolfellow.host.data.UserData;
import com.csuft.zzc.schoolfellow.login.LoginManager;
import com.google.gson.Gson;

/**
 * Created by wangzhi on 16/5/3.
 */
public class UserManager {
    static UserManager sInstance;

    LoginManager mLoginManager;
    UserData mUser;
    Gson mGson;

    private UserManager() {
        mLoginManager = new LoginManager();
        mGson = new Gson();
    }

    public static UserManager getInstance() {
        if (sInstance == null) {
            sInstance = new UserManager();
        }
        return sInstance;
    }

    public boolean isLogin() {
        return getUser() != null;
    }

    public void setUser(UserData user) {
        setUser(user, false);
    }

    public void setUser(UserData user, boolean save) {
        mUser = user;
        ImUtil.login(user.userName);
        if (save) {
            try {
                String str = mGson.toJson(user);
                new ScSharedPreferences("app").write("user", str);
                ScLog.i("write succ " + str);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    public UserData getUser() {
        if (mUser == null) {
            ScLog.i(TAG, "user is null");
            String userStr = new ScSharedPreferences("app").get("user", "");
            ScLog.i(TAG, "userStr is   " + userStr);
            if (TextUtils.isEmpty(userStr)) {
                ScLog.i(TAG, "not sp info");
                return null;
            } else {
                try {
                    mUser = mGson.fromJson(userStr, UserData.class);
                    ImUtil.login(mUser.userName);
                    ScLog.i(TAG, "form json");
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

        return mUser;
    }

    public String getUserName() {
        UserData user = getUser();
        return user == null ? null : user.userName;
    }

    public void logout() {
        ImUtil.logout(getUserName());
        mUser = null;
        new ScSharedPreferences("app").write("user", "");
    }

    private static final String TAG = "UserManager";
}
