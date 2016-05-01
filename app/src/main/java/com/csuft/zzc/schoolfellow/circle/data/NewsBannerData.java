package com.csuft.zzc.schoolfellow.circle.data;

import com.csuft.zzc.schoolfellow.base.data.BaseData;

import java.util.List;


public class NewsBannerData extends BaseData {
    public Result result;

    public static class BannerItem {
        public String imgUrl;

        public String url;

        public int width;

        public int height;

    }

    public static class Result {
        public List<BannerItem> bannerList;
    }

}