package com.csuft.zzc.schoolfellow.im.data;

import com.csuft.zzc.schoolfellow.base.data.QueryData;
import com.csuft.zzc.schoolfellow.host.data.UserData;

import java.util.List;

/**
 * Created by wangzhi on 16/5/4.
 */
public class ContactsData extends QueryData {
    public Result result;

    public static class Result {
        public List<UserData> users;
    }

}
