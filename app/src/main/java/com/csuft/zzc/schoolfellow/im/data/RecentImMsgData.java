package com.csuft.zzc.schoolfellow.im.data;


import com.csuft.zzc.schoolfellow.base.data.BaseData;
import com.csuft.zzc.schoolfellow.host.data.UserData;

import java.util.List;

public class RecentImMsgData extends BaseData {

    public Result result;

    public static class Result {
        public List<RecentImMsgItem> msgList;
    }

    public static class RecentImMsgItem {

        public UserData user;


        public String msg;

        public String msgType;

        public String time;

    }


}