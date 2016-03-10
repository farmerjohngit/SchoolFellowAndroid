package com.csuft.zzc.schoolfellow.host.data;

import java.util.List;

/**
 * Created by wangzhi on 16/3/11.
 */
public class SchoolCirclePagerData {
    public String title = "";
    public List<Info> itemDataList = null;


   public static class Info {
        public String name = "";
        public String time = "";
        public String content = "";
        public boolean isFollow;
        public String headImg = "";
        public List<String> imgs = null;
    }
}
