package com.csuft.zzc.schoolfellow.host;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.act.BaseFragmentActivity;
import com.csuft.zzc.schoolfellow.base.utils.BitmapUtil;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.csuft.zzc.schoolfellow.circle.fragment.SchoolCircleFragment;
import com.csuft.zzc.schoolfellow.host.fragment.FragmentFactory;
import com.csuft.zzc.schoolfellow.host.fragment.HostFragmentTabHost;
import com.csuft.zzc.schoolfellow.im.fragment.ChatFragment;
import com.csuft.zzc.schoolfellow.me.MeFragment;

import java.util.ArrayList;
import java.util.List;


public class HostActivity extends BaseFragmentActivity {
//
//    TabLayout mTablayout;
//
//
//    ViewPager mPager;

    HostFragmentTabHost mTabHost;
    FrameLayout mContent;


    private List<PageData> tabList = new ArrayList<>();
    private List<Class> fragmentList = new ArrayList<>();

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.host_act);
        init();
        initView();


    }

    private void init() {
        PageData pageData = new PageData(FragmentFactory.PAGER_SCHOOL_LIFE_TITLE, FragmentFactory.PAGER_SCHOOL_LIFE);
        tabList.add(pageData);
        fragmentList.add(SchoolCircleFragment.class);

        pageData = new PageData(FragmentFactory.PAGER_CHAT_TITLE, FragmentFactory.PAGER_CHAT);
        tabList.add(pageData);
        fragmentList.add(ChatFragment.class);


        pageData = new PageData(FragmentFactory.PAGER_ME_TITLE, FragmentFactory.PAGER_ME);
        tabList.add(pageData);
        fragmentList.add(MeFragment.class);
    }


    private void initView() {
        mTabHost = (HostFragmentTabHost) findViewById(R.id.host_tab_host);
        mContent = (FrameLayout) findViewById(R.id.host_content);

        mTabHost.setup(this, getSupportFragmentManager(), R.id.host_content);
        mTabHost.getTabWidget().setDividerDrawable(null);
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
//                switch (tabId) {
//                    case FragmentFactory.PAGER_SCHOOL_LIFE_TITLE:
//                        break;
//                    case FragmentFactory.PAGER_CHAT_TITLE:
//                        break;
//                    case FragmentFactory.PAGER_ME_TITLE:
//                        break;
//                }
//                if (!UserManager.getInstance().isLogin()) {
//                    startActivity(new Intent(HostActivity.this, LoginAct.class));
//                }
            }
        });
        for (int i = 0; i < tabList.size(); i++) {
            PageData pageData = tabList.get(i);
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(pageData.title).setIndicator(getTabItemView(pageData));
            //将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragmentList.get(i), null);

        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        String initFragment = getIntent().getStringExtra("init_fragment");
        ScLog.i("===>" + initFragment);
        if (!TextUtils.isEmpty(initFragment)) {
            mTabHost.setCurrentTabByTag(initFragment);
        }

    }


    public View getTabItemView(PageData data) {
        View itemView = LayoutInflater.from(this).inflate(R.layout.host_tab_item, null);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.tab_item_img);
        switch (data.title) {
            case FragmentFactory.PAGER_SCHOOL_LIFE_TITLE:
                imageView.setImageDrawable(BitmapUtil.getDrawable(this, R.drawable.host_school_circle_bg));
                break;
            case FragmentFactory.PAGER_CHAT_TITLE:
                imageView.setImageDrawable(BitmapUtil.getDrawable(this, R.drawable.host_im_bg));
                break;
            case FragmentFactory.PAGER_ME_TITLE:
                imageView.setImageDrawable(BitmapUtil.getDrawable(this, R.drawable.host_me_bg));
                break;
        }

        TextView textView = (TextView) itemView.findViewById(R.id.tab_item_txt);
        textView.setText(data.title);
        return itemView;
    }


    public void clickMe(View view) {
        Log.i("wangzhi", "clickme");
    }


}
