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
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.csuft.zzc.schoolfellow.base.utils.ScreenUtil;
import com.csuft.zzc.schoolfellow.circle.data.NewsBannerData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzhi on 16/3/10.
 */
public class AutoScrollViewPager extends AbsAutoScrollLayout<ViewPager> {

    ViewPagerAdapter mAdapter;
    private List<ViewPager.OnPageChangeListener> mOnPageChangeListeners = new ArrayList<>();

    public AutoScrollViewPager(Context context) {
        super(context);
    }

    public AutoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void autoScrollInternal() {
        mContainer.setCurrentItem(mContainer.getCurrentItem() + 1, true);
    }

    @Override
    protected View makeIndicatorView() {
        BannerDotView bannerDotView = (BannerDotView) mInflater.inflate(R.layout.banner_dot_view, mIndicatorContainer, false);
        Log.i("wangzhi", String.valueOf(bannerDotView.getLayoutParams()));
        return bannerDotView;
    }

    @Override
    protected ViewPager obtainContainer() {
        final ViewPager viewPager = new ViewPager(getContext());

        mAdapter = new ViewPagerAdapter();
        viewPager.setOverScrollMode(OVER_SCROLL_NEVER);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(1);
//        viewPager.addOnPageChangeListener(new CircularViewPagerHandler(viewPager));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                for (ViewPager.OnPageChangeListener listener : mOnPageChangeListeners) {
                    listener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(final int position) {
//                ScLog.i("onPageSelected " + position);
                viewPager.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        ScLog.i("onPageSelected  delay " + position);
                        if (position == 0) {
                            viewPager.setCurrentItem(getBannerCount(), false);
                        } else if (position == getBannerCount() + 1) {
                            viewPager.setCurrentItem(1, false); //notice how this jumps to position 1, and not position 0. Position 0 is the fake page!
                        }
                        for (ViewPager.OnPageChangeListener listener : mOnPageChangeListeners) {
                            listener.onPageSelected(mapIndex(position));
                        }
                        changeIndicator(position);
                    }
                }, 300);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                for (ViewPager.OnPageChangeListener listener : mOnPageChangeListeners) {
                    listener.onPageScrollStateChanged(state);
                }

            }
        });
        return viewPager;
    }


    class ViewPagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return getBannerCount() + 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            if (mDataList == null) {
                return null;
            }
            position = mapIndex(position);
            final NewsBannerData.BannerItem data = mDataList.get(position);

            View item = null;
            if (itemFactory != null) {
                item = itemFactory.createItemView(data);
            } else {
                item = createOwnItemView(data);
            }
            container.addView(item);
            final int finalPosition = position;
            item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListeners == null) {
                        return;
                    }
                    for (OnItemClickListener listener : mOnItemClickListeners) {
                        listener.onItemClick(v, finalPosition, data);
                    }

                }
            });
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

    protected View createOwnItemView(NewsBannerData.BannerItem data) {
        WebImageView item = new WebImageView(getContext());
        item.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        item.setImageUrl(data.imgUrl, ScreenUtil.instance().getScreenWidth(), ScreenUtil.instance().dip2px(120));
        item.setBackgroundColor(Color.WHITE);
        return item;
    }

    @Override
    protected void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
        mContainer.setCurrentItem(1);
        changeIndicator(1);
    }


    public int mapIndex(int pos) {
        int result = 0;
        if (pos == 0) {
            result = getBannerCount() - 1;
        } else if (pos == getBannerCount() + 1) {
            result = 0;
        } else {
            result = pos - 1;
        }
        return result;
    }

    protected void changeIndicator(int pos) {
        pos = mapIndex(pos);
        int childCount = mIndicatorContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View dotView = mIndicatorContainer.getChildAt(i);
            if (dotView instanceof BannerDotView) {
                ((BannerDotView) dotView).setBannerSelected(i == pos);
            }
        }
    }

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        if (mOnPageChangeListeners == null) {
            mOnPageChangeListeners = new ArrayList<>();
        }
        mOnPageChangeListeners.add(listener);
    }
}
