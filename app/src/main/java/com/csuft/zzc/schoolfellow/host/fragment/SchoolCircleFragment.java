package com.csuft.zzc.schoolfellow.host.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.data.DataFactory;
import com.csuft.zzc.schoolfellow.base.fragment.BaseFragment;
import com.csuft.zzc.schoolfellow.base.view.AutoScrollViewPager;
import com.csuft.zzc.schoolfellow.host.data.SchoolCirclePagerData;

import java.util.List;

/**
 * Created by wangzhi on 16/3/9.
 */
public class SchoolCircleFragment extends BaseFragment {

    List<SchoolCirclePagerData> mDataList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("wangzhi", "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected View createContentView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.school_circle_fra, container, false);
    }

    @Override
    protected void initView() {
        initData();

        AutoScrollViewPager scrollPager = (AutoScrollViewPager) mContentView.findViewById(R.id.scrollPager);
        scrollPager.setBannerData(DataFactory.createBannerDataList(2));


        ViewPager pager = (ViewPager) mContentView.findViewById(R.id.id_stick_pager);

        TabLayout tabLayout = (TabLayout) mContentView.findViewById(R.id.id_stick_nav);

        SchoolCirclePagerAdapter adapter = new SchoolCirclePagerAdapter(getFragmentManager());
        pager.setAdapter(adapter);

        tabLayout.setTabsFromPagerAdapter(adapter);
        tabLayout.setTabTextColors(Color.BLACK, Color.RED);
        tabLayout.setupWithViewPager(pager);
    }


    private void initData() {
        mDataList = DataFactory.createSchoolCirclePagerData(2);
    }


    class SchoolCirclePagerAdapter extends FragmentPagerAdapter {

        public SchoolCirclePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            WaterfallFragment waterfallFragment = WaterfallFragment.create(mDataList.get(position).itemDataList);
            return waterfallFragment;
        }

        @Override
        public int getCount() {
            return mDataList == null ? 0 : mDataList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mDataList.get(position).title;
        }
    }


}
