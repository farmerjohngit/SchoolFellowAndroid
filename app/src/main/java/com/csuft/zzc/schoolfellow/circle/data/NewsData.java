package com.csuft.zzc.schoolfellow.circle.data;

import android.text.TextUtils;

import com.csuft.zzc.schoolfellow.base.data.BaseData;

import java.util.List;

public class NewsData extends BaseData {
    public Result result;

    public static class Result {
        public List<NewsItem> newsList;
    }

    public static class NewsItem {
        public long indexId;

        public String _id;

        public String title;

        public String subTitle;

        public String time;

        public List<Paragraph> paragraphs;


        public String getFirstForSpecType(int type) {
            for (NewsData.Paragraph paragraph : paragraphs) {
                if (paragraph.contentType == type
                        && paragraph.content != null
                        && paragraph.content.size() > 0 && !TextUtils.isEmpty(paragraph.content.get(0).trim())) {
                    return paragraph.content.get(0);
                }
            }
            return null;
        }
    }


    public static class Paragraph {
        public static final int TEXT_TYPE = 0;
        public static final int IMG_TYPE = 1;
        public int contentType;

        public List<String> content;


    }
}




