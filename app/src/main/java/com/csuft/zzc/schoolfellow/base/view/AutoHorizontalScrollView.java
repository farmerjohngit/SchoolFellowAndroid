package com.csuft.zzc.schoolfellow.base.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;

import com.csuft.zzc.schoolfellow.R;

/**
 * Created by wangzhi on 16/5/2.
 */
public class AutoHorizontalScrollView extends AbsAutoScrollLayout<HorizontalScrollView> {
    public AutoHorizontalScrollView(Context context) {
        super(context);
    }

    public AutoHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void notifyDataSetChanged() {

    }

    @Override
    protected View makeIndicatorView() {
        BannerDotView bannerDotView = (BannerDotView) mInflater.inflate(R.layout.banner_dot_view, mIndicatorContainer, false);
        Log.i("wangzhi", String.valueOf(bannerDotView.getLayoutParams()));
        return bannerDotView;
    }

    @Override
    protected HorizontalScrollView obtainContainer() {
        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(getContext());
//        horizontalScrollView.setA
        return horizontalScrollView;
    }
}
