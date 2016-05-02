package com.csuft.zzc.schoolfellow.base.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.csuft.zzc.schoolfellow.base.utils.ScreenUtil;

/**
 * Created by wangzhi on 16/3/12.
 */
public abstract class AbsPullToRefresh<H extends RefreshIndicator, T extends View> extends LinearLayout {
    private static final String TAG = "AbsPullToRefresh";
    protected H mRefreshHeaderView;
    protected T mRefreshView;
    private int mTouchSlop;
    private int mLastY;
    private int mLastX;
    private int mDownY;
    private int mDownX;
    private float damp;
    private int mPullMaxDis;
    protected ScreenUtil mScreenUtil;
    private LinearLayout.LayoutParams mParams;
    private int mTopExtraMargin;
    private int mTopOffset;
    private int nowMargin = 0;
    RefreshStatus refreshStatus = RefreshStatus.INIT;


    OnRefreshListener mOnRefreshListener;

    public AbsPullToRefresh(Context context) {
        this(context, null);
    }

    public AbsPullToRefresh(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    protected void initView() {
        setOrientation(VERTICAL);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mScreenUtil = ScreenUtil.instance();
        mPullMaxDis = mScreenUtil.dip2px(120);
        mTopExtraMargin = mScreenUtil.dip2px(0);

        mRefreshHeaderView = createIndicatorView();
        mRefreshView = createRefreshView();

        this.addView(mRefreshHeaderView);
        this.addView(mRefreshView);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mParams = (LayoutParams) mRefreshHeaderView.getLayoutParams();
        mTopOffset = mParams.topMargin = -mRefreshHeaderView.getMeasuredHeight() - mTopExtraMargin;
        mParams.gravity = Gravity.CENTER_HORIZONTAL;
        if (nowMargin == 0) {
            nowMargin = mParams.topMargin;
        }
        Log.i(TAG, "layout " + mTopOffset);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        ScLog.i(TAG, "onTouchEvent " + ev.getAction());
        if (refreshStatus == RefreshStatus.REFRESHING) {
            return true;
        }
        int nowY = (int) ev.getY();
        int nowX = (int) ev.getX();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = mDownY = nowY;
                mLastX = mDownX = nowX;
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = nowY - mLastY;
                if (Math.abs(dy) > 0) {
                    ScLog.i(TAG, "nowMargin: " + nowMargin + "  mTopOffset: " + mTopOffset + "<>" + refreshViewIsOnTop());
                    if (nowMargin >= mTopOffset && refreshViewIsOnTop()) {
//                        if ((nowY - mDownY) * damp <= mPullMaxDis) {
                        int moveTo = (nowY - mDownY);
                        if (moveTo < mTopOffset) {
                            moveTo = mTopOffset;
                        } else if (moveTo > mPullMaxDis) {
                            moveTo = mPullMaxDis;
                        }
                        float percent = Math.abs(moveTo - mTopOffset) * 1.0f / (mPullMaxDis + Math.abs(mTopOffset));
                        ScLog.i(TAG, "percent " + percent + "  :   " + (percent * -0.3 + 0.5));
                        moveHeader((int) ((nowY - mDownY) * (percent * -0.4 + 0.8)));
//                        }
                    } else {
                        ScLog.i(TAG, "redispatch ");
                        MotionEvent event = MotionEvent.obtain(ev);
                        event.setAction(MotionEvent.ACTION_CANCEL);
                        dispatchTouchEvent(event);
                        event.setAction(MotionEvent.ACTION_DOWN);
                        dispatchTouchEvent(event);
                    }
                    mLastY = nowY;
                    mLastX = nowX;
                }

                break;
            case MotionEvent.ACTION_UP:
                ScLog.i(TAG, "nowMargin:   " + nowMargin + "  mPullMaxDis:   " + mPullMaxDis);
                if (nowMargin == mPullMaxDis) {
                    refreshStatus = RefreshStatus.REFRESHING;
                    if (mOnRefreshListener != null) {
                        mOnRefreshListener.onStartRefresh();
                    }
                    mRefreshHeaderView.refreshAnimation();
                } else {
                    moveHeader(mTopOffset);
                }
                break;
        }
        return super.onTouchEvent(ev);

    }


    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        Log.i("wangzhi", "requestDisallowInterceptTouchEvent " + disallowIntercept);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (refreshStatus == RefreshStatus.REFRESHING) {
            return true;
        }
        boolean isIntercept = false;
        int nowY = (int) ev.getY();
        int nowX = (int) ev.getX();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = mDownY = nowY;
                mLastX = mDownX = nowX;
                damp = 0.6f;
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = nowY - mLastY;
                int dx = nowX - mLastX;
                ScLog.i(TAG, "dy: " + dy
                        + " refreshViewIsOnTop:  " + refreshViewIsOnTop()
                        + "  " + (nowY - mDownY)
                        + " mPullMaxDis: " + mPullMaxDis);
                if (dy > 0 && Math.abs(dy) > Math.abs(dx) && refreshViewIsOnTop()) {
                    isIntercept = true;
                    mLastY = nowY;
                    mLastX = nowX;
                }

                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        if (isIntercept) {
            mLastY = mDownY = nowY;
            mLastX = mDownX = nowX;
        }
        ScLog.i(TAG, "onInterceptTouchEvent " + ev.getAction() + "  " + isIntercept);
        return isIntercept ? isIntercept : super.onInterceptTouchEvent(ev);
    }

    protected abstract H createIndicatorView();


    protected abstract T createRefreshView();


    public T getRefreshView() {
        return mRefreshView;
    }

    protected boolean refreshViewIsOnTop() {
        if (this.mRefreshView instanceof ScrollView) {
            return ((ScrollView) this.mRefreshView).getChildCount() == 0 ? true : this.mRefreshView.getScrollY() <= 0;
        } else if (this.mRefreshView instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) this.mRefreshView;
            View child = recyclerView.getChildAt(0);
            return null == child ? true : child.getTop() >= 0;
        } else if (this.mRefreshView instanceof ListView) {
            View child = ((ListView) mRefreshView).getChildAt(0);
            return ((ListView) mRefreshView).getFirstVisiblePosition() == 0 && child == null ? true : child.getTop() == 0;
        } else if (this.mRefreshView instanceof IRefreshAble) {
            return ((IRefreshAble) mRefreshView).onTop();
        } else {
            return true;
        }
    }

    private void moveHeader(int moveTo) {
        if (moveTo < mTopOffset) {
            moveTo = mTopOffset;
        } else if (moveTo > mPullMaxDis) {
            moveTo = mPullMaxDis;
        }
        float percent = Math.abs(moveTo - mTopOffset) * 1.0f / (mPullMaxDis + Math.abs(mTopOffset));
        mRefreshHeaderView.setPercent(percent);
        if (mOnRefreshListener != null) {
            mOnRefreshListener.onPullDown(percent);
        }
        refreshStatus = RefreshStatus.PULL_DOWN;
        ScLog.i(TAG, "movew to " + moveTo);
        nowMargin = mParams.topMargin = moveTo;
        mRefreshHeaderView.setLayoutParams(mParams);

    }

    public void refreshFinish() {
        refreshStatus = RefreshStatus.INIT;
        moveHeader(mTopOffset);
        mRefreshHeaderView.abortAnimation();
        mOnRefreshListener.onRefreshFinish();
        ScLog.i(TAG, "refreshFinish");
    }


    public void setOnRefreshListener(AbsPullToRefresh.OnRefreshListener onRefreshListener) {
        this.mOnRefreshListener = onRefreshListener;
    }

    public interface OnRefreshListener {
        void onStartRefresh();

        void onPullDown(float per);

        void onRefreshFinish();
    }


    public enum RefreshStatus {
        PULL_DOWN, REFRESHING, INIT
    }

    public interface IRefreshAble {
        boolean onTop();
    }

}
