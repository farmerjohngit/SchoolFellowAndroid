package com.csuft.zzc.schoolfellow.base.data;

import com.csuft.zzc.schoolfellow.circle.data.SchoolCirclePagerData;
import com.csuft.zzc.schoolfellow.im.act.ContactPersonAct.ContactPersonTitleData;
import com.csuft.zzc.schoolfellow.search.SearchAct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzhi on 16/3/10.
 */
public class DataFactory {
    static String[] imgUrls = new String[]{"http://s16.mogucdn.com/p1/160309/upload_ie4dqmrvha3dqmbtg4zdambqgiyde_715x530.jpg", "http://s16.mogucdn.com/p1/160309/upload_ie4gemlfmezwemjtg4zdambqgiyde_715x530.jpg"};
    static String[] schoolCircleTitles = new String[]{"新闻", "动态"};
    static String[] contactPersonTitles = new String[]{"校友", "老师"};
    static String[] searchTitles = new String[]{"联系人", "群"};
    static String[] userInfoTitles = new String[]{"基本信息", "动态"};


//    public static List<BannerData> createBannerDataList(int count) {
//        List<BannerData> dataList = new ArrayList<>();
//        for (int i = 0; i < count; i++) {
//            BannerData data = new BannerData();
//            data.imgUrl = imgUrls[i % imgUrls.length];
//            dataList.add(data);
//        }
//        return dataList;
//    }

    public static class TitleData {
        public String title;
        public int type;
        public static final int TYPE_1 = 0;
        public static final int TYPE_2 = 1;
    }

    public static List<TitleData> createUserInfoPagerData() {
        List<TitleData> dataList = new ArrayList<>();
        TitleData data = new TitleData();
        data.title = userInfoTitles[0];
        data.type = TitleData.TYPE_1;
        dataList.add(data);
        TitleData data1 = new TitleData();
        data1.title = userInfoTitles[1];
        data1.type = TitleData.TYPE_2;
        dataList.add(data1);
        return dataList;
    }

    public static List<SearchAct.SearchTitleData> createSearchPagerData() {
        List<SearchAct.SearchTitleData> dataList = new ArrayList<>();
        SearchAct.SearchTitleData data = new SearchAct.SearchTitleData();
        data.title = searchTitles[0];
        data.type = SearchAct.SearchTitleData.USER_TYPE;
        dataList.add(data);
        SearchAct.SearchTitleData data1 = new SearchAct.SearchTitleData();
        data1.title = searchTitles[1];
        data1.type = SearchAct.SearchTitleData.GROUP_TYPE;
        dataList.add(data1);
        return dataList;
    }

    public static List<ContactPersonTitleData> createContactPersonPagerData() {
        List<ContactPersonTitleData> dataList = new ArrayList<>();
        ContactPersonTitleData data = new ContactPersonTitleData();
        data.title = contactPersonTitles[0];
        data.type = ContactPersonTitleData.FRI_TYPE;
        dataList.add(data);
        ContactPersonTitleData data1 = new ContactPersonTitleData();
        data1.title = contactPersonTitles[1];
        data1.type = ContactPersonTitleData.TEA_TYPE;
        dataList.add(data1);
        return dataList;
    }


    public static List<SchoolCirclePagerData> createSchoolCirclePagerData(int count) {
        List<SchoolCirclePagerData> dataList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            SchoolCirclePagerData data = new SchoolCirclePagerData();
            data.title = schoolCircleTitles[i % schoolCircleTitles.length];
            data.itemDataList = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                SchoolCirclePagerData.Info info = new SchoolCirclePagerData.Info();
                info.name = "wangzhi";
                info.headImg = "http://avatar.csdn.net/F/F/5/1_lmj623565791.jpg";
                info.content = "this is content";
                info.time = "now time";
                data.itemDataList.add(info);
            }

            dataList.add(data);
        }
        return dataList;
    }


//    public static List<ImItemData> createImItemData(int count) {
//        List<ImItemData> list = new ArrayList<>();
//        for (int i = 0; i < count; i++) {
//            ImItemData imItemData = new ImItemData();
//            imItemData.name = "wangzhi   " + i;
//            imItemData.imgUrl = "http://avatar.csdn.net/F/F/5/1_lmj623565791.jpg";
//            imItemData.content = "this is content";
//            imItemData.time = "now time";
//            list.add(imItemData);
//        }
//        return list;
//    }
}
