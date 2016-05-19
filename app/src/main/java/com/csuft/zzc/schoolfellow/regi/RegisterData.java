package com.csuft.zzc.schoolfellow.regi;

import com.csuft.zzc.schoolfellow.base.data.BaseData;
import com.csuft.zzc.schoolfellow.host.data.UserData;

/**
 * Created by wangzhi on 16/5/4.
 */
public class RegisterData extends BaseData {
    public Result result;
    public static final int REGI_SUCCESS = 0;
    public static final int REGI_FAILURE = 1;

    public class Result {
        public UserData user;
        public int regiResult;
        public String regiMsg;

    }
}
