package com.csuft.zzc.schoolfellow.base.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by wangzhi on 16/3/12.
 */
public abstract class PullToRefresh extends LinearLayout {

    protected View mRefreshHeaderView;
    protected View mRefreshView;
    private int mTouchSlop;
    private int downX;
    private int downY;
    private float damp;
    private int mPullMaxDis;

    public PullToRefresh(Context context) {
        this(context, null);
    }

    public PullToRefresh(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    protected void initView() {
        setOrientation(VERTICAL);

        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

        mRefreshHeaderView = createHeaderView();
        mRefreshView = createRefreshView();

        this.addView(mRefreshHeaderView);
        this.addView(mRefreshView);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        LinearLayout.LayoutParams params = (LayoutParams) mRefreshHeaderView.getLayoutParams();
        params.topMargin = -mRefreshHeaderView.getMeasuredHeight();

    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int nowX = (int) ev.getX();
        int nowY = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = nowX;
                downY = nowY;
                damp = 1;
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = nowX - downX;
                int dy = nowY - downY;
                if (dy > mTouchSlop && refreshViewIsOnTop()) {
                    damp = Math.abs(dy) / (float) mPullMaxDis;
                    LinearLayout.LayoutParams params = (LayoutParams) mRefreshHeaderView.getLayoutParams();
                    params.topMargin += dy * damp;
                }

                break;
            case MotionEvent.ACTION_UP:
                break;

        }

        return super.onInterceptTouchEvent(ev);
    }

    protected abstract View createHeaderView();


    protected abstract View createRefreshView();


    protected boolean refreshViewIsOnTop() {
        if (this.mRefreshView instanceof ScrollView) {
            return ((ScrollView) this.mRefreshView).getChildCount() == 0 ? true : this.mRefreshView.getScrollY() <= 0;
        } else if (this.mRefreshView instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) this.mRefreshView;
            View child = recyclerView.getChildAt(0);
            return null == child ? true : child.getTop() >= 0;
        } else {
            return true;
        }
    }


}
