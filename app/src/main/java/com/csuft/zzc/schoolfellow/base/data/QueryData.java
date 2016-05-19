package com.csuft.zzc.schoolfellow.base.data;

/**
 * Created by wangzhi on 16/5/3.
 */
public class QueryData extends BaseData {
    public static final int SUCCESS = 0;
    public static final int FAILURE = 1;

    public class Result {
        public int responseResult;
        public String responseMsg;
    }

}
