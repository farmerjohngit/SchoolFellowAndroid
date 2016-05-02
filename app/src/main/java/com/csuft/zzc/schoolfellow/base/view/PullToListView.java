package com.csuft.zzc.schoolfellow.base.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by wangzhi on 16/3/12.
 */
public class PullToListView extends AbsPullToRefresh<RefreshIndicator, ListView> {


    public PullToListView(Context context) {
        super(context);
    }

    public PullToListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected RefreshIndicator createIndicatorView() {
//        LinearLayout linearLayout = new LinearLayout(getContext());
//        linearLayout.setBackgroundResource(R.drawable.pulltorefresh_header);
//        linearLayout.setLayoutParams(new LinearLayoutCompat.LayoutParams(mScreenUtil.dip2px(20), mScreenUtil.dip2px(20)));
//        return linearLayout;
        RefreshIndicator refreshIndicator = new RefreshIndicator(getContext());
        refreshIndicator.setLayoutParams(new LinearLayoutCompat.LayoutParams(mScreenUtil.dip2px(26), mScreenUtil.dip2px(26)));
        return refreshIndicator;
    }

    @Override
    protected ListView createRefreshView() {

        ListView listView = new ListView(getContext());
        listView.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return listView;
//        imItemDataList = DataFactory.createImItemData(30);
    }
}
