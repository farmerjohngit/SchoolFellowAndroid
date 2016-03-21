package com.csuft.zzc.schoolfellow.base.data;

import com.csuft.zzc.schoolfellow.host.data.ImItemData;
import com.csuft.zzc.schoolfellow.host.data.SchoolCirclePagerData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzhi on 16/3/10.
 */
public class DataFactory {
    static String[] imgUrls = new String[]{"http://s16.mogucdn.com/p1/160309/upload_ie4dqmrvha3dqmbtg4zdambqgiyde_715x530.jpg", "http://s16.mogucdn.com/p1/160309/upload_ie4gemlfmezwemjtg4zdambqgiyde_715x530.jpg"};
    static String[] schoolCircleTitles = new String[]{"关注", "热门"};


    public static List<BannerData> createBannerDataList(int count) {
        List<BannerData> dataList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            BannerData data = new BannerData();
            data.imgUrl = imgUrls[i % imgUrls.length];
            dataList.add(data);
        }
        return dataList;
    }

    public static List<SchoolCirclePagerData> createSchoolCirclePagerData(int count) {
        List<SchoolCirclePagerData> dataList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            SchoolCirclePagerData data = new SchoolCirclePagerData();
//
            data.title = schoolCircleTitles[i % schoolCircleTitles.length];
            data.itemDataList = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                SchoolCirclePagerData.Info info = new SchoolCirclePagerData.Info();
                info.name = "wangzhi";
                info.headImg = "http://avatar.csdn.net/F/F/5/1_lmj623565791.jpg";
                info.content = "this is content";
                info.time = "now time";
//                info.
                data.itemDataList.add(info);
            }

            dataList.add(data);
        }
        return dataList;
    }


    public static List<ImItemData> createImItemData(int count) {
        List<ImItemData> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ImItemData imItemData = new ImItemData();
            imItemData.name = "wangzhi   " + i;
            imItemData.imgUrl = "http://avatar.csdn.net/F/F/5/1_lmj623565791.jpg";
            imItemData.content = "this is content";
            imItemData.time = "now time";
            list.add(imItemData);
        }
        return list;
    }
}
