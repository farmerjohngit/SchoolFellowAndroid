package com.csuft.zzc.schoolfellow.host;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.act.BaseFragmentActivity;
import com.csuft.zzc.schoolfellow.host.fragment.FragmentFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class HostActivity extends BaseFragmentActivity {

    TabLayout mTablayout;


    ViewPager mPager;


    private List<PageData> tabList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.host_act);
        ButterKnife.bind(this);
        init();
        initView();
    }

    private void init() {
        PageData pageData = new PageData(FragmentFactory.PAGER_SCHOOL_LIFE_TITLE, FragmentFactory.PAGER_SCHOOL_LIFE);
        tabList.add(pageData);

        pageData = new PageData(FragmentFactory.PAGER_CHAT_TITLE, FragmentFactory.PAGER_CHAT);
        tabList.add(pageData);


        pageData = new PageData(FragmentFactory.PAGER_ME_TITLE, FragmentFactory.PAGER_ME);
        tabList.add(pageData);
    }


    private void initView() {

        mTablayout= (TabLayout) findViewById(R.id.host_tablayout);
        mPager= (ViewPager) findViewById(R.id.host_vpager);

        PagerAdapter adapter = new HostPageAdapter(getSupportFragmentManager(), tabList);
        mPager.setAdapter(adapter);
        mPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("wanzghi", "mPager onclick");
            }
        });

        mTablayout.setTabTextColors(Color.WHITE, Color.RED);
        mTablayout.setupWithViewPager(mPager);
        mTablayout.setTabsFromPagerAdapter(adapter);

        mTablayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("wanzghi", "onclick");
            }
        });
    }


    public void clickMe(View view) {
        Log.i("wangzhi", "clickme");
    }
}
