package com.csuft.zzc.schoolfellow.user;

import com.csuft.zzc.schoolfellow.login.LoginManager;

/**
 * Created by wangzhi on 16/5/3.
 */
public class UserManager {
    static UserManager sInstance;

    LoginManager mLoginManager;

    public static UserManager getInstance() {
        return sInstance;
    }

    public boolean isLogin() {
        return false;
    }

}
