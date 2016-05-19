package com.csuft.zzc.schoolfellow.login;

import com.csuft.zzc.schoolfellow.base.data.QueryData;
import com.csuft.zzc.schoolfellow.host.data.UserData;

import java.util.List;

/**
 * Created by wangzhi on 16/5/4.
 */
public class LoginData extends QueryData {

    public Result result;

    public class Result extends QueryData.Result {
        public UserData user;
        public List<UserData> users;

    }
}
