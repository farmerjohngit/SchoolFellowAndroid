package com.csuft.zzc.schoolfellow.circle.data;

import com.csuft.zzc.schoolfellow.base.data.BaseData;

import java.util.List;

/**
 * Created by wangzhi on 16/5/18.
 */
public class DynamicData extends BaseData {
    public Result result;

    public static class Result {
        public List<DynamicItem> dynamicList;
    }

    public static class DynamicItem {
        public List<String> imgs;
        public String text = "";
        public String avatar = "";
        public String userName = "";
        public String name = "";
        public long time;
    }
}
