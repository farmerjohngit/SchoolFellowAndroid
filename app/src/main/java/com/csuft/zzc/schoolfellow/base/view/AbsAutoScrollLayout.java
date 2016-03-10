package com.csuft.zzc.schoolfellow.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.csuft.zzc.schoolfellow.base.data.BannerData;
import com.csuft.zzc.schoolfellow.base.utils.ScreenUtil;

import java.util.List;

/**
 * Created by wangzhi on 16/3/10.
 */
public abstract class AbsAutoScrollLayout<T extends View> extends FrameLayout {


    protected T mContainer;
    protected LinearLayout mIndicatorContainer;
    protected List<BannerData> mDataList;
    protected LayoutInflater mInflater;
    protected BannerItemFactory itemFactory;
    protected ScreenUtil mScreenUtil;
    protected int defaultItem=0;


    public AbsAutoScrollLayout(Context context) {
        super(context);
        init();
    }

    public AbsAutoScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        mScreenUtil = ScreenUtil.instance();
        mInflater = LayoutInflater.from(getContext());

        mContainer = obtainContainer();
        mContainer.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.addView(mContainer);


        mIndicatorContainer = new LinearLayout(getContext());
        LayoutParams inParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, mScreenUtil.dip2px(10), Gravity.LEFT | Gravity.BOTTOM);
        inParams.setMargins(mScreenUtil.dip2px(20),0,0,mScreenUtil.dip2px(6));
        mIndicatorContainer.setLayoutParams(inParams);
        mIndicatorContainer.setOrientation(LinearLayout.HORIZONTAL);
        this.addView(mIndicatorContainer);

    }


    public void setBannerData(List<BannerData> data) {
        mDataList = data;
        mIndicatorContainer.removeAllViews();
        for (int i = 0; i < mDataList.size(); i++) {
            mIndicatorContainer.addView(makeIndicatorView());
        }
        notifyDataSetChanged();
    }


    protected int getBannerCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    protected abstract void notifyDataSetChanged();

    protected abstract View makeIndicatorView();

    protected abstract T obtainContainer();

    public interface BannerItemFactory {
        View createItemViewm(BannerData data);
    }
}
