package com.csuft.zzc.schoolfellow.base.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ListView;

import com.csuft.zzc.schoolfellow.R;

/**
 * Created by wangzhi on 16/5/2.
 */
public class PullToStickNavView extends AbsPullToRefresh<RefreshIndicator, StickNavLayout> {
    public PullToStickNavView(Context context) {
        super(context);
    }

    public PullToStickNavView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected RefreshIndicator createIndicatorView() {
        RefreshIndicator refreshIndicator = new RefreshIndicator(getContext());
        refreshIndicator.setLayoutParams(new LinearLayoutCompat.LayoutParams(mScreenUtil.dip2px(26), mScreenUtil.dip2px(26)));
        return refreshIndicator;
    }

    @Override
    protected StickNavLayout createRefreshView() {
        return (StickNavLayout) LayoutInflater.from(getContext()).inflate(R.layout.school_circle_fra_test, null);
    }
}
