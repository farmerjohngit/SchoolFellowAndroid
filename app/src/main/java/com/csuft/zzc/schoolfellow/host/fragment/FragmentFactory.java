package com.csuft.zzc.schoolfellow.host.fragment;

import com.csuft.zzc.schoolfellow.base.fragment.BaseFragment;
import com.csuft.zzc.schoolfellow.circle.fragment.SchoolCircleFragment;
import com.csuft.zzc.schoolfellow.host.PageData;
import com.csuft.zzc.schoolfellow.im.fragment.ChatFragment;

/**
 * Created by wangzhi on 16/3/9.
 */
public class FragmentFactory {


    public static final String PAGER_SCHOOL_LIFE = "PAGER_SCHOOL_LIFE";
    public static final String PAGER_SCHOOL_LIFE_TITLE = "校友圈";

    public static final String PAGER_CHAT = "PAGER_CHAT";
    public static final String PAGER_CHAT_TITLE = "聊聊聊";


    public static final String PAGER_ME = "PAGER_ME";
    public static final String PAGER_ME_TITLE = "我的";

    public static BaseFragment getFragment(PageData pageData) {

        BaseFragment baseFragment = null;
        switch (pageData.pageName) {
            case PAGER_SCHOOL_LIFE:
                baseFragment = new SchoolCircleFragment();
                break;
            case PAGER_CHAT:
                baseFragment = new ChatFragment();
                break;
            case PAGER_ME:
                baseFragment = new MeFragment();
                break;
        }

        return baseFragment;
    }
}
