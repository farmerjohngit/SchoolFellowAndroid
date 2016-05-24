package com.csuft.zzc.schoolfellow.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.csuft.zzc.schoolfellow.base.utils.ScreenUtil;
import com.csuft.zzc.schoolfellow.base.utils.ScrollUtil;

/**
 * Created by wangzhi on 16/3/12.
 */
public abstract class AbsPullToRefresh<H extends RefreshIndicator, T extends View> extends LinearLayout {
    private static final String TAG = "AbsPullToRefresh";
    protected H mRefreshHeaderView;
    protected T mRefreshView;
    protected View mRefreshBottomView;
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
    OnLoadMoreListener mOnLoadMoreListener;

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
        mRefreshBottomView = createRefreshBottomView();

        this.addView(mRefreshHeaderView);
        this.addView(mRefreshView);
        this.addView(mRefreshBottomView);
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
                if ((dy) > 0) {
                    ScLog.i(TAG, "nowMargin: " + nowMargin + "  mTopOffset: " + mTopOffset + "<>" + refreshViewIsOnTop());
                    if (nowMargin >= mTopOffset && refreshViewIsOnTop()) {
                        int moveTo = (nowY - mDownY);
                        if (moveTo < mTopOffset) {
                            moveTo = mTopOffset;
                        } else if (moveTo > mPullMaxDis) {
                            moveTo = mPullMaxDis;
                        }
                        float percent = Math.abs(moveTo - mTopOffset) * 1.0f / (mPullMaxDis + Math.abs(mTopOffset));
                        ScLog.i(TAG, "percent " + percent + "  :   " + (percent * -0.3 + 0.5));
                        moveHeader((int) ((nowY - mDownY) * (percent * -0.4 + 0.8)));
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
                } else {
                    if (refreshViewIsOnBottom()) {
                        if (mOnLoadMoreListener != null && refreshStatus == RefreshStatus.INIT) {
                            refreshStatus = RefreshStatus.LOADING;
                            mOnLoadMoreListener.onStartLoadMore();
                        }
                    }
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
                    refreshStatus = RefreshStatus.INIT;
                }
                break;
        }
        return super.onTouchEvent(ev);

    }


    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        ScLog.i(TAG, "requestDisallowInterceptTouchEvent " + disallowIntercept);
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
                if ((dy > 0 && Math.abs(dy) > Math.abs(dx) && refreshViewIsOnTop())
                        || (refreshViewIsOnBottom() && dy < 0)) {
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

    protected View createRefreshBottomView() {
        LinearLayout linearLayout = new LinearLayout(getContext());

        return LayoutInflater.from(getContext()).inflate(R.layout.refresh_bottom, null, false);
    }


    public T getRefreshView() {
        return mRefreshView;
    }

    protected boolean refreshViewIsOnTop() {
        return ScrollUtil.isScrollViewInTop(mRefreshView);
    }

    protected boolean refreshViewIsOnBottom() {
        return ScrollUtil.refreshViewInBottom(mRefreshView);
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
        ScLog.i(TAG, "move to " + moveTo);
        nowMargin = mParams.topMargin = moveTo;
        mRefreshHeaderView.setLayoutParams(mParams);

    }

    public void refreshFinish() {
        moveHeader(mTopOffset);
        refreshStatus = RefreshStatus.INIT;
        mRefreshHeaderView.abortAnimation();
        if (mOnRefreshListener != null) {
            mOnRefreshListener.onRefreshFinish();
        }
        ScLog.i(TAG, "refreshFinish");
    }

    public void loadFinish() {
        refreshStatus = RefreshStatus.INIT;
        if (mOnLoadMoreListener != null) {
            mOnLoadMoreListener.onLoadFinish();
        }
        ScLog.i(TAG, "loadFinish");
    }


    public void setOnRefreshListener(AbsPullToRefresh.OnRefreshListener onRefreshListener) {
        this.mOnRefreshListener = onRefreshListener;
    }


    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.mOnLoadMoreListener = onLoadMoreListener;
    }

    public interface OnRefreshListener {
        void onStartRefresh();

        void onPullDown(float per);

        void onRefreshFinish();

    }

    public interface OnLoadMoreListener {
        void onStartLoadMore();

        void onLoadFinish();

    }


    public enum RefreshStatus {
        PULL_DOWN, REFRESHING, INIT, LOADING
    }

    public interface IRefreshAble {
        boolean onTop();

        boolean onBottom();
    }

}
