package com.csuft.zzc.schoolfellow.im.data;

import com.csuft.zzc.schoolfellow.base.data.BaseData;

import java.util.List;

/**
 * Created by wangzhi on 16/5/19.
 */
public class ImMsgData extends BaseData {

    public Result result;

    public static class Result {
        public List<ImMsgItem> msgList;
    }

    public static class ImMsgItem {
        public String from;

        public String to;

        public String msg;

        public String msgType;

        public String time;

    }


}
