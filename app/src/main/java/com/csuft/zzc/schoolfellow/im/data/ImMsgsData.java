package com.csuft.zzc.schoolfellow.im.data;


import com.csuft.zzc.schoolfellow.base.data.BaseData;

import java.util.List;

public class ImMsgsData extends BaseData {

    public Result result;

    public static class Result {
        public List<ImMsgItem> msgList;
    }

    public static class ImMsgItem {
        public String userId;

        public String userName;

        public String userGroup;

        public String avatar;

        public String msg;

        public String msgType;

        public String time;

    }


}