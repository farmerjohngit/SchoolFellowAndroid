package com.csuft.zzc.schoolfellow.base.view;

import android.support.v4.view.ViewPager;

/**
 * Created by wangzhi on 16/5/1.
 */
public class CircularViewPagerHandler implements ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    private int mCurrentPosition;
    private int mScrollState;

    public CircularViewPagerHandler(final ViewPager viewPager) {
        mViewPager = viewPager;
    }

    @Override
    public void onPageSelected(final int position) {

    }

    @Override
    public void onPageScrollStateChanged(final int state) {
    }

    @Override
    public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
    }
}