package com.csuft.zzc.schoolfellow.base.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.data.BannerData;
import com.csuft.zzc.schoolfellow.base.utils.ScreenUtil;

/**
 * Created by wangzhi on 16/3/10.
 */
public class AutoScrollViewPager extends AbsAutoScrollLayout<ViewPager> {

    ViewPagerAdapter mAdapter;

    public AutoScrollViewPager(Context context) {
        super(context);
    }

    public AutoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View makeIndicatorView() {
        BannerDotView bannerDotView = (BannerDotView) mInflater.inflate(R.layout.banner_dot_view, mIndicatorContainer, false);
        Log.i("wangzhi", String.valueOf(bannerDotView.getLayoutParams()));
        return bannerDotView;
    }

    @Override
    protected ViewPager obtainContainer() {
        ViewPager viewPager = new ViewPager(getContext());
        mAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(defaultItem);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("wangzhi", "onPageSelected");
                changeIndicator(position % getChildCount());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return viewPager;
    }


    class ViewPagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return getBannerCount() > 0 ? Integer.MAX_VALUE : 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position % getBannerCount();
            BannerData data = mDataList.get(position);

            View item = null;
            if (itemFactory != null) {
                item = itemFactory.createItemViewm(data);
            } else {
                item = createOwnItemView(data);
            }
            container.addView(item);
            return item;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (object != null && object instanceof View) {
                container.removeView((View) object);
            }
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    protected View createOwnItemView(BannerData data) {
        WebImageView item = new WebImageView(getContext());
        item.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        item.setImageUrl(data.imgUrl, ScreenUtil.instance().getScreenWidth(), ScreenUtil.instance().dip2px(100));
        item.setBackgroundColor(Color.RED);
        return item;
    }

    @Override
    protected void notifyDataSetChanged() {
        mContainer.setCurrentItem(defaultItem);
        changeIndicator(defaultItem);
        mAdapter.notifyDataSetChanged();
    }


    protected void changeIndicator(int pos) {
        int childCount = mIndicatorContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View dotView = mIndicatorContainer.getChildAt(i);
            if (dotView instanceof BannerDotView) {
                ((BannerDotView) dotView).setBannerSelected(i == pos);
            }
        }
    }
}
